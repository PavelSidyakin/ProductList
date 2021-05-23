package com.productlist.product_ui.impl.mvi.product_list.controller

import com.productlist.product_ui.impl.mvi.product_list.store.ProductListStore

/**
 * Mapper of [ProductListStore.Intent] to [ProductListController.Output]
 */
internal val productListIntentToOutput: ProductListStore.Intent.() -> ProductListController.Output? = {
    when (this) {
        is ProductListStore.Intent.OnProductSelected -> ProductListController.Output.ProductSelected(productId)
        else -> null
    }
}