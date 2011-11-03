package FourthManda.Part1Bullet7;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import javax.crypto.Cipher;
import javax.crypto.CipherInputStream;
import javax.crypto.CipherOutputStream;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;

public class Encryption {
	private static final String password = "bdsp08-06";
	
	public static String encrypt(String message){
		String s = message;
		try{
		// Create Key
		byte key[] = password.getBytes();
		DESKeySpec desKeySpec = new DESKeySpec(key);
		SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
		SecretKey secretKey = keyFactory.generateSecret(desKeySpec);

		// Create Cipher
		Cipher desCipher = Cipher.getInstance("DES/ECB/PKCS5Padding");
		desCipher.init(Cipher.ENCRYPT_MODE, secretKey);

		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		
		byte[] msg = desCipher.doFinal(message.getBytes());
		baos.write(msg.length);
		baos.write(msg);
		
		s = baos.toString();
		}catch(Exception e){
			System.out.println("Something went wrong.");
			e.printStackTrace();
		}
		
		return s;
	}
	
	public static String decrypt(String message){
		String s = message;
		try{
		// Create Key
		byte key[] = password.getBytes();
		DESKeySpec desKeySpec = new DESKeySpec(key);
		SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
		SecretKey secretKey = keyFactory.generateSecret(desKeySpec);

		// Create Cipher
		Cipher desCipher = Cipher.getInstance("DES/ECB/PKCS5Padding");
		desCipher.init(Cipher.DECRYPT_MODE, secretKey);
		
		ByteArrayInputStream bais = new ByteArrayInputStream(message.getBytes());

		byte[] b = new byte[bais.read()];
		bais.read(b);
		
		s = new String(desCipher.doFinal(b)); 
		
		}catch(Exception e){
			System.out.println("Something went wrong.");
			e.printStackTrace();
		}
		
		return s;
	}
}
