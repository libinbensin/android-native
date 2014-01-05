package com.android.foodmark.database;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.android.foodmark.MainApplication;
import com.android.foodmark.database.FavoriteContract.FavoriteEntry;
import com.android.foodmark.model.Geometry;
import com.android.foodmark.model.PlaceList;
import com.android.foodmark.security.AppSecurity;
import com.android.foodmark.utils.AppLocationUtil;
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

    public static long add(SQLiteDatabase db, PlaceList placeList)
    {
        ContentValues contentValues = new ContentValues();

        contentValues.put(FavoriteEntry.COLUMN_TITLE, placeList.getDescription());
        contentValues.put(FavoriteEntry.COLUMN_VICINITY, placeList.getVicinity());
        contentValues.put(FavoriteEntry.COLUMN_RATING, placeList.getRating());
        contentValues.put(FavoriteEntry.COLUMN_REFERENCE, placeList.getReference());
        contentValues.put(FavoriteEntry.COLUMN_IS_FAVORITE , placeList.isFavorite() ? 1 : 0);
        contentValues.put(FavoriteEntry.COLUMN_ICON_URL , placeList.getIcon());
        if(placeList.getGeometry() != null)
        {
            Geometry.AppLocation appLocation = placeList.getGeometry().getLocation();
            if(appLocation != null)
            {
                contentValues.put(FavoriteEntry.COLUMN_LATITUDE , appLocation.getLat());
                contentValues.put(FavoriteEntry.COLUMN_LONGITUDE , appLocation.getLng());
            }
        }

       return db.insert(FavoriteContract.FavoriteEntry.TABLE_NAME , null , contentValues);
    }

    public static void update(SQLiteDatabase db, PlaceList placeList)
    {
        ContentValues contentValues = new ContentValues();
        String whereClause = FavoriteEntry.COLUMN_REFERENCE + "= ? ";
        String[] whereArgs = new String[]{placeList.getReference()};

        contentValues.put(FavoriteEntry.COLUMN_IS_FAVORITE , placeList.isFavorite() ? 1 : 0);

        db.update(FavoriteEntry.TABLE_NAME ,contentValues,whereClause,whereArgs);
    }

    public static void remove(SQLiteDatabase db, PlaceList placeList)
    {
        String whereClause = FavoriteEntry.COLUMN_REFERENCE + "= ? ";
        String[] whereArgs = new String[]{placeList.getReference()};

        db.delete(FavoriteContract.FavoriteEntry.TABLE_NAME, whereClause, whereArgs);
    }

    public static SortedMap<Double,PlaceList> getAll(SQLiteDatabase db)
    {
        SortedMap<Double,PlaceList> placeHashMap = new TreeMap<Double,PlaceList>();
        String[] queryList = {
                FavoriteEntry.COLUMN_TITLE ,
                FavoriteEntry.COLUMN_VICINITY ,
                FavoriteEntry.COLUMN_RATING ,
                FavoriteEntry.COLUMN_REFERENCE,
                FavoriteEntry.COLUMN_LATITUDE ,
                FavoriteEntry.COLUMN_LONGITUDE ,
                FavoriteEntry.COLUMN_IS_FAVORITE,
                FavoriteEntry.COLUMN_ICON_URL};

        Cursor cursor = db.query(FavoriteEntry.TABLE_NAME, queryList,null,null,null,null,null);

        if(cursor.moveToFirst())
        {
            do
            {
                PlaceList placeList = new PlaceList();
                int index = 0;

                index = cursor.getColumnIndex(FavoriteEntry.COLUMN_TITLE);
                placeList.setDescription(cursor.getString(index));
                index = cursor.getColumnIndex(FavoriteEntry.COLUMN_VICINITY);
                placeList.setVicinity(cursor.getString(index));
                int latIndex = cursor.getColumnIndex(FavoriteEntry.COLUMN_LATITUDE);
                int lngIndex = cursor.getColumnIndex(FavoriteEntry.COLUMN_LONGITUDE);
                placeList.setDistance(AppLocationUtil.getDistance(
                        cursor.getString(latIndex),cursor.getString(lngIndex)));
                index = cursor.getColumnIndex(FavoriteEntry.COLUMN_RATING);
                placeList.setRating(cursor.getString(index));
                index = cursor.getColumnIndex(FavoriteEntry.COLUMN_REFERENCE);
                placeList.setReference(cursor.getString(index));
                index = cursor.getColumnIndex(FavoriteEntry.COLUMN_IS_FAVORITE);
                placeList.setFavorite( (cursor.getInt(index) == 1 ) ? true : false);
                index = cursor.getColumnIndex(FavoriteEntry.COLUMN_ICON_URL);
                placeList.setIcon(cursor.getString(index));

                placeHashMap.put(Double.valueOf(placeList.getDistance()), placeList);
            }
            while (cursor.moveToNext());

        }
        if(cursor != null)
        {
            cursor.close();
        }
        return placeHashMap;
    }

    private static void addAll(SQLiteDatabase db , List<PlaceList> argPlaceList) {

        for(PlaceList placeList : argPlaceList)
        {
            add(db, placeList);
        }
    }

    public static boolean isFavorite(SQLiteDatabase db , PlaceList placeList)
    {
        String[] selectionArgs = new String[]{FavoriteEntry._ID,FavoriteEntry.COLUMN_REFERENCE};

        // check if row exists
        Cursor cursor = db.query(
                FavoriteEntry.TABLE_NAME,
                selectionArgs,
                FavoriteEntry.COLUMN_REFERENCE + " = '" +
                placeList.getReference()+"'",
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

    public static void setFavorite(SQLiteDatabase db , PlaceList placeList)
    {
        boolean exists = false;

        // check if row exists
        if(isFavorite(db, placeList))
        {
            exists = true;
        }

        if(exists)
        {
            if(placeList.isFavorite())
            {
                update(db, placeList);
            }
            else
            {
                remove(db, placeList);
            }
        }
        else
        {
            add(db, placeList);
        }
    }

    private static byte[] encryptedByte(String s)
    {
        return AppSecurity.encrypt(MainApplication.getSecretKey(),AppUtil.toBytes(s));
    }

    private static String decryptedString(byte[] blob)
    {
        byte[] decrypted = AppSecurity.decrypt(MainApplication.getSecretKey(),blob);
        return AppUtil.toString(decrypted);
    }


}
