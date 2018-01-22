package com.work.terry.snakeonastring_jbox2d;

import android.content.Context;
import android.opengl.GLES30;
import android.opengl.GLSurfaceView;
import android.util.Log;
import android.view.MotionEvent;

import com.work.terry.snakeonastring_jbox2d.JBox2DElements.CircleBody;
import com.work.terry.snakeonastring_jbox2d.JBox2DElements.MyBody;
import com.work.terry.snakeonastring_jbox2d.JBox2DElements.MyContactFilter;
import com.work.terry.snakeonastring_jbox2d.JBox2DElements.MyContactListener;
import com.work.terry.snakeonastring_jbox2d.JBox2DElements.RectBody;
import com.work.terry.snakeonastring_jbox2d.Thread.JBox2DThread;
import com.work.terry.snakeonastring_jbox2d.Util.ColorManager;
import com.work.terry.snakeonastring_jbox2d.Util.Constant;
import com.work.terry.snakeonastring_jbox2d.Util.ImgManager;
import com.work.terry.snakeonastring_jbox2d.Util.JBox2DUtil;
import com.work.terry.snakeonastring_jbox2d.Util.MatrixState;
import com.work.terry.snakeonastring_jbox2d.Util.TexDrawer;
import com.work.terry.snakeonastring_jbox2d.Util.TexManager;
import com.work.terry.snakeonastring_jbox2d.auto.*;


import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.World;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import static com.work.terry.snakeonastring_jbox2d.Util.Constant.*;

/**
 * Created by Terry on 2017/12/23.
 */

public class GamePlayView extends GLSurfaceView {
    public boolean IS_PLAYING = true;
    public World world;
    public JBox2DThread jBox2DThread = null;
    private SceneRenderer sceneRenderer;


    public Snake snake ;

    public GamePlayView(Context context){
        super(context);
        this.setEGLContextClientVersion(3);

        sceneRenderer = new SceneRenderer();
        setRenderer(sceneRenderer);
        setRenderMode(GLSurfaceView.RENDERMODE_CONTINUOUSLY);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event){
        if(snake == null)return true;
        int x = (int)event.getX();
        int y = (int)event.getY();
        int[]  xy = ScreenScaleUtil.touchFromTargetToOrigin(x,y, Constant.ssr);
        x = xy[0];
        y = xy[1];
        //Log.d("touchMotion","x="+x+" y="+y);

        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                snake.whenMotionDown(x,y);
                break;
            case MotionEvent.ACTION_MOVE:
                snake.whenMotionDown(x,y);
                break;
            case MotionEvent.ACTION_UP:
                break;
        }

        return true;
    }

    private  class  SceneRenderer implements Renderer {

        TexDrawer texDrawer;

        @Override
        public void onDrawFrame(GL10 gl){
            GLES30.glClear(GLES30.GL_DEPTH_BUFFER_BIT|GLES30.GL_COLOR_BUFFER_BIT);


            //snake.setColor(Constant.COLOR_DEFAULT);
            texDrawer.drawSelf(TexManager.getTex(Constant.backgroundImg), ColorManager.getColor(Constant.COLOR_DEFAULT),Constant.SCREEN_WIDTH/2,Constant.SCREEN_HEIGHT/2,Constant.SCREEN_WIDTH,Constant.SCREEN_HEIGHT,0);
            snake.drawSelf(texDrawer);
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

            Vec2 gravity = new Vec2(0,0);
            world = new World(gravity);
            constructWalls();

            snake = new Snake(world);
            jBox2DThread = new JBox2DThread(GamePlayView.this);
            world.setContactFilter(new MyContactFilter(GamePlayView.this));
            MyContactListener myContactListener = new MyContactListener(GamePlayView.this);
            world.setContactListener(myContactListener);

            jBox2DThread.start();
            snake.moving();
        }
    }
    public void constructWalls(){
        RectBody upWall = new RectBody(
                world,
                "upWall",
                SCREEN_WIDTH/2,-8,
                0,
                0,0,
                SCREEN_WIDTH/2,5,
                10,0.1f,0.1f,
                "",
                true
        );
        RectBody leftWall = new RectBody(
                world,
                "leftWall",
                -8,SCREEN_HEIGHT/2,
                0,
                0,0,
                5,SCREEN_HEIGHT/2,
                10,0.1f,0.1f,
                "",
                true
        );
        RectBody rightWall = new RectBody(
                world,
                "rightWall",
                SCREEN_WIDTH+8,SCREEN_HEIGHT/2,
                0,
                0,0,
                5,SCREEN_HEIGHT/2,
                10,0.1f,0.1f,
                "",
                true
        );
        RectBody buttomWall = new RectBody(
                world,
                "buttomWall",
                SCREEN_WIDTH/2,SCREEN_HEIGHT+8,
                0,
                0,0,
                SCREEN_WIDTH/2,5,
                10,0.1f,0.1f,
                "",
                true
        );
    }
    @Override
    public void onResume(){
        super.onResume();
        if(snake!=null)snake.onResume();
    }

    @Override
    public void onPause(){
        super.onPause();
        if(snake!=null)snake.onPause();
    }
}
