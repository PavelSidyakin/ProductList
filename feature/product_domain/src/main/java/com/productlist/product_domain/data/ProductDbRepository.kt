package com.productlist.product_domain.data

import com.productlist.product_domain.model.Product
import kotlinx.coroutines.flow.Flow

interface ProductDbRepository {

    fun observeProducts(): Flow<List<Product>>

    suspend fun requestProduct(productId: Long): Product

    suspend fun insertProducts(products: List<Product>)

    suspend fun updateFavoriteStatus(productId: Long, isFavorite: Boolean)

    suspend fun clearProducts()
    suspend fun withTransaction(block: suspend () -> Unit)
}