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
            double elevation=0.35f*Math.random()*Math.PI+Math.PI*0.15f;	//仰角
            double direction=Math.random()*Math.PI*2;		//方位角
            float vy=(float)(speedSpan*Math.sin(elevation));//分解出3个轴的初速度
            float vx=(float)(speedSpan*Math.cos(elevation)*Math.cos(direction));
            float vz=(float)(vZInit/2*Math.cos(elevation)*Math.sin(direction))+vZInit/2;
            float radius = (float) (Math.random()*maxSingleGrainRaius/2+maxSingleGrainRaius/2);

            singleGrains.add(
                    new SingleGrain(
                            centerXY,
                            (float) Math.random()*radius,
                            explosiveRadius,
                            G,
                            vx,vy,vz,
                            colorFloats,
                            5,
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
        Log.e("fountain step","currTimeStamp-startTimeStamp="+(currTimeStamp-startTimeStamp));
        if(currTimeStamp-startTimeStamp > timeSpan){
            Log.e("fountain step","remove");
            drawUtil.addToRemoveSequence(this);
        }
        if(dt>10){
            for(SingleGrain sg:singleGrains){
                sg.timeSpan = +speedStep;
                if(sg.timeSpan>10){
                    sg.timeSpan = 0;
                }
            }
            timeStamp = currTimeStamp;
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
