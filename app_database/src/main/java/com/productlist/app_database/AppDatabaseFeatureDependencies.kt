package com.productlist.app_database

import android.content.Context
import com.productlist.module_injector.BaseFeatureDependencies

interface AppDatabaseFeatureDependencies : BaseFeatureDependencies {
    val context: Context
}