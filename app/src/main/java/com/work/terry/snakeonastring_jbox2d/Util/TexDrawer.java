package com.work.terry.snakeonastring_jbox2d.Util;

import android.opengl.GLES30;
import android.opengl.GLSurfaceView;

import com.work.terry.snakeonastring_jbox2d.SurfaceViewAndActivity.GamePlayView;
import com.work.terry.snakeonastring_jbox2d.auto.ScreenScaleUtil;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

/**
 * Created by Terry on 2017/12/23.
 */

public class TexDrawer {

    int mProgram;
    int muMVPMatrixHandle;
    int maPositionHandle;
    int maTexCoorHandle;
    String mVertexShader;
    String mFragmentShader;

    int mProgramColorOpacity;
    int muMVPMatrixHandleColorOpacity;
    int maPositionHandleColorOpacity;
    int maTexCoorHandleColorOpacity;
    int muDownColorFactorColorOpacity;//向下颜色改变因子
    int muOpacityFactor;//透明度改变因子
    int muColorHandleColorOpacity;
    String mVertexShaderColorOpacity;
    String mFragmentShaderColorOpacity;


    FloatBuffer mVertexBuffer;
    FloatBuffer mTexCoorBuffer;
    int vCount = 0;

    public TexDrawer(GamePlayView view){
        initVertexData();
        initShader(view);
    }
    public void initVertexData(){
        vCount = 6;
        float[] vertices = new float[]{
//                0,0,0, 0,-1,0, 1,-1,0,
//                0,0,0, 1,-1,0, 1,0,0
                -0.5f,0.5f,0, -0.5f,-0.5f,0, 0.5f,-0.5f,0,
                -0.5f,0.5f,0, 0.5f,-0.5f,0, 0.5f,0.5f,0
        };

        ByteBuffer vbb = ByteBuffer.allocateDirect(vertices.length*4);
        vbb.order(ByteOrder.nativeOrder());
        mVertexBuffer = vbb.asFloatBuffer();
        mVertexBuffer.put(vertices);
        mVertexBuffer.position(0);

        float texCoor[] = new float[]{
                0,0, 0,1, 1,1,
                0,0, 1,1, 1,0
        };
        ByteBuffer cbb = ByteBuffer.allocateDirect(texCoor.length*4);
        cbb.order(ByteOrder.nativeOrder());
        mTexCoorBuffer = cbb.asFloatBuffer();
        mTexCoorBuffer.put(texCoor);
        mTexCoorBuffer.position(0);
    }
    public void initShader(GLSurfaceView view){
        mVertexShader = ShaderUtil.loadFromAssetsFile("sh/vertex.sh",view.getResources());
        mFragmentShader = ShaderUtil.loadFromAssetsFile("sh/frag.sh",view.getResources());
        mProgram = ShaderUtil.createProgram(mVertexShader,mFragmentShader);
        maPositionHandle = GLES30.glGetAttribLocation(mProgram,"aPosition");
        maTexCoorHandle = GLES30.glGetAttribLocation(mProgram,"aTexCoor");
        muMVPMatrixHandle = GLES30.glGetUniformLocation(mProgram,"uMVPMatrix");


        mVertexShaderColorOpacity = ShaderUtil.loadFromAssetsFile("sh/vertex.sh",view.getResources());
        mFragmentShaderColorOpacity = ShaderUtil.loadFromAssetsFile("sh/fragOpacityColor.sh",view.getResources());
        mProgramColorOpacity = ShaderUtil.createProgram(mVertexShaderColorOpacity,mFragmentShaderColorOpacity);
        maPositionHandleColorOpacity = GLES30.glGetAttribLocation(mProgramColorOpacity,"aPosition");
        maTexCoorHandleColorOpacity = GLES30.glGetAttribLocation(mProgramColorOpacity,"aTexCoor");
        muDownColorFactorColorOpacity = GLES30.glGetUniformLocation(mProgramColorOpacity,"uDownFactor");
        muOpacityFactor = GLES30.glGetUniformLocation(mProgramColorOpacity,"uOpacityFactor");
        muColorHandleColorOpacity = GLES30.glGetUniformLocation(mProgramColorOpacity,"uColor");
        muMVPMatrixHandleColorOpacity = GLES30.glGetUniformLocation(mProgramColorOpacity,"uMVPMatrix");
    }

/*
* 注意：x和y 是物体的中心  旋转中心
* 方便旋转和对齐
* */

