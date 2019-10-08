package symmetric;

import java.security.Key;
import java.security.spec.AlgorithmParameterSpec;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import util.CryptoTools;

public class DES_CBC_PKCS5Padding {

	// The process is nearly the same as AES
	public static void main(String[] args) throws Exception {

		// String inputs for one test case
		String key_temp = "CSE@YORK";
		String iv_temp = "4E51297B424F90D8";
		String ct_temp = "B2ACD6ADF010DDC4";

		// Invoke the method in decrypt mode
		desPaddingCipher(key_temp, iv_temp, ct_temp, false);
	}

	/**
	 * @param string_key
	 * @param string_iv
	 * @param string_ct
	 * @throws Exception
	 */
	public static void desPaddingCipher(String string_key, String string_iv, String string_text, boolean is_encrypt)
			throws Exception {

		/*
		 * Key and Initial value
		 * Note: The retrieve method for byte array might need to adapt manually
		 */
		byte[] key = string_key.getBytes();
		byte[] iv = CryptoTools.hexToBytes(string_iv);
		byte[] text = CryptoTools.hexToBytes(string_text);

		// Cipher setup
		Key symmetric_key = new SecretKeySpec(key, "DES");
		Cipher des_cipher = Cipher.getInstance("DES/CBC/PKCS5Padding");
		AlgorithmParameterSpec aps = new IvParameterSpec(iv);
		
		// Cipher process
		if (is_encrypt)
			des_cipher.init(Cipher.ENCRYPT_MODE, symmetric_key, aps);
		else
			des_cipher.init(Cipher.DECRYPT_MODE, symmetric_key, aps);

		// Output the plain text
		byte[] result = des_cipher.doFinal(text);
		System.out.println("The plain text: >" + new String(result) + "<");
	}
}
