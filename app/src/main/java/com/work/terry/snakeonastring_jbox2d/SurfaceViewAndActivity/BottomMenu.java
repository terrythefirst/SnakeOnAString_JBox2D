package com.work.terry.snakeonastring_jbox2d.SurfaceViewAndActivity;

import com.work.terry.snakeonastring_jbox2d.UI.ImgButton;
import com.work.terry.snakeonastring_jbox2d.Util.Constant;
import com.work.terry.snakeonastring_jbox2d.Util.LoadGameUtil;

/**
 * Created by Terry on 2018/3/18.
 */

public class BottomMenu extends MyMenu {
    GamePlay gamePlay;
    MyMenu fatherMenu;
    int gameModeAndLevel;
    int gameMode;

    ImgButton mainMenuButton;
    ImgButton preLevelButton;
    ImgButton nextLevelButton;
    ImgButton musicButton;

    static float bottomBandHeight = 300;
    static float bottomBandButtonsWidth = 160;
    static float bottomBandButtonsHeight = bottomBandButtonsWidth;
    static float bottomBandButtonsRelativeY = bottomBandButtonsHeight/2+40;
    static float bottomBandButtonsSpanX = 40;

    public BottomMenu(
            GamePlay gamePlay,
            MyMenu fatherMenu,
            float menuWidth,
            float defaultHeight,
            float menuFloorShadowFactorX,
            float menuFloorShadowFactorY
    ) {
        super(
                gamePlay.gamePlayView,
                "BottomMenu",
                720, Constant.SCREEN_HEIGHT-350,
                menuWidth,bottomBandHeight,
                150,
                Constant.COLOR_SKINISH,
                defaultHeight,
                0,
                Constant.ButtonBlockTopOffSetColorFactor,
                Constant.ButtonBlockHeightColorFactor,
                Constant.ButtonBlockFloorColorFactor,
                null,
                false
        );
        this.gamePlay = gamePlay;
        this.fatherMenu = fatherMenu;
        gameModeAndLevel = gamePlay.gameModeAndLevel;
        if(gameModeAndLevel>Constant.GAMEPLAY_VIEW_ENDLESS&&gameModeAndLevel<Constant.GAMEPLAY_VIEW_ENDLESS+10){
            gameMode = Constant.GAME_MODE_ENDLESS;
        }else if(gameModeAndLevel>Constant.GAMEPLAY_VIEW_ORIGINAL&&gameModeAndLevel<Constant.GAMEPLAY_VIEW_ORIGINAL+10){
            gameMode = Constant.GAME_MODE_ORIGINAL;
        }

        setFloorShadowFactorX(menuFloorShadowFactorX);
        setFloorShadowFactorY(menuFloorShadowFactorY);
        closeButton.setButtonListener(
                ()->{
                    Thread thread = backToBottom();
                    try {
                        thread.join();
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }
        );
        initBottomBand();
    }

    public void initBottomBand(){
        mainMenuButton = new ImgButton(
                0,
                -(bottomBandButtonsSpanX+bottomBandButtonsWidth)*(2+1)/2,bottomBandButtonsRelativeY,
                bottomBandButtonsWidth,bottomBandButtonsHeight,
                Constant.COLOR_RED,
                20,
                0,
                0,
                Constant.ButtonBlockHeightColorFactor,
                Constant.ButtonBlockFloorColorFactor,
                Constant.MenuImg,
                Constant.SnakeBodyImg
        );
        addButton(mainMenuButton);
        mainMenuButton.setTopImgRatio(0.5f);
        mainMenuButton.setButtonListener(
                ()->{
                    gamePlayView.setNowView(Constant.START_VIEW);
                    MyMenu endlessPlayMenu = new EndlessPlayMenu(gamePlayView);
                    gamePlayView.setNowMenu(endlessPlayMenu);
                }
        );


        preLevelButton = new ImgButton(
                0,
                -(bottomBandButtonsSpanX+bottomBandButtonsWidth)*(1)/2,bottomBandButtonsRelativeY,
                bottomBandButtonsWidth,bottomBandButtonsHeight,
                Constant.COLOR_GREEN,
                20,
                0,
                0,
                Constant.ButtonBlockHeightColorFactor,
                Constant.ButtonBlockFloorColorFactor,
                Constant.ArrowBackImg,
                Constant.SnakeBodyImg
        );
        preLevelButton.setButtonListener(
                ()->{
                    gamePlayView.setNowView(gameModeAndLevel-1);
                    fatherMenu.closeButton.doButtonStuff();
                }
        );
        addButton(preLevelButton);
        addToMenu(preLevelButton);

        nextLevelButton = new ImgButton(
                0,
                (bottomBandButtonsSpanX+bottomBandButtonsWidth)*(1)/2,bottomBandButtonsRelativeY,
                bottomBandButtonsWidth,bottomBandButtonsHeight,
                Constant.COLOR_GREEN,
                20,
                0,
                0,
                Constant.ButtonBlockHeightColorFactor,
                Constant.ButtonBlockFloorColorFactor,
                Constant.ArrowBackImg,
                Constant.SnakeBodyImg
        );
        nextLevelButton.setButtonListener(
                ()->{
                    gamePlayView.setNowView(gameModeAndLevel+1);
                    fatherMenu.closeButton.doButtonStuff();
                }
        );
        nextLevelButton.setTopImgRotateDegrees(180);
        addButton(nextLevelButton);
        addToMenu(nextLevelButton);


        if(gameModeAndLevel>Constant.GAMEPLAY_VIEW_ENDLESS&&gameModeAndLevel<Constant.GAMEPLAY_VIEW_ENDLESS+10){
            if(!LoadGameUtil.doLevelExist(Constant.GAME_MODE_ENDLESS,gameModeAndLevel%10-1,gamePlayView.getResources()))
                preLevelButton.setDisabled(true);
            if(!LoadGameUtil.doLevelExist(Constant.GAME_MODE_ENDLESS,gameModeAndLevel%10+1,gamePlayView.getResources()))
                nextLevelButton.setDisabled(true);
        }else if(gameModeAndLevel>Constant.GAMEPLAY_VIEW_ORIGINAL&&gameModeAndLevel<Constant.GAMEPLAY_VIEW_ORIGINAL+10){
            if(!LoadGameUtil.doLevelExist(Constant.GAME_MODE_ORIGINAL,gameModeAndLevel%10-1,gamePlayView.getResources()))
                preLevelButton.setDisabled(true);
            if(!LoadGameUtil.doLevelExist(Constant.GAME_MODE_ORIGINAL,gameModeAndLevel%10+1,gamePlayView.getResources()))
                nextLevelButton.setDisabled(true);
        }

        musicButton = new ImgButton(
                0,
                (bottomBandButtonsSpanX+bottomBandButtonsWidth)*(2+1)/2,bottomBandButtonsRelativeY,
                bottomBandButtonsWidth,bottomBandButtonsHeight,
                Constant.COLOR_SKYBLUE,
                20,
                0,
                0,
                Constant.ButtonBlockHeightColorFactor,
                Constant.ButtonBlockFloorColorFactor,
                Constant.SoundOutloud,
                Constant.SnakeBodyImg
        );
        addButton(musicButton);
        addToMenu(musicButton);
    }
}
