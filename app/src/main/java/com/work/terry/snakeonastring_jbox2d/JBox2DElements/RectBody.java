package com.work.terry.snakeonastring_jbox2d.JBox2DElements;

import android.content.SharedPreferences;
import android.util.Log;

import com.work.terry.snakeonastring_jbox2d.SurfaceViewAndActivity.GamePlay;
import com.work.terry.snakeonastring_jbox2d.Thread.JBox2DThread;
import com.work.terry.snakeonastring_jbox2d.Util.Constant;

import org.jbox2d.collision.shapes.PolygonShape;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.BodyDef;
import org.jbox2d.dynamics.BodyType;
import org.jbox2d.dynamics.FixtureDef;
import org.jbox2d.dynamics.World;

import static com.work.terry.snakeonastring_jbox2d.Util.Constant.RATE;

/**
 * Created by Terry on 2018/1/13.
 */

public class RectBody extends MyBody{
    float halfWidth;//声明矩形类物体半宽的变量
    float halfHeight;//声明矩形类物体半高的变量

    public RectBody(
            GamePlay gamePlay,
            String id,
            float x,float y,
            float angle,
            float halfWidth,//半宽
            float halfHeight,//半高

            int color,
            float defaultHeight,
            float topOffset,
            float topOffsetColorFactor,
            float heightColorFactor,
            float floorShadowColorFactor,

            float density,
            float friction,
            float restitution,

            String img,
            boolean isStatic)//构造函数
    {
        super(
                gamePlay,
                id,
                x,y,
                halfWidth*2,halfHeight*2,
                color,
                defaultHeight,
                topOffset,
                topOffsetColorFactor,
                heightColorFactor,
                floorShadowColorFactor,

                angle,
                0,
                0,
                density,
                friction,
                restitution,
                isStatic,

                img
        );
        this.halfHeight = halfHeight;
        this.halfWidth = halfWidth;

    }
    @Override
    public void createBody(){
        if(created)return;

        BodyDef bd=new BodyDef();//创建刚体描述
        if(isStatic)//判断是否为可运动刚体
        {
            bd.type= BodyType.STATIC;
        }
        else
        {
            bd.type= BodyType.DYNAMIC;
        }
        bd.position.set(x/RATE, y/RATE);//设置位置
        bd.userData = id;
        bd.angle = angleRadian;

        Body bodyTemp = world.createBody(bd);//在世界中创建刚体
        PolygonShape ps=new PolygonShape();//创建刚体形状
        ps.setAsBox(halfWidth/RATE, halfHeight/RATE);//设定边框
        FixtureDef fd=new FixtureDef();//创建刚体物理描述
        fd.density = density;//设置密度
        fd.friction = friction;//设置摩擦系数
        fd.restitution = restitution;//设置恢复系数

        fd.shape=ps;//设置形状
        if(!isStatic)//将刚体物理描述与刚体结合
        {
            bodyTemp.createFixture(fd);
        }
        else
        {
            bodyTemp.createFixture(ps, 0);
        }
        this.body = bodyTemp;

        if(this.body!=null)created=true;
    }
    @Override
    public void onPause(SharedPreferences.Editor editor){
        super.onPause(editor);
        editor.putFloat(id+"halfWidth",halfWidth);
        editor.putFloat(id+"halfHeight",halfHeight);
    }
}
