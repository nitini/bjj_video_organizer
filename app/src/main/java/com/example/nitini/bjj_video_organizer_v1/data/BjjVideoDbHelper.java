package com.example.nitini.bjj_video_organizer_v1.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by nitini on 7/16/17.
 */

public class BjjVideoDbHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "bjjVideosDb.db";
    private static final int VERSION = 1;

    BjjVideoDbHelper(Context context) { super(context, DATABASE_NAME, null, VERSION); }

    @Override
    public void onCreate(SQLiteDatabase db) {
        final String CREATE_TABLE = "CREATE TABLE " + BjjVideoContract.VideoEntry.TABLE_NAME + " (" +
                BjjVideoContract.VideoEntry._ID + " INTEGER PRIMARY KEY, " +
                BjjVideoContract.VideoEntry.COLUMN_TITLE + " TEXT NOT NULL, " +
                BjjVideoContract.VideoEntry.COLUMN_LINK + " TEXT NOT NULL);";

        db.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //FINISH THE BELOW LINE WITH ACTUAL TABLE NAME
        db.execSQL("DROP TABLE IF EXISTS " + BjjVideoContract.VideoEntry.TABLE_NAME);
        onCreate(db);

    }
}
