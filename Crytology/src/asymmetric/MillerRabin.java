package asymmetric;

import java.math.BigInteger;

// A class for Miller-Rabin primality test
public class MillerRabin {

	public static void main(String[] args) {
		
		BigInteger number = new BigInteger("1033931178476059651954862004553");
		
		// Start with Fermat's theorem test
		boolean answer = mr(number.subtract(BigInteger.ONE), number);
		
		System.out.println("The probable primality of number \"" + number.toString() + "\": " + answer);
	}
	
	// Recursive Miller-Rabin test for primality
	public static boolean mr(BigInteger p, BigInteger modulos) {
		
		// Result of Fermat's theorem test
		BigInteger result = BigInteger.TWO.modPow(p, modulos);
		
		boolean condition1 = result.add(modulos).mod(modulos).equals(BigInteger.ONE);	// The result is 1
		boolean condition2 = result.add(modulos).mod(modulos).equals(modulos.subtract(BigInteger.ONE));	// The result is n - 1
		
		// If the result is neither 1 nor n - 1, n is composite
		if(!condition1 && !condition2) {
			// Find the factors
			BigInteger factor = modulos.gcd(result.subtract(BigInteger.ONE));
			
			System.out.println("Factors found:");
			System.out.println("First factor: " + factor.toString());
			System.out.println("Second factor: " + modulos.divide(factor).toString() + "\n");
			
			return false;
		}
		// If the exponent is odd, or result is n - 1, the number is probably prime from Miller-Rabin test
		else if (!p.mod(BigInteger.TWO).equals(BigInteger.ZERO) || condition2) 
			return true;
		
		// Recursively divide the exponent by 2
		return mr(p.divide(BigInteger.TWO), modulos);
	}
}
