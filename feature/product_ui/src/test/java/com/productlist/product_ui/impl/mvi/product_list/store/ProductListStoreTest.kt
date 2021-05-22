package com.productlist.product_ui.impl.mvi.product_list.store

import com.arkivanov.mvikotlin.core.utils.isAssertOnMainThreadEnabled
import com.arkivanov.mvikotlin.main.store.DefaultStoreFactory
import com.arkivanov.mvikotlin.rx.observer
import com.productlist.common_utils.coroutine_utils.DispatcherProvider
import com.productlist.common_utils.coroutine_utils.DispatcherProviderStub
import com.productlist.product_domain.domain.ProductInteractor
import com.productlist.product_domain.model.Product
import io.mockk.coVerify
import io.mockk.spyk
import io.mockk.verify
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.TestCoroutineDispatcher
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test

internal class ProductListStoreTest {

    private val productInteractor: ProductInteractor = spyk(ProductInteractorStub(products))

    private lateinit var store: ProductListStore

    @BeforeEach
    fun beforeEachTest() {
        isAssertOnMainThreadEnabled = false
        createStore()
    }

    @Test
    @DisplayName("When Intent.OnIsFavoriteChanged, should set product.isFavorite state")
    fun test0() {
        store.accept(ProductListStore.Intent.OnIsFavoriteChanged(1, true))

        Assertions.assertEquals(true, store.state.productList?.find { it.product.id == 1L }?.product?.isFavorite)
    }

    @Test
    @DisplayName("When Intent.OnIsFavoriteChanged, should update database isFavorite state")
    fun test1() {
        store.accept(ProductListStore.Intent.OnIsFavoriteChanged(1, true))

        coVerify(exactly = 1) { productInteractor.updateFavoriteStatus(1, true) }
    }

    @Test
    @DisplayName("When initialized, should show and hide progress")
    fun test2() {
            val states = mutableListOf<ProductListStore.State>()
            store.states(observer { states.add(it) })

            Assertions.assertEquals(true, states[0].isInProress)
            //Assertions.assertEquals(false, states[1].isInProress)
    }

    private fun createStore() {
        store =
            ProductListStoreFactoryImpl(
                storeFactory = DefaultStoreFactory,
                productListIntentExecutor = ProductListIntentExecutorImpl(
                    productInteractor = productInteractor,
                    dispatcherProvider = object : DispatcherProvider {
                        override fun io(): CoroutineDispatcher {
                            return TestCoroutineDispatcher()
                        }

                        override fun main(): CoroutineDispatcher {
                            return TestCoroutineDispatcher()
                        }

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