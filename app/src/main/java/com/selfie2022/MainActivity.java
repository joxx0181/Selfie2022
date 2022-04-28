package com.selfie2022;

import static android.Manifest.permission.CAMERA;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.content.Intent;
import android.graphics.Bitmap;
import android.provider.MediaStore;
import android.widget.Button;
import android.widget.ImageView;

/**
 * @author Joxx0181
 * @version 2022.0427
 */
public class MainActivity extends AppCompatActivity {

    // Initializing
    Button cameraOpen;
    ImageView selfieImage;

    // This will help to retrieve the image
    ActivityResultLauncher<Intent> StartForResult = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {

        @Override
        public void onActivityResult(ActivityResult result) {

                /**
                 * BitMap is data structure of image file,
                 * which store the image in memory
                 */
                Bitmap photo = (Bitmap)result.getData().getExtras()
                        .get("data");

                // Set the image in imageview for display
                selfieImage.setImageBitmap(photo);
        }
    });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Request permission
        ActivityCompat.requestPermissions(this,new String[]{CAMERA, WRITE_EXTERNAL_STORAGE}, PackageManager.PERMISSION_GRANTED);

        // Getting view that is identified by the android:id
        cameraOpen = findViewById(R.id.camera_button);
        selfieImage = findViewById(R.id.selfie_image);

        // Perform click event using lambda on cameraOpen button
        cameraOpen.setOnClickListener(v -> {

            /**
             * Create a implicit Intent to take a picture
             * Using MediaStore.ACTION_IMAGE_CAPTURE to launch an existing camera installed on device
             */
            Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

            // Start the activity with cameraIntent
            StartForResult.launch(cameraIntent);
        });
    }
}