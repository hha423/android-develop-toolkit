package cn.code.utils;

public class Main {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		byte[] data = new byte[10];
		
		data[0]=(byte)48;
		data[1]=(byte)49;
		data[2]=(byte)65;
		data[3]=(byte)97;
		data[4]=(byte)90;
		data[5]=(byte)122;
		data[6]=(byte)80;
		System.out.println(new String(data));
		
		String str = "01AaZz";
		byte[] strbts= str.getBytes();
		System.out.println(strbts);
	}

}
