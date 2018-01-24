package com.work.terry.snakeonastring_jbox2d.JBox2DElements;

import android.util.Log;

import com.work.terry.snakeonastring_jbox2d.Util.JBox2DUtil;

import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.World;
import org.jbox2d.dynamics.joints.DistanceJoint;
import org.jbox2d.dynamics.joints.DistanceJointDef;


/**
 * Created by Terry on 2018/1/14.
 */

public class MyDistanceJoint
{
    World world;//物理世界对象
    public DistanceJoint dj;//距离关节对象
    public MyDistanceJoint
            (
                    String id,//id
                    World world,//物理世界
                    boolean collideConnected,//是否允许碰撞
                    MyBody poA,//刚体A
                    MyBody poB,//刚体B
                    Vec2 anchorA,//锚点
                    Vec2 anchorB,//锚点
                    float frequencyHz,//为0表示禁柔度
                    float dampingRatio// 阻尼系数.
            )
    {
        this.world=world;
        DistanceJointDef djd=new DistanceJointDef();//声明关节描述对象
        djd.collideConnected=collideConnected;//给是否允许碰撞标志赋值
        djd.userData=id;//给关节描述的用户数据赋予关节id
        djd.initialize(poA.body, poB.body, anchorA, anchorB);//调用关节的初始化方法
        djd.frequencyHz=frequencyHz;//设置关节频率
        djd.dampingRatio=dampingRatio;//设置关节阻尼系数
        dj=(DistanceJoint) world.createJoint(djd);//在物理世界添加距离关节


        Log.d("RectBody","length"+dj.getLength());
        JBox2DUtil.Joints.add(this);
    }
}
