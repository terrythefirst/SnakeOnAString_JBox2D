package com.work.terry.snakeonastring_jbox2d.UI;

import com.work.terry.snakeonastring_jbox2d.Util.ColorManager;
import com.work.terry.snakeonastring_jbox2d.Util.Constant;
import com.work.terry.snakeonastring_jbox2d.Util.TexDrawer;

/**
 * Created by Terry on 2018/2/1.
 */

public class ScoreBoard extends GameElement {
    public Score score;
    public ScoreBoard(
            Score score,
            float x, float y,
            float width, float height,
            int color,
            float defaultHeight,
            float topOffset,
            float topOffsetColorFactor,
            float heightColorFactor,
            float floorShadowColorFactor
    ) {
        super(
                "ScoreBoard",
                x, y,
                width, height,
                color,
                defaultHeight,
                topOffset,
                topOffsetColorFactor,
                heightColorFactor,
                floorShadowColorFactor,
                ""
        );
        this.score = score;
        setDoDrawHeight(false);
    }

    @Override
    public void drawSelf(TexDrawer painter){
        if(width-scaleWidth<=0||height-scaleHeight<=0)return;
        if(TopOffset!=0)
            drawNumberColorFactor(
                    painter,
                    score.getScore(),
                    x,y-jumpHeight-defaultHeight+TopOffset,
                    width+scaleWidth,height+scaleHeight,
                    colorFloats,
                    TopOffsetColorFactor
            );
        drawNumber(
                painter,
                score.getScore(),
                x,y-jumpHeight-defaultHeight,
                width+scaleWidth,height+scaleHeight,
                colorFloats
        );
    }
    @Override
    public void drawFloorShadow(TexDrawer painter){
        if(width-scaleWidth<=0||height-scaleHeight<=0)return;
        if(doDrawFloorShadow)
        drawNumberShadow(
                painter,
                score.getScore(),
                x,y,
                width+scaleWidth,height+scaleHeight,
                ColorManager.getColor(Constant.COLOR_GREY),
                FloorShadowColorFactor
        );
    }
}
