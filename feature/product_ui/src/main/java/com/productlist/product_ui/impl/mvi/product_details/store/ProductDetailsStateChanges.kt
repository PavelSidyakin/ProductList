package com.productlist.product_ui.impl.mvi.product_details.store

import com.productlist.product_domain.model.Product

internal sealed class ProductDetailsStateChanges {
    data class ProductChanged(val product: Product): ProductDetailsStateChanges()
}
