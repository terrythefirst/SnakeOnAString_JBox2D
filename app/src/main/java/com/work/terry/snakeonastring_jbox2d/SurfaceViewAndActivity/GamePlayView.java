package com.work.terry.snakeonastring_jbox2d.SurfaceViewAndActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.opengl.GLES30;
import android.opengl.GLSurfaceView;
import android.util.Log;
import android.view.MotionEvent;

import com.work.terry.snakeonastring_jbox2d.SnakeElements.SnakeSkin;
import com.work.terry.snakeonastring_jbox2d.SnakeElements.SnakeSkinManager;
import com.work.terry.snakeonastring_jbox2d.UI.TransitionAnimation;
import com.work.terry.snakeonastring_jbox2d.Util.Constant;
import com.work.terry.snakeonastring_jbox2d.Util.DrawUtil;
import com.work.terry.snakeonastring_jbox2d.Util.ImgManager;
import com.work.terry.snakeonastring_jbox2d.Util.LoadGameUtil;
import com.work.terry.snakeonastring_jbox2d.Util.MatrixState;
import com.work.terry.snakeonastring_jbox2d.Util.SoundPoolManager;
import com.work.terry.snakeonastring_jbox2d.Util.TexDrawer;
import com.work.terry.snakeonastring_jbox2d.Util.TexManager;
import com.work.terry.snakeonastring_jbox2d.auto.*;


import java.util.HashSet;
import java.util.Set;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import work.terry.com.snakeonastring_jbox2d.R;

import static com.work.terry.snakeonastring_jbox2d.Util.Constant.*;

/**
 * Created by Terry on 2017/12/23.
 */

public class GamePlayView extends GLSurfaceView {
    private DrawUtil gamePlayViewDrawUtil;
    private MainActivity context;
    public int yellowStars;
    public int maxScore;
    public int musicMode;
    public int SnakeSkinNumber = 0;
    public int nowViewIndex = 0;
    public Set<String> ObtainedSkins;
    public MyView nowView = null;
    public MyMenu nowMenu = null;
    private SceneRenderer sceneRenderer;
    SharedPreferences sp;

    public GamePlayView(MainActivity context){
        super(context);
        this.context = context;
        this.setEGLContextClientVersion(3);
        this.gamePlayViewDrawUtil = new DrawUtil(null);
        loadPlayerInfo();
        sceneRenderer = new SceneRenderer();
        setRenderer(sceneRenderer);
        setRenderMode(GLSurfaceView.RENDERMODE_CONTINUOUSLY);
    }
    public void savePlayerInfo(){
        SharedPreferences.Editor editor = sp.edit();
        editor.putInt("nowSelectSkinNumber",SnakeSkinNumber);
        editor.putStringSet("ObtainedSkins",ObtainedSkins);
        editor.putInt("yellowStars",yellowStars);
        editor.putInt("maxScore",maxScore);
        editor.putInt("musicMode",musicMode);
        editor.commit();
    }
    public void loadPlayerInfo(){
        if(sp==null) {
            sp = context.getSharedPreferences("userInfo", Context.MODE_PRIVATE);
        }
        SnakeSkinNumber = sp.getInt("nowSelectSkinNumber",SnakeSkinManager.SKIN_DEFAULT);
        ObtainedSkins = sp.getStringSet("ObtainedSkins",null);
        if(ObtainedSkins==null){
            ObtainedSkins = new HashSet<>();
            ObtainedSkins.add(SnakeSkinManager.SKIN_DEFAULT+"");
        }
        yellowStars = sp.getInt("yellowStars",0);
        maxScore = sp.getInt("maxScore",0);
        int musicMode = sp.getInt("musicMode",Constant.MUSIC_MODE_ALL_ON);
        setMusicMode(musicMode);
    }
    @Override
    public boolean onTouchEvent(MotionEvent event){
        int x = (int)event.getX();
        int y = (int)event.getY();
        int[]  xy = ScreenScaleUtil.touchFromTargetToOrigin(x,y, Constant.ssr);
        x = xy[0];
        y = xy[1];

        if(nowMenu!=null&&nowMenu.testTouch(x,y)){
            nowMenu.onTouchEvent(event,x,y);
        }else if(nowView!=null)
            nowView.onTouchEvent(event,x,y);
        return true;
    }
    public int getMusicMode(){
        return musicMode;
    }
    private void setMusicMode(int x){
        this.musicMode = x%3;
    }
    public void changeMusicMode(){
        setMusicMode(++musicMode);
        changeMusicVolumeAccordingToMusicMode();
    }
    public void changeMusicVolumeAccordingToMusicMode(){
        switch (musicMode){
            case Constant.MUSIC_MODE_ALL_ON:
                SoundPoolManager.setVolume(1);
                context.setBackgroundMusicVolume(1);
                break;
            case Constant.MUSIC_MODE_BG_OFF:
                SoundPoolManager.setVolume(1);
                context.setBackgroundMusicVolume(0);
                break;
            case Constant.MUSIC_MODE_ALL_OFF:
                SoundPoolManager.setVolume(0);
                context.setBackgroundMusicVolume(0);
                break;
        }
    }
    public void setNowViewIndex(int v){
        this.nowViewIndex = v;
    }
    public int getNowViewIndex(){
        return nowViewIndex;
    }
    public void setNowView(int index){
        if(index == CHANGE_BY_DEFAULT_VIEW){
            index = nowViewIndex;
        }else {
            nowViewIndex = index;
        }

        if(nowView!=null){
            nowView.onPause(null);
            nowView.onSwitchViewAndStop();
        }
        if(index == START_VIEW){
            context.setBackgroundMusic(R.raw.back_ground_music);
            nowView = new StartView(this,null);
        }else if(index > GAMEPLAY_VIEW_ORIGINAL&&index <GAMEPLAY_VIEW_ORIGINAL+10){
            context.setBackgroundMusic(R.raw.back_ground_music_magic_trunck);
            nowView = new LoadGameUtil().loadGameFromFile(OriginalPlayDirectoryPrefix+index%10+".gl",this,getSnakeSkinNumber());
        }else if(index > GAMEPLAY_VIEW_ENDLESS&&index <GAMEPLAY_VIEW_ENDLESS+10){
            context.setBackgroundMusic(R.raw.back_ground_music_magic_trunck);
            nowView = new LoadGameUtil().loadGameFromFile(EndlessPlayDirectoryPrefix+index%10+".gl",this,getSnakeSkinNumber());
        }else if(index==SKIN_CHANGING_VIEW){
            if(nowView instanceof StartView){
                nowView = new SkinChangingView(this,null);
            }else if(nowView instanceof GamePlay){
                int gameModeAndLevel = ((GamePlay)nowView).gameModeAndLevel;
                nowView = new SkinChangingView(this,null,gameModeAndLevel);
            }
        }
        new TransitionAnimation(gamePlayViewDrawUtil);
        changeMusicVolumeAccordingToMusicMode();
    }
    public int getSnakeSkinNumber(){
        return SnakeSkinNumber;
    }
    public void setSnakeSkinNumber(int x){
        this.SnakeSkinNumber = x;
    }
    public int getNowView(){
        return nowViewIndex;
    }
    public void setNowMenu(MyMenu menu){
        if(nowMenu!=null){
            this.nowMenu.setDoDraw(false);
            gamePlayViewDrawUtil.addToRemoveSequence(nowMenu);
        }
        this.nowMenu = menu;
        if(menu!=null){
            gamePlayViewDrawUtil.addToCenterLayer(nowMenu);
        }
    }

