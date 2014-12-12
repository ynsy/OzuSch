public class PasswordEncryption {
	public static String mixPassword(String password) throws Exception {
		//This methods mix password then encrypt it
		// if you want to use it. it so simple just call PasswordEncryption.mixPassword(password);
		String mixedPassword = getUnicode(password
				.charAt(password.length() - 2))
				+ password
				+ getUnicode(password.charAt(2));

		// System.out.println(mixedPassword);
		// System.out.println("password: " + MD5Encryp.createMD5(password));
		// System.out.println("mixedPassword: "+
		// MD5Encryp.createMD5(mixedPassword));

		mixedPassword = MD5Encryption.createMD5(password);

		mixedPassword = AESEncryption.encryptData(mixedPassword);

		//System.out.println("base 64: " + mixedPassword);
		
		mixedPassword = MD5Encryption.createMD5(mixedPassword);
		
		return mixedPassword;
	}

	public static String getUnicode(char c) {
		String unicode = Integer.toHexString(c);
		return unicode;
	}
}
