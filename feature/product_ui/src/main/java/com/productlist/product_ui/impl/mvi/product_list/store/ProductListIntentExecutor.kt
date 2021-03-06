package com.productlist.product_ui.impl.mvi.product_list.store

import com.arkivanov.mvikotlin.core.store.Executor

/**
 * MVIKotlin intent executor interface.
 */
internal interface ProductListIntentExecutor
    : Executor<
        ProductListStore.Intent,
        ProductListBootstrapper.Action,
        ProductListStore.State,
        ProductListStateChanges,
        Nothing,
        >