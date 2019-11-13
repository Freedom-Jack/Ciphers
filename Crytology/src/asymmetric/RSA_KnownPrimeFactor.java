package asymmetric;

import java.math.BigInteger;

public class RSA_KnownPrimeFactor {

	public static void main(String[] args) throws Exception {

		// The modulus, and the public key
		BigInteger n = new BigInteger(
				"94587468335128982981605019776781234618384857805657005686084562260910788622013722070926491690843853690071248130134427832324966728582532832363221542231787068203763027067400082835394459857525017707284768411819006776211493735326500782954621660256501187035611332577696332459049538105669711385995976912007767106063");
		BigInteger e = new BigInteger("74327");
		// The cipher text
		BigInteger c = new BigInteger(
				"10870101966939556606443697147757930290262227730644958783498257036423105365610629529910525828464329792615002602782366786531253275463358840412867833406256467153345139501952173409955322129689670345445632775574301781800376545448990332608558103266831217073027652061091790342124418143422318965525239492387183438956");
		// One of the prime factor of n
		BigInteger p = new BigInteger(
				"10358344307803887695931304169230543785620607743682421994532795393937342395753127888522373061586445417642355843316524942445924294144921649080401518286829171");
	
		BigInteger d = privateKeyRetrievalNPE(n, p, e);
		byte[] result = RSA_Cryptography.rsaCipher(n, d, c.toByteArray(), false);
		
		System.out.println("The plaintext: " + new String(result).trim());
	}
	
	// Retrieve private key, based on modulos "n", one of the prime factor "p", and the public key "e"
	public static BigInteger privateKeyRetrievalNPE(BigInteger modulos, BigInteger primeFactor, BigInteger publicKey) {
		
		// Calculate q from n and p, to find phi = (p-1)(q-1) = pq-p-q+1
		BigInteger otherFactor = modulos.divide(primeFactor);
		BigInteger totient = primeFactor.multiply(otherFactor).subtract(primeFactor).subtract(otherFactor)
				.add(BigInteger.ONE);
		
		// Calculate the private key
		BigInteger privateKey = publicKey.modInverse(totient);
		
		return privateKey;
	}
}
