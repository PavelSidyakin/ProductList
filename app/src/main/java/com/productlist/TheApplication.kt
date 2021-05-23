package com.productlist

import android.app.Application
import com.productlist.product_domain.ProductDomainComponentHolder
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

internal class TheApplication : Application(), CoroutineScope {

    override val coroutineContext: CoroutineContext = Job() + Dispatchers.IO

    private val productDomainFeatureApi by lazy {
        ProductDomainComponentHolder.get()
    }

    override fun onCreate() {
        super.onCreate()

        ModulesGlue.glueModules(this)

        launch {
            productDomainFeatureApi.productInteractor.loadProducts()
        }
    }

}