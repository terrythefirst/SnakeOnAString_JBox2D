package com.work.terry.snakeonastring_jbox2d;

import android.util.Log;

import com.work.terry.snakeonastring_jbox2d.JBox2DElements.CircleBody;
import com.work.terry.snakeonastring_jbox2d.JBox2DElements.MyBody;
import com.work.terry.snakeonastring_jbox2d.JBox2DElements.MyBox2DRevoluteJoint;
import com.work.terry.snakeonastring_jbox2d.JBox2DElements.MyDistanceJoint;
import com.work.terry.snakeonastring_jbox2d.JBox2DElements.MyWeldJoint;
import com.work.terry.snakeonastring_jbox2d.JBox2DElements.RectBody;
import com.work.terry.snakeonastring_jbox2d.Util.Constant;
import com.work.terry.snakeonastring_jbox2d.Util.MyMath;
import com.work.terry.snakeonastring_jbox2d.Util.TexDrawer;
import com.work.terry.snakeonastring_jbox2d.Util.TexManager;
import com.work.terry.snakeonastring_jbox2d.Util.VectorUtil;

import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.World;

import java.nio.channels.Channels;
import java.security.cert.CertificateNotYetValidException;

import static com.work.terry.snakeonastring_jbox2d.Util.VectorUtil.*;
import static com.work.terry.snakeonastring_jbox2d.Util.Constant.*;

/**
 * Created by Terry on 2017/12/28.
 */

public class SnakeNode extends CircleBody{
    Snake snake;

    public CircleBody front = null;
    public Vec2 frontV;

    public RectBody rectBody = null;
    private MovingThread movingThread = null;
    private float centerDistance = 0;

    public SnakeNode(Snake snake,World world,float x, float y, float vx, float vy,int id){
        super(
                world,
                "snakeBody"+id,
                x,y,
                calRotateAngleDegrees(vx,vy),
                vx,vy,
                Constant.bodyRadius,
                0,
                snakeBodyLinearDampingRate+id*snakeBodyLinearDampingRateFactorInter,
                snakeBodyDensity,
                snakeBodyFriction,
                snakeBodyRestitution,
                Constant.snakeBodyImg
        );
        this.snake = snake;
    }
    public SnakeNode(Snake snake,World world,CircleBody frontNode,int id){
        super(
                world,
                0,0,
                Constant.bodyRadius*2,
                Constant.bodyRadius*2,
                Constant.SnakeDownHeight,
                Constant.snakeBodyImg
        );
        this.snake = snake;
        this.front = frontNode;

        initSelf();
        createCircleBody(
                world,
                "snakeBody"+id,
                x,y,
                front.body.getAngle(),
                0,0,
                Constant.bodyRadius,
                0,
                snakeBodyLinearDampingRate+id*snakeBodyLinearDampingRateFactorInter,
                snakeBodyDensity,
                snakeBodyFriction,
                snakeBodyRestitution
                );
        initJoints();
        startMoving();
    }
    public Vec2 getFrontV2D(){
        if(front instanceof SnakeHead)return ((SnakeHead) front).getV2D();//headV
        else {
            Vec2 frontXY = front.getBodyXY();
            Vec2 frontFrontXY = ((SnakeNode)front).front.getBodyXY();
            return minusV2D(frontFrontXY,frontXY);
        }
    }
    public float getBodyCenterDistance(){
        Vec2 thisXY = this.getBodyXY();
        Vec2 frontXY = front.getBodyXY();
        return VectorUtil.calDistance(minusV2D(thisXY,frontXY));
    }
    public void initSelf(){
        frontV = getFrontV2D();
        Vec2 frontVNormalized = normalize2D(frontV);
        centerDistance = front.radius+this.radius;
        Vec2 frontXY = front.getBodyXY();
        x = frontXY.x - frontVNormalized.x*centerDistance;
        y = frontXY.y - frontVNormalized.y*centerDistance;
    }
    public void initJoints(){
        Vec2 center = getCenterV2(front.getBodyXY(),this.getBodyXY());
        float angle = front.body.getAngle();//calBodyRadians(-frontV.x,-frontV.y);
        float rectBodyLenth = getBodyCenterDistance();
        rectBody = new RectBody(
                world,
                ""+body.getUserData().toString()+"Rect",
                center.x,center.y,
                angle,
                0,0,
                1,rectBodyLenth/2,
                0.01f,
                0.0f,0.0f,
                "",
                false
        );
        //        if(frontBody instanceof SnakeHead){
//            new MyBox2DRevoluteJoint(
//                    ""+body.getUserData().toString()+"RevoltJoint1",
//                    world,
//                    true,
//                    rectBody,
//                    frontBody,
//                    frontBody.body.getPosition(),
//                    true,
//                    (float) ((-30/360)*Math.PI),
//                    (float) ((30/360)*Math.PI),
//                    false,0f,0f
//            );
//        }else {
//
//        }

//        new MyBox2DRevoluteJoint(
//                ""+body.getUserData().toString()+"RevoltJoint1",
//                world,
//                true,
//                rectBody,
//                front,
//                front.body.getPosition(),
//                false,
//                (float) ((-30/360)*Math.PI),
//                (float) ((30/360)*Math.PI),
//                false, 0f, 0f
//        );
//        new MyBox2DRevoluteJoint(
//                ""+body.getUserData().toString()+"RevoltJoint2",
//                world,
//                true,
//                rectBody,
//                this,
//                this.body.getPosition(),
//                false,
//                (float) ((-30/360)*Math.PI),
//                (float) ((30/360)*Math.PI),
//                false, 0f, 0f
//        );
        if(front instanceof SnakeHead){
            new MyWeldJoint(
                    "snake and body joint"+"WeldJoint1",
                    world,
                    true,
                    rectBody,
                    front,
                    //rectBody.body.getPosition(),
                    front.body.getPosition(),
                    0.01f,
                    0.05f
            );
        }else {
            new MyWeldJoint(
                    "snake and body joint"+"WeldJoint1",
                    world,
                    true,
                    rectBody,
                    front,
                    //rectBody.body.getPosition(),
                    front.body.getPosition(),
                    0.0f,
                    1.0f
            );
        }

        new MyWeldJoint(
                "snake and body joint"+"WeldJoint2",
                world,
                true,
                rectBody,
                this,
                this.body.getPosition(),
                0.2f,
                0.8f
        );
//        new MyDistanceJoint(
//                "snake and body joint",
//                world,
//                true,
//                this,
//                frontBody,
//                this.body.getPosition(),
//                frontBody.body.getPosition(),
//                0.0f,
//                0f
//        );
    }
    public void startMoving(){
        movingThread = new MovingThread();
        movingThread.start();
    }

