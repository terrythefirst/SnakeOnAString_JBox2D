package com.work.terry.snakeonastring_jbox2d.Thread;

import android.util.Log;

import com.work.terry.snakeonastring_jbox2d.SnakeElements.SnakeFood;
import com.work.terry.snakeonastring_jbox2d.SnakeElements.SnakeHead;
import com.work.terry.snakeonastring_jbox2d.Util.VectorUtil;

import org.jbox2d.common.Vec2;

/**
 * Created by Terry on 2018/2/2.
 */

public class FoodMagnetMoveThread extends Thread {
    SnakeFood snakeFood;
    SnakeHead snakeHead;

    public FoodMagnetMoveThread(SnakeFood snakeFood, SnakeHead snakeHead){
        this.snakeFood = snakeFood;
        this.snakeHead = snakeHead;

        snakeFood.moving = true;
    }
    @Override
    public void run(){
        Log.d("FoodMagnetMoveThread","start");
        while (!snakeFood.eatean){
            Vec2 foodLocation = snakeFood.getBodyXY();
            Vec2 headLocation = snakeHead.getBodyXY();
            Vec2 moveV = VectorUtil.minusV2D(headLocation,foodLocation);
            moveV = VectorUtil.normalize2D(moveV);
            moveV = VectorUtil.Mul2D(moveV,0.50f);

            snakeFood.body.applyForceToCenter(
                    moveV
            );
//            snakeFood.setBodyVelocity(
//                    moveV.x,
//                    moveV.y
//            );
        }
    }
}
