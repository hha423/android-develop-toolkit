package com.allthelucky.examples.common;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;
import java.util.Locale;

public class IP4Utils {

	public static void main(String argv[]) {
		System.out.println(getLocalIpAddress());
	}

	/**
	 * IPV4转换成8位字符串
	 * 
	 * @param input
	 * @return
	 */
	public static String formatIP(String input) {
		if (input == null) {
			return "00000000";
		}
		try {
			StringBuilder sb = new StringBuilder();

			String[] vals = input.split("\\.");
			for (int i = 0; i < 4; i++) {
				sb.append(intToHex(Integer.valueOf(vals[i])));
			}
			return sb.toString();
		} catch (NumberFormatException e) {
			e.printStackTrace();
			return "00000000";
		}
	}

	public static String intToHex(int value) {
		String hex;

		hex = Integer.toString(value, 16);

		if (hex.length() % 2 != 0) {
			hex = "0" + hex;
		}
		return hex.toUpperCase(Locale.getDefault());
	}

	public static String getLocalIpAddress() {
		try {
			for (Enumeration<NetworkInterface> en = NetworkInterface.getNetworkInterfaces(); en.hasMoreElements();) {
				NetworkInterface intf = en.nextElement();
				for (Enumeration<InetAddress> enumIpAddr = intf.getInetAddresses(); enumIpAddr.hasMoreElements();) {
					InetAddress inetAddress = enumIpAddr.nextElement();
					if (!inetAddress.isLoopbackAddress()) {
						return inetAddress.getHostAddress().toString();
					}
				}
			}
		} catch (SocketException ex) {
		}
		return null;
	}

}
