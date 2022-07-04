package com.productlist.product_ui.impl.mvi.product_list.store

import com.arkivanov.mvikotlin.extensions.coroutines.SuspendExecutor
import com.productlist.common_utils.coroutine_utils.DispatcherProvider
import com.productlist.product_domain.domain.ProductInteractor
import com.productlist.product_domain.model.Product
import com.productlist.product_ui.impl.mvi.product_list.view.recycler.ProductListItem
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.withContext

internal class ProductListIntentExecutorImpl @AssistedInject constructor(
    private val productInteractor: ProductInteractor,
    private val dispatcherProvider: DispatcherProvider,
) : SuspendExecutor<ProductListStore.Intent, ProductListBootstrapper.Action, ProductListStore.State, ProductListStateChanges, Nothing>(
    mainContext = dispatcherProvider.main(),
), ProductListIntentExecutor {

    override suspend fun executeAction(action: ProductListBootstrapper.Action, getState: () -> ProductListStore.State) {
        when (action) {
            is ProductListBootstrapper.Action.LoadList -> handleActionLoadList()
        }
    }

    override suspend fun executeIntent(intent: ProductListStore.Intent, getState: () -> ProductListStore.State) {
        when (intent) {
            is ProductListStore.Intent.OnIsFavoriteChanged -> handleIsFavoriteChanged(intent.productId, intent.isFavorite)
        }
    }

    private suspend fun handleIsFavoriteChanged(productId: Long, isFavorite: Boolean) {
        try {
            withContext(dispatcherProvider.io()) {
                productInteractor.updateFavoriteStatus(productId, isFavorite)
            }
        } catch (th: Throwable) {
            dispatchChanges(ProductListStateChanges.ErrorChanged(th))
        }
    }

    private suspend fun handleActionLoadList() = coroutineScope {
        dispatchChanges(ProductListStateChanges.ErrorChanged(null))

        withContext(dispatcherProvider.io()) {
            flow { emitAll(productInteractor.observeProducts()) }
                .onStart { showProgress(true) }
                .onEach { showProgress(false) }
                .catch { th ->
                    dispatchChanges(ProductListStateChanges.ErrorChanged(th))
                }
                .collectLatest { list: List<Product> ->
                    dispatchChanges(ProductListStateChanges.ListChanged(list.map { ProductListItem(it) }))
                }
        }
    }

    private suspend fun showProgress(show: Boolean) {
        dispatchChanges(ProductListStateChanges.ProgressStateChanged(isInProgress = show))
    }

    private suspend fun dispatchChanges(changes: ProductListStateChanges) {
        withContext(dispatcherProvider.main()) {
            dispatch(changes)
        }
    }

    @AssistedFactory
    interface Factory : ProductListIntentExecutorFactory {
        override fun create(): ProductListIntentExecutorImpl
    }
}