package com.productlist.product_ui.impl.mvi.product_list.store

internal interface ProductListIntentExecutorFactory {
    fun create(): ProductListIntentExecutor
}
