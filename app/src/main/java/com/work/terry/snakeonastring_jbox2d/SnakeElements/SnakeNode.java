package com.work.terry.snakeonastring_jbox2d.SnakeElements;

import android.content.SharedPreferences;
import android.util.Log;

import com.work.terry.snakeonastring_jbox2d.JBox2DElements.CircleBody;
import com.work.terry.snakeonastring_jbox2d.JBox2DElements.MyBox2DRevoluteJoint;
import com.work.terry.snakeonastring_jbox2d.JBox2DElements.MyDistanceJoint;
import com.work.terry.snakeonastring_jbox2d.JBox2DElements.MyJoint;
import com.work.terry.snakeonastring_jbox2d.JBox2DElements.MyWeldJoint;
import com.work.terry.snakeonastring_jbox2d.JBox2DElements.RectBody;
import com.work.terry.snakeonastring_jbox2d.SurfaceViewAndActivity.GamePlay;
import com.work.terry.snakeonastring_jbox2d.Thread.SnakeNodeMovingThread;
import com.work.terry.snakeonastring_jbox2d.Util.ColorManager;
import com.work.terry.snakeonastring_jbox2d.Util.Constant;
import com.work.terry.snakeonastring_jbox2d.Util.MyMath;
import com.work.terry.snakeonastring_jbox2d.Util.TexDrawer;
import com.work.terry.snakeonastring_jbox2d.Util.TexManager;
import com.work.terry.snakeonastring_jbox2d.Util.VectorUtil;

import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.World;

import java.util.ArrayList;
import java.util.List;

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
    public List<MyJoint> joints = new ArrayList<>();
    private Thread movingThread = null;
    public float centerDistance = 0;

    float angleRect;

    float frontInitX;
    float frontInitY;

    public SnakeNode(//注： 供显示用
            float x, float y,
            float angle,
            SnakeNodeSkinInfo snakeNodeSkinInfo,
                     float scaleRatio,
            float jumpHeight,
            int id
    ){
        super(
                null,
                "snakeBody "+id,
                x,y,
                angle,
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
        setTopImg( snakeNodeSkinInfo.getImg());
        if(!(TopImg==null||TopImg.contains("null")))setIsPureColor(false);
        setTopRatio(snakeNodeSkinInfo.getTopRatio());
    }
    public SnakeNode(
            Snake snake,
            GamePlay gamePlay,
            CircleBody frontNode,
            SnakeNodeSkinInfo snakeNodeSkinInfo,
            float scaleRatio,
            int id
    ){
        super(
                gamePlay,
                "snakeBody "+id,
                0,0,
                0,
                snakeNodeSkinInfo.getRadii()[1]*scaleRatio,

                0,
                Constant.SnakeDefaultHeight,
                Constant.SnakeDownLittleHeight,
                Constant.SnakeDownLittleColorFactor,
                Constant.SnakeHeightColorFactor,
                Constant.SnakeFloorColorFactor,

                SnakeBodyAngularDampingRate,
                SnakeBodyLinearDampingRate+id*SnakeBodyLinearDampingRateFactorInter,
                SnakeBodyDensity,
                SnakeBodyFriction,
                SnakeBodyRestitution,
                false,

                Constant.SnakeBodyImg
        );
        this.setColorFloats255(snakeNodeSkinInfo.getColor255());
        this.snake = snake;
        this.front = frontNode;
        this.frontInitX = front.x;
        this.frontInitY = front.y;
        setTopImg( snakeNodeSkinInfo.getImg());
        if(!(TopImg==null||TopImg.contains("null")))setIsPureColor(false);
        setTopRatio(snakeNodeSkinInfo.getTopRatio());

        //sendCreateTask();
        //startMoving();
    }
    @Override
    public void createBody(){
        initSelf();
        //super.createBody();
        initJoints();
    }
    @Override
    public void deleteBody(){
        super.deleteBody();

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
        this.angleRadian = VectorUtil.calRotateAngleRadius(frontV.x,frontV.y);
        super.createBody();
        //Log.d("bodyInitSelf"+id,"x="+x+" y="+y);

        Vec2 center = getCenterV2(front.getBodyXY(),this.getBodyXY());
        this.angleRect = calBodyRadians(frontVNormalized.x,frontVNormalized.y);
       // Log.e("initSelf","angleDegrees"+Math.toDegrees(angleRect));
        float rectBodyLength = getBodyCenterDistance();
        rectBody = new RectBody(
                gamePlay,
                body.getUserData().toString()+" Rect",
                center.x,center.y,
                angleRect,
                5,rectBodyLength/2,

                this.color,
                0,
                Constant.SnakeDownLittleHeight,
                Constant.SnakeDownLittleColorFactor,
                Constant.SnakeHeightColorFactor,
                Constant.SnakeFloorColorFactor,

                0.0000001f,
                0.0f,0.0f,
                ButtonImgRect,
                false
        );
        rectBody.createBody();
    }
    public void initJoints(){
        joints.add(
                new MyWeldJoint(
                        body.getUserData().toString()+" WeldJoint 1",
                        gamePlay,
                        false,
                        rectBody,
                        front,
                        //rectBody.body.getPosition(),
                        front.body.getPosition(),
                        10.0f,
                        0.70f
                ));

        joints.add(
                new MyWeldJoint(
                        body.getUserData().toString()+" WeldJoint 2",
                        gamePlay,
                        false,
                        rectBody,
                        this,
                        this.body.getPosition(),
                        10.0f,
                        0.50f
                ));

        joints.add(
                new MyDistanceJoint(
                        body.getUserData().toString() +" DistanceJoint 1",
                        gamePlay,
                        false,
                        this,
                        front,
                        this.body.getPosition(),
                        front.body.getPosition(),
                        0.0f,
                        0.0f
                )
        );
//
//        joints.add(
//            new MyBox2DRevoluteJoint(
//                    body.getUserData().toString()+" RevoluteJoint 1",
//                    gamePlay,
//                    false,
//                    front,
//                    rectBody,
//                    //rectBody.body.getPosition(),
//                    front.body.getPosition(),
//                    true,
//                    (float)Math.toRadians(-30),
//                    (float)Math.toRadians(30),
//                    false, 0, 0
//            )
//        );
//        joints.add(
//                new MyBox2DRevoluteJoint(
//                        body.getUserData().toString() +" RevoluteJoint 2",
//                        gamePlay,
//                        false,
//                        this,
//                        rectBody,
//                        //rectBody.body.getPosition(),
//                        this.body.getPosition(),
//                        true,
//                        (float)Math.toRadians(-30),
//                        (float)Math.toRadians(30),
//                        false, 0, 0
//                )
//        );
        for (MyJoint j:joints){
            j.createJoint();
        }
    }
    @Override
    public void drawSelf(TexDrawer painter){
        if(body!=null)
            rotateAngleGameElements =(float) Math.toDegrees(body.getAngle());
        super.drawSelf(painter);

        if(rectBody==null)return;
        rectBody.rotateAngleGameElements = -(float)Math.toDegrees( rectBody.body.getAngle());
        rectBody.drawSelf(painter);
    }
    @Override
    public void sendDeleteTask(){
        super.sendDeleteTask();
        if(rectBody!=null)rectBody.sendDeleteTask();
        for(MyJoint mj:joints)mj.sendDeleteTask();
    }
    public void startMoving(){
//        movingThread = new SnakeNodeMovingThread(this);
//        movingThread.start();
    }
    @Override
    public void onPause(SharedPreferences.Editor editor){

    }
    @Override
    public void onResume(){
        startMoving();
    }
}
