package com.productlist.product_domain.data

import com.productlist.product_domain.model.Product

interface ProductSourceRepository {
    suspend fun requestProducts(): List<Product>
}