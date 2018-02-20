package com.work.terry.snakeonastring_jbox2d.UI;

import android.content.SharedPreferences;
import android.util.Log;

import com.work.terry.snakeonastring_jbox2d.Util.ColorManager;
import com.work.terry.snakeonastring_jbox2d.Util.Constant;
import com.work.terry.snakeonastring_jbox2d.Util.ImgManager;
import com.work.terry.snakeonastring_jbox2d.Util.TexDrawer;
import com.work.terry.snakeonastring_jbox2d.Util.TexManager;

import java.util.ArrayList;
import java.util.List;

import static com.work.terry.snakeonastring_jbox2d.Util.Constant.*;
/**
 * Created by Terry on 2018/1/2.
 */

public class GameElements{
    public String id;
    public float x;
    public float y;
    public float width;
    public float scaleWidth;
    public float height;
    public float scaleHeight;

    public float TopRatio;

    public int color;
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

        this.floorShadowFactorX = Constant.FloorShadowFactorX;
        this.floorShadowFactorY = Constant.FloorShadowFactorY;

    }
    public void setTopRatio(float TopRatio){
        this.TopRatio = TopRatio;
        setTopHeightWidth();
    }
    public void setTopHeightWidth(){
        this.TopWidth = width*TopRatio;
        this.TopHeight = height*TopRatio;
    }
    public void setFloorShadowFactorX(float x){
        this.floorShadowFactorX = x;
    }
    public void setFloorShadowFactorY(float y){
        this.floorShadowFactorY = y;
    }
    public void drawSelf(TexDrawer painter){
        //offSet
        if (TopOffset != 0) {
            painter.drawColorFactorTex(
                    TexManager.getTex(Img),
                    ColorManager.getColor(color),
                    x,
                    y - jumpHeight - defaultHeight + TopOffset,
                    width+scaleWidth,
                    height+scaleHeight,
                    rotateAngleGameElements,
                    TopOffsetColorFactor
            );
        }
        //drawSelf
        painter.drawColorSelf(
                TexManager.getTex(Img),
                ColorManager.getColor(color),
                x,
                y - jumpHeight - defaultHeight,
                (TopWidth+scaleWidth)*((TopRatio==0)?1:TopRatio),
                (TopHeight+scaleHeight)*((TopRatio==0)?1:TopRatio),
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
                    width+scaleWidth,
                    height+scaleHeight,
                    rotateAngleGameElements,
                    HeightColorFactor
            );
            painter.drawColorFactorTex(
                    TexManager.getTex(SnakeBodyHeightImg),
                    ColorManager.getColor(color),
                    x,
                    y - jumpHeight / 2 - defaultHeight / 2,
                    width+scaleWidth,
                    jumpHeight + defaultHeight,
                    0,
                    HeightColorFactor
            );
    }
    public void drawFloorShadow(TexDrawer painter){
        Log.d(id,"defaultHeight="+defaultHeight+" jumpHeight="+jumpHeight);
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
            int Color
    ){
        painter.drawColorFactorTex(
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
    public static void drawNumberColorFactor(
            TexDrawer painter,
            int number,
            float x,float y,
            float width,float height,
            int Color,
            float colorFactor
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
            painter.drawColorFactorTex(
                    TexManager.getTex(ImgManager.getNumberImgName(digits.get(i))),
                    ColorManager.getColor(Color),
                    startX-i*perWidth,
                    y,
                    perWidth,
                    height,
                    0,
                    colorFactor
            );
        }
    }
    public static void drawNumberShadow(
            TexDrawer painter,
            int number,
            float x,float y,
            float width,float height,
            int Color,
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
                    ColorManager.getColor(Color),
                    startX-i*perWidth,
                    y,
                    perWidth,
                    height,
                    0,
                    floorShadowColorFactor
            );
        }
    }

    public static void drawNumber(
            TexDrawer painter,
            int number,
            float x,float y,
            float width,float height,
            int Color
    ){
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
            drawColorShape(
                    painter,
                    ImgManager.getNumberImgName(digits.get(i)),
                    startX-i*perWidth,
                    y,
                    perWidth,
                    height,
                    0,
                    Color
            );
        }
    }
    public void onPause(SharedPreferences.Editor editor){
        editor.putFloat(id+"x",x);
        editor.putFloat(id+"x",y);
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
