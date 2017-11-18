package com.lonelyyhu.exercise.contentprovider;

import android.app.LoaderManager;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.Loader;
import android.database.ContentObserver;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.CursorAdapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

import com.lonelyyhu.exercise.contentprovider.MyContract.UserColumns;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor> {

    private static final int LOADER_USER_CONTENT = 100;
    private ListView usersListView;
    SimpleCursorAdapter cursorAdapter;

    private ContentObserver userContentObserver = new ContentObserver(null) {
        @Override
        public void onChange(boolean selfChange) {

            Log.wtf("MainActivity", "ContentObserver onChange: ");

//            final Cursor cursor = getContentResolver().query(MyContract.USER_CONTENT_URI, null, null, null, null);
//
//            runOnUiThread(new Runnable() {
//                @Override
//                public void run() {
//                    cursorAdapter.swapCursor(cursor);
//                }
//            });

            getLoaderManager().getLoader(LOADER_USER_CONTENT).onContentChanged();

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        Log.wtf("MainActivity", "onCreate: ");
        
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        usersListView = findViewById(R.id.listview_users);

//        Cursor cursor = getContentResolver().query(MyContract.USER_CONTENT_URI, null, null, null, null);

        cursorAdapter = new SimpleCursorAdapter(this, R.layout.item_user, null
                , new String[]{UserColumns.NAME, UserColumns.AGE, UserColumns.PHONE}
                , new int[]{R.id.tv_name, R.id.tv_age, R.id.tv_phone}, CursorAdapter.NO_SELECTION);


        usersListView.setAdapter(cursorAdapter);

        getContentResolver().registerContentObserver(MyContract.USER_CONTENT_URI, true, userContentObserver);

        getLoaderManager().initLoader(LOADER_USER_CONTENT, null, this);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {

            case R.id.mi_add:
                Intent intent = new Intent(this, AddUserActivity.class);
                startActivity(intent);

                return true;


        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        Log.wtf("MainActivity", "onCreateLoader: ");
        return new CursorLoader(this, MyContract.USER_CONTENT_URI, null, null, null, null);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        Log.wtf("MainActivity", "onLoadFinished: ");
        cursorAdapter.swapCursor(data);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        cursorAdapter.swapCursor(null);
    }
}
