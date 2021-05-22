package com.productlist.product_ui.impl.mvi.product_details.controller

import com.arkivanov.mvikotlin.core.lifecycle.Lifecycle
import com.productlist.product_ui.impl.mvi.product_details.view.ProductDetailsView

internal interface ProductDetailsController {

    fun onViewCreated(productListView: ProductDetailsView, viewLifecycle: Lifecycle)

    interface Dependencies {
    }

    sealed class Output {
    }
}