    public void drawTex(int texId,float x,float y,float width, float height,float rotateAngle){
        GLES30.glEnable(GLES30.GL_BLEND);
        GLES30.glBlendFunc(GLES30.GL_SRC_ALPHA,GLES30.GL_ONE_MINUS_SRC_ALPHA);

        GLES30.glUseProgram(mProgram);

        float wSacle = ScreenScaleUtil.fromPixSizeToScreenSize(width, Constant.ssr);
        float hScale = ScreenScaleUtil.fromPixSizeToScreenSize(height,Constant.ssr);
        float xDraw = ScreenScaleUtil.from2DZBTo3DZBX(x,Constant.ssr);
        float yDraw = ScreenScaleUtil.from2DZBTo3DZBY(y,Constant.ssr);

        MatrixState.pushMatrix();

        MatrixState.translate(xDraw,yDraw,0);
        MatrixState.scale(wSacle,hScale,1.0f);
        MatrixState.rotate(rotateAngle,0,0,1);

        GLES30.glUniformMatrix4fv(muMVPMatrixHandle,1,false,MatrixState.getFinalMatrix(),0);
        GLES30.glVertexAttribPointer(
                maPositionHandle,
                3,
                GLES30.GL_FLOAT,
                false,
                3*4,
                mVertexBuffer
        );
        GLES30.glVertexAttribPointer(
                maTexCoorHandle,
                2,
                GLES30.GL_FLOAT,
                false,
                2*4,
                mTexCoorBuffer
        );
        GLES30.glEnableVertexAttribArray(maPositionHandle);
        GLES30.glEnableVertexAttribArray(maTexCoorHandle);

        GLES30.glActiveTexture(GLES30.GL_TEXTURE0);
        GLES30.glBindTexture(GLES30.GL_TEXTURE_2D,texId);

        GLES30.glDrawArrays(GLES30.GL_TRIANGLES,0,vCount);

        MatrixState.popMatrix();

        GLES30.glDisable(GLES30.GL_BLEND);
    }
    public void drawShadow(int texId,float[] color,float x,float y,float width, float height,float rotateAngle,float colorFactor){
        GLES30.glEnable(GLES30.GL_BLEND);
        GLES30.glBlendFunc(GLES30.GL_SRC_COLOR,GLES30.GL_ONE_MINUS_SRC_COLOR);
        GLES30.glUseProgram(mProgramColorOpacity);

        float wSacle = ScreenScaleUtil.fromPixSizeToScreenSize(width,Constant.ssr);
        float hScale = ScreenScaleUtil.fromPixSizeToScreenSize(height,Constant.ssr);
        float xDraw = ScreenScaleUtil.from2DZBTo3DZBX(x,Constant.ssr);
        float yDraw = ScreenScaleUtil.from2DZBTo3DZBY(y,Constant.ssr);

        MatrixState.pushMatrix();

        MatrixState.translate(xDraw,yDraw,0);
        MatrixState.rotate(rotateAngle,0,0,1);
        MatrixState.scale(wSacle,hScale,1.0f);


        GLES30.glUniformMatrix4fv(muMVPMatrixHandleColorOpacity,1,false,MatrixState.getFinalMatrix(),0);
        GLES30.glUniform1f(muDownColorFactorColorOpacity,colorFactor);
        GLES30.glUniform1f(muOpacityFactor,1);

        GLES30.glUniform3fv(muColorHandleColorOpacity,1,color,0);
        GLES30.glVertexAttribPointer(
                maPositionHandleColorOpacity,
                3,
                GLES30.GL_FLOAT,
                false,
                3*4,
                mVertexBuffer
        );
        GLES30.glVertexAttribPointer(
                maTexCoorHandleColorOpacity,
                2,
                GLES30.GL_FLOAT,
                false,
                2*4,
                mTexCoorBuffer
        );
        GLES30.glEnableVertexAttribArray(maPositionHandleColorOpacity);
        GLES30.glEnableVertexAttribArray(maTexCoorHandleColorOpacity);

        GLES30.glActiveTexture(GLES30.GL_TEXTURE0);
        GLES30.glBindTexture(GLES30.GL_TEXTURE_2D,texId);

        GLES30.glDrawArrays(GLES30.GL_TRIANGLES,0,vCount);

        MatrixState.popMatrix();

        GLES30.glDisable(GLES30.GL_BLEND);
    }
    public void drawColorOpacityFactorTex(int texId, float[] color, float x, float y, float width, float height, float rotateAngle, float colorFactor, float opacityFactor){
        GLES30.glEnable(GLES30.GL_BLEND);
        GLES30.glBlendFunc(GLES30.GL_SRC_ALPHA,GLES30.GL_ONE_MINUS_SRC_ALPHA);

        GLES30.glUseProgram(mProgramColorOpacity);

        float wSacle = ScreenScaleUtil.fromPixSizeToScreenSize(width,Constant.ssr);
        float hScale = ScreenScaleUtil.fromPixSizeToScreenSize(height,Constant.ssr);
        float xDraw = ScreenScaleUtil.from2DZBTo3DZBX(x,Constant.ssr);
        float yDraw = ScreenScaleUtil.from2DZBTo3DZBY(y,Constant.ssr);

        MatrixState.pushMatrix();

        MatrixState.translate(xDraw,yDraw,0);
        MatrixState.rotate(rotateAngle,0,0,1);
        MatrixState.scale(wSacle,hScale,1.0f);


        GLES30.glUniformMatrix4fv(muMVPMatrixHandleColorOpacity,1,false,MatrixState.getFinalMatrix(),0);
        GLES30.glUniform1f(muDownColorFactorColorOpacity,colorFactor);
        GLES30.glUniform1f(muOpacityFactor,opacityFactor);

        GLES30.glUniform3fv(muColorHandleColorOpacity,1,color,0);
        GLES30.glVertexAttribPointer(
                maPositionHandleColorOpacity,
                3,
                GLES30.GL_FLOAT,
                false,
                3*4,
                mVertexBuffer
        );
        GLES30.glVertexAttribPointer(
                maTexCoorHandleColorOpacity,
                2,
                GLES30.GL_FLOAT,
                false,
                2*4,
                mTexCoorBuffer
        );
        GLES30.glEnableVertexAttribArray(maPositionHandleColorOpacity);
        GLES30.glEnableVertexAttribArray(maTexCoorHandleColorOpacity);

        GLES30.glActiveTexture(GLES30.GL_TEXTURE0);
        GLES30.glBindTexture(GLES30.GL_TEXTURE_2D,texId);

        GLES30.glDrawArrays(GLES30.GL_TRIANGLES,0,vCount);

        MatrixState.popMatrix();

        GLES30.glDisable(GLES30.GL_BLEND);
    }
}
