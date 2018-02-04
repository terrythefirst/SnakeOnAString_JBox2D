package com.work.terry.snakeonastring_jbox2d.SnakeElements;

import android.content.SharedPreferences;
import android.util.Log;

import com.work.terry.snakeonastring_jbox2d.JBox2DElements.CircleBody;
import com.work.terry.snakeonastring_jbox2d.JBox2DElements.MyBox2DRevoluteJoint;
import com.work.terry.snakeonastring_jbox2d.JBox2DElements.MyDistanceJoint;
import com.work.terry.snakeonastring_jbox2d.JBox2DElements.MyWeldJoint;
import com.work.terry.snakeonastring_jbox2d.JBox2DElements.RectBody;
import com.work.terry.snakeonastring_jbox2d.Thread.SnakeNodeMovingThread;
import com.work.terry.snakeonastring_jbox2d.Util.ColorManager;
import com.work.terry.snakeonastring_jbox2d.Util.Constant;
import com.work.terry.snakeonastring_jbox2d.Util.TexDrawer;
import com.work.terry.snakeonastring_jbox2d.Util.TexManager;
import com.work.terry.snakeonastring_jbox2d.Util.VectorUtil;

import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.World;

import static com.work.terry.snakeonastring_jbox2d.Util.VectorUtil.*;
import static com.work.terry.snakeonastring_jbox2d.Util.Constant.*;

/**
 * Created by Terry on 2017/12/28.
 */

public class SnakeNode extends CircleBody{
    public Snake snake;

    public CircleBody front = null;
    public Vec2 frontV;

    public RectBody rectBody = null;
    private Thread movingThread = null;
    public float centerDistance = 0;

    public SnakeNode(Snake snake,World world,float x, float y, float vx, float vy,int color,float jumpHeight,int id){
        super(
                world,
                "snakeBody "+id,
                x,y,
                calRotateAngleDegrees(vx,vy),
                vx,vy,
                Constant.SnakeBodyRadius,

                color,
                jumpHeight,
                Constant.SnakeDownLittleHeight,
                Constant.SnakeDownLittleColorFactor,
                Constant.SnakeHeightColorFactor,
                Constant.SnakeFloorColorFactor,

                0,
                SnakeBodyLinearDampingRate+id*SnakeBodyLinearDampingRateFactorInter,
                SnakeBodyDensity,
                SnakeBodyFriction,
                SnakeBodyRestitution,
                false,
                Constant.SnakeBodyImg
        );
        this.snake = snake;
    }
    public SnakeNode(Snake snake,World world,CircleBody frontNode,int id){
        super(
                world,
                "snakeBody "+id,
                0,0,
                Constant.SnakeBodyRadius*2,
                Constant.SnakeBodyRadius*2,

                frontNode.color,
                Constant.SnakeDefaultHeight,
                Constant.SnakeDownLittleHeight,
                Constant.SnakeDownLittleColorFactor,
                Constant.SnakeHeightColorFactor,
                Constant.SnakeFloorColorFactor,

                Constant.SnakeBodyImg
        );
        this.snake = snake;
        this.front = frontNode;

        initSelf();
        createCircleBody(
                world,
                "snakeBody "+id,
                x,y,
                front.body.getAngle(),
                0,0,
                Constant.SnakeBodyRadius,
                0,
                SnakeBodyLinearDampingRate+id*SnakeBodyLinearDampingRateFactorInter,
                SnakeBodyDensity,
                SnakeBodyFriction,
                SnakeBodyRestitution,
                false
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
        while (world.isLocked()){
            Log.d("world","LOKED!");
        }
        frontV = getFrontV2D();
        Vec2 frontVNormalized = normalize2D(frontV);
        centerDistance = front.radius+this.radius;
        Vec2 frontXY = front.getBodyXY();
        x = frontXY.x - frontVNormalized.x*centerDistance;
        y = frontXY.y - frontVNormalized.y*centerDistance;
        Log.d("bodyInitSelf"+id,"x="+x+" y="+y);

        setTopRatio(SnakeBodyTopRadius*1.0f/SnakeBodyRadius);
    }
    public void initJoints(){
        Vec2 center = getCenterV2(front.getBodyXY(),this.getBodyXY());
        float angle = calBodyRadians(front.x-x,front.y-y);//front.body.getAngle();//calBodyRadians(-frontV.x,-frontV.y);
        float rectBodyLenth = getBodyCenterDistance();
        rectBody = new RectBody(
                world,
                body.getUserData().toString()+" Rect",
                center.x,center.y,
                angle,
                0,0,
                1,rectBodyLenth/2,

                this.color,
                0,
                Constant.SnakeDownLittleHeight,
                Constant.SnakeDownLittleColorFactor,
                Constant.SnakeHeightColorFactor,
                Constant.SnakeFloorColorFactor,

                0.01f,
                0.0f,0.0f,
                ButtonImgRect,
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
//        if(front instanceof SnakeHead){
//            new MyWeldJoint(
//                    body.getUserData().toString()+" WeldJoint 1",
//                    world,
//                    false,
//                    rectBody,
//                    front,
//                    //rectBody.body.getPosition(),
//                    front.body.getPosition(),
//                    0.01f,
//                    0.01f
//            );
//        }else {
            new MyWeldJoint(
                    body.getUserData().toString()+" WeldJoint 1",
                    world,
                    false,
                    rectBody,
                    front,
                    //rectBody.body.getPosition(),
                    front.body.getPosition(),
                    1.0f,
                    0.70f
            );
        //}

        new MyWeldJoint(
                body.getUserData().toString()+" WeldJoint 2",
                world,
                false,
                rectBody,
                this,
                this.body.getPosition(),
                20.0f,
                0.50f
        );
//
        new MyDistanceJoint(
                body.getUserData().toString() +" DistanceJoint 1",
                world,
                false,
                this,
                front,
                this.body.getPosition(),
                front.body.getPosition(),
                0.0f,
                0.0f
        );
//            new MyBox2DRevoluteJoint(
//                    body.getUserData().toString()+" RevoluteJoint 1",
//                    world,
//                    false,
//                    front,
//                    rectBody,
//                    //rectBody.body.getPosition(),
//                    front.body.getPosition(),
//                    true,
//                    (float)Math.toRadians(-30),
//                    (float)Math.toRadians(30),
//                    false, 0, 0
//            );
//        new MyBox2DRevoluteJoint(
//                body.getUserData().toString() +" RevoluteJoint 1",
//                world,
//                false,
//                this,
//                rectBody,
//                this.body.getPosition(),
//                true,
//                (float)Math.toRadians(-30),
//                (float)Math.toRadians(30),
//                false, 0, 0
//        );
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
    @Override
    public void drawSelf(TexDrawer painter){
        super.drawSelf(painter);

        rectBody.rotateAngleGameElements = -(float)Math.toDegrees(rectBody.body.getAngle());
        //rectBody.rotateAngleGameElements = 0;
                Log.d("rectBody","rotateAngle"+rectBody.rotateAngleGameElements%360);
        rectBody.drawSelf(painter);
    }
    @Override
    public void destroySelf(){
        super.destroySelf();
        rectBody.destroySelf();
    }
    public void startMoving(){
        movingThread = new SnakeNodeMovingThread(this);
        movingThread.start();
    }
    @Override
    public void onPause(SharedPreferences.Editor editor){

    }
    @Override
    public void onResume(){
        startMoving();
    }
}
