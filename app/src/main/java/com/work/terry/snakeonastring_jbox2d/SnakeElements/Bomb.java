package com.work.terry.snakeonastring_jbox2d.SnakeElements;

import android.util.Log;

import com.work.terry.snakeonastring_jbox2d.JBox2DElements.CircleBody;
import com.work.terry.snakeonastring_jbox2d.Util.DrawUtil;
import com.work.terry.snakeonastring_jbox2d.Util.TexDrawer;
import com.work.terry.snakeonastring_jbox2d.Util.TexManager;

import org.jbox2d.dynamics.World;

import static com.work.terry.snakeonastring_jbox2d.Util.Constant.SnakeFloorColorFactor;
import static com.work.terry.snakeonastring_jbox2d.Util.Constant.SnakeHeadEyesDiameter;

/**
 * Created by Terry on 2018/1/29.
 */

public class Bomb extends CircleBody {
    DrawUtil drawUtil;
    public boolean eatean = false;

    public int score;
    public Bomb(
            DrawUtil drawUtil,
            World world,
            int id,
            float x,float y,
            float width,float height,
            int color,
            int score,
            String Img){
        super(
                world,
                "Bomb "+id,
                x,y,
                0,0,0,
                width/2,

                color,
                6,0,0,0.4f,SnakeFloorColorFactor,

                0,0,0,0,0,
                true,
                Img
        );
        this.drawUtil = drawUtil;
        this.score = score;
        setDoDrawHeight(false);
    }
    public void setEaten(){
        Log.d("Bomb","eaten");
        eatean = true;
        drawUtil.addToRemoveSequence(this);
    }
    @Override
    public  void drawSelf(TexDrawer painter){
        painter.drawTex(
                TexManager.getTex(Img),
                x,
                y-jumpHeight-defaultHeight,
                width,
                height,
                0
        );
    }
}
