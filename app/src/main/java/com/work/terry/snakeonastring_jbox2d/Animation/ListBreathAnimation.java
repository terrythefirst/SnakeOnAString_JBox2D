package com.work.terry.snakeonastring_jbox2d.Animation;

import com.work.terry.snakeonastring_jbox2d.UI.GameElement;

import java.util.List;

/**
 * Created by Terry on 2018/2/20.
 */

public class ListBreathAnimation extends Thread {
    private List<GameElement> list;
    private boolean doScale;
    private boolean doHeight;
    private float jumSpan;
    private float perTimeSpan;
    private float maxScaleRate;
    private long sleepInterval;

    public ListBreathAnimation(
            List<GameElement> list,
            float jumpSpan,
            float perTimeSpan,
            long sleepInterval,

            boolean doScale,
            float maxScaleRate,
            boolean doHeight
    ){
        this.list = list;

        this.doHeight = doHeight;
        this.doScale = doScale;
        this.jumSpan = jumpSpan;
        this.perTimeSpan = perTimeSpan;
        this.maxScaleRate = maxScaleRate;
        this.sleepInterval = sleepInterval;
    }

    @Override
    public void run(){
        while (true){
            for (GameElement ge:list){
                Thread thread = new BreathAnimation(
                        ge,
                        doHeight,
                        jumSpan,
                        doScale,
                        maxScaleRate,
                        perTimeSpan
                );
                thread.start();

                try {
                    thread.join();
                }catch (Exception e){
                    e.printStackTrace();
                }
            }

            try{
                sleep(sleepInterval);
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }
}
