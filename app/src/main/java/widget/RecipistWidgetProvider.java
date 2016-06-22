// I used the guide created by Dharmang Soni to help create my collection widget
// http://dharmangsoni.blogspot.com/2014/03/collection-widget-with-event-handling.html

package widget;

import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.widget.RemoteViews;

import com.example.tobias.recipist.R;

/**
 * Created by Tobias on 22-06-2016.
 */
public class RecipistWidgetProvider extends AppWidgetProvider {
    private static final String ACTION_REFRESH = "widget.action.REFRESH";

    @Override
    public void onReceive(Context context, Intent intent) {
        super.onReceive(context, intent);
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        for (int widgetId : appWidgetIds) {
            RemoteViews remoteViews = initViews(context, appWidgetManager, widgetId);
            appWidgetManager.updateAppWidget(widgetId, remoteViews);
        }

        super.onUpdate(context, appWidgetManager, appWidgetIds);
    }

    @SuppressWarnings("deprecation")
    private RemoteViews initViews(Context context, AppWidgetManager appWidgetManager, int widgetId) {
        RemoteViews remoteViews = new RemoteViews(context.getPackageName(), R.layout.widget_layout);

        Intent intent = new Intent(context, RecipistWidgetService.class);
        intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, widgetId);
        intent.setData(Uri.parse(intent.toUri(Intent.URI_INTENT_SCHEME)));

        remoteViews.setRemoteAdapter(widgetId, R.id.widgetCollectionList, intent);

        return remoteViews;
    }
}
