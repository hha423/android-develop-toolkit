package cn.pattern.command;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class Handler {
	private BlockingQueue<Message> queue = new ArrayBlockingQueue<Message>(10);

	public void sendMessage(Message msg) {
		try {
			queue.put(msg);
			handMessage(queue.take());
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public void handMessage(Message msg) {

	}
	
	public static class Message {
		public int what;
		public Object obj;
	}
}
