package com.productlist.product_ui.impl.mvi.product_list.store

import com.arkivanov.mvikotlin.extensions.coroutines.SuspendExecutor
import com.productlist.common_utils.coroutine_utils.DispatcherProvider
import com.productlist.product_domain.domain.ProductInteractor
import com.productlist.product_domain.model.Product
import com.productlist.product_ui.impl.mvi.product_list.view.recycler.ProductListItem
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.collectLatest
import javax.inject.Inject

internal class ProductListIntentExecutorImpl @Inject constructor(
    private val proListInteractor: ProductInteractor,
    private val dispatcherProvider: DispatcherProvider,
) : SuspendExecutor<ProductListStore.Intent, ProductListBootstrapper.Action, ProductListStore.State, ProductListStateChanges, ProductListStore.Label>(
    mainContext = dispatcherProvider.main(),
), ProductListIntentExecutor {

    override suspend fun executeAction(
        action: ProductListBootstrapper.Action,
        getState: () -> ProductListStore.State,
    ) {
        when (action) {
            is ProductListBootstrapper.Action.LoadList -> handleActionLoadList()
        }
    }

    override suspend fun executeIntent(
        intent: ProductListStore.Intent,
        getState: () -> ProductListStore.State,
    ) {

    }

    private suspend fun handleActionLoadList() = coroutineScope {
        proListInteractor.observeProducts()
            .collectLatest { list: List<Product> ->
                dispatch(ProductListStateChanges.ListChanged(list.map { ProductListItem(it) }))
            }
    }
}