package com.work.terry.snakeonastring_jbox2d.JBox2DElements;

import com.work.terry.snakeonastring_jbox2d.Util.JBox2DUtil;

import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.World;
import org.jbox2d.dynamics.joints.RevoluteJoint;
import org.jbox2d.dynamics.joints.RevoluteJointDef;

import static com.work.terry.snakeonastring_jbox2d.Util.Constant.RATE;

public class MyBox2DRevoluteJoint {
	World world;////物理层里的物理世界
	public RevoluteJoint rjoint;//创建旋转关节对象
	RectBody squareBody;
	public MyBox2DRevoluteJoint(String id,//关节id
                                World world,//物理层里的物理世界
                                boolean collideConnected,
                                MyBody A,
                                MyBody B,
                                Vec2 anchor,
                                boolean enableLimit,//是否开启限制
                                float lowerAngleScale,//底部角度，弧度制
                                float upperAngleScale,//顶部角度，弧度制
                                boolean enableMotor,//是否开启马达
                                float motorSpeed,//马达速度n*Math.PI
                                float maxMotorTorque//马达扭矩
			)
	{
		this.world=world;
		RevoluteJointDef rjd=new RevoluteJointDef();//创建旋转关节描述对象
		rjd.collideConnected=collideConnected;//给是否允许碰撞标志赋值
		rjd.userData=id;						//给关节描述的用户数据赋予关节id
		rjd.enableLimit = enableLimit;			//给是否开启旋转限制赋值
		rjd.lowerAngle = (float) (lowerAngleScale* Math.PI);	//给底部角赋值
		rjd.upperAngle = (float) (upperAngleScale* Math.PI);	//给顶部角赋值
		rjd.enableMotor = enableMotor;					//给是否开启旋转马达赋值
		rjd.motorSpeed = motorSpeed;					//给关节马达速度赋值
		rjd.maxMotorTorque = maxMotorTorque;			//给关节马达的最大扭矩赋值
		anchor.x=anchor.x / RATE;					//更改锚点的x坐标
		anchor.y=anchor.y / RATE;					//更改锚点的y坐标
		rjd.initialize(A.body, B.body, anchor);//调用旋转关节描述的初始化函数
		rjoint=(RevoluteJoint)world.createJoint(rjd);		//在物理世界里增添旋转关节

		JBox2DUtil.Joints.add(this);
	}
}
