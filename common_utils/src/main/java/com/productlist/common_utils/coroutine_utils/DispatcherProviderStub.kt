package com.productlist.common_utils.coroutine_utils

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

/**
 * Stub with [Dispatchers.Unconfined] for all dispatchers.
 * Use for unit tests only
 */
class DispatcherProviderStub : DispatcherProvider {
    override fun io(): CoroutineDispatcher = Dispatchers.Unconfined
    override fun main(): CoroutineDispatcher = Dispatchers.Unconfined
}