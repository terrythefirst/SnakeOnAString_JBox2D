package com.work.terry.snakeonastring_jbox2d.Animation;


import com.work.terry.snakeonastring_jbox2d.UI.GameElement;

/**
 * Created by Terry on 2018/2/3.
 */

public class BreathAnimation extends Thread {
    GameElement gameElement;
    float jumpSpan;
    float maxScaleRate;
    boolean doScale;
    boolean doHeight;

//    public boolean stopped = false;
    int times;
    long sleepInterval;

    public BreathAnimation(
            GameElement gameElement,
            boolean doHeight,
            float jumpSpan,
            boolean doScale,
            float maxScaleRate,
            float timeSpan
    ){
        this.doHeight = doHeight;
        this.gameElement = gameElement;
        this.jumpSpan = jumpSpan;
        this.doScale = doScale;
        this.maxScaleRate = maxScaleRate;

        sleepInterval = (long)(timeSpan*1000/180);
    }
    @Override
    public void run(){
        float degrees = 0;
        while (degrees<180){
            if(doHeight)
                gameElement.jumpHeight = (float) Math.abs(Math.sin(Math.toRadians(degrees)))*jumpSpan;

            if (doScale){
                gameElement.scaleWidth = (jumpSpan- gameElement.jumpHeight)*maxScaleRate;
                gameElement.scaleHeight = (jumpSpan- gameElement.jumpHeight)*maxScaleRate;
            }

            degrees+=1f;
            try {
                sleep(sleepInterval);
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }
}
