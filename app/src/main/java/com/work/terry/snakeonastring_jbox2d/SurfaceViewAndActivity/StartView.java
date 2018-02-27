package com.work.terry.snakeonastring_jbox2d.SurfaceViewAndActivity;

import android.content.SharedPreferences;
import android.util.Log;
import android.view.MotionEvent;

import com.work.terry.snakeonastring_jbox2d.SnakeElements.Snake;
import com.work.terry.snakeonastring_jbox2d.SnakeElements.SnakeHead;
import com.work.terry.snakeonastring_jbox2d.SnakeElements.SnakeNode;
import com.work.terry.snakeonastring_jbox2d.UI.Button;
import com.work.terry.snakeonastring_jbox2d.UI.ButtonListener;
import com.work.terry.snakeonastring_jbox2d.UI.GameElements;
import com.work.terry.snakeonastring_jbox2d.UI.ImgButton;
import com.work.terry.snakeonastring_jbox2d.UI.JiggleAnimation;
import com.work.terry.snakeonastring_jbox2d.UI.ListJiggleAnimation;
import com.work.terry.snakeonastring_jbox2d.UI.PullMoveAnimation;
import com.work.terry.snakeonastring_jbox2d.UI.ScoreBoard;
import com.work.terry.snakeonastring_jbox2d.UI.UniformMotionAnimation;
import com.work.terry.snakeonastring_jbox2d.Util.Constant;
import com.work.terry.snakeonastring_jbox2d.Util.DrawUtil;
import com.work.terry.snakeonastring_jbox2d.Util.TexDrawer;

import org.jbox2d.common.Vec2;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import static com.work.terry.snakeonastring_jbox2d.Util.Constant.*;

/**
 * Created by Terry on 2018/2/17.
 */

public class StartView extends MyView {
    public GamePlayView gamePlayView;
    private String backgroundImg = BackgroundImg;
    private ImgButton musicButton;
    private ImgButton statisticsButton;
    private ScoreBoard yellowStarScoreboard;
    private GameElements yellowStar;
    private ImgButton switchButton;
    private ImgButton originalPlayButton;
    private GameElements originalPlayBand;
    private ImgButton endlessPlayButton;
    private GameElements endlessPlayBand;
    private ImgButton favoriteButton;
    private ImgButton rateButton;
    private ImgButton likeButton;

    private List<GameElements> letterSnakeList = new ArrayList<>();
    private List<GameElements> letterOnAStringList = new ArrayList<>();
    private List<GameElements> snake = new ArrayList<>();

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

    float HeadRadius = 50;
    float NodeRadius = 50;
    float SnakeY = 1400;
    float SnakeYSpan = 100;
    float SnakeXSpan = 90;
    float SnakeXTotalSpan = 8*SnakeXSpan;

    float OriginalEndlessY = 1850;
    float OriginalEndlessSpan = 400;
    float OriginalEndlessWidth = 250;
    float OriginalEndlessHeight = 250;
    float OriginalEndlessDefaultHeight = 60;

    float OriginalButtonBandSpan = 200;
    float OriginalBandWidth = 260;
    float OriginalBandHeight = 260;
    float OriginalBandDefaultHeight = 10;

