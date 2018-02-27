package com.work.terry.snakeonastring_jbox2d.SurfaceViewAndActivity;

import com.work.terry.snakeonastring_jbox2d.UI.Button;
import com.work.terry.snakeonastring_jbox2d.UI.GameElements;
import com.work.terry.snakeonastring_jbox2d.UI.ImgButton;
import com.work.terry.snakeonastring_jbox2d.UI.RoundEdgeRect;
import com.work.terry.snakeonastring_jbox2d.UI.RoundEdgeRectButton;
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
                Constant.COLOR_BLACKBLUE,
                20,
                0,
                Constant.ButtonBlockTopOffSetColorFactor,
                Constant.ButtonBlockHeightColorFactor,
                Constant.ButtonBlockFloorColorFactor,
                null
        );

        initBand();
        initLevels();
    }
    public void initLevels(){
        float levelButtonsDefaultHeight = 10;
        float levelButtonsAngleRadius = 100;
        float levelButtonsWidth = 350;
        float levelButtonHeight = 400;
        float levelButtonsStartY = 420;
        float levelButtonsYSpan = 100;
        float levelButtonsXSpan = 30;


        RoundEdgeRectButton level1Button = new RoundEdgeRectButton(
                0,
                0,0,
                levelButtonsWidth,levelButtonHeight,
                levelButtonsAngleRadius,
                Constant.C0LOR_CYAN,
                levelButtonsDefaultHeight,
                0,
                Constant.ButtonBlockTopOffSetColorFactor,
                Constant.ButtonBlockHeightColorFactor,
                0.8f,
                Constant.Number1Img,
                null
        );
        level1Button.setTopImgRatio(0.4f);
        level1Button.setConstantXY(new Vec2(-(levelButtonsXSpan+levelButtonsWidth),levelButtonsStartY));
        addButton(level1Button);

        RoundEdgeRectButton level2Button = new RoundEdgeRectButton(
                0,
                0,0,
                levelButtonsWidth,levelButtonHeight,
                levelButtonsAngleRadius,
                Constant.C0LOR_CYAN,
                levelButtonsDefaultHeight,
                0,
                Constant.ButtonBlockTopOffSetColorFactor,
                Constant.ButtonBlockHeightColorFactor,
                0.8f,
                Constant.Number2Img,
                null
        );
        level2Button.setTopImgRatio(0.4f);
        level2Button.setConstantXY(new Vec2(0,levelButtonsStartY));
        level2Button.setDisabled(true);
        addButton(level2Button);

        RoundEdgeRectButton level3Button = new RoundEdgeRectButton(
                0,
                0,0,
                levelButtonsWidth,levelButtonHeight,
                levelButtonsAngleRadius,
                Constant.C0LOR_CYAN,
                levelButtonsDefaultHeight,
                0,
                Constant.ButtonBlockTopOffSetColorFactor,
                Constant.ButtonBlockHeightColorFactor,
                0.8f,
                Constant.Number3Img,
                null
        );
        level3Button.setTopImgRatio(0.4f);
        level3Button.setConstantXY(new Vec2((levelButtonsXSpan+levelButtonsWidth),levelButtonsStartY));
        addButton(level3Button);

        RoundEdgeRectButton level4Button = new RoundEdgeRectButton(
                0,
                0,0,
                levelButtonsWidth,levelButtonHeight,
                levelButtonsAngleRadius,
                Constant.C0LOR_CYAN,
                levelButtonsDefaultHeight,
                0,
                Constant.ButtonBlockTopOffSetColorFactor,
                Constant.ButtonBlockHeightColorFactor,
                0.8f,
                Constant.Number4Img,
                null
        );
        level4Button.setTopImgRatio(0.4f);
        level4Button.setConstantXY(new Vec2(-(levelButtonsXSpan+levelButtonsWidth),levelButtonsStartY+levelButtonsYSpan+levelButtonHeight));
        addButton(level4Button);

        RoundEdgeRectButton level5Button = new RoundEdgeRectButton(
                0,
                0,0,
                levelButtonsWidth,levelButtonHeight,
                levelButtonsAngleRadius,
                Constant.C0LOR_CYAN,
                levelButtonsDefaultHeight,
                0,
                Constant.ButtonBlockTopOffSetColorFactor,
                Constant.ButtonBlockHeightColorFactor,
                0.8f,
                Constant.Number5Img,
                null
        );
        level5Button.setTopImgRatio(0.4f);
        level5Button.setConstantXY(new Vec2(0,levelButtonsStartY+levelButtonsYSpan+levelButtonHeight));
        addButton(level5Button);

        RoundEdgeRectButton level6Button = new RoundEdgeRectButton(
                0,
                0,0,
                levelButtonsWidth,levelButtonHeight,
                levelButtonsAngleRadius,
                Constant.C0LOR_CYAN,
                levelButtonsDefaultHeight,
                0,
                Constant.ButtonBlockTopOffSetColorFactor,
                Constant.ButtonBlockHeightColorFactor,
                0.8f,
                Constant.Number6Img,
                null
        );
        level6Button.setTopImgRatio(0.4f);
        level6Button.setConstantXY(new Vec2((levelButtonsXSpan+levelButtonsWidth),levelButtonsStartY+levelButtonsYSpan+levelButtonHeight));
        addButton(level6Button);

        RoundEdgeRectButton level7Button = new RoundEdgeRectButton(
                0,
                0,0,
                levelButtonsWidth,levelButtonHeight,
                levelButtonsAngleRadius,
                Constant.C0LOR_CYAN,
                levelButtonsDefaultHeight,
                0,
                Constant.ButtonBlockTopOffSetColorFactor,
                Constant.ButtonBlockHeightColorFactor,
                0.8f,
                Constant.Number7Img,
                null
        );
        level7Button.setTopImgRatio(0.4f);
        level7Button.setConstantXY(new Vec2(-(levelButtonsXSpan+levelButtonsWidth),levelButtonsStartY+(levelButtonsYSpan+levelButtonHeight)*2));
        addButton(level7Button);

        RoundEdgeRectButton level8Button = new RoundEdgeRectButton(
                0,
                0,0,
                levelButtonsWidth,levelButtonHeight,
                levelButtonsAngleRadius,
                Constant.C0LOR_CYAN,
                levelButtonsDefaultHeight,
                0,
                Constant.ButtonBlockTopOffSetColorFactor,
                Constant.ButtonBlockHeightColorFactor,
                0.8f,
                Constant.Number8Img,
                null
        );
        level8Button.setTopImgRatio(0.4f);
        level8Button.setConstantXY(new Vec2(0,levelButtonsStartY+(levelButtonsYSpan+levelButtonHeight)*2));
        addButton(level8Button);

        RoundEdgeRectButton level9Button = new RoundEdgeRectButton(
                0,
                0,0,
                levelButtonsWidth,levelButtonHeight,
                levelButtonsAngleRadius,
                Constant.C0LOR_CYAN,
                levelButtonsDefaultHeight,
                0,
                Constant.ButtonBlockTopOffSetColorFactor,
                Constant.ButtonBlockHeightColorFactor,
                0.8f,
                Constant.Number9Img,
                null
        );
        level9Button.setTopImgRatio(0.4f);
        level9Button.setConstantXY(new Vec2((levelButtonsXSpan+levelButtonsWidth),levelButtonsStartY+(levelButtonsYSpan+levelButtonHeight)*2));
        addButton(level9Button);
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
