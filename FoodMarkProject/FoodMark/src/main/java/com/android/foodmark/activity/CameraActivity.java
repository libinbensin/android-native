package com.android.foodmark.activity;

import android.hardware.Camera;
import android.media.CamcorderProfile;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.os.Environment;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.FrameLayout;

import com.android.foodmark.R;
import com.android.foodmark.constants.AppConstant;
import com.android.foodmark.widgets.CameraPreview;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by libin on 12/20/13.
 */
public class CameraActivity extends BaseActivity implements Camera.PictureCallback
{
    private Menu mMenu = null;

    private CameraPreview mCameraPreview = null;

    private MediaRecorder mMediaRecorder = null;

    private static int mediaCaptureType;

    private boolean isRecording = false;

    @Override
    public void onCreate(Bundle onSavedInstance)
    {
        super.onCreate(onSavedInstance);
        setContentView(R.layout.camera_preview_layout);
        mediaCaptureType =  getIntent().getExtras().getInt("MEDIA_TYPE");

        mCameraPreview = new CameraPreview(this);

        FrameLayout frameLayout = (FrameLayout) findViewById(R.id.device_camera_preview);
        frameLayout.addView(mCameraPreview);
    }


    @Override
    public void onPause()
    {
        super.onPause();
        releaseMediaRecorder();
    }



    /**
     * create a file to save the captured media
     * @return
     */
    private static File getOutputMediaFile(int type)
    {
        File mediaStorageDir = new File(Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_PICTURES),"FoodMark");

        if(!mediaStorageDir.exists())
        {
            if(!mediaStorageDir.mkdir())
            {
                //TODO : handle failure
                return null;
            }
        }

        // create a media file name
        String timeStamp  = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        File mediaFile;

        if(type == AppConstant.MEDIA_TYPE_IMAGE)
        {
            mediaFile = new File(mediaStorageDir.getPath() + File.separator
                + "IMG_" + timeStamp + ".jpg");
        }
        else if(type == AppConstant.MEDIA_TYPE_VIDEO)
        {
            mediaFile = new File(mediaStorageDir.getPath() + File.separator
                + "VID_" + timeStamp + ".mp4");
        }
        else
        {
            // not supported media type
            return null;
        }
        return  mediaFile;
    }

    @Override
    public void onPictureTaken(byte[] bytes, Camera camera)
    {
        isRecording = false;
        File pictureFile = getOutputMediaFile(mediaCaptureType);

        if(pictureFile == null)
        {
            // TODO : Handle failure
            return;
        }

        try
        {
            FileOutputStream outputStream = new FileOutputStream(pictureFile);
            outputStream.write(bytes);
            outputStream.close();
        }
        catch (FileNotFoundException e)
        {
            e.printStackTrace();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }

        // exit the activity once captured
        finish();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.action_camera, menu);
        this.mMenu = menu;
        if(mediaCaptureType == AppConstant.MEDIA_TYPE_VIDEO)
        {
            setCaptureMenuText(getResources().getString(R.string.camera_start));
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch (item.getItemId())
        {
            case R.id.menu_capture:
                if(mediaCaptureType == AppConstant.MEDIA_TYPE_IMAGE)
                {
                    mCameraPreview.takePicture(this);
                }
                else
                {
                    synchronized (this)
                    {
                        captureVideo();
                    }
                }
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void captureVideo()
    {
        if(isRecording)
        {
            isRecording = false;
            try
            {
                mMediaRecorder.stop();
            }
            catch (Exception e)
            {
                //TODO : cleanup
                /**
                 * Note that a RuntimeException is intentionally thrown to the application,
                 * if no valid audio/video data has been received when stop() is called.
                 * This happens if stop() is called immediately after start().
                 * The failure lets the application take action accordingly to clean up the
                 * output file (delete the output file, for instance), since the output file is
                 * not properly constructed when this happens.
                 */
            }
            releaseMediaRecorder();
            mCameraPreview.getCamera().lock();
            // exit the activity once recorded
            finish();
        }
        else
        {
            if(prepareVideoRecorder())
            {
                mMediaRecorder.start();

                //set the capture menu as stop
                setCaptureMenuText(getResources().getString(R.string.camera_stop));
                isRecording = true;
            }
            else
            {
                releaseMediaRecorder();
            }
        }
    }

    private void setCaptureMenuText(String s)
    {
       MenuItem menuItem = this.mMenu.findItem(R.id.menu_capture);

       menuItem.setTitle(s);
    }

    private void releaseMediaRecorder()
    {
        if(mMediaRecorder != null)
        {
            mMediaRecorder.reset();
            mMediaRecorder.release();
            mMediaRecorder = null;
            mCameraPreview.getCamera().lock();

            isRecording = false;
            // reset the menu title
            setCaptureMenuText(getResources().getString(R.string.camera_start));
        }
    }

    private boolean prepareVideoRecorder()
    {
        mMediaRecorder = new MediaRecorder();

        mCameraPreview.getCamera().unlock();
        mMediaRecorder.setCamera(mCameraPreview.getCamera());

        mMediaRecorder.setAudioSource(MediaRecorder.AudioSource.CAMCORDER);
        mMediaRecorder.setVideoSource(MediaRecorder.VideoSource.CAMERA);

        mMediaRecorder.setProfile(CamcorderProfile.get(CamcorderProfile.QUALITY_HIGH));

        mMediaRecorder.setOutputFile(getOutputMediaFile(AppConstant.MEDIA_TYPE_VIDEO).toString());
        mMediaRecorder.setPreviewDisplay(mCameraPreview.getHolder().getSurface());

        try
        {
            mMediaRecorder.prepare();
        }
        catch (IllegalStateException e)
        {
           releaseMediaRecorder();
            return false;
        }
        catch (IOException e)
        {
            releaseMediaRecorder();
            return false;
        }
        return true;
    }

}
