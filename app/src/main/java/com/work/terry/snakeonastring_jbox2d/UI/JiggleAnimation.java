package com.work.terry.snakeonastring_jbox2d.UI;

/**
 * Created by Terry on 2018/1/31.
 */

public class JiggleAnimation extends Thread{
    GameElements gameElements;
    float jumpHeight;
    float timeSpan;
    float times;
    long interval;

    public JiggleAnimation(
            GameElements gameElements,
            float jumpHeight,
            int timeSpan,
            int times,
            long interval
    ){
        this.gameElements = gameElements;
        this.jumpHeight = jumpHeight;
        this.timeSpan = timeSpan;
        this.times = times;
        this.interval = interval;
    }
    @Override
    public void run(){
        int nowTime = 0;

        while (nowTime<times){



            try {
                sleep(interval);
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }
}
