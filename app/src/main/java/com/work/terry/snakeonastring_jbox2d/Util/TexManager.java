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
        return BnETC2Util.initTextureETC2(texFileName,r);
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
