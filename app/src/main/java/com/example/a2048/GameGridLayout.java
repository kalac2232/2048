package com.example.a2048;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.GridLayout;

public class GameGridLayout extends GridLayout {
    private static final String TAG = "GameGridLayout";
    float startX;
    float startY;
    public GameGridLayout(Context context) {
        super(context);
    }

    public GameGridLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public GameGridLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }
    

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        getParent().requestDisallowInterceptTouchEvent(true);

        switch(event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                startX = event.getX();
                startY = event.getY();

                break;
            case MotionEvent.ACTION_MOVE:
                break;
            case MotionEvent.ACTION_UP:
                float endX = event.getX();
                float endY = event.getY();

                if (Math.abs(endX - startX) > 50) {
                    if (endX > startX) {
                        Game.moveRight();

                    } else {
                        Game.moveLeft();

                    }

                } else if (Math.abs(endY - startY) > 50){
                    if (endY > startY) {
                        Game.moveDown();
                    } else {
                        Game.moveUp();
                    }

                }
                break;
            default:
                break;
        }
        return true;
    }
}
