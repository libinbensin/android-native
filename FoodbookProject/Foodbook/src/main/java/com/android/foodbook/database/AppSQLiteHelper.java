package com.android.foodbook.database;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
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
        try
        {
            String s = FavoriteContract.CREATE_FAVORITE_TABLE;
		    db.execSQL(s);
        }
        catch (SQLException e)
        {
            // handle failure
        }
        catch (Exception e)
        {
            // handle failure
        }
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) 
	{
        //drop the existing table and recreate
		db.execSQL(FavoriteContract.DELETE_FAVORITE_TABLE);
        onCreate(db);
		
	}

}
