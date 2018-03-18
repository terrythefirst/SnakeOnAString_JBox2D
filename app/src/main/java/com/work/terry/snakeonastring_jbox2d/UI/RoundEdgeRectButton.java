package com.work.terry.snakeonastring_jbox2d.UI;

import android.util.Log;

import com.work.terry.snakeonastring_jbox2d.Util.ColorManager;
import com.work.terry.snakeonastring_jbox2d.Util.Constant;
import com.work.terry.snakeonastring_jbox2d.Util.TexDrawer;
import com.work.terry.snakeonastring_jbox2d.Util.TexManager;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Terry on 2018/2/27.
 */

public class RoundEdgeRectButton extends ImgButton {
    public Button rectButton1;
    public Button rectButton2;
    public Button circleButton;
    public float angleRadius;
    public List<GameElement> topGameElements = new ArrayList<>();
    public List<Button> buttons = new ArrayList<Button>();

    public RoundEdgeRectButton(
            int id,
            float x,float y,
            float width,float height,
            float angleRadius,

            int color,
            float defaultHeight,
            float topOffset,
            float topOffsetColorFactor,
            float heightColorFactor,
            float floorShadowColorFactor,

            String TopImg,
            String Img){
        super(
                id,
                x,y,
                width, height,
                color,
                defaultHeight,
                topOffset,
                topOffsetColorFactor,
                heightColorFactor,
                floorShadowColorFactor,
                TopImg,
                Img);
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
        buttons.add(circleButton);
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
        buttons.add(rectButton1);
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
        buttons.add(rectButton2);
    }
    public void addToTopGameElements(GameElement gameElement){
         topGameElements.add(gameElement);
    }
    @Override
    public void setColor(int x){
        super.color = x;
         for (Button button:buttons){
             button.setColor(x);
         }
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

        if(disabled)setColor(Constant.COLOR_GREY);

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

        if(TopImg!=null)
            painter.drawColorSelf(
                    TexManager.getTex(TopImg),
                    ColorManager.getColor(Constant.COLOR_WHITE),//(disabled?ColorManager.getColor(Constant.COLOR_GREY):ColorManager.getColor(Constant.COLOR_WHITE)),
                    x,
                    y - jumpHeight - defaultHeight,
                    (TopWidth+scaleWidth)*((TopRatio==0)?1:TopRatio)*((TopImgRatio==0)?1:TopImgRatio),
                    (TopHeight+scaleHeight)*((TopRatio==0)?1:TopRatio)*((TopImgRatio==0)?1:TopImgRatio),
                    rotateAngleGameElements
            );
        else {
            for(GameElement gameElement : topGameElements){
                gameElement.setXY(
                        x+ gameElement.constantXY.x,
                        y-height/2+ gameElement.constantXY.y
                );
                gameElement.drawFloorShadow(painter);
                gameElement.drawHeight(painter);
                gameElement.drawSelf(painter);
                //Log.d("Menu GameElementsList","x="+gameElement.x+" y="+gameElement.y);
            }
        }
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
    @Override
    public void whenPressed(){
        if(!disabled){
            jumpHeight = -defaultHeight*3/4;
            for (Button button:buttons){
                button.jumpHeight = -button.defaultHeight*3/4;
            }
            for(GameElement gameElement : topGameElements){
                gameElement.jumpHeight = -defaultHeight*3/4;
            }
        }else {
            jumpHeight = -defaultHeight*1/4;
            for (Button button:buttons){
                button.jumpHeight = -button.defaultHeight*1/4;
            }
            for(GameElement gameElement : topGameElements){
                gameElement.jumpHeight = -defaultHeight*1/4;
            }
        }
    }
    @Override
    public void whenReleased(boolean within){
        jumpHeight = 0;
        for (Button button:buttons){
            button.jumpHeight = 0;
        }
        for(GameElement gameElement : topGameElements){
            gameElement.jumpHeight = 0;
        }
        if(!disabled&&within&&buttonListener!=null)buttonListener.doButtonStuff();

    }
}
