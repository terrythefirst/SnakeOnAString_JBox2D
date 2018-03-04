package com.work.terry.snakeonastring_jbox2d.SnakeElements;

import android.content.res.Resources;
import android.util.Log;

import com.work.terry.snakeonastring_jbox2d.Util.Constant;
import com.work.terry.snakeonastring_jbox2d.Util.LoadSkinUtil;
import com.work.terry.snakeonastring_jbox2d.Util.TexManager;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Terry on 2018/3/1.
 */

public class SnakeSkinManager {
    public static final int SKIN_DEFAULT = 0;
    public static final int SKIN_DRAGON = 1;

    public static Map<Integer,SnakeSkin> skinMap  = new HashMap<>();
    public static SnakeNodeSkinInfo getSkin(int SkinNumber,int nodeNumber){
        return skinMap.get(SkinNumber).getSkinNodeInfo(nodeNumber);
    }

    public static void initSkinsFromFileSystem(
            Resources r
    ){
        try {
            String[] files = r.getAssets().list("snake_skin");//Constant.SnakeSkinDirectoryPrefix);
            for (String ss:files){
                if(ss.endsWith(".sk")){
                    SnakeSkin snakeSkin = LoadSkinUtil.loadSkinFromFile(ss,r);
                    Pattern pattern = Pattern.compile("\\d+");
                    Matcher matcher = pattern.matcher(ss);
                    //Log.e("matcher.group()",matcher.group());
                    if(matcher.find())
                        skinMap.put(new Integer(matcher.group()),snakeSkin);
                    else Log.e("skinMap FILE NUMBER","not Found");
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }

        TexManager.loadSkinTextures(r);
    }
}