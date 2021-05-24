package com.productlist.product_ui.impl.mvi.product_list.controller

import com.arkivanov.mvikotlin.core.binder.BinderLifecycleMode
import com.arkivanov.mvikotlin.core.instancekeeper.getStore
import com.arkivanov.mvikotlin.core.lifecycle.Lifecycle
import com.arkivanov.mvikotlin.extensions.coroutines.bind
import com.arkivanov.mvikotlin.extensions.coroutines.events
import com.arkivanov.mvikotlin.extensions.coroutines.states
import com.arkivanov.mvikotlin.keepers.instancekeeper.InstanceKeeper
import com.productlist.common_utils.coroutine_utils.DispatcherProvider
import com.productlist.product_ui.impl.mvi.product_list.store.ProductListStoreFactory
import com.productlist.product_ui.impl.mvi.product_list.view.ProductListView
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlinx.coroutines.flow.mapNotNull

internal class ProductListControllerImpl @AssistedInject constructor(
    productListStoreFactory: ProductListStoreFactory,
    private val dispatcherProvider: DispatcherProvider,
    @Assisted private val instanceKeeper: InstanceKeeper,
    @Assisted private val dependencies: ProductListController.Dependencies,
) : ProductListController {

    private val store = instanceKeeper.getStore {
        productListStoreFactory.create()
    }

    override fun onViewCreated(productListView: ProductListView, viewLifecycle: Lifecycle) {
        bind(viewLifecycle, BinderLifecycleMode.CREATE_DESTROY, dispatcherProvider.main()) {
            productListView.events bindTo store
        }

        bind(viewLifecycle, BinderLifecycleMode.START_STOP, dispatcherProvider.main()) {
            store.states bindTo productListView
            productListView.events.mapNotNull { productListIntentToOutput(it) } bindTo { dependencies.productSelectedCallback(it) }
        }
    }

    @AssistedFactory
    interface Factory : ProductListControllerFactory {
        override fun create(
            instanceKeeper: InstanceKeeper,
            dependencies: ProductListController.Dependencies,
        ): ProductListControllerImpl
    }
}