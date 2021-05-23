package com.productlist.product_domain.model

data class Product(
    /**
     * The product unique identifier.
     */
    val id: Long,

    /**
     * The product title.
     */
    val title: String,

    /**
     * The product author.
     * null if author in not applicable.
     */
    val author: String?,

    /**
     * The product image URL.
     * null if the image is not available.
     */
    val imageUrl: String?,

    /**
     * The product favorite status.
     * true - favorite,
     * false - not favorite.
     */
    val isFavorite: Boolean,
)
