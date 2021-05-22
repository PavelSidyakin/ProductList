package com.productlist.product_ui.impl.mvi.product_details.store

internal class ProductDetailsBootstrapper {

    sealed class Action {
        data class ShowDetails(val productId: Long): Action()
    }

}