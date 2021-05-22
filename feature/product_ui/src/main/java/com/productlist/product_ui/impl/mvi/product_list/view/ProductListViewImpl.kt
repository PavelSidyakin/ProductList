package com.productlist.product_ui.impl.mvi.product_list.view

import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import com.arkivanov.mvikotlin.core.utils.DiffBuilder
import com.arkivanov.mvikotlin.core.utils.diff
import com.arkivanov.mvikotlin.core.view.BaseMviView
import com.arkivanov.mvikotlin.core.view.ViewRenderer
import com.productlist.product_domain.model.Product
import com.productlist.product_ui.databinding.FragmentProductListBinding
import com.productlist.product_ui.impl.mvi.product_list.store.ProductListStore
import com.productlist.product_ui.impl.mvi.product_list.view.recycler.ProductListAdapter
import com.productlist.product_ui.impl.mvi.product_list.view.recycler.ProductListItemClickListener

internal class ProductListViewImpl(
    private val binding: FragmentProductListBinding,
) : BaseMviView<ProductListStore.State, ProductListStore.Intent>(), ProductListView {

    private val productListItemClickListener: ProductListItemClickListener = object :
        ProductListItemClickListener {
        override fun onProductClicked(product: Product) {
            Log.i("Product", "Product Clicked: $product")
        }
    }

    private val productListAdapter = ProductListAdapter(productListItemClickListener)

    init {
        binding.productListRecycler.layoutManager = LinearLayoutManager(binding.root.context)
        binding.productListRecycler.adapter = productListAdapter
    }

    override val renderer: ViewRenderer<ProductListStore.State>? = diff {
        diffRecyclerList(
            get = ProductListStore.State::productList,
            set = { list -> productListAdapter.products = list }
        )
    }

    private fun <Model : Any, PM, PD : List<PM>> DiffBuilder<Model>.diffRecyclerList(
        get: (Model) -> PD?,
        set: (PD) -> Unit
    ) {
        diff(
            get = get,
            set = { pd: PD? -> pd?.let { set(it) } },
            compare = { _, _ -> false } // Always let the adapter to compare
        )
    }
}