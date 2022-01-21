package com.example.torch;

import android.annotation.SuppressLint;
import android.content.Context;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Vibrator;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;
import android.widget.ToggleButton;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    ToggleButton toggleButton;
    ImageView imageView;
    Vibrator vibe;
    private CameraManager objCameraManager;

    private String mCameraId;

    private Boolean isTorchOn;

    @SuppressLint("UseCompatLoadingForDrawables")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toggleButton = findViewById(R.id.tb2);
        toggleButton.setOnClickListener(this);
        isTorchOn = false;
        imageView = findViewById(R.id.imageView);
        imageView.setImageDrawable(getResources().getDrawable(R.drawable.light_off));
        vibe = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
/*
  Check if device contains flashlight

  if not then exit from screen

 */
/*
        Boolean isFlashAvailable = getApplicationContext().getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA_FLASH);
        if (!isFlashAvailable) {
            AlertDialog alert = new AlertDialog.Builder(MainActivity.this).create();
            alert.setTitle(getString(R.string.app_name));
            alert.setMessage(getString(R.string.msg_error));
            alert.setButton(DialogInterface.BUTTON_POSITIVE, getString(R.string.lbl_ok), new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    finish();
                }
            });
            alert.show();
            return;
        }
 AlertDialog alert = new AlertDialog.Builder(MainActivity.this).create();

                alert.setTitle(getString(R.string.app_name));
                alert.setMessage(getString(R.string.msg_error));
                alert.setButton(DialogInterface.BUTTON_POSITIVE, getString(R.string.lbl_ok), new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText ( getApplicationContext (), "this is inside toast", Toast.LENGTH_SHORT ).show ();
                       // finish();
                    }
                });
                alert.show();

 */
        objCameraManager = (CameraManager) getSystemService(Context.CAMERA_SERVICE);
        try {
            mCameraId = objCameraManager.getCameraIdList()[0];
        } catch (CameraAccessException e) {
            e.printStackTrace();
        }
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    @Override
    public void onClick(View view) {

        try {
            if (toggleButton.isChecked()) {
                //
                imageView.setImageDrawable(getResources().getDrawable(R.drawable.light_on));
                turnOnLight();
                vibe.vibrate(100);
                Toast.makeText(this, "The torch is turned on", Toast.LENGTH_SHORT).show();
                isTorchOn = true;
            } else {
                imageView.setImageDrawable(getResources().getDrawable(R.drawable.light_off));
                vibe.vibrate(100);
                turnOffLight();
                Toast.makeText(this, "The torch is turned off", Toast.LENGTH_SHORT).show();
                isTorchOn = false;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void turnOnLight() {
        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                objCameraManager.setTorchMode(mCameraId, true);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Method for turning light OFF
     */
    public void turnOffLight() {
        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                objCameraManager.setTorchMode(mCameraId, false);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @Override
    protected void onStop() {
        super.onStop();
        if (isTorchOn) {
            turnOffLight();
        }
    }
    @Override
    protected void onPause() {
        super.onPause();
        if (isTorchOn) {
            turnOffLight();
        }
    }
    @Override
    protected void onResume() {
        super.onResume();
        if (isTorchOn) {
            turnOnLight();
        }
    }


}