package com.work.terry.snakeonastring_jbox2d.UI;

import android.graphics.Color;

import com.work.terry.snakeonastring_jbox2d.UI.GameElements;
import com.work.terry.snakeonastring_jbox2d.Util.Constant;
import com.work.terry.snakeonastring_jbox2d.Util.TexDrawer;
import static com.work.terry.snakeonastring_jbox2d.Util.Constant.*;

/**
 * Created by Terry on 2018/2/1.
 */

public class ScoreBoard extends GameElements {
    private byte[] scoreLock = new byte[0];
    public int Score = 0;

    public float scoreWidth;
    public ScoreBoard(
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
        setDoDrawHeight(false);
        this.scoreWidth = width;
    }
    public void plusScore(int x){
        synchronized (scoreLock){
            this.Score+=x;
        }
    }
    public int getScore(){
        return Score;
    }
    @Override
    public void drawSelf(TexDrawer painter){
        drawNumberColorFactor(
                painter,
                Score,
                x,y-jumpHeight-defaultHeight+TopOffset,
                width,height,
                color,
                TopOffsetColorFactor
        );
        drawNumber(
                painter,
                Score,
                x,y-jumpHeight-defaultHeight,
                width,height,
                color
        );
    }
    @Override
    public void drawFloorShadow(TexDrawer painter){
        drawNumberShadow(
                painter,
                Score,
                x,y,
                width,height,
                Constant.COLOR_GREY,
                FloorShadowColorFactor
        );
    }
}
