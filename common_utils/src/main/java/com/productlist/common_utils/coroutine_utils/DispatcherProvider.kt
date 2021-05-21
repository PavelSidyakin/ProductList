package com.productlist.common_utils.coroutine_utils

import kotlinx.coroutines.CoroutineDispatcher

interface DispatcherProvider {

    fun io(): CoroutineDispatcher
    fun main(): CoroutineDispatcher

}