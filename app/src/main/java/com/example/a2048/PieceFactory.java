package com.example.a2048;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class PieceFactory {
    public static View creatPiece(Context context,int num) {
        TextView view = new TextView(context);
        view.setTextSize(25);
        view.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
        view.setText(Integer.toString(num));
        view.setGravity(Gravity.CENTER);
        switch (num) {

            case 2:
                view.setBackgroundResource(R.drawable.piece_corner_2);
                view.setTextColor(Color.BLACK);
                break;
            case 4:
                view.setBackgroundResource(R.drawable.piece_corner_4);
                view.setTextColor(Color.BLACK);
                break;
            case 8:
                view.setBackgroundResource(R.drawable.piece_corner_8);
                view.setTextColor(Color.WHITE);
                break;
            case 16:
                view.setBackgroundResource(R.drawable.piece_corner_16);
                view.setTextColor(Color.WHITE);
                break;
            case 32:
                view.setBackgroundResource(R.drawable.piece_corner_32);
                view.setTextColor(Color.WHITE);
                break;
            case 64:
                view.setBackgroundResource(R.drawable.piece_corner_64);
                view.setTextColor(Color.WHITE);
                break;
            case 128:
                view.setBackgroundResource(R.drawable.piece_corner_128);
                view.setTextColor(Color.WHITE);
                break;
            case 256:
                view.setBackgroundResource(R.drawable.piece_corner_256);
                view.setTextColor(Color.WHITE);
                break;

        }
        return view;
    }
}
