package com.work.terry.snakeonastring_jbox2d.UI;

import com.work.terry.snakeonastring_jbox2d.Util.ColorManager;
import com.work.terry.snakeonastring_jbox2d.Util.Constant;
import com.work.terry.snakeonastring_jbox2d.Util.TexDrawer;
import com.work.terry.snakeonastring_jbox2d.Util.TexManager;

/**
 * Created by Terry on 2018/2/27.
 */

public class RoundEdgeRect extends GameElement {
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
                Constant.RoundRectSector
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
    public void setColor(int x){
        super.color = x;
        circleButton.setColor(x);
        rectButton1.setColor(x);
        rectButton2.setColor(x);
    }
    @Override
    public void setFloorShadowFactorX(float x){
        super.setFloorShadowFactorX(x);
        circleButton.setFloorShadowFactorX(x);
        rectButton1.setFloorShadowFactorX(x);
        rectButton2.setFloorShadowFactorX(x);
    }
    @Override
    public void setFloorShadowFactorY(float y){
        super.setFloorShadowFactorY(y);
        circleButton.setFloorShadowFactorY(y);
        rectButton1.setFloorShadowFactorY(y);
        rectButton2.setFloorShadowFactorY(y);
    }
    @Override
    public void drawSelf(TexDrawer painter){
        float ratio = this.scaleWidth/this.width;
        float width = this.width+this.scaleWidth;
        float height = this.height+this.scaleHeight;
        float angleRadius = this.angleRadius*(1+ratio);
        circleButton.scaleWidth = this.angleRadius*ratio*2;
        circleButton.scaleHeight = this.angleRadius*ratio*2;
        rectButton1.scaleWidth =  this.scaleWidth;
        rectButton1.scaleHeight = (this.height-this.angleRadius*2)*ratio;
        rectButton2.scaleWidth =  (this.width-this.angleRadius*2)*ratio;
        rectButton2.scaleHeight =  this.scaleHeight;

        int startAngle = 0;

        circleButton.rotateAngleGameElements = startAngle;
        circleButton.setXY(x-(width/2-angleRadius),y-(height/2-angleRadius));
        circleButton.drawSelf(painter);
        circleButton.rotateAngleGameElements = startAngle+270;
        circleButton.setXY(x+(width/2-angleRadius),y-(height/2-angleRadius));
        circleButton.drawSelf(painter);
        circleButton.rotateAngleGameElements = startAngle+90;
        circleButton.setXY(x-(width/2-angleRadius),y+(height/2-angleRadius));
        circleButton.drawSelf(painter);
        circleButton.rotateAngleGameElements = startAngle+180;
        circleButton.setXY(x+(width/2-angleRadius),y+(height/2-angleRadius));
        circleButton.drawSelf(painter);
        rectButton1.setXY(x,y);
        rectButton1.drawSelf(painter);
        rectButton2.setXY(x,y);
        rectButton2.drawSelf(painter);
    }
    @Override
    public void drawHeight(TexDrawer painter){
        float ratio = this.scaleWidth/this.width;
        float width = this.width+this.scaleWidth;
        float height = this.height+this.scaleHeight;
        float angleRadius = this.angleRadius*(1+ratio);
        circleButton.scaleWidth = this.angleRadius*ratio*2;
        circleButton.scaleHeight = this.angleRadius*ratio*2;
        rectButton1.scaleWidth =  this.scaleWidth;
        rectButton1.scaleHeight = (this.height-this.angleRadius*2)*ratio;
        rectButton2.scaleWidth =  (this.width-this.angleRadius*2)*ratio;
        rectButton2.scaleHeight =  this.scaleHeight;

        int startAngle = 0;

        circleButton.rotateAngleGameElements = startAngle;
        circleButton.setXY(x-(width/2-angleRadius),y-(height/2-angleRadius));
        circleButton.drawHeight(painter);
        circleButton.rotateAngleGameElements = startAngle+270;
        circleButton.setXY(x+(width/2-angleRadius),y-(height/2-angleRadius));
        circleButton.drawHeight(painter);
        circleButton.rotateAngleGameElements = startAngle+90;
        circleButton.setXY(x-(width/2-angleRadius),y+(height/2-angleRadius));
        circleButton.drawHeight(painter);
        circleButton.rotateAngleGameElements = startAngle+180;
        circleButton.setXY(x+(width/2-angleRadius),y+(height/2-angleRadius));
        circleButton.drawHeight(painter);


        rectButton1.setXY(x,y);
        rectButton1.drawHeight(painter);
        rectButton2.setXY(x,y);
        rectButton2.drawHeight(painter);
    }
    @Override
    public void drawFloorShadow(TexDrawer painter){
        float ratio = this.scaleWidth/this.width;
        float width = this.width+this.scaleWidth;
        float height = this.height+this.scaleHeight;
        float angleRadius = this.angleRadius*(1+ratio);
        circleButton.scaleWidth = this.angleRadius*ratio*2;
        circleButton.scaleHeight = this.angleRadius*ratio*2;
        rectButton1.scaleWidth =  this.scaleWidth;
        rectButton1.scaleHeight = (this.height-this.angleRadius*2)*ratio;
        rectButton2.scaleWidth =  (this.width-this.angleRadius*2)*ratio;
        rectButton2.scaleHeight =  this.scaleHeight;

        int startAngle = 0;

        circleButton.rotateAngleGameElements = startAngle;
        circleButton.setXY(x-(width/2-angleRadius),y-(height/2-angleRadius));
        circleButton.drawFloorShadow(painter);
        circleButton.rotateAngleGameElements = startAngle+270;
        circleButton.setXY(x+(width/2-angleRadius),y-(height/2-angleRadius));
        circleButton.drawFloorShadow(painter);
        circleButton.rotateAngleGameElements = startAngle+90;
        circleButton.setXY(x-(width/2-angleRadius),y+(height/2-angleRadius));
        circleButton.drawFloorShadow(painter);
        circleButton.rotateAngleGameElements = startAngle+180;
        circleButton.setXY(x+(width/2-angleRadius),y+(height/2-angleRadius));
        circleButton.drawFloorShadow(painter);
        rectButton1.setXY(x,y);
        rectButton1.drawFloorShadow(painter);
        rectButton2.setXY(x,y);
        rectButton2.drawFloorShadow(painter);
    }
}
