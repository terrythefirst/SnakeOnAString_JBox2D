package com.work.terry.snakeonastring_jbox2d.UI;

import org.jbox2d.common.Vec2;

/**
 * Created by Terry on 2018/2/27.
 */

public class UniformMotionAnimation extends Thread {
    GameElements gameElements;
    Vec2 targetPoint;
    float timeSpan;

    Vec2 V;

    long sleepInterval = 10;

    public UniformMotionAnimation(
            GameElements gameElements,
            Vec2 targetPoint,
            float timeSpan
    ){
        this.gameElements = gameElements;
        this.targetPoint = targetPoint;
        this.timeSpan = timeSpan;
        this.V = new Vec2((targetPoint.x-gameElements.x)/timeSpan,(targetPoint.y-gameElements.y)/timeSpan);
    }
    @Override
    public void run(){
        long time = 0;
        while (time<timeSpan*1000){
            gameElements.x+=V.x*sleepInterval*0.001f;
            gameElements.y+=V.y*sleepInterval*0.001f;

            time+=sleepInterval;
            try {
                sleep(sleepInterval);
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }
}
