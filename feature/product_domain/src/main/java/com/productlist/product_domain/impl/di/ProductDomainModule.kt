package com.productlist.product_domain.impl.di

import com.productlist.product_domain.domain.ProductInteractor
import com.productlist.product_domain.domain.ProductInteractorImpl
import dagger.Binds
import dagger.Module

@Module
internal interface ProductDomainModule {

    @Binds
    fun provideProductInteractor(impl: ProductInteractorImpl): ProductInteractor

}