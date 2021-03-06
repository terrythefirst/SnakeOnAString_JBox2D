package com.work.terry.snakeonastring_jbox2d.SurfaceViewAndActivity;

import android.content.SharedPreferences;
import android.util.Log;
import android.view.MotionEvent;

import com.work.terry.snakeonastring_jbox2d.Animation.BreathAnimation;
import com.work.terry.snakeonastring_jbox2d.SnakeElements.SnakeHead;
import com.work.terry.snakeonastring_jbox2d.SnakeElements.SnakeNode;
import com.work.terry.snakeonastring_jbox2d.SnakeElements.SnakeSkinManager;
import com.work.terry.snakeonastring_jbox2d.Thread.Stoppable;
import com.work.terry.snakeonastring_jbox2d.UI.Button;
import com.work.terry.snakeonastring_jbox2d.UI.GameElement;
import com.work.terry.snakeonastring_jbox2d.UI.ImgButton;
import com.work.terry.snakeonastring_jbox2d.Animation.ListJiggleAnimation;
import com.work.terry.snakeonastring_jbox2d.UI.Score;
import com.work.terry.snakeonastring_jbox2d.UI.ScoreBoard;
import com.work.terry.snakeonastring_jbox2d.Util.Constant;
import com.work.terry.snakeonastring_jbox2d.Util.DrawUtil;
import com.work.terry.snakeonastring_jbox2d.Util.SoundPoolManager;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static com.work.terry.snakeonastring_jbox2d.Util.Constant.*;

/**
 * Created by Terry on 2018/2/17.
 */

public class StartView extends MyView {
    private boolean closed = false;
    private String backgroundImg = BackgroundImg;
    private ImgButton musicButton;
    private ImgButton statisticsButton;
    private ScoreBoard yellowStarScoreboard;
    private GameElement yellowStar;
    private ImgButton switchButton;
    private ImgButton originalPlayButton;
    private GameElement originalPlayBand;
    private ImgButton endlessPlayButton;
    private GameElement endlessPlayBand;
    private ImgButton favoriteButton;
    private ImgButton rateButton;
    private ImgButton likeButton;

    private List<GameElement> letterSnakeList = new ArrayList<>();
    private List<GameElement> letterOnAStringList = new ArrayList<>();
    private List<GameElement> snake = new ArrayList<>();

    float letterSnakeDefaultHeight = 30;
    float letterSnakeBeginX = 250;
    float letterSnakeSpan = 40;
    float letterSnakeWidth = 200;
    float letterSnakeHeight = 200;
    float letterSnakeY = 500;

    float letterSnake_OnAStringSpanY = 200;
    float letterOnAStringY = letterSnakeY+letterSnake_OnAStringSpanY;
    float letterOnaStringStartX = 200;
    float letterDefaultHeight = 30;
    float letterOnAStringWidth = 100;
    float letterOnAStringHeight = 100;
    float letterOnAStringSpanX = 10;
    float letterOnAStringGapX = 100;

    private ListJiggleAnimation snakeJiggleAnimationThread;
    private ListJiggleAnimation  SnakeLettersAnimationThread;

    float scaleRatio = 1.4f;
    float SnakeY = 1200;
    float SnakeYSpan = 150;
    float SnakeXSpan = 90;
    float SnakeXTotalSpan = 8*SnakeXSpan;

    float OriginalEndlessY = 1750;
    float OriginalEndlessSpan = 560;
    float OriginalEndlessWidth = 260;
    float OriginalEndlessHeight = OriginalEndlessWidth;
    float OriginalEndlessDefaultHeight = 40;

    float OriginalEndlessMaxScaleRate = 0.1f;
    float OriginalEndlessJumpSpan = 30;
    float OriginalEndlessTimeSpan = 2f;

    float OriginalButtonBandSpan = 200;
    float OriginalBandWidth = 260;
    float OriginalBandHeight = 260;
    float OriginalBandDefaultHeight = 10;

    float EndlessButtonBandSpan = OriginalButtonBandSpan;
    float EndlessBandWidth = OriginalBandWidth;
    float EndlessBandHeight = OriginalBandHeight;
    float EndlessBandDefaultHeight = 10;

    private float threeButtonsY = 2260;
    private float threeButtonsSpan = 20;



