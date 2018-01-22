package com.work.terry.snakeonastring_jbox2d.SnakeElements;

import com.work.terry.snakeonastring_jbox2d.Util.VectorUtil;

/**
 * Created by Terry on 2018/1/22.
 */

public class SnakeHeadTarget {
    public float TargetHeadX = 720;
    public float TargetHeadY = 1280;
    public float TargetHeadVX = 0;
    public float TargetHeadVY = 1;

    public SnakeHeadTarget(float touchX,float touchY,float HeadX,float HeadY){
        setTarget(touchX,touchY,HeadX,HeadY);
    }
    public void setTarget(float touchX,float touchY,float HeadX,float HeadY){
        TargetHeadX = touchX;
        TargetHeadY = touchY;
        float[] normalTXY = VectorUtil.normalize2D(touchX - HeadX,touchY - HeadY);
        TargetHeadVX = normalTXY[0];
        TargetHeadVY = normalTXY[1];
        //Log.d("Target","target x="+TargetHeadX+" y="+TargetHeadY+"\n vx="+TargetHeadVX+" vy="+TargetHeadVY);
    }
}
