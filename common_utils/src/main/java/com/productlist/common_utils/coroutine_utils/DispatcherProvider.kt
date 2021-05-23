package com.productlist.common_utils.coroutine_utils

import kotlinx.coroutines.CoroutineDispatcher

/**
 * Provides different [CoroutineDispatcher]s in non-static way.
 * Useful for unit testing.
 */
interface DispatcherProvider {

    /**
     * Returns IO dispatcher.
     */
    fun io(): CoroutineDispatcher

    /**
     * Returns main thread dispatcher.
     */
    fun main(): CoroutineDispatcher

}