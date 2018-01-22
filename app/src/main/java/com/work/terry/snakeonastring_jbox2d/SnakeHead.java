package com.work.terry.snakeonastring_jbox2d;

import android.util.Log;

import com.work.terry.snakeonastring_jbox2d.JBox2DElements.CircleBody;
import com.work.terry.snakeonastring_jbox2d.JBox2DElements.MyBox2DRevoluteJoint;
import com.work.terry.snakeonastring_jbox2d.JBox2DElements.RectBody;
import com.work.terry.snakeonastring_jbox2d.Util.ColorManager;
import com.work.terry.snakeonastring_jbox2d.Util.Constant;
import com.work.terry.snakeonastring_jbox2d.Util.MyMath;
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

    Snake snake;
    Double sinRotateAngleStep = Math.sin(Math.toRadians(Constant.snakeHeadRotateStepAngle));
    Double cosRotateAngleStep = Math.cos(Math.toRadians(Constant.snakeHeadRotateStepAngle));
    public  float rotateAngle;

    private float speed = snakeHeadSpeed;
    private int SpeedFactor = snakeHeadSpeedFactor;

    public float HeadVX = 0;
    public float HeadVY = 1;
    Thread movingThread;

    Target target = null;
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

    private class Target{
        public float TargetHeadX = 720;
        public float TargetHeadY = 1280;
        public float TargetHeadVX = 0;
        public float TargetHeadVY = 1;

        public Target(float touchX,float touchY,float HeadX,float HeadY){
            setTarget(touchX,touchY,HeadX,HeadY);
        }
        public void setTarget(float touchX,float touchY,float HeadX,float HeadY){
            TargetHeadX = touchX;
            TargetHeadY = touchY;
            float[] normalTXY = VectorUtil.normalize2D(touchX - HeadX,touchY - HeadY);
            TargetHeadVX = normalTXY[0];
            TargetHeadVY = normalTXY[1];
            //Log.d("Target","target x="+TargetHeadX+" y="+TargetHeadY+"\n vx="+TargetHeadVX+" vy="+TargetHeadVY);
        }
    }
    private class MovingThread extends Thread {
        Vec2 bodyV;
        public void run () {
            while (!snake.isDead()) {
                bodyV = body.getLinearVelocity();//getBodyVelocityNormalized();
                //popXYfromBody();
                float dx = target.TargetHeadX - x;
                float dy = target.TargetHeadY - y;
                Vec2 vecDXY = new Vec2(dx, dy);
                //vecDXY = normalize2D(vecDXY);
                vecDXY = plusV2D(vecDXY,bodyV);
                vecDXY = normalize2D(vecDXY);
//                x += vecDXY .x * speedFactor(dx);
//                y += vecDXY .y * speedFactor(dy);
                //pushXYintoBody();


//                float dvx = target.TargetHeadVX - HeadVX;
//                float dvy = target.TargetHeadVY - HeadVY;
//                float[] normalvXY = normalize2D(dvx, dvy);
//                float[] nornalvPresentXY = normalize2D(HeadVX, HeadVY);
//                HeadVX = nornalvPresentXY[0];
//                HeadVY = nornalvPresentXY[1];
//
//                float dotMul = dotMul2D(dvx, dvy, HeadVX, HeadVY);
//
//                //Log.d("dotMul", dotMul + "");
//                if (!isReverse2D(dvx, dvy, HeadVX, HeadVY) && Math.abs(dotMul) > 0.3f) {
//                    HeadVX += normalvXY[0];
//                    HeadVY += normalvXY[1];
//                } else {
//                    HeadVX = target.TargetHeadVX;
//                    HeadVY = target.TargetHeadVY;
//                }

                float dvx = target.TargetHeadVX - HeadVX;
                float dvy = target.TargetHeadVY - HeadVY;
                Vec2 normalDvXY = new Vec2(dvx, dvy);
                normalDvXY = normalize2D(normalDvXY);
                Vec2 nornalvPresentXY = normalize2D(new Vec2(HeadVX, HeadVY));
                HeadVX = nornalvPresentXY.x;
                HeadVY = nornalvPresentXY.y;

                HeadVX += normalDvXY.x*sinRotateAngleStep;
                HeadVY += normalDvXY.y*sinRotateAngleStep;

//                setBodyVelocity(
//                        HeadVX* speedFactor(dx),
//                        HeadVY* speedFactor(dy)
//                );
                setBodyVelocity(
                        vecDXY .x* speedFactor(dx),
                        vecDXY .y* speedFactor(dy)
                );
                try {
                    sleep(1);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private float speedFactor(float x){
        float vv = MyMath.smoothStep(0,SpeedFactor,Math.abs(x));
        return vv*speed;
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
    public void moving(){
        target = new Target(
                x, y,
                0, 1
        );
        movingThread =  new MovingThread();
        movingThread.start();
    }
    public void whenMotionDown(int touchX,int touchY){
        if(touchX!=x||touchY!=y){
            target.setTarget(touchX,touchY,x,y);
        }
    }
}
