package com.carlosveloper.state.Utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.Log;
import android.util.SparseArray;
import android.view.MotionEvent;
import android.view.View;

import com.carlosveloper.state.R;

import java.util.HashSet;
import java.util.Random;

public class ImageDrawingView extends View {

    private static final String TAG = "CirclesDrawingView";

    /** Main bitmap */
    private Bitmap mBitmap = null;

    private Rect mMeasuredRect;

    /** Stores data about single circle */
    private static class CircleArea {
        int radius;
        int centerX;
        int centerY;
        Bitmap drawableImage;


        CircleArea(int centerX, int centerY, int radius, Bitmap drawableImage) {
            this.radius = radius;
            this.centerX = centerX;
            this.centerY = centerY;
            this.drawableImage= drawableImage;
        }

        @Override
        public String toString() {
            return "Circle[" + centerX + ", " + centerY + ", " + radius + "]";
        }
    }

    /** Paint to draw circles */
    private Paint mCirclePaint;

    private final Random mRadiusGenerator = new Random();
    // Radius limit in pixels
    private final static int RADIUS_LIMIT = 80;

    public int CIRCLES_LIMIT = 0;

    /** All available circles */
    private HashSet<CircleArea> mCircles = new HashSet<CircleArea>(CIRCLES_LIMIT);
    private SparseArray<CircleArea> mCirclePointer = new SparseArray<CircleArea>(CIRCLES_LIMIT);

    /**
     * Default constructor
     *
     * @param ct {@link Context}
     */
    public ImageDrawingView(final Context ct) {
        super(ct);

        init(ct);
    }

    public ImageDrawingView(final Context ct, final AttributeSet attrs) {
        super(ct, attrs);

        init(ct);
    }

    public ImageDrawingView(final Context ct, final AttributeSet attrs, final int defStyle) {
        super(ct, attrs, defStyle);

        init(ct);
    }

    private void init(final Context ct) {
        // Generate bitmap used for background
     //   mBitmap = BitmapFactory.decodeResource(ct.getResources(), R.drawable.zeus);
        mCirclePaint = new Paint();
        mCirclePaint.setColor(Color.BLUE);
        mCirclePaint.setStrokeWidth(40);
        mCirclePaint.setStyle(Paint.Style.FILL);


    }

    @Override
    public void onDraw(final Canvas canv) {
        // background bitmap to cover all area
        //canv.drawBitmap(mBitmap, null, mMeasuredRect, null);
        //canv.drawColor(Color.WHITE);
      //  canv.drawColor(1, PorterDuff.Mode.CLEAR);

        for (CircleArea circle : mCircles) {
            //canv.drawCircle(circle.centerX, circle.centerY, circle.radius, mCirclePaint);


         //   Bitmap bitmap = BitmapFactory.decodeResource(getResources(), circle.drawableImage);

      /*      Bitmap bitmap      = ((BitmapDrawable) circle.drawableImage).getBitmap();


            Rect source = new Rect(circle.centerX, circle.centerY, bitmap.getWidth(), bitmap.getHeight());
            Rect bitmapRect = new Rect(circle.centerX, circle.centerY, bitmap.getWidth(), bitmap.getHeight());
            canv.drawBitmap(bitmap, source, bitmapRect, new Paint());*/




           /* Bitmap b=BitmapFactory.decodeResource(getResources(), R.drawable.diamante);
            canv.drawBitmap(b, circle.centerX, circle.centerY, new Paint());
*/
            canv.drawBitmap(circle.drawableImage, circle.centerX, circle.centerY, new Paint());
        }
    }

