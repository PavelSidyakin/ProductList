package com.productlist.common_utils.di

import com.productlist.common_utils.CommonUtilsFeatureApi
import com.productlist.common_utils.CommonUtilsFeatureDependencies
import dagger.Component
import javax.inject.Singleton

@Component(dependencies = [CommonUtilsFeatureDependencies::class], modules = [CommonUtilsModule::class])
@Singleton
internal interface CommonUtilsComponent : CommonUtilsFeatureApi {

    companion object {
        fun initAndGet(commonUtilsDependencies: CommonUtilsFeatureDependencies): CommonUtilsComponent {
            return DaggerCommonUtilsComponent.builder()
                .commonUtilsFeatureDependencies(commonUtilsDependencies)
                .build()
        }
    }
}
