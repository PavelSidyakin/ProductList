package com.productlist.product_ui.impl.mvi.product_list.store

import com.productlist.product_domain.domain.ProductInteractor
import com.productlist.product_domain.model.Product
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.map

/**
 * Stub simulates the real interactor behaviour.
 */
internal class ProductInteractorStub(private val products: List<Product>) : ProductInteractor {

    private val productsFlow: MutableSharedFlow<List<Product>> = MutableSharedFlow()

    override fun observeProducts(): Flow<List<Product>> {
        return productsFlow
    }

    override fun observeProduct(productId: Long): Flow<Product> {
        return productsFlow.map { it.find { it.id == productId }!! }
    }

    override suspend fun loadProducts() {
        productsFlow.emit(value = products)
    }

    override suspend fun updateFavoriteStatus(productId: Long, isFavorite: Boolean) {
        productsFlow.emit((products.map { product ->
            if (product.id == productId) product.copy(isFavorite = isFavorite) else product
        }))
    }
}