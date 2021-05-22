package com.productlist.product_data.impl

import android.content.Context
import android.content.res.Resources
import com.google.gson.Gson
import com.productlist.common_utils.coroutine_utils.DispatcherProvider
import com.productlist.product_data.R
import com.productlist.product_domain.data.ProductSourceRepository
import com.productlist.product_domain.model.Product
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.withContext
import java.io.InputStream
import java.io.InputStreamReader
import javax.inject.Inject


internal class ProductSourceRepositoryImpl @Inject constructor(
    private val context: Context,
    private val dispatcherProvider: DispatcherProvider,
) : ProductSourceRepository {

    override suspend fun requestProducts(): List<Product> = coroutineScope {
        withContext(dispatcherProvider.io()) {
            val res: Resources = context.resources
            var inputStream: InputStream? = null
            try {
                inputStream = res.openRawResource(R.raw.products)
                Gson().fromJson(
                    InputStreamReader(inputStream),
                    Array<ProductJsonModel>::class.java,
                ).map { it.toProduct() }
            }
            finally {
                try {
                    inputStream?.close()
                } catch (th: Throwable) {
                    // ignore
                }
            }
        }
    }

    private fun ProductJsonModel.toProduct(): Product {
        return Product(
            id = 0,
            title = title,
            author = author.orEmpty(),
            imageUrl = imageURL.orEmpty(),
            isFavorite = false,
        )
    }
}