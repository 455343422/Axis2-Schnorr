package com.axis2.Schnorr;
import java.math.BigInteger;
import java.util.Random;

import javax.swing.JOptionPane;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import org.apache.log4j.Logger;
public class Schnorr {

	private Logger logger = Logger.getLogger(Schnorr.class);
	private BigInteger[] publicKeys = new BigInteger[4];
	private BigInteger privateKey;
	File file = new File("F:\\publicKey");

	public BigInteger calculate_X( BigInteger r) {
		BigInteger x = publicKeys[2].modPow(r, publicKeys[0]);
		return x;
	}
	
	public BigInteger calculate_S( BigInteger r, BigInteger e) {
		BigInteger s = (r.add(privateKey.multiply(e))).mod(publicKeys[1]);
		return s;
	}
	
	public boolean checkOut(BigInteger x, BigInteger s, BigInteger e, BigInteger p, BigInteger g, BigInteger y) {
		String str;
		BigInteger qq = (g.modPow(s, p)).multiply(y.modPow(e, p))
				.mod(p);
		if (x.equals(qq)) {
		
			return true;
		} else {
			
			return false;
		}
	}

	public void clear() {
		this.privateKey = null;
		this.publicKeys[0] = null;
		this.publicKeys[1] = null;
		this.publicKeys[2] = null;
		this.publicKeys[3] = null;

	}	
	
	public BigInteger[] getPublicKeys() {
		FileReader fr;
		try {
			fr = new FileReader(new File("F:\\publicKeys"));
			BufferedReader br = new BufferedReader(fr);
			publicKeys[0] = new BigInteger(br.readLine());
			publicKeys[1] = new BigInteger(br.readLine());
			publicKeys[2] = new BigInteger(br.readLine());
			publicKeys[3] = new BigInteger(br.readLine());
			br.close();
			fr.close();
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return publicKeys;
	}

	public BigInteger getPrivateKey() {
		return privateKey;
	}
}
