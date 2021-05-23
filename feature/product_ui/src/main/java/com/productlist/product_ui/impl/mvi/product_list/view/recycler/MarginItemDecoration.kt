package com.productlist.product_ui.impl.mvi.product_list.view.recycler

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MarginItemDecoration(private val margin: Int) : RecyclerView.ItemDecoration() {
    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        val firstItemInRow = isFirstItemInRow(view, parent.layoutManager)
        val lastItemInRow = isLastItemInRow(view, parent.layoutManager)

        with(outRect) {
            top = margin
            bottom = margin
            left = when {
                firstItemInRow -> 0
                else -> margin
            }
            right = when {
                lastItemInRow -> 0
                else -> margin
            }
        }
    }

    private fun isLastItemInRow(view: View, layoutManager: RecyclerView.LayoutManager?) = when (layoutManager) {
        is GridLayoutManager -> {
            val spanCount = layoutManager.spanCount
            val layoutParams = view.layoutParams as GridLayoutManager.LayoutParams
            layoutParams.spanIndex + layoutParams.spanSize == spanCount
        }
        else -> false
    }

    private fun isFirstItemInRow(view: View, layoutManager: RecyclerView.LayoutManager?) = when (layoutManager) {
        is GridLayoutManager -> {
            val layoutParams = view.layoutParams as GridLayoutManager.LayoutParams
            layoutParams.spanIndex == 0
        }
        else -> true
    }
}