package symmetric;

import util.CryptoTools;
import java.security.Key;
import java.security.spec.AlgorithmParameterSpec;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

public class AES_CBC_PKCS5Padding {

	public static void main(String[] args) throws Exception {

		// String inputs for one test case
		String key_temp = "DO NOT TELL EVE!";
		String iv_temp = "20FC19123087BF6CAC8D0F1254123004";
		String ct_temp = "3188073EA5DB3F5C05B6307B3595607135F5D4B22F2C3EB710AA31377F78B997";
		
		// Invoke the method in decrypt mode
		aesPaddingCipher(key_temp, iv_temp, ct_temp, false);
	}

	/**
	 * @param string_key
	 * @param string_iv
	 * @param string_ct
	 * @throws Exception
	 */
	public static void aesPaddingCipher(String string_key, String string_iv, String string_text, boolean is_encrypt)
			throws Exception {

		/*
		 * Key and Initial value, 
		 * Note: The retrieve method for byte array might need to adapt manually
		 */
		byte[] key = string_key.getBytes();
		byte[] iv = CryptoTools.hexToBytes(string_iv);
		byte[] text = CryptoTools.hexToBytes(string_text);

		// Cipher setup
		Key symmetric_key = new SecretKeySpec(key, "AES");
		Cipher aes_cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
		AlgorithmParameterSpec aps = new IvParameterSpec(iv);

		// Cipher process
		if (is_encrypt)
			aes_cipher.init(Cipher.ENCRYPT_MODE, symmetric_key, aps);
		else
			aes_cipher.init(Cipher.DECRYPT_MODE, symmetric_key, aps);

		// Output the plain text
		byte[] result = aes_cipher.doFinal(text);
		System.out.println("The plain text: >" + new String(result) + "<");
	}
}
