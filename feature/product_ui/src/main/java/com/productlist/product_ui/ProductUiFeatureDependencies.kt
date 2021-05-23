package com.productlist.product_ui

import com.productlist.common_utils.coroutine_utils.DispatcherProvider
import com.productlist.module_injector.BaseFeatureDependencies
import com.productlist.product_domain.domain.ProductInteractor

interface ProductUiFeatureDependencies : BaseFeatureDependencies {
    /**
     * [ProductInteractor] instance.
     */
    val productInteractor: ProductInteractor

    /**
     * [DispatcherProvider] instance.
     */
    val dispatcherProvider: DispatcherProvider
}