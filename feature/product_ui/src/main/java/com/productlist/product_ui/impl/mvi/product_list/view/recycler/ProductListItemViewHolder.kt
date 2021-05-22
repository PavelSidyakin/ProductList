package com.productlist.product_ui.impl.mvi.product_list.view.recycler

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.productlist.product_ui.impl.views.ProductListItemView

internal class ProductListItemViewHolder private constructor(
    private val listItemView: ProductListItemView,
    private val productListItemListener: ProductListItemListener,
) : RecyclerView.ViewHolder(listItemView) {

    fun bind(productListItem: ProductListItem) {
        bindIsFavorite(productListItem.product.isFavorite)

        listItemView.author = productListItem.product.author
        listItemView.title = productListItem.product.title
        listItemView.imageUrl = productListItem.product.imageUrl

        listItemView.onIsFavoriteChangedListener = { isFavorite ->
            productListItemListener.onFavoriteStateChanged(productListItem.product.id, isFavorite)
        }

        itemView.setOnClickListener { productListItemListener.onProductClicked(productListItem.product.id) }
    }

    fun bindIsFavorite(isFavorite: Boolean) {
        listItemView.isFavorite = isFavorite
    }

    companion object {

        fun create(
            parent: ViewGroup,
            productListItemListener: ProductListItemListener,
        ): ProductListItemViewHolder {

            return ProductListItemViewHolder(ProductListItemView(parent.context), productListItemListener)
        }
    }

}