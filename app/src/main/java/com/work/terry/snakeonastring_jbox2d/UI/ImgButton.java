package com.work.terry.snakeonastring_jbox2d.UI;

import com.work.terry.snakeonastring_jbox2d.Util.ColorManager;
import com.work.terry.snakeonastring_jbox2d.Util.Constant;
import com.work.terry.snakeonastring_jbox2d.Util.ImgManager;
import com.work.terry.snakeonastring_jbox2d.Util.TexDrawer;
import com.work.terry.snakeonastring_jbox2d.Util.TexManager;

/**
 * Created by Terry on 2018/2/19.
 */

public class ImgButton extends Button{
    String TopImgImgButton;
    float topImgRotateDegrees = 0;
    float TopImgRatio = 0;
    public ImgButton(
            int id,
            float x,float y,
            float width,float height,

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
                Img);
        this.TopImgImgButton = TopImg;
        //buttonDefaultHeight = defaultHeight;
        //jumpHeight = defaultHeight;
        //this.defaultHeight = 0;
    }
    public void setTopImgImgButton(String x){
        TopImgImgButton = x;
    }
    public void setTopImgRotateDegrees(float x){this.topImgRotateDegrees = x;}
    public void setTopImgRatio(float rate){
        this.TopImgRatio = rate;
    }
    public void drawSelf(TexDrawer painter){
        if(disabled)setColor(Constant.COLOR_GREY);
        super.drawSelf(painter);

        if(TopImgImgButton!=null)
            painter.drawColorOpacityFactorTex(
                    TexManager.getTex(TopImgImgButton),
                    ColorManager.getColor(Constant.COLOR_WHITE),//(disabled?ColorManager.getColor(Constant.COLOR_GREY):ColorManager.getColor(Constant.COLOR_WHITE)),
                    x,
                    y - jumpHeight - defaultHeight,
                    (TopWidth+scaleWidth)*((TopRatio==0)?1:TopRatio)*((TopImgRatio==0)?1:TopImgRatio),
                    (TopHeight+scaleHeight)*((TopRatio==0)?1:TopRatio)*((TopImgRatio==0)?1:TopImgRatio),
                    topImgRotateDegrees,
                    1,
                    opacityFactor
            );
    }
}
