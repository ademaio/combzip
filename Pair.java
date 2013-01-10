
public class Pair implements java.io.Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	char c;
	long count;
	
	public Pair(char c, long count) {
		this.c = c;
		this.count = count;
	}
	
	public char getChar() {
		return this.c;
	}
	
	public long getCount() {
		return this.count;
	}
}
