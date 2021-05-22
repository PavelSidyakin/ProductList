package com.productlist.product_ui.impl.mvi.product_list.controller

import com.arkivanov.mvikotlin.core.lifecycle.Lifecycle
import com.productlist.product_ui.impl.mvi.product_list.view.ProductListView

internal interface ProductListController {

    fun onViewCreated(productListView: ProductListView, viewLifecycle: Lifecycle)

    interface Dependencies {
        val productSelectedCallback: (Output) -> Unit
    }

    sealed class Output {
        data class ProductSelected(val productId: Long): Output()
    }
}