package com.productlist.product_data

import com.productlist.product_domain.data.ProductDbRepository
import com.productlist.product_domain.data.ProductSourceRepository
import com.productlist.module_injector.BaseFeatureAPI

interface ProductDataFeatureApi : BaseFeatureAPI {
    val productSourceRepository: ProductSourceRepository
    val productDbRepository: ProductDbRepository
}