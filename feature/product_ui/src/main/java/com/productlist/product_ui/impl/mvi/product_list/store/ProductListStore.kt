package com.productlist.product_ui.impl.mvi.product_list.store

import com.arkivanov.mvikotlin.core.store.Store
import com.productlist.product_ui.impl.mvi.product_list.view.recycler.ProductListItem

/**
 * MVIKotlin store interface.
 */
internal interface ProductListStore :
    Store<ProductListStore.Intent, ProductListStore.State, Nothing> {

    sealed class Intent {
        /**
         * Fired when the product is selected.
         *
         * @param productId The product id.
         */
        data class OnProductSelected(val productId: Long) : Intent()

        /**
         * Fired when the product's favorite state is changed.
         *
         * @param productId The product id.
         * @param isFavorite The new favorite state.
         */
        data class OnIsFavoriteChanged(val productId: Long, val isFavorite: Boolean) : Intent()
    }

    data class State(
        val isInProgress: Boolean = false,
        val productList: List<ProductListItem>? = null,
        val error: Throwable? = null,
    )
}