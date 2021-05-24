package com.productlist.product_ui.impl.mvi.product_list.store

import com.arkivanov.mvikotlin.core.utils.isAssertOnMainThreadEnabled
import com.arkivanov.mvikotlin.main.store.DefaultStoreFactory
import com.productlist.common_utils.coroutine_utils.DispatcherProviderStub
import com.productlist.product_domain.model.Product
import io.mockk.Runs
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.just
import io.mockk.spyk
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.runBlocking
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
    }

    @Test
    @DisplayName("When Intent.OnIsFavoriteChanged, should set product.isFavorite state")
    fun test0() {
        // action
        createStore()
        store.accept(ProductListStore.Intent.OnIsFavoriteChanged(1, true))

        // verify
        Assertions.assertEquals(true, store.state.productList?.find { it.product.id == 1L }?.product?.isFavorite)
    }

    @Test
    @DisplayName("When Intent.OnIsFavoriteChanged, should update database isFavorite state")
    fun test1() {
        // action
        createStore()
        store.accept(ProductListStore.Intent.OnIsFavoriteChanged(1, true))

        // verify
        coVerify(exactly = 1) { productInteractor.updateFavoriteStatus(1, true) }
    }

    @Test
    @DisplayName("When initialized, should show progress")
    fun test2() {
        // action
        createStore()

        // verify
        Assertions.assertTrue(store.state.isInProgress)
    }

    @Test
    @DisplayName("When data is loaded, should hide progress")
    fun test3() {
        runBlocking {
            // action
            createStore()
            productInteractor.loadProducts()

            // verify
            Assertions.assertFalse(store.state.isInProgress)
        }
    }

    @Test
    @DisplayName("When error occurred in observeProducts() (emits error), should show error")
    fun test4() {
        //mock
        every { productInteractor.observeProducts() } returns flow { throw RuntimeException() }

        // action
        createStore()

        // verify
        Assertions.assertNotNull(store.state.error)
    }

    @Test
    @DisplayName("When error occurred in observeProducts() (throws itself), should show error")
    fun test6() {
        //mock
        every { productInteractor.observeProducts() } throws RuntimeException()

        // action
        createStore()

        // verify
        Assertions.assertNotNull(store.state.error)
    }

    @Test
    @DisplayName("When observeProducts() succeeded, shouldn't show error")
    fun test5() {
        //mock
        every { productInteractor.observeProducts() } returns flow { products }

        // action
        createStore()

        // verify
        Assertions.assertNull(store.state.error)
    }

    @Test
    @DisplayName("When error occurred in updateFavoriteStatus(), should show error")
    fun test8() {
        //mock
        coEvery { productInteractor.updateFavoriteStatus(any(), any()) } throws RuntimeException()

        // action
        createStore()
        store.accept(ProductListStore.Intent.OnIsFavoriteChanged(1, true))

        // verify
        Assertions.assertNotNull(store.state.error)
    }

    @Test
    @DisplayName("When updateFavoriteStatus() succeeded, shouldn't show error")
    fun test9() {
        //mock
        coEvery { productInteractor.updateFavoriteStatus(any(), any()) } just Runs

        // action
        createStore()
        store.accept(ProductListStore.Intent.OnIsFavoriteChanged(1, true))

        // verify
        Assertions.assertNull(store.state.error)
    }

    private fun createStore() {
        store = ProductListStoreFactoryImpl(
            storeFactory = DefaultStoreFactory,
            productListIntentExecutor = ProductListIntentExecutorImpl(
                productInteractor = productInteractor,
                dispatcherProvider = DispatcherProviderStub(),
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