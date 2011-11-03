package FourthManda.Part1Bullet5;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
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

public class Encrypter {

	private static final String password = "bdsp08-06";
	private static final String plaintext = "The quick brown fox jumps over the lazy dog.";

	public static void main(String args[]) throws Exception {
		// Create Key
		byte key[] = password.getBytes();
		DESKeySpec desKeySpec = new DESKeySpec(key);
		SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
		SecretKey secretKey = keyFactory.generateSecret(desKeySpec);

		// Create Cipher
		Cipher desCipher = Cipher.getInstance("DES/ECB/PKCS5Padding");
		desCipher.init(Cipher.ENCRYPT_MODE, secretKey);

		// Create outputstream
		FileOutputStream fos = new FileOutputStream("out.txt");
		BufferedOutputStream bos = new BufferedOutputStream(fos);
		CipherOutputStream cos = new CipherOutputStream(bos, desCipher);
		ObjectOutputStream oos = new ObjectOutputStream(cos);

		// Write
		oos.writeUTF(plaintext);
		oos.flush();
		oos.close();
	}
}