package com.productlist.product_ui.impl.mvi.product_list.view.recycler

import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.productlist.product_domain.model.Product

internal class ProductListAdapter(
    private val productListItemListener: ProductListItemListener,
) : ListAdapter<ProductListItem, ProductListItemViewHolder>(DiffUtilItemCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductListItemViewHolder {
        return ProductListItemViewHolder.create(parent, productListItemListener)
    }

    override fun onBindViewHolder(holder: ProductListItemViewHolder, position: Int) {
        getItem(position)?.let { holder.bind(it) }
    }

    override fun onBindViewHolder(holder: ProductListItemViewHolder, position: Int, payloads: MutableList<Any>) {
        if (payloads.isEmpty()) {
            return super.onBindViewHolder(holder, position, payloads)
        }

        val payload: Pair<ProductListItem, ProductListItem> =
            (payloads[0] as? Pair<ProductListItem, ProductListItem>) ?: return

        val oldProduct: Product = payload.first.product
        val newProduct: Product = payload.second.product

        when (newProduct) {
            oldProduct.copy(isFavorite = newProduct.isFavorite) -> holder.bindIsFavorite(newProduct.isFavorite)
            else -> super.onBindViewHolder(holder, position, payloads)
        }
    }

    class DiffUtilItemCallback : DiffUtil.ItemCallback<ProductListItem>() {
        override fun areItemsTheSame(oldItem: ProductListItem, newItem: ProductListItem): Boolean {
            return oldItem.product.id == newItem.product.id
        }

        override fun areContentsTheSame(oldItem: ProductListItem, newItem: ProductListItem): Boolean {
            return oldItem.product == newItem.product
        }

        override fun getChangePayload(oldItem: ProductListItem, newItem: ProductListItem): Any? {
            return if (oldItem == newItem) {
                null
            } else {
                oldItem to newItem
            }
        }
    }
}