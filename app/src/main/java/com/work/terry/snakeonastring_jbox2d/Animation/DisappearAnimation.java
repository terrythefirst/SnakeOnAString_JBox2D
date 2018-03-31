package com.work.terry.snakeonastring_jbox2d.Animation;

import com.work.terry.snakeonastring_jbox2d.Thread.Stoppable;
import com.work.terry.snakeonastring_jbox2d.UI.GameElement;
import com.work.terry.snakeonastring_jbox2d.Util.MyMath;

/**
 * Created by Terry on 2018/3/8.
 */

public class DisappearAnimation extends Thread implements Stoppable{
    GameElement gameElement;
    boolean shouldDie = false;
    boolean disappear;
    float timeSpan;
    float targetWidth;
    float targetHeight;

    public DisappearAnimation(
            GameElement gameElement,
            boolean disappear,
            float timeSpan
    ){
        this.gameElement = gameElement;
        this.disappear = disappear;
        this.timeSpan = timeSpan;

        targetWidth = gameElement.width;
        targetHeight = gameElement.height;

    }
    @Override
    public void run(){
        long sleepInterval = 10;
        int times = (int) (timeSpan*1000/sleepInterval);
        int timeCount = 0;
        while (!shouldDie&&timeCount<times){
            float ratio = MyMath.smoothStep(0,1,timeCount*1.0f/times);
            if(!disappear)ratio = 1-ratio;

            gameElement.scaleWidth = -ratio*targetWidth;
            gameElement.scaleHeight = -ratio*targetHeight;

            timeCount++;
            try {
                sleep(sleepInterval);
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }

    @Override
    public void setShouldDie() {
        shouldDie = true;
    }
}
