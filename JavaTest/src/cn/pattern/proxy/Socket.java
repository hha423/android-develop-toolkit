package cn.pattern.proxy;

/**
 * Real Implement
 */
public class Socket implements Proxy {

	@Override
	public void send() {
		System.out.println("send data to internet...");
	}

	@Override
	public void receive() {
		System.out.println("receiver some data from internet...");
	}

}
