package com.axis2.server;

public class SampleServiceImpl implements SampleServer {

	@Override
	public String echo(String s) {
		// TODO Auto-generated method stub
		String backValue = "Axis2 Test2 "+ s; 
		return backValue;
	}

}
