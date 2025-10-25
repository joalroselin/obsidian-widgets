package com.hellojoal.obsidianwidget // Make sure this matches your package name

import android.app.PendingIntent
import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.widget.RemoteViews

/**
 * This is the main class that controls the widget.
 */
class NewNoteWidgetProvider : AppWidgetProvider() {

    /**
     * This function is called when the widget is placed on the home screen
     * and when it updates.
     */
    override fun onUpdate(
        context: Context,
        appWidgetManager: AppWidgetManager,
        appWidgetIds: IntArray
    ) {
        // Loop through all instances of this widget
        for (appWidgetId in appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId)
        }
    }

    /**
     * A helper function to set up a single widget instance.
     */
    private fun updateAppWidget(
        context: Context,
        appWidgetManager: AppWidgetManager,
        appWidgetId: Int
    ) {
        // 1. Get the layout for the widget
        val views = RemoteViews(context.packageName, R.layout.new_note_widget)

        // 2. Define the Intent to launch
        val vaultName = "Hello%Joal"
        val obsidianUri = "obsidian://new?"

        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(obsidianUri)).apply {
            // This flag is required to launch an app from a background service (like a widget)
            flags = Intent.FLAG_ACTIVITY_NEW_TASK
        }

        // 3. Create the PendingIntent. This is the "click" action.
        // We use FLAG_IMMUTABLE for security, as required by modern Android versions.
        val pendingIntent = PendingIntent.getActivity(
            context,
            appWidgetId, // Use a unique request code (the widgetId is perfect for this)
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        // 4. Attach the click listener (PendingIntent) to the button
        views.setOnClickPendingIntent(R.id.widget_button, pendingIntent)

        // 5. Tell the AppWidgetManager to apply the changes to the widget
        appWidgetManager.updateAppWidget(appWidgetId, views)
    }
}