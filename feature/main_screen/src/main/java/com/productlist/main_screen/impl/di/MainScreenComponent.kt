package com.productlist.main_screen.impl.di

import com.productlist.main_screen.MainScreenFeatureApi
import com.productlist.main_screen.MainScreenFeatureDependencies
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