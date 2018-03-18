package com.work.terry.snakeonastring_jbox2d.SnakeElements;

import android.content.SharedPreferences;
import android.util.Log;

import com.work.terry.snakeonastring_jbox2d.JBox2DElements.CircleBody;
import com.work.terry.snakeonastring_jbox2d.SurfaceViewAndActivity.GamePlay;
import com.work.terry.snakeonastring_jbox2d.Thread.SnakeHeadMovingThread;
import com.work.terry.snakeonastring_jbox2d.Util.ColorManager;
import com.work.terry.snakeonastring_jbox2d.Util.Constant;
import com.work.terry.snakeonastring_jbox2d.Util.TexDrawer;
import com.work.terry.snakeonastring_jbox2d.Util.TexManager;

import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.World;

import static com.work.terry.snakeonastring_jbox2d.Util.Constant.*;
import static com.work.terry.snakeonastring_jbox2d.Util.VectorUtil.*;
/**
 * Created by Terry on 2017/12/27.
 */

public class SnakeHead extends CircleBody{
    public Snake snake;

    //public String nowFace;

    public float speed = SnakeHeadSpeed;
    public int SpeedFactor = SnakeHeadSpeedFactor;

    public float HeadVX = 0;
    public float HeadVY = 1;
    Thread movingThread;

    public Nail target = null;
    public SnakeHead(//注： 供显示用
            float x, float y,
            float vx, float vy,
            SnakeNodeSkinInfo snakeNodeSkinInfo,
            float scaleRatio,
            float jumpHeight
    ){
        super(
                null,
                "snakeHead ",
                x,y,
                0,
                snakeNodeSkinInfo.getRadii()[1]*scaleRatio,

                0,
                jumpHeight,
                Constant.SnakeDownLittleHeight,
                Constant.SnakeDownLittleColorFactor,
                Constant.SnakeHeightColorFactor,
                Constant.SnakeFloorColorFactor,

                0,
                SnakeBodyLinearDampingRate,//+id*SnakeBodyLinearDampingRateFactorInter,
                SnakeBodyDensity,
                SnakeBodyFriction,
                SnakeBodyRestitution,
                false,
                Constant.SnakeBodyImg
        );
        this.setColorFloats255(snakeNodeSkinInfo.getColor255());
        this.snake = null;
        this.rotateAngleGameElements = calRotateAngleDegrees(vx,vy);
        this.HeadVX = vx;
        this.HeadVY = vy;
        setTopImg( snakeNodeSkinInfo.getImg());
        //this.nowFace = this.TopImg;
        setTopRatio(snakeNodeSkinInfo.getTopRatio());
        setIsPureColor(false);
    }
    public SnakeHead(Snake snake, GamePlay gamePlay, float x, float y, float vx, float vy, SnakeNodeSkinInfo snakeNodeSkinInfo,float scaleRatio, float defaultHeight){
        super(
                gamePlay,
                "snakeHead",
                x,y,
                (float) ((135/360)*Math.PI),
                snakeNodeSkinInfo.getRadii()[1]*scaleRatio,
                0,
                defaultHeight,
                SnakeDownLittleHeight,
                SnakeDownLittleColorFactor,
                SnakeHeightColorFactor,
                SnakeFloorColorFactor,

                SnakeHeadAngularDampingRate,
                SnakeHeadLinearDampingRate,
                SnakeHeadDensity,
                SnakeHeadFriction,
                SnakeHeadRestitution,
                false,
                Constant.SnakeBodyImg
        );
        this.setColorFloats255(snakeNodeSkinInfo.getColor255());
        this.snake = snake;
        setTopImg( snakeNodeSkinInfo.getImg());
        setTopRatio(snakeNodeSkinInfo.getTopRatio());
        setIsPureColor(false);

        rotateAngleGameElements = calRotateAngleDegrees(vx,vy);
        HeadVX = vx;
        HeadVY = vy;
    }

    public Vec2 getV2D(){
        return new Vec2(HeadVX,HeadVY);
    }
    @Override
    public void drawSelf(TexDrawer painter){
        if(snake!=null&&snake.isDead()){
            changeFace(SnakeSkinManager.getSkin(snake.getSkinNumber(),SnakeHeadDeadImgCode).getImg());
            rotateAngleGameElements =(float) Math.toDegrees(body.getAngle());
        }else {
            rotateAngleGameElements = calRotateAngleDegrees(HeadVX,HeadVY);
        }
        if(body!=null)
            Log.e("snakeHeadDrawSelf","V x="+body.getLinearVelocity() .x+" y="+body.getLinearVelocity() .y);
        super.drawSelf(painter);
    }
    public void changeFace(String face){
        TopImg = face;
    }
    public void startMoving(){
        target = new Nail(
                x+HeadVX, y+HeadVY,
                x, y,
                3
        );
        movingThread =  new SnakeHeadMovingThread(this);
        movingThread.start();
    }
    public void whenMotionDown(float touchX,float touchY){
        if(target!=null)target.setDoDraw(false);
        if(touchX!=x||touchY!=y){
            target.setReached(false);
            target.setTarget(touchX,touchY,x,y);
        }
    }
    public void whenMotionUp(){
        if(target!=null)target.setDoDraw(true);
    }

    @Override
    public void onResume(){
        movingThread =  new SnakeHeadMovingThread(this);
        movingThread.start();
    }
    @Override
    public void onPause(SharedPreferences.Editor editor){
        editor.putFloat(id+"defaultHeight",defaultHeight);
    }
}
