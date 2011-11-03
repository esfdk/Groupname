package FourthManda.Part1Bullet5;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.ObjectInputStream;

import javax.crypto.Cipher;
import javax.crypto.CipherInputStream;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;

public class Decrypter {
	private static final String password = "bdsp08-06";
	private static final String plaintext = "The quick brown fox jumps over the lazy dog.";
	
	public static void main(String args[]) throws Exception {
		byte key[] = password.getBytes();
		DESKeySpec desKeySpec = new DESKeySpec(key);
		SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
		SecretKey secretKey = keyFactory.generateSecret(desKeySpec);
		

		// Create cipher mode
		Cipher desCipher = Cipher.getInstance("DES/ECB/PKCS5Padding");
		desCipher.init(Cipher.DECRYPT_MODE, secretKey);

		// Create stream
		FileInputStream fis = new FileInputStream("out.txt");
		BufferedInputStream bis = new BufferedInputStream(fis);
		CipherInputStream cis = new CipherInputStream(bis, desCipher);
		ObjectInputStream ois = new ObjectInputStream(cis);

		// Read 
		String plaintext2 = ois.readUTF();
		ois.close();

		// Compare
		System.out.println(plaintext);
		System.out.println(plaintext2);
		System.out.println("Identical? " + (plaintext.equals(plaintext2)));
	}
}
