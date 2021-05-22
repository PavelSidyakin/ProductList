package com.productlist.product_ui.impl.mvi.product_list.view.recycler

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.productlist.product_ui.R
import com.productlist.product_ui.databinding.ViewProductListItemBinding

internal class ProductListItemViewHolder private constructor(
    private val binding: ViewProductListItemBinding,
    private val productListItemClickListener: ProductListItemClickListener,
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(productListItem: ProductListItem) {
        bindIsFavorite(productListItem.product.isFavorite)

        binding.productAuthorTextview.text = productListItem.product.author
        binding.productTitleTextview.text = productListItem.product.title

        if (productListItem.product.imageUrl.isNullOrEmpty()) {
            binding.productImageImageview.setImageResource(R.drawable.ic_image_placeholder)
        } else {
            Glide.with(binding.root.context)
                .load(Uri.parse(productListItem.product.imageUrl))
                .placeholder(R.drawable.ic_loading_placeholder)
                .error(R.drawable.ic_loading_placeholder)
                .into(binding.productImageImageview)
        }

        itemView.setOnClickListener { productListItemClickListener.onProductClicked(productListItem.product) }
    }

    fun bindIsFavorite(isFavorite: Boolean) {
        binding.favoriteCheckbox.isChecked = isFavorite
    }

    companion object {

        fun create(
            parent: ViewGroup,
            productListItemClickListener: ProductListItemClickListener,
        ): ProductListItemViewHolder {

            val binding: ViewProductListItemBinding = ViewProductListItemBinding.inflate(LayoutInflater.from(parent.context),
                parent, false,
            )

            return ProductListItemViewHolder(binding, productListItemClickListener)
        }
    }

}