package com.productlist.product_domain

import com.productlist.product_domain.data.ProductDbRepository
import com.productlist.product_domain.data.ProductSourceRepository
import com.productlist.module_injector.BaseFeatureDependencies

interface ProductDomainFeatureDependencies : BaseFeatureDependencies {
    val productSourceRepository: ProductSourceRepository
    val productDbRepository: ProductDbRepository
}