package com.productlist.product_data.impl.di

import com.productlist.product_data.ProductDataFeatureApi
import com.productlist.product_data.ProductDataFeatureDependencies
import dagger.Component
import javax.inject.Singleton

@Component(dependencies = [ProductDataFeatureDependencies::class], modules = [ProductDataModule::class])
@Singleton
internal interface ProductDataComponent : ProductDataFeatureApi {

    companion object {
        fun initAndGet(productDataDependencies: ProductDataFeatureDependencies): ProductDataComponent {
            return DaggerProductDataComponent.builder()
                .productDataFeatureDependencies(productDataDependencies)
                .build()
        }
    }
}
