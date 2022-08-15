package com.example.ticktock.widget

import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.widget.RemoteViews
import com.example.ticktock.R
import com.example.ticktock.views.ClockDigit


/**
 * Implementation of App Widget functionality.
 */
class TickTockWidget : AppWidgetProvider() {
    override fun onUpdate(
        context: Context,
        appWidgetManager: AppWidgetManager,
        appWidgetIds: IntArray
    ) {
        // There may be multiple widgets active, so update all of them
        for (appWidgetId in appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId)
        }
    }

    override fun onEnabled(context: Context) {
        super.onEnabled(context)
    }

    override fun onDisabled(context: Context) {
        super.onDisabled(context)
    }
}

internal fun updateAppWidget(
    context: Context,
    appWidgetManager: AppWidgetManager,
    appWidgetId: Int
) {
    // Construct the RemoteViews object
    val views = RemoteViews(context.packageName, R.layout.tick_tock_widget)
    //time(views)
    // Instruct the widget manager to update the widget
    appWidgetManager.updateAppWidget(appWidgetId, views)
    val myView = ClockDigit(context)
    myView.measure(500, 500)
    myView.layout(0, 0, 500, 500)
    val bitmap = Bitmap.createBitmap(500, 500, Bitmap.Config.ARGB_8888)
    myView.draw(Canvas(bitmap))
    views.setImageViewBitmap(R.id.imageView, bitmap)
}

