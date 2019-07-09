package com.example.flower;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathMeasure;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

class Position
{
    double x;
    double y;

    Position(double x, double y) {
        this.x = x;
        this.y = y;
    }
}

public class AnimPathView extends View implements View.OnClickListener {

    private Path path;
    private Paint paint;

    private static final long animSpeedInMs = 3;
    private static final long animMsBetweenStrokes = 20;
    private long animLastUpdate;
    private boolean animRunning;
    private int animCurrentCountour;
    private float animCurrentPos;
    private Path animPath;
    private PathMeasure animPathMeasure;

    private Path path_flower;
    private Paint paint_flower;
    private double radius;
    private double borderSpace = 50;
    private float linewidth = 6;

    public void setLineWidth(float lineWidth) {
        paint.setStrokeWidth(lineWidth);
        paint_flower.setStrokeWidth(lineWidth);
    }

    public void setDrawingColor(int color) {
        paint.setColor(color);
    }

    public void setAfterDrawingColor(int color) {
        paint_flower.setColor(color);
    }

    private void init()
    {
        path_flower = new Path();
        paint_flower = new Paint();
        paint_flower.setAntiAlias(true);
        paint_flower.setDither(true);
        paint_flower.setColor(0xff0000ff);
        paint_flower.setStyle(Paint.Style.STROKE);
        paint_flower.setStrokeJoin(Paint.Join.ROUND);
        paint_flower.setStrokeCap(Paint.Cap.ROUND);
        paint_flower.setStrokeWidth(linewidth);
    }

    private void drawCircle(Position center, double from, double angle, double radius) {

        double from_radian = from * Math.PI / 180;
        double begin_x = center.x + radius * Math.cos(from_radian);
        double begin_y = center.y + radius * Math.sin(from_radian);

        //path_flower.moveTo((float)begin_x, (float)begin_y);

        path_flower.addArc((float)(center.x - radius), (float)(center.y - radius),
                (float)(center.x + radius), (float)(center.y + radius), (float)from, (float) angle);
    }

    private void drawFirstRowCircles(Position center) {
        for (double i = -90.0; i < 270; i+= 60) {
            double radians = i * Math.PI / 180;
            double x = center.x + radius * Math.cos(radians);
            double y = center.y + radius * Math.sin(radians);

            drawCircle(new Position(x, y), -90, 360, radius);
        }
    }

    private void drawSecondRowCircles(Position center) {
        double second_radius1 = 2 * radius;
        double second_radius2 = Math.sqrt(3.0) * radius;

        for (double i = -90; i < 270; i+= 60) {
            double radians = i * Math.PI / 180;
            double x = center.x + second_radius1 * Math.cos(radians);
            double y = center.y + second_radius1 * Math.sin(radians);

            drawCircle(new Position(x, y), -90, 360.0, radius);

            radians = (i + 30) * Math.PI / 180;
            x = center.x + second_radius2 * Math.cos(radians);
            y = center.y + second_radius2 * Math.sin(radians);

            drawCircle(new Position(x, y), -90, 360.0, radius);
        }
    }

    private void drawLongPartialCircles(Position center) {
        double third_radius1 = 3 * radius;
        double third_radisu2 = Math.sqrt(7.0) * radius;
        int num_circles = 0;

        for (double i = -90; i < 270; i+= 60) {

            double radians = i * Math.PI / 180;
            double x = center.x + third_radius1 * Math.cos(radians);
            double y = center.y + third_radius1 * Math.sin(radians);

            drawCircle(new Position(x, y), 30 + num_circles * 60,  120.0, radius);

            radians = (i + 19) * Math.PI / 180;
            x = center.x + third_radisu2 * Math.cos(radians);
            y = center.y + third_radisu2 * Math.sin(radians);

            drawCircle(new Position(x, y), 30 + num_circles * 60, 180.0, radius);

            radians = (i + 41) * Math.PI / 180;
            x = center.x + third_radisu2 * Math.cos(radians);
            y = center.y + third_radisu2 * Math.sin(radians);

            drawCircle(new Position(x, y), 30 + num_circles * 60, 180.0, radius);

            num_circles += 1;

        }
    }

