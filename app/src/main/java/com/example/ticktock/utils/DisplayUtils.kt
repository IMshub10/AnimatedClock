package com.example.ticktock.utils

import android.util.DisplayMetrics
import android.view.WindowManager


object DisplayUtils {
     fun getWidthNHeight(windowManager: WindowManager) : Display{
        val displayMetrics = DisplayMetrics()
        windowManager.defaultDisplay.getMetrics(displayMetrics)
         return Display(displayMetrics.widthPixels,displayMetrics.heightPixels)
    }
}

data class Display(var width:Int, var height:Int)