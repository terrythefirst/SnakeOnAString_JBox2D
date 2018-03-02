package com.work.terry.snakeonastring_jbox2d.Util;

import android.content.res.Resources;
import android.util.Log;

import com.work.terry.snakeonastring_jbox2d.GamePlayElements.ButtonBlock;
import com.work.terry.snakeonastring_jbox2d.GamePlayElements.ButtonBlockCircle;
import com.work.terry.snakeonastring_jbox2d.SnakeElements.Snake;
import com.work.terry.snakeonastring_jbox2d.SurfaceViewAndActivity.GamePlay;
import com.work.terry.snakeonastring_jbox2d.UI.Button;
import com.work.terry.snakeonastring_jbox2d.UI.GameElement;

import org.jbox2d.common.Vec2;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Terry on 2018/2/28.
 */

public class LoadGameUtil {
    public Map<Integer,GameElement> loadHelper  = null;
    public GamePlay loadGameFromFile
            (String file, Resources r){
        loadHelper = new HashMap<>();

        GamePlay gamePlay = null;
        if(file.contains("endless"))
             gamePlay= new GamePlay(Constant.GAME_MODE_ENDLESS);
        else if(file.contains("original"))
            gamePlay = new GamePlay(Constant.GAME_MODE_ORIGINAL);

        try{
            InputStream in = r.getAssets().open(file);
            InputStreamReader isr = new InputStreamReader(in);
            BufferedReader br=new BufferedReader(isr);
            String temps;

            temps = br.readLine();
            if(!temps.contains("bg"))throw new RuntimeException("wrong gameLevelFile!!!");
            String backgroundImg = temps.substring(3).trim();
            if (backgroundImg.equals("null"))
                gamePlay.setDrawUtilAndBacktoundImg(Constant.BackgroundImg);
            else
                gamePlay.setDrawUtilAndBacktoundImg(backgroundImg);

            while ((temps=br.readLine())!=null){
                if (temps.trim().equals(""))continue;

                String[] tempsp = temps.split(":");//引号
                tempsp[1] = tempsp[1].replace("height",Constant.SCREEN_HEIGHT+"");
                tempsp[1] = tempsp[1].replace("width",Constant.SCREEN_WIDTH+"");
                String[] subStrings = tempsp[1].split(";");//分号
                if(tempsp[0].trim().equals("sn")){
                    loadSnake(gamePlay,subStrings);
                }else if(tempsp[0].trim().equals("bb")){
                    loadButtonBlock(gamePlay,subStrings);
                }else if(tempsp[0].trim().equals("bbc")){
                    loadButtonBlockCircle(gamePlay,subStrings);
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }

        loadHelper = null;
        gamePlay.startGame();
        return gamePlay;
    }
    public Vec2 getXY(String ss){
        String[] spXY = ss.trim().split(",");
        float x = (float) Calculator.conversion(spXY[0]);
        float y = (float) Calculator.conversion(spXY[1]);
        return new Vec2(x,y);
    }
    public float[] getColor(String ss){
        String[] spColor = ss.trim().split(",");
        return new float[]{Float.parseFloat(spColor[0]),Float.parseFloat(spColor[1]),Float.parseFloat(spColor[2])};
    }
    public void loadButtonBlock(GamePlay gamePlay,String[] bbcString){
        Vec2 XY = null;
        float radius = 0;
        float[] color255 = null;
        boolean isStatic = false;
        float totalLength = 0;
        float defaultHeight = 0;
        float rotateAngle = 0;

        int id = -1;

        for(String ss:bbcString){
            String[] tempsp = ss.split("[ ]+");
            if(tempsp[0].trim().equals("loc")){
                XY = getXY(tempsp[1]);
            }else if(tempsp[0].trim().equals("rad")){
                radius = Float.parseFloat(tempsp[1].trim());
            }else if(tempsp[0].trim().equals("co")){
                color255 = getColor(tempsp[1]);
            }else if(tempsp[0].trim().equals("dfh")){
                defaultHeight = Float.parseFloat(tempsp[1].trim());
            }else if(tempsp[0].trim().equals("id")){
                id = Integer.parseInt(tempsp[1].trim());
            }else if(tempsp[0].trim().equals("len")){
                totalLength = Float.parseFloat(tempsp[1].trim());
            } else if(tempsp[0].trim().equals("dir")){
                //0 horizontal   90 vertical  clockwise
                rotateAngle = Float.parseFloat(tempsp[1].trim());
            }
        }
        ButtonBlock buttonBlock = new ButtonBlock(
                gamePlay.world,
                "ButtonBlock ",
                XY.x,XY.y,
                radius*2,
                totalLength,
                defaultHeight,
                rotateAngle,
                isStatic,
                color255
        );
        if (id!=-1)
            loadHelper.put(id,buttonBlock);
        gamePlay.addGameElements(buttonBlock,Constant.LAYER_CENTER);
    }
    public void loadButtonBlockCircle(GamePlay gamePlay,String[] bbcString){
        Vec2 XY = null;
        float radius = 0;
        float[] color255 = null;
        boolean isStatic = false;
        float defaultHeight = 0;

        int id = -1;

        for(String ss:bbcString){
            String[] tempsp = ss.split("[ ]+");
            if(tempsp[0].trim().equals("loc")){
                XY = getXY(tempsp[1]);
            }else if(tempsp[0].trim().equals("rad")){
                radius = Float.parseFloat(tempsp[1].trim());
            }else if(tempsp[0].trim().equals("co")){
                color255 = getColor(tempsp[1]);
            }else if(tempsp[0].trim().equals("dfh")){
                defaultHeight = Float.parseFloat(tempsp[1].trim());
            }else if(tempsp[0].trim().equals("id")){
                id = Integer.parseInt(tempsp[1].trim());
            }
        }
        ButtonBlockCircle buttonBlockCircle = new ButtonBlockCircle(
                gamePlay.world,
                XY.x,XY.y,
                radius,
                color255,
                isStatic,
                defaultHeight,
                0
        );
        if (id!=-1)
            loadHelper.put(id,buttonBlockCircle);
        gamePlay.addGameElements(buttonBlockCircle,Constant.LAYER_CENTER);
    }
    public void loadSnake(GamePlay gamePlay,String[] snakeStrings){
        Vec2 XY = null;
        Vec2 V = null;
        float scaleRatio = 1;
        int totalLength = 1;
        for (String str:snakeStrings){
            String[] tempsp = str.split("[ ]+");
            if(tempsp[0].trim().equals("loc")){
                XY = getXY(tempsp[1]);
            }else if(tempsp[0].trim().equals("dir")){
                V = getXY(tempsp[1]);
            }else if(tempsp[0].trim().equals("len")){
                totalLength = Integer.parseInt(tempsp[1]);
            }else if(tempsp[0].trim().equals("scale")){
                scaleRatio = Float.parseFloat(tempsp[1]);
            }else if(tempsp[0].trim().equals("ani")){

            }
        }

        if (XY==null)throw new RuntimeException("XY IS NULL");
        if (V==null)throw new RuntimeException("V IS NULL");

        Snake snake = new Snake(
                gamePlay,
                XY,
                V,
                scaleRatio,
                totalLength,
                0
        );
        gamePlay.setSnake(snake);
    }
}
