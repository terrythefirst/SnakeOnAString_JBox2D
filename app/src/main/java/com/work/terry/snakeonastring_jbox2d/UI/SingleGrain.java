package com.work.terry.snakeonastring_jbox2d.UI;

import android.util.Log;

import com.work.terry.snakeonastring_jbox2d.Util.Constant;
import com.work.terry.snakeonastring_jbox2d.Util.MyMath;
import com.work.terry.snakeonastring_jbox2d.Util.TexDrawer;
import com.work.terry.snakeonastring_jbox2d.Util.VectorUtil;

import org.jbox2d.common.Vec2;

import static com.work.terry.snakeonastring_jbox2d.Util.Constant.RATE;

/**
 * Created by Terry on 2018/3/12.
 */

public class SingleGrain extends GameElement {
    private static int SingleGrainCount = 0 ;
    Vec2 centerXY;
    float centerRadius;
    float radius;
    float vx;
    float vy;
    float vz;

    public float timeSpan = 0;
    public float dt=0;
    public float G;

    public SingleGrain(
            float x,float y,
            Vec2 centerXY,
            float radius,
            float centerRadius,
            float G,
            float vx,
            float vy,
            float vz,
            float[] colorFloats,
            float defaultHeight,
            float floorShadowColorFactor) {
        super(
                "SingleGrain "+SingleGrainCount++,
                x, y,
                radius*2, radius*2,
                colorFloats, defaultHeight,
                0,0,0,
                floorShadowColorFactor,
                Constant.SnakeBodyImg
        );
        this.G = G;
        this.centerXY = centerXY;
        this.centerRadius = centerRadius;
        this.radius = radius;
        this.vx = vx;
        this.vy = vy;
        this.vz = vz;
        setDoDrawHeight(false);
        setColorFloats(colorFloats);
    }
    public void stepMove(){
        if(!doDraw)return;
        float centerDistance = VectorUtil.calDistance(x-centerXY.x,y-centerXY.y);
        if(vz==0||centerDistance>centerRadius)setDoDraw(false);
        float ratio =MyMath.smoothStep(0,centerRadius,centerDistance);
        opacityFactor = 1-ratio;
        scaleWidth = -width*ratio;
        scaleHeight = -height*ratio;

//        x =centerXY.x+ vx*timeSpan*RATE;
//        y =centerXY.y+ vy*timeSpan*RATE;
        timeSpan +=dt;
        x += vx*dt*RATE;
        y += vy*dt*RATE;
       float height = (float) (vz*timeSpan-0.5*timeSpan*timeSpan*G);
        if(height<=0){
            vz = vz*0.65f;
            timeSpan=0;
        }
        if(Math.abs(vz)<0.1f)vz=0;
        //else  Log.e("Math.abs(vz)",Math.abs(vz)+"");
        jumpHeight = height;
        //Log.e("singleGrain jumpHeight",jumpHeight+"");
    }
    @Override
    public void drawSelf(TexDrawer painter){
        stepMove();
        super.drawSelf(painter);
    }
}
