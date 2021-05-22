package com.productlist.product_domain

import com.productlist.product_domain.impl.di.ProductDomainComponent
import com.productlist.module_injector.ComponentHolder
import com.productlist.module_injector.ComponentHolderDelegate

object ProductDomainComponentHolder : ComponentHolder<ProductDomainFeatureApi, ProductDomainFeatureDependencies> {
    private val componentHolderDelegate = ComponentHolderDelegate<
            ProductDomainFeatureApi,
            ProductDomainFeatureDependencies,
            ProductDomainComponent> { dependencies: ProductDomainFeatureDependencies ->
        ProductDomainComponent.initAndGet(dependencies)
    }

    override var dependencyProvider: (() -> ProductDomainFeatureDependencies)? by componentHolderDelegate::dependencyProvider

    override fun get(): ProductDomainFeatureApi = componentHolderDelegate.get()
}

