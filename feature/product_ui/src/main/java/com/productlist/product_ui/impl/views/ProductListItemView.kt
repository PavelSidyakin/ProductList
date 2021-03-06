package com.productlist.product_ui.impl.views

import android.content.Context
import android.net.Uri
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.FrameLayout
import com.bumptech.glide.Glide
import com.productlist.product_ui.R
import com.productlist.product_ui.databinding.ViewProductListItemBinding

internal class ProductListItemView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0,
) : FrameLayout(context, attrs, defStyleAttr) {

    var title: CharSequence? = null
        set(value) {
            binding.productTitleTextview.text = value
            field = value
        }

    var author: CharSequence? = null
        set(value) {
            binding.productAuthorTextview.text = value
            field = value
        }

    var isFavorite: Boolean
        get() = binding.favoriteCheckbox.isChecked
        set(value) {
            val onIsFavoriteChangedListenerBak = onIsFavoriteChangedListener
            onIsFavoriteChangedListener = null
            binding.favoriteCheckbox.isChecked = value
            onIsFavoriteChangedListener = onIsFavoriteChangedListenerBak
        }

    var imageUrl: String? = null
        set(value) {
            if (value.isNullOrEmpty()) {
                binding.productImageImageview.setImageResource(R.drawable.ic_image_placeholder)
            } else {
                Glide.with(binding.root.context)
                    .load(Uri.parse(value))
                    .placeholder(R.drawable.ic_loading_placeholder)
                    .error(R.drawable.ic_loading_placeholder)
                    .into(binding.productImageImageview)
            }
            field = value
        }

    var onIsFavoriteChangedListener: ((isFavorite: Boolean) -> Unit)? = null
        set(value) {
            if (value == null) {
                binding.favoriteCheckbox.setOnCheckedChangeListener(null)
            } else {
                binding.favoriteCheckbox.setOnCheckedChangeListener { _, isChecked ->
                    value(isChecked)
                }
            }
            field = value
        }

    private val binding: ViewProductListItemBinding =
        ViewProductListItemBinding.inflate(LayoutInflater.from(context), this, true)
}