package com.productlist.product_data

import com.productlist.module_injector.ComponentHolder
import com.productlist.module_injector.ComponentHolderDelegate
import com.productlist.product_data.impl.di.ProductDataComponent

object ProductDataComponentHolder : ComponentHolder<ProductDataFeatureApi, ProductDataFeatureDependencies> {
    private val componentHolderDelegate = ComponentHolderDelegate<
            ProductDataFeatureApi,
            ProductDataFeatureDependencies,
            ProductDataComponent> { dependencies: ProductDataFeatureDependencies ->
        ProductDataComponent.initAndGet(dependencies)
    }

    override var dependencyProvider: (() -> ProductDataFeatureDependencies)? by componentHolderDelegate::dependencyProvider

    override fun get(): ProductDataFeatureApi = componentHolderDelegate.get()
}

