package com.axis2.server;

import javax.jws.WebService;

@WebService
public interface SampleServer {
	public String echo(String s);
}
