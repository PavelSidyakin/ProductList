package com.productlist.product_data

import android.content.Context
import androidx.room.RoomDatabase
import com.productlist.app_database.impl.room.ProductDao
import com.productlist.common_utils.coroutine_utils.DispatcherProvider
import com.productlist.module_injector.BaseFeatureDependencies

interface ProductDataFeatureDependencies : BaseFeatureDependencies {
    val dispatcherProvider: DispatcherProvider
    val database: RoomDatabase
    val productDao: ProductDao
    val context: Context
}