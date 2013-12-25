package com.android.foodmark.database;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.android.foodmark.database.FavoriteContract.FavoriteEntry;
import com.android.foodmark.model.GooglePlace;
import com.android.foodmark.utils.AppUtil;

import java.util.List;
import java.util.SortedMap;
import java.util.TreeMap;

/**
 * Created by libin on 12/17/13.
 */
public final class FavoriteExecutor
{
    private FavoriteExecutor()
    {

    }

    public static long add(SQLiteDatabase db, GooglePlace googlePlace)
    {
        ContentValues contentValues = new ContentValues();
        long result = 0;
        contentValues.put(FavoriteEntry.COLUMN_TITLE, googlePlace.getDescription());
        contentValues.put(FavoriteEntry.COLUMN_VICINITY, googlePlace.getVicinity());
        contentValues.put(FavoriteEntry.COLUMN_DISTANCE, googlePlace.getDistance());
        contentValues.put(FavoriteEntry.COLUMN_RATING, googlePlace.getRating());
        contentValues.put(FavoriteEntry.COLUMN_REFERENCE, googlePlace.getReference());
        contentValues.put(FavoriteEntry.COLUMN_IS_FAVORITE ,googlePlace.isFavorite() ? 1 : 0);

       result =  db.insert(FavoriteContract.FavoriteEntry.TABLE_NAME , null , contentValues);

       return result;
    }

    public static void update(SQLiteDatabase db, GooglePlace googlePlace)
    {
        ContentValues contentValues = new ContentValues();
        long result = 0;
        String whereClause = FavoriteEntry.COLUMN_REFERENCE + "= ? ";
        String[] whereArgs = new String[]{googlePlace.getReference()};
        contentValues.put(FavoriteEntry.COLUMN_IS_FAVORITE ,googlePlace.isFavorite() ? 1 : 0);
        result = db.update(FavoriteEntry.TABLE_NAME ,contentValues,whereClause,whereArgs);
    }

    public static void remove(SQLiteDatabase db, GooglePlace googlePlace)
    {
        String whereClause = FavoriteEntry.COLUMN_REFERENCE + "= ? ";
        String[] whereArgs = new String[]{googlePlace.getReference()};

        db.delete(FavoriteContract.FavoriteEntry.TABLE_NAME, whereClause, whereArgs);
    }

    public static SortedMap<Double,GooglePlace> getAll(SQLiteDatabase db)
    {
        SortedMap<Double,GooglePlace> placeHashMap = new TreeMap<Double,GooglePlace>();

        String[] queryList = {
                FavoriteEntry.COLUMN_TITLE ,
                FavoriteEntry.COLUMN_VICINITY ,
                FavoriteEntry.COLUMN_DISTANCE ,
                FavoriteEntry.COLUMN_RATING ,
                FavoriteEntry.COLUMN_REFERENCE,
                FavoriteEntry.COLUMN_IS_FAVORITE};

        Cursor cursor = db.query(FavoriteEntry.TABLE_NAME, queryList,null,null,null,null,null);

        if(cursor.moveToFirst())
        {
            do
            {
                GooglePlace googlePlace = new GooglePlace();
                int index = cursor.getColumnIndex(FavoriteEntry.COLUMN_TITLE);
                googlePlace.setDescription(cursor.getString(index));
                index = cursor.getColumnIndex(FavoriteEntry.COLUMN_VICINITY);
                googlePlace.setVicinity(cursor.getString(index));
                index = cursor.getColumnIndex(FavoriteEntry.COLUMN_DISTANCE);
                googlePlace.setDistance(cursor.getString(index));
                index = cursor.getColumnIndex(FavoriteEntry.COLUMN_RATING);
                googlePlace.setRating(cursor.getString(index));
                index = cursor.getColumnIndex(FavoriteEntry.COLUMN_REFERENCE);
                googlePlace.setReference(cursor.getString(index));
                index = cursor.getColumnIndex(FavoriteEntry.COLUMN_IS_FAVORITE);
                googlePlace.setFavorite( (cursor.getInt(index) == 1 ) ? true : false);
                placeHashMap.put(Double.valueOf(googlePlace.getDistance()),googlePlace);
            }
            while (cursor.moveToNext());

        }
        if(cursor != null)
        {
            cursor.close();
        }
        return placeHashMap;
    }


    private static void addAll(SQLiteDatabase db , List<GooglePlace> argGooglePlace) {

        for(GooglePlace googlePlace : argGooglePlace)
        {
            add(db,googlePlace);
        }
    }

    public static boolean isFavorite(SQLiteDatabase db , GooglePlace googlePlace)
    {
        String[] selectionArgs = new String[]{FavoriteEntry._ID,FavoriteEntry.COLUMN_REFERENCE};

        // check if row exists
        Cursor cursor = db.query(
                FavoriteEntry.TABLE_NAME,
                selectionArgs,
                FavoriteEntry.COLUMN_REFERENCE + " = '" +
                googlePlace.getReference()+"'",
                null,null,null,null);

        if(cursor != null && cursor.getCount() > 0)
        {
            return true;
        }
        else
        {
            return false;
        }
    }

    public static void setFavorite(SQLiteDatabase db , GooglePlace googlePlace)
    {
        boolean exists = false;

        // check if row exists
        if(isFavorite(db,googlePlace))
        {
            exists = true;
        }

        if(exists)
        {
            if(googlePlace.isFavorite())
            {
                update(db,googlePlace);
            }
            else
            {
                remove(db,googlePlace);
            }
        }
        else
        {
            add(db,googlePlace);
        }
    }
}
