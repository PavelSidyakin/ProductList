package com.productlist.product_data

import com.productlist.module_injector.BaseFeatureAPI
import com.productlist.product_domain.data.ProductDbRepository
import com.productlist.product_domain.data.ProductSourceRepository

interface ProductDataFeatureApi : BaseFeatureAPI {
    /**
     * Product source repository.
     * The returned implementation loads products from resources.
     */
    val productSourceRepository: ProductSourceRepository

    /**
     * Product database repository.
     * Uses the application database.
     */
    val productDbRepository: ProductDbRepository
}