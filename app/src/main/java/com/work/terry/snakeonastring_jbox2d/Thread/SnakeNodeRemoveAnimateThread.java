package com.work.terry.snakeonastring_jbox2d.Thread;

import android.util.Log;

import com.work.terry.snakeonastring_jbox2d.SnakeElements.SnakeNode;
import com.work.terry.snakeonastring_jbox2d.SnakeElements.SnakeNodeAnimateDraw;
import com.work.terry.snakeonastring_jbox2d.Util.Constant;
import com.work.terry.snakeonastring_jbox2d.Util.DrawUtil;

/**
 * Created by Terry on 2018/1/31.
 */

public class SnakeNodeRemoveAnimateThread extends Thread {
    private int code = Constant.SNAKE_ANIMATION_REMOVE;
    SnakeNode snakeNode;
    SnakeNodeAnimateDraw snakeNodeAnimateDraw;
    DrawUtil drawUtil;

    public SnakeNodeRemoveAnimateThread (SnakeNode snakeNode, DrawUtil drawUtil){
        this.snakeNode = snakeNode;
        this.snakeNodeAnimateDraw = new SnakeNodeAnimateDraw(snakeNode,code);
        this.drawUtil = drawUtil;
        drawUtil.addToCenterLayer(snakeNodeAnimateDraw);
    }
    @Override
    public void run(){
        snakeNode.snake.beginAddAnimation();

        while(!snakeNodeAnimateDraw.isFinished()){
            snakeNodeAnimateDraw.AnimationStep(-0.5f);

            try {
                sleep(10);
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        //drawUtil.deleteElement(snakeNodeAnimateDraw);
        snakeNodeAnimateDraw.setDoDraw(false);
        drawUtil.addToRemoveSequence(snakeNodeAnimateDraw);

        snakeNode.snake.endAddAnimation();
        //drawUtil.addToCenterLayer(snakeNode);

        Log.d("SnakeNodeRemoveAnimateThread",snakeNodeAnimateDraw.id+"finished!");
    }
}
