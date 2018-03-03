package com.work.terry.snakeonastring_jbox2d.UI;

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
                Constant.SnakeBodyImg
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
    @Override
    public void setColor(int x){
        super.color = x;
         for (Button button:buttons){
             button.setColor(x);
         }
    }
    @Override
    public void drawSelf(TexDrawer painter){
        if(disabled)setColor(Constant.COLOR_GREY);

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

        painter.drawColorSelf(
                TexManager.getTex(TopImg),
                ColorManager.getColor(Constant.COLOR_WHITE),//(disabled?ColorManager.getColor(Constant.COLOR_GREY):ColorManager.getColor(Constant.COLOR_WHITE)),
                x,
                y - jumpHeight - defaultHeight,
                (TopWidth+scaleWidth)*((TopRatio==0)?1:TopRatio)*((TopImgRatio==0)?1:TopImgRatio),
                (TopHeight+scaleHeight)*((TopRatio==0)?1:TopRatio)*((TopImgRatio==0)?1:TopImgRatio),
                rotateAngleGameElements
        );

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
    @Override
    public void whenPressed(){
        if(!disabled){
            jumpHeight = -defaultHeight*3/4;
            for (Button button:buttons){
                button.jumpHeight = -button.defaultHeight*3/4;
            }
        }else {
            jumpHeight = -defaultHeight*1/4;
            for (Button button:buttons){
                button.jumpHeight = -button.defaultHeight*1/4;
            }
        }
    }
    @Override
    public void whenReleased(boolean within){
        jumpHeight = 0;
        for (Button button:buttons){
            button.jumpHeight = 0;
        }
        if(!disabled&&within&&buttonListener!=null)buttonListener.doButtonStuff();

    }
}
