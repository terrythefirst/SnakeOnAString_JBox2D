package com.work.terry.snakeonastring_jbox2d;

import android.util.Log;

import com.work.terry.snakeonastring_jbox2d.Util.ColorManager;
import com.work.terry.snakeonastring_jbox2d.Util.Constant;
import com.work.terry.snakeonastring_jbox2d.Util.TexDrawer;
import com.work.terry.snakeonastring_jbox2d.Util.TexManager;
import static com.work.terry.snakeonastring_jbox2d.Util.Constant.*;
/**
 * Created by Terry on 2018/1/2.
 */

public class GameElements{
    public float x;
    public float y;
    public float width;
    public float height;

    public int color;
    public float defaultHeight;
    public float jumpHeight;
    public float TopOffset=0;
    public float TopOffsetColorFactor =0;
    public float HeightColorFactor = 0;
    public float FloorShadowColorFactor = 0;

    public float rotateAngleGameElements = 0;

    public float TopWidth;
    public float TopHeight;

    public String Img;

    public GameElements(
            float x,float y,
            float width,float height,

            int color,
            float defaultHeight,
            float topOffset,
            float topOffsetColorFactor,
            float heightColorFactor,
            float floorShadowColorFactor,

            String Img
            ){
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.TopWidth = width;
        this.TopHeight = height;
        this.Img = Img;
        this.color = color;
        this.defaultHeight = defaultHeight;
        this.TopOffset = topOffset;
        this.TopOffsetColorFactor = topOffsetColorFactor;
        this.HeightColorFactor = heightColorFactor;
        this.FloorShadowColorFactor = floorShadowColorFactor;
        this.jumpHeight = 0;
    }
    public void setTopRatio(float TopRatio){
        this.TopWidth = width*TopRatio;
        this.TopHeight = height*TopRatio;
    }
    public void drawSelf(TexDrawer painter){
        //offSet
        if(TopOffset!=0){
            painter.drawColorFactorTex(
                    TexManager.getTex(Img),
                    ColorManager.getColor(color),
                    x,
                    y - jumpHeight-defaultHeight+TopOffset,
                    width,
                    height,
                    rotateAngleGameElements,
                    TopOffsetColorFactor
            );
        }

        //drawSelf
        painter.drawColorSelf(
                TexManager.getTex(Img),
                ColorManager.getColor(color),
                x,
                y-jumpHeight-defaultHeight,
                TopWidth,
                TopHeight,
                rotateAngleGameElements
        );


    }

    public void drawHeight(TexDrawer painter){
        //drawHeight
        painter.drawColorFactorTex(
                TexManager.getTex(Img),
                ColorManager.getColor(color),
                x,
                y,
                width,
                height,
                rotateAngleGameElements,
                HeightColorFactor
        );
        painter.drawColorFactorTex(
                TexManager.getTex(SnakeBodyHeightImg),
                ColorManager.getColor(color),
                x,
                y - jumpHeight/2-defaultHeight/2,
                width,
                jumpHeight+defaultHeight,
                0,
                HeightColorFactor
        );
    }
    public void drawFloorShadow(TexDrawer painter){
        painter.drawShadow(
                TexManager.getTex(Img),
                ColorManager.getColor(Constant.COLOR_GREAY),
                x+(defaultHeight+jumpHeight)*Constant.FloorShadowFactorX,
                y+(defaultHeight+jumpHeight)*Constant.FloorShadowFactorY,
                width,
                height,
                rotateAngleGameElements,
                FloorShadowColorFactor
        );
    }

}
