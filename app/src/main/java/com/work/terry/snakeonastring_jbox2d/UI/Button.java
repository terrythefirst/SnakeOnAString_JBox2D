package com.work.terry.snakeonastring_jbox2d.UI;

import android.util.Log;

import com.work.terry.snakeonastring_jbox2d.Util.SoundPoolManager;

/**
 * Created by Terry on 2017/12/30.
 */

public class Button extends GameElement {
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
        setIsPureColor(true);
        //buttonDefaultHeight = defaultHeight;
        //jumpHeight = defaultHeight;
        //this.defaultHeight = 0;
    }
    public void setButtonListener(ButtonListener buttonListener){
        this.buttonListener = buttonListener;
    }
    public void setDisabled(boolean x){
        disabled = x;
    }
    public void whenPressed(){
        //SoundPoolManager.play(SoundPoolManager.buttonPress,0);
        if(!disabled){
            jumpHeight = -defaultHeight*3/4;
        }else {
            jumpHeight = -defaultHeight*1/4;
        }
    }
    public void whenReleased(boolean within){
        SoundPoolManager.play(SoundPoolManager.snakeBodyPopUpSound,0);
        jumpHeight = 0;
        if(!disabled&&within&&buttonListener!=null)buttonListener.doButtonStuff();
    }
    public void doButtonStuff(){
        if(buttonListener!=null)buttonListener.doButtonStuff();
    }
}
