package com.work.terry.snakeonastring_jbox2d.UI;

/**
 * Created by Terry on 2018/2/3.
 */

public class MyAnimation extends GameElements {

    public  MyAnimation(
            int id,
            float x, float y,
            float width, float height,
            int color,
            float defaultHeight,
            float topOffset,
            float topOffsetColorFactor,
            float heightColorFactor,
            float floorShadowColorFactor,
            String Img
    ) {
        super(
                "Animation "+id,
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
        setDoDrawHeight(false);
    }
}
