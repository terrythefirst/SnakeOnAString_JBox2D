package com.work.terry.snakeonastring_jbox2d.Util;

import android.content.res.Resources;

import com.work.terry.snakeonastring_jbox2d.SurfaceViewAndActivity.GamePlay;

import org.jbox2d.common.Vec2;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Created by Terry on 2018/2/28.
 */

public class LoadGameUtil {
    public GamePlay loadGameFromFile
            (String file, Resources r){
        GamePlay gamePlay = new GamePlay();

        try{
            InputStream in = r.getAssets().open(file);
            InputStreamReader isr = new InputStreamReader(in);
            BufferedReader br=new BufferedReader(isr);
            String temps=null;

            while ((temps=br.readLine())!=null){
                String[] tempsp = temps.split(":");//引号
                tempsp[1] = tempsp[1].replace("height",Constant.SCREEN_HEIGHT+"");
                tempsp[1] = tempsp[1].replace("width",Constant.SCREEN_WIDTH+"");
                String[] subStrings = tempsp[1].split(";");//分号
                if(tempsp[0].trim().equals("sn")){
                    loadSnake(subStrings);
                }else if(tempsp[0].trim().equals("bb")){

                }else if(tempsp[0].trim().equals("bbc")){

                }else if(tempsp[0].trim().equals("bg")){

                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return gamePlay;
    }
    public Vec2 getXY(String ss){
        String[] spXY = ss.trim().split(",");
        float x = (float) Calculator.conversion(spXY[0]);
        float y = (float) Calculator.conversion(spXY[1]);
        return new Vec2(x,y);
    }
    public void loadSnake(String[] snakeStrings){
        for (String str:snakeStrings){
            String[] tempsp = str.split("[ ]+");
            if(tempsp[0].trim().equals("loc")){
                Vec2 XY = getXY(tempsp[1]);

            }
        }
    }
}
