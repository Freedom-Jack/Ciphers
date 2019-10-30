package asymmetric;

import java.math.BigInteger;

public class MillerRabin {

	public static void main(String[] args) {
		
		BigInteger number = new BigInteger("1033931178476059651954862004553");
		
		boolean answer = mr(number.subtract(new BigInteger("1")), number);
		
		System.out.println("The probable primality of number \"" + number.toString() + "\": " + answer);
	}
	
	// Recursive Miller Rabin test for primality
	public static boolean mr(BigInteger p, BigInteger modulos) {
		
		BigInteger two = new BigInteger("2");
		BigInteger result = two.modPow(p, modulos);
		
		if(Math.abs(result.intValue()) != 1)
			return false;
		else if (p.intValue() != 0 && Math.abs(result.intValue()) == 1) 
			return true;
		
		return mr(p.divide(two), modulos);
	}
}
