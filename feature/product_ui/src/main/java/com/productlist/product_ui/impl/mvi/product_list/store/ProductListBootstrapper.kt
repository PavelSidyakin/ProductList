package com.productlist.product_ui.impl.mvi.product_list.store

internal class ProductListBootstrapper {

    sealed class Action {
        class LoadList: Action()
    }

}