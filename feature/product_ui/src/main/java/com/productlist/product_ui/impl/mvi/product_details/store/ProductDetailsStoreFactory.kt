package com.productlist.product_ui.impl.mvi.product_details.store

internal interface ProductDetailsStoreFactory {
    fun create(productId: Long): ProductDetailsStore
}