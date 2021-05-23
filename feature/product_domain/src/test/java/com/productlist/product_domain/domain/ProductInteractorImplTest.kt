package com.productlist.product_domain.domain

import com.productlist.product_domain.data.ProductDbRepository
import com.productlist.product_domain.data.ProductSourceRepository
import com.productlist.product_domain.model.Product
import io.mockk.MockKAnnotations
import io.mockk.Runs
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.just
import io.mockk.slot
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.toCollection
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test

internal class ProductInteractorImplTest {

    @MockK
    private lateinit var dbRepository: ProductDbRepository

    @MockK
    private lateinit var sourceRepository: ProductSourceRepository

    private lateinit var interactor: ProductInteractorImpl

    @BeforeEach
    fun beforeEachTest() {
        MockKAnnotations.init(this)
        interactor = ProductInteractorImpl(dbRepository, sourceRepository)

        // Just execute transaction in the current thread.
        val transactionBlock = slot<(suspend () -> Unit)>()
        coEvery { dbRepository.withTransaction(block = capture(transactionBlock)) } answers {
            runBlocking {
                transactionBlock.captured.invoke()
            }
        }
    }

    @Test
    @DisplayName("When observeProducts() is called, should emit the same product list")
    fun test0() = runBlocking {
        // when
        every { dbRepository.observeProducts() } returns flowOf(PRODUCTS)

        // action
        val productsFromInteractor: List<List<Product>> = interactor.observeProducts()
            .toCollection(mutableListOf())

        //verify
        Assertions.assertArrayEquals(PRODUCTS.toTypedArray(), productsFromInteractor[0].toTypedArray())
    }

    @Test
    @DisplayName("When observeProduct() is called, should emit the same product")
    fun test1() = runBlocking {
        // when
        every { dbRepository.observeProduct(PRODUCT_1_ID) } returns flowOf(PRODUCT_1)

        // action
        val productsFromInteractor: List<Product> = interactor.observeProduct(PRODUCT_1_ID)
            .toCollection(mutableListOf())

        //verify
        Assertions.assertEquals(PRODUCT_1.copy(), productsFromInteractor[0])
    }

    @Nested
    @DisplayName("When loadProducts() is called")
    inner class Nested0 {

        @BeforeEach
        fun beforeEachTest() = runBlocking {
            // when
            coEvery { dbRepository.insertProducts(any()) } just Runs
            coEvery { dbRepository.clearProducts() } just Runs
            coEvery { sourceRepository.requestProducts() } returns PRODUCTS

            // action
            interactor.loadProducts()
        }

        @Test
        @DisplayName("should delete previous products from the DB")
        fun test2() = runBlocking {
            //verify
            coVerify { dbRepository.clearProducts() }
        }

        @Test
        @DisplayName("should get products from the source and put to the DB")
        fun test3() = runBlocking {
            //verify
            coVerify { dbRepository.insertProducts(PRODUCTS.toList()) }
        }
    }

    @Test
    @DisplayName("When updateFavoriteStatus() is called, should update the status in the DB")
    fun test3() = runBlocking {
        // when
        coEvery { dbRepository.updateFavoriteStatus(any(), any()) } just Runs

        // action
        interactor.updateFavoriteStatus(PRODUCT_1_ID, isFavorite = true)

        //verify
        coVerify { dbRepository.updateFavoriteStatus(PRODUCT_1_ID, isFavorite = true) }
    }

    @Test
    @DisplayName("When updateFavoriteStatus() is called with wrong product ID, should throw an exception")
    fun test4(): Unit = runBlocking {
        // when
        coEvery { dbRepository.updateFavoriteStatus(any(), any()) } just Runs
        coEvery { dbRepository.updateFavoriteStatus(eq(NON_EXISTENT_PRODUCT_ID), any()) } throws RuntimeException("Wrong ID")

        // action
        Assertions.assertThrows(Throwable::class.java) {
            runBlocking {
                interactor.updateFavoriteStatus(NON_EXISTENT_PRODUCT_ID, isFavorite = true)
            }
        }
    }

    companion object {
        private const val PRODUCT_1_ID = 1L
        private val PRODUCT_1 = Product(PRODUCT_1_ID, "title1", "author1", null, isFavorite = false)

        private const val NON_EXISTENT_PRODUCT_ID = 9999L

        private val PRODUCTS = listOf(
            PRODUCT_1,
            Product(2, "title2", null, null, isFavorite = false),
        )
    }
}
