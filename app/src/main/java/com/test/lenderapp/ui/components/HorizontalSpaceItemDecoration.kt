package com.test.lenderapp.ui.components

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView

//This class is required to remove divider from last row of recycler view.
class HorizontalSpaceItemDecoration(private val horizontalSpaceWidth: Int) : RecyclerView.ItemDecoration() {

    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
        if (parent.getChildAdapterPosition(view) != parent.adapter!!.itemCount - 1) {
            outRect.right = horizontalSpaceWidth
        }
    }
}