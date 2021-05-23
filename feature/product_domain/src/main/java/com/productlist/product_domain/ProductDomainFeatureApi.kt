package com.productlist.product_domain

import com.productlist.module_injector.BaseFeatureAPI
import com.productlist.product_domain.domain.ProductInteractor

interface ProductDomainFeatureApi : BaseFeatureAPI {
    /**
     * [ProductInteractor] instance.
     */
    val productInteractor: ProductInteractor
}