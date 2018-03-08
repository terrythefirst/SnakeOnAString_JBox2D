package com.work.terry.snakeonastring_jbox2d.Thread;

import com.work.terry.snakeonastring_jbox2d.Animation.BreathAnimation;
import com.work.terry.snakeonastring_jbox2d.UI.GameElement;

/**
 * Created by Terry on 2018/3/8.
 */

public class StoppableBreathAnimationThread extends Thread {
    boolean shouldDie = false;
    GameElement gameElement;
    float jumpSpan;
    boolean doScale;
    float maxScaleRate;
    boolean doHeight;
    float timeSpan;

    public StoppableBreathAnimationThread(
            GameElement gameElement,
            float jumpSpan,
            boolean doScale,
            float maxScaleRate,
            boolean doHeight,
            float timeSpan
    ){
        this.gameElement = gameElement;
        this.jumpSpan = jumpSpan;
        this.doHeight = doHeight;
        this.doScale = doScale;
        this.maxScaleRate = maxScaleRate;
        this.timeSpan = timeSpan;
    }
    public void setShouldDie(){
        shouldDie = true;
    }
    @Override
    public void run(){
        while(!shouldDie){
            Thread thread = new BreathAnimation(
                    gameElement,
                    doHeight,
                    jumpSpan,
                    doScale,
                    maxScaleRate,
                    timeSpan
            );

            thread.start();
            try {
                thread.join();
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }
}
