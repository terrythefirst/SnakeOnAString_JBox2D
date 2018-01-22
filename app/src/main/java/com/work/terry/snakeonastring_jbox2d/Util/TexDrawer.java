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
    int muColorHandle;
    String mVertexShader;
    String mFragmentShader;

    int mProgramColor;
    int muMVPMatrixHandleColor;
    int maPositionHandleColor;
    int maTexCoorHandleColor;
    int muColorHandleColor;
    String mVertexShaderColor;
    String mFragmentShaderColor;

    int mProgramShadow;
    int muMVPMatrixHandleShadow;
    int maPositionHandleShadow;
    int maTexCoorHandleShadow;
    int muDownColorFactorShadow;//向下颜色改变因子
    int muColorHandleShadow;
    String mVertexShaderShadow;
    String mFragmentShaderShadow;


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
        muColorHandle = GLES30.glGetUniformLocation(mProgram,"uColor");
        muMVPMatrixHandle = GLES30.glGetUniformLocation(mProgram,"uMVPMatrix");

        mVertexShaderColor = ShaderUtil.loadFromAssetsFile("sh/vertexColor.sh",view.getResources());
        mFragmentShaderColor = ShaderUtil.loadFromAssetsFile("sh/fragColor.sh",view.getResources());
        mProgramColor = ShaderUtil.createProgram(mVertexShaderColor,mFragmentShaderColor);
        maPositionHandleColor = GLES30.glGetAttribLocation(mProgramColor,"aPosition");
        maTexCoorHandleColor = GLES30.glGetAttribLocation(mProgramColor,"aTexCoor");
        muColorHandleColor = GLES30.glGetUniformLocation(mProgramColor,"uColor");
        muMVPMatrixHandleColor = GLES30.glGetUniformLocation(mProgramColor,"uMVPMatrix");

        mVertexShaderShadow = ShaderUtil.loadFromAssetsFile("sh/vertexShadow.sh",view.getResources());
        mFragmentShaderShadow = ShaderUtil.loadFromAssetsFile("sh/fragShadow.sh",view.getResources());
        mProgramShadow = ShaderUtil.createProgram(mVertexShaderShadow,mFragmentShaderShadow);
        maPositionHandleShadow = GLES30.glGetAttribLocation(mProgramShadow,"aPosition");
        maTexCoorHandleShadow = GLES30.glGetAttribLocation(mProgramShadow,"aTexCoor");
        muDownColorFactorShadow = GLES30.glGetUniformLocation(mProgramShadow,"uDownFactor");
        muColorHandleShadow = GLES30.glGetUniformLocation(mProgramShadow,"uColor");
        muMVPMatrixHandleShadow = GLES30.glGetUniformLocation(mProgramShadow,"uMVPMatrix");
    }

