package com.productlist.product_ui.impl.mvi.product_details.store

internal interface ProductDetailsIntentExecutorFactory {
    fun create(productId: Long): ProductDetailsIntentExecutor
}
