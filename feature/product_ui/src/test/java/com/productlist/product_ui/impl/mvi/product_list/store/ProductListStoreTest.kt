package com.productlist.product_ui.impl.mvi.product_list.store

import com.arkivanov.mvikotlin.core.utils.isAssertOnMainThreadEnabled
import com.arkivanov.mvikotlin.main.store.DefaultStoreFactory
import com.productlist.common_utils.coroutine_utils.DispatcherProvider
import com.productlist.product_domain.model.Product
import io.mockk.coVerify
import io.mockk.spyk
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.TestCoroutineDispatcher
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test

internal class ProductListStoreTest {

    private val productInteractor: ProductInteractorStub = spyk(ProductInteractorStub(products))

    private lateinit var store: ProductListStore

    @BeforeEach
    fun beforeEachTest() {
        isAssertOnMainThreadEnabled = false
        createStore()
    }

    @Test
    @DisplayName("When Intent.OnIsFavoriteChanged, should set product.isFavorite state")
    fun test0() {
        // action
        store.accept(ProductListStore.Intent.OnIsFavoriteChanged(1, true))

        // verify
        Assertions.assertEquals(true, store.state.productList?.find { it.product.id == 1L }?.product?.isFavorite)
    }

    @Test
    @DisplayName("When Intent.OnIsFavoriteChanged, should update database isFavorite state")
    fun test1() {
        // action
        store.accept(ProductListStore.Intent.OnIsFavoriteChanged(1, true))

        // verify
        coVerify(exactly = 1) { productInteractor.updateFavoriteStatus(1, true) }
    }

    @Test
    @DisplayName("When initialized, should show progress")
    fun test2() {
        // verify
        Assertions.assertEquals(true, store.state.isInProgress)
    }

    @Test
    @DisplayName("When data is loaded, should hide progress")
    fun test3() {
        runBlocking {
            // action
            productInteractor.loadProducts()

            // verify
            Assertions.assertEquals(false, store.state.isInProgress)
        }
    }

    private fun createStore() {
        store =
            ProductListStoreFactoryImpl(
                storeFactory = DefaultStoreFactory,
                productListIntentExecutor = ProductListIntentExecutorImpl(
                    productInteractor = productInteractor,
                    dispatcherProvider = object : DispatcherProvider {
                        override fun io(): CoroutineDispatcher = TestCoroutineDispatcher()
                        override fun main(): CoroutineDispatcher = TestCoroutineDispatcher()
                    },
                )
            ).create()
    }

    companion object {
        private val products = listOf(
            Product(1, "title1", "author1", null, isFavorite = false),
            Product(2, "title2", null, null, isFavorite = false),
        )
    }
}