package com.productlist.common_utils

import com.productlist.common_utils.coroutine_utils.DispatcherProvider
import com.productlist.module_injector.BaseFeatureAPI

interface CommonUtilsFeatureApi : BaseFeatureAPI {
    /**
     * Dispatcher provider.
     */
    val dispatcherProvider: DispatcherProvider
}