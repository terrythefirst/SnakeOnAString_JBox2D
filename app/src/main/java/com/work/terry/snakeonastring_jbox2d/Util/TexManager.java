package com.work.terry.snakeonastring_jbox2d.Util;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.opengl.GLES20;
import android.opengl.GLES30;
import android.opengl.GLUtils;
import android.util.Log;

import com.work.terry.snakeonastring_jbox2d.SnakeElements.SnakeNodeSkinInfo;
import com.work.terry.snakeonastring_jbox2d.SnakeElements.SnakeSkin;
import com.work.terry.snakeonastring_jbox2d.SnakeElements.SnakeSkinManager;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by Terry on 2017/12/25.
 */

public class TexManager {

    private static Map<String,Integer> tex = new HashMap<String,Integer>();
    private static List<String> texName = new ArrayList<String>();

    private static int initTexture(Resources r,String texFileName){
        int[] textures = new int[1];
        GLES30.glGenTextures(
                1,
                textures,
                0
        );

        int textureID = textures[0];
        GLES30.glBindTexture(GLES30.GL_TEXTURE_2D,textureID);
        GLES30.glTexParameterf(GLES30.GL_TEXTURE_2D,GLES30.GL_TEXTURE_MIN_FILTER,GLES30.GL_NEAREST);
        GLES30.glTexParameterf(GLES30.GL_TEXTURE_2D,GLES20.GL_TEXTURE_MAG_FILTER,GLES30.GL_LINEAR);
        GLES30.glTexParameterf(GLES30.GL_TEXTURE_2D,GLES30.GL_TEXTURE_WRAP_R,GLES30.GL_CLAMP_TO_EDGE);
        GLES30.glTexParameterf(GLES30.GL_TEXTURE_2D,GLES30.GL_TEXTURE_WRAP_T,GLES30.GL_CLAMP_TO_EDGE);

        InputStream is = null;
        Bitmap bitmap = null;
        try{
            is = r.getAssets().open(texFileName);
            bitmap = BitmapFactory.decodeStream(is);
        }catch (Exception e){
            Log.e("loadTex","PicName: "+texFileName);
            e.printStackTrace();
        }finally {
            try{
                is.close();
            }catch (IOException ee){
                ee.printStackTrace();
            }
        }

        GLUtils.texImage2D(
                GLES30.GL_TEXTURE_2D,
                0,
                bitmap,
                0
        );
        bitmap.recycle();

        return textureID;
    }

    public static void addTex(String tn){texName.add(tn);}

    public static void addTexArray(String[] tna){
        for(String tn:tna){
            texName.add(tn);
        }
    }

    public static void loadTextures(Resources r){
        for(String tn:texName){
            tex.put(tn,initTexture(r,Constant.PicDirectoryPrefix+tn));
        }
    }
    public static void loadSkinTextures(Resources r){
        Set<Integer> set = SnakeSkinManager.skinMap.keySet();
        for (Integer i:set){
            Map<Integer,SnakeNodeSkinInfo> skinInfo = SnakeSkinManager.skinMap.get(i).getSkinInfo();
            Set<Integer> keyset = skinInfo.keySet();
            for (Integer k:keyset){
                String snakeSkinPicName = skinInfo.get(k).getImg();
                if(snakeSkinPicName==null||snakeSkinPicName.equals("null"))continue;
                tex.put(snakeSkinPicName,initTexture(r,Constant.SnakeSkinPicDirectoryPrefix+snakeSkinPicName));
            }
        }
    }
    public static int getTex(String tn){
        try{
            return tex.get(tn);
        }catch (Exception e){
            throw new RuntimeException("wrongPicDirectory:"+tn);
        }
    }

}
