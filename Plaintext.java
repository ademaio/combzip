
public class Plaintext {
	
	//returns an array of character, long pairs sorted by ascending longs
	public static Pair[] decompose(char[] cs) {
		
		int[] counts = new int[256];
		int nonZeroEntries = 0;
		
		for (int i = 0; i < cs.length; i++) {
			System.out.println(cs[i]);
			counts[cs[i]]++;
		}
		
		for (int i = 0; i < counts.length; i++) {
			if (counts[i] > 0) nonZeroEntries++;
		}
		
		Pair[] answer = new Pair[nonZeroEntries];
		int index = 0;

		for (int i = 0; i < counts.length; i++) {
			if (counts[i] > 0) {
				answer[index] = new Pair((char) i, (long) counts[i]);
				index++;
			}
		}

		return sort(answer,0);
	}
	
	//sorts the array of Pairs by count using insertion sort
	private static Pair[] sort(Pair[] ps, int index) {
		
		if (index == ps.length - 1) return ps;
		
		long min = ps[index].getCount();
		int minIndex = index;
		
		for (int i = index + 1; i < ps.length; i++) {
			if (ps[i].getCount() < min) {
				min = ps[i].getCount();
				minIndex = i;
			}
		}
		
		Pair temp = ps[index];
		ps[index] = ps[minIndex];
		ps[minIndex] = temp;
		
		return sort(ps, index + 1);
	}
	
	//return an extended copy of cs with char toInsert inserted at indices corresponding to 'true' in bs
	public static char[] insert(char[] cs, boolean[] bs, char toInsert) {
		
		char[] answer = new char[bs.length];
		int index = 0;
		
		for (int i = 0; i < bs.length; i++) {
			if (bs[i]) {
				answer[i] = toInsert;
			} else {
				answer[i] = cs[index];
				index++;
			}
		}
		
		return answer;
	}
	
	//returns a char array identical to cs but with all instances of toRemove removed
	public static char[] remove(char[] cs, char toRemove) {
		//first get count of occurrences of toRemove in cs
		int count = 0;
		for (int i = 0; i < cs.length; i++) {
			if (cs[i] != toRemove) count++;
		}
		
		//initialize a properly sized answer array and populate it with using an extra index
		char[] answer = new char[count];
		int index = 0;
		for (int i = 0; i < cs.length; i++) {
			if (cs[i] != toRemove) {
				answer[index] = cs[i];
				index++;
			}
		}
		
		return answer;
	}
}
