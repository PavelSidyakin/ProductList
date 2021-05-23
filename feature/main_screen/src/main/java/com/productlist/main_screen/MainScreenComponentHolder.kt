package com.productlist.main_screen

import com.productlist.main_screen.impl.di.MainScreenComponent
import com.productlist.main_screen.impl.di.MainScreenInjector
import com.productlist.module_injector.ComponentHolder
import com.productlist.module_injector.ComponentHolderDelegate

// Useless now. But can be used in the future
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

