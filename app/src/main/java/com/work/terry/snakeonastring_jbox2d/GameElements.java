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
    protected float x;
    protected float y;
    protected float width;
    protected float height;
    protected float jumpHeight;
    protected float downHeight;

    protected String Img;

    public GameElements(
            float x,float y,
            float width,float height,
            float downHeight,
            String Img){
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.downHeight = downHeight;
        this.Img = Img;

        this.jumpHeight = 0;
    }
    public GameElements(
            float x,float y,
            float width,float height,
            float downHeight,
            String Img,
            float jumpHeight){
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.downHeight = downHeight;
        this.Img = Img;
        this.jumpHeight = jumpHeight;
    }

    public void drawSelf(TexDrawer painter, float[] color){
        painter.drawColorSelf(
                TexManager.getTex(Img),
                color,
                x,
                y-jumpHeight,
                width,
                height,
                0
        );
    }
    public void drawHeightShadow(TexDrawer painter,float[] color){
        painter.drawDownShadow(
                TexManager.getTex(Img),
                color,
                x,
                y,
                width,
                height,
                0,
                0,
                Constant.SnakeDownHeight,
                Constant.SnakeHeightColorFactor
        );
        painter.drawDownShadow(
                TexManager.getTex(snakeBodyHeightImg),
                color,
                x,
                y-downHeight/2-jumpHeight/2,
                width,
                downHeight+jumpHeight,
                0,
                0,
                downHeight,
                Constant.SnakeHeightColorFactor
        );
    }

    public void drawFloorShadow(TexDrawer painter,float[] color){
        painter.drawFloorShadow(
                TexManager.getTex(Img),
                color,
                x,
                y+Constant.SnakeDownHeight,
                width,
                height,
                0,
                (downHeight+jumpHeight)*Constant.FloorShadowFactorX,
                (downHeight+jumpHeight)*Constant.FloorShadowFactorY,
                Constant.SnakeFloorColorFactor
        );
    }

}
