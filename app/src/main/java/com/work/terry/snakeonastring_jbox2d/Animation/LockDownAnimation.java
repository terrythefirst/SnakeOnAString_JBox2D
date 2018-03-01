package com.work.terry.snakeonastring_jbox2d.Animation;

import com.work.terry.snakeonastring_jbox2d.UI.GameElement;
import com.work.terry.snakeonastring_jbox2d.Util.Constant;
import com.work.terry.snakeonastring_jbox2d.Util.DrawUtil;

/**
 * Created by Terry on 2018/2/3.
 */

public class LockDownAnimation extends Thread {
    public static int AnimationNum = 0;

    GameElement gameElement;
    DrawUtil drawUtil;
    MyAnimation myAnimation;
    boolean expand;
    float timeSpan;
    float startWidth;
    float startHeight;
    float maxScaleRate;

    public LockDownAnimation(
            GameElement gameElement,
            DrawUtil drawUtil,
            boolean expand,
            float maxScaleRate,
            float timeSpan){
        this.gameElement = gameElement;
        this.drawUtil = drawUtil;
        this.expand = expand;
        this.timeSpan = timeSpan;
        this.maxScaleRate = maxScaleRate;

        startWidth = 0;
        startHeight = 0;
        if(!expand){
            startWidth = gameElement.width*maxScaleRate;
            startHeight = gameElement.height*maxScaleRate;
        }

        myAnimation = new MyAnimation(
                AnimationNum++,
                gameElement.x, gameElement.y,
                gameElement.width, gameElement.height,
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
            myAnimation.x = gameElement.x;
            myAnimation.y = gameElement.y;

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
