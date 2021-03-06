package com.productlist.common_utils.di

import com.productlist.common_utils.coroutine_utils.DispatcherProvider
import com.productlist.common_utils.coroutine_utils.DispatcherProviderImpl
import dagger.Binds
import dagger.Module

@Module
internal interface CommonUtilsModule {
    @Binds
    fun provideDispatcherProvider(dispatcherProvider: DispatcherProviderImpl): DispatcherProvider
}