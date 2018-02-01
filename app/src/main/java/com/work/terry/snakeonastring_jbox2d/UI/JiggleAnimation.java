package com.work.terry.snakeonastring_jbox2d.UI;

import android.util.Log;

import com.work.terry.snakeonastring_jbox2d.Util.MyMath;

/**
 * Created by Terry on 2018/1/31.
 */

public class JiggleAnimation extends Thread{
    GameElements gameElements;
    float jumpSpan;
    float timeSpan;
    //float times;
    long interval;

    float G;

    boolean doScale;
    float defaultWidth;
    float defaultHeight;
    float maxRatio;

    public boolean firstRoundFinished = false;
    boolean allRoundFinnished = false;

    public boolean getFirstRoundFinished(){
        return firstRoundFinished;
    }
    public JiggleAnimation(
            GameElements gameElements,
            float jumpSpan,
            float timeSpan,

            boolean doScale,
            float maxRatio
    ){
        this.gameElements = gameElements;
        this.jumpSpan = jumpSpan;
        this.timeSpan = timeSpan;
        this.maxRatio = maxRatio;

        this.doScale = doScale;
        if(doScale){
            defaultHeight = gameElements.width;
            defaultWidth = gameElements.height;
        }

        this.G = (float) (2*jumpSpan/Math.pow(timeSpan/2,2));
        this.interval = 10;
    }
    @Override
    public void run(){
        float nowTime = 0;
        float nowTimes = 0;
        float vy = G*timeSpan/2;
        while (nowTimes<10){
            nowTime +=interval*0.001f;
            //根据此轮起始Y坐标、此轮运动时间、此轮起始速度计算当前位置
            float tempCurrY=-0.5f*G*nowTime*nowTime+vy*nowTime;

            if(tempCurrY<=0)
            {//若当前位置低于地面则碰到地面反弹
                //反弹后起始高度为0
                nowTimes++;

                //反弹后起始速度
                vy=-(vy-G*nowTime)*0.76f;
                //反弹后此轮运动时间清0
                nowTime=0;
                //若速度小于阈值则停止运动
                if(vy<1f)
                {
                    gameElements.jumpHeight=0;
                    allRoundFinnished = true;
                    break;
                }
            }
            else
            {//若没有碰到地面则正常运动
                gameElements.jumpHeight = tempCurrY;
            }

            if(doScale){
                float ratio = gameElements.jumpHeight/jumpSpan * maxRatio;
                gameElements.scaleWidth = defaultWidth*ratio;
                gameElements.scaleHeight = defaultHeight*ratio;
            }

            try {
                sleep(interval);
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        Log.d("JiggleAnimation","NowJumpHeight"+gameElements.jumpHeight);
    }
}
