package com.work.terry.snakeonastring_jbox2d.Animation;

import com.work.terry.snakeonastring_jbox2d.SnakeElements.SnakeHead;
import com.work.terry.snakeonastring_jbox2d.SnakeElements.SnakeSkinManager;
import com.work.terry.snakeonastring_jbox2d.Util.Constant;

/**
 * Created by Terry on 2018/3/4.
 */

public class SnakeEatingHeadAnimation extends Thread {
    SnakeHead snakeHead;

    int times = 3;
    public SnakeEatingHeadAnimation(
            SnakeHead snakeHead,
            int times
    ){
        this.snakeHead =snakeHead;
        this.times = times;
    }
    @Override
    public void run(){
        int time = 0;
        boolean open = false;
        while (time<times){
            if (open)snakeHead.changeFace(SnakeSkinManager.getSkin(snakeHead.snake.getSkinNumber(),Constant.SnakeHeadEatingOpenImgCode).getImg());
            else snakeHead.changeFace(SnakeSkinManager.getSkin(snakeHead.snake.getSkinNumber(),Constant.SnakeHeadEatingCloseImgCode).getImg());

            open=!open;
            if(open)time++;
            try {
                sleep(500);
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        snakeHead.changeFace(SnakeSkinManager.getSkin(snakeHead.snake.getSkinNumber(),Constant.SnakeHeadImgCode).getImg());
    }
}
