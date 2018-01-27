package com.work.terry.snakeonastring_jbox2d.SnakeElements;

import com.work.terry.snakeonastring_jbox2d.GameElements;
import com.work.terry.snakeonastring_jbox2d.SurfaceViewAndActivity.GamePlay;
import com.work.terry.snakeonastring_jbox2d.Util.VectorUtil;

import org.jbox2d.common.Vec2;

/**
 * Created by Terry on 2018/1/27.
 */

public class SnakeNodeAnimateDraw extends GameElements{
    public static int DrawId = 0;
    public float targetRadius;
    public float nowRadius = 0;
    public SnakeNode snakeNode;
    public SnakeNodeAnimateDraw(
            SnakeNode snakeNode
    ){
        super(
                "SnakeNodeAnimateDraw "+DrawId++,
                snakeNode.x,snakeNode.y,
                0,0,
                snakeNode.color,
                snakeNode.defaultHeight,
                snakeNode.TopOffset,
                snakeNode.TopOffsetColorFactor,
                snakeNode.HeightColorFactor,
                snakeNode.FloorShadowColorFactor,
                snakeNode.Img);
        this.snakeNode = snakeNode;
        this.targetRadius = snakeNode.radius;
    }
    public boolean isFinished(){
        return (nowRadius>=targetRadius);
    }
    public void changeXY(){
        width = nowRadius*2;
        height = nowRadius*2;
        Vec2 snakeNodeXY = snakeNode.getBodyXY();
        Vec2 frontXY = snakeNode.front.getBodyXY();

        Vec2 targetXYV = VectorUtil.minusV2D(snakeNodeXY,frontXY);
        targetXYV.normalize();

        x = targetXYV.x*nowRadius;
        y = targetXYV.y*nowRadius;
    }
}
