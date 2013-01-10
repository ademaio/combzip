import java.math.BigInteger;


public class Comb {
	
	//find n choose k. Perhaps optimize this with some memoization later on
	public static BigInteger choose(long n, long k) {
		
		if (n < k) return BigInteger.ZERO;
		
		BigInteger answer = BigInteger.ONE;
		// make sure we do the fewest possible calculations by minimizing k
		k = Math.min(k, n-k);
		
		for (long i = n-k+1; i <= n; i++) {
			answer = answer.multiply(BigInteger.valueOf(i));
		}
		
		for (long i = 2; i <= k; i++) {
			answer = answer.divide(BigInteger.valueOf(i));
		}
		
		return answer;
	}
	
	//finds the largest n for which n choose k is less than or equal to target, this may also
	//be memoized in the future. currently performs a recursive binary search
	public static long findLargestN(BigInteger target, long k, long max, long min) throws Exception {
		
		if (max < min) //throw new Exception("Incorrect parameters for findLargestN");
		return k - 1;
		if (max == min) return max;
		
		long average = (max + min)/2;
		
		switch (choose(average, k).compareTo(target)) {
		case -1:
			if (average == min) {
				if (choose(max, k).compareTo(target) <= 0) {
					return max;
				} else {
					return min;
				}
			} else {
				return findLargestN(target, k, max, average);
			}
		case 0:
			return average;
		case 1:
			return findLargestN(target, k, average, min);
		default: //something went wrong with the comparison function
			return 0;	
		}
	}
}
