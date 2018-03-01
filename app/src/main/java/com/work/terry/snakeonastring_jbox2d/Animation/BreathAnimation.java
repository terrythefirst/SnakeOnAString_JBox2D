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

    public boolean stopped = false;

    public BreathAnimation(
            GameElement gameElement,
            float jumpSpan,
            boolean doScale,
            float maxScaleRate){
        this.gameElement = gameElement;
        this.jumpSpan = jumpSpan;
        this.doScale = doScale;
        this.maxScaleRate = maxScaleRate;
    }
    @Override
    public void run(){
        float degrees = 0;
        while (!stopped){
            gameElement.jumpHeight = (float) Math.abs(Math.sin(Math.toRadians(degrees)))*jumpSpan;

            if (doScale){
                gameElement.scaleWidth = (jumpSpan- gameElement.jumpHeight)*maxScaleRate;
                gameElement.scaleHeight = (jumpSpan- gameElement.jumpHeight)*maxScaleRate;
            }

            degrees+=1f;
            try {
                sleep(50);
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }
}