    float EndlessButtonBandSpan = 200;
    float EndlessBandWidth = 260;
    float EndlessBandHeight = 260;
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
                Constant.C0LOR_CYAN,
                30,
                10,
                Constant.ButtonBlockTopOffSetColorFactor,
                Constant.ButtonBlockHeightColorFactor,
                Constant.ButtonBlockFloorColorFactor,
                Constant.StarFavoriteImg
        ) ;
        yellowStar.setDoDrawHeight(false);
        drawUtil.addToCenterLayer(yellowStar);

        yellowStarScoreboard = new ScoreBoard(
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
        int NodeIndex = 0;
        while (NodeIndex < 8){
            SnakeNode snakeNode = new SnakeNode(
                    720+SnakeXSpan*(NodeIndex-4),SnakeY+SnakeYSpan*((float)Math.sin(0.25*Math.PI*NodeIndex)),
                    NodeRadius,
                    Constant.C0LOR_SNAKE_WHITE,
                    Constant.SnakeDefaultHeight,
                    NodeIndex++
            );
            snakeNode.setTopRatio(0.95f);
            drawUtil.addToCenterLayer(snakeNode);
            snake.add(snakeNode);
        }

        SnakeHead snakeHead = new SnakeHead(
                720+SnakeXSpan*(NodeIndex-4),SnakeY+SnakeYSpan*((float)Math.sin(0.25*Math.PI*NodeIndex)),
                1,1,
                NodeRadius,
                Constant.C0LOR_SNAKE_WHITE,
                Constant.SnakeDefaultHeight
        );
        snakeHead.setTopRatio(0.95f);
        drawUtil.addToCenterLayer(snakeHead);
        snake.add(snakeHead);

        Collections.reverse(snake);
        new ListJiggleAnimation(
                snake,
                40,
                0.3f,
                2000,
                false,
                0,
                true
        ).start();
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

        new ListJiggleAnimation(
                letterSnakeList,
                40,
                0.3f,
                2000,
                true,
                0.3f,
                true
        ).start();
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

        new ListJiggleAnimation(
                letterOnAStringList,
                20,
                1f,
                1500,
                true,
                0.2f,
                false
        ).start();
    }
    public void initButtons(){
        switchButton = new ImgButton(
                0,
                720,SnakeY,
                150,150,
                Constant.C0LOR_CYAN,
                30,
                0,
                0,
                Constant.ButtonBlockHeightColorFactor,
                Constant.ButtonBlockFloorColorFactor,
                Constant.ArrowsSwitchImg,
                Constant.SnakeBodyImg
        );
        switchButton.setTopImgRatio(0.7f);
        switchButton.setButtonListener(
                ()->Log.d("switchButton","clicked!")
        );
        buttons.add(switchButton);
        drawUtil.addToTopLayer(switchButton);

        musicButton = new ImgButton(
                0,
                100,140,
                160,160,
                Constant.C0LOR_CYAN,
                20,
                0,
                0,
                Constant.ButtonBlockHeightColorFactor,
                Constant.ButtonBlockFloorColorFactor,
                Constant.SoundOutloud,
                Constant.SnakeBodyImg
        );
        drawUtil.addToCenterLayer(musicButton);
        buttons.add(musicButton);

        statisticsButton = new ImgButton(
                0,
                280,140,
                160,160,
                Constant.C0LOR_CYAN,
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
                Constant.C0LOR_CYAN,
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
                while(true){
                    Thread thread = new JiggleAnimation(
                            originalPlayButton,
                            20,
                            3f,
                            true,
                            0.4f,
                            false
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
                Constant.C0LOR_CYAN,
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
                        gamePlayView.setNowMenu(
                                endlessPlayMenu
                        );
                        new UniformMotionAnimation(
                                endlessPlayMenu,
                                new Vec2(Constant.SCREEN_WIDTH/2,
                                        Constant.SCREEN_HEIGHT/2+100),
                                0.5f
                        ).start();
                    }
        );
        drawUtil.addToCenterLayer(endlessPlayButton);
        buttons.add(endlessPlayButton);
        new Thread(){
            @Override
            public void run(){
                while(true){
                    Thread thread = new JiggleAnimation(
                            endlessPlayButton,
                            20,
                            3f,
                            true,
                            0.4f,
                            false
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
        ) ;
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
                Constant.C0LOR_CYAN,
                20,
                0,
                0,
                Constant.ButtonBlockHeightColorFactor,
                Constant.ButtonBlockFloorColorFactor,
                Constant.StatisticsBars,
                Constant.SnakeBodyImg
        );
        drawUtil.addToCenterLayer(favoriteButton);
        buttons.add(favoriteButton);

        rateButton = new ImgButton(
                0,
                720,threeButtonsY,
                160,160,
                Constant.C0LOR_CYAN,
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
                Constant.C0LOR_CYAN,
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
    public void onTouchEvent(MotionEvent event, int x, int y) {
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

    }

    @Override
    public void onPause(SharedPreferences.Editor editor) {

    }
}
