package com.allthelucky.examples.common;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.util.Enumeration;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

public class FileZipUtils {

	/**
	 * Get MD5 of one file:hex string,test OK!
	 * 
	 * @param file
	 * @return
	 */
	public static String getFileMD5(File file) {
		if (!file.exists() || !file.isFile()) {
			return null;
		}
		MessageDigest digest = null;
		FileInputStream in = null;
		byte buffer[] = new byte[1024];
		int len;
		try {
			digest = MessageDigest.getInstance("MD5");
			in = new FileInputStream(file);
			while ((len = in.read(buffer, 0, 1024)) != -1) {
				digest.update(buffer, 0, len);
			}
			in.close();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		BigInteger bigInt = new BigInteger(1, digest.digest());
		return bigInt.toString(16);
	}

	/**
	 * 删除某目录下文件及子目录
	 * 
	 * @param zipDir
	 * @param zipFile
	 */
	public static void deleteDirFiles(File zipDir, File zipFile) {
		for (File f : zipDir.listFiles()) {
			try {
				if (!zipFile.getAbsolutePath().equals(f.getAbsolutePath())) {
					if (f.isDirectory()) {
						deleteDirFiles(f, zipFile);
					}
					f.delete();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * 解压文件到指定目录
	 * 
	 * @param zipFile
	 * @param zipDir
	 * @author isea533
	 */
	public static void unZipFiles(File zipFile, File zipDir) throws IOException {
		String targetPath = zipDir.getAbsolutePath() + File.separator;

		ZipFile zip = new ZipFile(zipFile);
		for (Enumeration<?> entries = zip.entries(); entries.hasMoreElements();) {
			ZipEntry entry = (ZipEntry) entries.nextElement();
			String zipEntryName = entry.getName();
			InputStream in = zip.getInputStream(entry);
			String outPath = (targetPath + zipEntryName).replaceAll("\\*", "/");
			// 判断路径是否存在,不存在则创建文件路径
			File file = new File(outPath.substring(0, outPath.lastIndexOf('/')));
			if (!file.exists()) {
				file.mkdirs();
			}
			// 判断文件全路径是否为文件夹,如果是上面已经上传,不需要解压
			if (new File(outPath).isDirectory()) {
				continue;
			}
			// 输出文件路径信息
			System.out.println(outPath);

			OutputStream out = new FileOutputStream(outPath);
			byte[] buf1 = new byte[1024];
			int len;
			while ((len = in.read(buf1)) > 0) {
				out.write(buf1, 0, len);
			}
			in.close();
			out.close();
		}
		System.out.println("******************解压完毕********************");
	}

}
