package com.work.terry.snakeonastring_jbox2d.UI;

import android.util.Log;

import com.work.terry.snakeonastring_jbox2d.Util.TexDrawer;

import java.net.PortUnreachableException;
import java.nio.channels.FileLock;

import static com.work.terry.snakeonastring_jbox2d.Util.Constant.NailShadowImg;
import static com.work.terry.snakeonastring_jbox2d.Util.Constant.SnakeFloorColorFactor;
import static com.work.terry.snakeonastring_jbox2d.Util.Constant.SnakeHeightColorFactor;

/**
 * Created by Terry on 2017/12/30.
 */

public class Button extends GameElements {
    //public float buttonDefaultHeight ;
    public boolean disabled = false;
    public ButtonListener buttonListener = null;
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
        //buttonDefaultHeight = defaultHeight;
        //jumpHeight = defaultHeight;
        //this.defaultHeight = 0;
    }
    public void setButtonListener(ButtonListener buttonListener){
        Log.d("setButtonListener","set");
        this.buttonListener = buttonListener;
    }
    public void setDisabled(boolean x){
        disabled = x;
    }
    public void whenPressed(){
        if(!disabled){
            jumpHeight = -defaultHeight*3/4;
        }else {
            jumpHeight = -defaultHeight*1/4;
        }
    }
    public void whenReleased(boolean within){
        jumpHeight = 0;
        if(within&&buttonListener!=null)buttonListener.doButtonStuff();
    }
}
