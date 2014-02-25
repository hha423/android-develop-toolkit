package cn.pattern.proxy;

public class Test {

	/**
	 * Test
	 */
	public static void main(String[] args) {
		Proxy proxy = new Socket();
		Proxy vpn = new VpnSoftware(proxy);
		
		vpn.send();
		vpn.receive();
	}

}
