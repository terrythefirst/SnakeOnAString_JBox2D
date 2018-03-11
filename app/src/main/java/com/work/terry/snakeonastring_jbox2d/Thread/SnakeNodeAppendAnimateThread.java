package com.work.terry.snakeonastring_jbox2d.Thread;

import android.content.SharedPreferences;
import android.util.Log;

import com.work.terry.snakeonastring_jbox2d.SnakeElements.Snake;
import com.work.terry.snakeonastring_jbox2d.SnakeElements.SnakeNode;
import com.work.terry.snakeonastring_jbox2d.SnakeElements.SnakeNodeAnimateDraw;
import com.work.terry.snakeonastring_jbox2d.Util.Constant;
import com.work.terry.snakeonastring_jbox2d.Util.DrawUtil;


/**
 * Created by Terry on 2018/1/27.
 */

public class SnakeNodeAppendAnimateThread extends Thread {
    Thread waitThread;
    private int code = Constant.SNAKE_ANIMATION_APPEND;
    SnakeNode snakeNode;
    SnakeNodeAnimateDraw snakeNodeAnimateDraw;
    DrawUtil drawUtil;

    public SnakeNodeAppendAnimateThread (SnakeNode snakeNode, DrawUtil drawUtil,Thread waitThread){
        this.snakeNode = snakeNode;
        this.snakeNodeAnimateDraw = new SnakeNodeAnimateDraw(snakeNode,code);
        this.drawUtil = drawUtil;
        this.waitThread  = waitThread;
        drawUtil.addToCenterLayer(snakeNodeAnimateDraw);
    }
    @Override
    public void run(){
        snakeNode.snake.beginAddAnimation();
        try {
            waitThread.join();
        }catch (Exception e){
            e.printStackTrace();
        }
        if(snakeNode.snake.getSnakeAjaxLength()>snakeNode.snake.getLength()) {
            while (!snakeNode.created){
                try {
                    sleep(10);
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
            while(!snakeNode.snake.isDead()&&!snakeNodeAnimateDraw.isFinished()){
                snakeNodeAnimateDraw.AnimationStep(0.5f);

                try {
                    sleep(10);
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
            //drawUtil.deleteElement(snakeNodeAnimateDraw);
            snakeNodeAnimateDraw.setDoDraw(false);
        }

        snakeNode.snake.endAddAnimation();
        snakeNode.snake.snakeBodies.add(snakeNode);
        snakeNode.setDoDraw(true);
        drawUtil.addToRemoveSequence(snakeNodeAnimateDraw);
        //drawUtil.addToCenterLayer(snakeNode);

        Log.d("SnakeNodeAppendAnimateThread",snakeNodeAnimateDraw.id+"finished!");
    }
}
