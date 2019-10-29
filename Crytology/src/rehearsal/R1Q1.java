package rehearsal;

import foundation.V_Crypta;
import util.CryptoTools;

public class R1Q1 {
	
	// Caesar cipher decryption
	public static void main(String[] args) throws Exception {

		// Read the file in bytes
		byte[] rawct = CryptoTools.fileToBytes("data/R1Q1.ct");
		byte[] ct = CryptoTools.clean(rawct);

		// Invoke the cryptanalytic method to get the plain text
		int[] integersKey = V_Crypta.vigenereDecryptCrypta(ct);
		byte[] byteKey = V_Crypta.convertKey(integersKey);
		byte[] pt = V_Crypta.vigenereDecipher(ct, integersKey);

		// Print out the key word
		System.out.println("The key is most likely to be: " + new String(byteKey));
		System.out.println(new String(pt));
	}
}
