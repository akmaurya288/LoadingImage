package com.akashmaurya.loadingimage;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.view.SimpleDraweeView;

import java.io.File;
import java.io.InputStream;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ImageView imageView_glide;
    private static final int PERMISSION_REQUEST_CODE = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Fresco.initialize(this);
        setContentView(R.layout.activity_main);

        if (checkPermission())
        {
            showImageFromLocalStorage();

        } else {
            requestPermission();
        }

        imageView_glide = findViewById(R.id.imageView_Glidenet);

        netImageWithGlide();

        netImageWithFresco();


        new showImageFromNet((ImageView) findViewById(R.id.imageView_Code))
                .execute("https://www.gstatic.com/webp/gallery/2.jpg");
    }

    private boolean checkPermission() {
        int result = ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE);
        if (result == PackageManager.PERMISSION_GRANTED) {
            return true;
        } else {
            return false;
        }
    }

    private void requestPermission() {

            ActivityCompat.requestPermissions(MainActivity.this,
                    new String[]{android.Manifest.permission.WRITE_EXTERNAL_STORAGE}, PERMISSION_REQUEST_CODE);

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case PERMISSION_REQUEST_CODE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Log.e("value", "Permission Granted, Now you can use local drive .");
                    showImageFromLocalStorage();
                } else {
                    Log.e("value", "Permission Denied, You cannot use local drive .");
                }
                break;
        }
    }

    public void netImageWithGlide(){
        Glide.with(this)
                .load("https://www.gstatic.com/webp/gallery/3.webp")
                .override(800,600)
                .into(imageView_glide);
    }

    public void netImageWithFresco(){
        Uri imageUri = Uri.parse("https://www.gstatic.com/webp/gallery/1.webp");
        SimpleDraweeView draweeView = (SimpleDraweeView) findViewById(R.id.sdvImage);
        draweeView.setImageURI(imageUri);
    }

    private class showImageFromNet extends AsyncTask<String, Void, Bitmap> {
        ImageView imageView;

        public showImageFromNet(ImageView imageView) {
            this.imageView = imageView;
        }

        protected Bitmap doInBackground(String... urls) {
            String imageURL = urls[0];
            Bitmap bimage = null;
            try {
                InputStream in = new java.net.URL(imageURL).openStream();
                bimage = BitmapFactory.decodeStream(in);

            } catch (Exception e) {
                Log.e("Error Message", e.getMessage());
            }
            return bimage;
        }

        protected void onPostExecute(Bitmap result) {
            imageView.setImageBitmap(result);
        }
    }

    private void showImageFromLocalStorage(){
        File imgFile = new  File(getImagesPath(this));
        if(imgFile.exists()){

            Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());

            ImageView imageView = findViewById(R.id.imageView_Code_Local);

            imageView.setImageBitmap(myBitmap);

        };
    }

    private String getImagesPath(Activity activity) {
        Uri uri;
        Cursor cursor;
        ArrayList<String> listOfAllImages = new ArrayList<String>();
        String absolutePathOfImage = null;
        uri = android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI;

        String[] projection = { MediaStore.MediaColumns.DATA };

        cursor = activity.getContentResolver().query(uri, projection, null,
                null, null);

        while (cursor.moveToNext()) {
            absolutePathOfImage = cursor.getString(0);

            listOfAllImages.add(absolutePathOfImage);
        }

        return listOfAllImages.get(0);
    }
}
