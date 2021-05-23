package com.productlist.product_domain.data

import com.productlist.product_domain.model.Product

interface ProductSourceRepository {
    /**
     * Requests products from a source.
     *
     * @return List with [Product].
     * @error: if failed to request the list.
     */
    suspend fun requestProducts(): List<Product>
}