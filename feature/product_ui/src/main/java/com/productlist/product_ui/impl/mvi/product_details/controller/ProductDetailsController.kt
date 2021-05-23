package com.productlist.product_ui.impl.mvi.product_details.controller

import com.arkivanov.mvikotlin.core.lifecycle.Lifecycle
import com.productlist.product_ui.impl.mvi.product_details.view.ProductDetailsView

/**
 * MVIKotlin controller interface.
 *
 */
internal interface ProductDetailsController {

    /**
     * Must be called after the view creation.
     */
    fun onViewCreated(productListView: ProductDetailsView, viewLifecycle: Lifecycle)

    interface Dependencies {
    }
}