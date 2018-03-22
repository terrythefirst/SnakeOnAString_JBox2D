package com.work.terry.snakeonastring_jbox2d.Thread;

import com.work.terry.snakeonastring_jbox2d.SnakeElements.SnakeFood;
import com.work.terry.snakeonastring_jbox2d.Animation.JiggleAnimation;

/**
 * Created by Terry on 2018/2/2.
 */

public class SnakeFoodJumpScoreThread extends Thread {
    SnakeFood snakeFood;
    int originScore;
    int targetScore;

    int times;
    int perReduce;

    public SnakeFoodJumpScoreThread(
            SnakeFood snakeFood,
            int targetScore,
            int times
    ){
        this.snakeFood = snakeFood;
        this.originScore = snakeFood.score;
        this.targetScore = targetScore;
        this.times = times;

        perReduce = (originScore-targetScore)/times;
    }
    @Override
    public void run(){
        while (!snakeFood.eatean&&snakeFood.score > targetScore){
            if (!snakeFood.gamePlay.IS_PLAYING) {
                try {
                    sleep(100);
                    continue;
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            new JiggleAnimation(
                    snakeFood,
                    50,
                    0.6f,

                    true,
                    0.5f,
                    true
            ).run();

            if (!snakeFood.gamePlay.IS_PLAYING) {
                try {
                    sleep(100);
                    continue;
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            try {
                sleep(1000);
            }catch (Exception e){
                e.printStackTrace();
            }
            snakeFood.score -=(perReduce==0)?1:perReduce;
        }
    }
}
