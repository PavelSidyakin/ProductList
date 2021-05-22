package com.productlist.product_ui.impl.mvi.product_details.store

import com.arkivanov.mvikotlin.core.store.Executor
import com.arkivanov.mvikotlin.core.store.SimpleBootstrapper
import com.arkivanov.mvikotlin.core.store.Store
import com.arkivanov.mvikotlin.core.store.StoreFactory
import javax.inject.Inject

internal class ProductDetailsStoreFactoryImpl @Inject constructor(
    private val storeFactory: StoreFactory,
    private val productListIntentExecutor: ProductDetailsIntentExecutor,
) : ProductDetailsStoreFactory {

    override fun create(productId: Long): ProductDetailsStore {
        return object : ProductDetailsStore,
            Store<ProductDetailsStore.Intent, ProductDetailsStore.State, Nothing>
            by storeFactory.create(
                name = "ProductDetailsStore",
                initialState = ProductDetailsStore.State(),
                bootstrapper = SimpleBootstrapper(ProductDetailsBootstrapper.Action.ShowDetails(productId)),
                executorFactory = ::getExecutor,
                reducer = ProductDetailsReducer(),
            ) {}
    }

    private fun getExecutor(): Executor<ProductDetailsStore.Intent, ProductDetailsBootstrapper.Action, ProductDetailsStore.State, ProductDetailsStateChanges, Nothing> =
        productListIntentExecutor
}