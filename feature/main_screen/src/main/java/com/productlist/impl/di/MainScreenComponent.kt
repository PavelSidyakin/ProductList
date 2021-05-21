package com.productlist.impl.di

import com.productlist.MainScreenFeatureApi
import com.productlist.MainScreenFeatureDependencies
import dagger.Component

@Component(
    dependencies = [MainScreenFeatureDependencies::class],
    modules = [
        MainScreenModule::class,
    ]
)
internal interface MainScreenComponent : MainScreenFeatureApi, MainScreenInjector {

    companion object {
        fun initAndGet(mainScreenFeatureDependencies: MainScreenFeatureDependencies): MainScreenComponent {
            return DaggerMainScreenComponent.builder()
                .mainScreenFeatureDependencies(mainScreenFeatureDependencies)
                .build()
        }
    }
}