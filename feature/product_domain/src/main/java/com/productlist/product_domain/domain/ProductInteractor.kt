package com.productlist.product_domain.domain

import com.productlist.product_domain.model.Product
import kotlinx.coroutines.flow.Flow

interface ProductInteractor {
    fun observeProducts(): Flow<List<Product>>

    fun observeProduct(productId: Long): Flow<Product>

    suspend fun loadProducts()

    suspend fun updateFavoriteStatus(productId: Long, isFavorite: Boolean)
}