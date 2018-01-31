package com.work.terry.snakeonastring_jbox2d;

import android.content.SharedPreferences;
import android.util.Log;

import com.work.terry.snakeonastring_jbox2d.Util.ColorManager;
import com.work.terry.snakeonastring_jbox2d.Util.Constant;
import com.work.terry.snakeonastring_jbox2d.Util.ImgManager;
import com.work.terry.snakeonastring_jbox2d.Util.TexDrawer;
import com.work.terry.snakeonastring_jbox2d.Util.TexManager;
import static com.work.terry.snakeonastring_jbox2d.Util.Constant.*;
/**
 * Created by Terry on 2018/1/2.
 */

public class GameElements{
    public String id;
    public float x;
    public float y;
    public float width;
    public float height;

    public float TopRatio;

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

    public boolean doDrawHeight = true;
    public boolean doDraw = true;

    public GameElements(
            String id,
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
        this.id = id;
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
        this.TopRatio = TopRatio;
        setTopHeightWidth();
    }
    public void setTopHeightWidth(){
        this.TopWidth = width*TopRatio;
        this.TopHeight = height*TopRatio;
    }
    public void drawSelf(TexDrawer painter){
        //offSet
        if (TopOffset != 0) {
            painter.drawColorFactorTex(
                    TexManager.getTex(Img),
                    ColorManager.getColor(color),
                    x,
                    y - jumpHeight - defaultHeight + TopOffset,
                    width,
                    height,
                    rotateAngleGameElements,
                    TopOffsetColorFactor
            );
        }

        if (TopRatio != 0) setTopHeightWidth();
        //drawSelf
        painter.drawColorSelf(
                TexManager.getTex(Img),
                ColorManager.getColor(color),
                x,
                y - jumpHeight - defaultHeight,
                TopWidth,
                TopHeight,
                rotateAngleGameElements
        );
    }

    public void setDoDrawHeight(boolean x){
        this.doDrawHeight = x;
    }
    public void setDoDraw(boolean x){
        this.doDraw = x;
    }
    public void drawHeight(TexDrawer painter){
        if (!doDrawHeight)return;
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
                    y - jumpHeight / 2 - defaultHeight / 2,
                    width,
                    jumpHeight + defaultHeight,
                    0,
                    HeightColorFactor
            );
    }
    public void drawFloorShadow(TexDrawer painter){
            painter.drawShadow(
                    TexManager.getTex(Img),
                    ColorManager.getColor(Constant.COLOR_GREAY),
                    x + (defaultHeight + jumpHeight) * Constant.FloorShadowFactorX,
                    y + (defaultHeight + jumpHeight) * Constant.FloorShadowFactorY,
                    width,
                    height,
                    rotateAngleGameElements,
                    FloorShadowColorFactor
            );
    }
    public static void drawPic(
            TexDrawer painter,
            String picName,
            float x,
            float y,
            float width,
            float height,
            float rotateAngle,
            int Color
    ){
        painter.drawShadow(
                TexManager.getTex(picName),
                ColorManager.getColor(Color),
                x,
                y,
                width,
                height,
                rotateAngle,
                1
        );
    }
    public static void drawNumberUnder3Digit(
            TexDrawer painter,
            int number,
            float x,float y,
            float width,float height,
            int Color
    ){
        if(number<10){
            float quarterWidth = width*1.0f/4;
            drawPic(
                    painter,
                    ImgManager.getNumberImgName(number),
                    x+quarterWidth,
                    y,
                    width/2,
                    height,
                    0,
                    Color
            );
            drawPic(
                    painter,
                    ImgManager.getNumberImgName(0),
                    x-quarterWidth,
                    y,
                    width/2,
                    height,
                    0,
                    Color
            );
        }else if(number>=10&&number<100){
            float quarterWidth = width*1.0f/4;
            drawPic(
                    painter,
                    ImgManager.getNumberImgName(number%10),
                    x+quarterWidth,
                    y,
                    width/2,
                    height,
                    0,
                    Color
            );
            drawPic(
                    painter,
                    ImgManager.getNumberImgName(number/10),
                    x-quarterWidth,
                    y,
                    width/2,
                    height,
                    0,
                    Color
            );
        }else if(number>=100){
            float thirdWidth = width*1.0f/4;
            drawPic(
                    painter,
                    ImgManager.getNumberImgName(number%10),
                    x+thirdWidth,
                    y,
                    thirdWidth,
                    height,
                    0,
                    Color
            );
            drawPic(
                    painter,
                    ImgManager.getNumberImgName(number/10),
                    x,
                    y,
                    thirdWidth,
                    height,
                    0,
                    Color
            );
            drawPic(
                    painter,
                    ImgManager.getNumberImgName(number/10),
                    x-thirdWidth,
                    y,
                    thirdWidth,
                    height,
                    0,
                    Color
            );
        }
    }
    public void onPause(SharedPreferences.Editor editor){
        editor.putFloat(id+"defaultHeight",defaultHeight);
        editor.putFloat(id+"topOffset",TopOffset);
        editor.putFloat(id+"topOffsetColorFactor",TopOffsetColorFactor);
        editor.putFloat(id+"heightColorFactor",HeightColorFactor);
        editor.putFloat(id+"floorShadowColorFactor",FloorShadowColorFactor);
        editor.putString(id+"img",Img);
    }
    public void onResume(){

    }
}
