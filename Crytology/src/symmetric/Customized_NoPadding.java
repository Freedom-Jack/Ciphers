package symmetric;

import java.io.ByteArrayOutputStream;
import java.security.Key;
import java.security.spec.AlgorithmParameterSpec;
import java.util.Arrays;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import util.CryptoTools;

public class Customized_NoPadding {

	/*
	 * This algorithm is DES, CBC, NO PADDING, 
	 * but the initial value is negated before xor with the plain text
	 */
	public static void main(String[] args) throws Exception {

		// Initialize string inputs for one test case
		byte[] key = CryptoTools.hexToBytes("6B79466F724D4F50");
		byte[] iv = CryptoTools.hexToBytes("6976466F724D4F50");
		byte[] ct = CryptoTools.hexToBytes("437DBAB5607137A5CFC1031114634087"); // Must have length of 8n

		// Check if the text is size of multiples of 8
		if (ct.length % 8 != 0)
			throw new Exception();

		// Decipher each block and store the result in an output stream
		ByteArrayOutputStream result = new ByteArrayOutputStream();

		for (int i = 0; i < ct.length; i += 8) {
			// Decipher the current block and update the passed iv
			byte[] mediateResult = cryptoEngine(key, iv, Arrays.copyOfRange(ct, i, i + 8), false);
			iv = Arrays.copyOfRange(ct, i, i + 8);

			// Write to output stream
			result.write(mediateResult);
		}

		System.out.println("The plain text: >" + result.toString() + "<");
	}

	public static byte[] cryptoEngine(byte[] key, byte[] iv, byte[] text, boolean is_encrypt) throws Exception {

		// Generate a negated iv
		byte[] negated_iv = new byte[iv.length];
		for (int i = 0; i < iv.length; i++) {
			negated_iv[i] = (byte) ~iv[i];
		}

		// Cipher setup
		Key symmetric_key = new SecretKeySpec(key, "DES");
		Cipher customized_cipher = Cipher.getInstance("DES/CBC/NoPadding");
		AlgorithmParameterSpec aps = new IvParameterSpec(negated_iv);

		// Cipher process
		if (is_encrypt)
			customized_cipher.init(Cipher.ENCRYPT_MODE, symmetric_key, aps);
		else
			customized_cipher.init(Cipher.DECRYPT_MODE, symmetric_key, aps);

		return customized_cipher.doFinal(text);
	}
}
