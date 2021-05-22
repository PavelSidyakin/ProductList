package com.productlist.product_ui.impl.di

import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.arkivanov.mvikotlin.main.store.DefaultStoreFactory
import com.productlist.product_ui.impl.mvi.product_list.store.ProductListIntentExecutor
import com.productlist.product_ui.impl.mvi.product_list.store.ProductListIntentExecutorImpl
import com.productlist.product_ui.impl.mvi.product_list.store.ProductListStoreFactory
import com.productlist.product_ui.impl.mvi.product_list.store.ProductListStoreFactoryImpl
import dagger.Binds
import dagger.Module
import dagger.Provides

@Module
internal interface ProductUiModule {

    @Binds
    fun provideProductListIntentExecutor(impl: ProductListIntentExecutorImpl): ProductListIntentExecutor

    @Binds
    fun provideProductListStoreFactory(impl: ProductListStoreFactoryImpl): ProductListStoreFactory

    companion object {

        @Provides
        fun provideStoreFactory(): StoreFactory {
            return DefaultStoreFactory
        }
    }
}