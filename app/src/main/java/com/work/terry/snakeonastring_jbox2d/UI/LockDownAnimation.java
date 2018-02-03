package com.work.terry.snakeonastring_jbox2d.UI;

/**
 * Created by Terry on 2018/2/3.
 */

public class LockDownAnimation extends Thread {
    GameElements gameElements;
    boolean expand;


    public LockDownAnimation(
            GameElements gameElements,
            boolean expand,
            float maxScaleRate){
        this.gameElements = gameElements;
        this.expand = expand;
    }
    @Override
    public void run(){

    }
}
