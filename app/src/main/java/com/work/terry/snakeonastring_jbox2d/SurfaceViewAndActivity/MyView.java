package com.work.terry.snakeonastring_jbox2d.SurfaceViewAndActivity;

import android.content.SharedPreferences;
import android.view.MotionEvent;

import com.work.terry.snakeonastring_jbox2d.Util.DrawUtil;

import static com.work.terry.snakeonastring_jbox2d.Util.Constant.BackgroundImg;

/**
 * Created by Terry on 2018/1/26.
 */

public abstract class MyView {
    public DrawUtil drawUtil;
    public void setDrawUtilAndBacktoundImg(String Img){
        drawUtil = new DrawUtil(Img);
    }
    public DrawUtil getDrawUtil(){
        return drawUtil;
    }
    public abstract void onTouchEvent(MotionEvent event, int x, int y);
    public abstract void onResume();
    public abstract void onPause(SharedPreferences.Editor editor);
}
