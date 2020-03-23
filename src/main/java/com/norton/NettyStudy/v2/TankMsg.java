package com.norton.NettyStudy.v2;

public class TankMsg {

	public int x,y;
	
	public TankMsg(int x,int y) {
		this.x=x;
		this.y=y;
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return "TankMsg:"+x+","+y;
	}
	
	
}
