package com.work.terry.snakeonastring_jbox2d.UI;

import com.work.terry.snakeonastring_jbox2d.Util.Constant;
import com.work.terry.snakeonastring_jbox2d.Util.DrawUtil;

import org.jbox2d.common.Vec2;

/**
 * Created by Terry on 2018/2/3.
 */

public class LockDownAnimation extends Thread {
    public static int AnimationNum = 0;

    GameElements gameElements;
    DrawUtil drawUtil;
    MyAnimation myAnimation;
    boolean expand;
    float timeSpan;
    float startWidth;
    float startHeight;
    float maxScaleRate;

    public LockDownAnimation(
            GameElements gameElements,
            DrawUtil drawUtil,
            boolean expand,
            float maxScaleRate,
            float timeSpan){
        this.gameElements = gameElements;
        this.drawUtil = drawUtil;
        this.expand = expand;
        this.timeSpan = timeSpan;
        this.maxScaleRate = maxScaleRate;

        startWidth = 0;
        startHeight = 0;
        if(!expand){
            startWidth = gameElements.width*maxScaleRate;
            startHeight = gameElements.height*maxScaleRate;
        }

        myAnimation = new MyAnimation(
                AnimationNum++,
                gameElements.x,gameElements.y,
                gameElements.width,gameElements.height,
                Constant.C0LOR_SNAKE_WHITE,
                0,
                0,
                0,
                Constant.ButtonBlockHeightColorFactor,
                Constant.ButtonBlockFloorColorFactor,
                Constant.LockDownImg
        );
        myAnimation.scaleWidth = startWidth;
        myAnimation.scaleHeight = startHeight;
        drawUtil.addToAnimationLayer(myAnimation);
    }
    @Override
    public void run(){
        long interval = 10;

        float degrees = 0;
        float degreeSpan = 90;
        float perDegrees = degreeSpan/interval;

        float rotateDegrees = 0;
        float rotateDegreeSpan = 180;
        float perRotateDegrees = 3;

        long clockTick = 0;
        float clickTimes = timeSpan*1000/interval;

        while (clockTick < clickTimes){
            myAnimation.x = gameElements.x;
            myAnimation.y = gameElements.y;

            if(expand){
                degrees += perDegrees;
                rotateDegrees +=perRotateDegrees;
            }else {
                degrees -= perDegrees;
                rotateDegrees -=perRotateDegrees;
            }

            myAnimation.rotateAngleGameElements = rotateDegrees;
            myAnimation.scaleWidth = (float) Math.sin(Math.toRadians(degrees))*40+myAnimation.width*maxScaleRate;
            myAnimation.scaleHeight = (float) Math.sin(Math.toRadians(degrees))*40+myAnimation.height*maxScaleRate;

            clockTick++;
            try {
                sleep(interval);
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        drawUtil.addToRemoveSequence(myAnimation);
    }
}
