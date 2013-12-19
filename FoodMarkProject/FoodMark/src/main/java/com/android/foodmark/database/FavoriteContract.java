package com.android.foodmark.database;

import android.provider.BaseColumns;

import java.security.PublicKey;

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
        public static final String COLUMN_NAME_ENTRY_ID ="FAVORITE_ID";
        public static final String COLUMN_NAME_TITLE = "TITLE";

    }

    private static final String CREATE_TABLE = "CREATE TABLE";
    private static final String DROP_TABLE = "DROP TABLE IF EXISTS";
    private static final String INTEGER_PRIMARY_KEY = "INTEGER PRIMARY KEY";
    public static final String TEXT_TYPE = " TEXT";
    public static final String COMMA_SEP = ",";
    public static final String SPACE = " ";
    public static final String OPEN_BRACKET = "(";
    public static final String CLOSE_BRACKET = ")";

    public static final String CREATE_FAVORITE_TABLE = CREATE_TABLE + SPACE +
            FavoriteEntry.TABLE_NAME + SPACE + OPEN_BRACKET + FavoriteEntry._ID + SPACE +
            INTEGER_PRIMARY_KEY + COMMA_SEP + FavoriteEntry.COLUMN_NAME_ENTRY_ID + TEXT_TYPE +
            COMMA_SEP + FavoriteEntry.COLUMN_NAME_TITLE + CLOSE_BRACKET;

    public static final String DELETE_FAVORITE_TABLE = DROP_TABLE + FavoriteEntry.TABLE_NAME;

}
