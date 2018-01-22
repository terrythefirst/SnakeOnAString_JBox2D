package com.work.terry.snakeonastring_jbox2d;

import android.util.Log;

import com.work.terry.snakeonastring_jbox2d.JBox2DElements.CircleBody;
import com.work.terry.snakeonastring_jbox2d.JBox2DElements.MyBody;
import com.work.terry.snakeonastring_jbox2d.JBox2DElements.MyBox2DRevoluteJoint;
import com.work.terry.snakeonastring_jbox2d.JBox2DElements.MyDistanceJoint;
import com.work.terry.snakeonastring_jbox2d.JBox2DElements.MyWeldJoint;
import com.work.terry.snakeonastring_jbox2d.JBox2DElements.RectBody;
import com.work.terry.snakeonastring_jbox2d.Util.Constant;
import com.work.terry.snakeonastring_jbox2d.Util.TexDrawer;
import com.work.terry.snakeonastring_jbox2d.Util.TexManager;

import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.World;

import static com.work.terry.snakeonastring_jbox2d.Util.VectorUtil.*;
import static com.work.terry.snakeonastring_jbox2d.Util.Constant.*;

/**
 * Created by Terry on 2017/12/28.
 */

public class SnakeNode extends CircleBody{
    Snake snake;
    private Front front = null;
    public RectBody rectBody = null;
//    private MovingThread movingThread = null;

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
        this(
                snake,
                world,
                0,0,
                frontNode.body.getLinearVelocity().x,
                frontNode.body.getLinearVelocity().y,
                id
        );
        if(frontNode instanceof SnakeHead){
            this.front = new Front((SnakeHead) frontNode);
        }else{
            this.front = new Front((SnakeNode) frontNode);
        }

        initSelf(world,frontNode);
        //startMoving();
    }
    //    public SnakeBody(World world,SnakeBody frontNode){
