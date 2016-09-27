

/**
 * SampleServiceImplTest.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis2 version: 1.7.2  Built on : May 02, 2016 (05:55:18 BST)
 */
    package com.axis2.server;

    /*
     *  SampleServiceImplTest Junit test case
    */

    public class SampleServiceImplTest extends junit.framework.TestCase{

     
        /**
         * Auto generated test method
         */
        public  void testecho() throws java.lang.Exception{

        com.axis2.server.SampleServiceImplStub stub =
                    new com.axis2.server.SampleServiceImplStub();//the default implementation should point to the right endpoint

           com.axis2.server.Echo echo4=
                                                        (com.axis2.server.Echo)getTestObject(com.axis2.server.Echo.class);
                    // TODO : Fill in the echo4 here
                
                        assertNotNull(stub.echo(
                        echo4));
                  



        }
        
        //Create an ADBBean and provide it as the test object
        public org.apache.axis2.databinding.ADBBean getTestObject(java.lang.Class type) throws java.lang.Exception{
           return (org.apache.axis2.databinding.ADBBean) type.newInstance();
        }

        
        

    }
    