package com.work.terry.snakeonastring_jbox2d.Thread;

import com.work.terry.snakeonastring_jbox2d.JBox2DElements.CircleBody;
import com.work.terry.snakeonastring_jbox2d.SnakeElements.Snake;
import com.work.terry.snakeonastring_jbox2d.SnakeElements.SnakeHead;
import com.work.terry.snakeonastring_jbox2d.Animation.JiggleAnimation;
import com.work.terry.snakeonastring_jbox2d.Util.Constant;

/**
 * Created by Terry on 2018/1/31.
 */

public class SnakeJumpAnimationThread extends Thread {
    Snake snake;
    int jumpLength;
    public SnakeJumpAnimationThread(Snake snake,int jumpLength){
        this.snake = snake;
        this.jumpLength = jumpLength;
    }
    @Override
    public void run(){
        int time = 0;
        int index = 0;
        int timeLimit = (int) (2* Constant.JumpMathFactor);
        boolean finished = false;
        while (!snake.paused&&!finished){
            if (!snake.gamePlay.IS_PLAYING) {
                try {
                    sleep(100);
                    continue;
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            if(index<snake.getLength()&&index<=jumpLength){
                CircleBody cc  = snake.snakeBodies.get(index);
                Thread animateThread = new JiggleAnimation(
                        cc,
                        50,
                        0.1f,

                        !(cc instanceof SnakeHead),
                        0.5f,
                        true
                        );
                animateThread.start();
                index++;
                try {
                    sleep(50);
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
            else finished = true;
        }
    }
}
