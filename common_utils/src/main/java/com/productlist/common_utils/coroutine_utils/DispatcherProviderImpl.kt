package com.productlist.common_utils.coroutine_utils

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import javax.inject.Inject

internal class DispatcherProviderImpl
    @Inject
    constructor()
    : DispatcherProvider {

    override fun main(): CoroutineDispatcher = Dispatchers.Main

    override fun io(): CoroutineDispatcher = Dispatchers.IO

}