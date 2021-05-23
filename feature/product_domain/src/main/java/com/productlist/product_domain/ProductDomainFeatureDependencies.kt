package com.productlist.product_domain

import com.productlist.module_injector.BaseFeatureDependencies
import com.productlist.product_domain.data.ProductDbRepository
import com.productlist.product_domain.data.ProductSourceRepository

interface ProductDomainFeatureDependencies : BaseFeatureDependencies {
    /**
     * [ProductSourceRepository] instance
     */
    val productSourceRepository: ProductSourceRepository

    /**
     * [ProductDbRepository] instance
     */
    val productDbRepository: ProductDbRepository
}