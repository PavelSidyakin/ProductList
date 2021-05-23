package com.productlist.product_ui.impl.mvi.product_details.store

import com.arkivanov.mvikotlin.core.store.Store
import com.productlist.product_domain.model.Product

/**
 * MVIKotlin store interface.
 */
internal interface ProductDetailsStore :
    Store<ProductDetailsStore.Intent, ProductDetailsStore.State, Nothing> {

    sealed class Intent {
        /**
         * Fired when the product favorite state is changed.
         *
         * @param productId The affected product ID.
         * @param isFavorite the new favorite state.
         */
        data class OnIsFavoriteChanged(val productId: Long, val isFavorite: Boolean) : Intent()
    }

    data class State(
        val product: Product? = null,
        val isLoading: Boolean = false,
        val error: Throwable? = null,
    )
}