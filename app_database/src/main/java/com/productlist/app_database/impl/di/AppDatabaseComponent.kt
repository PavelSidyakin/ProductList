package com.productlist.app_database.impl.di

import com.productlist.app_database.AppDatabaseFeatureApi
import com.productlist.app_database.AppDatabaseFeatureDependencies
import dagger.Component
import javax.inject.Singleton

@Component(dependencies = [AppDatabaseFeatureDependencies::class], modules = [AppDatabaseModule::class])
@Singleton
internal interface AppDatabaseComponent : AppDatabaseFeatureApi {

    companion object {
        fun initAndGet(appDatabaseDependencies: AppDatabaseFeatureDependencies): AppDatabaseComponent {
            return DaggerAppDatabaseComponent.builder()
                .appDatabaseFeatureDependencies(appDatabaseDependencies)
                .build()
        }
    }
}