    private  class  SceneRenderer implements Renderer {

        TexDrawer texDrawer;

        @Override
        public void onDrawFrame(GL10 gl){
            GLES30.glClear(GLES30.GL_DEPTH_BUFFER_BIT|GLES30.GL_COLOR_BUFFER_BIT);

            nowView.getDrawUtil().stepDraw(texDrawer);
            gamePlayViewDrawUtil.stepDraw(texDrawer);
            //nowMenuStepDraw(texDrawer);
        }
        public void nowMenuStepDraw(TexDrawer painter){
            if(nowMenu!=null){
                if(nowMenu.doDraw==false)nowMenu=null;
                else {
                    try {
                        nowMenu.drawFloorShadow(painter);
                        nowMenu.drawHeight(painter);
                        nowMenu.drawSelf(painter);
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }
            }
        }
        @Override
        public void onSurfaceChanged(GL10 gl, int width,int height){
            GLES30.glViewport(Constant.ssr.lucX,Constant.ssr.lucY,Constant.ssr.skWidth,Constant.ssr.skHeight);
            MatrixState.setProjectOrtho(
                    -Constant.ssr.vpRatio,
                    Constant.ssr.vpRatio,
                    -1,1,
                    1,10
            );

            MatrixState.setCamera(0,0,3,0f,0f,0f,0f,1.0f,0f);
            MatrixState.setInitStack();
        }
        @Override
        public void onSurfaceCreated(GL10 gl, EGLConfig config){
            GLES30.glClearColor(0,1,1,1.0f);
            texDrawer = new TexDrawer(GamePlayView.this);
            GLES30.glDisable(GLES30.GL_DEPTH_TEST);

            TexManager.addTexArray(ImgManager.picName);
            TexManager.loadTextures(GamePlayView.this.getResources());
            SnakeSkinManager.initSkinsFromFileSystem(GamePlayView.this.getResources());

            GLES30.glDisable(GLES30.GL_CULL_FACE);

            setNowViewIndex(START_VIEW);
            setNowView(START_VIEW);
        }
    }
    public void onResume(){
        super.onResume();
    }
    public void onPause(SharedPreferences.Editor editor){
        savePlayerInfo();
        if(nowView!=null)nowView.onResume();
    }
}
