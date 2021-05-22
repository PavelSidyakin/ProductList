package com.productlist.product_domain.domain

import com.productlist.product_domain.data.ProductDbRepository
import com.productlist.product_domain.data.ProductSourceRepository
import com.productlist.product_domain.model.Product
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

internal class ProductInteractorImpl @Inject constructor(
    private val dbRepository: ProductDbRepository,
    private val sourceRepository: ProductSourceRepository,
): ProductInteractor {

    override fun observeProducts(): Flow<List<Product>> {
        return dbRepository.observeProducts()
    }

    override suspend fun requestProduct(productId: Long): Product {
        return dbRepository.requestProduct(productId)
    }

    override suspend fun loadProducts() {
        dbRepository.withTransaction {
            dbRepository.clearProducts()
            dbRepository.insertProducts(sourceRepository.requestProducts())
        }
    }

    override suspend fun updateFavoriteStatus(productId: Long, isFavorite: Boolean) {
        dbRepository.updateFavoriteStatus(productId, isFavorite)
    }

}