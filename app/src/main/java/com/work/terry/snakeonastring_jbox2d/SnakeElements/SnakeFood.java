package com.work.terry.snakeonastring_jbox2d.SnakeElements;

import android.util.Log;

import com.work.terry.snakeonastring_jbox2d.GameElements;
import com.work.terry.snakeonastring_jbox2d.JBox2DElements.CircleBody;
import com.work.terry.snakeonastring_jbox2d.SurfaceViewAndActivity.GamePlay;
import com.work.terry.snakeonastring_jbox2d.Thread.JBox2DThread;
import com.work.terry.snakeonastring_jbox2d.Util.ColorManager;
import com.work.terry.snakeonastring_jbox2d.Util.Constant;
import com.work.terry.snakeonastring_jbox2d.Util.DrawUtil;
import com.work.terry.snakeonastring_jbox2d.Util.JBox2DUtil;
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

    public int score;
    public SnakeFood(
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
                "snakeFood "+id,
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

        drawUtil.addToFloorLayer(this);
    }
    public void setEatean(){
        Log.d("SnakeFood","eaten");
        eatean = true;
    }
    @Override
    public void drawFloorShadow(TexDrawer painter){
        super.drawFloorShadow(painter);
        drawNumberUnder3Digit(
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
