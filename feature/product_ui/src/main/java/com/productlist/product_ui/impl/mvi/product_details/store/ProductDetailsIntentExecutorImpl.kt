package com.productlist.product_ui.impl.mvi.product_details.store

import com.arkivanov.mvikotlin.extensions.coroutines.SuspendExecutor
import com.productlist.common_utils.coroutine_utils.DispatcherProvider
import com.productlist.product_domain.domain.ProductInteractor
import com.productlist.product_domain.model.Product
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.collectLatest
import javax.inject.Inject

internal class ProductDetailsIntentExecutorImpl @Inject constructor(
    private val productInteractor: ProductInteractor,
    private val dispatcherProvider: DispatcherProvider,
) : SuspendExecutor<ProductDetailsStore.Intent, ProductDetailsBootstrapper.Action, ProductDetailsStore.State, ProductDetailsStateChanges, Nothing>(
    mainContext = dispatcherProvider.main(),
), ProductDetailsIntentExecutor {

    override suspend fun executeAction(
        action: ProductDetailsBootstrapper.Action,
        getState: () -> ProductDetailsStore.State,
    ) {
        when (action) {
            is ProductDetailsBootstrapper.Action.ShowDetails -> handleShowDetails(action.productId)
        }
    }

    override suspend fun executeIntent(
        intent: ProductDetailsStore.Intent,
        getState: () -> ProductDetailsStore.State,
    ) {
        when (intent) {
            is ProductDetailsStore.Intent.OnIsFavoriteChanged -> handleIsFavoriteChanged(intent.productId, intent.isFavorite)
        }
    }

    private suspend fun handleIsFavoriteChanged(productId: Long, isFavorite: Boolean) {
        productInteractor.updateFavoriteStatus(productId, isFavorite)
    }

    private suspend fun handleShowDetails(productId: Long) = coroutineScope {
        productInteractor.observeProduct(productId)
            .collectLatest { product: Product ->
                dispatch(ProductDetailsStateChanges.ProductChanged(product))
            }
    }
}