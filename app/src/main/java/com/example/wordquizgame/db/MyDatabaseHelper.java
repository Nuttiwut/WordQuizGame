package com.example.wordquizgame.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.nfc.Tag;
import android.util.Log;

public class MyDatabaseHelper extends SQLiteOpenHelper {

    private static final String DB_NAME = "wordquizgame.db";
    private static final int DB_VERSION = 1;

    public static final String TABLE_NAME_WORD = "word";
    public static final String COL_ID = "_id";
    public static final String COL_NAME = "_name";
    public static final String COL_PICTURE = "_picture";

    private static final String SQL_CREATE_TABLE_WORD = "CREATE TABLE " + TABLE_NAME_WORD + "("
            + COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + COL_NAME + " TEXT, "
            + COL_PICTURE + " TEXT "
            + ")";

    private Context mContext;

    public MyDatabaseHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
        this.mContext = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_TABLE_WORD);

        String[] names = new String[]{"Rabbit", "Dog", "Lion", "Dolphin", "Koala", "Pig", "Tiger", "Cat"};
        String[] picture = {"rabbit.png", "dog.png", "lion.png", "dolphin.png", "koala.png", "pig.png", "tiger.png", "cat.png"};

        ContentValues cv ;
        for (int i = 0;i < names.length; i++){
            cv = new ContentValues();
            cv.put(COL_NAME,names[i]);
            cv.put(COL_PICTURE, picture[i]);
            db.insert(TABLE_NAME_WORD, null, cv);
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {

    }
}
