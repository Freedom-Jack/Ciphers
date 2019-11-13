package foundation;

import util.CryptoTools;
import foundation.C_Crypta;
import java.io.ByteArrayOutputStream;
import java.util.Arrays;

public class V_Crypta {

	// Cryptanalysis attack on Vigenere Cipher
	public static void main(String[] args) throws Exception {

		// Read the file in bytes
		byte[] rawct = CryptoTools.fileToBytes("data/MSG4.ct");
		byte[] ct = CryptoTools.clean(rawct);
		
		// Invoke the cryptanalytic method to get the plain text 
		int[] integersKey = vigenereDecryptCrypta(ct);
		byte[] byteKey = convertKey(integersKey);
		byte[] pt = vigenereDecipher(ct, integersKey);
		
		// Print out the key word
		System.out.println("The key is most likely to be: " + new String(byteKey));
		System.out.println(new String(pt));
	}
	
	// Method for decrypting Vigenere cipher cryptanalyticly, and return the possible key
	public static int[] vigenereDecryptCrypta(byte[] cipher) {
		
		// Variables declarations
		int lengthLimits = 50;	// Limit of the maximum size of the key word
		double targetIC = 0.067;	// Target index of coincidence (English)
				
		// Find the key length
		int keyLength = keyLengthFind(cipher, lengthLimits, targetIC);
		System.out.println("The key length is most likely to be: " + keyLength);
				
		// Perform a Caesar decipher using the retrieved key length
		int[] key = new int[keyLength];
		for(int i = 0; i < keyLength; i++) {
			key[i] = C_Crypta.caesarDecryptCrypta(sampling(cipher, keyLength, i));
		}
		
		// Output the key in integers
		System.out.print("The key(Integers) is most likely to be: ");
		for(int i = 0; i < keyLength; i ++) {
			System.out.print(key[i] + " ");
		}
		System.out.print("\n");
		
		return key;
	}
	
	// Method for decrypting a Vigenere cipher text by a given key
	public static byte[] vigenereDecipher(byte[] cipher, int[] key) {
		
		byte[] plain = new byte[cipher.length];
		int counter = 0;
		
		for(int i = 0; i < cipher.length; i++) {
			plain[i] = (byte) ((cipher[i] - 'A' - key[counter] + 26) % 26 + 'A');
			counter = (counter + 1) % key.length;
		}
		
		return plain;
	}
	
	// Method for getting the key word from an int array
	public static byte[] convertKey(int[] keyInIntegers) {
		byte[] result = new byte[keyInIntegers.length];
		
		for(int i = 0; i < keyInIntegers.length; i++) {
			result[i] = (byte) ('A' + keyInIntegers[i]);
		}
		
		return result;
	}
	
	// Method for determine the key length
	public static int keyLengthFind(byte[] cipher, int limit, double target) {
		
		int trials = 20;
		int[] results = new int[trials];
		Arrays.fill(results, -1);
		
		double[] valuesArray = new double[limit];
		
		// Loop for finding candidate lengths
		for (int loop = 0; loop < trials; loop++) {
			
			// Find the best key for current trials by comparing IC
			for (int i = 1; i <= limit; i++) {
				
				byte[] sample = sampling(cipher, i, 0);
			
				valuesArray[i - 1] = CryptoTools.getIC(sample);
				//System.out.println("Key length: " + i + "; IC: " + valuesArray[i - 1]);
			}
		
			results[loop] = closestPos(valuesArray, target);
		}
		
		return gcdArray(results);
	}
	
	// Method for obtaining a sample array base on a given key size
	public static byte[] sampling(byte[] cipher, int keySize, int keyIndex) {
		
		ByteArrayOutputStream sampleStream = new ByteArrayOutputStream();
		
		for(int i = keyIndex; i < cipher.length; i += keySize) {
			sampleStream.write(cipher[i]);
		}
		
		return sampleStream.toByteArray();
	}
	
	// Method for finding the closest value's position in an array to a certain number
	public static int closestPos(double[] array, double goal) {
		
		int answer = 0;
		double closestOffset = Double.MAX_VALUE;
		
		for(int i = 0; i < array.length; i++) {
			if(Math.abs(array[i] - goal) < closestOffset) {
				closestOffset = Math.abs(array[i] - goal);
				answer = (i + 1);
			}
		}
		
		return answer;
	}
	
	// Method for finding the GCD of two numbers
	public static int gcd(int a, int b) {
		
		if(a == 0)
			return b;
		
		return gcd(b % a, a);
	}
	
	// Method for finding the GCD of an array, with non-negative values
	public static int gcdArray(int[] input) {
		
		int result = input[0];
		
		for(int i = 0; i < input.length && input[i] >= 0; i++)
			result = gcd(input[i], result);
		
		return result;
	}
}
