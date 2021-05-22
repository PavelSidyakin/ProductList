package com.productlist.app_database

import androidx.room.RoomDatabase
import com.productlist.app_database.impl.room.ProductDao
import com.productlist.module_injector.BaseFeatureAPI

interface AppDatabaseFeatureApi : BaseFeatureAPI {
    val roomDb: RoomDatabase
    val productDao: ProductDao
}