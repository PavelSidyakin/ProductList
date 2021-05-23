package com.productlist.product_ui.impl.mvi.product_list.view

import com.arkivanov.mvikotlin.core.view.MviView
import com.productlist.product_ui.impl.mvi.product_list.store.ProductListStore

/**
 * MVIKotlin view interface.
 */
internal interface ProductListView: MviView<ProductListStore.State, ProductListStore.Intent>