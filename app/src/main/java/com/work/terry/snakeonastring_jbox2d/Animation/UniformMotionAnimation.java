package com.work.terry.snakeonastring_jbox2d.Animation;

import com.work.terry.snakeonastring_jbox2d.UI.GameElement;

import org.jbox2d.common.Vec2;

/**
 * Created by Terry on 2018/2/27.
 */

public class UniformMotionAnimation extends Thread {
    GameElement gameElement;
    Vec2 targetPoint;
    float timeSpan;

    Vec2 V;

    long sleepInterval = 10;

    public UniformMotionAnimation(
            GameElement gameElement,
            Vec2 targetPoint,
            float timeSpan
    ){
        this.gameElement = gameElement;
        this.targetPoint = targetPoint;
        this.timeSpan = timeSpan;
        this.V = new Vec2((targetPoint.x- gameElement.x)/timeSpan,(targetPoint.y- gameElement.y)/timeSpan);
    }
    @Override
    public void run(){
        long time = 0;
        while (time<timeSpan*1000){
            gameElement.x+=V.x*sleepInterval*0.001f;
            gameElement.y+=V.y*sleepInterval*0.001f;

            time+=sleepInterval;
            try {
                sleep(sleepInterval);
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }
}
