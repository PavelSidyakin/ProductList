package com.productlist.product_ui.impl.mvi.product_list.store

import com.arkivanov.mvikotlin.core.store.Reducer

internal class ProductListReducer: Reducer<ProductListStore.State, ProductListStateChanges> {

    override fun ProductListStore.State.reduce(result: ProductListStateChanges): ProductListStore.State {
        return when (result) {
            is ProductListStateChanges.ListChanged -> copy(productList = result.productList)
        }
    }
}