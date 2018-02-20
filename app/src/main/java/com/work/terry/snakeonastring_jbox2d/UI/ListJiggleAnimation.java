package com.work.terry.snakeonastring_jbox2d.UI;

import java.util.List;

/**
 * Created by Terry on 2018/2/20.
 */

public class ListJiggleAnimation extends Thread {
    private List<GameElements> list;
    private boolean doScale;
    private boolean doHeight;
    private float jumSpan;
    private float perTimeSpan;
    private float maxScaleRate;
    private long sleepInterval;

    public ListJiggleAnimation(
            List<GameElements> list,
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
        int index = 0;
        while (true){
            for (GameElements ge:list){
                new JiggleAnimation(
                        ge,
                        jumSpan,
                        perTimeSpan,
                        doScale,
                        maxScaleRate,
                        doHeight
                ).start();


                try {
                    sleep((long)(perTimeSpan*1000/3));
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
