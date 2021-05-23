package com.productlist.product_ui.impl.mvi.product_details.store

import com.productlist.product_domain.model.Product

/**
 * MVIKotlin state changes.
 */
internal sealed class ProductDetailsStateChanges {
    /**
     * Notifies the product state changed.
     * @param product The new product data.
     */
    data class ProductChanged(val product: Product): ProductDetailsStateChanges()
}
