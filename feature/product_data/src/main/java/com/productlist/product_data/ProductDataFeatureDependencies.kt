package com.productlist.product_data

import android.content.Context
import androidx.room.RoomDatabase
import com.productlist.app_database.impl.room.ProductDao
import com.productlist.common_utils.coroutine_utils.DispatcherProvider
import com.productlist.module_injector.BaseFeatureDependencies

interface ProductDataFeatureDependencies : BaseFeatureDependencies {
    /**
     * Dispatchers provider.
     */
    val dispatcherProvider: DispatcherProvider

    /**
     * The application database.
     */
    val database: RoomDatabase

    /**
     * The database access object for products.
     */
    val productDao: ProductDao

    /**
     * The application context.
     */
    val context: Context
}