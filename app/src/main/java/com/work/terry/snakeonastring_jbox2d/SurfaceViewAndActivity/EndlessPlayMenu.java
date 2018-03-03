package com.work.terry.snakeonastring_jbox2d.SurfaceViewAndActivity;

import com.work.terry.snakeonastring_jbox2d.UI.GameElement;
import com.work.terry.snakeonastring_jbox2d.UI.RoundEdgeRectButton;
import com.work.terry.snakeonastring_jbox2d.Util.Constant;
import com.work.terry.snakeonastring_jbox2d.Util.LoadGameUtil;

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
                null,
                true
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
                1,
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
        level1Button.setDisabled(!LoadGameUtil.doLevelExist(Constant.GAME_MODE_ENDLESS,1,gamePlayView.getResources()));
        level1Button.setButtonListener(
                ()->{
                    gamePlayView.setNowView(Constant.GAMEPLAY_VIEW_ENDLESS+1);
                    closeButton.doButtonStuff();
                }
        );

        RoundEdgeRectButton level2Button = new RoundEdgeRectButton(
                2,
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
        level2Button.setDisabled(!LoadGameUtil.doLevelExist(Constant.GAME_MODE_ENDLESS,2,gamePlayView.getResources()));
        level2Button.setButtonListener(
                ()->{
                    gamePlayView.setNowView(Constant.GAMEPLAY_VIEW_ENDLESS+2);
                    closeButton.doButtonStuff();
                }
        );
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
        level3Button.setDisabled(!LoadGameUtil.doLevelExist(Constant.GAME_MODE_ENDLESS,3,gamePlayView.getResources()));
        level3Button.setButtonListener(
                ()->{
                    gamePlayView.setNowView(Constant.GAMEPLAY_VIEW_ENDLESS+3);
                    closeButton.doButtonStuff();
                }
        );

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
        level4Button.setDisabled(!LoadGameUtil.doLevelExist(Constant.GAME_MODE_ENDLESS,4,gamePlayView.getResources()));
        level4Button.setButtonListener(
                ()->{
                    gamePlayView.setNowView(Constant.GAMEPLAY_VIEW_ENDLESS+4);
                    closeButton.doButtonStuff();
                }
        );

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
        level5Button.setDisabled(!LoadGameUtil.doLevelExist(Constant.GAME_MODE_ENDLESS,5,gamePlayView.getResources()));
        level5Button.setButtonListener(
                ()->{
                    gamePlayView.setNowView(Constant.GAMEPLAY_VIEW_ENDLESS+5);
                    closeButton.doButtonStuff();
                }
        );

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
        level6Button.setDisabled(!LoadGameUtil.doLevelExist(Constant.GAME_MODE_ENDLESS,6,gamePlayView.getResources()));
        level6Button.setButtonListener(
                ()->{
                    gamePlayView.setNowView(Constant.GAMEPLAY_VIEW_ENDLESS+6);
                    closeButton.doButtonStuff();
                }
        );

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
        level7Button.setDisabled(!LoadGameUtil.doLevelExist(Constant.GAME_MODE_ENDLESS,7,gamePlayView.getResources()));
        level7Button.setButtonListener(
                ()->{
                    gamePlayView.setNowView(Constant.GAMEPLAY_VIEW_ENDLESS+7);
                    closeButton.doButtonStuff();
                }
        );

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
        level8Button.setDisabled(!LoadGameUtil.doLevelExist(Constant.GAME_MODE_ENDLESS,8,gamePlayView.getResources()));
        level8Button.setButtonListener(
                ()->{
                    gamePlayView.setNowView(Constant.GAMEPLAY_VIEW_ENDLESS+8);
                    closeButton.doButtonStuff();
                }
        );

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
        level9Button.setDisabled(!LoadGameUtil.doLevelExist(Constant.GAME_MODE_ENDLESS,9,gamePlayView.getResources()));
        level9Button.setButtonListener(
                ()->{
                    gamePlayView.setNowView(Constant.GAMEPLAY_VIEW_ENDLESS+9);
                    closeButton.doButtonStuff();
                }
        );
    }
    public void initBand(){
        GameElement endlessBand = new GameElement(
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
