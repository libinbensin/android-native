package com.android.foodmark.database;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by libin_000 on 12/17/13.
 */
public final class FavoriteExecutor
{
    private FavoriteExecutor()
    {

    }

    public static void add(SQLiteDatabase db, String title)
    {
        ContentValues contentValues = new ContentValues();

        contentValues.put(FavoriteContract.FavoriteEntry.COLUMN_NAME_TITLE,title);

        db.insert(FavoriteContract.FavoriteEntry.TABLE_NAME , null , contentValues);
    }

    public static void remove(SQLiteDatabase db, String title)
    {
        db.delete(FavoriteContract.FavoriteEntry.TABLE_NAME ,
                FavoriteContract.FavoriteEntry.COLUMN_NAME_TITLE + "=" + title ,null);

    }
}
