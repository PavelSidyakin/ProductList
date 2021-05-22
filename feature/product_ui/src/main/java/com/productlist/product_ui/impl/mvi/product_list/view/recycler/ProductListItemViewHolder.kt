package com.productlist.product_ui.impl.mvi.product_list.view.recycler

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.productlist.product_ui.impl.views.ProductListItemView

internal class ProductListItemViewHolder private constructor(
    private val listItemView: ProductListItemView,
    private val productListItemClickListener: ProductListItemClickListener,
) : RecyclerView.ViewHolder(listItemView) {

    fun bind(productListItem: ProductListItem) {
        bindIsFavorite(productListItem.product.isFavorite)

        listItemView.author = productListItem.product.author
        listItemView.title = productListItem.product.title
        listItemView.imageUrl = productListItem.product.imageUrl

        itemView.setOnClickListener { productListItemClickListener.onProductClicked(productListItem.product) }
    }

    fun bindIsFavorite(isFavorite: Boolean) {
        listItemView.isFavorite = isFavorite
    }

    companion object {

        fun create(
            parent: ViewGroup,
            productListItemClickListener: ProductListItemClickListener,
        ): ProductListItemViewHolder {

            return ProductListItemViewHolder(ProductListItemView(parent.context), productListItemClickListener)
        }
    }

}