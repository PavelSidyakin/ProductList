package com.productlist.product_ui.impl.mvi.product_details.view

import android.net.Uri
import com.arkivanov.mvikotlin.core.utils.diff
import com.arkivanov.mvikotlin.core.view.BaseMviView
import com.arkivanov.mvikotlin.core.view.ViewRenderer
import com.bumptech.glide.Glide
import com.productlist.product_ui.R
import com.productlist.product_ui.databinding.FragmentProductDetailsBinding
import com.productlist.product_ui.impl.mvi.product_details.store.ProductDetailsStore


internal class ProductDetailsViewImpl(
    private val productId: Long,
    private val binding: FragmentProductDetailsBinding,
) : BaseMviView<ProductDetailsStore.State, ProductDetailsStore.Intent>(), ProductDetailsView {


    override val renderer: ViewRenderer<ProductDetailsStore.State>? = diff {
        diff(get = { it.product?.isFavorite }, set = { setIsFavoriteState(it ?: false) })
        diff(get = { it.product?.author }, set = { binding.productAuthorTextview.text = it })
        diff(get = { it.product?.title }, set = { binding.productTitleTextview.text = it })
        diff(get = { it.product?.imageUrl }, set = { showProductImage(it) })
    }

    init {
        setOnIsFavoriteChangedListener()
    }

    private fun showProductImage(url: String?) {
        when (url == null) {
            true -> binding.productImageImageview.setImageResource(R.drawable.ic_image_placeholder)
            false -> {
                Glide.with(binding.root.context)
                    .load(Uri.parse(url))
                    .placeholder(R.drawable.ic_loading_placeholder)
                    .error(R.drawable.ic_loading_placeholder)
                    .into(binding.productImageImageview)
            }
        }
    }

    private fun setOnIsFavoriteChangedListener() {
        binding.favoriteCheckbox.setOnCheckedChangeListener { _, isChecked ->
            dispatch(ProductDetailsStore.Intent.OnIsFavoriteChanged(productId, isChecked))
        }
    }

    private fun setIsFavoriteState(isFavorite: Boolean) {
        // Change is favorite state without the listener notification.

        binding.favoriteCheckbox.setOnCheckedChangeListener(null)
        binding.favoriteCheckbox.isChecked = isFavorite
        setOnIsFavoriteChangedListener()
    }

}