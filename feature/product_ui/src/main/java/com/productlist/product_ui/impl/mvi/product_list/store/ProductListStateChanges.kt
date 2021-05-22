package com.productlist.product_ui.impl.mvi.product_list.store

import com.productlist.product_ui.impl.mvi.product_list.view.recycler.ProductListItem

internal sealed class ProductListStateChanges {
    data class ListChanged(val productList: List<ProductListItem>): ProductListStateChanges()
}
