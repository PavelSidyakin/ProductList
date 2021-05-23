package com.productlist.app_database

import androidx.room.RoomDatabase
import com.productlist.app_database.impl.room.ProductDao
import com.productlist.module_injector.BaseFeatureAPI

interface AppDatabaseFeatureApi : BaseFeatureAPI {
    /**
     * The application database instance.
     *
     * Can be used to perform transactions.
     */
    val roomDb: RoomDatabase

    /**
     * The database access object for products.
     */
    val productDao: ProductDao
}