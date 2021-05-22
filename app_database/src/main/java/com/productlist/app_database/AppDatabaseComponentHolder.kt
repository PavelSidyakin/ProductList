package com.productlist.app_database

import com.productlist.app_database.impl.di.AppDatabaseComponent
import com.productlist.module_injector.ComponentHolder
import com.productlist.module_injector.ComponentHolderDelegate

object AppDatabaseComponentHolder : ComponentHolder<AppDatabaseFeatureApi, AppDatabaseFeatureDependencies> {
    private val componentHolderDelegate = ComponentHolderDelegate<
            AppDatabaseFeatureApi,
            AppDatabaseFeatureDependencies,
            AppDatabaseComponent> { dependencies: AppDatabaseFeatureDependencies ->
        AppDatabaseComponent.initAndGet(dependencies)
    }

    override var dependencyProvider: (() -> AppDatabaseFeatureDependencies)? by componentHolderDelegate::dependencyProvider

    override fun get(): AppDatabaseFeatureApi = componentHolderDelegate.get()
}