    public StartView(
        GamePlayView gamePlayView,
        String backgroundImg
    ){
        this.gamePlayView = gamePlayView;
        if(backgroundImg!=null)this.backgroundImg = backgroundImg;
        drawUtil = new DrawUtil(this.backgroundImg);
        initSnake();
        initButtons();
        initLetterSnake();
        initOnAString();
        initScoreBoard();
    }
    public void initScoreBoard(){
        yellowStar = new Button(
                0,
                800,140,
                150,150,
                Constant.COLOR_GOLDEN,
                30,
                10,
                Constant.ButtonBlockTopOffSetColorFactor,
                Constant.ButtonBlockHeightColorFactor,
                Constant.ButtonBlockFloorColorFactor,
                Constant.StarFavoriteImg
        ) ;
        yellowStar.setDoDrawHeight(false);
        drawUtil.addToCenterLayer(yellowStar);

        Score yellowStarScore = new Score(gamePlayView.yellowStars);
        yellowStarScoreboard = new ScoreBoard(
                yellowStarScore,
                1000,140,
                400,150,
                Constant.COLOR_WHITE,
                30,
                10,
                Constant.ButtonBlockTopOffSetColorFactor,
                Constant.ButtonBlockHeightColorFactor,
                Constant.ButtonBlockFloorColorFactor
        );
        drawUtil.addToCenterLayer(yellowStarScoreboard);
    }
    public void initSnake(){
        int nowSkin = gamePlayView.getSnakeSkinNumber();//SnakeSkinManager.SKIN_DEFAULT;

        int NodeIndex = 0;
        while (NodeIndex < 8){
            SnakeNode snakeNode = new SnakeNode(
                    720+SnakeXSpan*(NodeIndex-4),SnakeY+SnakeYSpan*((float)Math.sin(0.25*Math.PI*NodeIndex)),
                    0,
                    SnakeSkinManager.getSkinNodeInfo(nowSkin,8-NodeIndex),
                    scaleRatio,
                    Constant.SnakeDefaultHeight,
                    NodeIndex++
            );
            drawUtil.addToCenterLayer(snakeNode);
            snake.add(snakeNode);
        }

        SnakeHead snakeHead = new SnakeHead(
                720+SnakeXSpan*(NodeIndex-4),SnakeY+SnakeYSpan*((float)Math.sin(0.25*Math.PI*NodeIndex)),
                1,1,
                SnakeSkinManager.getSkinNodeInfo(nowSkin,0),
                scaleRatio,
                Constant.SnakeDefaultHeight
        );
        drawUtil.addToCenterLayer(snakeHead);
        snake.add(snakeHead);

        Collections.reverse(snake);
        snakeJiggleAnimationThread = new ListJiggleAnimation(
                snake,
                40,
                0.3f,
                2000,
                false,
                0,
                true
        );
        snakeJiggleAnimationThread.start();
    }
    public void initLetterSnake(){
        Button tempt = new Button(
                5,
                letterSnakeBeginX,letterSnakeY,
                letterSnakeWidth,letterSnakeHeight,
                Constant.C0LOR_SNAKE_WHITE,
                letterSnakeDefaultHeight,
                10,
                Constant.ButtonBlockTopOffSetColorFactor,
                0,
                Constant.ButtonBlockFloorColorFactor,
                Constant.LetterS
        );
        tempt.setDoDrawHeight(false);
        letterSnakeList.add(tempt);
        drawUtil.addToCenterLayer(tempt);

        tempt = new Button(
                5,
                letterSnakeBeginX+letterSnakeWidth+letterSnakeSpan,letterSnakeY,
                letterSnakeWidth,letterSnakeHeight,
                Constant.C0LOR_SNAKE_WHITE,
                letterSnakeDefaultHeight,
                10,
                Constant.ButtonBlockTopOffSetColorFactor,
                0,
                Constant.ButtonBlockFloorColorFactor,
                Constant.LetterN
        );
        tempt.setDoDrawHeight(false);
        letterSnakeList.add(tempt);
        drawUtil.addToCenterLayer(tempt);

        tempt = new Button(
                5,
                letterSnakeBeginX+(letterSnakeWidth+letterSnakeSpan)*2,letterSnakeY,
                letterSnakeWidth,letterSnakeHeight,
                Constant.C0LOR_SNAKE_WHITE,
                letterSnakeDefaultHeight,
                10,
                Constant.ButtonBlockTopOffSetColorFactor,
                0,
                Constant.ButtonBlockFloorColorFactor,
                Constant.LetterA
        );
        tempt.setDoDrawHeight(false);
        letterSnakeList.add(tempt);
        drawUtil.addToCenterLayer(tempt);

        tempt = new Button(
                5,
                letterSnakeBeginX+(letterSnakeWidth+letterSnakeSpan)*3,letterSnakeY,
                letterSnakeWidth,letterSnakeHeight,
                Constant.C0LOR_SNAKE_WHITE,
                letterSnakeDefaultHeight,
                10,
                Constant.ButtonBlockTopOffSetColorFactor,
                0,
                Constant.ButtonBlockFloorColorFactor,
                Constant.LetterK
        );
        tempt.setDoDrawHeight(false);
        letterSnakeList.add(tempt);
        drawUtil.addToCenterLayer(tempt);

        tempt = new Button(
                5,
                letterSnakeBeginX+(letterSnakeWidth+letterSnakeSpan)*4,letterSnakeY,
                letterSnakeWidth,letterSnakeHeight,
                Constant.C0LOR_SNAKE_WHITE,
                letterSnakeDefaultHeight,
                10,
                Constant.ButtonBlockTopOffSetColorFactor,
                0,
                Constant.ButtonBlockFloorColorFactor,
                Constant.LetterE
        );
        tempt.setDoDrawHeight(false);
        letterSnakeList.add(tempt);
        drawUtil.addToCenterLayer(tempt);

        SnakeLettersAnimationThread = new ListJiggleAnimation(
                letterSnakeList,
                40,
                0.3f,
                2000,
                true,
                0.3f,
                true
        );
        SnakeLettersAnimationThread.start();
    }
    public void initOnAString(){
        Button tempt = new Button(
                9,
                letterOnaStringStartX,letterOnAStringY,
                letterOnAStringWidth,letterOnAStringHeight,
                Constant.C0LOR_SNAKE_WHITE,
                letterDefaultHeight,
                10,
                Constant.ButtonBlockTopOffSetColorFactor,
                0,
                Constant.ButtonBlockFloorColorFactor,
                Constant.LetterO
        );
        tempt.setDoDrawHeight(false);
        letterOnAStringList.add(tempt);
        drawUtil.addToCenterLayer(tempt);

        tempt = new Button(
                9,
                letterOnAStringList.get(letterOnAStringList.size()-1).x+(letterOnAStringWidth+letterOnAStringSpanX),letterOnAStringY,
                letterOnAStringWidth,letterOnAStringHeight,
                Constant.C0LOR_SNAKE_WHITE,
                letterDefaultHeight,
                10,
                Constant.ButtonBlockTopOffSetColorFactor,
                0,
                Constant.ButtonBlockFloorColorFactor,
                Constant.LetterN
        );
        tempt.setDoDrawHeight(false);
        letterOnAStringList.add(tempt);
        drawUtil.addToCenterLayer(tempt);

        tempt = new Button(
                9,
                letterOnAStringList.get(letterOnAStringList.size()-1).x+(letterOnAStringWidth+letterOnAStringGapX),letterOnAStringY,
                letterOnAStringWidth,letterOnAStringHeight,
                Constant.C0LOR_SNAKE_WHITE,
                letterDefaultHeight,
                10,
                Constant.ButtonBlockTopOffSetColorFactor,
                0,
                Constant.ButtonBlockFloorColorFactor,
                Constant.LetterA
        );
        tempt.setDoDrawHeight(false);
        letterOnAStringList.add(tempt);
        drawUtil.addToCenterLayer(tempt);


        tempt = new Button(
                9,
                letterOnAStringList.get(letterOnAStringList.size()-1).x+(letterOnAStringWidth+letterOnAStringGapX),letterOnAStringY,
                letterOnAStringWidth,letterOnAStringHeight,
                Constant.C0LOR_SNAKE_WHITE,
                letterDefaultHeight,
                10,
                Constant.ButtonBlockTopOffSetColorFactor,
                0,
                Constant.ButtonBlockFloorColorFactor,
                Constant.LetterS
        );
        tempt.setDoDrawHeight(false);
        letterOnAStringList.add(tempt);
        drawUtil.addToCenterLayer(tempt);

        tempt = new Button(
                9,
                letterOnAStringList.get(letterOnAStringList.size()-1).x+(letterOnAStringWidth+letterOnAStringSpanX),letterOnAStringY,
                letterOnAStringWidth,letterOnAStringHeight,
                Constant.C0LOR_SNAKE_WHITE,
                letterDefaultHeight,
                10,
                Constant.ButtonBlockTopOffSetColorFactor,
                0,
                Constant.ButtonBlockFloorColorFactor,
                Constant.LetterT
        );
        tempt.setDoDrawHeight(false);
        letterOnAStringList.add(tempt);
        drawUtil.addToCenterLayer(tempt);

        tempt = new Button(
                9,
                letterOnAStringList.get(letterOnAStringList.size()-1).x+(letterOnAStringWidth+letterOnAStringSpanX),letterOnAStringY,
                letterOnAStringWidth,letterOnAStringHeight,
                Constant.C0LOR_SNAKE_WHITE,
                letterDefaultHeight,
                10,
                Constant.ButtonBlockTopOffSetColorFactor,
                0,
                Constant.ButtonBlockFloorColorFactor,
                Constant.LetterR
        );
        tempt.setDoDrawHeight(false);
        letterOnAStringList.add(tempt);
        drawUtil.addToCenterLayer(tempt);

        tempt = new Button(
                9,
                letterOnAStringList.get(letterOnAStringList.size()-1).x+(letterOnAStringWidth+letterOnAStringSpanX),letterOnAStringY,
                letterOnAStringWidth,letterOnAStringHeight,
                Constant.C0LOR_SNAKE_WHITE,
                letterDefaultHeight,
                10,
                Constant.ButtonBlockTopOffSetColorFactor,
                0,
                Constant.ButtonBlockFloorColorFactor,
                Constant.LetterI
        );
        tempt.setDoDrawHeight(false);
        letterOnAStringList.add(tempt);
        drawUtil.addToCenterLayer(tempt);

        tempt = new Button(
                9,
                letterOnAStringList.get(letterOnAStringList.size()-1).x+(letterOnAStringWidth+letterOnAStringSpanX),letterOnAStringY,
                letterOnAStringWidth,letterOnAStringHeight,
                Constant.C0LOR_SNAKE_WHITE,
                letterDefaultHeight,
                10,
                Constant.ButtonBlockTopOffSetColorFactor,
                0,
                Constant.ButtonBlockFloorColorFactor,
                Constant.LetterN
        );
        tempt.setDoDrawHeight(false);
        letterOnAStringList.add(tempt);
        drawUtil.addToCenterLayer(tempt);

        tempt = new Button(
                9,
                letterOnAStringList.get(letterOnAStringList.size()-1).x+(letterOnAStringWidth+letterOnAStringSpanX),letterOnAStringY,
                letterOnAStringWidth,letterOnAStringHeight,
                Constant.C0LOR_SNAKE_WHITE,
                letterDefaultHeight,
                10,
                Constant.ButtonBlockTopOffSetColorFactor,
                0,
                Constant.ButtonBlockFloorColorFactor,
                Constant.LetterG
        );
        tempt.setDoDrawHeight(false);
        letterOnAStringList.add(tempt);
        drawUtil.addToCenterLayer(tempt);

        float perTimeSpan = 2.0f;
        float jumpSpan = 40;
        float maxScaleRate = 0.2f;
        new Thread(){
            @Override
            public void run(){
                for(GameElement ge:letterOnAStringList){
                    new Thread(){
                        public void run(){
                            while (!closed){
                                new BreathAnimation(
                                        ge,
                                        false,
                                        jumpSpan,
                                        true,
                                        maxScaleRate,
                                        perTimeSpan
                                ).start();
                                try {
                                    sleep((long)(perTimeSpan*1000));
                                }catch (Exception e){
                                    e.printStackTrace();
                                }
                            }
                        }
                    }.start();
                    try {
                        sleep((long)(perTimeSpan*200));
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }
            }
        }.start();
    }
    public void initButtons(){
        switchButton = new ImgButton(
                0,
                720,SnakeY-40,
                150,150,
                Constant.COLOR_RED,
                8,
                0,
                0,
                Constant.ButtonBlockHeightColorFactor,
                Constant.ButtonBlockFloorColorFactor,
                Constant.ArrowsSwitchImg,
                Constant.SnakeBodyImg
        );
        switchButton.setFloorShadowFactorX(0);
        switchButton.setFloorShadowFactorY(4f);
        switchButton.setTopImgRatio(0.7f);
        switchButton.setButtonListener(
                ()->{
                    gamePlayView.setNowView(Constant.SKIN_CHANGING_VIEW);
                }
        );
        buttons.add(switchButton);
        drawUtil.addToTopLayer(switchButton);

        musicButton = new ImgButton(
                0,
                100,140,
                160,160,
                Constant.COLOR_SKYBLUE,
                20,
                0,
                0,
                Constant.ButtonBlockHeightColorFactor,
                Constant.ButtonBlockFloorColorFactor,
                Constant.SoundOutloud,
                Constant.SnakeBodyImg
        );
        int musicMode1 = gamePlayView.getMusicMode();
        switch(musicMode1){
            case Constant.MUSIC_MODE_ALL_ON:
                musicButton.setTopImgImgButton(Constant.SoundOutloud);
                break;
            case Constant.MUSIC_MODE_BG_OFF:
                musicButton.setTopImgImgButton(Constant.SoundAltImg);
                break;
            case Constant.MUSIC_MODE_ALL_OFF:
                musicButton.setTopImgImgButton(Constant.SoundOffImg);
                break;
        }
        musicButton.setButtonListener(
                ()->{
                    gamePlayView.changeMusicMode();

                    int musicMode = gamePlayView.getMusicMode();
                    switch(musicMode){
                        case Constant.MUSIC_MODE_ALL_ON:
                            musicButton.setTopImgImgButton(Constant.SoundOutloud);
                            break;
                        case Constant.MUSIC_MODE_BG_OFF:
                            musicButton.setTopImgImgButton(Constant.SoundAltImg);
                            break;
                        case Constant.MUSIC_MODE_ALL_OFF:
                            musicButton.setTopImgImgButton(Constant.SoundOffImg);
                            break;
                    }
                }
        );
        drawUtil.addToCenterLayer(musicButton);
        buttons.add(musicButton);

        statisticsButton = new ImgButton(
                0,
                280,140,
                160,160,
                Constant.COLOR_GREEN,
                20,
                0,
                0,
                Constant.ButtonBlockHeightColorFactor,
                Constant.ButtonBlockFloorColorFactor,
                Constant.StatisticsBars,
                Constant.SnakeBodyImg
        );
        drawUtil.addToCenterLayer(statisticsButton);
        buttons.add(statisticsButton);

        initThreeButtons();
        initOriginalEndlessButton();
    }
    public void initOriginalEndlessButton(){
        originalPlayButton = new ImgButton(
                0,
                720-OriginalEndlessSpan/2,OriginalEndlessY,
                OriginalEndlessWidth,OriginalEndlessHeight,
                Constant.COLOR_GOLDEN,
                OriginalEndlessDefaultHeight,
                0,
                0,
                Constant.ButtonBlockHeightColorFactor,
                Constant.ButtonBlockFloorColorFactor,
                Constant.StartImg,
                Constant.RoundEdgeCube
        );
        originalPlayButton.setTopImgRatio(0.5f);
        originalPlayButton.setDisabled(true);
        drawUtil.addToCenterLayer(originalPlayButton);
        buttons.add(originalPlayButton);
        new Thread(){
            @Override
            public void run(){
                while(!closed){
                    Thread thread = new BreathAnimation(
                            originalPlayButton,
                            false,
                            OriginalEndlessJumpSpan,
                            true,
                            OriginalEndlessMaxScaleRate,
                            OriginalEndlessTimeSpan
                    );

                    thread.start();
                    try {
                        thread.join();
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }
            }
        }.start();
        originalPlayBand = new Button(
                0,
                originalPlayButton.x,originalPlayButton.y+OriginalButtonBandSpan,
                OriginalBandWidth,OriginalBandHeight,
                Constant.COLOR_WHITE,
                OriginalBandDefaultHeight,
                0,
                Constant.ButtonBlockTopOffSetColorFactor,
                Constant.ButtonBlockHeightColorFactor,
                Constant.ButtonBlockFloorColorFactor,
                Constant.LetterOriginal
        ) ;
        originalPlayBand.setDoDrawHeight(false);
        drawUtil.addToCenterLayer(originalPlayBand);

        endlessPlayButton= new ImgButton(
                0,
                720+OriginalEndlessSpan/2,OriginalEndlessY,
                OriginalEndlessWidth,OriginalEndlessHeight,
                Constant.COLOR_BLACKBLUE,
                OriginalEndlessDefaultHeight,
                0,
                0,
                Constant.ButtonBlockHeightColorFactor,
                Constant.ButtonBlockFloorColorFactor,
                Constant.InfiniteImg,
                Constant.RoundEdgeCube
        );
        endlessPlayButton.setTopImgRatio(0.5f);
        endlessPlayButton.setButtonListener(
                ()->{
                        MyMenu endlessPlayMenu = new EndlessPlayMenu(gamePlayView);
                        endlessPlayMenu.popFromBottom();
                        gamePlayView.setNowMenu(
                                endlessPlayMenu
                        );
                    }
        );
        drawUtil.addToCenterLayer(endlessPlayButton);
        buttons.add(endlessPlayButton);
        new Thread(){
            @Override
            public void run(){
                try {
                    sleep((long)(OriginalEndlessTimeSpan*1000/2));
                }catch (Exception e){
                    e.printStackTrace();
                }
                while(!closed){
                    Thread thread = new BreathAnimation(
                            endlessPlayButton,
                            false,
                            OriginalEndlessJumpSpan,
                            true,
                            OriginalEndlessMaxScaleRate,
                            OriginalEndlessTimeSpan
                    );

                    thread.start();
                    try {
                        thread.join();
                    }catch (Exception e){
                        e.printStackTrace();
                    }

                }
            }
        }.start();
        endlessPlayBand = new Button(
                0,
                endlessPlayButton.x,endlessPlayButton.y+EndlessButtonBandSpan,
                EndlessBandWidth,EndlessBandHeight,
                Constant.COLOR_WHITE,
                EndlessBandDefaultHeight,
                0,
                Constant.ButtonBlockTopOffSetColorFactor,
                Constant.ButtonBlockHeightColorFactor,
                Constant.ButtonBlockFloorColorFactor,
                Constant.LetterEndless
        );
        endlessPlayBand.setDoDrawHeight(false);
        drawUtil.addToCenterLayer(endlessPlayBand);
    }
    public void initThreeButtons(){
        threeButtonsY = 2260;
        threeButtonsSpan = 30;
        favoriteButton = new ImgButton(
                0,
                720-160-threeButtonsSpan,threeButtonsY,
                160,160,
                Constant.COLOR_GOLDEN,
                20,
                0,
                0,
                Constant.ButtonBlockHeightColorFactor,
                Constant.ButtonBlockFloorColorFactor,
                Constant.StarFavoriteImg,
                Constant.SnakeBodyImg
        );
        drawUtil.addToCenterLayer(favoriteButton);
        buttons.add(favoriteButton);

        rateButton = new ImgButton(
                0,
                720,threeButtonsY,
                160,160,
                Constant.COLOR_SKYBLUE,
                20,
                0,
                0,
                Constant.ButtonBlockHeightColorFactor,
                Constant.ButtonBlockFloorColorFactor,
                Constant.RateImg,
                Constant.SnakeBodyImg
        );
        rateButton.setTopImgRatio(0.6f);
        drawUtil.addToCenterLayer(rateButton);
        buttons.add(rateButton);

        likeButton = new ImgButton(
                0,
                720+160+threeButtonsSpan,threeButtonsY,
                160,160,
                Constant.COLOR_RED,
                20,
                0,
                0,
                Constant.ButtonBlockHeightColorFactor,
                Constant.ButtonBlockFloorColorFactor,
                Constant.LikeImg,
                Constant.SnakeBodyImg
        );
        likeButton.setTopImgRatio(0.6f);
        drawUtil.addToCenterLayer(likeButton);
        buttons.add(likeButton);
    }

    @Override
    public void onTouchEvent(MotionEvent event, float x, float y) {
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
            case MotionEvent.ACTION_MOVE:
                Button tempt = nowPressedButton;
                nowPressedButton = whichButtonTouched(x,y);
                if(tempt!=null&&nowPressedButton!=tempt)tempt.whenReleased(false);
                if(nowPressedButton!=null)nowPressedButton.whenPressed();
                break;
            case MotionEvent.ACTION_UP:
                // if(pauseButton.testTouch(x,y))pauseButton
                whenUp(x,y);
                break;
        }
    }

    @Override
    public void onResume() {
        closed = false;
    }

    @Override
    public void onPause(SharedPreferences.Editor editor) {

    }

    @Override
    public void onSwitchViewAndStop() {
        if(snakeJiggleAnimationThread!=null)snakeJiggleAnimationThread.setShouldDie();
        if(SnakeLettersAnimationThread!=null)SnakeLettersAnimationThread.setShouldDie();
        closed = true;
    }
}
