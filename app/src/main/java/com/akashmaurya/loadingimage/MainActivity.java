package com.akashmaurya.loadingimage;

import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.view.SimpleDraweeView;

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
    }

    public void netImageWithGlide(){
        Glide.with(this)
                .load("https://moodle.htwchur.ch/pluginfile.php/124614/mod_page/content/4/example.jpg")
                .override(800,600)
                .into(imageView);
    }

    public void netImageWithFresco(){
        Uri imageUri = Uri.parse("https://i.imgur.com/tGbaZCY.jpg");
        SimpleDraweeView draweeView = (SimpleDraweeView) findViewById(R.id.sdvImage);
        draweeView.setImageURI(imageUri);
    }
}
