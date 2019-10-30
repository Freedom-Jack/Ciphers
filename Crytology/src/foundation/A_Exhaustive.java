package foundation;

import util.CryptoTools;
import java.math.*;

public class A_Exhaustive {
	
	// Exhaustive attack on Affine Cipher
	public static void main(String[] args) throws Exception {

		// Read the file in bytes
		byte[] rawct = CryptoTools.fileToBytes("data/MSG3.ct");
		byte[] ct = CryptoTools.clean(rawct);
		
		int[] alphaCandidate = { 1, 3, 5, 7, 9, 11, 15, 17, 19, 21, 23, 25 };
		
		// Find the position of alpha in the alphaCanadidate array
		int position = affineDecryptExhaust(ct);

		// Print the key and prompt
		int keyAlphaIndex = position / 26;
		int keyAlpha = alphaCandidate[keyAlphaIndex];
		int keyBeta = position - 26 * keyAlphaIndex;
		System.out.println("The key is most likely to be: (" + keyAlpha + ", " + keyBeta + ")");
		System.out.println(new String(affineDecrypt(ct, keyAlpha, keyBeta)));
	}

	
	// Method for decrypting Caesar cipher exhaustively, and return the position of the possible key
	public static int affineDecryptExhaust(byte[] cipher) {
		
		int[] alphaCandidate = { 1, 3, 5, 7, 9, 11, 15, 17, 19, 21, 23, 25 };

		int counter = 0;
		double[] dotProductArray = new double[312];

		for (int i = 0; i < alphaCandidate.length; i++) {
			for (int j = 0; j < 26; j++) {

				// Decipher by the attempted key
				byte[] candidate = affineDecrypt(cipher, alphaCandidate[i], j);

				// Calculate the frequencies array
				int[] letterCounts = CryptoTools.getFrequencies(candidate);
				double[] freq = new double[26];
				for (int k = 0; k < 26; k++) {
					freq[k] = (double) letterCounts[k] / candidate.length;
				}

				// Calculate the dot product and prompt
				dotProductArray[counter] = dotProduct(freq, CryptoTools.ENGLISH);
				//System.out.println("Key: (" + alphaCandidate[i] + ", " + j + "); " + "Dot product: " + dotProductArray[counter]);
				counter++;
			}
		}
		
		return findMaxPos(dotProductArray);
	}
	
	
	// Affine cipher decryption by the given key
	public static byte[] affineDecrypt(byte[] cipher, int alpha, int beta) {

		byte[] answer = new byte[cipher.length];

		int alphaInverse = BigInteger.valueOf(alpha).modInverse(BigInteger.valueOf(26)).intValue();
		for (int i = 0; i < cipher.length; i++) {
			answer[i] = (byte) (((alphaInverse * (cipher[i] - 'A' - beta)) % 26 + 26) % 26 + 'A');
		}

		return answer;
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
