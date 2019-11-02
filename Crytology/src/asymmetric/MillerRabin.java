package asymmetric;

import java.math.BigInteger;

public class MillerRabin {

	public static void main(String[] args) {
		
		BigInteger number = new BigInteger("1033931178476059651954862004553");
		
		boolean answer = mr(number.subtract(new BigInteger("1")), number);
		
		System.out.println("The probable primality of number \"" + number.toString() + "\": " + answer);
	}
	
	// Recursive Miller-Rabin test for primality
	public static boolean mr(BigInteger p, BigInteger modulos) {
		
		BigInteger result = BigInteger.TWO.modPow(p, modulos);
		
		boolean condition1 = result.abs().compareTo(BigInteger.ONE) == 0;	// The result is 1
		boolean condition2 = result.abs().compareTo(modulos.subtract(BigInteger.ONE)) == 0;	// The result is n - 1
		
		// If the result is neither 1 or n - 1, n is composite
		if(!condition1 && !condition2) {
			BigInteger factor = result.subtract(BigInteger.ONE).gcd(modulos);
			System.out.println("Factor found: " + factor.intValue());
			
			return false;
		}
		// If the exponent is odd, or result is n - 1, the number is probably prime from Miller-Rabin test
		else if (p.mod(BigInteger.TWO).compareTo(BigInteger.ZERO) == 1 || condition2) 
			return true;
		
		return mr(p.divide(BigInteger.TWO), modulos);
	}
}
