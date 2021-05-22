package com.productlist.product_ui.impl.mvi.product_list.store

import com.arkivanov.mvikotlin.core.store.Executor
import com.arkivanov.mvikotlin.core.store.SimpleBootstrapper
import com.arkivanov.mvikotlin.core.store.Store
import com.arkivanov.mvikotlin.core.store.StoreFactory
import javax.inject.Inject

internal class ProductListStoreFactoryImpl @Inject constructor(
    private val storeFactory: StoreFactory,
    private val productListIntentExecutor: ProductListIntentExecutor,
) : ProductListStoreFactory {
    override fun create(): ProductListStore {
        return object : ProductListStore,
            Store<ProductListStore.Intent, ProductListStore.State, ProductListStore.Label>
            by storeFactory.create(
                name = "ProductListStore",
                initialState = ProductListStore.State(),
                bootstrapper = SimpleBootstrapper(ProductListBootstrapper.Action.LoadList()),
                executorFactory = ::getExecutor,
                reducer = ProductListReducer(),
            ) {}
    }

    private fun getExecutor(): Executor<ProductListStore.Intent, ProductListBootstrapper.Action, ProductListStore.State, ProductListStateChanges, ProductListStore.Label> =
        productListIntentExecutor
}