package com.android.foodmark.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

public class AppSQLiteHelper extends SQLiteOpenHelper
{
    // if database schema is changed , version should be incremented
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "FoodMark.db";

	public AppSQLiteHelper(Context context)
	{
		super(context, DATABASE_NAME, null , DATABASE_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db) 
	{
		db.execSQL(FavoriteContract.CREATE_FAVORITE_TABLE);
		
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) 
	{
        //drop the existing table and recreate
		db.execSQL(FavoriteContract.DELETE_FAVORITE_TABLE);
        onCreate(db);
		
	}

}
