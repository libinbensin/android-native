package com.android.foodbook.database;

import android.provider.BaseColumns;

/**
 * Created by libin on 12/17/13.
 */
public final class FavoriteContract
{
    private FavoriteContract()
    {

    }

    public abstract static class FavoriteEntry implements BaseColumns
    {
        public static final String TABLE_NAME = "FAVORITE";
        public static final String COLUMN_ENTRY_ID ="FAVORITE_ID";

        public static final String COLUMN_TITLE = "TITLE";
        public static final String COLUMN_VICINITY = "VICINITY";
        public static final String COLUMN_RATING = "RATING";
        public static final String COLUMN_REFERENCE = "REFERENCE";
        public static final String COLUMN_IS_FAVORITE = "IS_FAVORITE";
        public static final String COLUMN_ICON_URL = "ICON_URL";
        public static final String COLUMN_LATITUDE = "LATITUDE";
        public static final String COLUMN_LONGITUDE = "LONGITUDE";
    }

    private static final String CREATE_TABLE = "CREATE TABLE";
    private static final String DROP_TABLE = "DROP TABLE IF EXISTS";
    private static final String INTEGER_PRIMARY_KEY = "INTEGER PRIMARY KEY";
    public static final String TEXT_TYPE = " TEXT";
    public static final String INTEGER_TYPE = " INTEGER";
    public static final String BLOB_TYPE = " BLOB";
    public static final String COMMA_SEP = ",";
    public static final String SPACE = " ";
    public static final String OPEN_BRACKET = "(";
    public static final String CLOSE_BRACKET = ")";
    public static final String UNIQUE = "UNIQUE";

    public static final String CREATE_FAVORITE_TABLE = CREATE_TABLE + SPACE +
            FavoriteEntry.TABLE_NAME + SPACE + OPEN_BRACKET + FavoriteEntry._ID + SPACE +
            INTEGER_PRIMARY_KEY + COMMA_SEP + FavoriteEntry.COLUMN_ENTRY_ID + TEXT_TYPE +
            COMMA_SEP + FavoriteEntry.COLUMN_TITLE + TEXT_TYPE +
            COMMA_SEP + FavoriteEntry.COLUMN_VICINITY  + TEXT_TYPE +
            COMMA_SEP + FavoriteEntry.COLUMN_RATING  + TEXT_TYPE +
            COMMA_SEP + FavoriteEntry.COLUMN_ICON_URL  + TEXT_TYPE +
            COMMA_SEP + FavoriteEntry.COLUMN_REFERENCE  + TEXT_TYPE + UNIQUE +
            COMMA_SEP + FavoriteEntry.COLUMN_LATITUDE  + TEXT_TYPE +
            COMMA_SEP + FavoriteEntry.COLUMN_LONGITUDE  + TEXT_TYPE +
            COMMA_SEP + FavoriteEntry.COLUMN_IS_FAVORITE + INTEGER_TYPE + SPACE +
            CLOSE_BRACKET;

    public static final String DELETE_FAVORITE_TABLE = DROP_TABLE + SPACE + FavoriteEntry.TABLE_NAME;

}
