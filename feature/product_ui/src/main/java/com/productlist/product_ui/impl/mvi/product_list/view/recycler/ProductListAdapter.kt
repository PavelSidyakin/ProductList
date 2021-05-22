package com.productlist.product_ui.impl.mvi.product_list.view.recycler

import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.productlist.product_domain.model.Product

internal class ProductListAdapter(
    private val productListItemClickListener: ProductListItemClickListener,
) : RecyclerView.Adapter<ProductListItemViewHolder>() {

    var products: List<ProductListItem>? = null
        set(value) {
            val diffResult: DiffUtil.DiffResult = DiffUtil.calculateDiff(
                DiffUtilCallback(
                    newProducts = field ?: emptyList(),
                    oldProducts = value ?: emptyList(),
                )
            )
            field = value
            diffResult.dispatchUpdatesTo(this)
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductListItemViewHolder {
        return ProductListItemViewHolder.create(parent, productListItemClickListener)
    }

    override fun onBindViewHolder(holder: ProductListItemViewHolder, position: Int) {
        products?.get(position)?.let { holder.bind(it) }
    }

    override fun onBindViewHolder(
        holder: ProductListItemViewHolder,
        position: Int,
        payloads: MutableList<Any>,
    ) {
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

    override fun getItemCount(): Int = products?.size ?: 0

    class DiffUtilCallback(
        private val newProducts: List<ProductListItem>,
        private val oldProducts: List<ProductListItem>,
    ) : DiffUtil.Callback() {

        override fun getOldListSize(): Int {
            return oldProducts.size
        }

        override fun getNewListSize(): Int {
            return newProducts.size
        }

        override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return oldProducts[oldItemPosition].product.id == newProducts[newItemPosition].product.id
        }

        override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return oldProducts[oldItemPosition] == newProducts[newItemPosition]
        }

        override fun getChangePayload(oldItemPosition: Int, newItemPosition: Int): Any? {
            val oldProductListItem: ProductListItem = oldProducts[oldItemPosition]
            val newProductListItem: ProductListItem = newProducts[newItemPosition]

            if (oldProductListItem == newProductListItem) {
                return null
            } else {
                return oldProductListItem to newProductListItem
            }
        }
    }
}