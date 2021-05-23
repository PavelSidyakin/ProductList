package com.productlist.product_ui.impl.mvi.product_list.store

/**
 * MVIKotlin Bootstrapper class.
 */
internal class ProductListBootstrapper {

    sealed class Action {
        /**
         * The action to load product list for the first time.
         */
        class LoadList : Action()
    }

}