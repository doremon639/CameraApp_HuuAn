package com.example.cameraapp;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.widget.Button;
import android.widget.ImageView;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import java.io.File;
import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    Button btnCapture;
    ImageView imgResult;
    Uri imageUri;
    ActivityResultLauncher<Uri> cameraLauncher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnCapture = findViewById(R.id.btnCapture);
        imgResult = findViewById(R.id.imgResult);

        // Chuẩn bị launcher mới cho Camera
        cameraLauncher = registerForActivityResult(
                new ActivityResultContracts.TakePicture(),
                result -> {
                    if (result) {
                        imgResult.setImageURI(imageUri);
                    }
                }
        );

        btnCapture.setOnClickListener(v -> {
            try {
                File photoFile = File.createTempFile(
                        "IMG_", ".jpg",
                        getExternalFilesDir(Environment.DIRECTORY_PICTURES)
                );

                imageUri = FileProvider.getUriForFile(
                        this,
                        getPackageName() + ".provider",
                        photoFile
                );

                cameraLauncher.launch(imageUri);

            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }
}
