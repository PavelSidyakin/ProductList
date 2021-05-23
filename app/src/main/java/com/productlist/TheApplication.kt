package com.productlist

import android.app.Application
import com.productlist.product_domain.ProductDomainComponentHolder
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

/**
 * The main application class.
 */
internal class TheApplication : Application(), CoroutineScope {

    override val coroutineContext: CoroutineContext = Job() + Dispatchers.IO

    private val productDomainFeatureApi by lazy {
        ProductDomainComponentHolder.get()
    }

    override fun onCreate() {
        super.onCreate()

        ModulesGlue.glueModules(this)

        // Load product on the applications start.
        // Depends on the business requirements this can be in another place.
        launch {
            productDomainFeatureApi.productInteractor.loadProducts()
        }
    }

}