    private class MovingThread extends Thread{
        @Override
        public void run(){
            while(!snake.isDead()){
                if(body==null)continue;
                popXYfromBody();
                changeBodyImpulseByFront();
                try {
                    sleep(5);
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }
        public void changeBodyImpulseByFront(){
            frontV = getFrontV2D();
            Vec2 frontXY = front.getBodyXY();
            float dx = frontXY.x - x;
            float dy = frontXY.y - y;
            Vec2 dxyV = new Vec2(dx,dy);
            Vec2 frontVNormalized = normalize2D(frontV);
            Vec2 targetMoveV = minusV2D(
                    dxyV,
                    Mul2D(
                            frontVNormalized,
                            getBodyCenterDistance()
                    )
            );

//            body.applyForceToCenter(
//                    Mul2D(targetMoveV,10f)
//            );
            body.applyLinearImpulse(
                    Mul2D(targetMoveV,0.01f),
                    body.getPosition(),
                    true
            );
        }
    }

    @Override
    public void drawSelf(TexDrawer painter,float[] color){
        Log.d(
                "Draw"+body.getUserData().toString(),
                "BODYx="+body.getPosition().x*RATE
                        +" BODYy="+body.getPosition().y*RATE
                        +" vX="+body.getLinearVelocity().x*RATE
                        +" vY="+body.getLinearVelocity().y*RATE
                        +"\nx="+x
                        +" y="+y
        );
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
                bodyTopRadius*2,
                bodyTopRadius*2,
                0
        );
//        painter.drawSelf(
//                TexManager.getTex(snakeBodyHeightImg),
//                color,
//                rectBody.x,
//                rectBody.y,
//                10,
//                rectBody.height,
//                (float) Math.toDegrees(rectBody.body.getAngle())
//        );
    }
}
