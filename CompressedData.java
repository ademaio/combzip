import java.math.BigInteger;
import java.nio.ByteBuffer;
import java.util.*;
import java.lang.Byte;


public class CompressedData implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	Pair[] counts;
	BigInteger code;
	
	public CompressedData(Pair[] counts, BigInteger code) {
		this.counts = counts;
		this.code = code;
	}
	
	public CompressedData(byte[] data) {
		
		int index = 0, count;
		List<Pair> countlist = new LinkedList<Pair>();
		char c;
		
		while (data[index] != 0) {
			c = (char) (byte) data[index];
			index++;
			count = toInt(data, index);
			countlist.add(new Pair(c,count));
			index += 4;
		}
		index++;
		byte[] codebytes = new byte[data.length - index];
		for (int i = 0; i < codebytes.length; i++) {
			codebytes[i] = data[index + i];
		}
		this.code = new BigInteger(codebytes);
		Object[] objarray = countlist.toArray();
		Pair[] counts = new Pair[objarray.length];
		for (int i = 0; i < counts.length; i++) {
			counts[i] = (Pair) objarray[i];
		}
		this.counts = counts;
	}
	
	private static int toInt(byte[] bytes, int offset) {
		  int ret = 0;
		  for (int i=0; i<4 && i+offset<bytes.length; i++) {
		    ret <<= 8;
		    ret |= (int)bytes[i+offset] & 0xFF;
		  }

		  return ret;
	}
	
	public Pair[] getCounts() {
		return counts;
	}
	
	public BigInteger getCode() {
		return code;
	}
	
	public byte[] toByteArray() {
		List<Byte> answer = new LinkedList<Byte>();
		for (int i = 0; i < counts.length; i++) {
			answer.add((byte) counts[i].getChar());
			byte[] intbytes = ByteBuffer.allocate(4).putInt((int) counts[i].getCount()).array();
			for (int j = 0; j < intbytes.length; j++) {
				answer.add(intbytes[j]);
			}
		}
		answer.add((byte) 0);
		byte[] codebytes = code.toByteArray();
		for (int i = 0; i < codebytes.length; i++) {
			answer.add(codebytes[i]);
		}
		byte[] realanswer = new byte[answer.size()];
		for (int i = 0; i < realanswer.length; i++) {
			realanswer[i] = answer.get(i);
		}
		return realanswer;
	}

}
