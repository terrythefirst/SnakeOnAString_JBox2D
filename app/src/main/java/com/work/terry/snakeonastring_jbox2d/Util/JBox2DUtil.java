package com.work.terry.snakeonastring_jbox2d.Util;

import com.work.terry.snakeonastring_jbox2d.JBox2DElements.MyBody;
import com.work.terry.snakeonastring_jbox2d.JBox2DElements.MyDistanceJoint;
import com.work.terry.snakeonastring_jbox2d.JBox2DElements.MyJoint;
import com.work.terry.snakeonastring_jbox2d.SnakeElements.Bomb;
import com.work.terry.snakeonastring_jbox2d.SnakeElements.FoodMagnet;
import com.work.terry.snakeonastring_jbox2d.SnakeElements.SnakeFood;
import com.work.terry.snakeonastring_jbox2d.SurfaceViewAndActivity.GamePlay;
import com.work.terry.snakeonastring_jbox2d.Thread.FoodMagnetMoveThread;

import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;

import java.util.ArrayList;
import java.util.List;

import static com.work.terry.snakeonastring_jbox2d.Util.Constant.RATE;

/**
 * Created by Terry on 2018/1/14.
 */

public class JBox2DUtil {
    public static List<MyBody> Bodies= new ArrayList<>();//所有对Bodies的操作要求再 STEP期间做
    public static List<MyBody> removeBodyList = new ArrayList<>();
    public static List<MyJoint> Joints = new ArrayList<>();

    public static Body staticBody = null;

    public static void MyJBox2DStep(){
        for (MyBody mb : JBox2DUtil.Bodies) {
            mb.popXYfromBody();

            if (mb instanceof SnakeFood) {
                if (((SnakeFood) mb).eatean) {
                    mb.setDoDraw(false);
                    JBox2DUtil.removeBodyList.add(mb);
                }
            }
            if(mb instanceof Bomb){
                if(((Bomb)mb).eatean){
                    mb.setDoDraw(false);
                    JBox2DUtil.removeBodyList.add(mb);
                }
            }
            if(mb instanceof FoodMagnet){
                if(((FoodMagnet)mb).eatean){
                    mb.setDoDraw(false);
                    JBox2DUtil.removeBodyList.add(mb);
                }
            }
        }
        synchronized (removeBodyList){
            for(MyBody mb:JBox2DUtil.removeBodyList){
                JBox2DUtil.Bodies.remove(mb);
                mb.destroySelf();
            }
        }
        JBox2DUtil.removeBodyList.clear();
    }

}
