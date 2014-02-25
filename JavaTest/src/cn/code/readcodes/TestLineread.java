package cn.code.readcodes;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class TestLineread {

    public static void main(String[] args) {
        try {
            readString("E:\\weibo_sina_code.json");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static String readString(String pathname) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(pathname));
        StringBuilder sb = new StringBuilder();
        sb.append("{");
        String line = "";
        while ((line = reader.readLine()) != null) {
            String values[] = line.split(":");
            sb.append("\"").append(values[0].trim()).append("\"").append(":").append("\"").append(values[1].trim()).append("\"").append(",");
            sb.append("\r\n");
        }
        sb.append("}");
        System.out.println(sb.toString());
        reader.close();
        return sb.toString();
    }

}
