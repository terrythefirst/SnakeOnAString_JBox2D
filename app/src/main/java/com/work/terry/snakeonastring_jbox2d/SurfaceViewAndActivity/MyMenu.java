package com.work.terry.snakeonastring_jbox2d.SurfaceViewAndActivity;


import android.util.Log;
import android.view.MotionEvent;

import com.work.terry.snakeonastring_jbox2d.UI.Button;
import com.work.terry.snakeonastring_jbox2d.UI.ButtonListener;
import com.work.terry.snakeonastring_jbox2d.UI.GameElements;
import com.work.terry.snakeonastring_jbox2d.UI.ImgButton;
import com.work.terry.snakeonastring_jbox2d.UI.LockDownAnimation;
import com.work.terry.snakeonastring_jbox2d.UI.PullMoveAnimation;
import com.work.terry.snakeonastring_jbox2d.Util.ColorManager;
import com.work.terry.snakeonastring_jbox2d.Util.Constant;
import com.work.terry.snakeonastring_jbox2d.Util.DrawUtil;
import com.work.terry.snakeonastring_jbox2d.Util.ImgManager;
import com.work.terry.snakeonastring_jbox2d.Util.TexDrawer;
import com.work.terry.snakeonastring_jbox2d.Util.TexManager;

import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.contacts.PolygonAndCircleContact;

import java.net.PortUnreachableException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Terry on 2018/2/21.
 */

public class MyMenu extends GameElements {
    public float angleRadius;
    public ImgButton closeButton;
    public Button circleButton;
    public Button closeButtonUnder;
    public Button rectButton1;
    public Button rectButton2;
    public DrawUtil drawUtil;
    public List<GameElements>  gameElementsList = new ArrayList<>();
    public List<Button> buttons = new ArrayList<>();

