package com.work.terry.snakeonastring_jbox2d.Util;

import android.util.Log;

import java.nio.channels.FileLock;

/**
 * Created by Terry on 2018/1/21.
 */

public class MyMath {
    public static float smoothStep(float a,float b,float x){
        if (x<=a)return 0;
        else if(x>=b)return 1;
        else {
            float y = (x-a)/(b-a);
            return (float) (y*y*(3.0-2.0*y));
        }
    }
    public static float JumpMath(float jumpSpan,float JumpMathFactor,float x){
        float b = jumpSpan;
        float a = (float)(-jumpSpan/Math.pow(JumpMathFactor,2));
        return  b+a*(float)Math.pow(x-JumpMathFactor,2);
    }
}
