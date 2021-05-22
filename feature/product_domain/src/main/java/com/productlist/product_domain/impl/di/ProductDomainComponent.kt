package com.productlist.product_domain.impl.di

import com.productlist.product_domain.ProductDomainFeatureApi
import com.productlist.product_domain.ProductDomainFeatureDependencies
import dagger.Component
import javax.inject.Singleton

@Component(dependencies = [ProductDomainFeatureDependencies::class], modules = [ProductDomainModule::class])
@Singleton
internal interface ProductDomainComponent : ProductDomainFeatureApi {

    companion object {
        fun initAndGet(productDomainDependencies: ProductDomainFeatureDependencies): ProductDomainComponent {
            return DaggerProductDomainComponent.builder()
                .productDomainFeatureDependencies(productDomainDependencies)
                .build()
        }
    }
}
