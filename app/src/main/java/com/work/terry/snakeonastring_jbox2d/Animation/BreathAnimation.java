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

    float defaultWidth;
    float defaultHeight;

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
        if(doScale){
            defaultHeight = gameElement.height;
            defaultWidth = gameElement.width;
        }
        this.maxScaleRate = maxScaleRate;

        sleepInterval = (long)(timeSpan*1000/360);
    }
    @Override
    public void run(){
        float degrees = 0;
        float jumpHeight = 0;
        while (degrees<360){
            jumpHeight= (float) Math.sin(Math.toRadians(degrees))*jumpSpan;
            if(doHeight)
                gameElement.jumpHeight =jumpHeight;

            if(doScale){
                float ratio = jumpHeight/jumpSpan* maxScaleRate;
                gameElement.scaleWidth = defaultWidth*ratio;
                gameElement.scaleHeight = defaultHeight*ratio;
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