    @Override
    public boolean onTouchEvent(final MotionEvent event) {
        boolean handled = false;

        CircleArea touchedCircle;
        int xTouch;
        int yTouch;
        int pointerId;
        int actionIndex = event.getActionIndex();
        Log.e("le di click",""+actionIndex);

        // get touch event coordinates and make transparent circle from it
        switch (event.getActionMasked()) {


            case MotionEvent.ACTION_DOWN:
                // it's the first pointer, so clear all existing pointers data
                clearCirclePointer();

                xTouch = (int) event.getX(0);
                yTouch = (int) event.getY(0);

                // check if we've touched inside some circle
                touchedCircle = obtainTouchedCircle(xTouch, yTouch);
                touchedCircle.centerX = xTouch;
                touchedCircle.centerY = yTouch;
                mCirclePointer.put(event.getPointerId(0), touchedCircle);

                invalidate();
                handled = true;
                break;

            case MotionEvent.ACTION_POINTER_DOWN:
                Log.e(TAG, "Pointer down");
                // It secondary pointers, so obtain their ids and check circles
                pointerId = event.getPointerId(actionIndex);

                xTouch = (int) event.getX(actionIndex);
                yTouch = (int) event.getY(actionIndex);

                // check if we've touched inside some circle
                touchedCircle = obtainTouchedCircle(xTouch, yTouch);

                mCirclePointer.put(pointerId, touchedCircle);
                touchedCircle.centerX = xTouch;
                touchedCircle.centerY = yTouch;
                invalidate();
                handled = true;
                break;

            case MotionEvent.ACTION_MOVE:
                final int pointerCount = event.getPointerCount();

                Log.w(TAG, "Move");

                for (actionIndex = 0; actionIndex < pointerCount; actionIndex++) {
                    // Some pointer has moved, search it by pointer id
                    pointerId = event.getPointerId(actionIndex);

                    xTouch = (int) event.getX(actionIndex);
                    yTouch = (int) event.getY(actionIndex);

                    touchedCircle = mCirclePointer.get(pointerId);

                    if (null != touchedCircle) {
                        touchedCircle.centerX = xTouch;
                        touchedCircle.centerY = yTouch;
                    }
                }
                invalidate();
                handled = true;
                break;

            case MotionEvent.ACTION_UP:
                Log.w(TAG, "UP");

                clearCirclePointer();
                invalidate();
                handled = true;
                break;

            case MotionEvent.ACTION_POINTER_UP:
                Log.w(TAG, "POINTER_UP");

                // not general pointer was up
                pointerId = event.getPointerId(actionIndex);

                mCirclePointer.remove(pointerId);
                invalidate();
                handled = true;
                break;

            case MotionEvent.ACTION_CANCEL:
                Log.w(TAG, "ACTION_CANCEL");

                handled = true;
                break;

            default:
                // do nothing
                break;
        }

        return super.onTouchEvent(event) || handled;
    }

    /**
     * Clears all CircleArea - pointer id relations
     */
    private void clearCirclePointer() {
        Log.w(TAG, "clearCirclePointer");

        mCirclePointer.clear();
    }

    /**
     * Search and creates new (if needed) circle based on touch area
     *
     * @param xTouch int x of touch
     * @param yTouch int y of touch
     *
     * @return obtained {@link CircleArea}
     */
    private CircleArea obtainTouchedCircle(final int xTouch, final int yTouch) {
        CircleArea touchedCircle = getTouchedCircle(xTouch, yTouch);

        if (null == touchedCircle) {
            touchedCircle = new CircleArea(xTouch, yTouch, mRadiusGenerator.nextInt(RADIUS_LIMIT) + RADIUS_LIMIT,mBitmap);
        //    touchedCircle.drawableImage(getResources().getDrawable(R.drawable.diamante));
            Log.w(TAG, "Principio" + mCircles.size());

         /*   if (mCircles.size() == CIRCLES_LIMIT) {
                Log.w(TAG, "Clear all circles, size is " + mCircles.size());
                // remove first circle
                mCircles.clear();
            }

            Log.w(TAG, "Added circle " + touchedCircle);
            */


            if(mCircles.size()<CIRCLES_LIMIT){
                Log.w(TAG, "Added circle " + touchedCircle);
                mCircles.add(touchedCircle);
            }
        }

        return touchedCircle;
    }

    /**
     * Determines touched circle
     *
     * @param xTouch int x touch coordinate
     * @param yTouch int y touch coordinate
     *
     * @return {@link CircleArea} touched circle or null if no circle has been touched
     */
    private CircleArea getTouchedCircle(final int xTouch, final int yTouch) {
        CircleArea touched = null;

        for (CircleArea circle : mCircles) {
            if ((circle.centerX - xTouch) * (circle.centerX - xTouch) + (circle.centerY - yTouch) * (circle.centerY - yTouch) <= circle.radius * circle.radius) {
                touched = circle;
                break;
            }
        }

        return touched;
    }

    @Override
    protected void onMeasure(final int widthMeasureSpec, final int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        mMeasuredRect = new Rect(0, 0, getMeasuredWidth(), getMeasuredHeight());
    }


    public Bitmap getBitmap() {
        View v = (View) this.getParent();
        Bitmap b = Bitmap.createBitmap(v.getMeasuredWidth(), v.getMeasuredHeight(), Bitmap.Config.ARGB_8888);
        Canvas c = new Canvas(b);
        v.layout(v.getLeft(), v.getTop(), v.getRight(), v.getBottom());
        v.draw(c);
        return b;
    }

    public void selectBitmap(Bitmap bit){
        mBitmap=bit;
    }

    public void aumentarImagen(){
        CIRCLES_LIMIT++;
    }

}
