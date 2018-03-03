package com.work.terry.snakeonastring_jbox2d.SurfaceViewAndActivity;

import android.content.SharedPreferences;
import android.view.MotionEvent;

import com.work.terry.snakeonastring_jbox2d.UI.Button;
import com.work.terry.snakeonastring_jbox2d.Util.DrawUtil;

import java.util.ArrayList;
import java.util.List;

import static com.work.terry.snakeonastring_jbox2d.Util.Constant.BackgroundImg;

/**
 * Created by Terry on 2018/1/26.
 */

public abstract class MyView {
    public DrawUtil drawUtil;
    public List<Button> buttons = new ArrayList<>();
    public Button nowPressedButton;
    public void setDrawUtilAndBacktoundImg(String Img){
        setBackgroundImg(Img);
    }
    public void setBackgroundImg(String Img){
        if(drawUtil==null){
            drawUtil = new DrawUtil(Img);
        }else {
            drawUtil.setBackgroundImg(Img);
        }
    }
    public Button whichButtonTouched(float x,float y){
        for(Button b:buttons){
            if (b.testTouch(x,y))return b;
        }
        return null;
    }
    public void whenUp(float x,float y){
        if(nowPressedButton!=null)
            nowPressedButton.whenReleased(nowPressedButton.testTouch(x,y));
    }
    public DrawUtil getDrawUtil(){
        return drawUtil;
    }
    public abstract void onTouchEvent(MotionEvent event, int x, int y);
    public abstract void onResume();
    public abstract void onPause(SharedPreferences.Editor editor);
}
