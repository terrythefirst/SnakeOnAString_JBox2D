package com.work.terry.snakeonastring_jbox2d.UI;


/**
 * Created by Terry on 2018/2/3.
 */

public class BreathAnimation extends Thread {
    GameElements gameElements;
    float jumpSpan;
    float maxScaleRate;
    boolean doScale;

    public boolean stopped = false;

    public BreathAnimation(
            GameElements gameElements,
            float jumpSpan,
            boolean doScale,
            float maxScaleRate){
        this.gameElements = gameElements;
        this.jumpSpan = jumpSpan;
        this.doScale = doScale;
        this.maxScaleRate = maxScaleRate;
    }
    @Override
    public void run(){
        float degrees = 0;
        while (!stopped){
            gameElements.jumpHeight = (float) Math.abs(Math.sin(Math.toRadians(degrees)))*jumpSpan;

            if (doScale){
                gameElements.scaleWidth = (jumpSpan-gameElements.jumpHeight)*maxScaleRate;
                gameElements.scaleHeight = (jumpSpan-gameElements.jumpHeight)*maxScaleRate;
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
