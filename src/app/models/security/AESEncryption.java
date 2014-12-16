import java.security.*;

import javax.crypto.*;
import javax.crypto.spec.SecretKeySpec;

import sun.misc.*;

public class AESEncryption {

	private static final String CIPHER_SUITE = "AES";
	private static final byte[] keyValue = new byte[] { 'O', 'z', 'U', 'S',
			'c', 'h', 'Y', 'y', 'O', 'a', 'B', 'k', 'B', 'a', 'V', 'h' };

	@SuppressWarnings("restriction")
	public static String encryptData(String Data) throws Exception {
		Key key = generateKey();
		Cipher cipher = Cipher.getInstance(CIPHER_SUITE);
		cipher.init(Cipher.ENCRYPT_MODE, key);
		byte[] encodedData = cipher.doFinal(Data.getBytes());
		String encryptedData = new BASE64Encoder().encode(encodedData);
		return encryptedData;
	}

	private static Key generateKey() throws Exception {
		Key key = new SecretKeySpec(keyValue, CIPHER_SUITE);
		return key;
	}

}

arardajadjaskd
