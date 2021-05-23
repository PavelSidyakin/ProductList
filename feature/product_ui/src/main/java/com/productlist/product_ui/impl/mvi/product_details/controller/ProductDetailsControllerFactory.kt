package com.productlist.product_ui.impl.mvi.product_details.controller

import com.arkivanov.mvikotlin.keepers.instancekeeper.InstanceKeeper

/**
 * MVIKotlin controller factory.
 *
 */
internal interface ProductDetailsControllerFactory {
    /**
     * Creates [ProductDetailsController].
     *
     * @param productId The product ID
     * @instanceKeeper instance keeper instance.
     * @param dependencies Dependencies instance.
     * @return [ProductDetailsController]
     */
    fun create(
        productId: Long,
        instanceKeeper: InstanceKeeper,
        dependencies: ProductDetailsController.Dependencies,
    ): ProductDetailsController
}