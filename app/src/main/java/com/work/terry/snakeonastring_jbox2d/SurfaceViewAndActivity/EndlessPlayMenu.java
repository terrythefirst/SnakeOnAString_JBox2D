package com.work.terry.snakeonastring_jbox2d.SurfaceViewAndActivity;

import com.work.terry.snakeonastring_jbox2d.UI.Button;
import com.work.terry.snakeonastring_jbox2d.UI.GameElements;
import com.work.terry.snakeonastring_jbox2d.UI.ImgButton;
import com.work.terry.snakeonastring_jbox2d.Util.Constant;

import org.jbox2d.common.Vec2;

/**
 * Created by Terry on 2018/2/27.
 */

public class EndlessPlayMenu extends MyMenu {
    public EndlessPlayMenu
            (GamePlayView gamePlayView
            ) {
        super(
                gamePlayView,
                "menu",
                Constant.SCREEN_WIDTH/2,
                Constant.SCREEN_HEIGHT+1050,//+Constant.SCREEN_HEIGHT*3/8,
                1230,
                2100,
                200,
                Constant.C0LOR_CYAN,
                20,
                0,
                Constant.ButtonBlockTopOffSetColorFactor,
                Constant.ButtonBlockHeightColorFactor,
                Constant.ButtonBlockFloorColorFactor,
                null
        );

        initBand();


    }
    public void initBand(){
        GameElements endlessBand = new GameElements(
                "endlessMenuBand",
                0, 100,
                800,800,
                Constant.COLOR_WHITE,
                0,
                0,
                Constant.ButtonBlockTopOffSetColorFactor,
                Constant.ButtonBlockHeightColorFactor,
                Constant.ButtonBlockFloorColorFactor,
                Constant.LetterEndless
        );
        endlessBand.setDoDrawHeight(false);
        endlessBand.setDoDrawFloorShadow(false);
        endlessBand.setConstantXY(new Vec2(0, 150));
        addToMenu(endlessBand);
    }
}
