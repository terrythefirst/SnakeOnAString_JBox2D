package com.work.terry.snakeonastring_jbox2d.Thread;

import com.work.terry.snakeonastring_jbox2d.SnakeElements.FoodMagnet;
import com.work.terry.snakeonastring_jbox2d.SnakeElements.Snake;
/**
 * Created by Terry on 2018/2/2.
 */

public class FoodMagnetSearchThread extends Thread {
    FoodMagnet foodMagnet;
    Snake snake;

    int nowTime = 0;
    public FoodMagnetSearchThread(
            FoodMagnet foodMagnet,
            Snake snake
    ){
        this.foodMagnet = foodMagnet;
        this.snake = snake;
    }
    @Override
    public void run(){
       nowTime = 0;
       int interval = 1000;
       snake.setIsMagnetic(true);
       while (nowTime<foodMagnet.duration){
           nowTime ++;
           try {
               sleep(interval);
           }catch (Exception e){
               e.printStackTrace();
           }
       }
       snake.setIsMagnetic(false);
    }
    public void setNowTime(int x){
        nowTime = x;
    }

}
