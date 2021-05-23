package com.productlist.product_ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewTreeObserver
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.arkivanov.mvikotlin.core.lifecycle.asMviLifecycle
import com.arkivanov.mvikotlin.keepers.instancekeeper.getInstanceKeeper
import com.productlist.product_ui.ProductUiComponentHolder
import com.productlist.product_ui.databinding.FragmentProductListBinding
import com.productlist.product_ui.impl.mvi.product_list.controller.ProductListController
import com.productlist.product_ui.impl.mvi.product_list.view.ProductListViewImpl

/**
 * Product list fragment.
 *
 * Displays product list.
 */
class ProductListFragment : Fragment() {

    private val productUiInjector by lazy {
        ProductUiComponentHolder.getInjector()
    }

    // MVI Kotlin entities
    private val instanceKeeper by lazy { getInstanceKeeper() }
    private val controller: ProductListController by lazy {
        productUiInjector.productListControllerFactory.create(
            instanceKeeper = instanceKeeper,
            dependencies = object : ProductListController.Dependencies {
                override val productSelectedCallback: (ProductListController.Output) -> Unit =
                    { output: ProductListController.Output ->
                        when (output) {
                            is ProductListController.Output.ProductSelected -> {
                                productIdSharedViewModel.setProductId(output.productId)
                            }
                        }
                    }
            }
        )
    }

    // View binding
    private var _binding: FragmentProductListBinding? = null
    private val binding get() = _binding!!

    // Shared view model to notify the product selection.
    private lateinit var productIdSharedViewModel: ProductIdSharedViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        productIdSharedViewModel = requireActivity().run {
            ViewModelProvider(this).get(ProductIdSharedViewModel::class.java)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProductListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        // We need width of the fake item to determine column count correctly.
        // So, wait for the first global layout.
//        view.viewTreeObserver.addOnGlobalLayoutListener(object :
//            ViewTreeObserver.OnGlobalLayoutListener {
//            override fun onGlobalLayout() {
//                view.viewTreeObserver.removeOnGlobalLayoutListener(this)
//            }
//        })

        controller.onViewCreated(ProductListViewImpl(binding), lifecycle.asMviLifecycle())

    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }

    internal class ProductIdSharedViewModel : ViewModel() {

        private val liveData = MutableLiveData<Long?>()

        fun observeProductId(lifecycleOwner: LifecycleOwner, block: (productId: Long) -> Unit) {
            liveData.observe(lifecycleOwner) { value ->
                if (value != null) {
                    block(value)
                    liveData.value = null
                }
            }
        }

        fun setProductId(productId: Long) {
            liveData.value = productId
        }
    }

    companion object {
        fun observeProductId(activity: AppCompatActivity, block: (productId: Long) -> Unit) {
            ViewModelProvider(activity).get(ProductIdSharedViewModel::class.java)
                .observeProductId(activity) { productId: Long ->
                    block(productId)
                }
        }
    }

}