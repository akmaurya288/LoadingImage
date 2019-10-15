package com.akashmaurya.loadingimage;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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

import java.io.InputStream;

public class MainActivity extends AppCompatActivity {

    ImageView imageView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Fresco.initialize(this);
        setContentView(R.layout.activity_main);

        imageView = findViewById(R.id.imageView_Glidenet);

        netImageWithGlide();
        netImageWithFresco();

        new showImageFromNet((ImageView) findViewById(R.id.imageView_Code))
                .execute("https://www.gstatic.com/webp/gallery/4.jpg");
    }

    public void netImageWithGlide(){
        Glide.with(this)
                .load("https://www.gstatic.com/webp/gallery/3.webp")
                .override(800,600)
                .into(imageView);
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

}
