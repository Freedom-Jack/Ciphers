package rehearsal;

import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.security.spec.AlgorithmParameterSpec;

import javax.crypto.Cipher;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import util.CryptoTools;

public class R1Q4 {

	// Simple 128-bit AES, CBC, PKCS5 padding
	public static void main(String[] args) throws Exception {

		// Input values
		byte[] key = CryptoTools.hexToBytes("444F204E4F542054454C4C2045564521");
		byte[] iv = CryptoTools.hexToBytes("20FC19123087BF6CAC8D0F1254123004");
		byte[] ct = CryptoTools.hexToBytes("FB0692B011F74F8BF77EDE2630852C1700C204407EDF2222D965F74A8BCEB236");

		Key key_aes = new SecretKeySpec(key, "AES");
		Cipher aes_cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
		AlgorithmParameterSpec aps = new IvParameterSpec(iv);

		aes_cipher.init(Cipher.DECRYPT_MODE, key_aes, aps);
		byte[] result = aes_cipher.doFinal(ct);

		System.out.println(new String(result));
	}
}
