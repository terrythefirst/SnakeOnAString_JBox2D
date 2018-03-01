package com.work.terry.snakeonastring_jbox2d.Util;

import android.content.res.Resources;
import android.util.Log;

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

        String skinName;
        Map<Integer,List<Object>> skinInfo = new HashMap<>();

        try {
            InputStream in = r.getAssets().open(Constant.SnakeSkinDirectoryPrefix+file);
            InputStreamReader isr = new InputStreamReader(in);
            BufferedReader br = new BufferedReader(isr);
            String temps = null;

            skinName = br.readLine();
            if(skinName==null) Log.e("LoadSkinUtil","skinName NULL!!");

            List<Object> skinPerInfo ;
            while ((temps=br.readLine())!=null){
                skinPerInfo = new ArrayList<>();

                String[] tempsp = temps.split("[ ]+");//引号
                Integer number = new Integer(tempsp[0]);

                String[] colorStrings = tempsp[1].split("[,]+");
                float[] color = new float[]{Float.parseFloat(colorStrings[0]),Float.parseFloat(colorStrings[1]),Float.parseFloat(colorStrings[2])};
                skinPerInfo.add(color);

                String imgName = tempsp[2];
                skinPerInfo.add(imgName);

                String[] radiusStrings = tempsp[3].split(",");
                float[] radiuses = new float[]{Float.parseFloat(radiusStrings[0]),Float.parseFloat(radiusStrings[1])};
                skinPerInfo.add(radiuses);

                skinInfo.put(number,skinPerInfo);
            }
            snakeSkin = new SnakeSkin(skinName,skinInfo);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return snakeSkin;
    }
}
