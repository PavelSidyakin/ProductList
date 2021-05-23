package com.productlist.product_ui.impl.mvi.product_list.controller

import com.arkivanov.mvikotlin.keepers.instancekeeper.InstanceKeeper
import com.productlist.product_ui.impl.mvi.product_details.controller.ProductDetailsController

/**
 * MVIKotlin controller factory.
 *ProductListController
 */
internal interface ProductListControllerFactory {
    /**
     * Creates [ProductListController].
     *
     * @instanceKeeper instance keeper instance.
     * @param dependencies Dependencies instance.
     * @return [ProductDetailsController]
     */
    fun create(
        instanceKeeper: InstanceKeeper,
        dependencies: ProductListController.Dependencies,
    ): ProductListController
}