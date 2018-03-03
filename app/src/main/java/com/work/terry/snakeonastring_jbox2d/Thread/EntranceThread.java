package com.work.terry.snakeonastring_jbox2d.Thread;

import com.work.terry.snakeonastring_jbox2d.Animation.UniformMotionAnimation;
import com.work.terry.snakeonastring_jbox2d.GamePlayElements.Entrance;
import com.work.terry.snakeonastring_jbox2d.SnakeElements.Snake;
import com.work.terry.snakeonastring_jbox2d.SnakeElements.SnakeNode;
import com.work.terry.snakeonastring_jbox2d.Util.Constant;

import org.jbox2d.common.Vec2;

/**
 * Created by Terry on 2018/3/3.
 */

public class EntranceThread extends Thread{
    Snake snake;
    Entrance entrance;

    boolean enterred = false;

    public EntranceThread(Snake snake,Entrance entrance){
        this.snake = snake;
        this.entrance = entrance;
    }

    @Override
    public void run(){
        while (snake.gamePlay.IS_PLAYING&&!enterred){
            SnakeNode snakeNode = (SnakeNode) snake.snakeBodies.get(snake.getLength()-1);
            if(snakeNode.y< Constant.SCREEN_HEIGHT-snakeNode.radius)enterred = true;
            try {
                sleep(50);
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        Thread thread = new UniformMotionAnimation(
                entrance,
                new Vec2(entrance.x,Constant.SCREEN_HEIGHT+entrance.height),
                0.5f
        );
        thread.start();
        try {
            thread.join();
            snake.gamePlay.constructButtonWall();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
