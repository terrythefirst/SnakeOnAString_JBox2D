package com.work.terry.snakeonastring_jbox2d.UI;

import android.util.Log;

import com.work.terry.snakeonastring_jbox2d.Util.Constant;
import com.work.terry.snakeonastring_jbox2d.Util.DrawUtil;
import com.work.terry.snakeonastring_jbox2d.Util.MyMath;
import com.work.terry.snakeonastring_jbox2d.Util.TexDrawer;

public class TransitionAnimation extends GameElement {
    DrawUtil drawUtil;
    long timeSpan = 500;
    long timeStamp = 0;
    public TransitionAnimation(DrawUtil drawUtil) {
        super(
                "TransitionAnimation",
                720, -10,
                1440, 6000,
                Constant.COLOR_WHITE,
                0,0,0,0,0,
                Constant.SnakeBodyHeightImg
        );
        this.drawUtil = drawUtil;
        drawUtil.addToTopLayer(this);
        setDoDrawHeight(false);
        setDoDrawFloorShadow(false);
        timeStamp = System.nanoTime()/1000000;
    }

    @Override
    public void drawSelf(TexDrawer painter){
        long currTimeStamp = System.nanoTime()/1000000;
        long dt = currTimeStamp-timeStamp;
        if(dt>=timeSpan){
            drawUtil.addToRemoveSequence(this);
            return;
        }
        float ratio =1- MyMath.smoothStep(0,timeSpan,dt);
        setOpacityFactor(ratio);

        super.drawSelf(painter);
    }
}
