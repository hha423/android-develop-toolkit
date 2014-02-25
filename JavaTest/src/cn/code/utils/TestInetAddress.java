package cn.code.utils;
import java.net.InetAddress;

public class TestInetAddress {
	public static void main(String args[]) {
		try {
			InetAddress ia = InetAddress.getLocalHost();
			showInfo(ia);
			ia = InetAddress.getByName("www.winfirm.net");
			showInfo(ia);
		} catch (java.net.UnknownHostException e) {
			e.printStackTrace();
		}
	}
	public static void showInfo(InetAddress ia) {
		String name = ia.getHostName();
		String adress = ia.getHostAddress();
		System.out.println(name);
		System.out.println(adress);
		System.out.println("-----------");
	}
}
