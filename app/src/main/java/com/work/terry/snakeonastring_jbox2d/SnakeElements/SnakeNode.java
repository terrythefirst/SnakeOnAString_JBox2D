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

    public SnakeNode(//注： 供显示用
            float x, float y,
            float vx,float vy,
            SnakeNodeSkinInfo snakeNodeSkinInfo,
            float jumpHeight,
            int id
    ){
        super(
                null,
                "snakeBody "+id,
                x,y,
                0,
                vx,vy,
                snakeNodeSkinInfo.getRadii()[1],

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
    public SnakeNode(Snake snake,World world,CircleBody frontNode,SnakeNodeSkinInfo snakeNodeSkinInfo,int id){
        super(
                world,
                "snakeBody "+id,
                0,0,
                snakeNodeSkinInfo.getRadii()[1]*2,
                snakeNodeSkinInfo.getRadii()[1]*2,

                0,
                Constant.SnakeDefaultHeight,
                Constant.SnakeDownLittleHeight,
                Constant.SnakeDownLittleColorFactor,
                Constant.SnakeHeightColorFactor,
                Constant.SnakeFloorColorFactor,

                Constant.SnakeBodyImg
        );
        this.setColorFloats255(snakeNodeSkinInfo.getColor255());
        this.snake = snake;
        this.front = frontNode;
        setTopImg( snakeNodeSkinInfo.getImg());
        if(!(TopImg==null||TopImg.contains("null")))setIsPureColor(false);
        setTopRatio(snakeNodeSkinInfo.getTopRatio());

        initSelf();
        Vec2 frontV = getFrontV2D();
        createCircleBody(
                world,
                "snakeBody "+id,
                x,y,
                front.body.getAngle(),
                frontV.x,frontV.y,
                radius,
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

                0.0000001f,
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
                    10.0f,
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
                10.0f,
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
//    @Override
//    public void drawSelf(TexDrawer painter){
//        super.drawSelf(painter);
//
//        rectBody.rotateAngleGameElements = -(float)Math.toDegrees(rectBody.body.getAngle());
//        //rectBody.rotateAngleGameElements = 0;
//        //        Log.d("rectBody","rotateAngle"+rectBody.rotateAngleGameElements%360);
//        rectBody.drawSelf(painter);
//    }
    @Override
    public void drawSelf(TexDrawer painter){
//        if(isPureColor){
//            super.drawSelf(painter);
//            return;
//        }
        if(body!=null)
            rotateAngleGameElements =(float) Math.toDegrees(body.getAngle());
        super.drawSelf(painter);
        //offSet
//        if (TopOffset != 0) {
//            painter.drawColorFactorTex(
//                    TexManager.getTex(Img),
//                    colorFloats==null?ColorManager.getColor(color):colorFloats,
//                    x,
//                    y - jumpHeight - defaultHeight + TopOffset,
//                    width+scaleWidth,
//                    height+scaleHeight,
//                    rotateAngleGameElements,
//                    TopOffsetColorFactor
//            );
//        }
//        //drawSelf
//        painter.drawTex(
//                TexManager.getTex(TopImg==null?Img:TopImg),
//                x,
//                y - jumpHeight - defaultHeight,
//                (TopWidth+scaleWidth)*((TopRatio==0)?1:TopRatio),
//                (TopHeight+scaleHeight)*((TopRatio==0)?1:TopRatio),
//                rotateAngleGameElements
//        );
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
