package com.productlist.product_domain.data

import com.productlist.product_domain.model.Product
import kotlinx.coroutines.flow.Flow

/**
 * A database repository for products.
 */
interface ProductDbRepository {

    /**
     * Observes product list updates in the database.
     * Emits the current list immediately on collect.
     *
     * @return [Flow] which emits list of [Product] on each update of the list in the database.
     * @error: If database select failed.
     */
    fun observeProducts(): Flow<List<Product>>

    /**
     * Observes product with the specified ID in the database.
     * Emits the current product immediately on collect.
     *
     * @return [Flow] which emits [Product] with the specified id on each update of the product in the database.
     * @param productId The product id to observe.
     * @error: If database select failed.
     */
    fun observeProduct(productId: Long): Flow<Product>

    /**
     * Inserts the provided list of [Product] to the database.
     * If a product with provided id already exists in the database, update the product data.
     *
     * @param products The product list.
     * @error: If database insert failed.
     */
    suspend fun insertProducts(products: List<Product>)

    /**
     * Updates in the database favorite status of the product with the specified ID.
     *
     * @param productId The product ID.
     * @param isFavorite The new favorite status.
     * @error: If the product does not exists in the database or if database update failed.
     */
    suspend fun updateFavoriteStatus(productId: Long, isFavorite: Boolean)

    /**
     * Clears all product in the database.
     *
     * @error: If database update failed.
     */
    suspend fun clearProducts()

    /**
     * Runs the specified block in transaction.
     *
     * @error: no
     */
    suspend fun withTransaction(block: suspend () -> Unit)
}