package com.mobileapp.localnews;

/**
 * Created by Venkatesh on 3/4/2017.
 */

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;

public class MyContentProvider extends ContentProvider{

    private MyDatabaseHelper dbHelper;

    private static final int ALL_NEWS = 1;
    private static final int SINGLE_NEWS = 2;

    // authority is the symbolic name of your provider
    // To avoid conflicts with other providers, you should use
    // Internet domain ownership (in reverse) as the basis of your provider authority.
    private static final String AUTHORITY = "com.mobileapp.localnews";

    // create content URIs from the authority by appending path to database table
    public static final Uri NEWS_URI = Uri.parse("content://" + AUTHORITY + "/countries");



    private static final UriMatcher uriMatcher;
    static {
        uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        uriMatcher.addURI(AUTHORITY, "countries", ALL_NEWS);
        uriMatcher.addURI(AUTHORITY, "countries/#", SINGLE_NEWS);
    }

    @Override
    public boolean onCreate() {
        dbHelper = new MyDatabaseHelper(getContext());
        return false;
    }

    //Return the MIME type corresponding to a content URI
    @Override
    public String getType(Uri uri) {

        switch (uriMatcher.match(uri)) {
            case ALL_NEWS:
                return "vnd.android.cursor.dir/vnd.com.mobileapp.localnews.countries";
            case SINGLE_NEWS:
                return "vnd.android.cursor.item/vnd.com.mobileapp.localnews.countries";
            default:
                throw new IllegalArgumentException("Unsupported URI: " + uri);
        }
    }

       @Override
    public Uri insert(Uri uri, ContentValues values) {

        SQLiteDatabase db = dbHelper.getWritableDatabase();
        switch (uriMatcher.match(uri)) {
            case ALL_NEWS:
                //do nothing
                break;
            default:
                throw new IllegalArgumentException("Unsupported URI: " + uri);
        }
        long id = db.insert(MyDatabaseHelper.SQLITE_TABLE, null, values);

        getContext().getContentResolver().notifyChange(uri, null);
        return Uri.parse(NEWS_URI + "/" + id);
    }



    @Override
    public Cursor query(Uri uri, String[] projection, String selection,String[] selectionArgs, String sortOrder) {


        SQLiteDatabase db = dbHelper.getWritableDatabase();
        SQLiteQueryBuilder queryBuilder = new SQLiteQueryBuilder();
        queryBuilder.setTables(MyDatabaseHelper.SQLITE_TABLE);

        switch (uriMatcher.match(uri)) {
            case ALL_NEWS:
                //do nothing
                break;
            case SINGLE_NEWS:
                String id = uri.getPathSegments().get(1);
                queryBuilder.appendWhere(MyDatabaseHelper.KEY_CODE + "=" + id);
                break;
            default:
                throw new IllegalArgumentException("Unsupported URI: " + uri);
        }

        Cursor cursor = queryBuilder.query(db, projection, selection, selectionArgs, null, null, sortOrder);

        return cursor;

    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        return 0;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        return 0;
    }


}
