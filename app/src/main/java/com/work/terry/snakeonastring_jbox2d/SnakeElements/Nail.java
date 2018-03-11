package com.work.terry.snakeonastring_jbox2d.SnakeElements;

import com.work.terry.snakeonastring_jbox2d.UI.GameElement;
import com.work.terry.snakeonastring_jbox2d.Util.TexDrawer;
import com.work.terry.snakeonastring_jbox2d.Util.TexManager;
import com.work.terry.snakeonastring_jbox2d.Util.VectorUtil;
import static com.work.terry.snakeonastring_jbox2d.Util.Constant.NailShadowImg;
import static com.work.terry.snakeonastring_jbox2d.Util.Constant.NailVerticalImg;
import static com.work.terry.snakeonastring_jbox2d.Util.Constant.SnakeFloorColorFactor;
import static com.work.terry.snakeonastring_jbox2d.Util.Constant.SnakeHeightColorFactor;

/**
 * Created by Terry on 2018/1/31.
 */

public class Nail extends GameElement {
    public float TargetHeadVX = 0;
    public float TargetHeadVY = 1;

    public boolean reached = false;

    public Nail(float x, float y, float vx, float vy, int color) {
        super(
                "Nail",
                x,y,
                40, 40,
                color,
                20,
                0,
                0,
                SnakeHeightColorFactor,
                SnakeFloorColorFactor,
                NailShadowImg);
        setDoDrawHeight(false);
        setTarget(x,y,vx,vy);
    }
    public void setReached(boolean x){
        reached = x;
    }
    public boolean getReached(){
        return reached;
    }
    @Override
    public void drawSelf(TexDrawer painter){
        painter.drawTex(
                TexManager.getTex(NailVerticalImg),
                x-10,
                y-jumpHeight-defaultHeight-5,
                width,
                height,
               0
        );
    }
    public void setTarget(float touchX,float touchY,float HeadX,float HeadY){
        x = touchX;
        y = touchY;
        float[] normalTXY = VectorUtil.normalize2D(touchX - HeadX,touchY - HeadY);
        TargetHeadVX = normalTXY[0];
        TargetHeadVY = normalTXY[1];
        //Log.d("Target","target x="+TargetHeadX+" y="+TargetHeadY+"\n vx="+TargetHeadVX+" vy="+TargetHeadVY);
    }

}
