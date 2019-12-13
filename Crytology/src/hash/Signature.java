package hash;

import java.math.BigInteger;
import java.security.MessageDigest;

import util.CryptoTools;
import util.CustomizedHelpers;

public class Signature {
	
	// An example of using hash and asymmetric cipher for signature purpose
	public static void main(String[] args) throws Exception {

		// The modulus, and the pair of asymmetric keys
		BigInteger n = new BigInteger(
				"94587468335128982981605019776781234618384857805657005686084562260910788622013722070926491690843853690071248130134427832324966728582532832363221542231787068203763027067400082835394459857525017707284768411819006776211493735326500782954621660256501187035611332577696332459049538105669711385995976912007767106063");
		BigInteger e = new BigInteger("74327");
		BigInteger d = new BigInteger(
				"7289370196881601766768920490284861650464951706793000236386405648425161747775298344104658393385359209126267833888223695609366844098655240542152017354442883676634193191857568369042999854440242050353181703706753485749165295123694487676952198090537385200990850805837963871485320168470788328336240930212290450023");
		// The hashing message
		byte[] pt = "Meet me at 5 pm tomorrow".getBytes();
		
		// Calculate the hash value, then encrypt it with the private key
		byte[] pt_hash = generalHash(pt, "SHA-512");
		byte[] output = CustomizedHelpers.rsaGeneral(n, d, pt_hash, true, "RSA", "RSA/ECB/PKCS1Padding");
		
		// Final message that is being sent (Signature)
		String signaturedMessage = new String(pt) + "\t" + CryptoTools.bytesToHex(output);
		
		// Print the final message
		System.out.println("The message's hash: " + CryptoTools.bytesToHex(pt_hash) + "\n");
		System.out.println("The sent final message with signature:");
		System.out.println(signaturedMessage + "\n");
		
		// Double-check the message correctness
		System.out.println("Sender Integrity: " + verifySenderIntegrity(signaturedMessage, n, e));
	}
	
	// Method for getting the hash value of a message with a specified hash function
	public static byte[] generalHash(byte[] message, String hashAlgorithm) throws Exception {

		MessageDigest md = MessageDigest.getInstance(hashAlgorithm);
		byte[] hash = md.digest(message);
		
		return hash;
	}
	
	// Method for verifying the received message
	public static boolean verifySenderIntegrity(String message, BigInteger modulo, BigInteger public_key) throws Exception {
		
		// Split the string to get the plaintext and the signature
		String[] parts = message.split("\t");
		String pt_string = parts[0];
		String signature_string = parts[1];
		
		// Compute the hash of the plaintext
		String pt_hash = CryptoTools.bytesToHex(generalHash(pt_string.getBytes(), "SHA-512"));
		
		// Decrypt the signature, and clean up the leading zeros to obtain the clean hash
		byte[] uncleanHash = CustomizedHelpers.rsaGeneral(modulo, public_key, CryptoTools.hexToBytes(signature_string), false, "RSA", "RSA/ECB/PKCS1Padding");
		String verificationHash = CryptoTools.bytesToHex(uncleanHash);
		
		// Return the comparison
		return pt_hash.equals(verificationHash);
	}
}
