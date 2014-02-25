package cn.code.readcodes;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;

public class Test {

    public static void main(String[] args) {
        final String path = "E:\\android\\temp";

        try {
            generate(path);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void generate(String path) throws Exception {
        File dir = new File("E:\\android\\temp");
        if (dir.exists()) {
            if (dir.isDirectory()) {
                File files[] = dir.listFiles();
                int size = files.length;
                if (size > 0) {
                    int i = 0;
                    for (File f : files) {
                        readFile(f, i++);
                    }
                }
            }
        }
    }

    private synchronized static void readFile(File f, int i) throws Exception {
        FileInputStream ins = new FileInputStream(f);
        ByteArrayOutputStream byteStream = new ByteArrayOutputStream();
        byte[] bytes = new byte[4096];
        int len = 0;
        while ((len = ins.read(bytes)) > 0) {
            byteStream.write(bytes, 0, len);
        }

        String str = new String(byteStream.toByteArray(), "UTF-8");
        System.out.println(str);
        System.out.println();
    }

}
