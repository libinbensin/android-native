package com.android.foodmark.model;

import android.graphics.Bitmap;

/**
 * Created by libin on 12/19/13.
 */
public class VideoDetail
{
    private long id;

    private String title;

    private Bitmap thumbnail;

    public long getId()
    {
        return id;
    }

    public void setId(long id)
    {
        this.id = id;
    }

    public String getTitle()
    {
        return title;
    }

    public void setTitle(String title)
    {
        this.title = title;
    }

    public Bitmap getThumbnail()
    {
        return thumbnail;
    }

    public void setThumbnail(Bitmap thumbnail)
    {
        this.thumbnail = thumbnail;
    }
}
