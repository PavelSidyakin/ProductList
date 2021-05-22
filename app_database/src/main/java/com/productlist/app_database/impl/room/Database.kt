package com.productlist.app_database.impl.room

import androidx.room.ColumnInfo
import androidx.room.Dao
import androidx.room.Database
import androidx.room.Entity
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.PrimaryKey
import androidx.room.Query
import androidx.room.RoomDatabase
import kotlinx.coroutines.flow.Flow

@Entity(tableName = "t_product")
data class ProductTable(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "product_id")
    val id: Long = 0L,

    @ColumnInfo(name = "title")
    val title: String,

    @ColumnInfo(name = "author")
    val author: String?,

    @ColumnInfo(name = "image_url")
    val imageUrl: String?,

    @ColumnInfo(name = "is_favorite")
    val isFavorite: Boolean,
)

@Dao
interface ProductDao {
    @Query("SELECT * FROM t_product")
    fun observeProducts(): Flow<List<ProductTable>>

    @Query(
        """
        SELECT * 
        FROM t_product 
        WHERE product_id == :productId
        """
    )
    fun observeProduct(productId: Long): Flow<ProductTable>

    @Query("DELETE from t_product")
    suspend fun deleteAllProducts()

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun updateOrInsertProduct(productTable: ProductTable)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun updateOrInsertProducts(productTable: List<ProductTable>)

    @Query(
        """
        UPDATE t_product
        SET is_favorite = :isFavorite
        WHERE product_id == :productId
        """
    )
    suspend fun updateIsFavorite(productId: Long, isFavorite: Boolean)

}

@Database(
    version = 1,
    entities = [
        ProductTable::class
    ],
)
internal abstract class AppDatabase : RoomDatabase() {
    abstract fun productDao(): ProductDao
}
