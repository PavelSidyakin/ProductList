package com.productlist.product_domain.domain

import com.productlist.product_domain.model.Product
import kotlinx.coroutines.flow.Flow

interface ProductInteractor {
    fun observeProducts(): Flow<List<Product>>

    suspend fun requestProduct(productId: Long): Product

    suspend fun loadProducts()

    suspend fun updateFavoriteStatus(productId: Long, isFavorite: Boolean)
}