/*
 * Copyright 2004,2005 The Apache Software Foundation.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.axis2.server;

import org.apache.ws.security.WSPasswordCallback;

import com.axis2.schnorr.Schnorr;

import javax.security.auth.callback.Callback;
import javax.security.auth.callback.CallbackHandler;
import javax.security.auth.callback.UnsupportedCallbackException;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.math.BigInteger;
import java.util.Random;
import org.apache.log4j.Logger;

public class ClientAuthHandler implements CallbackHandler {

	private Logger logger = Logger.getLogger(ClientAuthHandler.class);

	public void handle(Callback[] callbacks) throws IOException, UnsupportedCallbackException {
		/*
		 * 将Schnorr在Alice中的操作分离出来， 读写文件传值
		 */
		Schnorr SCHNORR = new Schnorr();
		String privateKey = BigInteger.probablePrime(128, new Random()).toString();
		SCHNORR.generateKeys(privateKey);
		logger.debug("logger.debug   privateKey: " + privateKey);
		logger.debug("logger.debug   publicKeys:\n" + SCHNORR.getPublicKeys()[0].toString() + "\n"
				+ SCHNORR.getPublicKeys()[1].toString() + "\n" + SCHNORR.getPublicKeys()[2].toString() + "\n"
				+ SCHNORR.getPublicKeys()[3].toString() + "\n");
		String R = BigInteger.probablePrime(128, new Random()).toString();
		String X = SCHNORR.calculate_X(new BigInteger(R)).toString();
		String Es = "";
		try {
			FileWriter fw = new FileWriter(new File("F:\\X"));
			BufferedWriter bw = new BufferedWriter(fw);
			bw.write(X + "\n");
			bw.close();
			fw.close();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		BigInteger E = BigInteger.probablePrime(72, new Random());
		FileWriter fw;
		try {
			fw = new FileWriter(new File("F:\\E"));
			BufferedWriter bw = new BufferedWriter(fw);
			bw.write(E.toString() + "\n");
			bw.close();
			fw.close();
		} catch (IOException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
		logger.debug("logger.debug    R: " + R);
		logger.debug("logger.debug    X: " + X);
		logger.debug("logger.debug    E: " + E.toString());

		String Ss = SCHNORR.calculate_S(new BigInteger(R), E).toString();
		logger.debug("logger.debug     S: " + Ss);

		try {
			fw = new FileWriter(new File("F:\\S"));
			BufferedWriter bw = new BufferedWriter(fw);
			bw.write(Ss + "\n");
			bw.close();
			fw.close();
		} catch (IOException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}

		for (int i = 0; i < callbacks.length; i++) {
			// When the server side need to authenticate the user
			WSPasswordCallback pwcb = (WSPasswordCallback) callbacks[i];
			if (pwcb.getUsage() == WSPasswordCallback.USERNAME_TOKEN_UNKNOWN) {
				if (pwcb.getIdentifier().equals("server555")) {
					pwcb.setPassword(Ss);
					return;
				} else {
					throw new UnsupportedCallbackException(callbacks[i], "check failed");
				}
			}

			// When the client requests for the password to be added in to the
			// UT element
			pwcb.setPassword(Ss);

		}
	}

}
