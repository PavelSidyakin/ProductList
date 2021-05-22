package com.productlist.product_ui.impl.mvi.product_details.store

import com.arkivanov.mvikotlin.core.store.Executor

internal interface ProductDetailsIntentExecutor
    : Executor<
        ProductDetailsStore.Intent,
        ProductDetailsBootstrapper.Action,
        ProductDetailsStore.State,
        ProductDetailsStateChanges,
        Nothing,
        >