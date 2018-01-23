package com.work.terry.snakeonastring_jbox2d.Util;

/**
 * Created by Terry on 2017/12/30.
 */

public class ColorManager {
    public static final float Colors[][] = new float[][]{
            {255,255,255},//白
            {139,131,134},//灰
            {255,250,240},//默认蛇的米白色
            {127,255,212},
            {255,69,0},
    };
    public static float[] getColorByRGB255(float[] color255){
        return new float[]{
                color255[0]/255,
                color255[1]/255,
                color255[2]/255,
        };
    }
    public static float[] getColor(int index){
        return getColorByRGB255(Colors[index]);
    }
}
