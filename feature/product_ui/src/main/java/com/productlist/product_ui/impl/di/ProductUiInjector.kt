package com.productlist.product_ui.impl.di

import com.productlist.product_ui.impl.mvi.product_details.controller.ProductDetailsControllerFactory
import com.productlist.product_ui.impl.mvi.product_list.controller.ProductListControllerFactory


internal interface ProductUiInjector {
    val productListControllerFactory: ProductListControllerFactory
    val productDetailsControllerFactory: ProductDetailsControllerFactory
}