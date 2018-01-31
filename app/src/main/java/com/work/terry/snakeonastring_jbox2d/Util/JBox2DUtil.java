package com.work.terry.snakeonastring_jbox2d.Util;

import com.work.terry.snakeonastring_jbox2d.JBox2DElements.MyBody;
import com.work.terry.snakeonastring_jbox2d.JBox2DElements.MyDistanceJoint;
import com.work.terry.snakeonastring_jbox2d.JBox2DElements.MyJoint;
import com.work.terry.snakeonastring_jbox2d.SnakeElements.Bomb;
import com.work.terry.snakeonastring_jbox2d.SnakeElements.SnakeFood;

import org.jbox2d.dynamics.Body;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Terry on 2018/1/14.
 */

public class JBox2DUtil {
    public static List<MyBody> Bodies= new ArrayList<>();
    public static List<MyBody> removeBodyList = new ArrayList<>();
    public static List<MyJoint> Joints = new ArrayList<>();

    public static Body staticBody = null;

    public static void MyJBox2DStep(){
        for (MyBody mb : JBox2DUtil.Bodies) {
            mb.popXYfromBody();

            if (mb instanceof SnakeFood) {
                if (((SnakeFood) mb).eatean) {
                    mb.setDoDraw(false);
                    mb.destroySelf();
                    JBox2DUtil.removeBodyList.add(mb);
                }
            }
            if(mb instanceof Bomb){
                if(((Bomb)mb).eatean){
                    mb.setDoDraw(false);
                    mb.destroySelf();
                    JBox2DUtil.removeBodyList.add(mb);
                }
            }
        }
        for(MyBody mb:JBox2DUtil.removeBodyList){
            JBox2DUtil.Bodies.remove(mb);
        }
        JBox2DUtil.removeBodyList.clear();
    }
}
