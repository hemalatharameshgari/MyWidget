package com.hemalatha.mywidgets;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.widget.RemoteViews;

import java.text.DateFormat;
import java.util.Date;

/**
 * Implementation of App Widget functionality.
 */
public class NewAppWidget extends AppWidgetProvider {
    //step1
    private static final String SHERED_FILE = "com.hemalatha.mywidgets";
    private static final String count = "count";

    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                int appWidgetId) {
        //step2
        SharedPreferences preferences = context.getSharedPreferences(SHERED_FILE, 0);
        int count1 = preferences.getInt(count + appWidgetId, 0);
        count1++;
        String dateString = DateFormat.getDateInstance(DateFormat.SHORT).format(new Date());

        //step3
        // Construct the RemoteViews object
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.widget_update_design);

        //step4
        views.setTextViewText(R.id.textViewWidgetId, String.valueOf(appWidgetId));
        views.setTextViewText(R.id.textView3, context.getResources().
                getString(R.string.date_format, count1, dateString));

        //step5
        SharedPreferences.Editor editor = preferences.edit();
        editor.putInt(count + appWidgetId, count1);
        editor.apply();


        //step6
        Intent updateIntent = new Intent(context, MainActivity.class);
        updateIntent.setAction(AppWidgetManager.ACTION_APPWIDGET_UPDATE);
        int[] array = new int[]{appWidgetId};
        updateIntent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS, array);
        PendingIntent intent = PendingIntent.getBroadcast(context, appWidgetId,
                updateIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        views.setOnClickPendingIntent(R.id.button, intent);


        // Instruct the widget manager to update the widget
        appWidgetManager.updateAppWidget(appWidgetId, views);
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // There may be multiple widgets active, so update all of them
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId);
        }
    }

    @Override
    public void onEnabled(Context context) {
        // Enter relevant functionality for when the first widget is created
    }

    @Override
    public void onDisabled(Context context) {
        // Enter relevant functionality for when the last widget is disabled
    }
}

