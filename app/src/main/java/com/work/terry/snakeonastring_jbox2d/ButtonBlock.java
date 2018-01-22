package com.work.terry.snakeonastring_jbox2d;

import android.widget.*;

import com.work.terry.snakeonastring_jbox2d.JBox2DElements.CircleBody;
import com.work.terry.snakeonastring_jbox2d.JBox2DElements.RectBody;
import com.work.terry.snakeonastring_jbox2d.Util.Constant;
import com.work.terry.snakeonastring_jbox2d.Util.TexDrawer;

/**
 * Created by Yearn on 2018/1/21.
 */

public class ButtonBlock extends GameElements{
    CircleBody circleBody1;
    CircleBody circleBody2;
    RectBody rectBody;

    public int Color = 1;

    private float circleDiameter;
    private float rectLength;
    private float rotateAngle;

    public ButtonBlock(
            float x,float y,
            float circleDiameter,
            float totalLength,
            float jumpHeight,
            float rotateAngle,
            int Color){
        super(
                x,y,
                totalLength,circleDiameter,
                Constant.SnakeDownHeight,
                "",
                jumpHeight
        );
        this.x = x;
        this.y = y;
        this.Color = Color;
        this.rotateAngle = rotateAngle;
        this.circleDiameter = circleDiameter;
        rectLength = totalLength-circleDiameter;

        initBody();
    }
    public void initBody(){

    }
    public void drawSelf(TexDrawer painter, float[] color){

    }
    public void drawHeightShadow(TexDrawer painter,float[] color){

    }
    public void drawFloorShadow(TexDrawer painter,float[] color){

    }
}
