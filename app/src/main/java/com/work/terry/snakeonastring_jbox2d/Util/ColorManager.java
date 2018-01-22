package com.work.terry.snakeonastring_jbox2d.Util;

/**
 * Created by Terry on 2017/12/30.
 */

public class ColorManager {
    public static final float Colors[][] = new float[][]{
         {0.0f,0.0f,0.0f},
         {0.05f,0.005F,0.015F},
        {0.10f,0.10F,0.025F},//蛇的米白色
        {0.33f,0.94f,0.94f},//青色
        {0.95f,0.325f,0.103f},//橘色
        {0.0f,0.3F,0.7F},//天蓝
    };

    public static float[] getColor(int index){
        return Colors[index];
    }
}
