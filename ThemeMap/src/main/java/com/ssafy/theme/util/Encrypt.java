package com.ssafy.theme.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

import org.springframework.stereotype.Component;

@Component
public class Encrypt {
	
	private static Encrypt instance = new Encrypt();
	
	public static Encrypt getInstance() {
		return instance;
	}
	
	public String EncryptPwd(String pw) {
		Encrypt en = new Encrypt();

		String salt = en.getSalt();
		
		String res = en.getEncrypt(pw, salt);
		
		return res;
	}

	public String getSalt() {
		SecureRandom  r = new SecureRandom ();
		byte[] salt = new byte[20];
		
		r.nextBytes(salt);

		StringBuffer sb = new StringBuffer();
		for(byte b : salt) {
			sb.append(String.format("%02x", b));
		};
		
		return sb.toString();
	}
	
	public String getEncrypt(String pw, String salt) {
		
		String result = "";
		
		try {
			MessageDigest md = MessageDigest.getInstance("SHA-256");
			
			md.update((pw+salt).getBytes());
			byte[] pwdsalt = md.digest();
			
			StringBuffer sb = new StringBuffer();
			for (byte b : pwdsalt) {
				sb.append(String.format("%02x", b));
			}
			
			result=sb.toString();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}

		return result;
	}
	
}