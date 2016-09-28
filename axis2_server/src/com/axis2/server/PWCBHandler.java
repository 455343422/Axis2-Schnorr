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

import com.axis2.Schnorr.Schnorr;
import javax.security.auth.callback.Callback;
import javax.security.auth.callback.CallbackHandler;
import javax.security.auth.callback.UnsupportedCallbackException;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.math.BigInteger;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Random;
import org.apache.log4j.Logger;

public class PWCBHandler implements CallbackHandler {
	private static Logger logger = Logger.getLogger(PWCBHandler.class);

	public void handle(Callback[] callbacks) throws IOException, UnsupportedCallbackException {
		BigInteger X = null, S = null, E = null;
		FileReader fr = new FileReader(new File("F:\\X"));
		BufferedReader br = new BufferedReader(fr);
		String Xs = "";
		while (true) {
			Xs = br.readLine();
			if (Xs.equals("") || Xs.equals(null)) {
				Thread thread = Thread.currentThread();
				try {
					thread.sleep(50);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			} else {
				br.close();
				fr.close();
				break;
			}
		}
		logger.debug("logger.debug   X:" + Xs);
		fr = new FileReader(new File("F:\\E"));
		br = new BufferedReader(fr);
		String Es = "";
		while (true) {
			Es = br.readLine();
			if (Es.equals("") || Es.equals(null)) {
				Thread thread = Thread.currentThread();
				try {
					thread.sleep(50);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			} else {
				br.close();
				fr.close();
				break;
			}
		}
		logger.debug("logger.debug    " + this.getClass().getName());
		logger.debug("logger.debug   E:" + Es);
		X = new BigInteger(Xs);
		E = new BigInteger(Es);

		// System.out.println("X: " + X);
		// System.out.println("E: " +E.toString());
		Schnorr SCHNORR = new Schnorr();
		BigInteger publicKeys[] = SCHNORR.getPublicKeys();

		String Ss = "";
		fr = new FileReader(new File("F:\\S"));
		br = new BufferedReader(fr);
		while (true) {
			Ss = br.readLine();
			if (Ss.equals("") || Es.equals(null)) {
				Thread thread = Thread.currentThread();
				try {
					thread.sleep(50);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			} else {
				br.close();
				fr.close();
				break;
			}
		}
		logger.debug("logger.debug   Ss:" + Ss);
		S = new BigInteger(Ss);
		for (int i = 0; i < callbacks.length; i++) {
			// When the server side need to authenticate the user
			WSPasswordCallback pwcb = (WSPasswordCallback) callbacks[i];
			logger.debug("logger.debug    Identifiers:" + pwcb.getIdentifier());
			logger.debug("logger.debug    password:" + pwcb.getPassword());
			logger.debug("logger.debug    getUsage:" + pwcb.getUsage());
			logger.debug("logger.debug    UsernameTokenUnknown:" + WSPasswordCallback.USERNAME_TOKEN_UNKNOWN);
			if (pwcb.getUsage() == WSPasswordCallback.USERNAME_TOKEN_UNKNOWN) {
				if (pwcb.getIdentifier().equals("sever555") && pwcb.getPassword().equals(Ss)) {
					logger.debug("logger.debug   Identifier is server 555");
					return;
				} else {
					throw new UnsupportedCallbackException(callbacks[i], "check failed");
				}
			}

			// When the client requests for the password to be added in to the
			// UT element
			logger.debug("logger.debug   publicKeys:\n" + publicKeys[0] + "\n" + publicKeys[1] + "\n" + publicKeys[2]
					+ "\n" + publicKeys[3] + "\n");
			boolean result = SCHNORR.checkOut(X, S, E, publicKeys[0], publicKeys[2], publicKeys[3]);
			logger.debug("logger.debug   result:" + result);
			if (result) {
				logger.debug("logger.debug   result is true!");
				logger.debug("set new password");
				pwcb.setPassword(Ss);
			}

		}

	}
}
