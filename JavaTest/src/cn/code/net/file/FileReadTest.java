package cn.code.net.file;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.PrintWriter;

import org.json.JSONArray;
import org.json.JSONObject;

public class FileReadTest {

	public static void main(String[] args) {
		new Thread(new Runnable() {
			@Override
			public void run() {
				getCitys("E:\\hotcitys.json");
			}
		}).start();
	}

	/**
	 * 解析
	 * 
	 * @param context
	 * @param assertFile
	 * @return
	 */
	public static void getCitys(String filePath) {
		GB2Alpha convert = new GB2Alpha();
		final JSONArray all = new JSONArray();
		try {
			String json = readFile(new File(filePath));
			JSONArray array = new JSONArray(json);
			if (array != null && array.length() > 0) {
				int len = array.length();
				JSONObject o = null;
				for (int index = 0; index < len; index++) {
					o = array.optJSONObject(index);
					o.remove("latitude");
					o.remove("longitude");
					String name = o.optString("name");
					o.put("jx", convert.String2Alpha(name).toLowerCase());
					all.put(o);
					o = null;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("parse city err");
		}
		System.out.println(all.toString());
	}

	private synchronized static String readFile(File f) throws Exception {
		ByteArrayOutputStream byteStream = null;
		FileInputStream ins = null;
		try {
			ins = new FileInputStream(f);
			byteStream = new ByteArrayOutputStream();
			byte[] bytes = new byte[4096];
			int len = 0;
			while ((len = ins.read(bytes)) > 0) {
				byteStream.write(bytes, 0, len);
			}
			return new String(byteStream.toByteArray(), "UTF-8");
		} finally {
			if(ins!=null) {
				ins.close();
			}
		}

	}

	/**
	 * 写文本到文件
	 * 
	 * @param array
	 */
	public static void writeSD(JSONArray array) {
		System.out.println(array.toString());
		File f = new File("/mnt/sdcard/city.json");
		PrintWriter pw = null;
		try {
			pw = new PrintWriter(f);
			pw.println(array.toString());
			pw.flush();//
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} finally {
			if (pw != null) {
				pw.close();
			}
		}
		System.out.println("total:" + array.length());
	}

}