    public MyMenu
            (
                    String id,
                    float x, float y,
                    float width, float height,
                    float angleRadius,
                    int color,
                    float defaultHeight,
                    float topOffset,
                    float topOffsetColorFactor,
                    float heightColorFactor,
                    float floorShadowColorFactor,
                    String Img
            )
    {
        super(
                id,
                x, y,
                width, height,
                color,
                defaultHeight,
                topOffset,
                topOffsetColorFactor,
                heightColorFactor,
                floorShadowColorFactor,
                Img
        );
        this.angleRadius = angleRadius;
        drawUtil = new DrawUtil(null);
        closeButton = new ImgButton(
                0,
                x,y,
                100,
                100,
                Constant.COLOR_GREY,
                20,
                topOffset,
                topOffsetColorFactor,
                heightColorFactor,
                floorShadowColorFactor,
                Constant.DeleteImg,
                Constant.SnakeBodyImg
        );
        closeButton.setTopImgRatio(0.4f);
        closeButton.setButtonListener(
                new ButtonListener(){
                    @Override
                    public void doButtonStuff(){
                        Log.d("closeButton","clicked!!");
                    }
                }
        );
        buttons.add(closeButton);

        closeButtonUnder = new Button(
                0,
                x,y,
                angleRadius*2,
                angleRadius*2,
                color,
                defaultHeight,
                topOffset,
                topOffsetColorFactor,
                heightColorFactor,
                floorShadowColorFactor,
                Constant.SnakeBodyImg
        );
        circleButton = new Button(
                0,
                0,0,
                angleRadius*2,
                angleRadius*2,
                color,
                defaultHeight,
                topOffset,
                topOffsetColorFactor,
                heightColorFactor,
                floorShadowColorFactor,
                Constant.SnakeBodyImg
        );
        rectButton1 = new Button(
                0,
                x,y,
                width,
                height-angleRadius*2,
                color,
                defaultHeight,
                topOffset,
                topOffsetColorFactor,
                heightColorFactor,
                floorShadowColorFactor,
                Constant.SnakeBodyHeightImg
        );
        rectButton2 = new Button(
                0,
                x,y,
                width-angleRadius*2,
                height,
                color,
                defaultHeight,
                topOffset,
                topOffsetColorFactor,
                heightColorFactor,
                floorShadowColorFactor,
                Constant.SnakeBodyHeightImg
        );
    }
    public DrawUtil getDrawUtil(){
        return drawUtil;
    }
    public static void PopFromButtom(MyMenu myMenu){
        new PullMoveAnimation(
                myMenu,
                new Vec2(Constant.SCREEN_WIDTH/2,Constant.SCREEN_HEIGHT/2),
                2,
                0.01f,
                3
        ).start();
    }
    public void addToMenu(GameElements gameElements){
        gameElementsList.add(gameElements);
    }
    @Override
    public void drawSelf(TexDrawer painter){
        closeButtonUnder.setXY(x+width/2,y-height/2);
        closeButtonUnder.drawSelf(painter);
        circleButton.setXY(x-(width/2-angleRadius),y-(height/2-angleRadius));
        circleButton.drawSelf(painter);
        circleButton.setXY(x+(width/2-angleRadius),y-(height/2-angleRadius));
        circleButton.drawSelf(painter);
        circleButton.setXY(x-(width/2-angleRadius),y+(height/2-angleRadius));
        circleButton.drawSelf(painter);
        circleButton.setXY(x+(width/2-angleRadius),y+(height/2-angleRadius));
        circleButton.drawSelf(painter);
        rectButton1.setXY(x,y);
        rectButton1.drawSelf(painter);
        rectButton2.setXY(x,y);
        rectButton2.drawSelf(painter);

        closeButton.setXY(x+width/2,y-height/2-(closeButtonUnder.jumpHeight+closeButtonUnder.defaultHeight));
        closeButton.drawFloorShadow(painter);
        closeButton.drawHeight(painter);
        closeButton.drawSelf(painter);
    }
    @Override
    public void drawHeight(TexDrawer painter){
        closeButtonUnder.setXY(x+width/2,y-height/2);
        closeButtonUnder.drawHeight(painter);
        circleButton.setXY(x-(width/2-angleRadius),y-(height/2-angleRadius));
        circleButton.drawHeight(painter);
        circleButton.setXY(x+(width/2-angleRadius),y-(height/2-angleRadius));
        circleButton.drawHeight(painter);
        circleButton.setXY(x-(width/2-angleRadius),y+(height/2-angleRadius));
        circleButton.drawHeight(painter);
        circleButton.setXY(x+(width/2-angleRadius),y+(height/2-angleRadius));
        circleButton.drawHeight(painter);
        rectButton1.setXY(x,y);
        rectButton1.drawHeight(painter);
        rectButton2.setXY(x,y);
        rectButton2.drawHeight(painter);
    }
    @Override
    public void drawFloorShadow(TexDrawer painter){
        closeButtonUnder.setXY(x+width/2,y-height/2);
        closeButtonUnder.drawFloorShadow(painter);
        circleButton.setXY(x-(width/2-angleRadius),y-(height/2-angleRadius));
        circleButton.drawFloorShadow(painter);
        circleButton.setXY(x+(width/2-angleRadius),y-(height/2-angleRadius));
        circleButton.drawFloorShadow(painter);
        circleButton.setXY(x-(width/2-angleRadius),y+(height/2-angleRadius));
        circleButton.drawFloorShadow(painter);
        circleButton.setXY(x+(width/2-angleRadius),y+(height/2-angleRadius));
        circleButton.drawFloorShadow(painter);
        rectButton1.setXY(x,y);
        rectButton1.drawFloorShadow(painter);
        rectButton2.setXY(x,y);
        rectButton2.drawFloorShadow(painter);
    }

    private Button whichButtonTouched(float x,float y){
        for(Button b:buttons){
            if (b.testTouch(x,y))return b;
        }
        return null;
    }
    private void whenReleased(){
        for(Button b:buttons){
            b.whenReleased();
        }
    }
    public void onTouchEvent(MotionEvent event, int x, int y){
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
            case MotionEvent.ACTION_MOVE:
                Button tempt = whichButtonTouched(x,y);
                if(tempt!=null)tempt.whenPressed();
                break;
            case MotionEvent.ACTION_UP:
                // if(pauseButton.testTouch(x,y))pauseButton
                whenReleased();
                break;
        }
    }
    @Override
    public boolean testTouch(float touchX,float touchY){
        closeButtonUnder.setXY(x+width/2,y-height/2);
        return closeButtonUnder.testTouch(x,y)||(touchX>x-width/2-scaleWidth/2
                &&touchX<x+width/2+scaleWidth/2
                &&touchY>y-height/2-scaleHeight/2
                &&touchY<y+height/2+(jumpHeight+defaultHeight)+scaleHeight/2)
                ;
    }
}
