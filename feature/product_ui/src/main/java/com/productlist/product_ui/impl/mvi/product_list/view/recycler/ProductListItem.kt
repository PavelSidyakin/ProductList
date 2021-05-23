package com.productlist.product_ui.impl.mvi.product_list.view.recycler

import com.productlist.product_domain.model.Product

/**
 * The item represents visual appearance of the product in the list.
 */
internal data class ProductListItem(
    val product: Product,
)
