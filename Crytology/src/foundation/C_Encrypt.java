package foundation;

import util.CryptoTools;

public class C_Encrypt {
	
	// Caesar encryption
	public static void main(String[] args) throws Exception {

		/*
		 * Read the file in bytes, and then clean up all the spaces, and store it into a
		 * file.
		 */
		byte[] rawpt = CryptoTools.fileToBytes("data/MSG1.pt");
		byte[] pt = CryptoTools.clean(rawpt);
		CryptoTools.bytesToFile(pt, "data/MSG1.clean");

		// Encrypt with Caesar cipher and store in a file
		byte[] ct = caesarEncrypt(pt, 19);
		CryptoTools.bytesToFile(ct, "data/MSG1.ct");

		// Calculate the MD5 string
		String MD5 = CryptoTools.getMD5(ct);

		// Prompt the answer
		System.out.println("My MD5: " + MD5);
		System.out.println("My answer is: " + MD5.equals("2C422B741EF90FD4424EBC83692398B0"));

		// Prompt the IC
		System.out.println("IC is: " + CryptoTools.getIC(pt));
	}
	
	// Method for encrypting Caesar cipher using a given key
	public static byte[] caesarEncrypt(byte[] plain, int key) {

		byte[] cipher = new byte[plain.length];

		// Caesar encryption with the specified key
		for (int i = 0; i < plain.length; i++) {
			cipher[i] = (byte) ((plain[i] - 'A' + key) % 26 + 'A');
		}

		return cipher;
	}
}
