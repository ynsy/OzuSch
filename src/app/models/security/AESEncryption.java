/*
Created by ynsy || Yunus YILMAZ || 
*/

import java.security.*;

import javax.crypto.*;
import javax.crypto.spec.SecretKeySpec;

import sun.misc.*;

public class AESEncryption {

	private static final String CIPHER_SUITE = "AES";
	private static final byte[] keyValue = new byte[] { 'O', 'z', 'U', 'S',
			'c', 'h', 'Y', 'y', 'O', 'a', 'B', 'k', 'B', 'a', 'V', 'h' };

	public static String encrypt(String Data) throws Exception {
		Key key = generateKey();
		Cipher c = Cipher.getInstance(CIPHER_SUITE);
		c.init(Cipher.ENCRYPT_MODE, key);
		byte[] encVal = c.doFinal(Data.getBytes());
		String encryptedValue = new BASE64Encoder().encode(encVal);
		return encryptedValue;
	}

	public static String decrypt(String encryptedData) throws Exception {
		Key key = generateKey();
		Cipher c = Cipher.getInstance(CIPHER_SUITE);
		c.init(Cipher.DECRYPT_MODE, key);
		byte[] decordedValue = new BASE64Decoder().decodeBuffer(encryptedData);
		byte[] decValue = c.doFinal(decordedValue);
		String decryptedValue = new String(decValue);
		return decryptedValue;
	}

	private static Key generateKey() throws Exception {
		Key key = new SecretKeySpec(keyValue, CIPHER_SUITE);
		return key;
	}

}
