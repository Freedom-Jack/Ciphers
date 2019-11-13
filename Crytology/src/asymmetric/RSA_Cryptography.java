package asymmetric;

import java.math.BigInteger;
import java.security.*;
import java.security.spec.RSAPrivateKeySpec;
import java.security.spec.RSAPublicKeySpec;

import javax.crypto.Cipher;

public class RSA_Cryptography {

	// An example for a RSA decryption, with no padding
	public static void main(String[] args) throws Exception {

		// The modulus, and the pair of asymmetric keys
		BigInteger n = new BigInteger(
				"94587468335128982981605019776781234618384857805657005686084562260910788622013722070926491690843853690071248130134427832324966728582532832363221542231787068203763027067400082835394459857525017707284768411819006776211493735326500782954621660256501187035611332577696332459049538105669711385995976912007767106063");
		BigInteger e = new BigInteger("74327");
		BigInteger d = new BigInteger(
				"7289370196881601766768920490284861650464951706793000236386405648425161747775298344104658393385359209126267833888223695609366844098655240542152017354442883676634193191857568369042999854440242050353181703706753485749165295123694487676952198090537385200990850805837963871485320168470788328336240930212290450023");
		// The cipher text
		BigInteger c = new BigInteger(
				"87014856975716299121085087309577038316883175412853820115551293556230488405826385706604303724175236985573832006395540199066061101502996745421485579743246846982636317440505885092956723199407403632041108913018671613508572002898008615700858579079601105011909417884801902333329415712320494308682279897714456370814");
		
		// Decipher the cipher text using the private key
		byte[] result = rsaCipher(n, d, c.toByteArray(), false);
		BigInteger encryptedAgain = new BigInteger(rsaCipher(n, e, result, true));
        
		// Print out the result messages
		String msg = encryptedAgain.compareTo(c) == 0 ? "True" : "False";
        System.out.println("The plaintext: " + new String(result).trim());
        System.out.println("The encrypted plaintext is same as the original ciphertext: " + msg);
	}
	
	// Method for RSA encryption/decryption
	public static byte[] rsaCipher(BigInteger modulo, BigInteger theKey, byte[] text, boolean is_encrypt) throws Exception{
		
		KeyFactory keyFactory = KeyFactory.getInstance("RSA");			// Key initialization
		Cipher rsa_cipher = Cipher.getInstance("RSA/ECB/NoPadding");	// Cipher initialization
		
		// Key generations and cipher setup
		if(is_encrypt) {
			RSAPublicKeySpec pubSpec = new RSAPublicKeySpec(modulo, theKey); 
			PublicKey pub = keyFactory.generatePublic(pubSpec);
		    rsa_cipher.init(Cipher.ENCRYPT_MODE, pub);
		}
		else {
			RSAPrivateKeySpec privSpec = new RSAPrivateKeySpec(modulo, theKey);
		    PrivateKey priv = keyFactory.generatePrivate(privSpec);
		    rsa_cipher.init(Cipher.DECRYPT_MODE, priv);
		}
		
		// Encrypt/Decrypt the cipher, and return the result
		byte[] pt = rsa_cipher.doFinal(text);
		return pt;
	}
}
