package com.work.terry.snakeonastring_jbox2d;

import android.util.Log;

import com.work.terry.snakeonastring_jbox2d.Util.Constant;
import com.work.terry.snakeonastring_jbox2d.Util.TexDrawer;
import com.work.terry.snakeonastring_jbox2d.Util.TexManager;

import org.jbox2d.dynamics.Body;

import static com.work.terry.snakeonastring_jbox2d.Util.Constant.snakeBodyHeightImg;
import static com.work.terry.snakeonastring_jbox2d.Util.Constant.snakeBodyImg;

/**
 * Created by Terry on 2018/1/2.
 */

public class GameElements{
    public float x;
    public float y;
    public float width;
    public float height;
    public float defaultHeight;
    public float jumpHeight;

    public String Img;

    public GameElements(
            float x,float y,
            float width,float height,
            float defaultHeight,
            String Img
            ){
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.Img = Img;
        this.defaultHeight = defaultHeight;
        this.jumpHeight = 0;
    }

    public void drawSelf(TexDrawer painter, float[] color){
        painter.drawColorSelf(
                TexManager.getTex(Img),
                color,
                x,
                y-jumpHeight-defaultHeight,
                width,
                height,
                0
        );
    }
    public void drawHeightShadow(TexDrawer painter,float[] color){
        painter.drawColorFactorSelf(
                TexManager.getTex(Img),
                color,
                x,
                y,
                width,
                height,
                0,
                Constant.SnakeHeightColorFactor
        );
        painter.drawColorFactorSelf(
                TexManager.getTex(snakeBodyHeightImg),
                color,
                x,
                y - jumpHeight/2-defaultHeight/2,
                width,
                jumpHeight+defaultHeight,
                0,
                Constant.SnakeHeightColorFactor
        );
    }

    public void drawFloorShadow(TexDrawer painter,float[] color){
        painter.drawShadow(
                TexManager.getTex(Img),
                color,
                x+(defaultHeight+jumpHeight)*Constant.FloorShadowFactorX,
                y+(defaultHeight+jumpHeight)*Constant.FloorShadowFactorY,
                width,
                height,
                0,
                Constant.SnakeFloorColorFactor
        );
    }

}
