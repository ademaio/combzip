import java.math.BigInteger;
import java.io.*;


public class Main {
	
	public static void main(String[] args) throws Exception {

		if (args.length < 2) 
			throw new Exception("Not enough arguments: supply -c or -d to compress or decompress, followed by filename.");
		
		if (args[0].equals("-c")) {
			compress(args[1]);
		} else if (args[0].equals("-d")) {
			decompress(args[1]); 
		} else {
			throw new Exception ("Valid tag not supplied: supply -c or -d to compress or decompress, followed by filename.");
		}
	}
	
	public static void compress(String filename) throws Exception {
		
		//extract the text from the source file
		BufferedReader in = new BufferedReader(new FileReader(filename));
		StringBuffer text = new StringBuffer();
		String nextLine = in.readLine();
		
		while (nextLine != null) {
			text.append(nextLine);
			text.append("\n");
			nextLine = in.readLine();
		}
		
		in.close();
		
		//compress the text
		Pair[] counts = Plaintext.decompose(text.toString().toCharArray());
		BigInteger encoding = Encode.encodeString(text.toString());
		CompressedData data = new CompressedData(counts, encoding);
		
		if (filename.indexOf(".") >= 0) {
			filename = filename.substring(0,filename.indexOf("."));
		}
		
		FileOutputStream out = 
			new FileOutputStream(new File(filename + ".czip"));
		
		out.write(data.toByteArray());
		
		out.close();
		
		System.out.println(text);
	}
	
	public static void decompress(String filename) throws Exception {
		
		//reads the bytes from the input stream
		FileInputStream in = new FileInputStream(new File(filename));
		
		byte[] bytes = new byte[in.available()];
		
		for (int i = 0; i < bytes.length; i++) {
			bytes[i] = (byte) in.read();
		}
		
		in.close();
		//converts it to CompressedData then decodes from this info
		CompressedData data = new CompressedData(bytes);
		
		String text = Decode.decodeString(data.getCode(), data.getCounts());
		
		if (filename.indexOf(".") >= 0) {
			filename = filename.substring(0,filename.indexOf("."));
		}
		
		BufferedWriter out = 
			new BufferedWriter(new FileWriter(filename + ".txt"));
		
		out.write(text);
		
		out.close();
	}
}
