package com.work.terry.snakeonastring_jbox2d.SnakeElements;

import android.content.SharedPreferences;
import android.util.Log;

import com.work.terry.snakeonastring_jbox2d.JBox2DElements.CircleBody;
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
    public boolean initSelfFinished = false;

    public Snake snake;
    public  float rotateAngle;
    public String nowFace = SnakeHeadEyesImg;

    public float speed = SnakeHeadSpeed;
    public int SpeedFactor = SnakeHeadSpeedFactor;

    public float HeadVX = 0;
    public float HeadVY = 1;
    Thread movingThread;

    public Nail target = null;
    public SnakeHead(
            float x, float y,
            float vx, float vy,
            float radius,
            int color,
            float jumpHeight
    ){
        super(
                null,
                "snakeHead ",
                x,y,
                0,
                0,0,
                radius,

                color,
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
        this.snake = null;
        this.rotateAngle = calRotateAngleDegrees(vx,vy);
    }
    public SnakeHead(Snake snake,World world,int x, int y, int vx, int vy,int color,float defaultHeight){
        super(
                world,
                "snakeHead",
                x,y,
                (float) ((135/360)*Math.PI),
                vx,vy,
                Constant.SnakeHeadRadius,

                color,
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
        this.snake = snake;
        rotateAngle = calRotateAngleDegrees(vx,vy);
        HeadVX = vx;
        HeadVY = vy;
    }


    public Vec2 getV2D(){
        return new Vec2(HeadVX,HeadVY);
    }
    @Override
    public void drawSelf(TexDrawer painter){
        if(body!=null)
            Log.d(
                    "Draw"+body.getUserData().toString(),
                    "BODYx="+body.getPosition().x*RATE
                            +" BODYy="+body.getPosition().y*RATE
                      +"\nx="+x
                            +" y="+y
                            +" HeadvX="+HeadVX
                            +" HeadvY="+HeadVY
            );
        if(snake!=null)
            if(snake.isDead()){
                rotateAngle =(float) Math.toDegrees(body.getAngle());
               //Vec2 v = getBodyVelocityNormalized();
               //rotateAngle =calRotateAngleDegrees(v.x,v.y);
            }else {
                rotateAngle = calRotateAngleDegrees(HeadVX,HeadVY);
            }
        //rotateAngle =(float) Math.toDegrees(body.getAngle())+90;
        //float AxisRotateAngle =(float) Math.toDegrees(body.getAngle());
        //calRotateAngleRadius(body.getLinearVelocity().x,body.getLinearVelocity().y);

        //drawSelf
        super.drawSelf(painter);
        float SnakeHeadEyesDiameter = radius/Constant.SnakeHeadRatio*2;
        painter.drawColorFactorTex(
                TexManager.getTex(SnakeHeadEyesBallImg),
                ColorManager.getColor(color),
                x,
                y -jumpHeight-defaultHeight+Constant.SnakeEyesDownLittleHeight,
                SnakeHeadEyesDiameter,
                SnakeHeadEyesDiameter,
                rotateAngle,
                Constant.SnakeEyesDownLittleColorFactor
        );
        painter.drawTex(
                TexManager.getTex(nowFace),
                x,
                y-jumpHeight-defaultHeight,
                SnakeHeadEyesDiameter,
                SnakeHeadEyesDiameter,
                rotateAngle
        );

//        painter.drawSelf(
//                TexManager.getTex(axisImg),
//                ColorManager.getColor(Constant.C0LOR_WHITE),
//                x,
//                y-jumpHeight,
//                headEyesDiameter,
//                headEyesDiameter,
//                AxisRotateAngle
//        );
    }
    public void changeFace(String face){
        nowFace = face;
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
    public void whenMotionDown(int touchX,int touchY){
        if(target!=null)target.setDoDraw(false);

        if(touchX!=x||touchY!=y){
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
