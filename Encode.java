import java.math.BigInteger;


public class Encode {
	
	public static BigInteger encodeChar(char[] cs, char toEncode) {
		
		BigInteger answer = BigInteger.ZERO;
		long n = cs.length;
		long k = 0;
		//first set k to be the number of instances of toEncode in cs
		for (int i = 0; i < cs.length; i++) {
			if (cs[i] == toEncode) k++;
		}
		
		for (int i = 0; i < cs.length; i++) {
			if (cs[i] == toEncode) {
				answer = answer.add(Comb.choose(n-1, k));
				k--;
			}
			n--;
		}

		return answer;
	}
	
	//takes a string and applies the compression algorithm to each character
	public static BigInteger encodeString(String s) {
		
		char[] cs = s.toCharArray();
		Pair[] counts = Plaintext.decompose(cs);
		BigInteger answer = BigInteger.ZERO;
		
		for (int i = 0; i < counts.length; i++) {
			System.out.println(counts[i].getChar());
			//multiply our encoding by n choose k, where n is all remaining characters, k is 
			//the count of the character we care about this iteration
			answer = answer.multiply(Comb.choose(cs.length, counts[i].getCount()));
			
			answer = answer.add(encodeChar(cs, counts[i].getChar()));

			cs = Plaintext.remove(cs, counts[i].getChar());
		}
		
		return answer;
	}
}
