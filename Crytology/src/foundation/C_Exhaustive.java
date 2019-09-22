package foundation;

import util.CryptoTools;

public class C_Exhaustive {
	
	// Exhaustive attack on Caesar Cipher
	public static void main(String[] args) throws Exception {

		// Read the file in bytes
		byte[] rawct = CryptoTools.fileToBytes("data/MSG2.ct");
		byte[] ct = CryptoTools.clean(rawct);

		int key = caesarDecryptExhaust(ct);
		System.out.println("The key is most likely to be: " + key);
		System.out.println(new String(caesarDecrypt(ct, key)));
	}

	// Method for decrypting Caesar cipher exhaustively, and return the possible key
	public static int caesarDecryptExhaust(byte[] cipher) {

		double[] dotProductArray = new double[26];

		for (int i = 0; i < 26; i++) {
			byte[] candidate = caesarDecrypt(cipher, i);
			
			// Calculate the frequencies array
			int[] letterCounts = CryptoTools.getFrequencies(candidate);
			double[] freq = new double[26];
			for (int j = 0; j < 26; j++) {
				freq[j] = (double) letterCounts[j] / candidate.length;
			}
			
			// Calculate the dot product and prompt
			dotProductArray[i] = dotProduct(freq, CryptoTools.ENGLISH);
			//System.out.println("Key: " + i + "; " + "Dot product: " + dotProductArray[i]);
		}

		return findMaxPos(dotProductArray);
	}

	// Method for decrypting Caesar cipher using a given key
	public static byte[] caesarDecrypt(byte[] cipher, int key) {

		byte[] plain = new byte[cipher.length];

		// Caesar decryption with the specified key
		for (int i = 0; i < cipher.length; i++) {
			plain[i] = (byte) (((cipher[i] - 'A' - key) % 26 + 26) % 26 + 'A');
		}

		return plain;
	}

	// Method for calculating dot product for 2 int arrays
	public static double dotProduct(double[] ar1, double[] ar2) {

		if (ar1.length != ar2.length)
			return 0;

		double answer = 0;
		for (int i = 0; i < ar1.length; i++) {
			answer += ar1[i] * ar2[i];
		}

		return answer;
	}
	
	// Method for finding the max value's position in an array
	public static int findMaxPos(double[] ar) {
		
		// Selective selection
		double temp = 0;
		int pos = 0;
		
		for (int i = 0; i < ar.length; i++) {
			if (ar[i] > temp) {
				temp = ar[i];
				pos = i;
			}
		}
		
		return pos;
	}
}
