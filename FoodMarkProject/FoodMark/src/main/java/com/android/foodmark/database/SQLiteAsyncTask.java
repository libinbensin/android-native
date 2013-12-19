package com.android.foodmark.database;

import android.content.Context;
import android.support.v4.content.AsyncTaskLoader;

/**
 * Created by libin on 12/17/13.
 */
public class SQLiteAsyncTask extends AsyncTaskLoader
{
    private AppSQLiteHelper appSQLiteHelper = null;

    public SQLiteAsyncTask(Context context)
    {
        super(context);
        appSQLiteHelper = new AppSQLiteHelper(context);
    }

    @Override
    public Object loadInBackground()
    {
        return appSQLiteHelper.getWritableDatabase();
    }
}
