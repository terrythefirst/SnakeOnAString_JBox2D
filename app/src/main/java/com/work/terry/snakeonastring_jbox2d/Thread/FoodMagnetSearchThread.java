package com.work.terry.snakeonastring_jbox2d.Thread;

import android.util.Log;

import com.work.terry.snakeonastring_jbox2d.SnakeElements.FoodMagnet;
import com.work.terry.snakeonastring_jbox2d.SnakeElements.Snake;
import com.work.terry.snakeonastring_jbox2d.UI.LockDownAnimation;

/**
 * Created by Terry on 2018/2/2.
 */

public class FoodMagnetSearchThread extends Thread {
    float duration = 0;
    Snake snake;

    public FoodMagnetSearchThread(
            Snake snake
    ){
        this.snake = snake;
        this.duration +=snake.getMagneticDurationSetZero();
        Log.d("duration",""+duration);
    }
    @Override
    public void run(){
       int nowTime = 0;
       int interval = 1000;

        new LockDownAnimation(
                snake.snakeHead,
                snake.gamePlay.getDrawUtil(),
                true,
                3,
                0.5f
        ).start();
       while (nowTime<duration){
           this.duration += snake.getMagneticDurationSetZero();
           this.duration = (duration>snake.snakeMaxMagneticDuration)?snake.snakeMaxMagneticDuration:duration;

           nowTime ++;
           try {
               sleep(interval);
           }catch (Exception e){
               e.printStackTrace();
           }
       }

       snake.setIsMagnetic(false);
        new LockDownAnimation(
                snake.snakeHead,
                snake.gamePlay.getDrawUtil(),
                false,
                3,
                0.5f
        ).start();
    }
}
