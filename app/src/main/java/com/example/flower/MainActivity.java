package com.example.flower;

import android.graphics.Point;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Display;

public class MainActivity extends AppCompatActivity {

    AnimPathView animPathView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Get the screen size
//        Display display = getWindowManager().getDefaultDisplay();
//        Point size = new Point();
//        display.getSize(size);

        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int height = displayMetrics.heightPixels;
        int width = displayMetrics.widthPixels;

        // create AnimPathView
        animPathView = new AnimPathView(this);

        // Draw the path of Flowers
        animPathView.drawFlower(width, height);

        // Set Properties
        animPathView.setBackgroundColor(0xff000000);
        animPathView.setLineWidth(2);
        animPathView.setDrawingColor(0xff000099);
        animPathView.setAfterDrawingColor(0xffff0000);

        setContentView(animPathView);
    }
}
