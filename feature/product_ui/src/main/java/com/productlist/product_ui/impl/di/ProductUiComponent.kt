package com.productlist.product_ui.impl.di

import com.productlist.product_ui.ProductUiFeatureApi
import com.productlist.product_ui.ProductUiFeatureDependencies
import com.productlist.product_ui.impl.mvi.product_details.controller.ProductDetailsControllerImpl
import com.productlist.product_ui.impl.mvi.product_details.store.ProductDetailsIntentExecutorFactory
import com.productlist.product_ui.impl.mvi.product_details.store.ProductDetailsIntentExecutorImpl
import com.productlist.product_ui.impl.mvi.product_list.controller.ProductListControllerImpl
import com.productlist.product_ui.impl.mvi.product_list.store.ProductListIntentExecutorFactory
import com.productlist.product_ui.impl.mvi.product_list.store.ProductListIntentExecutorImpl
import dagger.Component
import javax.inject.Singleton

@Component(dependencies = [ProductUiFeatureDependencies::class], modules = [ProductUiModule::class])
@Singleton
internal interface ProductUiComponent : ProductUiFeatureApi, ProductUiInjector {

    override val productListControllerFactory: ProductListControllerImpl.Factory
    override val productDetailsControllerFactory: ProductDetailsControllerImpl.Factory
    override val productDetailsIntentExecutorFactory: ProductDetailsIntentExecutorImpl.Factory
    override val productListIntentExecutorFactory: ProductListIntentExecutorImpl.Factory

    companion object {
        fun initAndGet(productUiDependencies: ProductUiFeatureDependencies): ProductUiComponent {
            return DaggerProductUiComponent.builder()
                .productUiFeatureDependencies(productUiDependencies)
                .build()
        }
    }
}
