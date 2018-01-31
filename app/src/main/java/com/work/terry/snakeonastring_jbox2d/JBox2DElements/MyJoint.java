package com.work.terry.snakeonastring_jbox2d.JBox2DElements;

import org.jbox2d.dynamics.World;
import org.jbox2d.dynamics.joints.Joint;

import java.net.PortUnreachableException;

/**
 * Created by Terry on 2018/1/31.
 */

public class MyJoint {
    public World world;
    public Joint joint;
    public boolean destroyed = false;

    public void destroySelf(){
        if(!destroyed){
            world.destroyJoint(joint);
            destroyed = true;
        }
    }
}
