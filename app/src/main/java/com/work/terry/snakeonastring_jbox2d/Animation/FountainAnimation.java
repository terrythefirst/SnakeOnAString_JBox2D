package com.work.terry.snakeonastring_jbox2d.Animation;

import android.util.Log;

import com.work.terry.snakeonastring_jbox2d.UI.GameElement;
import com.work.terry.snakeonastring_jbox2d.UI.SingleGrain;
import com.work.terry.snakeonastring_jbox2d.Util.Constant;
import com.work.terry.snakeonastring_jbox2d.Util.DrawUtil;
import com.work.terry.snakeonastring_jbox2d.Util.TexDrawer;

import org.jbox2d.common.Vec2;

import java.nio.channels.FileLock;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Terry on 2018/3/12.
 */

public class FountainAnimation extends GameElement {
    DrawUtil drawUtil;
    Vec2 centerXY;
    List<SingleGrain> singleGrains = new ArrayList<>();
    float maxSingleGrainRaius;
    long timeSpan;
    int grainCount;
    float speedSpan;
    float jumpSpan;

    static float speedStep = 0.02f;


    float[] colorFloats;

    long timeStamp = 0;
    long startTimeStamp = 0;
    float G;
    float vZInit;


    public FountainAnimation(
            DrawUtil drawUtil,
            int id,
            float x, float y,
            int grainCount,

            float maxSingleGrainRadius,

            float explosiveRadius,
            float jumpSpan,

            float speedSpan,

            float timeSpan,
            float[] colorFloats
    ) {
        super(
                "FountainAnimation "+id,
                0,0,0,0,0,0,0,0,0,0,
                null
        );
        this.jumpSpan = jumpSpan;
        this.drawUtil = drawUtil;
        this.maxSingleGrainRaius = maxSingleGrainRadius;
        this.timeSpan = (long)(timeSpan*1000);
        this.centerXY = new Vec2(x,y);
        this.grainCount = grainCount;
        this.colorFloats = colorFloats;
        this.speedSpan = speedSpan;

        this.G = (float) (2*jumpSpan/Math.pow(timeSpan/2,2));
        this.vZInit =  G*timeSpan/2;

        for(int i=0;i<grainCount;i++){
            double elevation=Math.random()*Math.PI*2;	//仰角
            float speedX = ((float)Math.random()*speedSpan);
            float speedY = ((float)Math.random()*speedSpan);
            float vy=(float)(speedX*Math.sin(elevation));//分解出3个轴的初速度
            float vx=(float)(speedY*Math.cos(elevation));
            //float vz=(float)(vZInit/2*Math.cos(elevation)+vZInit/2);
            float radius = (float) (Math.random()*maxSingleGrainRaius/2+maxSingleGrainRaius/2);
            double rho = Math.random()*explosiveRadius;
            double theta = Math.random()*Math.PI*2;
            //Log.e("vZInit",vZInit+"");
            singleGrains.add(
                    new SingleGrain(
                            centerXY.x+(float)(rho*Math.cos(theta)),
                            centerXY.y+(float)(rho*Math.sin(theta)),
                            centerXY,
                            (float) Math.random()*radius,
                            explosiveRadius,
                            G,
                            vx,vy,vZInit,
                            colorFloats,
                            10,
                            Constant.ButtonBlockFloorColorFactor
                    )
            );
        }
        drawUtil.addToAnimationLayer(this);
        this.startTimeStamp = System.nanoTime()/1000000;
    }
    public void step(){
        long currTimeStamp = System.nanoTime()/1000000;
        long dt = currTimeStamp-timeStamp;
        //Log.e("fountain step","currTimeStamp-startTimeStamp="+(currTimeStamp-startTimeStamp));
//        if(currTimeStamp-startTimeStamp > timeSpan){
//            //Log.e("fountain step","remove");
//            drawUtil.addToRemoveSequence(this);
//        }
        boolean remove = true;
        if(dt>10){
            for(SingleGrain sg:singleGrains){
                sg.dt = speedStep;
                if(sg.doDraw)remove = false;
            }
            timeStamp = currTimeStamp;
        }else {
            for(SingleGrain sg:singleGrains){
                sg.dt = 0;
                if(sg.doDraw)remove = false;
            }
        }
        if(remove){
            //Log.e("fountain step","remove");
            drawUtil.addToRemoveSequence(this);
        }
    }
    @Override
    public void drawSelf(TexDrawer painter){
        step();
        for(SingleGrain sg:singleGrains){
            sg.drawSelf(painter);
        }
    }
    @Override
    public void drawFloorShadow(TexDrawer painter){
        for(SingleGrain sg:singleGrains){
            sg.drawFloorShadow(painter);
        }
    }
}
