package com.work.terry.snakeonastring_jbox2d.SnakeElements;

import com.work.terry.snakeonastring_jbox2d.UI.GameElement;
import com.work.terry.snakeonastring_jbox2d.Util.Constant;
import com.work.terry.snakeonastring_jbox2d.Util.VectorUtil;

import org.jbox2d.common.Vec2;

/**
 * Created by Terry on 2018/1/27.
 */

public class SnakeNodeAnimateDraw extends GameElement {
    public static int DrawId = 0;
    public float targetRadius;
    public float nowRadius = 0;
    public SnakeNode snakeNode;
    public SnakeNodeAnimateDraw(
            SnakeNode snakeNode,
            int code
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
        this.setColorFloats(snakeNode.colorFloats);
        this.snakeNode = snakeNode;
        if(code == Constant.SNAKE_ANIMATION_REMOVE){
            this.nowRadius = snakeNode.radius;
            this.targetRadius = 0;
        }else if(code ==Constant.SNAKE_ANIMATION_APPEND){
            this.nowRadius = 0;
            this.targetRadius = snakeNode.radius;
        }

    }
    public boolean isFinished(){
        return (nowRadius==targetRadius);
    }
    public void AnimationStep(float perRadius){
        if(snakeNode==null||snakeNode.body==null)return;
        nowRadius += perRadius;

        float ratio = nowRadius/snakeNode.radius;
        float distance = nowRadius+snakeNode.front.radius;
        TopOffset = ratio*snakeNode.TopOffset;
        defaultHeight = ratio*snakeNode.defaultHeight;

        width = nowRadius*2;
        height = nowRadius*2;
        Vec2 snakeNodeXY = snakeNode.getBodyXY();
        Vec2 frontXY = snakeNode.front.getBodyXY();

        Vec2 targetXYV = VectorUtil.minusV2D(frontXY,snakeNodeXY);
        targetXYV.normalize();


        x =frontXY.x- targetXYV.x*distance;
        y =frontXY.y- targetXYV.y*distance;
    }
}
