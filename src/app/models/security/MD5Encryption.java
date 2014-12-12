public class MD5Encryption {

	public static String createMD5(String data) {
		try {
			java.security.MessageDigest messageDigest = java.security.MessageDigest
					.getInstance("MD5");
			byte[] byteArray = messageDigest.digest(data.getBytes());
			StringBuffer stringBuffer = new StringBuffer();
			for (int i = 0; i < byteArray.length; i++) {
				stringBuffer.append(Integer.toHexString(
						(byteArray[i] & 0xFF) | 0x100).substring(1, 3));
			}
			return stringBuffer.toString();
		} catch (java.security.NoSuchAlgorithmException e) {
		}
		return null;
	}
}