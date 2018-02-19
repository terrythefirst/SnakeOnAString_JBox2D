package com.work.terry.snakeonastring_jbox2d.UI;

import com.work.terry.snakeonastring_jbox2d.Util.TexDrawer;

import java.nio.channels.FileLock;

import static com.work.terry.snakeonastring_jbox2d.Util.Constant.NailShadowImg;
import static com.work.terry.snakeonastring_jbox2d.Util.Constant.SnakeFloorColorFactor;
import static com.work.terry.snakeonastring_jbox2d.Util.Constant.SnakeHeightColorFactor;

/**
 * Created by Terry on 2017/12/30.
 */

public class Button extends GameElements {
    public float buttonDefaultHeight ;
    public boolean disabled = false;

    public Button(
            int id,
            float x,float y,
            float width,float height,

            int color,
            float defaultHeight,
            float topOffset,
            float topOffsetColorFactor,
            float heightColorFactor,
            float floorShadowColorFactor,

            String Img){
        super(
                "Button "+id,
                x,y,
                width, height,
                color,
                defaultHeight,
                topOffset,
                topOffsetColorFactor,
                heightColorFactor,
                floorShadowColorFactor,
                Img);
        buttonDefaultHeight = defaultHeight;
        jumpHeight = defaultHeight;
        this.defaultHeight = 0;
    }
    public void setDisabled(boolean x){
        disabled = x;
    }
    public void whenPressed(){
        if(disabled){
            jumpHeight = buttonDefaultHeight*2/3;
        }else {
            jumpHeight = 0;
        }
    }
    public void whenReleased(){
        jumpHeight = buttonDefaultHeight;
    }
    public boolean testTouch(float touchX,float touchY){
        return touchX>x-width/2
                &&touchX<x+width/2
                &&touchY>y-height/2
                &&touchY<y+height/2
                ;
    }
}
