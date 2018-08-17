package com.theta360.pluginapplication;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.graphics.ImageFormat;
import android.hardware.Camera;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.KeyEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import com.theta360.pluginlibrary.activity.PluginActivity;
import com.theta360.pluginlibrary.callback.KeyCallback;
import com.theta360.pluginlibrary.receiver.KeyReceiver;
import com.theta360.pluginlibrary.values.LedColor;
import com.theta360.pluginlibrary.values.LedTarget;

import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import static java.lang.Thread.sleep;

public class MainActivity extends PluginActivity implements SurfaceHolder.Callback {
    private Camera mCamera = null;
    private Context mcontext;
    private int bcnt = 0; //bracketing count
    private int exposureCompensationValue = -6;  // can be -6 to +6
    private int numberOfImages = 7;
    private boolean m_is_bracket = false;

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.camera_main);
        mcontext = this;
        SurfaceView preview = (SurfaceView)findViewById(R.id.preview_id);
        SurfaceHolder holder = preview.getHolder();
        holder.addCallback(this);
        setKeyCallback(new KeyCallback() {
            @Override
            public void onKeyDown(int keyCode, KeyEvent event) {
                if (keyCode == KeyReceiver.KEYCODE_CAMERA) {
                    customShutter(m_is_bracket);
                    if(m_is_bracket){
                        notificationLedBlink(LedTarget.LED3, LedColor.MAGENTA, 300);
                    }
                    else {
                        notificationLedBlink(LedTarget.LED3, LedColor.CYAN, 2000);
                    }
                }
                else if(keyCode == KeyReceiver.KEYCODE_WLAN_ON_OFF){
                    m_is_bracket = !m_is_bracket;
                    if(m_is_bracket){
                        notificationLedBlink(LedTarget.LED3, LedColor.MAGENTA, 300);
                    }
                    else {
                        notificationLedBlink(LedTarget.LED3, LedColor.CYAN, 2000);
                    }
                }
            }

            @Override
            public void onKeyUp(int keyCode, KeyEvent event) {
                /**
                 * You can control the LED of the camera.
                 * It is possible to change the way of lighting, the cycle of blinking, the color of light emission.
                 * Light emitting color can be changed only LED3.
                 */
            }

            @Override
            public void onKeyLongPress(int keyCode, KeyEvent event) {
                notificationError("");
            }
        });

    }

    @Override
    public void onResume() {
        super.onResume();
        if(m_is_bracket){
            notificationLedBlink(LedTarget.LED3, LedColor.MAGENTA, 300);
        }
        else {
            notificationLedBlink(LedTarget.LED3, LedColor.CYAN, 2000);
        }
    }

    public void onPause() {
        super.onPause();
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        // TODO Auto-generated method stub
        Log.d("camera" , "opened");
        Intent intent = new Intent("com.theta360.plugin.ACTION_MAIN_CAMERA_CLOSE");
        sendBroadcast(intent);
        mCamera = Camera.open();
        try {
            mCamera.setPreviewDisplay(holder);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
        // TODO Auto-generated method stub
        mCamera.stopPreview();
        Camera.Parameters params = mCamera.getParameters();
        params.set("RIC_SHOOTING_MODE", "RicMonitoring");

        List<Camera.Size> previewSizes = params.getSupportedPreviewSizes();
        Camera.Size size = previewSizes.get(0);
        for(int i = 0; i < previewSizes.size(); i++) {
            size = previewSizes.get(i);
            Log.d("preview", "size = " + size.width + "x" + size.height);
        }
        params.setPreviewSize(size.width, size.height);
        mCamera.setParameters(params);
        mCamera.startPreview();
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        // TODO Auto-generated method stub
        Log.d("camera" , "closed");
        notificationLedHide(LedTarget.LED3);
        mCamera.stopPreview();
        mCamera.release();
        mCamera = null;
        Intent intent = new Intent("com.theta360.plugin.ACTION_MAIN_CAMERA_OPEN");
        sendBroadcast(intent);
    }

    private void customShutter(boolean is_bracket){
        Intent intent = new Intent("com.theta360.plugin.ACTION_AUDIO_SH_OPEN");
        sendBroadcast(intent);

        Camera.Parameters params = mCamera.getParameters();
        //Log.d("shooting mode", params.flatten());
        params.set("RIC_SHOOTING_MODE", "RicStillCaptureStd");

        params.set("RIC_PROC_STITCHING", "RicNonStitching");
        params.setPictureFormat(ImageFormat.JPEG);
        params.setPictureSize(5792, 2896);

        //iso = 64 fix & 3 shots (+-2EV step)
        if(is_bracket) {
            // RIC_EXPOSURE_MODE can't be set to RicAutoExposureS
            // for exposure compensation adjustment to take effect
            // https://api.ricoh/docs/theta-plugin-reference/camera-api/
            // Sv or Sensitivity value appears to be specific to Ricoh/Pentax cameras
            // Tv appears to indicate shutter priority
            params.set("RIC_EXPOSURE_MODE", "RicAutoExposureP");
            params.set("RIC_MANUAL_EXPOSURE_ISO_FRONT", 1);
            params.set("RIC_MANUAL_EXPOSURE_ISO_BACK", 1);
            //shutter speed based bracket (+-2EV)
            // set initial exposure compensation to -6;
            params.setExposureCompensation(exposureCompensationValue);

            bcnt = numberOfImages;
            exposureCompensationValue = -6;
            bcnt = bcnt -1;
        }
        //iso = auto & 1 shot
        else{
            params.set("RIC_EXPOSURE_MODE", "RicAutoExposureP");
            params.set("RIC_MANUAL_EXPOSURE_ISO_FRONT", -1);
            params.set("RIC_MANUAL_EXPOSURE_ISO_BACK", -1);
            exposureCompensationValue = 0;
            params.setExposureCompensation(exposureCompensationValue);
            bcnt = 0;
        }

        mCamera.setParameters(params);
        params = mCamera.getParameters();
        Log.d("shooting mode", params.flatten()); //debug
        //3sec delay timer
        try{
            sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        intent = new Intent("com.theta360.plugin.ACTION_AUDIO_SHUTTER");
        sendBroadcast(intent);
        mCamera.takePicture(null,null, null, pictureListener);
    }

    private void nextShutter(){
        //restart preview
        Camera.Parameters params = mCamera.getParameters();
        params.set("RIC_SHOOTING_MODE", "RicMonitoring");
        mCamera.setParameters(params);
        mCamera.startPreview();

        //shutter speed based bracket
        if(bcnt > 0) {
            params = mCamera.getParameters();
            params.set("RIC_SHOOTING_MODE", "RicStillCaptureStd");
            exposureCompensationValue = exposureCompensationValue + 2;
            params.setExposureCompensation(exposureCompensationValue);

            bcnt = bcnt - 1;
            mCamera.setParameters(params);
            Intent intent = new Intent("com.theta360.plugin.ACTION_AUDIO_SHUTTER");
            sendBroadcast(intent);
            mCamera.takePicture(null, null, null, pictureListener);
        }
        else{
            // reset exposureCompensationValue
            exposureCompensationValue = -6;
            Intent intent = new Intent("com.theta360.plugin.ACTION_AUDIO_SH_CLOSE");
            sendBroadcast(intent);
        }

    }


    private Camera.PictureCallback pictureListener = new Camera.PictureCallback() {
        @Override
        public void onPictureTaken(byte[] data, Camera camera) {
            //save image to storage
            Log.d("onpicturetaken", "called");
            if (data != null) {
                FileOutputStream fos = null;
                try {
                    String tname = getNowDate();
                    String opath = Environment.getExternalStorageDirectory().getPath()+ "/DCIM/100RICOH/" + "FISHEYE" +tname+".JPG";
                    Log.d("save", opath);
                    fos = new FileOutputStream(opath);
                    fos.write(data);
                    fos.close();
                    registImage(tname, opath, mcontext, "image/jpeg");
                } catch (Exception e) {
                    e.printStackTrace();
                }
                nextShutter();
            }
        }
    };
    private static String getNowDate(){
        final DateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");
        final Date date = new Date(System.currentTimeMillis());
        return df.format(date);
    }

    private static void registImage(String fileName, String filePath, Context mcontext, String mimetype) {
        ContentValues values = new ContentValues();
        ContentResolver contentResolver = mcontext.getContentResolver();
        //"image/jpeg"
        values.put(MediaStore.Images.Media.MIME_TYPE, mimetype);
        values.put(MediaStore.Images.Media.TITLE, fileName);
        values.put("_data", filePath);
        contentResolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
    }


}



