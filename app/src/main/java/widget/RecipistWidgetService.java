// I used the guide created by Dharmang Soni to help create my collection widget
// http://dharmangsoni.blogspot.com/2014/03/collection-widget-with-event-handling.html

package widget;

import android.content.Intent;
import android.widget.RemoteViewsService;

/**
 * Created by Tobias on 22-06-2016.
 */
public class RecipistWidgetService extends RemoteViewsService {
    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        return new RecipistWidgetDataProvider(getApplicationContext(), intent);
    }
}