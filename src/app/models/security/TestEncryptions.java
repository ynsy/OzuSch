/*
Created by ynsy || Yunus YILMAZ || 
*/

import java.util.Scanner;

public class TestEncryptions {

	public static void main(String[] args) throws Exception {
		@SuppressWarnings("resource")
		Scanner input = new Scanner(System.in);
		System.out.println("Write password: ");
		String password = input.nextLine();
		// String passwordEnc = AESEncryption.encrypt(password);
		// String passwordDec = AESEncryption.decrypt(passwordEnc);
		// String passwordEnc = MD5Encryption.createMD5(password);
		System.out.println("Plain Text : " + password);
		System.out.println(PasswordEncryption.mixPassword(password));
		// System.out.println("Encrypted Text : " + passwordEnc);
		// System.out.println("Decrypted Text : " + passwordDec);

		// a2c9a5d635f96695f809ce5272736ec5 // yunus
		// QUYtMA3WvHGKdV3xgkPzBQyoXaHtgBe6ZEYI+qwptGunZQJzqT+PZgJEG1NfN3ns
	}
}
