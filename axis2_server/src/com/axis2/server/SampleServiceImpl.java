package com.axis2.server;
import org.apache.log4j.*;

public class SampleServiceImpl implements SampleServer {
	private Logger log = Logger.getLogger(SampleServiceImpl.class);
	@Override
	public String echo(String s) {
		// TODO Auto-generated method stub
		String backValue = "Axis2 Test2 "+ s;
		log.debug("SampleServiceImpl");
		return backValue;
	}

}
