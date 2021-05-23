package com.productlist.product_ui.impl.mvi.product_details.store

import com.arkivanov.mvikotlin.core.store.Executor

/**
 * MVIKotlin intent executor interface.
 */
internal interface ProductDetailsIntentExecutor
    : Executor<
        ProductDetailsStore.Intent,
        ProductDetailsBootstrapper.Action,
        ProductDetailsStore.State,
        ProductDetailsStateChanges,
        Nothing,
        >