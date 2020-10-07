package com.carlosveloper.state.Utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.carlosveloper.state.R;

public class MyView  extends View {

    boolean touching = false;

    public MyView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public MyView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);    //To change body of overridden methods use File | Settings | File Templates.

        if (touching) {
            //You can draw other thing here.  just draw bitmap for example.
            Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.pencil);
            Rect source = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
            Rect bitmapRect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
            canvas.drawBitmap(bitmap, source, bitmapRect, new Paint());
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                touching = true;
                invalidate();
                break;
            case MotionEvent.ACTION_CANCEL:
            case MotionEvent.ACTION_UP:
                touching = false;
                invalidate();
                break;
        }
        return super.onTouchEvent(event);    //To change body of overridden methods use File | Settings | File Templates.
    }
}