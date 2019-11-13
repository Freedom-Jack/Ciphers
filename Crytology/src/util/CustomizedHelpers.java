package util;

import java.io.ByteArrayOutputStream;
import java.math.BigInteger;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.RSAPrivateKeySpec;
import java.security.spec.RSAPublicKeySpec;

import javax.crypto.Cipher;

public class CustomizedHelpers {

	public static void main(String[] args) {

	}

	// Method for RSA encryption/decryption
	public static byte[] rsaGeneral(BigInteger modulo, BigInteger theKey, byte[] text, boolean is_encrypt,
			String keyInstance, String cipherInstance) throws Exception {

		KeyFactory keyFactory = KeyFactory.getInstance(keyInstance); // Key initialization
		Cipher rsa_cipher = Cipher.getInstance(cipherInstance); // Cipher initialization

		// Key generations and cipher setup
		if (is_encrypt) {
			RSAPublicKeySpec pubSpec = new RSAPublicKeySpec(modulo, theKey);
			PublicKey pub = keyFactory.generatePublic(pubSpec);
			rsa_cipher.init(Cipher.ENCRYPT_MODE, pub);
		} else {
			RSAPrivateKeySpec privSpec = new RSAPrivateKeySpec(modulo, theKey);
			PrivateKey priv = keyFactory.generatePrivate(privSpec);
			rsa_cipher.init(Cipher.DECRYPT_MODE, priv);
		}

		// Encrypt/Decrypt the cipher, and return the result
		byte[] pt = rsa_cipher.doFinal(text);
		return pt;
	}
	
	// Method for concatenating two byte arrays
	public static byte[] twoByteArraysConcatenation(byte[] a, byte[] b) throws Exception {

		// Using an output stream for bytes
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

		outputStream.write(a);
		outputStream.write(b);

		return outputStream.toByteArray();
	}
}
