package com.work.terry.snakeonastring_jbox2d.SnakeElements;

import android.util.Log;

import com.work.terry.snakeonastring_jbox2d.JBox2DElements.CircleBody;
import com.work.terry.snakeonastring_jbox2d.Thread.SnakeHeadMovingThread;
import com.work.terry.snakeonastring_jbox2d.Util.ColorManager;
import com.work.terry.snakeonastring_jbox2d.Util.Constant;
import com.work.terry.snakeonastring_jbox2d.Util.TexDrawer;
import com.work.terry.snakeonastring_jbox2d.Util.TexManager;
import com.work.terry.snakeonastring_jbox2d.Util.VectorUtil;

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

    public float speed = snakeHeadSpeed;
    public int SpeedFactor = snakeHeadSpeedFactor;

    public float HeadVX = 0;
    public float HeadVY = 1;
    Thread movingThread;

    public SnakeHeadTarget target = null;
    public SnakeHead(Snake snake,World world,int x, int y, int vx, int vy){
        super(
                world,
                "snakeHead",
                x,y,
                (float) ((135/360)*Math.PI),
                vx,vy,
                Constant.headRadius,
                snakeHeadAngularDampingRate,
                snakeHeadLinearDampingRate,
                snakeHeadDensity,
                snakeHeadFriction,
                snakeHeadRestitution,
                false,
                Constant.snakeHeadHeadImg
        );
        this.snake = snake;
        rotateAngle = calRotateAngleDegrees(vx,vy);
//        squareBody = new RectBody(
//                world,
//                ""+body.getUserData().toString()+"Rect",
//                body.getPosition().x,body.getPosition().y,
//                rotateAngle,
//                0,0,
//                4,4,
//                "",
//                false
//        );
//        new MyBox2DRevoluteJoint(
//                ""+body.getUserData().toString()+"RevoltJoint1",
//                world,
//                false,
//                squareBody,
//                this,
//                this.getBodyXY(),
//                true,
//                0,0,
//                false,0f,0f
//        );
        //pushXYintoBody();
    }


    public Vec2 getV2D(){
        return new Vec2(HeadVX,HeadVY);
    }
    @Override
    public void drawSelf(TexDrawer painter, float[] color){
        Log.d(
                "Draw"+body.getUserData().toString(),
                "BODYx="+body.getPosition().x*RATE
                        +" BODYy="+body.getPosition().y*RATE
                  +"\nx="+x
                        +" y="+y
                        +" HeadvX="+HeadVX
                        +" HeadvY="+HeadVY
        );
        if(snake.isDead()){
            rotateAngle =(float) Math.toDegrees(body.getAngle());
           //Vec2 v = getBodyVelocityNormalized();
           //rotateAngle =calRotateAngleDegrees(v.x,v.y);
        }else {
            rotateAngle = calRotateAngleDegrees(HeadVX,HeadVY);
        }
        float AxisRotateAngle =(float) Math.toDegrees(body.getAngle());
        //calRotateAngleRadius(body.getLinearVelocity().x,body.getLinearVelocity().y);
        painter.drawDownShadow(
                TexManager.getTex(Img),
                color,
                x,
                y-jumpHeight,
                width,
                height,
                0,
                0,
                Constant.SnakeDownLittleHeight,
                Constant.SnakeDownLittleColorFactor
        );
        painter.drawColorSelf(
                TexManager.getTex(Img),
                color,
                x,
                y-jumpHeight,
                Constant.headTopRadius*2,
                height,
                0
        );

        painter.drawDownShadow(
                TexManager.getTex(snakeHeadEyesBallImg),
                color,
                x,
                y-jumpHeight,
                headEyesDiameter,
                headEyesDiameter,
                rotateAngle,
                0,
                Constant.SnakeEyesDownLittleHeight,
                Constant.SnakeEyesDownLittleColorFactor
        );
        if(snake.isDead()){
            painter.drawSelf(
                    TexManager.getTex(snakeHeadDeadEyesImg),
                    ColorManager.getColor(Constant.C0LOR_WHITE),
                    x,
                    y-jumpHeight,
                    headEyesDiameter,
                    headEyesDiameter,
                    rotateAngle
            );
        }else {
            painter.drawSelf(
                    TexManager.getTex(snakeHeadEyesImg),
                    ColorManager.getColor(Constant.C0LOR_WHITE),
                    x,
                    y-jumpHeight,
                    headEyesDiameter,
                    headEyesDiameter,
                    rotateAngle
            );
        }
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
    @Override
    public void drawHeightShadow(TexDrawer painter,float[] color){
        painter.drawDownShadow(
                TexManager.getTex(Img),
                color,
                x,
                y,
                width,
                height,
                0,
                0,
                downHeight,
                Constant.SnakeHeightColorFactor
        );
        painter.drawDownShadow(
                TexManager.getTex(snakeBodyHeightImg),
                color,
                x,
                y-downHeight/2-jumpHeight/2,
                width*Constant.SnakeHeadRatio,
                downHeight+jumpHeight,
                0,
                0,
                downHeight,
                Constant.SnakeHeightColorFactor
        );
    }
    public void startMoving(){
        target = new SnakeHeadTarget(
                x, y,
                0, 1
        );
        movingThread =  new SnakeHeadMovingThread(this);
        movingThread.start();
    }
    public void whenMotionDown(int touchX,int touchY){
        if(touchX!=x||touchY!=y){
            target.setTarget(touchX,touchY,x,y);
        }
    }
}
