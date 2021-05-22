package com.productlist.product_ui.impl.mvi.product_list.view

import android.graphics.Rect
import android.util.DisplayMetrics
import android.view.View
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.arkivanov.mvikotlin.core.utils.DiffBuilder
import com.arkivanov.mvikotlin.core.utils.diff
import com.arkivanov.mvikotlin.core.view.BaseMviView
import com.arkivanov.mvikotlin.core.view.ViewRenderer
import com.productlist.product_ui.R
import com.productlist.product_ui.databinding.FragmentProductListBinding
import com.productlist.product_ui.impl.mvi.product_list.store.ProductListStore
import com.productlist.product_ui.impl.mvi.product_list.view.recycler.ProductListAdapter
import com.productlist.product_ui.impl.mvi.product_list.view.recycler.ProductListItemListener
import kotlin.math.roundToInt


internal class ProductListViewImpl(
    private val binding: FragmentProductListBinding,
) : BaseMviView<ProductListStore.State, ProductListStore.Intent>(), ProductListView {

    private val productListItemListener: ProductListItemListener = object :
        ProductListItemListener {
        override fun onProductClicked(productId: Long) {
            dispatch(ProductListStore.Intent.OnProductSelected(productId))
        }

        override fun onFavoriteStateChanged(productId: Long, isFavorite: Boolean) {
            dispatch(ProductListStore.Intent.OnIsFavoriteChanged(productId, isFavorite))
        }
    }

    private val productListAdapter = ProductListAdapter(productListItemListener)

    override val renderer: ViewRenderer<ProductListStore.State>? = diff {
        diffRecyclerList(
            get = ProductListStore.State::productList,
            set = { list -> productListAdapter.products = list }
        )
    }

    init {
        val itemWidth: Int = binding.productListFakeItemView.width

        binding.productListRecycler.layoutManager =
            GridLayoutManager(binding.root.context, calculateNumberOfColumns(itemWidth))
        binding.productListRecycler.adapter = productListAdapter

        binding.productListRecycler.addItemDecoration(
            MarginItemDecoration(
                margin = binding.root.context.resources.getDimensionPixelSize(R.dimen.product_list_item_margin),
            )
        )
    }

    private fun calculateNumberOfColumns(
        columnWidthPx: Int,
    ): Int {
        val displayMetrics: DisplayMetrics = binding.root.context.resources.displayMetrics
        return ((displayMetrics.widthPixels.toFloat() / columnWidthPx) + 0.5).roundToInt()
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

    class MarginItemDecoration(private val margin: Int) : RecyclerView.ItemDecoration() {
        override fun getItemOffsets(
            outRect: Rect,
            view: View,
            parent: RecyclerView,
            state: RecyclerView.State
        ) {
            with(outRect) {
                right = margin
                left = margin
                top = margin
                bottom = margin
            }
        }
    }
}