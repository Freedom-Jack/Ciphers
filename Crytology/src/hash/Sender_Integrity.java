package hash;

import java.math.BigInteger;

public class Sender_Integrity {

	// Sender Integrity between sender A and receiver B
	public static void main(String[] args) throws Exception {

		// A's asymmetric information
		BigInteger nA = new BigInteger("171024704183616109700818066925197841516671277");
		BigInteger eA = new BigInteger("1571");
		// B's asymmetric information
		BigInteger pB = new BigInteger("98763457697834568934613");
		BigInteger qB = new BigInteger("8495789457893457345793");
		BigInteger eB = new BigInteger("87697");
		BigInteger dB = eB.modInverse(pB.subtract(BigInteger.ONE).multiply(qB.subtract(BigInteger.ONE)));
		BigInteger nB = pB.multiply(qB);

		// Two sent messages: m' and s', where m' = E(m, eB), s' = E(E(n, dA), eB)
		BigInteger mprime = new BigInteger("418726553997094258577980055061305150940547956");
		BigInteger sprime = new BigInteger("749142649641548101520133634736865752883277237");

		// Decrypt both encrypted messages to get m and s
		BigInteger m = customizedRSA(nB, dB, mprime);
		BigInteger s = customizedRSA(nB, dB, sprime);
		BigInteger signature = customizedRSA(nA, eA, s);
		
		System.out.println("Message: " + m.toString());
		System.out.println("Signature: " + signature.toString());
		System.out.println("Sender integrity: " + m.toString().equals(signature.toString()));
	}
	
	// Customized RSA, which allow arbitrary input size
	public static BigInteger customizedRSA(BigInteger modulo, BigInteger key, BigInteger text) {
		return text.modPow(key, modulo);
	}
}
