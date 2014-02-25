package cn.code.classloader;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class MyClassLoader {
	
	public static void main(String[] args) throws Exception {
		FileInputStream fin = new FileInputStream("./ops.class");

		FileOutputStream fos  = new FileOutputStream("./opss.class");
		cypher(fin, fos);
		fin.close();
		fos.close();
	}
	
	private static void cypher(InputStream ins, OutputStream ops) throws IOException {
		int b=-1;
		while((b=ins.read())!=-1) {
			ops.write(b^0xff);
		}
	}

}
