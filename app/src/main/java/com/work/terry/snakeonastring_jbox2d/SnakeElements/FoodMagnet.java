package com.work.terry.snakeonastring_jbox2d.SnakeElements;

import android.util.Log;

import com.work.terry.snakeonastring_jbox2d.JBox2DElements.CircleBody;
import com.work.terry.snakeonastring_jbox2d.Util.DrawUtil;
import com.work.terry.snakeonastring_jbox2d.Util.TexDrawer;
import com.work.terry.snakeonastring_jbox2d.Util.TexManager;

import org.jbox2d.dynamics.World;

import static com.work.terry.snakeonastring_jbox2d.Util.Constant.SnakeFloorColorFactor;

/**
 * Created by Terry on 2018/2/2.
 */

public class FoodMagnet extends CircleBody {
    public boolean eatean;
    DrawUtil drawUtil;

    public float duration;
    public float searchRadius;
    public float numRays = 80;

    public FoodMagnet(
            DrawUtil drawUtil,
            World world,
            int id,
            float x,float y,
            float radius,

            float duration,
            float searchRadius,

            String Img){
        super(
                world,
                "FoodMagnet "+id,
                x,y,
                0,0,0,
                radius,

                0,
                6,0,0,0.4f,SnakeFloorColorFactor,

                0,0,0,0,0,
                true,
                Img
        );
        this.searchRadius = searchRadius;
        this.duration = duration;
        this.drawUtil = drawUtil;
        setDoDrawHeight(false);
    }
    public void setEaten(){
        Log.d("FoodMagnet","eaten");
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
