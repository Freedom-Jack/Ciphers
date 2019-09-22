package foundation;

import util.CryptoTools;

public class C_Crypta {
	
	// Cryptanalysis attack on Caesar cipher
	public static void main(String[] args) throws Exception {

		// Read the file in bytes
		byte[] rawct = CryptoTools.fileToBytes("data/MSG2.ct");
		byte[] ct = CryptoTools.clean(rawct);

		int[] letterCounts = CryptoTools.getFrequencies(ct);
		double[] freq = new double[26];
		for (int i = 0; i < 26; i++) {
			freq[i] = (double) letterCounts[i] / ct.length;

			System.out.print(freq[i] + "\n");
		}
	}
}
