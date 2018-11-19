package com.example.a2048;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.GridLayout;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class Game {
    public static int[][] gameData = new int[4][4];
    public static int viewWidth;
    public static int margin;
    private static Context mContext;
    private static GameGridLayout gridLayout;
    private static List<Animator> animatorList;
    private static FrameLayout mMoveDistrict;
    private static int size = 0;
    public static int gameScore = 0;
    public static int MaxScore = 0;
    private static MainActivity activity;

    public static void moveUp(){
        int index = 0;
        for (int x = 0;x < 4 ;x++){
            for (int y = 0;y < 4 ;y++) {
                if (gameData[x][y] != 0) {
                    for (int k = index ;k < y ;k++) {
                        if (gameData[x][k] == 0 && canMoveVertical(x,k,y,gameData)) {
                            //添加移动的动画
                            startMoveAnimation(x,y,x,k);
                            gameData[x][k] = gameData[x][y];
                            gameData[x][y] = 0;


                        } else if (gameData[x][k] == gameData[x][y] && canMoveVertical(x,k,y,gameData)){
                            startMoveAnimation(x,y,x,k);
                            //添加移动动画
                            gameData[x][k] += gameData[x][k];
                            gameData[x][y] = 0;
                            size--;
                            index = y;
                            //更新积分
                            updataScore(gameData[x][k]);
                        }
                    }
                }
            }
            index = 0;
        }
    }
    public static void moveLeft(){
        int index = 0;
        for (int y = 0;y < 4 ;y++){
            for (int x = 0;x < 4 ;x++) {
                if (gameData[x][y] != 0) {
                    for (int k = index ;k < x ;k++) {
                        if (gameData[k][y] == 0 && canMoveHorizontal(k,y,x,gameData)) {
                            //添加移动的动画
                            startMoveAnimation(x,y,k,y);
                            gameData[k][y] = gameData[x][y];
                            gameData[x][y] = 0;

                        } else if (gameData[k][y] == gameData[x][y] && canMoveHorizontal(k,y,x,gameData)){
                            startMoveAnimation(x,y,k,y);
                            //添加移动动画
                            gameData[k][y] += gameData[k][y];
                            gameData[x][y] = 0;
                            size--;
                            index = x;
                            //更新积分
                            updataScore(gameData[k][y]);
                        }
                    }
                }
            }
            index = 0;
        }
    }
    public static void moveRight(){
        int index = 3;
        for (int y = 3;y >= 0 ;y--){
            for (int x = 3;x>=0 ;x--) {
                if (gameData[x][y] != 0) {
                    for (int k = index ;k > x ;k--) {
                        if (gameData[k][y] == 0 && canMoveHorizontal(x,y,k,gameData)) {
                            //添加移动的动画
                            startMoveAnimation(x,y,k,y);
                            gameData[k][y] = gameData[x][y];
                            gameData[x][y] = 0;

                        } else if (gameData[k][y] == gameData[x][y] && canMoveHorizontal(x,y,k,gameData)){
                            //添加移动动画
                            startMoveAnimation(x,y,k,y);
                            gameData[k][y] += gameData[k][y];
                            gameData[x][y] = 0;
                            size--;
                            index = x;
                            //更新积分
                            updataScore(gameData[k][y]);
                        }
                    }
                }
            }
            index = 3;
        }
    }
    public static void moveDown(){
        int index = 3;
        for (int x = 3;x >= 0 ;x--){
            for (int y = 3;y>=0 ;y--) {
                if (gameData[x][y] != 0) {
                    for (int k = index ;k > y ;k--) {
                        if (gameData[x][k] == 0 && canMoveVertical(x,y,k,gameData)) {
                            //添加移动的动画
                            startMoveAnimation(x,y,x,k);
                            gameData[x][k] = gameData[x][y];
                            gameData[x][y] = 0;
                            //更新积分

                        } else if (gameData[x][k] == gameData[x][y] && canMoveVertical(x,y,k,gameData)){
                            //添加移动动画
                            startMoveAnimation(x,y,x,k);
                            gameData[x][k] += gameData[x][k];
                            gameData[x][y] = 0;
                            size--;
                            index = y;
                            //更新积分
                            updataScore(gameData[x][k]);
                        }
                    }
                }
            }
            index = 3;
        }
    }
    private static boolean canMoveVertical(int row, int col1, int col2, int[][] gameData) {
        for (int i = col1 + 1; i < col2; i++) {
            if (gameData[row][i] != 0) {
                return false;
            }
        }
        return true;
    }
    private static boolean canMoveHorizontal(int col1, int column, int col2, int[][] gameData) {
        for (int i = col1 + 1; i < col2; i++) {
            if (gameData[i][column] != 0) {
                return false;
            }
        }
        return true;
    }
    private static void checkGameEnd() {
        boolean result = true;
        if (size != 16) {
            result = false;
        }
        for (int y = 0; y < 4; y++) {
            for (int x = 0; x < 3; x++) {
                if (gameData[x][y] == gameData[x+1][y]) {
                    result = false;
                }
            }
        }
        for (int y = 0; y < 3; y++) {
            for (int x = 0; x < 4; x++) {
                if (gameData[x][y] == gameData[x][y+1]) {
                    result = false;
                }
            }
        }

        if (result) {
            Log.i("-------------", "checkGameEnd: gameEnd");
            MainActivity activity = (MainActivity) mContext;
            activity.showEndPopupWindow();
        }
    }
    public static void refresh(){
        //首先将格子中清空
        for (int i = 0; i<16 ;i++) {
            ViewGroup viewGroup = (ViewGroup) gridLayout.getChildAt(i);
            viewGroup.removeAllViews();
        }
        //根据数据内容填充表格
        for (int y = 0;y<4;y++) {
            for (int x = 0;x < 4;x++) {
                if (gameData[x][y] != 0) {
                    ViewGroup viewGroup = (ViewGroup) gridLayout.getChildAt(x + y * 4);
                    View view = PieceFactory.creatPiece(mContext,gameData[x][y]);
                    viewGroup.addView(view);
                }
            }
        }

    }
    private static void startMoveAnimation(int startX, int startY, int endX, int endY){
        final ObjectAnimator translation;
        //用于计算块移动的距离
        ViewGroup startChild = (ViewGroup) gridLayout.getChildAt(startX + startY * 4);
        ViewGroup endChild = (ViewGroup) gridLayout.getChildAt(endX + endY * 4);

        if (gameData[startX][startY] == 0) {
            return;
        }
        //产生块
        final View view = PieceFactory.creatPiece(mContext,gameData[startX][startY]);
        //设置view的属性
        GridLayout.LayoutParams params = new GridLayout.LayoutParams(new ViewGroup.LayoutParams(viewWidth,viewWidth));
        params.leftMargin = startChild.getLeft();
        params.topMargin = startChild.getTop();
        params.rightMargin = margin;
        params.bottomMargin = margin;
        view.setLayoutParams(params);

        mMoveDistrict.addView(view);
        //上层添加上view后，将格子中的移除
        startChild.removeAllViews();
        //计算距离
        if (startX == endX) {
            //垂直方向
            int distance = endChild.getTop() - startChild.getTop();
            translation = ObjectAnimator.ofFloat(view, "translationY", 0.0F, distance);
        } else {
            //水平方向
            int distance = endChild.getLeft() - startChild.getLeft();
            translation = ObjectAnimator.ofFloat(view, "translationX", 0.0F, distance);
        }
        animatorList.add(translation);
        final AnimatorSet set = new AnimatorSet();
        set.play(translation);
        set.setDuration(150);
        set.start();

        //动画监听
        set.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                mMoveDistrict.removeView(view);
                animatorList.remove(translation);
                if (animatorList.isEmpty()) {
                    //动画结束
                    refresh();
                    randomPiece();
                    checkGameEnd();
                }
            }
        });
    }
    public static void initGame(Context context, GameGridLayout mLattice, FrameLayout moveDistrict){
        mContext = context;
        gridLayout = mLattice;
        mMoveDistrict = moveDistrict;
        gameScore = 0;
        animatorList = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            randomPiece();
        }
    }
    private static void randomPiece(){
        Random rand = new Random();
        int x;
        int y;
        do {
            x = rand.nextInt(4);
            y = rand.nextInt(4);
        }while (gameData[x][y] != 0);

        int seed = rand.nextInt(2);
        if (seed == 0) {
            Game.gameData[x][y] = 2;
            View view = PieceFactory.creatPiece(mContext, 2);
            ViewGroup group = (ViewGroup) gridLayout.getChildAt(x + y * 4);
            group.addView(view);
        } else {
            Game.gameData[x][y] = 4;
            View view = PieceFactory.creatPiece(mContext, 4);
            ViewGroup group = (ViewGroup) gridLayout.getChildAt(x + y * 4);
            group.addView(view);
        }
        size++;
    }
    private static void updataScore(int score) {
        gameScore += score;
        activity = (MainActivity) Game.mContext;
        activity.updataScore();
    }
    public static void startNewGame(){
        for (int[] temp:gameData) {
            Arrays.fill(temp,0);
        }
        size = 0;
        for (int i = 0; i < 3; i++) {
            randomPiece();
        }
        refresh();
        gameScore = 0;
        activity.updataScore();
    }

}
