package com.carlosveloper.state.Utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import java.util.ArrayList;

public class DrawView  extends View {
    Paint mPaint;
    Bitmap mBitmap;
    Canvas mCanvas;
    Path mPath;
    float x, y;
    private ArrayList<Path> paths = new ArrayList<Path>();
    private ArrayList<Path> undonePaths = new ArrayList<Path>();
    private ArrayList<Paint> mpaints = new ArrayList<Paint>();

    Paint mBitmapPaint;

    public DrawView(Context context) {
        super(context);


        mPath = new Path();
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setDither(true);
        mPaint.setColor(Color.argb(255, 0, 0, 0));
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeJoin(Paint.Join.ROUND);
        mPaint.setStrokeCap(Paint.Cap.ROUND);
        mPaint.setStrokeWidth(10);
    }


    public void changeColor(int Color){
        mPaint.setColor(Color);

    }






    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

        if (w == 0) {
            w = 1080;
        }
        if (mBitmap == null) {
            mBitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);

        } else {
            h = mBitmap.getHeight();
            w = mBitmap.getWidth();
            mBitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
        }
        if (mCanvas == null)

        {
            mCanvas = new Canvas(mBitmap);
        } else {
            mCanvas = null;
            mCanvas = new Canvas(mBitmap);
        }

    }

    @Override
    public void draw(Canvas canvas) {
        // TODO Auto-generated method stub
        super.draw(canvas);
        for (int i = 0; i < paths.size(); i++) {
            canvas.drawPath(paths.get(i), mpaints.get(i));
            canvas.drawBitmap(mBitmap, 0, 0, mpaints.get(i));
        }
        canvas.drawPath(mPath, mPaint);

   /* for (int i = 0; i < paths.size(); i++) {
        canvas.drawPath(paths.get(i), mPaint);
        canvas.drawBitmap(mBitmap, 0, 0, mBitmapPaint);
    }*/

    }

    /*public void onClickUndo() {

        if (paths.size() > 0) {
            undonePaths.add(paths.remove(paths.size() - 1));
            mpaints.remove(paths.size());
            invalidate();
        }
    }*/

    public void onClickUndo() {
        if (paths.size() > 0) {

            undonePaths.add(paths.remove(paths.size() - 1));
          //  mPaint.setColor( mpaints.get(mpaints.size()-1).getColor());


         //   mpaints.remove(paths.size() - 1);

            for (int i = 0; i <undonePaths.size(); i++) {
                mCanvas.drawColor(1, PorterDuff.Mode.CLEAR);
              mPaint = new Paint(mPaint);
         /*   Path path   = this.undonePaths.get(i);
            Paint paint=this.mpaints.get(i);
            mCanvas.drawPath(path, paint);*/
                /*mCanvas.drawBitmap(mBitmap, 0, 0, mBitmapPaint);*/

            }

            if(paths.size()<=0){
                mpaints.clear();
            }
            invalidate();


        }else{
            mpaints.clear();
        }

    }

    private float mX, mY;
    private static final float TOUCH_TOLERANCE =10;






    @Override
    public boolean onTouchEvent(MotionEvent event) {
        x = event.getX();
        y = event.getY();

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
               // touch_start(x, y);
                TouchStart(x,y);
                invalidate();
                break;
            case MotionEvent.ACTION_MOVE:
              //  touch_move(x, y);
                TouchMove(x,y);
                invalidate();
                break;
            case MotionEvent.ACTION_UP:
                //touch_up();
                TouchUp();
                invalidate();
                break;
        }
        return true;
    }

    private void TouchStart(float x, float y) {
        mPath.reset();
        mPath.moveTo(x, y);
        undonePaths.clear();
        mX = x;
        mY = y;

    }




    private void TouchMove(float x, float y) {
        float dx = Math.abs(x - mX);
        float dy = Math.abs(y - mY);
        if (dx >= TOUCH_TOLERANCE || dy >= TOUCH_TOLERANCE) {
            mPath.quadTo(mX, mY, (x + mX) / 2, (y + mY) / 2);
            mX = x;
            mY = y;
        }
    }





    private void TouchUp() {
        if (!mPath.isEmpty()) {
            mPath.lineTo(mX, mY);
            mCanvas.drawPath(mPath, mPaint);

            paths.add(mPath);
            mPath = new Path();
            mpaints.add(mPaint);
            mPaint = new Paint(mPaint);
        } else {
            mCanvas.drawPoint(mX, mY, mPaint);
        }
        mPath.reset();
    }

    public Bitmap getBitmap() {
        View v = (View) this.getParent();
        Bitmap b = Bitmap.createBitmap(v.getWidth(), v.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas c = new Canvas(b);
        v.layout(v.getLeft(), v.getTop(), v.getRight(), v.getBottom());
        v.draw(c);

        return b;
    }



}