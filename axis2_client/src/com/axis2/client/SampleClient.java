package com.axis2.client;

import java.io.File;
import org.apache.axis2.context.ConfigurationContext;
import org.apache.axis2.context.ConfigurationContextFactory;
import org.apache.axis2.databinding.ADBBean;
import org.apache.rampart.RampartMessageData;

import com.axis2.server.Echo;
import com.axis2.server.SampleServiceImplStub;
import javax.xml.namespace.QName;

import org.apache.axiom.om.OMAbstractFactory;
import org.apache.axiom.om.OMElement;
import org.apache.axiom.om.OMFactory;
import org.apache.axiom.om.OMNamespace;

import org.apache.axis2.addressing.EndpointReference;
import org.apache.axis2.client.Options;
import org.apache.axis2.client.ServiceClient;
import org.apache.axis2.context.ConfigurationContext;
import org.apache.axis2.context.ConfigurationContextFactory;

public class SampleClient {
	/*
	 * public String testserver() throws Exception { String toEPR =
	 * "http://localhost:8080/axis2_server/services/SampleServer?wsdl";
	 * SampleServerStub serviceClient = new SampleServerStub(toEPR); Echo echo =
	 * (Echo) getTestObject(Echo.class); echo.setS("aaa"); return
	 * serviceClient.echo(echo).get_return(); }
	 * 
	 * public ADBBean getTestObject(Class type) throws Exception { return
	 * (ADBBean) type.newInstance(); }
	 * 
	 * public static void main(String[] args) { SampleClient sampleClient=new
	 * SampleClient(); try { String resaultValue=sampleClient.testserver();
	 * System.out.println("从服务端返回内容为："+resaultValue); } catch (Exception e) {
	 * e.printStackTrace(); } }
	 */

	/*2016/09/01 可以运行，但Missing wsse:Security header in request*/
	  public String testserver(String path) throws Exception {
		String toEPR = "http://localhost:8080/axis2_server/services/SampleServiceImpl?wsdl";
		ConfigurationContext configContext = null;
		SampleServiceImplStub serviceClient = null;
		configContext = ConfigurationContextFactory.createConfigurationContextFromFileSystem(
				path + "/WebContent/WEB-INF", path + "/WebContent/WEB-INF/conf/axis2.xml");
		serviceClient = new SampleServiceImplStub(configContext, toEPR);
		Echo echo = (Echo) getTestObject(Echo.class);
		echo.setS("abc");
		return serviceClient.echo(echo).get_return();
	}

	public ADBBean getTestObject(Class type) throws Exception {
		return (ADBBean) type.newInstance();
	}

	public static void main(String[] args) {
		SampleClient sampleClient = new SampleClient();
		try {
			File file = new File("");
			String path = file.getAbsolutePath();
			String resaultValue = sampleClient.testserver(path);
			System.out.println("从服务端返回内容为：" + resaultValue);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	
	

}