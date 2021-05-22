package com.productlist.product_ui.impl.mvi.product_details.controller

import com.arkivanov.mvikotlin.keepers.instancekeeper.InstanceKeeper

internal interface ProductDetailsControllerFactory {
    fun create(
        productId: Long,
        instanceKeeper: InstanceKeeper,
        dependencies: ProductDetailsController.Dependencies,
    ): ProductDetailsController
}