//        this(
//                world,
//                0,0,
//                frontNode.body.getLinearVelocity().x,
//                frontNode.body.getLinearVelocity().y
//        );
//        this.front = new Front(frontNode);
//        initSelf();
//    }
//    public void initSelf2(World world, MyBody frontBody){
//        Vec2 frontV = this.front.getV2D();
//        frontV = normalize2D(frontV);
//        Vec2 frontXY = front.getXY();
//        Log.d("FrontV","FrontVx = "+frontV.x+" Front = "+frontV.y);
//        x = frontXY.x-frontV.x*front.getCenterDistance();
//        y = frontXY.y-frontV.y*front.getCenterDistance();
//        new MyDistanceJoint(
//                "snake and body joint",
//                world,
//                true,
//                this,
//                frontBody,
//                this.body.getPosition(),
//                frontBody.body.getPosition(),
//                0.0f,
//                0.1f
//        );
//        //pushXYintoBody();
//        movingThread = new MovingThread();
//        movingThread.start();
//    }
    public void initSelf(World world,CircleBody frontBody){
        Vec2 frontV = normalize2D(front.getV2D());
        float distance = front.getCenterDistance();
        Vec2 frontXY = front.getXY();
        x = frontXY.x - frontV.x*distance;
        y = frontXY.y - frontV.y*distance;
        pushXYintoBody();

        Vec2 center = getCenterV2(front.getXY(),this.getBodyXY());
        float angle = calBodyRadians(frontV.x,frontV.y);
        rectBody = new RectBody(
                world,
                ""+body.getUserData().toString()+"Rect",
                center.x,center.y,
                angle,
                0,0,
                4,distance/2,
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
//        }

//        new MyBox2DRevoluteJoint(
//                ""+body.getUserData().toString()+"RevoltJoint1",
//                world,
//                true,
//                rectBody,
//                frontBody,
//                frontBody.body.getPosition(),
//                true,
//                (float) ((0/360)*Math.PI),
//                (float) ((60/360)*Math.PI),
//                false, 0f, 0f
//        );
//        new MyBox2DRevoluteJoint(
//                ""+body.getUserData().toString()+"RevoltJoint2",
//                world,
//                true,
//                rectBody,
//                this,
//                this.getBodyXY(),
//                true,
//                (float) ((0/360)*Math.PI),
//                (float) ((60/360)*Math.PI),
//                false, 0f, 0f
//        );
        new MyWeldJoint(
                "snake and body joint"+"RevoltJoint1",
                world,
                true,
                rectBody,
                frontBody,
                //rectBody.body.getPosition(),
                frontBody.body.getPosition(),
                0.0f,
                1.0f
        );
        new MyWeldJoint(
                "snake and body joint"+"RevoltJoint2",
                world,
                true,
                rectBody,
                this,
                this.body.getPosition(),
                0.2f,
                0.99f
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
//    public void startMoving(){
//        movingThread = new MovingThread();
//        movingThread.start();
//    }
//
//    private class MovingThread extends Thread{
//        Vec2 bodyV;
//        @Override
//        public void run(){
//            while(!snake.isDead()){
//                if(body==null)continue;
//                //popXYfromBody();
//                changeBodyXYByFront();
////                Vec2 vSkew = new Vec2(
////                        -targetMoveV.x*front.getCenterDistance(),
////                        -targetMoveV.y*front.getCenterDistance()).skew();
//                try {
//                    sleep(5);
//                }catch (Exception e){
//                    e.printStackTrace();
//                }
//            }
//            setBodyVelocity(
//                    front.getV2D().x,front.getV2D().y
//            );
//
//        }
//        public void changeBodyXYByFront(){
//            bodyV = getBodyVelocityNormalized();
//
//            Vec2 frontXY = front.getXY();
//            float dx = frontXY.x - x;
//            float dy = frontXY.y - y;
//            Vec2 frontV = front.getV2D();
//            frontV = normalize2D(frontV);
//            Vec2 targetMoveV = plusV2D(new Vec2(dx,dy),frontV);
//            targetMoveV = normalize2D(targetMoveV);
//            targetMoveV = plusV2D(bodyV,targetMoveV);
//            targetMoveV = normalize2D(targetMoveV);
//            x = frontXY.x-targetMoveV.x*front.getCenterDistance();
//            y = frontXY.y-targetMoveV.y*front.getCenterDistance();
//
//            pushXYintoBody();
//            setBodyVelocity(
//                    0,0
//            );
//        }
//    }

    private class Front{
        SnakeHead head= null;
        SnakeNode frontBody = null;
        public float width = 0;
        public float height = 0;
        private float CenterDistanceX = 0;
        private float CenterDistanceY = 0;
        private float CenterDistance = 0;

        public Front(SnakeNode front){
            this.frontBody = front;
            width = bodyRadius*2;
            height = width;
            calCenterDistance();
        }
        public Front(SnakeHead front){
            this.head = front;
            width = headRadius*2;
            height = width;
            calCenterDistance();
        }
        public Vec2 getV2D(){
            if(head!=null)return head.getV2D();
            else {
                Vec2 frontXY = frontBody.front.getXY();
                Vec2 xy = this.getXY();
                return new Vec2(frontXY.x-xy.x,frontXY.y-xy.y);
            }
        }
        public Vec2 getBodyV2D(){
            if(head!=null)return head.getBodyVelocityNormalized();
            else {
                return frontBody.getBodyVelocityNormalized();
            }
        }
        public Vec2 getXY(){
//            if(head!=null) return head.getBodyXY();
//            else return frontBody.getBodyXY();
            if(head!=null)
                return new Vec2(head.x,head.y);
            else
                return new Vec2(frontBody.x,frontBody.y);
        }
        public float getWidth(){
            return width;
        }
        public float getHeight(){
            return height;
        }

        private void calCenterDistance(){
            CenterDistanceX = (getWidth()+ Constant.bodyRadius*2)/2;
            CenterDistanceY = (getHeight()+Constant.bodyRadius*2)/2;
            CenterDistance = calDistance(CenterDistanceX,CenterDistanceY);
//            if(head!=null)CenterDistance-=Constant.DistanceOffset*7/6;
//            else CenterDistance-=Constant.DistanceOffset;
        }
        public float getCenterDistance(){
            return CenterDistance;
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
    }
}
