package com.work.terry.snakeonastring_jbox2d.JBox2DElements;

import android.util.Log;

import com.work.terry.snakeonastring_jbox2d.Util.JBox2DUtil;

import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.World;
import org.jbox2d.dynamics.joints.WeldJoint;
import org.jbox2d.dynamics.joints.WeldJointDef;

public class MyWeldJoint 
{
	World world;//声明物理世界引用
	public WeldJoint wj;//声明焊接关节引用
	public MyWeldJoint
	(
            String id,//关节id
            World world,//物理世界对象
            boolean collideConnected,//是否允许两个刚体碰撞
            MyBody poA,//刚体A
            MyBody poB,//刚体B
            Vec2 anchor,    //焊接关节的锚点
            float frequencyHz,                //关节频率
            float dampingRatio            //阻尼系数
	)
	{
		this.world=world;//给物理世界类对象赋值
		WeldJointDef wjd=new WeldJointDef();//声明焊接关节描述对象
		wjd.userData=id;//给关节描述的用户数据赋予关节id
		wjd.collideConnected=collideConnected;//给是否允许碰撞标志赋值
		wjd.initialize(poA.body,poB.body,anchor);//调用焊接关节的初始化方法
		wjd.frequencyHz = frequencyHz;					//给关节频率赋值
		wjd.dampingRatio = dampingRatio;					//给阻尼系数赋值
//		while (world.isLocked()){
//			Log.d("world","LOKED!");
//		}
		wj=(WeldJoint) world.createJoint(wjd);//在物理世界添加焊接关节

		JBox2DUtil.Joints.add(this);
	}
}
