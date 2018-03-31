package com.work.terry.snakeonastring_jbox2d.SnakeElements;

import com.work.terry.snakeonastring_jbox2d.Animation.DisappearAnimation;
import com.work.terry.snakeonastring_jbox2d.UI.GameElement;
import com.work.terry.snakeonastring_jbox2d.Util.Constant;
import com.work.terry.snakeonastring_jbox2d.Util.TexDrawer;
import com.work.terry.snakeonastring_jbox2d.Util.TexManager;
import com.work.terry.snakeonastring_jbox2d.Util.VectorUtil;

import org.jbox2d.common.Vec2;

import static com.work.terry.snakeonastring_jbox2d.Util.Constant.NailShadowImg;
import static com.work.terry.snakeonastring_jbox2d.Util.Constant.NailVerticalImg;
import static com.work.terry.snakeonastring_jbox2d.Util.Constant.SnakeFloorColorFactor;
import static com.work.terry.snakeonastring_jbox2d.Util.Constant.SnakeHeightColorFactor;

/**
 * Created by Terry on 2018/1/31.
 */

public class Nail extends GameElement {
    DisappearAnimation disappearAnimation;
    public float TargetHeadVX = 0;
    public float TargetHeadVY = 1;
    private SnakeHead snakeHead;
    public GameElement nailTop;
    public GameElement nailString;
    public boolean reached = false;

    private boolean doDrawNail = true;

    private static float nailDefaultHeight = 30;

    public Nail(float x, float y, float vx, float vy, float[] colorFloats,SnakeHead snakeHead) {
        super(
                "Nail",
                x,y,
                20, 20,
                colorFloats,
                nailDefaultHeight,
                0,
                0,
                SnakeHeightColorFactor,
                SnakeFloorColorFactor,
                Constant.SnakeBodyImg);
        //setDoDrawHeight(false);
        setDoDraw(true);
        setTarget(x,y,vx,vy);
        setFloorShadowFactorX(0.1f);
        setFloorShadowFactorY(0.2f);
        this.snakeHead = snakeHead;
        nailString = new GameElement(
                "nailString",
                0,0,
                16,0,
                Constant.COLOR_RED,
                nailDefaultHeight,
                0,0,0,
                Constant.SnakeFloorColorFactor,

                Constant.SnakeBodyHeightImg
        );
        nailString.setFloorShadowFactorX(0.1f);
        nailString.setFloorShadowFactorY(0.2f);
        nailTop = new GameElement(
                "nailTop",
                x,y,
                40,40,
                colorFloats,
                nailDefaultHeight,
                5,
                Constant.ButtonBlockTopOffSetColorFactor,
                0,
                Constant.SnakeFloorColorFactor,

                Constant.SnakeBodyImg
        );
    }
    public void setDoDrawNail(boolean x){
        doDrawNail = x;
    }
    public void setReached(boolean x){
        reached = x;
    }
    public boolean getReached(){
        return reached;
    }
    @Override
    public void drawSelf(TexDrawer painter){
        nailTop.setXY(x,y);
        Vec2 headXYV = snakeHead.getBodyXY();
        Vec2 nailStringXYV =  VectorUtil.getCenterV2(headXYV,new Vec2(x,y));
        Vec2 nailStringV = VectorUtil.minusV2D(new Vec2(x,y),headXYV);
        nailString.rotateAngleGameElements = VectorUtil.calRotateAngleDegrees(nailStringV.x,nailStringV.y);
        nailString.setXY(nailStringXYV.x,nailStringXYV.y);
        nailString.TopHeight = VectorUtil.calDistance(nailStringV);
        nailString.height = nailString.TopHeight;

        if(doDrawNail)super.drawSelf(painter);
        nailString.drawSelf(painter);
        if(doDrawNail)nailTop.drawSelf(painter);
    }
    @Override
    public void drawHeight(TexDrawer painter){
        if(doDrawNail)super.drawHeight(painter);
    }
    @Override
    public void drawFloorShadow(TexDrawer painter){
        if(doDrawNail)super.drawFloorShadow(painter);
        nailString.drawFloorShadow(painter);
        //nailTop.drawFloorShadow(painter);
    }
    public void setTarget(float touchX,float touchY,float HeadX,float HeadY){
        if(disappearAnimation!=null){
            disappearAnimation.setShouldDie();
            disappearAnimation = null;
        }
        if(nailTop!=null){
            disappearAnimation = new DisappearAnimation(
                    nailTop,
                    false,
                    0.2f
            );
            disappearAnimation.start();
        }


        x = touchX;
        y = touchY;
        float[] normalTXY = VectorUtil.normalize2D(touchX - HeadX,touchY - HeadY);
        TargetHeadVX = normalTXY[0];
        TargetHeadVY = normalTXY[1];
        //Log.d("Target","target x="+TargetHeadX+" y="+TargetHeadY+"\n vx="+TargetHeadVX+" vy="+TargetHeadVY);
    }

}
