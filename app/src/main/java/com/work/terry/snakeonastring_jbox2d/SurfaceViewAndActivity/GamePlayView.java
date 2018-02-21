package com.work.terry.snakeonastring_jbox2d.SurfaceViewAndActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.opengl.GLES30;
import android.opengl.GLSurfaceView;
import android.view.MotionEvent;

import com.work.terry.snakeonastring_jbox2d.Util.Constant;
import com.work.terry.snakeonastring_jbox2d.Util.ImgManager;
import com.work.terry.snakeonastring_jbox2d.Util.MatrixState;
import com.work.terry.snakeonastring_jbox2d.Util.TexDrawer;
import com.work.terry.snakeonastring_jbox2d.Util.TexManager;
import com.work.terry.snakeonastring_jbox2d.auto.*;


import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import static com.work.terry.snakeonastring_jbox2d.Util.Constant.*;

/**
 * Created by Terry on 2017/12/23.
 */

public class GamePlayView extends GLSurfaceView {
    public int nowViewIndex = 0;
    public MyView nowView = null;
    public MyMenu nowMenu = null;
    private SceneRenderer sceneRenderer;

    public GamePlayView(Context context){
        super(context);
        this.setEGLContextClientVersion(3);

        sceneRenderer = new SceneRenderer();
        setRenderer(sceneRenderer);
        setRenderMode(GLSurfaceView.RENDERMODE_CONTINUOUSLY);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event){
        int x = (int)event.getX();
        int y = (int)event.getY();
        int[]  xy = ScreenScaleUtil.touchFromTargetToOrigin(x,y, Constant.ssr);
        x = xy[0];
        y = xy[1];

        nowView.onTouchEvent(event,x,y);
        return true;
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
        switch (index){
            case START_VIEW:
                nowView = new StartView(null);
                break;
            case MENU_VIEW:
                break;
            case GAMEPLAY_VIEW:
                nowView = new GamePlay();
                break;
        }
    }
    public int getNowView(){
        return nowViewIndex;
    }

    private  class  SceneRenderer implements Renderer {

        TexDrawer texDrawer;

        @Override
        public void onDrawFrame(GL10 gl){
            GLES30.glClear(GLES30.GL_DEPTH_BUFFER_BIT|GLES30.GL_COLOR_BUFFER_BIT);
            if(nowMenu!=null)nowMenu.getDrawUtil().stepDraw(texDrawer);
            nowView.getDrawUtil().stepDraw(texDrawer);
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
            GLES30.glDisable(GLES30.GL_CULL_FACE);

            setNowViewIndex(START_VIEW);
            setNowView(START_VIEW);
        }
    }
    public void onResume(){
        super.onResume();

    }
    public void onPause(SharedPreferences.Editor editor){
        if(nowView!=null)nowView.onResume();
    }

}
