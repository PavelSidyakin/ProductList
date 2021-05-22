package com.productlist.product_domain.model

data class Product(
    val id: Long,
    val title: String,
    val author: String,
    val imageUrl: String,
    val isFavorite: Boolean,
)
