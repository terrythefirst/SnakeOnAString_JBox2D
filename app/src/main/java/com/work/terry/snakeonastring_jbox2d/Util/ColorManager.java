package com.work.terry.snakeonastring_jbox2d.Util;

import android.graphics.Color;

/**
 * Created by Terry on 2017/12/30.
 */

public class ColorManager {
    public static final float Colors[][] = new float[][]{
            {255,255,255},//白
            {0,0,0},//黑
            {139,131,134},//灰
            {255,250,240},//默认蛇的米白色
            {82,194,170},//青绿
            {255,127,80},//橙色
            {100,220,255},//天蓝
            {255,216,0},//金黄
            {255,96,93},//红
            {40,91,116},//墨蓝
            {179,255,184},//浅绿
            {255,20,147},//深粉
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
