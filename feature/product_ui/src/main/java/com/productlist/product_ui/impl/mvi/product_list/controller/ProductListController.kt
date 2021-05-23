package com.productlist.product_ui.impl.mvi.product_list.controller

import com.arkivanov.mvikotlin.core.lifecycle.Lifecycle
import com.productlist.product_ui.impl.mvi.product_list.view.ProductListView

/**
 * MVIKotlin controller interface.
 *
 */
internal interface ProductListController {

    /**
     * Must be called after the view creation.
     */
    fun onViewCreated(productListView: ProductListView, viewLifecycle: Lifecycle)

    interface Dependencies {
        /**
         * Used to notify the outer world about the item selection.
         */
        val productSelectedCallback: (Output) -> Unit
    }

    sealed class Output {
        /**
         * Fired when the product is selected.
         *
         * @param productId The product ID.
         */
        data class ProductSelected(val productId: Long) : Output()
    }
}