package com.productlist.product_ui.impl.mvi.product_list.view

import android.util.DisplayMetrics
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.arkivanov.mvikotlin.core.utils.DiffBuilder
import com.arkivanov.mvikotlin.core.utils.diff
import com.arkivanov.mvikotlin.core.view.BaseMviView
import com.arkivanov.mvikotlin.core.view.ViewRenderer
import com.productlist.product_ui.R
import com.productlist.product_ui.databinding.FragmentProductListBinding
import com.productlist.product_ui.impl.mvi.product_list.store.ProductListStore
import com.productlist.product_ui.impl.mvi.product_list.view.recycler.ProductGridMarginItemDecoration
import com.productlist.product_ui.impl.mvi.product_list.view.recycler.ProductListAdapter
import com.productlist.product_ui.impl.mvi.product_list.view.recycler.ProductListItemListener
import com.productlist.product_ui.impl.views.ProductListItemView
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
            set = { list -> productListAdapter.submitList(list) }
        )
        diff(get = ProductListStore.State::isInProgress, set = { isInProgress ->
            binding.progressGroup.visibility = when (isInProgress) {
                true -> View.VISIBLE
                false -> View.GONE
            }
        })
        diff(get = ProductListStore.State::error, set = { showError(it) })
    }

    init {
        // Restore position on the fragment restore.
        productListAdapter.stateRestorationPolicy = RecyclerView.Adapter.StateRestorationPolicy.PREVENT_WHEN_EMPTY

        binding.productListRecycler.run {
            adapter = productListAdapter
            addItemDecoration(
                ProductGridMarginItemDecoration(
                    margin = binding.root.context.resources.getDimensionPixelSize(R.dimen.product_list_item_half_distance),
                )
            )
        }

        // Get the width of the list item view.
        val itemWidth = ProductListItemView(binding.root.context).run {
            measure(0, 0)
            measuredWidth
        } + binding.root.context.resources.displayMetrics.density * 64 // add margins

        binding.productListRecycler.run {
            layoutManager = GridLayoutManager(binding.root.context, calculateNumberOfColumns(itemWidth.toInt()))
        }
    }

    // Calculates number of columns by the item width and the display width.
    private fun calculateNumberOfColumns(columnWidthPx: Int): Int {
        val displayMetrics: DisplayMetrics = binding.root.context.resources.displayMetrics
        return ((displayMetrics.widthPixels.toFloat() / columnWidthPx) + 0.5).roundToInt()
    }

    private fun showError(throwable: Throwable?) {
        if (throwable != null) {
            Toast.makeText(binding.root.context, R.string.general_error_message, Toast.LENGTH_LONG).show()
        }
    }

    // Checks is the old and new list have the same reference (===).
    // If so, do not pass the list to recycler.
    private fun <Model : Any, PM, PD : List<PM>> DiffBuilder<Model>.diffRecyclerList(
        get: (Model) -> PD?,
        set: (PD) -> Unit
    ) {
        diff(
            get = get,
            set = { pd: PD? -> pd?.let { set(it) } },
            compare = { a, b -> a === b }
        )
    }
}