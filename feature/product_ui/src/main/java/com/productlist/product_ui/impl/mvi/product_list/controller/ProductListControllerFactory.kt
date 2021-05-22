package com.productlist.product_ui.impl.mvi.product_list.controller

import com.arkivanov.mvikotlin.keepers.instancekeeper.InstanceKeeper

internal interface ProductListControllerFactory {
    fun create(
        instanceKeeper: InstanceKeeper,
        dependencies: ProductListController.Dependencies,
    ): ProductListController
}