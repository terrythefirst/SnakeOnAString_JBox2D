package com.work.terry.snakeonastring_jbox2d.JBox2DElements;

import android.util.Log;

import com.work.terry.snakeonastring_jbox2d.SurfaceViewAndActivity.GamePlay;

import org.jbox2d.dynamics.World;
import org.jbox2d.dynamics.joints.Joint;

import java.net.PortUnreachableException;

/**
 * Created by Terry on 2018/1/31.
 */

public abstract class MyJoint {
    String id;
    public GamePlay gamePlay;
    public World world;
    public Joint joint;

    public boolean created = false;
    public boolean destroyed = false;

//    public void destroySelf(){
//        if(!destroyed){
//            world.destroyJoint(joint);
//            destroyed = true;
//        }
//    }
    public void sendCreateTask(){
        if(!created){
            gamePlay.jBox2DThread.addToTasks(new JBox2dThreadTask(JBox2dThreadTask.OPERATION_ADD_JOINT,this));
        }
    }
    public void sendDeleteTask(){
        gamePlay.jBox2DThread.addToTasks(new JBox2dThreadTask(JBox2dThreadTask.OPERATION_DELETE_JOINT,this));
    }
    public abstract void createJoint();
    public void deleteJoint(){
        if(created&&!destroyed){
            world.destroyJoint(joint);
            destroyed = true;
        }
    }
}
