package com.work.terry.snakeonastring_jbox2d.UI;

import android.util.Log;

import com.work.terry.snakeonastring_jbox2d.Util.ColorManager;
import com.work.terry.snakeonastring_jbox2d.Util.Constant;
import com.work.terry.snakeonastring_jbox2d.Util.ImgManager;
import com.work.terry.snakeonastring_jbox2d.Util.TexDrawer;
import com.work.terry.snakeonastring_jbox2d.Util.TexManager;

import org.jbox2d.common.Vec2;

import java.util.ArrayList;
import java.util.List;

import static com.work.terry.snakeonastring_jbox2d.Util.Constant.*;
/**
 * Created by Terry on 2018/1/2.
 */

public class GameElement {
    public static int classCount = 1000;
    public String id;
    public float x;
    public float y;
    public float width;
    public float scaleWidth;
    public float height;
    public float scaleHeight;

    public float TopRatio;

    public int color;
    public float[] colorFloats;
    public float opacityFactor = 1;

    public float defaultHeight;
    public float jumpHeight;
    public float TopOffset=0;
    public float TopOffsetColorFactor =0;
    public float HeightColorFactor = 0;
    public float FloorShadowColorFactor = 0;

    public float rotateAngleGameElements = 0;

    public float floorShadowFactorX = Constant.FloorShadowFactorX;
    public float floorShadowFactorY = Constant.FloorShadowFactorY;

    public float TopWidth;
    public float TopHeight;

    public String Img;
    public String TopImg;
    public boolean isPureColor = true;

    public boolean doDrawHeight = true;
    public boolean doDrawFloorShadow = true;
    public boolean doDraw = true;

    public Vec2 constantXY;
    public GameElement(
            String id,
            float x,float y,
            float width,float height,

            float[] colorFloats255,
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
        this.colorFloats = ColorManager.getColorByRGB255(colorFloats255);
        this.defaultHeight = defaultHeight;
        this.TopOffset = topOffset;
        this.TopOffsetColorFactor = topOffsetColorFactor;
        this.HeightColorFactor = heightColorFactor;
        this.FloorShadowColorFactor = floorShadowColorFactor;
        this.jumpHeight = 0;

        this.floorShadowFactorX = Constant.FloorShadowFactorX;
        this.floorShadowFactorY = Constant.FloorShadowFactorY;

        this.constantXY = new Vec2(x,y);
    }
    public GameElement(
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
        this.colorFloats = ColorManager.getColor(color);
        this.defaultHeight = defaultHeight;
        this.TopOffset = topOffset;
        this.TopOffsetColorFactor = topOffsetColorFactor;
        this.HeightColorFactor = heightColorFactor;
        this.FloorShadowColorFactor = floorShadowColorFactor;
        this.jumpHeight = 0;

        this.floorShadowFactorX = Constant.FloorShadowFactorX;
        this.floorShadowFactorY = Constant.FloorShadowFactorY;

        this.constantXY = new Vec2(x,y);
    }
    public void setTopRatio(float TopRatio){
        this.TopRatio = TopRatio;
        setTopHeightWidth();
    }
    public void setTopHeightWidth(){
        this.TopWidth = width*TopRatio;
        this.TopHeight = height*TopRatio;
    }
    public void setDrawArgs(
            float topOffsetColorFactor,
            float heightColorFactor,
            float floorShadowColorFactor
    ){
        this.TopOffsetColorFactor = topOffsetColorFactor;
        this.HeightColorFactor = heightColorFactor;
        this.FloorShadowColorFactor = floorShadowColorFactor;
    }
    public void setTopOffset(float x){
                TopOffset = x;
    }
    public void setTopImg(String x){
        this.TopImg = x;
    }
    public void setIsPureColor(boolean isPureColor){
        this.isPureColor = isPureColor;
    }
    public void setConstantXY(Vec2 xy){
        constantXY = xy;
    }
    public void setFloorShadowFactorX(float x){
        this.floorShadowFactorX = x;
    }
    public void setFloorShadowFactorY(float y){
        this.floorShadowFactorY = y;
    }
    public void drawSelf(TexDrawer painter){
        if(!doDraw)return;
        //offSet
        if (TopOffset != 0) {
            painter.drawColorOpacityFactorTex(
                    TexManager.getTex(Img),
                    colorFloats,
                    x,
                    y - jumpHeight - defaultHeight + TopOffset,
                    width+scaleWidth,
                    height+scaleHeight,
                    rotateAngleGameElements,
                    TopOffsetColorFactor,
                    opacityFactor
            );
        }
        if(isPureColor){
            //drawSelf
            painter.drawColorOpacityFactorTex(
                    TexManager.getTex(TopImg==null?Img:TopImg),
                    colorFloats,
                    x,
                    y - jumpHeight - defaultHeight,
                    (TopWidth+scaleWidth)*((TopRatio==0)?1:TopRatio),
                    (TopHeight+scaleHeight)*((TopRatio==0)?1:TopRatio),
                    rotateAngleGameElements,
                    1,
                    opacityFactor
            );
        }else {
            painter.drawTex(
                    TexManager.getTex(TopImg==null?Img:TopImg),
                    x,
                    y - jumpHeight - defaultHeight,
                    (TopWidth+scaleWidth)*((TopRatio==0)?1:TopRatio),
                    (TopHeight+scaleHeight)*((TopRatio==0)?1:TopRatio),
                    rotateAngleGameElements
            );
        }

    }
    public boolean testTouch(float touchX,float touchY){
        return touchX>x-width/2-scaleWidth/2
                &&touchX<x+width/2+scaleWidth/2
                &&touchY>y-height/2-scaleHeight/2
                &&touchY<y+height/2+(jumpHeight+defaultHeight)+scaleHeight/2
                ;
    }
    public void setDoDrawHeight(boolean x){
        this.doDrawHeight = x;
    }
    public void setDoDrawFloorShadow(boolean x){this.doDrawFloorShadow = x;}
    public void setDoDraw(boolean x){
        this.doDraw = x;
    }
    public void drawHeight(TexDrawer painter){
        if (!doDraw||!doDrawHeight)return;
        //drawHeight
            painter.drawColorOpacityFactorTex(
                    TexManager.getTex(Img),
                    colorFloats,
                    x,
                    y,
                    width+scaleWidth,
                    height+scaleHeight,
                    rotateAngleGameElements,
                    HeightColorFactor,
                    opacityFactor
            );
            painter.drawColorOpacityFactorTex(
                    TexManager.getTex(SnakeBodyHeightImg),
                    colorFloats,
                    x,
                    y - jumpHeight / 2 - defaultHeight / 2,
                    width+scaleWidth,
                    jumpHeight + defaultHeight,
                    0,
                    HeightColorFactor,
                    opacityFactor
            );
    }

