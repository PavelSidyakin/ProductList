package com.productlist.product_data.impl

import androidx.room.RoomDatabase
import androidx.room.withTransaction
import com.productlist.app_database.impl.room.ProductDao
import com.productlist.app_database.impl.room.ProductTable
import com.productlist.product_domain.data.ProductDbRepository
import com.productlist.product_domain.model.Product
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

internal class ProductDbRepositoryImpl @Inject constructor(
    private val db: RoomDatabase,
    private val productDao: ProductDao,
): ProductDbRepository {

    override suspend fun withTransaction(block: suspend () -> Unit) {
        db.withTransaction {
            block()
        }
    }

    override fun observeProducts(): Flow<List<Product>> {
        return productDao.observeProducts().map { it.map { it.toProduct() } }
    }

    override fun observeProduct(productId: Long): Flow<Product> {
        return productDao.observeProduct(productId).map { it.toProduct() }
    }

    override suspend fun insertProducts(products: List<Product>) {
        productDao.updateOrInsertProducts(products.map { it.toProductTable() })
    }

    override suspend fun updateFavoriteStatus(productId: Long, isFavorite: Boolean) {
        productDao.updateIsFavorite(productId, isFavorite)
    }

    override suspend fun clearProducts() {
        productDao.deleteAllProducts()
    }

    private fun ProductTable.toProduct(): Product {
        return Product(id, title, author, imageUrl, isFavorite)
    }

    private fun Product.toProductTable(): ProductTable {
        return ProductTable(
            title = title,
            author = author,
            imageUrl = imageUrl,
            isFavorite = isFavorite
        )
    }
}