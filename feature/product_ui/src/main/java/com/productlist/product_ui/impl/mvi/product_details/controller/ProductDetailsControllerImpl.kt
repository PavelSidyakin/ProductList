package com.productlist.product_ui.impl.mvi.product_details.controller

import com.arkivanov.mvikotlin.core.binder.BinderLifecycleMode
import com.arkivanov.mvikotlin.core.instancekeeper.getStore
import com.arkivanov.mvikotlin.core.lifecycle.Lifecycle
import com.arkivanov.mvikotlin.extensions.coroutines.bind
import com.arkivanov.mvikotlin.extensions.coroutines.events
import com.arkivanov.mvikotlin.extensions.coroutines.states
import com.arkivanov.mvikotlin.keepers.instancekeeper.InstanceKeeper
import com.productlist.common_utils.coroutine_utils.DispatcherProvider
import com.productlist.product_ui.impl.mvi.product_details.store.ProductDetailsStoreFactory
import com.productlist.product_ui.impl.mvi.product_details.view.ProductDetailsView
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject

internal class ProductDetailsControllerImpl @AssistedInject constructor(
    productDetailsStoreFactory: ProductDetailsStoreFactory,
    private val dispatcherProvider: DispatcherProvider,
    @Assisted private val productId: Long,
    @Assisted private val instanceKeeper: InstanceKeeper,
    @Assisted private val dependencies: ProductDetailsController.Dependencies,
) : ProductDetailsController {

    private val store = instanceKeeper.getStore {
        productDetailsStoreFactory.create(productId)
    }

    override fun onViewCreated(productDetailsView: ProductDetailsView, viewLifecycle: Lifecycle) {
        bind(viewLifecycle, BinderLifecycleMode.CREATE_DESTROY, dispatcherProvider.main()) {
            productDetailsView.events bindTo store
        }

        bind(viewLifecycle, BinderLifecycleMode.START_STOP, dispatcherProvider.main()) {
            store.states bindTo productDetailsView
        }
    }

    @AssistedFactory
    interface Factory : ProductDetailsControllerFactory {
        override fun create(
            productId: Long,
            instanceKeeper: InstanceKeeper,
            dependencies: ProductDetailsController.Dependencies
        ): ProductDetailsControllerImpl
    }
}