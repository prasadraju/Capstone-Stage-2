package com.mobileapp.localnews.widget;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.google.gson.Gson;
import com.mobileapp.localnews.MyContentProvider;
import com.mobileapp.localnews.MyDatabaseHelper;
import com.mobileapp.localnews.R;
import com.mobileapp.localnews.data.NewsPojo;

import java.util.ArrayList;
import java.util.List;

/**
 * WidgetDataProvider acts as the adapter for the collection view widget,
 * providing RemoteViews to the widget in the getViewAt method.
 */
public class WidgetDataProvider implements RemoteViewsService.RemoteViewsFactory {

    private static final String TAG = "WidgetDataProvider";

    List<String> mCollection = new ArrayList<>();
    Context mContext = null;

    public WidgetDataProvider(Context context, Intent intent) {
        mContext = context;
    }

    @Override
    public void onCreate() {
        initData();
    }

    @Override
    public void onDataSetChanged() {
        initData();
    }

    @Override
    public void onDestroy() {

    }

    @Override
    public int getCount() {
        return mCollection.size();
    }

    @Override
    public RemoteViews getViewAt(int position) {
        RemoteViews view = new RemoteViews(mContext.getPackageName(),
                android.R.layout.simple_list_item_1);
        view.setTextViewText(android.R.id.text1, mCollection.get(position));
        return view;
    }

    @Override
    public RemoteViews getLoadingView() {
        return null;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    private void initData() {
        mCollection.clear();

        Thread thread = new Thread() {
            public void run() {
                String[] projection = {
                        MyDatabaseHelper.KEY_ROWID,
                        MyDatabaseHelper.KEY_CODE,
                        MyDatabaseHelper.KEY_NAME,};

                Uri uri = Uri.parse(MyContentProvider.NEWS_URI + "/" + R.id.id_top);
                Cursor cursor = mContext.getContentResolver().query(uri, projection, null, null, null);

                if (cursor != null) {
                    if (cursor.moveToFirst()) {
                        String myCode = cursor.getString(cursor.getColumnIndexOrThrow(MyDatabaseHelper.KEY_CODE));
                        String myName = cursor.getString(cursor.getColumnIndexOrThrow(MyDatabaseHelper.KEY_NAME));
                        NewsPojo data = new Gson().fromJson(myName, NewsPojo.class);

                        List<NewsPojo.Inner> list = data.getArticles();

                        for (int i = 0; i < list.size(); i++) {
                            NewsPojo.Inner inner = list.get(i);
                            mCollection.add(inner.getTitle());
                        }

                    }
                }
            }
        };
        thread.start();

    }

}
