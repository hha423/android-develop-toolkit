package cn.code.alogrithon.rsa;

import java.security.Key;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

import javax.crypto.Cipher;

public class RSAUtils {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			KeyPairGenerator kpg = KeyPairGenerator.getInstance("RSA");
			kpg.initialize(2048);
			KeyPair kp = kpg.genKeyPair();
			Key publicKey = kp.getPublic();
			Key privateKey = kp.getPrivate();
			
			System.out.println(publicKey.getFormat());
			System.out.println(privateKey.getFormat());
			
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		


		try {
		    byte[] input = "aa".getBytes();
		    Cipher cipher = Cipher.getInstance("RSA/None/NoPadding", "BC");
		    SecureRandom random = new SecureRandom();
		    KeyPairGenerator generator = KeyPairGenerator.getInstance("RSA", "BC");

		    generator.initialize(256, random);
		    KeyPair pair = generator.generateKeyPair();
		    Key pubKey = pair.getPublic();
		    Key privKey = pair.getPrivate();

		    cipher.init(Cipher.ENCRYPT_MODE, pubKey, random);

		    byte[] cipherText = cipher.doFinal(input);
		    System.out.println("cipher: " + new String(cipherText));
		    cipher.init(Cipher.DECRYPT_MODE, privKey);
		    byte[] plainText = cipher.doFinal(cipherText);
		    System.out.println("plain : " + new String(plainText));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
	}

}