    public void drawFloorShadow(TexDrawer painter){
        if(doDraw&&doDrawFloorShadow)
            painter.drawShadow(
                    TexManager.getTex(Img),
                    ColorManager.getColor(Constant.COLOR_GREY),
                    x + (defaultHeight + jumpHeight) * floorShadowFactorX,
                    y + (defaultHeight + jumpHeight) * floorShadowFactorY,
                    width+scaleWidth,
                    height+scaleHeight,
                    rotateAngleGameElements,
                    FloorShadowColorFactor
            );
    }
    public static void drawColorShape(
            TexDrawer painter,
            String picName,
            float x,
            float y,
            float width,
            float height,
            float rotateAngle,
            float[] ColorFloats,
            float opacityFactor
    ){
        painter.drawColorOpacityFactorTex(
                TexManager.getTex(picName),
                ColorFloats,
                x,
                y,
                width,
                height,
                rotateAngle,
                1,
                opacityFactor
        );
    }
    public static void drawNumberColorFactor(
            TexDrawer painter,
            int number,
            float x,float y,
            float width,float height,
            float[] ColorFloats,
            float colorFactor,
            float opacityFactor
    ) {
        List<Integer> digits = new ArrayList<>();
        if(number==0)digits.add(0);
        while(number>0){
            digits.add(number%10);
            number/=10;
        }
        int length = digits.size();

        float perWidth = width/length;
        if(perWidth>height){
            perWidth = height*4/5;
        }
        float startX = x+(length-1)*perWidth/2;
        for (int i=0;i<length;i++){
            painter.drawColorOpacityFactorTex(
                    TexManager.getTex(ImgManager.getNumberImgName(digits.get(i))),
                    ColorFloats,
                    startX-i*perWidth,
                    y,
                    perWidth,
                    height,
                    0,
                    colorFactor,
                    opacityFactor
            );
        }
    }
    public static void drawNumberShadow(
            TexDrawer painter,
            int number,
            float x,float y,
            float width,float height,
            float[] ColorFloats,
            float floorShadowColorFactor
    ) {
        List<Integer> digits = new ArrayList<>();
        if(number==0)digits.add(0);
        while(number>0){
            digits.add(number%10);
            number/=10;
        }
        int length = digits.size();

        float perWidth = width/length;
        if(perWidth>height){
            perWidth = height*4/5;
        }
        float startX = x+(length-1)*perWidth/2;
        for (int i=0;i<length;i++){
            painter.drawShadow(
                    TexManager.getTex(ImgManager.getNumberImgName(digits.get(i))),
                    ColorFloats,
                    startX-i*perWidth,
                    y,
                    perWidth,
                    height,
                    0,
                    floorShadowColorFactor
            );
        }
    }
    public void setXY(float x,float y){
          this.x = x;
          this.y = y;
    }
    public static void drawNumber(
            TexDrawer painter,
            int number,
            float x,float y,
            float width,float height,
            float[] ColorFloats,
            float opacityFactor
    ){
        List<Integer> digits = new ArrayList<>();
        if(number==0)digits.add(0);
        while(number>0){
            digits.add(number%10);
            number/=10;
        }
        int length = digits.size();

        float perWidth = width/length;
        if(perWidth>height&&height>0){
            perWidth = height*4/5;
        }
        float startX = x+(length-1)*perWidth/2;
        for (int i=0;i<length;i++){
            drawColorShape(
                    painter,
                    ImgManager.getNumberImgName(digits.get(i)),
                    startX-i*perWidth,
                    y,
                    perWidth,
                    height,
                    0,
                    ColorFloats,
                    opacityFactor
            );
        }
    }
    public void setColor(int x){
        setColorFloats(ColorManager.getColor(x));
    }
    public void setColorFloats255(float[] floats){
        setColorFloats(ColorManager.getColorByRGB255(floats));
    }
    public void setColorFloats(float[] floats){
        this.colorFloats = floats;
    }
    public void setOpacityFactor(float x){
        opacityFactor = x;
    }
}
