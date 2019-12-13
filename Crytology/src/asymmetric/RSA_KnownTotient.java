package asymmetric;

import java.math.BigInteger;

public class RSA_KnownTotient {

	public static void main(String[] args) throws Exception {
		
		// Given information: totient, one of the prime factors, public key, and the cipher text
		BigInteger phi = new BigInteger(
				"8584037913642434144111279062847405921823163865842701785008602377400681495147541519557274092429073976252689387304835782258785521935078205581766754116919200");
		BigInteger p = new BigInteger("87020952829623092932322362936864583897972618059974315662422560067745889600571");
		BigInteger e = new BigInteger("65537");
		BigInteger c = new BigInteger(
				"1817487313698347891034157970684926175211834109573277793102901854482611726141560963120214926234448852417078321539316776648109260519063106558303669880226359");
	
		// Find the remaining values
		BigInteger d = privateKeyRetrievalPhiE(phi, e);
		BigInteger q = primeFactorRetrieval(phi, p);
		BigInteger n = modulosRetrieval(p, q);
		
		// Decipher
		byte[] result = RSA_Cryptography.rsaCipher(n, d, c.toByteArray(), false);
		
		System.out.println("The plaintext: " + new String(result).trim());
	}
	
	// Retrieve the modulos, based on two prime factors
	public static BigInteger modulosRetrieval(BigInteger p, BigInteger q) {
		return p.multiply(q);
	}
	
	// Retrieve the other prime factor, based on one prime factor and totient
	public static BigInteger primeFactorRetrieval(BigInteger totient, BigInteger primeFactor) {
		return totient.divide(primeFactor.subtract(BigInteger.ONE)).add(BigInteger.ONE);
	}
	
	// Retrieve private key, based on totient "phi", and public key "e"
	public static BigInteger privateKeyRetrievalPhiE(BigInteger totient, BigInteger publicKey) {
		return publicKey.modInverse(totient);
	}
}
