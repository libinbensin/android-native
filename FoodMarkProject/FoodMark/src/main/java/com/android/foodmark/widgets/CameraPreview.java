package com.android.foodmark.widgets;

import android.content.Context;
import android.hardware.Camera;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.io.IOException;

/**
 * Created by libin on 12/20/13.
 */
public final class CameraPreview extends SurfaceView implements SurfaceHolder.Callback
{
    private Camera mCamera = null;

    public CameraPreview(Context context)
    {
        super(context);
        getHolder().addCallback(this);
    }

    @Override
    public void surfaceCreated(SurfaceHolder surfaceHolder)
    {
        mCamera = getCameraInstance();

        if(mCamera == null)
        {
            return;
        }
        try
        {
            mCamera.setPreviewDisplay(surfaceHolder);

        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    @Override
    public void surfaceChanged(SurfaceHolder surfaceHolder, int i, int i2, int i3)
    {
        if(surfaceHolder.getSurface() == null)
        {
            return;
        }

        // stop preview before making changes
        try
        {
            mCamera.stopPreview();
        }
        catch (Exception e)
        {
            // ignore: tried to stop a non-existent preview
        }

        try
        {
            mCamera.setPreviewDisplay(surfaceHolder);
            mCamera.startPreview();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder surfaceHolder)
    {
        if(mCamera != null)
        {
            mCamera.release();
            mCamera = null;
        }
    }

    /**
     * create camera instance
     *
     * @return camera instance
     */
    private static Camera getCameraInstance()
    {
        Camera camera = null;

        try
        {
            camera = Camera.open();
        }
        catch (Exception e)
        {

        }
        return camera;
    }

    public Camera getCamera()
    {
        return mCamera;
    }

    public void takePicture(Camera.PictureCallback pictureCallback)
    {
        synchronized (this)
        {
            mCamera.takePicture(null,null,pictureCallback);
        }
    }


}
