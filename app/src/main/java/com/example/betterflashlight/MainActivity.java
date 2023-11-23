package com.example.betterflashlight;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.content.pm.PackageManager;
import android.graphics.Camera;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraManager;
import android.os.Build;
import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    boolean flashlightOn = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ImageButton button = findViewById(R.id.toggleFlashlightButton);
        button.setOnClickListener((view) -> {
            flashlightOn = !flashlightOn;
            if (flashlightOn) {
                button.setImageResource(R.drawable.outline_flashlight_on_24);
            } else {
                button.setImageResource(R.drawable.outline_flashlight_off_24);
            }
            setFlashlight(flashlightOn);
        });
    }

    private void setFlashlight(boolean value) {
        if (getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA_FLASH)) {
            CameraManager cameraManager = (CameraManager) getSystemService(CAMERA_SERVICE);
            try {
                String camId = cameraManager.getCameraIdList()[0];
                cameraManager.setTorchMode(camId, value);
            } catch (CameraAccessException e) {
                throw new RuntimeException(e);
            }
        } else {
            Toast.makeText(this, "There is no camera on your device.", Toast.LENGTH_SHORT).show();

        }
    }
}