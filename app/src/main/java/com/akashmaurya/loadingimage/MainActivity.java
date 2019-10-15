package com.akashmaurya.loadingimage;

import android.content.Context;
import android.content.ContextWrapper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.AsyncTask;
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

public class MainActivity extends AppCompatActivity {

    ImageView imageView_glide;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Fresco.initialize(this);
        setContentView(R.layout.activity_main);

        imageView_glide = findViewById(R.id.imageView_Glidenet);

        netImageWithGlide();

        netImageWithFresco();

        showImageFromLocalStorage();

        new showImageFromNet((ImageView) findViewById(R.id.imageView_Code))
                .execute("https://www.gstatic.com/webp/gallery/2.jpg");
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
        File imgFile = new  File("/sdcard/images.jpeg");
        if(imgFile.exists()){

            Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());

            ImageView imageView = findViewById(R.id.imageView_Code_Local);

            imageView.setImageBitmap(myBitmap);

        };
    }
}
