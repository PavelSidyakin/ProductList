package com.productlist.product_ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.arkivanov.mvikotlin.core.lifecycle.asMviLifecycle
import com.arkivanov.mvikotlin.keepers.instancekeeper.getInstanceKeeper
import com.productlist.product_ui.ProductUiComponentHolder
import com.productlist.product_ui.databinding.FragmentProductDetailsBinding
import com.productlist.product_ui.fragment.ProductDetailsFragment.Companion.createArgumentsBundle
import com.productlist.product_ui.impl.mvi.product_details.controller.ProductDetailsController
import com.productlist.product_ui.impl.mvi.product_details.view.ProductDetailsViewImpl

/**
 * Product details fragment.
 *
 * Displays product details.
 *
 * Specified arguments must be provided. See [createArgumentsBundle].
 */
class ProductDetailsFragment : DialogFragment() {

    private val productUiInjector by lazy {
        ProductUiComponentHolder.getInjector()
    }

    // MVI Kotlin entities
    private val instanceKeeper by lazy { getInstanceKeeper() }
    private val controller: ProductDetailsController by lazy {
        productUiInjector.productDetailsControllerFactory.create(
            productId = arguments.readProductId(),
            instanceKeeper = instanceKeeper,
            dependencies = object : ProductDetailsController.Dependencies {
            }
        )
    }

    // View binding
    private var _binding: FragmentProductDetailsBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProductDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        controller.onViewCreated(ProductDetailsViewImpl(arguments.readProductId(), binding), viewLifecycleOwner.lifecycle.asMviLifecycle())

    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }

    companion object {
        private const val PRODUCT_ID_ARGUMENT_KEY = "PRODUCT_ID_ARGUMENT_KEY"

        /**
         * Creates arguments for the [ProductDetailsFragment].
         *
         * @param productId The product ID to be displayed in the fragment.
         * @return [Bundle] to be used as the fragment's arguments.
         */
        fun createArgumentsBundle(productId: Long): Bundle {
            return Bundle().apply {
                putLong(PRODUCT_ID_ARGUMENT_KEY, productId)
            }
        }

        private fun Bundle?.readProductId(): Long {
            return this?.getLong(PRODUCT_ID_ARGUMENT_KEY)
                ?: throw IllegalArgumentException("Product ID is not set")
        }
    }
}