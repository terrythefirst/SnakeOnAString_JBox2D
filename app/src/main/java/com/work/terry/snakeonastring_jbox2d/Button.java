package com.work.terry.snakeonastring_jbox2d;

import com.work.terry.snakeonastring_jbox2d.Util.Constant;
import com.work.terry.snakeonastring_jbox2d.Util.TexDrawer;

/**
 * Created by Terry on 2017/12/30.
 */

public class Button extends GameElements{
    public int Color = 1;

    private float circleDiameter;
    private float rectLength;
    private float rotateAngle;

    public Button(
            float x,float y,
            float circleDiameter,
            float totalLength,float jumpHeight,
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
    }

    public void drawSelf(TexDrawer painter, float[] color){

    }
    public void drawHeightShadow(TexDrawer painter,float[] color){

    }
    public void drawFloorShadow(TexDrawer painter,float[] color){

    }

}
