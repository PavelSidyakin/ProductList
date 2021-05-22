package com.productlist.product_ui.impl.mvi.product_list.store

import com.arkivanov.mvikotlin.core.store.Store
import com.productlist.product_ui.impl.mvi.product_list.view.recycler.ProductListItem

internal interface ProductListStore : Store<ProductListStore.Intent, ProductListStore.State, ProductListStore.Label> {

    sealed class Intent {
        data class OnProductSelected(val productId: Long): Intent()
    }

    data class State(
        val isLoading: Boolean = false,
        val productList: List<ProductListItem>? = null,
        val error: Throwable? = null,
    )

    sealed class Label {

    }
}