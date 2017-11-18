package com.lonelyyhu.exercise.contentprovider;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.Nullable;

import com.lonelyyhu.exercise.contentprovider.MyContract.UserColumns;

public class MyContentProvider extends ContentProvider {

    private DBHelper dbHelper;
    private UriMatcher uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

    private static final int USERS = 100;
    private static final int USERS_WITH_ID = 101;

    public MyContentProvider() {
    }

    @Override
    public boolean onCreate() {

        // content://com.lonelyyhu.exercise.myContentProvider/users
        uriMatcher.addURI(MyContract.CONTENT_AUTHORITY, MyContract.USER_CONTENT, USERS);

        // content://com.lonelyyhu.exercise.myContentProvider/users/id
        uriMatcher.addURI(MyContract.CONTENT_AUTHORITY, MyContract.USER_CONTENT + "/#", USERS_WITH_ID);

        dbHelper = new DBHelper(getContext());

        return true;
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {

        switch (uriMatcher.match(uri)) {
            case USERS:
                //Rows aren't counted with null selection
                selection = (selection == null) ? "1" : selection;
                break;
            case USERS_WITH_ID:
                long id = ContentUris.parseId(uri);
                selection = String.format("%s = ?", UserColumns._ID);
                selectionArgs = new String[]{String.valueOf(id)};
                break;
            default:
                throw new IllegalArgumentException("Illegal delete URI");
        }

        SQLiteDatabase db = dbHelper.getWritableDatabase();
        int count = db.delete(MyContract.USER_CONTENT, selection, selectionArgs);

        if (count > 0) {
            notifyContentChange(uri);
        }

        return count;
    }

    @Override
    public String getType(Uri uri) {
        // TODO: Implement this to handle requests for the MIME type of the data
        // at the given URI.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {

        switch (uriMatcher.match(uri)) {

            case USERS:
                long id = dbHelper.getWritableDatabase().insert(MyContract.USER_CONTENT, null, values);
                if (id > 0) {
                    notifyContentChange(uri);
                    return ContentUris.withAppendedId(MyContract.USER_CONTENT_URI, id);
                }
                throw new RuntimeException("insert user fail");

        }

        throw new IllegalArgumentException("Illegal insert URI");

    }

    @Nullable
    @Override
    public Cursor query(Uri uri, String[] projection, String selection,
                        String[] selectionArgs, String sortOrder) {

        switch (uriMatcher.match(uri)) {

            case USERS:
                return dbHelper.getReadableDatabase().query(MyContract.USER_CONTENT, projection, selection, selectionArgs, null, null, sortOrder);

            case USERS_WITH_ID:

                long id = ContentUris.parseId(uri);
                selection = String.format("%s = ?", UserColumns._ID);
                selectionArgs = new String[]{String.valueOf(id)};
                return dbHelper.getReadableDatabase().query(MyContract.USER_CONTENT, projection, selection, selectionArgs, null, null, sortOrder);

            default:
                return null;


        }

    }

    @Override
    public int update(Uri uri, ContentValues values, String selection,
                      String[] selectionArgs) {

        switch (uriMatcher.match(uri)) {

            case USERS_WITH_ID:
                long id = ContentUris.parseId(uri);
                selection = String.format("%s = ?", UserColumns._ID);
                selectionArgs = new String[]{String.valueOf(id)};
                int count = dbHelper.getWritableDatabase().update(MyContract.USER_CONTENT, values, selection, selectionArgs);
                if (count>0) {
                    notifyContentChange(uri);
                }
                return count;
        }


        throw new IllegalArgumentException("Illegal update URI");
    }

    /**
     * Notify observers of the change
     * @param uri content uri
     */
    private void notifyContentChange(Uri uri) {

        if (getContext() != null) {
            getContext().getContentResolver().notifyChange(uri, null);
        }

    }
}
