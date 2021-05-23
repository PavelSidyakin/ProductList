package com.productlist.product_ui.impl.mvi.product_details.store

import com.arkivanov.mvikotlin.core.store.Reducer

/**
 * MVIKotlin reducer class.
 */
internal class ProductDetailsReducer: Reducer<ProductDetailsStore.State, ProductDetailsStateChanges> {

    override fun ProductDetailsStore.State.reduce(result: ProductDetailsStateChanges): ProductDetailsStore.State {
        return when (result) {
            is ProductDetailsStateChanges.ProductChanged -> copy(product = result.product)
        }
    }
}