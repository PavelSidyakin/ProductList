package com.productlist.product_ui.impl.mvi.product_list.view.recycler

/**
 * List items UI events listener.
 */
internal interface ProductListItemListener {
    /**
     * Invoked when the product is clicked.
     *
     * @param productId The product ID.
     */
    fun onProductClicked(productId: Long)

    /**
     * Invoked when the product favorite state is changed.
     *
     * @param productId The product ID.
     * @param isFavorite The new favorite state.
     */
    fun onFavoriteStateChanged(productId: Long, isFavorite: Boolean)
}