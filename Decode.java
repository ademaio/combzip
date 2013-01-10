import java.math.BigInteger;


public class Decode {

	public static boolean[] decodeChar(BigInteger code, long n, long k) throws Exception {
		
		boolean[] answer = new boolean[(int) n];
		long largestN = n;
		//initialize our answer array to all false
		for (int i = 0; i < answer.length; i++) {
			answer[i] = false;
		}
		
		//set indices true for indices n_0, where n_0 choose k is the largest number fitting under code
		while (k > 0) {
			//if code is nonzero, use binary search. Otherwise, choose largest n for which it is 0
			//i.e., k-1
			if (!code.equals(BigInteger.ZERO)) { 
				largestN = Comb.findLargestN(code, k, largestN - 1, k);
			} else {
				largestN = k - 1;
			}
			//entry indexed at n - largestN - 1 is largestN away from end of array
			answer[(int) (n - largestN - 1)] = true;
			
			code = code.subtract(Comb.choose(largestN, k));
			k--;
		}
		
		return answer;
	}
	
	public static String decodeString(BigInteger code, Pair[] counts) throws Exception {
		
		char[] answer = new char[0];
		int n = 0, k = 0;
		BigInteger[] divrem;
		
		for (int i = counts.length - 1; i >= 0; i--) {
			System.out.println(counts[i].getChar());
			k = (int) counts[i].getCount();
			n += k;
			divrem = code.divideAndRemainder(Comb.choose(n, k));
			
			answer = Plaintext.insert(answer, decodeChar(divrem[1], n, k), counts[i].getChar());
			code = divrem[0];
		}

		return new String(answer);
	}
}
