package androidkehitys.fi.widget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;

public class ExampleWidgetProvider extends AppWidgetProvider {
	private static int number = 0;
	
	@Override
	public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
		final int N = appWidgetIds.length;
        for (int i=0; i<N; i++) {
            int appWidgetId = appWidgetIds[i];
            updateAppWidget(context, appWidgetManager, appWidgetId);
        }
	}
	
	public void onDeleted(Context context, int[] appWidgetIds) {
		// Called when one or more instance of this appWidget is destroyed
		super.onDeleted(context, appWidgetIds);
	}
	
	@Override
	public void onDisabled(Context context) {
		// Called when last instance of this appWidget is destroyed
		super.onDisabled(context);
	}
	
	@Override
	public void onEnabled(Context context) {
        // Called when widget is instantiated
		super.onEnabled(context);
	}
	
	/**
	 * Update widget. This method can be called inside the same package.
	 * 
	 * @param context
	 * @param appWidgetManager
	 * @param appWidgetId
	 */
    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager, int appWidgetId) {
        Intent intent = new Intent(context, ExampleWidgetProvider.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 0, intent, 0);
        
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.widget_layout);
        views.setOnClickPendingIntent(R.id.start_button, pendingIntent);
        views.setTextViewText(R.id.number, ""+number);
        
        // Tell the widget manager to update
        appWidgetManager.updateAppWidget(appWidgetId, views);
    }

    @Override
    public void onReceive(Context context, Intent intent) {
    	// This method is called when ever this provider gets an intent. 
    	number += 1;
    	AppWidgetManager gm = AppWidgetManager.getInstance(context);
    	int [] appWidgetIds = gm.getAppWidgetIds(new ComponentName(context, ExampleWidgetProvider.class));

    	for (int i=0; i<appWidgetIds.length; i++) {
    		ExampleWidgetProvider.updateAppWidget(context, gm, appWidgetIds[i]);
    	}
    	super.onReceive(context, intent);
    }
    
	
}