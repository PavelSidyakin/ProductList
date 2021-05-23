package com.productlist.product_data.impl.di

import com.productlist.product_data.impl.ProductDbRepositoryImpl
import com.productlist.product_data.impl.ProductSourceRepositoryImpl
import com.productlist.product_domain.data.ProductDbRepository
import com.productlist.product_domain.data.ProductSourceRepository
import dagger.Binds
import dagger.Module

@Module
internal interface ProductDataModule {

    @Binds
    fun provideProductDbRepository(impl: ProductDbRepositoryImpl): ProductDbRepository

    @Binds
    fun provideProductSourceRepository(impl: ProductSourceRepositoryImpl): ProductSourceRepository
}