package com.axis2.schnorr;
import java.math.BigInteger;
import java.util.Random;

import javax.swing.JOptionPane;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class Schnorr {

	private BigInteger[] publicKeys = new BigInteger[4];
	private BigInteger privateKey;

	
	public void generateKeys(String privateKey) {
		if (privateKey.isEmpty()) {
			JOptionPane.showMessageDialog(null, "Private Key is empty!!!",
					"Error", JOptionPane.ERROR_MESSAGE);
		} else {

			Random random = new Random();
			BigInteger p, q, g, y;

			q = BigInteger.probablePrime(256, random); // ****计算q
														// *****

			BigInteger k = BigInteger.probablePrime(160, random);
			do {
				k = k.add(BigInteger.ONE);
				p = (q.multiply(k)).add(BigInteger.ONE);
			} while (p.isProbablePrime(15));

			BigInteger e0 = BigInteger.valueOf(2);
			g = e0.modPow((p.subtract(BigInteger.ONE).divide(q)), p);

			// w = BigInteger.probablePrime(32, random);
			BigInteger w = new BigInteger(privateKey);
			this.privateKey = w;

			y = g.modPow(w.negate(), p);

			publicKeys[0] = p;
			publicKeys[1] = q;
			publicKeys[2] = g;
			publicKeys[3] = y;
			FileWriter fw;
			try {
				fw = new FileWriter(new File("F:\\publicKeys"));
				BufferedWriter bw = new BufferedWriter(fw);
				bw.write(p.toString()+"\n");
				bw.write(q.toString()+"\n");
				bw.write(g.toString()+"\n");
				bw.write(y.toString()+"\n");
				bw.close();
				fw.close();
				
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
//			JOptionPane.showMessageDialog(null, "Public Keys succesfully generated!",
//					"Keys:", JOptionPane.INFORMATION_MESSAGE);
		}
	}

	public String toString() {
		String s = "";
		s += "public  = (\n";
		s += "  p =  " + publicKeys[0] + ",\n";
		s += "  q =  " + publicKeys[1] + ",\n";
		s += "  g = " + publicKeys[2] + ",\n";
		s += "  y = " + publicKeys[3] + "\n";
		s += "private  w = " + privateKey + "\n";
		return s;
	}

	public BigInteger calculate_X( BigInteger r) {
		BigInteger x = publicKeys[2].modPow(r, publicKeys[0]);
		return x;
	}
	
	public BigInteger calculate_S( BigInteger r, BigInteger e) {
		BigInteger s = (r.add(privateKey.multiply(e))).mod(publicKeys[1]);
		return s;
	}
	
	public void checkOut(BigInteger x, BigInteger s, BigInteger e) {
		BigInteger p = publicKeys[0];
		BigInteger g = publicKeys[2];
		BigInteger y = publicKeys[3];
		String str;
		BigInteger qq = (g.modPow(s, publicKeys[0])).multiply(y.modPow(e, p))
				.mod(p);
		if (x.equals(qq)) {
			str = "认证成功!!!\n\n";
			str += "控制的意义:\n";
			str += "\n" + qq.toString() + "\n\n 匹配的X !!!";

			JOptionPane.showMessageDialog(null, str, "Success!",
					JOptionPane.INFORMATION_MESSAGE);
		} else {
			str = "认证错误!!!\n\n";
			str += "控制的意义:\n";
			str += "\n" + qq.toString() + "\n\n不匹配的 X !!!";

			JOptionPane.showMessageDialog(null, str, "Error!",
					JOptionPane.ERROR_MESSAGE);
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
		return publicKeys;
	}

	public BigInteger getPrivateKey() {
		return privateKey;
	}
}
