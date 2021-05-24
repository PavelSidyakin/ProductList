package com.productlist.common_utils.coroutine_utils

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

/**
 * Stub with the specified [CoroutineDispatcher] for all dispatchers.
 */
class DispatcherProviderStub(
    private val dispatcher: CoroutineDispatcher = Dispatchers.Unconfined,
) : DispatcherProvider {
    override fun io(): CoroutineDispatcher = dispatcher
    override fun main(): CoroutineDispatcher = dispatcher
    override fun mainImmediate(): CoroutineDispatcher = dispatcher
}