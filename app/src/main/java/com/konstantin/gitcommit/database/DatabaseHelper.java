package com.konstantin.gitcommit.database;

import android.content.Context;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DB_NAME = "gitcommit";
    private static final int DB_VERSION = 1;

    public static final String TABLE_NAME = "commits";
    public static final String ID_COLUMN = "_id";
    public static final String DATE_COLUMN = "date";
    public static final String SHA_COLUMN = "sha";
    public static final String HTML_URL_COLUMN = "html_url";
    public static final String AUTHOR_NAME_COLUMN = "author";
    public static final String COMMITER_NAME_COLUMN = "commiter";
    public static final String MESSAGE_COLUMN = "message";

    private static final String DB_CREATE = "CREATE TABLE " + TABLE_NAME + "( "
            + ID_COLUMN + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + DATE_COLUMN + " TEXT NOT NULL, "
            + SHA_COLUMN + " TEXT NOT NULL, "
            + HTML_URL_COLUMN + " TEXT NOT NULL, "
            + AUTHOR_NAME_COLUMN + " TEXT NOT NULL, "
            + COMMITER_NAME_COLUMN + " TEXT NOT NULL, "
            + MESSAGE_COLUMN + " TEXT NOT NULL);";

    public DatabaseHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    public DatabaseHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    public DatabaseHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version, DatabaseErrorHandler errorHandler) {
        super(context, name, factory, version, errorHandler);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(DB_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }
}
