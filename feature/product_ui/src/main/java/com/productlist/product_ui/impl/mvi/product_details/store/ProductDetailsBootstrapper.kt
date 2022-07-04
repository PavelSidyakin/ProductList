package com.productlist.product_ui.impl.mvi.product_details.store

/**
 * MVIKotlin Bootstrapper class.
 */
internal class ProductDetailsBootstrapper {

    sealed class Action {
        /**
         * The action to load product details.
         */
        object ShowDetails : Action()
    }

}