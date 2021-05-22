package com.productlist.product_ui

import com.productlist.module_injector.ComponentHolder
import com.productlist.module_injector.ComponentHolderDelegate
import com.productlist.product_ui.impl.di.ProductUiComponent
import com.productlist.product_ui.impl.di.ProductUiInjector

object ProductUiComponentHolder : ComponentHolder<ProductUiFeatureApi, ProductUiFeatureDependencies> {
    private val componentHolderDelegate = ComponentHolderDelegate<
            ProductUiFeatureApi,
            ProductUiFeatureDependencies,
            ProductUiComponent> { dependencies: ProductUiFeatureDependencies ->
        ProductUiComponent.initAndGet(dependencies)
    }

    internal fun getInjector(): ProductUiInjector = componentHolderDelegate.getComponentImpl()

    override var dependencyProvider: (() -> ProductUiFeatureDependencies)? by componentHolderDelegate::dependencyProvider

    override fun get(): ProductUiFeatureApi = componentHolderDelegate.get()
}

