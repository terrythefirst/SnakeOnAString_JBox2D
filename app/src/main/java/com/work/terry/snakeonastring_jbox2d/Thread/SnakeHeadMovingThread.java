package com.work.terry.snakeonastring_jbox2d.Thread;

import android.util.Log;

import com.work.terry.snakeonastring_jbox2d.JBox2DElements.CircleBody;
import com.work.terry.snakeonastring_jbox2d.SnakeElements.SnakeHead;
import com.work.terry.snakeonastring_jbox2d.Util.Constant;
import com.work.terry.snakeonastring_jbox2d.Util.MyMath;

import org.jbox2d.common.Vec2;

import static com.work.terry.snakeonastring_jbox2d.Util.VectorUtil.Mul2D;
import static com.work.terry.snakeonastring_jbox2d.Util.VectorUtil.calDistance;
import static com.work.terry.snakeonastring_jbox2d.Util.VectorUtil.normalize2D;
import static com.work.terry.snakeonastring_jbox2d.Util.VectorUtil.plusV2D;

/**
 * Created by Terry on 2018/1/22.
 */

public class SnakeHeadMovingThread extends Thread {
    private SnakeHead snakeHead;
    private Vec2 bodyV;
    double sinRotateAngleStep = Math.sin(Math.toRadians(Constant.SnakeHeadRotateStepAngle));
    double cosRotateAngleStep = Math.cos(Math.toRadians(Constant.SnakeHeadRotateStepAngle));

    public SnakeHeadMovingThread (SnakeHead snakeHead) {
        this.snakeHead = snakeHead;
    }
    public void run () {
        while (!snakeHead.snake.isPaused()&&!snakeHead.snake.isDead()) {
            bodyV = snakeHead.body.getLinearVelocity();//getBodyVelocityNormalized();
            if(snakeHead.snake.checkEntered()&&snakeHead.target.getReached()){
                snakeHead.setBodyVelocity(
                        snakeHead.HeadVX* 2,
                        snakeHead.HeadVY* 2
                );
            }else {
                float dx = snakeHead.target.x - snakeHead.x;
                float dy = snakeHead.target.y - snakeHead.y;
                //Log.e("calDistance(dx,dy)<10f",calDistance(dx,dy)+"");
                if(calDistance(dx,dy)<10f){
                    snakeHead.target.setReached(true);
                    snakeHead.target.setDoDraw(false);
                }else {
                    snakeHead.target.setReached(false);
                }

                Vec2 vecDXY = new Vec2(dx, dy);
                //vecDXY = normalize2D(vecDXY);
                vecDXY = plusV2D(vecDXY,bodyV);
                vecDXY = normalize2D(vecDXY);

                float dvx = snakeHead.target.TargetHeadVX - snakeHead.HeadVX;
                float dvy = snakeHead.target.TargetHeadVY - snakeHead.HeadVY;

                Vec2 normalDvXY = new Vec2(dvx, dvy);
                normalDvXY = normalize2D(normalDvXY);
                Vec2 nornalvPresentXY = normalize2D(new Vec2(snakeHead.HeadVX, snakeHead.HeadVY));
                snakeHead.HeadVX = nornalvPresentXY.x;
                snakeHead.HeadVY = nornalvPresentXY.y;

                snakeHead.HeadVX += normalDvXY.x*sinRotateAngleStep;
                snakeHead.HeadVY += normalDvXY.y*sinRotateAngleStep;
                //            snakeHead.body.applyLinearImpulse(
//                    new Vec2(
//                            snakeHead.HeadVX* forceFactor(dx),
//                            snakeHead.HeadVY* forceFactor(dy) ),
//                    snakeHead.body.getPosition(),
//                    true
//            );
//            snakeHead.body.applyForceToCenter(
//                    new Vec2(
//                            vecDXY .x* 1000,
//                            vecDXY .y* 1000 )
//            );
            snakeHead.body.applyForceToCenter(
                    new Vec2(
                    vecDXY .x* forceFactor(dx),
                    vecDXY .y* forceFactor(dy)
                    ));
            //Log.e("snakeMoving","force x="+vecDXY .x* forceFactor(dx)+" y="+vecDXY .y* forceFactor(dy));
//                snakeHead.setBodyVelocity(
//                        vecDXY .x* speedFactor(dx),
//                        vecDXY .y* speedFactor(dy)
//                );
            }

            try {
                sleep(1);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
    private float speedFactor(float x){
        return MyMath.smoothStep(
                5,
                snakeHead.SpeedFactor,
                Math.abs(x)
        )
                *snakeHead.speed
                * snakeHead.snake.getLength()
                *10;
    }
    public float forceFactor(float x){
        float totalMass = 0;
        for(CircleBody cb:snakeHead.snake.snakeBodies){
            if(cb!=null&&cb.body!=null)
                totalMass+=cb.body.getMass();
        }
        Log.e("totalMass",""+totalMass);
        float ratio =  MyMath.smoothStep(
                1,
                10,
                Math.abs(x)
        );
        return totalMass*ratio*2;

    }
}
