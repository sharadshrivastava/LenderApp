package com.test.lenderapp.util

import android.content.Context
import android.content.res.Resources




object Utils {

    fun pxFromDp(context: Context?, dp: Float): Float {
        return dp * Resources.getSystem().displayMetrics.density
    }

    fun DpFromPx(context: Context?, px: Float): Float {
        return px / Resources.getSystem().displayMetrics.density;
    }
}