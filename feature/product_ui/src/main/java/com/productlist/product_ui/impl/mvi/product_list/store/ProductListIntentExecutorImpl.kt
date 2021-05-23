package com.productlist.product_ui.impl.mvi.product_list.store

import com.arkivanov.mvikotlin.extensions.coroutines.SuspendExecutor
import com.productlist.common_utils.coroutine_utils.DispatcherProvider
import com.productlist.product_domain.domain.ProductInteractor
import com.productlist.product_domain.model.Product
import com.productlist.product_ui.impl.mvi.product_list.view.recycler.ProductListItem
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.withContext
import javax.inject.Inject

internal class ProductListIntentExecutorImpl @Inject constructor(
    private val productInteractor: ProductInteractor,
    private val dispatcherProvider: DispatcherProvider,
) : SuspendExecutor<ProductListStore.Intent, ProductListBootstrapper.Action, ProductListStore.State, ProductListStateChanges, Nothing>(
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
        when (intent) {
            is ProductListStore.Intent.OnIsFavoriteChanged -> handleIsFavoriteChanged(intent.productId, intent.isFavorite)
        }
    }

    private suspend fun handleIsFavoriteChanged(productId: Long, isFavorite: Boolean) {
        productInteractor.updateFavoriteStatus(productId, isFavorite)
    }

    private suspend fun handleActionLoadList() = coroutineScope {

        withContext(dispatcherProvider.io()) {
            productInteractor.observeProducts()
                .onStart { showProgress(true) }
                .onEach { showProgress(false) }
                .collectLatest { list: List<Product> ->
                    withContext(dispatcherProvider.main()) {
                        dispatch(ProductListStateChanges.ListChanged(list.map { ProductListItem(it) }))
                    }
                }
        }
    }

    private suspend fun showProgress(show: Boolean) {
        withContext(dispatcherProvider.main()) {
            dispatch(ProductListStateChanges.ProgressStateChanged(isInProgress = show))
        }
    }
}