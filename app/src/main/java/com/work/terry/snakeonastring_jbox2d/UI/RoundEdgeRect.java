package com.work.terry.snakeonastring_jbox2d.UI;

import android.util.Log;

import com.work.terry.snakeonastring_jbox2d.Util.Constant;
import com.work.terry.snakeonastring_jbox2d.Util.TexDrawer;

/**
 * Created by Terry on 2018/2/27.
 */

public class RoundEdgeRect extends GameElements{
    public Button rectButton1;
    public Button rectButton2;
    public Button circleButton;
    public float angleRadius;
    public RoundEdgeRect( String id,
                          float x, float y,
                          float width, float height,
                          float angleRadius,
                          int color,
                          float defaultHeight,
                          float topOffset,
                          float topOffsetColorFactor,
                          float heightColorFactor,
                          float floorShadowColorFactor,
                          String Img
    ) {
        super(
                id,
                x, y,
                width, height,
                color,
                defaultHeight,
                topOffset,
                topOffsetColorFactor,
                heightColorFactor,
                floorShadowColorFactor,
                Img
        );
        this.angleRadius = angleRadius;
        circleButton = new Button(
                0,
                0,0,
                angleRadius*2,
                angleRadius*2,
                color,
                defaultHeight,
                topOffset,
                topOffsetColorFactor,
                heightColorFactor,
                floorShadowColorFactor,
                Constant.SnakeBodyImg
        );
        rectButton1 = new Button(
                0,
                x,y,
                width,
                height-angleRadius*2,
                color,
                defaultHeight,
                topOffset,
                topOffsetColorFactor,
                heightColorFactor,
                floorShadowColorFactor,
                Constant.SnakeBodyHeightImg
        );
        rectButton2 = new Button(
                0,
                x,y,
                width-angleRadius*2,
                height,
                color,
                defaultHeight,
                topOffset,
                topOffsetColorFactor,
                heightColorFactor,
                floorShadowColorFactor,
                Constant.SnakeBodyHeightImg
        );
    }
    @Override
    public void drawSelf(TexDrawer painter){
        circleButton.setXY(x-(width/2-angleRadius),y-(height/2-angleRadius));
        circleButton.drawSelf(painter);
        circleButton.setXY(x+(width/2-angleRadius),y-(height/2-angleRadius));
        circleButton.drawSelf(painter);
        circleButton.setXY(x-(width/2-angleRadius),y+(height/2-angleRadius));
        circleButton.drawSelf(painter);
        circleButton.setXY(x+(width/2-angleRadius),y+(height/2-angleRadius));
        circleButton.drawSelf(painter);
        rectButton1.setXY(x,y);
        rectButton1.drawSelf(painter);
        rectButton2.setXY(x,y);
        rectButton2.drawSelf(painter);
    }
    @Override
    public void drawHeight(TexDrawer painter){
        circleButton.setXY(x-(width/2-angleRadius),y-(height/2-angleRadius));
        circleButton.drawHeight(painter);
        circleButton.setXY(x+(width/2-angleRadius),y-(height/2-angleRadius));
        circleButton.drawHeight(painter);
        circleButton.setXY(x-(width/2-angleRadius),y+(height/2-angleRadius));
        circleButton.drawHeight(painter);
        circleButton.setXY(x+(width/2-angleRadius),y+(height/2-angleRadius));
        circleButton.drawHeight(painter);
        rectButton1.setXY(x,y);
        rectButton1.drawHeight(painter);
        rectButton2.setXY(x,y);
        rectButton2.drawHeight(painter);
    }
    @Override
    public void drawFloorShadow(TexDrawer painter){
        circleButton.setXY(x-(width/2-angleRadius),y-(height/2-angleRadius));
        circleButton.drawFloorShadow(painter);
        circleButton.setXY(x+(width/2-angleRadius),y-(height/2-angleRadius));
        circleButton.drawFloorShadow(painter);
        circleButton.setXY(x-(width/2-angleRadius),y+(height/2-angleRadius));
        circleButton.drawFloorShadow(painter);
        circleButton.setXY(x+(width/2-angleRadius),y+(height/2-angleRadius));
        circleButton.drawFloorShadow(painter);
        rectButton1.setXY(x,y);
        rectButton1.drawFloorShadow(painter);
        rectButton2.setXY(x,y);
        rectButton2.drawFloorShadow(painter);
    }
}
