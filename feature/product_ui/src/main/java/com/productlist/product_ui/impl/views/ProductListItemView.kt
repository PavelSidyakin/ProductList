package com.productlist.product_ui.impl.views

import android.content.Context
import android.net.Uri
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.FrameLayout
import com.bumptech.glide.Glide
import com.productlist.product_ui.R
import com.productlist.product_ui.databinding.ViewProductListItemBinding


internal class ProductListItemView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0,
) : FrameLayout(context, attrs, defStyleAttr) {

    private val binding: ViewProductListItemBinding by lazy {
        ViewProductListItemBinding.inflate(LayoutInflater.from(context), this, true)
    }

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
            binding.favoriteCheckbox.isChecked = value
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

    init {
        View.inflate(context, R.layout.view_product_list_item, this)
    }
}