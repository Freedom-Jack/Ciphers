package hash;

import java.security.Key;
import java.security.NoSuchAlgorithmException;

import javax.crypto.Cipher;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;

import util.CryptoTools;
import util.CustomizedHelpers;

public class MAC {

	// Hash application for MAC
	public static void main(String[] args) throws Exception {
		
		byte[] message = "No one can make you feel inferior without your consent.".getBytes();
		byte[] secretKey = "checkhospitalsup".getBytes(); 
		
		byte[] result = mac_general(message, secretKey, "MD5", "AES", "AES/ECB/NoPadding");
		
		System.out.println(CryptoTools.bytesToHex(result));
	}
	
	public static byte[] mac_general(byte[] m, byte[] k, String hashFunction, String keyInstance, String cipherInstance) throws Exception {
		
		// Symmetric cipher generation
		Key symmetric_key = new SecretKeySpec(k, keyInstance);
		Cipher aes_cipher = Cipher.getInstance(cipherInstance);
		aes_cipher.init(Cipher.ENCRYPT_MODE, symmetric_key);
		
		// Calculate the hash of the message
		byte[] messageHash = Signature.generalHash(m, hashFunction);
		
		// Send the encrypted hash value
		return aes_cipher.doFinal(messageHash);
	}
}
