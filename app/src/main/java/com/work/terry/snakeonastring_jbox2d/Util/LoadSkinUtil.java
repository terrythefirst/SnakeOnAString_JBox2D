package com.work.terry.snakeonastring_jbox2d.Util;

import android.content.res.Resources;
import android.util.Log;

import com.work.terry.snakeonastring_jbox2d.SnakeElements.SnakeNodeSkinInfo;
import com.work.terry.snakeonastring_jbox2d.SnakeElements.SnakeSkin;
import com.work.terry.snakeonastring_jbox2d.SurfaceViewAndActivity.GamePlay;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Terry on 2018/3/1.
 */

public class LoadSkinUtil {
    public static SnakeSkin loadSkinFromFile
            (String file, Resources r) {
        SnakeSkin snakeSkin = null;

        String skinName = "";
        float speed = 0.2f;
        float luck = 0.2f;
        int price = 0;
        Map<Integer,SnakeNodeSkinInfo> skinInfo = new HashMap<>();

        try {
            InputStream in = r.getAssets().open(Constant.SnakeSkinDirectoryPrefix+file);
            InputStreamReader isr = new InputStreamReader(in);
            BufferedReader br = new BufferedReader(isr);
            String temps;

            String[] firstLineStrings = br.readLine().split("[ ]+");
            skinName = firstLineStrings[0];
            speed = Float.parseFloat(firstLineStrings[1]);
            luck = Float.parseFloat(firstLineStrings[2]);
            price = Integer.parseInt(firstLineStrings[3]);
            Log.d("loadSnakeSkinFromFile",skinName+" start");

            while ((temps=br.readLine())!=null){
                String[] tempsp = temps.split("[ ]+");//引号
                Integer number = new Integer(tempsp[0]);

                String[] colorStrings = tempsp[1].split("[,]+");
                float[] color = new float[]{Float.parseFloat(colorStrings[0]),Float.parseFloat(colorStrings[1]),Float.parseFloat(colorStrings[2])};

                String imgName = tempsp[2];
                if(imgName.contains("null"))imgName = null;

                String[] radiusStrings = tempsp[3].split(",");
                float[] radii = new float[]{Float.parseFloat(radiusStrings[0]),Float.parseFloat(radiusStrings[1])};

                skinInfo.put(number,new SnakeNodeSkinInfo(color,imgName,radii));
                Log.d("put number",number+" name:"+imgName);
            }

            snakeSkin = new SnakeSkin(skinName,speed,luck,price,skinInfo);
        } catch (Exception e) {
            e.printStackTrace();
        }
        Log.d("loadSnakeSkinFromFile",skinName+" finish");
        return snakeSkin;
    }
}
