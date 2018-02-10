package com.work.terry.snakeonastring_jbox2d.Thread;

import com.work.terry.snakeonastring_jbox2d.SnakeElements.SnakeNode;
import com.work.terry.snakeonastring_jbox2d.Util.MyMath;

import org.jbox2d.common.Vec2;

import static com.work.terry.snakeonastring_jbox2d.Util.VectorUtil.Mul2D;
import static com.work.terry.snakeonastring_jbox2d.Util.VectorUtil.minusV2D;
import static com.work.terry.snakeonastring_jbox2d.Util.VectorUtil.normalize2D;

/**
 * Created by Terry on 2018/1/22.
 */

public class SnakeNodeMovingThread extends Thread{
    SnakeNode snakeNode;
    public SnakeNodeMovingThread(SnakeNode snakeNode){
        this.snakeNode = snakeNode;
    }
    @Override
    public void run(){
        while(!snakeNode.snake.isPaused()&&!snakeNode.snake.isDead()){
            if(snakeNode.body==null)continue;
            snakeNode.popXYfromBody();
            changeBodyImpulseByFront();
            try {
                sleep(5);
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }
    public void changeBodyImpulseByFront(){
        snakeNode.frontV = snakeNode.getFrontV2D();

        Vec2 frontXY = snakeNode.front.getBodyXY();
        float dx = frontXY.x - snakeNode.x;
        float dy = frontXY.y - snakeNode.y;
        Vec2 dxyV = new Vec2(dx,dy);
        Vec2 frontVNormalized = normalize2D(snakeNode.frontV);
        Vec2 targetMoveV = minusV2D(
                dxyV,
                Mul2D(
                        frontVNormalized,
                        snakeNode.getBodyCenterDistance()
                )
        );

//        snakeNode.body.applyForceToCenter(
//                Mul2D(dxyV,10f)
//        );
//        snakeNode.body.applyForceToCenter(
//                    Mul2D(targetMoveV,6f)
//            );

        snakeNode.body.applyLinearImpulse(
                Mul2D(targetMoveV, MyMath.smoothStep(0,6,snakeNode.getId())*1f),//0.006f*snakeNode.getId()),
                snakeNode.body.getPosition(),
                true
        );
//        snakeNode.setBodyVelocity(
//                targetMoveV.x/10,
//                targetMoveV.y/10
//        );
    }
}