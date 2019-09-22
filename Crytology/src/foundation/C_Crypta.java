package foundation;

import util.CryptoTools;

public class C_Crypta {
	
	// Cryptanalysis attack on Caesar cipher
	public static void main(String[] args) throws Exception {

		// Read the file in bytes
		byte[] rawct = CryptoTools.fileToBytes("data/MSG2.ct");
		byte[] ct = CryptoTools.clean(rawct);

		int key = caesarDecryptCrypta(ct);
		
		// Print out the key
		System.out.println("The key is most likely to be: " + key);
	}
	
	// Method for decrypting Caesar cipher cryptanalyticly, and return the possible key
	public static int caesarDecryptCrypta(byte[] cipher) {
		
		int[] letterCounts = CryptoTools.getFrequencies(cipher);
		double[] freq = new double[26];
		for (int i = 0; i < 26; i++) {
			freq[i] = (double) letterCounts[i] / cipher.length;

			//System.out.println("Letter: " + i + "; Frequency: " + freq[i]);
		}
		
		// Find the letter has maximum frequency, which is most likely to be 'E' in pt
		int maxFreqPos = findMaxPos(freq);
		
		return (maxFreqPos - 4 + 26) % 26;
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
