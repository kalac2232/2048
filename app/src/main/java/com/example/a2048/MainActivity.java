package com.example.a2048;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.GridLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import static com.example.a2048.Game.MaxScore;
import static com.example.a2048.Game.gameData;
import static com.example.a2048.Game.gameScore;
import static com.example.a2048.Game.margin;
import static com.example.a2048.Game.viewWidth;

public class MainActivity extends AppCompatActivity {
    private Context mContext;
    private FrameLayout mMoveDistrict;
    private GameGridLayout mLattice;
    private int mNavigationBarHeight;
    private TextView currentScore;
    private TextView tvMaxScore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mContext = this;
        initGame();

    }

    private void initGame() {
        initData();
        initUI();
        Game.initGame(mContext,mLattice,mMoveDistrict);
    }

    private void initData() {
        //获取屏幕宽度
        DisplayMetrics metric = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metric);
        int width = metric.widthPixels;
        viewWidth = width / 5;
        margin = viewWidth / 13;
        Resources res = mContext.getResources();
        int resourceId = res.getIdentifier("navigation_bar_height", "dimen", "android");
        if (resourceId > 0) {
            mNavigationBarHeight = res.getDimensionPixelSize(resourceId);
        }
        SharedPreferences sharedPreferences = mContext.getSharedPreferences("filename", Context.MODE_PRIVATE);
        int score = sharedPreferences.getInt("maxScore",0);
        MaxScore = score;
    }

    private void initUI() {
        mMoveDistrict = findViewById(R.id.fl_movedistrict);
        mLattice = findViewById(R.id.gridlayout);
        currentScore = findViewById(R.id.tv_currentscore);
        tvMaxScore = findViewById(R.id.tv_maxscore);
        mLattice.setPadding(margin, margin, margin, margin);
        //将格子填充好默认view
        for (int i = 0; i < 16; i++) {
            FrameLayout view = new FrameLayout(mContext);
            ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(viewWidth,viewWidth);
            GridLayout.LayoutParams layoutParams = new GridLayout.LayoutParams(params);
            layoutParams.leftMargin = margin;
            layoutParams.topMargin = margin;
            layoutParams.rightMargin = margin;
            layoutParams.bottomMargin = margin;
            view.setLayoutParams(layoutParams);
            view.setBackgroundResource(R.drawable.piece_corner);
            mLattice.addView(view);
        }
        currentScore.setText("0");
        tvMaxScore.setText(String.valueOf(Game.MaxScore));
    }
    public void updataScore() {
        currentScore.setText(String.valueOf(Game.gameScore));
    }
    public void showEndPopupWindow() {
        View view = LayoutInflater.from(mContext).inflate(R.layout.pop_window, null);
        //构造方法public PopupWindow(View contentView, int width, int height, boolean focusable)  focusable让PopupWindow获得焦点
        final PopupWindow popupWindow = new PopupWindow(view, mMoveDistrict.getWidth(), mMoveDistrict.getHeight(), true);
        //位于于控件的下方，
        popupWindow.showAsDropDown(mLattice,0,-mLattice.getMeasuredHeight(),Gravity.CENTER);
        popupWindow.setFocusable(true);
        popupWindow.setOutsideTouchable(false);

        Button button = view.findViewById(R.id.startnewgame);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.dismiss();
            }
        });

        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {

            public void onDismiss() {
                gameEnd();
            }
        });
    }

    private void gameEnd() {
        if (gameScore > MaxScore) {
            SharedPreferences sharedPreferences = mContext.getSharedPreferences("filename", Context.MODE_PRIVATE);//getSharedPreferences("文件名",操作模式)
            SharedPreferences.Editor edit = sharedPreferences.edit();
            edit.putInt("maxScore",gameScore);
            edit.commit();
            MaxScore = gameScore;
            tvMaxScore.setText(String.valueOf(gameScore));
        }
        Game.startNewGame();
    }
}
