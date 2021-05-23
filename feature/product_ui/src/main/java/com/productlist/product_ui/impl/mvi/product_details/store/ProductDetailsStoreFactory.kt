package com.productlist.product_ui.impl.mvi.product_details.store

/**
 * MVIKotlin store factory.
 */
internal interface ProductDetailsStoreFactory {
    /**
     * Creates [ProductDetailsStore].
     *
     * @param productId The product ID.
     * @return new [ProductDetailsStore] instance.
     */
    fun create(productId: Long): ProductDetailsStore
}