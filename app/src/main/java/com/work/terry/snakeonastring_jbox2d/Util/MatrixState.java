package com.work.terry.snakeonastring_jbox2d.Util;

import android.opengl.Matrix;

/**
 * Created by Terry on 2017/12/23.
 */

public class MatrixState {
    private static float[] mProjMatrix = new float[16];
    private static float[] mVMatrix = new float[16];
    private static float[] currMatrix;
//    public static float[] lightLocation = new float[]{0,0,0};
//    public static FloatBuffer cameFB;
//    public static FloatBuffer lightPositionFB;

//    public static Stack<float[]> mStack = new Stack<float[]>();
//
//    public static void setInitStack(){
//        currMatrix = new float[16];
//        Matrix.setIdentityM(currMatrix,0);
//    }
//    public static void pushMatrix(){
//        mStack.push(currMatrix);
//    }
//    public static void popMatrix(){
//        currMatrix = mStack.pop();
//    }
    //保护变换矩阵的栈
    static float[][] mStack=new float[10][16];
    public static int stackTop=-1;

    public static void setInitStack()//获取不变换初始矩阵
    {
        currMatrix=new float[16];
        Matrix.setIdentityM(currMatrix, 0);
    }

    public static void pushMatrix()//保护变换矩阵
    {
        stackTop++;
        for(int i=0;i<16;i++)
        {
            mStack[stackTop][i]=currMatrix[i];
        }
    }

    public static void popMatrix()//恢复变换矩阵
    {
        for(int i=0;i<16;i++)
        {
            currMatrix[i]=mStack[stackTop][i];
        }
        stackTop--;
    }

    public static void translate(float x,float y,float z){
        Matrix.translateM(currMatrix,0,x,y,z);
    }
    public static void rotate(float angle,float x,float y,float z){
        Matrix.rotateM(currMatrix,0,angle,x,y,z);
    }
    public static void scale(float x,float y, float z){
        Matrix.scaleM(currMatrix,0,x,y,z);
    }

    public static void setCamera
            (
                    float cx,
                    float cy,
                    float cz,
                    float tx,
                    float ty,
                    float tz,
                    float upx,
                    float upy,
                    float upz
            ){
        Matrix.setLookAtM(
                mVMatrix,
                0,
                cx,
                cy,
                cz,
                tx,
                ty,
                tz,
                upx,
                upy,
                upz
        );
//
//        float[] cameraLocation = new float[3];
//        cameraLocation[0] = cx;
//        cameraLocation[1] = cy;
//        cameraLocation[2] = cz;
//        Log.d("camera","x:"+cx+" y:"+cy+" z:"+cz);
//        ByteBuffer llbb = ByteBuffer.allocateDirect(3*4);
//        llbb.order(ByteOrder.nativeOrder());
//        cameFB = llbb.asFloatBuffer();
//        cameFB.put(cameraLocation);
//        cameFB.position(0);
    }
//    public static void setProjectFrustum
//            (
//                    float left,
//                    float right,
//                    float bottom,
//                    float top,
//                    float near,
//                    float far
//            ){
//        Matrix.frustumM(mProjMatrix,0,left,right,bottom,top,near,far);
//    }
    public static void setProjectOrtho
            (
                    float left,
                    float right,
                    float bottom,
                    float top,
                    float near,
                    float far
            ){
        Matrix.orthoM(mProjMatrix,0,left,right,bottom,top,near,far);
    }

    static float[] mMVPMatrix = new float[16];
    public static float[] getFinalMatrix(){

        Matrix.multiplyMM(mMVPMatrix,0,mVMatrix,0,currMatrix,0);
        Matrix.multiplyMM(mMVPMatrix,0,mProjMatrix,0,mMVPMatrix,0);
        return mMVPMatrix;
    }

//    public static float[] getMMatrix(){return currMatrix;}

//    public static void setLightLocation(float x,float y,float z){
//        lightLocation[0] = x;
//        lightLocation[1] = y;
//        lightLocation[2] = z;
//        ByteBuffer llbb = ByteBuffer.allocateDirect(3*4);
//        llbb.order(ByteOrder.nativeOrder());
//        lightPositionFB = llbb.asFloatBuffer();
//        lightPositionFB.put(lightLocation);
//        lightPositionFB.position(0);
//    }
}
