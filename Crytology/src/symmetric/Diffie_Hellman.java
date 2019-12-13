package symmetric;

import java.math.BigInteger;
import java.security.Key;
import java.util.Arrays;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

import util.CryptoTools;

public class Diffie_Hellman {

	public static void main(String[] args) throws Exception {
		
		// Diffie-Hellman parameters
		BigInteger p = new BigInteger("1426978031065901624399459");  	//prime modulus
		// BigInteger g = new BigInteger("142983226354603241203899");   	//primitive root
		BigInteger aX = new BigInteger("151084547242378126197174");  	//Alice's DH private
		BigInteger bY = new BigInteger("1107944920143556495858904"); 	//Bob's DH private
		// The received DES/ECB/PKCS5Padding cipher text in hex string
		String ct = "C651F654C658F15B";
		int size = 64;
		
		// Generated the shared symmetric key, of the specified size
		BigInteger dh_key = bY.modPow(aX, p);
		byte[] key = Arrays.copyOfRange(dh_key.toByteArray(), 0, size / 8);
		
		// Text in bytes
		byte[] text = CryptoTools.hexToBytes(ct);

		// Cipher setup
		Key symmetric_key = new SecretKeySpec(key, "DES");
		Cipher my_cipher = Cipher.getInstance("DES/ECB/PKCS5Padding");

		// Cipher process
		my_cipher.init(Cipher.DECRYPT_MODE, symmetric_key);

		// Output the plain text
		byte[] result = my_cipher.doFinal(text);
		System.out.println("The plain text: >" + new String(result) + "<");
	}
	
	// Method for Diffie Hellman secret key generation
	public static BigInteger diffiehellmanKey(BigInteger modulus, BigInteger root, BigInteger a, BigInteger b) {
		return root.modPow(a, modulus).modPow(b, modulus); 
	}
}
