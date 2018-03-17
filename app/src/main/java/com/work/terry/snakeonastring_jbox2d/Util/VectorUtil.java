package com.work.terry.snakeonastring_jbox2d.Util;

import android.util.Log;

import org.jbox2d.common.Vec2;

/**
 * Created by Terry on 2017/12/28.
 */

public class VectorUtil {
    public static boolean isReverse2D(Vec2 v1,Vec2 v2){
        return ((v1.x*v2.x<0)||(v2.y*v1.y<0));
    }
    public static float calDistance(double dx,double dy){
        return (float)Math.sqrt(Math.pow(dx,2)+Math.pow(dy,2));
    }
    public static float calDistance(Vec2 v){
        return (float)Math.sqrt(Math.pow(v.x,2)+Math.pow(v.y,2));
    }
    public static Vec2 getCenterV2(Vec2 v1,Vec2 v2){
        return new Vec2((v1.x+v2.x)/2,(v1.y+v2.y)/2);
    }
    public static float dotMul2D(Vec2 v1,Vec2 v2){
        return v2.x*v1.y+v1.x*v2.y;
    }
    public static float[] normalize2D(float x,float y){
        double ds =  Math.sqrt(Math.pow(x,2)+Math.pow(y,2));
        return new float[]{(float)(x/ds),(float)(y/ds)};
    }
    public static Vec2 Mul2D(Vec2 v,float num){
        return new Vec2(v.x*num,v.y*num);
    }
    public static Vec2 normalize2D(Vec2 v){
        double ds =  Math.sqrt(Math.pow(v.x,2)+Math.pow(v.y,2));
        if(ds==0)return new Vec2(0,0);
        //Log.d("normalize2D","ds = "+ds);
        return new Vec2((float)(v.x/ds),(float)(v.y/ds));
    }
    public static float calBodyRadians(float dx,float dy){
        if (dx==0){
            if (dy<0)return (float) Math.toRadians(0);
            else return (float) Math.toRadians(180);
        }else {
            return (float)( Math.atan(dy/dx)+Math.PI/2);
        }
    }
    public static float calRotateAngleDegrees(float dx,float dy){
        float res = 0;
        if(dx >0){
            if (dy > 0) {
                res = (float)Math.toDegrees(Math.atan(dx/dy));
            }else if(dy < 0){
                res = 90 - (float)Math.toDegrees(Math.atan(dy/dx));
            }else if(dy == 0){
                res = 90;
            }
        }else if(dx <0){
            if (dy > 0) {
                res = 270 - (float)Math.toDegrees(Math.atan(dy/dx));
            }else if(dy < 0){
                res = 180 + (float)Math.toDegrees(Math.atan(dx/dy));
            }else if(dy == 0){
                res = 270;
            }
        }else if(dx == 0){
            if (dy > 0) {
                res = 0;
            }else if(dy < 0){
                res = 180;
            }else if(dy == 0){
                res = 0;
            }
        }
        return res;
    }
    public static float calRotateAngleRadius(float dx,float dy){
        return (float) Math.toRadians(calRotateAngleDegrees(dx,dy));
    }
    public static Vec2 plusV2D(Vec2 v1,Vec2 v2){
        return new Vec2(v1.x+v2.x,v1.y+v2.y);
    }
    public static Vec2 minusV2D(Vec2 v1,Vec2 v2){
        return new Vec2(v1.x-v2.x,v1.y-v2.y);
    }
}
