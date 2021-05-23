package com.productlist.product_domain.domain

import com.productlist.product_domain.model.Product
import kotlinx.coroutines.flow.Flow

interface ProductInteractor {
    /**
     * Observes product list updates.
     * Emits the current list immediately on collect.
     *
     * @return [Flow] which emits list of [Product] on each update of the list.
     * @error: on unexpected situation.
     */
    fun observeProducts(): Flow<List<Product>>

    /**
     * Observes product with the specified ID.
     * Emits the current product immediately on collect.
     *
     * @return [Flow] which emits [Product] with the specified id on each update of the product.
     * @param productId The product id to observe.
     * @error: on unexpected situation.
     */
    fun observeProduct(productId: Long): Flow<Product>

    /**
     * Loads initial product list.
     *
     * @error: on unexpected situation.
     */
    suspend fun loadProducts()

    /**
     * Updates favorite status of the product with the specified ID.
     *
     * @param productId The product ID.
     * @param isFavorite The new favorite status.
     * @error: If the product does not exists in the database or if database update failed.
     */
    suspend fun updateFavoriteStatus(productId: Long, isFavorite: Boolean)
}