package com.productlist.product_ui.impl.mvi.product_list.view.recycler

internal interface ProductListItemListener {
    fun onProductClicked(productId: Long)
    fun onFavoriteStateChanged(productId: Long, isFavorite: Boolean)
}