package com.work.terry.snakeonastring_jbox2d.SnakeElements;

import android.util.Log;

import com.work.terry.snakeonastring_jbox2d.JBox2DElements.CircleBody;
import com.work.terry.snakeonastring_jbox2d.Thread.SnakeFoodJumpScoreThread;
import com.work.terry.snakeonastring_jbox2d.Util.DrawUtil;
import com.work.terry.snakeonastring_jbox2d.Util.TexDrawer;
import com.work.terry.snakeonastring_jbox2d.Util.TexManager;

import org.jbox2d.dynamics.World;

import static com.work.terry.snakeonastring_jbox2d.Util.Constant.*;

/**
 * Created by Terry on 2018/1/25.
 */

public class SnakeFood extends CircleBody{
    public DrawUtil drawUtil;
    public boolean eatean = false;
    public boolean moving = false;
    public Thread jumpScoreThread;

    public int score;
    public SnakeFood(
            DrawUtil drawUtil,
            World world,
            int id,
            float x,float y,
            float raius,
            int color,
            int score,
            String Img){
        super(
                world,
                "snakeFood "+id,
                x,y,
                0,0,0,
                raius,

                color,
                6,0,0,0.4f,SnakeFloorColorFactor,

                0,SnakeBodyLinearDampingRate,SnakeHeadDensity,SnakeBodyFriction,SnakeBodyRestitution,
                false,
                Img
        );
        this.drawUtil = drawUtil;
        this.score = score;
        setDoDrawHeight(false);

        drawUtil.addToFloorLayer(this);
        starttJumpScoreThread();
    }
    public void starttJumpScoreThread(){
        jumpScoreThread = new SnakeFoodJumpScoreThread(
                this,
                score-score/3,
                4
        );
        jumpScoreThread.start();
    }
    public void setEaten(){
        Log.d("SnakeFood","eaten");
        eatean = true;
        drawUtil.addToRemoveSequence(this);
    }
    @Override
    public  void drawSelf(TexDrawer painter){
        painter.drawTex(
                TexManager.getTex(Img),
                x,
                y-jumpHeight-defaultHeight,
                width+scaleWidth,
                height+scaleHeight,
                0
        );
    }
    @Override
    public void drawFloorShadow(TexDrawer painter){
        super.drawFloorShadow(painter);
        drawNumber(
                painter,
                score,
                x,
                y+width/2+30,
                width*2,
                60,
                0
        );
    }
}
