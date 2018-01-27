package com.work.terry.snakeonastring_jbox2d.JBox2DElements;

import com.work.terry.snakeonastring_jbox2d.Util.JBox2DUtil;

import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.World;
import org.jbox2d.dynamics.joints.PrismaticJoint;
import org.jbox2d.dynamics.joints.PrismaticJointDef;

import static com.work.terry.snakeonastring_jbox2d.Util.Constant.RATE;

/**
 * Created by Terry on 2018/1/23.
 */

public class MyPrismaticJoint {
    World world;//物理层里的物理世界
    PrismaticJoint pjoint;//创建移动关节对象
    public MyPrismaticJoint(
            String id,
            World world,
            boolean collideConnected,
            Body A,
            Body B,
            Vec2 anchor,
            Vec2 localAxisA,
            float referenceAngle,
            boolean enableLimit,
            float lowerTranslation,
            float upperTranslation,
            boolean enableMotor,
            float motorSpeed,
            float maxMotorForce
    ){
        this.world=world;
        PrismaticJointDef pjd=new PrismaticJointDef();//创建移动关节描述对象
        pjd.userData=id;								//给关节描述的用户数据赋予关节id
        pjd.collideConnected=collideConnected;		//给是否允许碰撞标志赋值
//        localAxisA.normalize();						//单位化
//        pjd.localAxisA=localAxisA;					//设置移动关节的轴向量
        pjd.referenceAngle=referenceAngle;			//设置刚体B与刚体A的角度差
        pjd.enableMotor=enableMotor;					//给是否开启移动马达赋值
        pjd.motorSpeed = motorSpeed;					//给关节马达速度赋值
        pjd.maxMotorForce=maxMotorForce;				//给关节马达的最大扭矩赋值
        pjd.lowerTranslation = lowerTranslation / RATE;		//最小变换
        pjd.upperTranslation = upperTranslation / RATE;		//最大变换
        pjd.enableLimit=enableLimit;							//给是否开启关节限制赋值
        pjd.initialize(A, B, anchor, localAxisA);			//调用移动关节描述对象的初始化函数
        pjoint=(PrismaticJoint)world.createJoint(pjd);		//在物理世界里增添移动关节

        JBox2DUtil.Joints.add(pjoint);
    }
}