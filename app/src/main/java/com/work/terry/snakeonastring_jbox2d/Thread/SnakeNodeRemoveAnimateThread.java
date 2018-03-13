package com.work.terry.snakeonastring_jbox2d.Thread;

import android.util.Log;

import com.work.terry.snakeonastring_jbox2d.Animation.FountainAnimation;
import com.work.terry.snakeonastring_jbox2d.SnakeElements.SnakeNode;
import com.work.terry.snakeonastring_jbox2d.SnakeElements.SnakeNodeAnimateDraw;
import com.work.terry.snakeonastring_jbox2d.Util.Constant;
import com.work.terry.snakeonastring_jbox2d.Util.DrawUtil;

/**
 * Created by Terry on 2018/1/31.
 */

public class SnakeNodeRemoveAnimateThread extends Thread {
    Thread waitThread;
    private int code = Constant.SNAKE_ANIMATION_REMOVE;
    SnakeNode snakeNode;
    //SnakeNodeAnimateDraw snakeNodeAnimateDraw;
    DrawUtil drawUtil;

    public SnakeNodeRemoveAnimateThread (SnakeNode snakeNode, DrawUtil drawUtil,Thread waitThread){
        this.snakeNode = snakeNode;
        //this.snakeNodeAnimateDraw = new SnakeNodeAnimateDraw(snakeNode,code);
        this.drawUtil = drawUtil;
        this.waitThread = waitThread;
        //drawUtil.addToCenterLayer(snakeNodeAnimateDraw);
    }
    @Override
    public void run(){
        snakeNode.snake.beginAddAnimation();
        try {
            waitThread.join();
        }catch (Exception e){
            e.printStackTrace();
        }

        if(snakeNode.snake.getSnakeAjaxLength()<snakeNode.snake.getLength()) {
            snakeNode.setDoDraw(false);
            new FountainAnimation(
                    snakeNode.snake.gamePlay.getDrawUtil(),
                    1,
                    snakeNode.x,snakeNode.y,
                    40,
                    40,
                    400,
                    20,
                    100,
                    0.5F,
                    snakeNode.colorFloats
            );
//            while (!snakeNode.snake.isDead() && !snakeNodeAnimateDraw.isFinished()) {
//                snakeNodeAnimateDraw.AnimationStep(-0.5f);
//
//                try {
//                    sleep(10);
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//            }

            //drawUtil.deleteElement(snakeNodeAnimateDraw);
            //snakeNodeAnimateDraw.setDoDraw(false);
            //drawUtil.addToRemoveSequence(snakeNodeAnimateDraw);

            snakeNode.sendDeleteTask();
            snakeNode.snake.snakeBodies.remove(snakeNode);
            drawUtil.addToRemoveSequence(snakeNode);
        }
        snakeNode.snake.endAddAnimation();
        //drawUtil.addToCenterLayer(snakeNode);

        //Log.d("SnakeNodeRemoveAnimateThread",snakeNodeAnimateDraw.id+"finished!");
    }
}
