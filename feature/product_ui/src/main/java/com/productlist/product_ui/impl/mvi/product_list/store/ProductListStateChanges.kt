package com.productlist.product_ui.impl.mvi.product_list.store

import com.productlist.product_ui.impl.mvi.product_details.store.ProductDetailsStateChanges
import com.productlist.product_ui.impl.mvi.product_list.view.recycler.ProductListItem

/**
 * MVIKotlin state changes.
 */
internal sealed class ProductListStateChanges {
    /**
     * Notifies about the list changed.
     *
     * @param productList The new list.
     */
    data class ListChanged(val productList: List<ProductListItem>) : ProductListStateChanges()

    /**
     * Notifies about the progress state changed.
     *
     * @param isInProgress The new progress state.
     */
    data class ProgressStateChanged(val isInProgress: Boolean) : ProductListStateChanges()

    /**
     * Notifies about error.
     *
     * @param error The exception.
     */
    data class ErrorChanged(val error: Throwable?): ProductListStateChanges()

}
