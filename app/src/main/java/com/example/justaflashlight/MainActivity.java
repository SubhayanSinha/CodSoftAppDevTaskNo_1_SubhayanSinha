package com.example.justaflashlight;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.pm.PackageManager;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraManager;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private ImageButton imageButton ;
    boolean flashcheck = false ;
    boolean turnon = false ;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        imageButton = findViewById(R.id.ImageButton) ;

        flashcheck = getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA_FLASH) ;

        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if( !flashcheck )
                    Toast.makeText(MainActivity.this, "No Camaera Flash Detected, Sorry!", Toast.LENGTH_SHORT).show();
                else
                    if(turnon == false) // if the flash is off
                    {
                        turnon = true ;
                        imageButton.setImageResource(R.drawable.poweron);
                        try {
                            TurnItOn() ;
                        } catch (CameraAccessException e) {
                            throw new RuntimeException(e);
                        }
                    }
                    else // flash is on
                    {
                        turnon = false ;
                        imageButton.setImageResource(R.drawable.poweroff);
                        try {
                            TurnItOff() ;
                        } catch (CameraAccessException e) {
                            throw new RuntimeException(e);
                        }
                    }
            }
        });
    }

    private void TurnItOn() throws CameraAccessException
    {
        CameraManager cameraManager = (CameraManager) getSystemService(Context.CAMERA_SERVICE) ;
        String cameraId = cameraManager.getCameraIdList()[0] ;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            cameraManager.setTorchMode(cameraId, true);
        }
    }

    private void TurnItOff() throws CameraAccessException
    {
        CameraManager cameraManager = (CameraManager) getSystemService(Context.CAMERA_SERVICE);
        String cameraId = cameraManager.getCameraIdList()[0];
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            cameraManager.setTorchMode(cameraId, false);
        }
    }

}