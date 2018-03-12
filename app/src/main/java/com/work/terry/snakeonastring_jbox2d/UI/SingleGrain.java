package com.work.terry.snakeonastring_jbox2d.UI;

import com.work.terry.snakeonastring_jbox2d.Util.Constant;
import com.work.terry.snakeonastring_jbox2d.Util.MyMath;
import com.work.terry.snakeonastring_jbox2d.Util.TexDrawer;
import com.work.terry.snakeonastring_jbox2d.Util.VectorUtil;

import org.jbox2d.common.Vec2;

import static com.work.terry.snakeonastring_jbox2d.Util.Constant.RATE;

/**
 * Created by Terry on 2018/3/12.
 */

public class SingleGrain extends GameElement {
    private static int SingleGrainCount = 0 ;
    Vec2 centerXY;
    float centerRadius;
    float radius;
    float vx;
    float vy;
    float vz;

    public float timeSpan = 0;
    public float G;

    public SingleGrain(
            Vec2 centerXY,
            float radius,
            float centerRadius,
            float G,
            float vx,
            float vy,
            float vz,
            float[] colorFloats,
            float defaultHeight,
            float floorShadowColorFactor) {
        super(
                "SingleGrain "+SingleGrainCount++,
                centerXY.x, centerXY.y,
                radius*2, radius*2,
                colorFloats, defaultHeight,
                0,0,0,
                floorShadowColorFactor,
                Constant.SnakeBodyImg
        );
        this.G = G;
        this.centerXY = centerXY;
        this.centerRadius = centerRadius;
        this.radius = radius;
        this.vx = vx;
        this.vy = vy;
        this.vz = vz;
        setDoDrawHeight(false);
        setColorFloats(colorFloats);
    }
    public void stepMove(){
        if(!doDraw)return;
        if(vz==0)setDoDraw(false);

        float centerDistance = VectorUtil.calDistance(x-centerXY.x,y-centerXY.y);
        float ratio =MyMath.smoothStep(0,centerRadius,centerDistance);
        scaleWidth = -width*ratio*0.6f;
        scaleHeight = -height*ratio*0.6f;

        x += vx*timeSpan*RATE;
        y += vy*timeSpan*RATE;
        float height = (float) (vz*timeSpan-0.5*timeSpan*timeSpan*G)*RATE;
        if(height<=0)vz = -vy*0.76f;
        if(vz<0.03f)vz=0;
        jumpHeight = height/defaultHeight;
    }
    @Override
    public void drawSelf(TexDrawer painter){
        stepMove();
        super.drawSelf(painter);
    }
}
