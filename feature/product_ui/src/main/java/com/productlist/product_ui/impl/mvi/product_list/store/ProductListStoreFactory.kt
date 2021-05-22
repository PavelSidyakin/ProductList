package com.productlist.product_ui.impl.mvi.product_list.store

internal interface ProductListStoreFactory {
    fun create(): ProductListStore
}