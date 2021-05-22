package com.productlist.product_ui.impl.mvi.product_details.view

import com.arkivanov.mvikotlin.core.view.MviView
import com.productlist.product_ui.impl.mvi.product_details.store.ProductDetailsStore

internal interface ProductDetailsView: MviView<ProductDetailsStore.State, ProductDetailsStore.Intent>