package com.example.sdaassign32022;

import android.content.Context;
import android.graphics.Bitmap;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class ImageSave {
    public void saveImageUsingFilesDir(Context context, Bitmap bitmap, String fileName) {
        File directory = context.getFilesDir(); // Get the internal storage directory
        File imageFile = new File(directory, fileName);

        try {
            FileOutputStream fos = new FileOutputStream(imageFile);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos);
            fos.close();
            Toast.makeText(context, "Image Saved at: " + imageFile.getAbsolutePath(), Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(context, "Error Saving Image", Toast.LENGTH_SHORT).show();
        }
    }
}

