package com.productlist.common_utils

import com.productlist.common_utils.di.CommonUtilsComponent
import com.productlist.module_injector.ComponentHolder
import com.productlist.module_injector.ComponentHolderDelegate

object CommonUtilsComponentHolder : ComponentHolder<CommonUtilsFeatureApi, CommonUtilsFeatureDependencies> {
    private val componentHolderDelegate = ComponentHolderDelegate<
            CommonUtilsFeatureApi,
            CommonUtilsFeatureDependencies,
            CommonUtilsComponent> { dependencies: CommonUtilsFeatureDependencies ->
        CommonUtilsComponent.initAndGet(dependencies)
    }

    override var dependencyProvider: (() -> CommonUtilsFeatureDependencies)? by componentHolderDelegate::dependencyProvider

    override fun get(): CommonUtilsFeatureApi = componentHolderDelegate.get()
}

