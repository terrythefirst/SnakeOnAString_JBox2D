package com.work.terry.snakeonastring_jbox2d.UI;

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
    GameElements gameElements;
    int maxPassTimes;
    float K;

    private Vec2 AVector;
    private float sleepInterval = 0.01f;

    private int passTimes = -1;

    public PullMoveAnimation (
           GameElements gameElements,
           Vec2 targetPoint,
           float K,
           float sleepInterval,
           int maxPassTimes
    ){
        this.gameElements = gameElements;
        this.targetPoint = targetPoint;
        this.K = K;
        this.sleepInterval = sleepInterval;
        this.maxPassTimes = maxPassTimes;

        this.lastTimeA = new Vec2(targetPoint.x-gameElements.x,targetPoint.y-gameElements.y);
        this.AVector = lastTimeA;
    }

    @Override
    public void run(){

        while (passTimes/2 <= maxPassTimes){
            lastTimeA = AVector;
            AVector = new Vec2(targetPoint.x-gameElements.x,targetPoint.y-gameElements.y);
            AVector = VectorUtil.Mul2D(VectorUtil.normalize2D(AVector),K);

            V.x += AVector.x;
            V.y += AVector.y;

            gameElements.x += V.x*sleepInterval+AVector.x*((float)(Math.pow(sleepInterval,2)));
            gameElements.y += V.y*sleepInterval+AVector.y*((float)(Math.pow(sleepInterval,2)));

            if(VectorUtil.dotMul2D(lastTimeA,AVector)<0)passTimes+=2;
            try {
                sleep((long)(sleepInterval*1000));
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        gameElements.setXY(targetPoint.x,targetPoint.y);
    }
}
