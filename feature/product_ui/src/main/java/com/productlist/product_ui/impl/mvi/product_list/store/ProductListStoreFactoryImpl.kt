package com.productlist.product_ui.impl.mvi.product_list.store

import com.arkivanov.mvikotlin.core.store.SimpleBootstrapper
import com.arkivanov.mvikotlin.core.store.Store
import com.arkivanov.mvikotlin.core.store.StoreFactory
import javax.inject.Inject

internal class ProductListStoreFactoryImpl @Inject constructor(
    private val storeFactory: StoreFactory,
    private val productListIntentExecutorFactory: ProductListIntentExecutorFactory
) : ProductListStoreFactory {
    override fun create(): ProductListStore {
        return object : ProductListStore,
            Store<ProductListStore.Intent, ProductListStore.State, Nothing>
            by storeFactory.create(
                name = "ProductListStore",
                initialState = ProductListStore.State(),
                bootstrapper = SimpleBootstrapper(ProductListBootstrapper.Action.LoadList()),
                executorFactory = { productListIntentExecutorFactory.create() },
                reducer = ProductListReducer(),
            ) {}
    }
}
