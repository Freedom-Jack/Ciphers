package symmetric;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.Key;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

import util.CryptoTools;

public class DES_EBC_Twice {
	
	// The encryption is as follows: CT = E[~K, E(K, PT)]
	public static void main(String[] args) throws Exception {
		
		// Key and file cipher text input
		byte[] key = CryptoTools.hexToBytes("7072616374696365");
		String string_ct = Files.readString(Paths.get("data/check3.txt"));
		byte[] ct = CryptoTools.hexToBytes(string_ct);
		
		// Create the negated key
		byte[] negated_key = new byte[key.length];
		for (int i = 0; i < key.length; i++) {
			negated_key[i] = (byte) ~key[i];
		}
		
		// Instantiate cipher
		Key symmetric_key = new SecretKeySpec(negated_key, "DES");
		Cipher des_cipher = Cipher.getInstance("DES/ECB/NoPadding");
		
		// Decryption1: Key = ~K
		des_cipher.init(Cipher.DECRYPT_MODE, symmetric_key);
		byte[] result1 = des_cipher.doFinal(ct);
		
		// Decryption2: Key = K
		symmetric_key = new SecretKeySpec(key, "DES");
		des_cipher.init(Cipher.DECRYPT_MODE, symmetric_key);
		byte[] result2 = des_cipher.doFinal(result1);
		
		// Output the final answer
		System.out.println("The plain text: >" + new String(result2) + "<");
	}
}
