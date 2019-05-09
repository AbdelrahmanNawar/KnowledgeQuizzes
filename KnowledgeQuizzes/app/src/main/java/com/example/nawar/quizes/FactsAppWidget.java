package com.example.nawar.quizes;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;

import static com.example.nawar.quizes.Welcome.FACTS_KEY;


/**
 * Implementation of App Widget functionality.
 */
public class FactsAppWidget extends AppWidgetProvider {


    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                int appWidgetId) {

        String fact = SharedPreferencesMethods.loadSavedPreferencesString(context, context.getString(R.string.facts_key));
        // Construct the RemoteViews object
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.facts_app_widget);
        views.setTextViewText(R.id.appwidget_text, fact);
        views.setImageViewResource(R.id.imageView, R.drawable.widget_ico);
        Intent shareIntent = new Intent(context, Welcome.class);
        shareIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);

        shareIntent.putExtra(FACTS_KEY,"");
        PendingIntent sharePendingIntent = PendingIntent.getActivity(context, 0, shareIntent, 0);
        views.setOnClickPendingIntent(R.id.share, sharePendingIntent);
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

