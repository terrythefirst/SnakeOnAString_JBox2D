package com.work.terry.snakeonastring_jbox2d.Animation;

import android.util.Log;

import com.work.terry.snakeonastring_jbox2d.UI.GameElement;
import com.work.terry.snakeonastring_jbox2d.Util.VectorUtil;

import org.jbox2d.common.Vec2;

/**
 * Created by Terry on 2018/2/22.
 */

public class PullMoveAnimation extends Thread {
    Vec2 targetPoint;
    Vec2 lastTimeA;
    Vec2 V = new Vec2(0,0);
    private boolean finished =false;
    GameElement gameElement;
    int maxPassTimes;
    float K;

    private Vec2 AVector;
    private float sleepInterval = 0.01f;

    private int passTimes = -1;

    public PullMoveAnimation (
           GameElement gameElement,
           Vec2 targetPoint,
           float K,
           float sleepInterval,
           int maxPassTimes
    ){
        this.gameElement = gameElement;
        this.targetPoint = targetPoint;
        this.K = K;
        this.sleepInterval = sleepInterval;
        this.maxPassTimes = maxPassTimes;

        this.lastTimeA = new Vec2(targetPoint.x- gameElement.x,targetPoint.y- gameElement.y);
        this.AVector = lastTimeA;
    }

    @Override
    public void run(){

        while (passTimes/2 <= maxPassTimes){
            lastTimeA = AVector;
            AVector = new Vec2(targetPoint.x- gameElement.x,targetPoint.y- gameElement.y);
            //AVector = new Vec2((float)(Math.pow(AVector.x,2)*2),(float)(Math.pow(AVector.y,2)*2));
            //AVector = new Vec2((float)(Math.pow(AVector.x,2)*K),(float)(Math.pow(AVector.y,2)*K));
            AVector = VectorUtil.Mul2D(VectorUtil.normalize2D(AVector),K);

            V.x += AVector.x;
            V.y += AVector.y;

            gameElement.x += V.x*sleepInterval+AVector.x*((float)(Math.pow(sleepInterval,2)));
            gameElement.y += V.y*sleepInterval+AVector.y*((float)(Math.pow(sleepInterval,2)));

            Log.d("AVector","x="+AVector.x+" y="+AVector.y);
            Log.d("passTimes",passTimes+"");
            if(VectorUtil.isReverse2D(lastTimeA,AVector))passTimes+=2;
            try {
                sleep((long)(sleepInterval*1000));
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        gameElement.setXY(targetPoint.x,targetPoint.y);
    }
}
