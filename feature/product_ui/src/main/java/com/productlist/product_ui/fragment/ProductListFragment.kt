package com.productlist.product_ui.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewTreeObserver
import androidx.fragment.app.Fragment
import com.arkivanov.mvikotlin.core.lifecycle.asMviLifecycle
import com.arkivanov.mvikotlin.keepers.instancekeeper.getInstanceKeeper
import com.productlist.product_ui.ProductUiComponentHolder
import com.productlist.product_ui.databinding.FragmentProductListBinding
import com.productlist.product_ui.impl.mvi.product_list.controller.ProductListController
import com.productlist.product_ui.impl.mvi.product_list.view.ProductListViewImpl

class ProductListFragment : Fragment() {

    private val instanceKeeper by lazy { getInstanceKeeper() }

    private val controller: ProductListController by lazy {
        ProductUiComponentHolder.getInjector().productListControllerFactory.create(
            instanceKeeper = instanceKeeper,
            dependencies = object : ProductListController.Dependencies {
                override val productSelectedCallback: (ProductListController.Output) -> Unit =
                    { output: ProductListController.Output ->
                        when (output) {
                            is ProductListController.Output.ProductSelected -> {
                                Log.i("Product", "Selected: ${output.productId}")
                            }
                        }
                    }
            }
        )
    }

    private var _binding: FragmentProductListBinding? = null

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentProductListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        // We need width of the fake item to determine column count correctly.
        // So, wait for the first global layout.
        view.viewTreeObserver.addOnGlobalLayoutListener(object :
            ViewTreeObserver.OnGlobalLayoutListener {
            override fun onGlobalLayout() {
                controller.onViewCreated(ProductListViewImpl(binding), lifecycle.asMviLifecycle())
                view.viewTreeObserver.removeOnGlobalLayoutListener(this)
            }
        })
    }

}