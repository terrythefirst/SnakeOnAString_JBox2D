package com.work.terry.snakeonastring_jbox2d.Thread;


import com.work.terry.snakeonastring_jbox2d.SnakeElements.Snake;

/**
 * Created by Terry on 2018/1/27.
 */

public class SnakeBodyAppendThread extends Thread {
    Snake snake;
    public SnakeBodyAppendThread(Snake snake){
        this.snake = snake;
    }
    @Override
    public void run(){
        while (!snake.isDead()&&snake.SnakeAddLength>0){
            snake.addBody();
            snake.SnakeAddLength--;

            try {
                sleep(100);
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }
}
