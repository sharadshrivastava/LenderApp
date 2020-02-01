package com.test.lenderapp.util

import android.content.Context

object Utils {

    fun pxFromDp(context: Context?, dp: Float): Float {
        return dp * context?.resources?.displayMetrics?.density!!
    }
}