/*
* 注意：x和y 是物体的中心  旋转中心
* 方便旋转和对齐
* */

    public void drawSelf(int texId,float[] color,float x,float y,float width, float height,float rotateAngle){
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
        GLES30.glUniform3fv(muColorHandle,1,color,0);
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
    public void drawColorSelf(int texId,float[] color,float x,float y,float width, float height,float rotateAngle){
        GLES30.glEnable(GLES30.GL_BLEND);
        GLES30.glBlendFunc(GLES30.GL_SRC_ALPHA,GLES30.GL_ONE_MINUS_SRC_ALPHA);

        GLES30.glUseProgram(mProgramColor);

        float wSacle = ScreenScaleUtil.fromPixSizeToScreenSize(width,Constant.ssr);
        float hScale = ScreenScaleUtil.fromPixSizeToScreenSize(height,Constant.ssr);
        float xDraw = ScreenScaleUtil.from2DZBTo3DZBX(x,Constant.ssr);
        float yDraw = ScreenScaleUtil.from2DZBTo3DZBY(y,Constant.ssr);

        MatrixState.pushMatrix();

        MatrixState.translate(xDraw,yDraw,0);
        MatrixState.scale(wSacle,hScale,1.0f);
        MatrixState.rotate(rotateAngle,0,0,1);

        GLES30.glUniformMatrix4fv(muMVPMatrixHandleColor,1,false,MatrixState.getFinalMatrix(),0);
        GLES30.glUniform3fv(muColorHandleColor,1,color,0);
        GLES30.glVertexAttribPointer(
                maPositionHandleColor,
                3,
                GLES30.GL_FLOAT,
                false,
                3*4,
                mVertexBuffer
        );
        GLES30.glVertexAttribPointer(
                maTexCoorHandleColor,
                2,
                GLES30.GL_FLOAT,
                false,
                2*4,
                mTexCoorBuffer
        );
        GLES30.glEnableVertexAttribArray(maPositionHandleColor);
        GLES30.glEnableVertexAttribArray(maTexCoorHandleColor);

        GLES30.glActiveTexture(GLES30.GL_TEXTURE0);
        GLES30.glBindTexture(GLES30.GL_TEXTURE_2D,texId);

        GLES30.glDrawArrays(GLES30.GL_TRIANGLES,0,vCount);

        MatrixState.popMatrix();

        GLES30.glDisable(GLES30.GL_BLEND);
    }
    public void drawFloorShadow(int texId,float[] color,float x,float y,float width, float height,float rotateAngle,float downOffsetX,float downOffsetY,float colorFactor){
        GLES30.glEnable(GLES30.GL_BLEND);
        GLES30.glBlendFunc(GLES30.GL_SRC_COLOR,GLES30.GL_ONE_MINUS_SRC_COLOR);

        x+=downOffsetX;
        y+=downOffsetY;
        GLES30.glUseProgram(mProgramShadow);

        float wSacle = ScreenScaleUtil.fromPixSizeToScreenSize(width,Constant.ssr);
        float hScale = ScreenScaleUtil.fromPixSizeToScreenSize(height,Constant.ssr);
        float xDraw = ScreenScaleUtil.from2DZBTo3DZBX(x,Constant.ssr);
        float yDraw = ScreenScaleUtil.from2DZBTo3DZBY(y,Constant.ssr);

        MatrixState.pushMatrix();

        MatrixState.translate(xDraw,yDraw,0);
        MatrixState.scale(wSacle,hScale,1.0f);
        MatrixState.rotate(rotateAngle,0,0,1);

        GLES30.glUniformMatrix4fv(muMVPMatrixHandleShadow,1,false,MatrixState.getFinalMatrix(),0);
        GLES30.glUniform1f(muDownColorFactorShadow,colorFactor);
        GLES30.glUniform3fv(muColorHandle,1,color,0);
        GLES30.glVertexAttribPointer(
                maPositionHandleShadow,
                3,
                GLES30.GL_FLOAT,
                false,
                3*4,
                mVertexBuffer
        );
        GLES30.glVertexAttribPointer(
                maTexCoorHandleShadow,
                2,
                GLES30.GL_FLOAT,
                false,
                2*4,
                mTexCoorBuffer
        );
        GLES30.glEnableVertexAttribArray(maPositionHandleShadow);
        GLES30.glEnableVertexAttribArray(maTexCoorHandleShadow);

        GLES30.glActiveTexture(GLES30.GL_TEXTURE0);
        GLES30.glBindTexture(GLES30.GL_TEXTURE_2D,texId);

        GLES30.glDrawArrays(GLES30.GL_TRIANGLES,0,vCount);

        MatrixState.popMatrix();

        GLES30.glDisable(GLES30.GL_BLEND);
    }
    public void drawDownShadow(int texId,float[] color,float x,float y,float width, float height,float rotateAngle,float downOffsetX,float downOffsetY,float colorFactor){
        GLES30.glEnable(GLES30.GL_BLEND);
        GLES30.glBlendFunc(GLES30.GL_SRC_ALPHA,GLES30.GL_ONE_MINUS_SRC_ALPHA);

        x+=downOffsetX;
        y+=downOffsetY;
        GLES30.glUseProgram(mProgramShadow);

        float wSacle = ScreenScaleUtil.fromPixSizeToScreenSize(width,Constant.ssr);
        float hScale = ScreenScaleUtil.fromPixSizeToScreenSize(height,Constant.ssr);
        float xDraw = ScreenScaleUtil.from2DZBTo3DZBX(x,Constant.ssr);
        float yDraw = ScreenScaleUtil.from2DZBTo3DZBY(y,Constant.ssr);

        MatrixState.pushMatrix();

        MatrixState.translate(xDraw,yDraw,0);
        MatrixState.scale(wSacle,hScale,1.0f);
        MatrixState.rotate(rotateAngle,0,0,1);

        GLES30.glUniformMatrix4fv(muMVPMatrixHandleShadow,1,false,MatrixState.getFinalMatrix(),0);
        GLES30.glUniform1f(muDownColorFactorShadow,colorFactor);
        GLES30.glUniform3fv(muColorHandle,1,color,0);
        GLES30.glVertexAttribPointer(
                maPositionHandleShadow,
                3,
                GLES30.GL_FLOAT,
                false,
                3*4,
                mVertexBuffer
        );
        GLES30.glVertexAttribPointer(
                maTexCoorHandleShadow,
                2,
                GLES30.GL_FLOAT,
                false,
                2*4,
                mTexCoorBuffer
        );
        GLES30.glEnableVertexAttribArray(maPositionHandleShadow);
        GLES30.glEnableVertexAttribArray(maTexCoorHandleShadow);

        GLES30.glActiveTexture(GLES30.GL_TEXTURE0);
        GLES30.glBindTexture(GLES30.GL_TEXTURE_2D,texId);

        GLES30.glDrawArrays(GLES30.GL_TRIANGLES,0,vCount);

        MatrixState.popMatrix();

        GLES30.glDisable(GLES30.GL_BLEND);
    }
}
