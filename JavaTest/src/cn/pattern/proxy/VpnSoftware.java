package cn.pattern.proxy;

/**
 * Proxy Implement
 */
public class VpnSoftware implements Proxy{
	private Proxy mSocket;
	
	public VpnSoftware(Proxy socket) {
		mSocket = socket;
	}
	
	@Override
	public void send() {
		mSocket.send();
	}

	@Override
	public void receive() {
		mSocket.receive();
	}
	
}
