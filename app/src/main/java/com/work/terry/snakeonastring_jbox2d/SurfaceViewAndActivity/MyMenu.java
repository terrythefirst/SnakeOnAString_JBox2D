package com.work.terry.snakeonastring_jbox2d.SurfaceViewAndActivity;


import android.util.Log;
import android.view.MotionEvent;

import com.work.terry.snakeonastring_jbox2d.UI.Button;
import com.work.terry.snakeonastring_jbox2d.UI.GameElement;
import com.work.terry.snakeonastring_jbox2d.UI.ImgButton;
import com.work.terry.snakeonastring_jbox2d.UI.RoundEdgeRect;
import com.work.terry.snakeonastring_jbox2d.Animation.UniformMotionAnimation;
import com.work.terry.snakeonastring_jbox2d.Util.Constant;
import com.work.terry.snakeonastring_jbox2d.Util.DrawUtil;
import com.work.terry.snakeonastring_jbox2d.Util.TexDrawer;

import org.jbox2d.common.Vec2;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Terry on 2018/2/21.
 */

public class MyMenu extends RoundEdgeRect {
    public GamePlayView gamePlayView;
    public ImgButton closeButton;
    public boolean needDefaultCloseButton = true;
    public Button closeButtonUnder;

    public List<GameElement> gameElementList = new ArrayList<>();
    /*
    * 注：gameElementsList里的东西坐标随整个Menu的移动而移动
    *   记住一定要设置xy常量才能计算  setConstantXY6
    *   为了方便计算 坐标系如下
    *       x轴方向不变 但原点在menu的x从处
    *       y轴方向不变 原点变为menu的顶部y
    * */
    public List<Button> buttons = new ArrayList<>();
    public Button nowPressedButton;


    public MyMenu
            (
                    GamePlayView gamePlayView,
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
                    String Img,
                    boolean needDefaultCloseButton
            )
    {
        super(
                id,
                x, y,
                width, height,
                angleRadius,
                color,
                defaultHeight,
                topOffset,
                topOffsetColorFactor,
                heightColorFactor,
                floorShadowColorFactor,
                Img
        );
        setIsPureColor(true);
        this.needDefaultCloseButton = needDefaultCloseButton;
        this.gamePlayView = gamePlayView;
        //drawUtil = new DrawUtil(null);
        closeButton = new ImgButton(
                0,
                x,y,
                90,
                90,
                Constant.COLOR_GREY,
                10,
                topOffset,
                topOffsetColorFactor,
                heightColorFactor,
                floorShadowColorFactor,
                Constant.DeleteImg,
                Constant.SnakeBodyImg
        );
        closeButton.setTopImgRatio(0.4f);
        closeButton.setButtonListener(
                ()->{
//                    Thread thread = new PullMoveAnimation(
//                            this,
//                            new Vec2(Constant.SCREEN_WIDTH/2,Constant.SCREEN_HEIGHT+height/2),
//                            100f,
//                            0.01f,
//                            1
//                    );
//
//                    thread.start();
//                    try {
//                        thread.join();
//                        gamePlayView.setNowMenu(null);
//                    }catch (Exception e){
//                        e.printStackTrace();
//                    }

                    Thread thread = new UniformMotionAnimation(
                            this,
                            new Vec2(Constant.SCREEN_WIDTH/2,Constant.SCREEN_HEIGHT+height/2),
                            0.5f
                    );

                    thread.start();
                    try {
                        thread.join();
                        gamePlayView.setNowMenu(null);
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }
        );
        if (needDefaultCloseButton){
            buttons.add(closeButton);
        }

        closeButtonUnder = new Button(
                0,
                x,y,
                160,
                160,
                color,
                defaultHeight,
                topOffset,
                topOffsetColorFactor,
                heightColorFactor,
                floorShadowColorFactor,
                Constant.SnakeBodyImg
        );

    }
    public static void PopFromButtom(MyMenu myMenu){
//        new PullMoveAnimation(
//                myMenu,
//                new Vec2(Constant.SCREEN_WIDTH/2,Constant.SCREEN_HEIGHT/2),
//                0.4f,
//                0.01f,
//                3
//        ).start();
    }
    public void addToMenu(GameElement gameElement){
        gameElementList.add(gameElement);
    }
    public void addButton(Button button){
        buttons.add(button);
        gameElementList.add(button);
    }

    @Override
    public void drawSelf(TexDrawer painter){
        if (needDefaultCloseButton){
            closeButtonUnder.setXY(x+width/2-angleRadius/3,y-height/2+angleRadius/3);
            closeButtonUnder.drawSelf(painter);
        }
        super.drawSelf(painter);
        if (needDefaultCloseButton){
            closeButton.setXY(x+width/2-angleRadius/3,y-height/2+angleRadius/3-(closeButtonUnder.jumpHeight+closeButtonUnder.defaultHeight));
            closeButton.drawFloorShadow(painter);
            closeButton.drawHeight(painter);
            closeButton.drawSelf(painter);
        }
        for(GameElement gameElement : gameElementList){
            gameElement.setXY(
                    x+ gameElement.constantXY.x,
                    y-height/2+ gameElement.constantXY.y
            );
            gameElement.drawFloorShadow(painter);
            gameElement.drawHeight(painter);
            gameElement.drawSelf(painter);
            //Log.d("Menu GameElementsList","x="+gameElement.x+" y="+gameElement.y);
        }
        //drawUtil.stepDraw(painter);
    }
    @Override
    public void drawHeight(TexDrawer painter){
        if (needDefaultCloseButton){
            closeButtonUnder.setXY(x+width/2-angleRadius/3,y-height/2+angleRadius/3);
            closeButtonUnder.drawHeight(painter);
        }

        super.drawHeight(painter);
    }
    @Override
    public void drawFloorShadow(TexDrawer painter){
        if (needDefaultCloseButton){
            closeButtonUnder.setXY(x+width/2-angleRadius/3,y-height/2+angleRadius/3);
            closeButtonUnder.drawFloorShadow(painter);
        }
        super.drawFloorShadow(painter);
    }

    private Button whichButtonTouched(float x,float y){
        for(Button b:buttons){
            if (b.testTouch(x,y))return b;
        }
        return null;
    }
    private void whenUp(float x,float y){
        if(nowPressedButton!=null)
            nowPressedButton.whenReleased(nowPressedButton.testTouch(x,y));
    }
    public void onTouchEvent(MotionEvent event, float x, float y){
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
            case MotionEvent.ACTION_MOVE:
                Button tempt = nowPressedButton;
                nowPressedButton = whichButtonTouched(x,y);
                if(tempt!=null&&nowPressedButton!=tempt)tempt.whenReleased(false);
                if(nowPressedButton!=null)nowPressedButton.whenPressed();
                break;
            case MotionEvent.ACTION_UP:
                // if(pauseButton.testTouch(x,y))pauseButton
                whenUp(x,y);
                break;
        }
    }
    @Override
    public boolean testTouch(float touchX,float touchY){
        return closeButtonUnder.testTouch(touchX,touchY)||(touchX>x-width/2-scaleWidth/2
                &&touchX<x+width/2+scaleWidth/2
                &&touchY>y-height/2-scaleHeight/2
                &&touchY<y+height/2+(jumpHeight+defaultHeight)+scaleHeight/2)
                ;
    }
}
