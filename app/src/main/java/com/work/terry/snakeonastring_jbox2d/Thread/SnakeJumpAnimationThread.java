package com.work.terry.snakeonastring_jbox2d.Thread;

import com.work.terry.snakeonastring_jbox2d.SnakeElements.Snake;
import com.work.terry.snakeonastring_jbox2d.Util.Constant;
import com.work.terry.snakeonastring_jbox2d.Util.MyMath;

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
            if(index<snake.getLength()&&index<=jumpLength)
                snake.snakeBodies.get(index).jumpHeight =  MyMath.JumpMath(Constant.SnakeDefaultHeight  + 10 ,Constant.JumpMathFactor,time);
            else finished = true;

            if(time<timeLimit){
                time++;
            }else {
                time = 0;
                index++;
            }

            try {
                sleep(20);
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }


}
