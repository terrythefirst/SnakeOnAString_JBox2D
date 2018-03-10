package com.work.terry.snakeonastring_jbox2d.JBox2DElements;

import com.work.terry.snakeonastring_jbox2d.SurfaceViewAndActivity.GamePlay;

import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.World;
import org.jbox2d.dynamics.joints.WeldJoint;
import org.jbox2d.dynamics.joints.WeldJointDef;

public class MyWeldJoint extends MyJoint
{
	public MyWeldJoint
	(
			String id,//关节id
			GamePlay gamePlay,//物理世界对象
			boolean collideConnected,//是否允许两个刚体碰撞
			MyBody poA,//刚体A
			MyBody poB,//刚体B
			Vec2 anchor,    //焊接关节的锚点
			float frequencyHz,                //关节频率
			float dampingRatio            //阻尼系数
	)
	{
		this.world=gamePlay.world;//给物理世界类对象赋值
		this.gamePlay = gamePlay;
		WeldJointDef wjd=new WeldJointDef();//声明焊接关节描述对象
		wjd.userData=id;//给关节描述的用户数据赋予关节id
		wjd.collideConnected=collideConnected;//给是否允许碰撞标志赋值
		wjd.initialize(poA.body,poB.body,anchor);//调用焊接关节的初始化方法
		wjd.frequencyHz = frequencyHz;					//给关节频率赋值
		wjd.dampingRatio = dampingRatio;					//给阻尼系数赋值

		joint=(WeldJoint) world.createJoint(wjd);//在物理世界添加焊接关节

		gamePlay.jBox2DThread.Joints.add(this);
	}
}
