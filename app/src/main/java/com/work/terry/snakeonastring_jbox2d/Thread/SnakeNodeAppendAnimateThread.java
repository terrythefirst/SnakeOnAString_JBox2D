package com.work.terry.snakeonastring_jbox2d.Thread;

import android.content.SharedPreferences;
import android.util.Log;

import com.work.terry.snakeonastring_jbox2d.SnakeElements.Snake;
import com.work.terry.snakeonastring_jbox2d.SnakeElements.SnakeNode;
import com.work.terry.snakeonastring_jbox2d.SnakeElements.SnakeNodeAnimateDraw;
import com.work.terry.snakeonastring_jbox2d.Util.DrawUtil;

import static com.work.terry.snakeonastring_jbox2d.Util.Constant.SnakeBodyRadius;

/**
 * Created by Terry on 2018/1/27.
 */

public class SnakeNodeAppendAnimateThread extends Thread {
    SnakeNode snakeNode;
    SnakeNodeAnimateDraw snakeNodeAnimateDraw;
    DrawUtil drawUtil;

    public SnakeNodeAppendAnimateThread (SnakeNode snakeNode, DrawUtil drawUtil){
        this.snakeNode = snakeNode;
        this.snakeNodeAnimateDraw = new SnakeNodeAnimateDraw(snakeNode);
        this.drawUtil = drawUtil;
        drawUtil.addToCenterLayer(snakeNodeAnimateDraw);
    }
    @Override
    public void run(){
        while(!snakeNodeAnimateDraw.isFinished()){
            snakeNodeAnimateDraw.nowRadius +=2;
            snakeNodeAnimateDraw.changeXY();

            try {
                sleep(50);
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        //drawUtil.deleteElement(snakeNodeAnimateDraw);
        snakeNodeAnimateDraw.setDoDraw(false);
        snakeNode.setDoDraw(true);
        //drawUtil.addToCenterLayer(snakeNode);

        Log.d("SnakeNodeAppendAnimateThread","finished!");
    }
}
