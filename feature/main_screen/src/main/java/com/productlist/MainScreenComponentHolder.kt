package com.productlist

import com.productlist.impl.di.MainScreenComponent
import com.productlist.impl.di.MainScreenInjector
import com.productlist.module_injector.ComponentHolder
import com.productlist.module_injector.ComponentHolderDelegate


object MainScreenComponentHolder :
    ComponentHolder<MainScreenFeatureApi, MainScreenFeatureDependencies> {
    private val componentHolderDelegate = ComponentHolderDelegate<
            MainScreenFeatureApi,
            MainScreenFeatureDependencies,
            MainScreenComponent> { dependencies: MainScreenFeatureDependencies ->
        MainScreenComponent.initAndGet(dependencies)
    }

    internal fun getInjector(): MainScreenInjector = componentHolderDelegate.getComponentImpl()

    override var dependencyProvider: (() -> MainScreenFeatureDependencies)? by componentHolderDelegate::dependencyProvider

    override fun get(): MainScreenFeatureApi {
        return componentHolderDelegate.getComponentImpl()
    }
}

