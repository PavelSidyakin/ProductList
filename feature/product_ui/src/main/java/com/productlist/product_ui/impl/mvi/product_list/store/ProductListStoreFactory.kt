package com.productlist.product_ui.impl.mvi.product_list.store

/**
 * MVIKotlin store factory.
 */
internal interface ProductListStoreFactory {
    /**
     * Creates [ProductListStore].
     *
     * @return new [ProductListStore] instance.
     */
    fun create(): ProductListStore
}