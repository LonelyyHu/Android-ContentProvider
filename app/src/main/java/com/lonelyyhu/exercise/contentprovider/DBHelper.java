package com.lonelyyhu.exercise.contentprovider;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.lonelyyhu.exercise.contentprovider.MyContract.UserColumns;

/**
 * Created by hulonelyy on 2017/11/18.
 */

public class DBHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "MY_DB";
    private static final int DATABASE_VERSION = 1;

    private static final String SQL_CREATE_TABLE_USER = String.format("CREATE TABLE %s"
                    +" (%s INTEGER PRIMARY KEY AUTOINCREMENT, %s TEXT, %s INTEGER, %s TEXT)",
            MyContract.USER_CONTENT,
            UserColumns._ID,
            UserColumns.NAME,
            UserColumns.AGE,
            UserColumns.PHONE
    );


    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase database) {

        database.execSQL(SQL_CREATE_TABLE_USER);
        insertDemoUser(database);

    }

    private void insertDemoUser(SQLiteDatabase database) {

        ContentValues values = new ContentValues();
        values.put(UserColumns.NAME, "Lonelyy");
        values.put(UserColumns.AGE, 35);
        values.put(UserColumns.PHONE, "0912345678");

        database.insert(MyContract.USER_CONTENT, null, values);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("DROP TABLE IF EXISTS " + MyContract.USER_CONTENT);
        onCreate(db);

    }
}
