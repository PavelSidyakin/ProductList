package com.productlist

import android.app.Application
import android.content.Context
import com.productlist.app_database.AppDatabaseComponentHolder
import com.productlist.app_database.AppDatabaseFeatureApi
import com.productlist.app_database.AppDatabaseFeatureDependencies
import com.productlist.app_database.impl.room.ProductDao
import com.productlist.common_utils.CommonUtilsComponentHolder
import com.productlist.common_utils.CommonUtilsFeatureApi
import com.productlist.common_utils.CommonUtilsFeatureDependencies
import com.productlist.common_utils.coroutine_utils.DispatcherProvider
import com.productlist.module_injector.BaseDependencyHolder
import com.productlist.module_injector.BaseFeatureDependencies
import com.productlist.module_injector.DependencyHolder0
import com.productlist.module_injector.DependencyHolder1
import com.productlist.module_injector.DependencyHolder2
import com.productlist.product_data.ProductDataComponentHolder
import com.productlist.product_data.ProductDataFeatureApi
import com.productlist.product_data.ProductDataFeatureDependencies
import com.productlist.product_domain.ProductDomainComponentHolder
import com.productlist.product_domain.ProductDomainFeatureApi
import com.productlist.product_domain.ProductDomainFeatureDependencies
import com.productlist.product_domain.data.ProductDbRepository
import com.productlist.product_domain.data.ProductSourceRepository
import com.productlist.product_domain.domain.ProductInteractor
import com.productlist.product_ui.ProductUiComponentHolder
import com.productlist.product_ui.ProductUiFeatureDependencies

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

        AppDatabaseComponentHolder.dependencyProvider = {
            class AppDatabaseDependencyHolder(
                override val block: (BaseDependencyHolder<AppDatabaseFeatureDependencies>) -> AppDatabaseFeatureDependencies
            ) : DependencyHolder0<AppDatabaseFeatureDependencies>()

            AppDatabaseDependencyHolder { dependencyHolder ->
                object : AppDatabaseFeatureDependencies {
                    override val context: Context = this@TheApplication
                    override val dependencyHolder: BaseDependencyHolder<out BaseFeatureDependencies> =
                        dependencyHolder
                }
            }.dependencies
        }

        ProductDataComponentHolder.dependencyProvider = {
            class ProductDataDependencyHolder(
                override val block: (BaseDependencyHolder<ProductDataFeatureDependencies>, CommonUtilsFeatureApi, AppDatabaseFeatureApi) -> ProductDataFeatureDependencies
            ) : DependencyHolder2<CommonUtilsFeatureApi, AppDatabaseFeatureApi, ProductDataFeatureDependencies>(
                api1 = CommonUtilsComponentHolder.get(),
                api2 = AppDatabaseComponentHolder.get(),
            )

            ProductDataDependencyHolder { baseDependencyHolder: BaseDependencyHolder<ProductDataFeatureDependencies>, commonUtilsFeatureApi: CommonUtilsFeatureApi, appDatabaseFeatureApi: AppDatabaseFeatureApi ->
                object : ProductDataFeatureDependencies {
                    override val dispatcherProvider: DispatcherProvider =
                        commonUtilsFeatureApi.dispatcherProvider
                    override val database = appDatabaseFeatureApi.roomDb
                    override val productDao: ProductDao = appDatabaseFeatureApi.productDao
                    override val dependencyHolder: BaseDependencyHolder<out BaseFeatureDependencies> =
                        baseDependencyHolder
                    override val context = this@TheApplication
                }
            }.dependencies

        }

        ProductDomainComponentHolder.dependencyProvider = {
            class ProductDomainDependencyHolder(
                override val block: (BaseDependencyHolder<ProductDomainFeatureDependencies>, ProductDataFeatureApi) -> ProductDomainFeatureDependencies
            ) : DependencyHolder1<ProductDataFeatureApi, ProductDomainFeatureDependencies>(
                api1 = ProductDataComponentHolder.get(),
            )

            ProductDomainDependencyHolder { baseDependencyHolder: BaseDependencyHolder<ProductDomainFeatureDependencies>, productDataFeatureApi: ProductDataFeatureApi ->
                object : ProductDomainFeatureDependencies {
                    override val productSourceRepository: ProductSourceRepository =
                        productDataFeatureApi.productSourceRepository
                    override val productDbRepository: ProductDbRepository =
                        productDataFeatureApi.productDbRepository
                    override val dependencyHolder: BaseDependencyHolder<out BaseFeatureDependencies> =
                        baseDependencyHolder
                }
            }.dependencies
        }

        ProductUiComponentHolder.dependencyProvider = {
            class ProductUiDependencyHolder(
                override val block: (BaseDependencyHolder<ProductUiFeatureDependencies>, CommonUtilsFeatureApi, ProductDomainFeatureApi) -> ProductUiFeatureDependencies

            ) : DependencyHolder2<CommonUtilsFeatureApi, ProductDomainFeatureApi, ProductUiFeatureDependencies>(
                api1 = CommonUtilsComponentHolder.get(),
                api2 = ProductDomainComponentHolder.get(),
            )

            ProductUiDependencyHolder { baseDependencyHolder: BaseDependencyHolder<ProductUiFeatureDependencies>, commonUtilsFeatureApi: CommonUtilsFeatureApi, productDomainFeatureApi: ProductDomainFeatureApi ->
                object : ProductUiFeatureDependencies {
                    override val productInteractor: ProductInteractor =
                        productDomainFeatureApi.productInteractor
                    override val dispatcherProvider: DispatcherProvider =
                        commonUtilsFeatureApi.dispatcherProvider
                    override val dependencyHolder: BaseDependencyHolder<out BaseFeatureDependencies> =
                        baseDependencyHolder
                }
            }.dependencies
        }
    }
}