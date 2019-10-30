package asymmetric;

import java.math.BigInteger;


public class CRT {

	// Chinese Remainder Theorem: find the smallest qualified number from two modulus equations
	public static void main(String[] args) {
		
		BigInteger m1 = new BigInteger("1055827021987");
		BigInteger m2 = new BigInteger("973491987203");
		BigInteger r1 = new BigInteger("365944767426");
		BigInteger r2 = new BigInteger("698856040412");
		
		BigInteger[] result = chineseRemainderTheorem(m1, r1, m2, r2);

		System.out.println(result[0].toString());
	}
	
	public static BigInteger[] chineseRemainderTheorem(BigInteger modulos1, BigInteger remainder1, BigInteger modulos2, BigInteger remainder2) {
		
		// The lowest common multiplier
		BigInteger product = modulos1.multiply(modulos2);
		
		// Calculate the two modular inverse
		BigInteger modInverse1 = modulos1.modInverse(modulos2);
		BigInteger modInverse2 = modulos2.modInverse(modulos1);
		
		// Calculate the two components
		BigInteger component1 = remainder1.multiply(modulos2).multiply(modInverse2);
		BigInteger component2 = remainder2.multiply(modulos1).multiply(modInverse1);
		
		// The answer
		BigInteger answer = (component1.add(component2)).mod(product);
		
		// Return an array of BigInteger, result as the first entry, product as the second entry
		BigInteger[] returning = new BigInteger[2];
		returning[0] = answer;
		returning[1] = product;
		return returning;
	}
}