    private void  drawShortPartialCircles(Position center) {
        double forth_radius1 = Math.sqrt(13.0) * radius;
        double forth_radius2 = Math.sqrt(12.0) * radius;
        int num_circles = 0;

        for (double i = -90; i < 270; i+= 60) {

            double radians = (i + 14.0) * Math.PI / 180;
            double x = center.x + forth_radius1 * Math.cos(radians);
            double y = center.y + forth_radius1 * Math.sin(radians);

            drawCircle(new Position(x, y), 90 + num_circles * 60, 60.0, radius);

            radians = (i + 30.0) * Math.PI / 180;
            x = center.x + forth_radius2 * Math.cos(radians);
            y = center.y + forth_radius2 * Math.sin(radians);

            drawCircle(new Position(x, y), 90 + num_circles * 60, 60.0, radius);

            radians = (i + 46.0) * Math.PI / 180;
            x = center.x + forth_radius1 * Math.cos(radians);
            y = center.y + forth_radius1 * Math.sin(radians);

            drawCircle(new Position(x, y), 90 + num_circles * 60, 60.0, radius);

            num_circles += 1;
        }
    }

    private void drawOutline(Position center) {
        drawCircle(center, -90, 360, radius * 3 + borderSpace / 4);
        drawCircle(center, -90, 360, radius * 3 + borderSpace);
    }

    public void drawFlower(int width, int height) {

        path.close();
        path = new Path();

        radius = (Math.min(width, height) - 2 * borderSpace - 20) / 6;

        Position center = new Position(width /  2, height / 2);

        // the center circle
        drawCircle(center, -90, 360.0, radius);

        // first row circles
        drawFirstRowCircles(center);

        //second row circles
        drawSecondRowCircles(center);

        // long partial ciricles
        drawLongPartialCircles(center);

        // short partial circles

        drawShortPartialCircles(center);

        //drawOutline
        drawOutline(center);

        setPath(path_flower);
    }

    public AnimPathView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initDisplayKanjiView();
    }

    public AnimPathView(Context context) {
        super(context);
        initDisplayKanjiView();
    }

    private final void initDisplayKanjiView() {

        paint = new Paint();
        paint.setAntiAlias(true);
        paint.setDither(true);
        paint.setColor(0xff336699);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeJoin(Paint.Join.ROUND);
        paint.setStrokeCap(Paint.Cap.ROUND);
        paint.setStrokeWidth(linewidth);

        path = new Path();
        animRunning = true;

        init();

        this.setOnClickListener(this);
    }

    public void setPath(Path p) {
        path = p;
    }

    @Override
    public void onClick(View v) {
        startAnimation();
    }

    public void startAnimation() {
        animRunning = true;
        animPathMeasure = null;
        invalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        Log.d("draw", "draw");
        if (animRunning) {
            drawAnimation(canvas);
        } else {
           drawStatic(canvas);
        }
    }

    private void drawAnimation(Canvas canvas) {
        if (animPathMeasure == null) {
            // Start of animation. Set it up.
            animPathMeasure = new PathMeasure(path, false);
            //animPathMeasure.nextContour();
            animPath = new Path();
            animLastUpdate = System.currentTimeMillis();
            animCurrentCountour = 0;
            animCurrentPos = 0.0f;
        } else {
            // Get time since last frame
            long now = System.currentTimeMillis();
            long timeSinceLast = now - animLastUpdate;

            if (animCurrentPos == 0.0f) {
                timeSinceLast -= animMsBetweenStrokes;
            }

            if (timeSinceLast > 0) {
                // Get next segment of path
                float newPos = (float)(timeSinceLast) * animSpeedInMs + animCurrentPos;
                boolean moveTo = (animCurrentPos == 0.0f);
                animPathMeasure.getSegment(animCurrentPos, newPos, animPath, moveTo);
                animCurrentPos = newPos;
                animLastUpdate = now;

                // If this stroke is done, move on to next
                if (newPos > animPathMeasure.getLength()) {
                    animCurrentPos = 0.0f;
                    animCurrentCountour++;
                    boolean more = animPathMeasure.nextContour();
                    // Check if finished
                    if (!more) { animRunning = false; }
                }
            }
            // Draw path
            canvas.drawPath(animPath, paint);
        }

        invalidate();
    }

    private void drawStatic(Canvas canvas) {
        canvas.drawPath(path, paint_flower);
    }
}
