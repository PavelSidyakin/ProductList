package com.productlist

import android.app.Application
import com.productlist.common_utils.CommonUtilsComponentHolder
import com.productlist.common_utils.CommonUtilsFeatureDependencies
import com.productlist.module_injector.BaseDependencyHolder
import com.productlist.module_injector.BaseFeatureDependencies
import com.productlist.module_injector.DependencyHolder0

internal class TheApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        CommonUtilsComponentHolder.dependencyProvider = {
            class CommonUtilsComponentDependencyHolder(
                override val block: (BaseDependencyHolder<CommonUtilsFeatureDependencies>) -> CommonUtilsFeatureDependencies
            ) : DependencyHolder0<CommonUtilsFeatureDependencies>()

            CommonUtilsComponentDependencyHolder { baseDependencyHolder ->
                object : CommonUtilsFeatureDependencies {
                    override val dependencyHolder: BaseDependencyHolder<out BaseFeatureDependencies> =
                        baseDependencyHolder
                }
            }.dependencies
        }
    }

}