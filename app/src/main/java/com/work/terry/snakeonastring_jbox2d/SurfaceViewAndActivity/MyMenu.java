package com.work.terry.snakeonastring_jbox2d.SurfaceViewAndActivity;

import android.content.SharedPreferences;
import android.view.MotionEvent;

import com.work.terry.snakeonastring_jbox2d.UI.GameElements;
import com.work.terry.snakeonastring_jbox2d.UI.ImgButton;
import com.work.terry.snakeonastring_jbox2d.Util.DrawUtil;
import com.work.terry.snakeonastring_jbox2d.Util.TexDrawer;

/**
 * Created by Terry on 2018/2/21.
 */

public class MyMenu extends GameElements {
    public ImgButton closeButton;
    public DrawUtil drawUtil;

    public GameElements circle1 ;

    public MyMenu
            (
                    String id,
                    float x, float y,
                    float width, float height,
                    int color,
                    float defaultHeight,
                    float topOffset,
                    float topOffsetColorFactor,
                    float heightColorFactor,
                    float floorShadowColorFactor,
                    String Img
            )
    {
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
        drawUtil = new DrawUtil(null);
    }
    public DrawUtil getDrawUtil(){
        return drawUtil;
    }
    @Override
    public void drawSelf(TexDrawer painter){

    }
}
