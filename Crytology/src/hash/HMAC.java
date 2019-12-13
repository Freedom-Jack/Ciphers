package hash;

import java.io.ByteArrayOutputStream;
import java.util.Arrays;

import util.CryptoTools;

public class HMAC {

	// Hash application for HMAC
	public static void main(String[] args) throws Exception {

		// Information for HMAC
		byte[] m = "Mainly cloudy with 40 percent chance of showers".getBytes();
		byte[] k = "This is an ultra-secret key".getBytes();
		String hashMethod = "SHA-1";
		int block_size_in_byte = 64;
		String constant1 = "5c";
		String constant2 = "36";

		byte[] result1 = hmac_general(k, m, hashMethod, block_size_in_byte, constant1, constant2);
		byte[] result2 = hmac_general(k, m, hashMethod, block_size_in_byte, constant2, constant1);
		
		// Information print out
		System.out.println("Message: " + new String(m));
		System.out.println("Secret key: " + new String(k));
		System.out.println("Hash function: " + hashMethod + "\n");
		System.out.println("HMAC(5c, 36): " + CryptoTools.bytesToHex(result1) + " (Ordinary)");
		System.out.println("HMAC(36, 5c): " + CryptoTools.bytesToHex(result2));
	}

	// Method for HMAC in general
	public static byte[] hmac_general(byte[] secret_key, byte[] message, String hashFunction, int blockSizeInByte,
			String oString, String iString) throws Exception {

		// Padding constants
		byte[] opad = padFinding(oString, blockSizeInByte);
		byte[] ipad = padFinding(iString, blockSizeInByte);

		byte[] kprime = kprimeFinding(secret_key, hashFunction, blockSizeInByte);

		// Calculate K' xor opad
		byte[] oPart = bitwise_xor(kprime, opad);
		// Calculate H[(K' xor ipad) || m]
		byte[] iInput = twoArraysConcatenation(bitwise_xor(kprime, ipad), message);
		byte[] iPart = Signature.generalHash(iInput, hashFunction);
		
		// Concatenate both oPart and iPart
		byte[] lastHashing = twoArraysConcatenation(oPart, iPart);
		
		return Signature.generalHash(lastHashing, hashFunction);
	}

	// Method for bitwise xor on two byte arrays
	public static byte[] bitwise_xor(byte[] input1, byte[] input2) throws Exception {
		
		// To xor, 2 input arrays must be equal length
		if (input1.length != input2.length)
			throw new Exception();

		byte[] result = new byte[input1.length];

		for (int i = 0; i < input1.length; i++)
			result[i] = (byte) (input1[i] ^ input2[i]);

		return result;
	}

	// Method for concatenating two byte arrays
	public static byte[] twoArraysConcatenation(byte[] a, byte[] b) throws Exception {

		// Using an output stream for bytes
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

		outputStream.write(a);
		outputStream.write(b);

		return outputStream.toByteArray();
	}
	
	// Method for finding a pad byte array
	public static byte[] padFinding(String padString, int sizeInByte) throws Exception {
		
		String result = "";
		
		// 1Hex = 0.5byte, number of time it repeats is size in byte divided by half of the hex string's length
		for(int i = 0; i < sizeInByte / padString.length() * 2; i++)
			result += padString;
		
		return CryptoTools.hexToBytes(result);
	}
	
	// Method for finding kprime according to a certain block size
	public static byte[] kprimeFinding(byte[] key, String hashing, int sizeInByte) throws Exception {
		
		byte[] result;
		
		// If key is longer than the block size of hash, hash it
		if (key.length > sizeInByte)
			result = CryptoTools.bytesToHex(Signature.generalHash(key, hashing)).getBytes();
		// Else if the key is shorter, pad (byte)zeros to the right of the key
		else if (key.length < sizeInByte)
			result = zerosRight(key, sizeInByte);
		// Else when the key is just the size of the block, do nothing
		else
			result = Arrays.copyOf(key, key.length);
		
		return result;
	}
	
	// Method for padding zero to the left
	public static byte[] zerosRight(byte[] key, int sizeInByte) throws Exception {
		
		// To pad zeros, the key size must be smaller than the block
		if(key.length > sizeInByte)
			throw new Exception();
		
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		
		outputStream.write(key);
		for(int i = 0; i < (sizeInByte - key.length); i++)
			outputStream.write(0);
		
		return outputStream.toByteArray();
	}
}
