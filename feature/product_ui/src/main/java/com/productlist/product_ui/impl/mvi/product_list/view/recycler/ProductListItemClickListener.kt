package com.productlist.product_ui.impl.mvi.product_list.view.recycler

import com.productlist.product_domain.model.Product

internal interface ProductListItemClickListener {
    fun onProductClicked(product: Product)
}