package com.work.terry.snakeonastring_jbox2d.JBox2DElements;

import com.work.terry.snakeonastring_jbox2d.Thread.JBox2DThread;

import org.jbox2d.dynamics.World;

import java.net.PortUnreachableException;

/**
 * Created by Terry on 2018/3/11.
 */

public class JBox2dThreadTask {
    public static final int OPERATION_ADD_BODY = 0;
    public static final int OPERATION_ADD_JOINT = 1;

    public static final int OPERATION_DELETE_BODY = 10;
    public static final int OPERATION_DELETE_JOINT = 11;

    public int operation;
    public World world;
    public MyBody myBody;
    public MyJoint myJoint;

    public JBox2dThreadTask(
            int operation,
            MyBody myBody
    ){
        this.operation = operation;
        this.myBody = myBody;
        this.world = myBody.world;
        this.myJoint = null;
    }
    public JBox2dThreadTask(
            int operation,
            MyJoint myJoint
    ){
        this.operation = operation;
        this.myBody = null;
        this.myJoint = myJoint;
        this.world = myJoint.world;
    }

    public void doTask(){
        switch (operation){
            case OPERATION_ADD_BODY:
                myBody.createBody();
                break;
            case OPERATION_ADD_JOINT:
                myJoint.createJoint();
                break;
            case OPERATION_DELETE_BODY:
                if(!myBody.destroyed){
                    myBody.deleteBody();
                    myBody.destroyed = true;
                }
                break;
            case OPERATION_DELETE_JOINT:
                if(!myJoint.destroyed){
                    myJoint.deleteJoint();
                    myJoint.destroyed = true;
                }

                //break;
        }
    }
}
