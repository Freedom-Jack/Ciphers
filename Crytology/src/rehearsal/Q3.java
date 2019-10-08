package rehearsal;

import java.io.ByteArrayOutputStream;
import java.security.Key;
import java.util.Arrays;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

import util.CryptoTools;

public class Q3 {

	public static void main(String[] args) throws Exception {

		// Initialize string inputs for one test case
		byte[] key = CryptoTools.hexToBytes("4F75725269676874");
		byte[] iv = CryptoTools.hexToBytes("496E566563746F72");
		// CT must have length of 8n
		byte[] ct = CryptoTools.hexToBytes("7AA38A029E773CBBC188A9FCEADAE99DA560B784C99AFEF2");

		// Check if the text is size of multiples of 8
		if (ct.length % 8 != 0)
			throw new Exception();

		// Decipher each block and store the result in an output stream
		ByteArrayOutputStream result = new ByteArrayOutputStream();

		for (int i = 0; i < ct.length; i += 8) {
			// Decipher the current block and update the passed iv (previous ct)
			byte[] mediateResult = cryptoEngine(key, iv, Arrays.copyOfRange(ct, i, i + 8), false, i + 8 >= ct.length);
			iv = Arrays.copyOfRange(ct, i, i + 8);

			// Write to output stream
			result.write(mediateResult);
		}

		System.out.println("The plain text: >" + result.toString() + "<");
	}

	// CryptoEngine for one single block, return the result in byte array
	public static byte[] cryptoEngine(byte[] key, byte[] iv, byte[] text, boolean is_encrypt, boolean is_last) throws Exception {

		// Instantiate cipher
		Key symmetric_key = new SecretKeySpec(key, "DES");
		Cipher customized_cipher = Cipher.getInstance("DES/ECB/NoPadding");
		Cipher last_cipher = Cipher.getInstance("DES/ECB/PKCS5Padding");
		
		// Variables set up
		byte[] text_temp = text.clone();
		byte[] result = new byte[text.length];
		
		// Cipher set up
		if (is_encrypt) {
			customized_cipher.init(Cipher.ENCRYPT_MODE, symmetric_key);
			last_cipher.init(Cipher.ENCRYPT_MODE, symmetric_key);
		}
		else {
			customized_cipher.init(Cipher.DECRYPT_MODE, symmetric_key);
			last_cipher.init(Cipher.DECRYPT_MODE, symmetric_key);
			
			text_temp = xor(text, iv);
		}
		
		// Cipher process
		if (is_last)
			result = last_cipher.doFinal(text_temp);
		else
			result = customized_cipher.doFinal(text_temp);

		if (is_encrypt) {
			return xor(result, iv);
		}

		return result;
	}

	// Function for xor two byte array
	public static byte[] xor(byte[] ar1, byte[] ar2) throws Exception {

		if (ar1.length != ar2.length)
			throw new Exception();

		byte[] result = new byte[ar1.length];
		for (int i = 0; i < ar1.length; i++) {
			result[i] = (byte) (ar1[i] ^ ar2[i]);
		}

		return result;
	}
}
