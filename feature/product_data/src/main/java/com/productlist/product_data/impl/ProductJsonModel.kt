package com.productlist.product_data.impl

import com.google.gson.annotations.SerializedName

internal data class ProductJsonModel(
    @SerializedName("title")
    val title: String,

    @SerializedName("author")
    val author: String?,

    @SerializedName("imageURL")
    val imageURL: String?,
)