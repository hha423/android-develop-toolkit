package cn.pattern.command;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * ���������
 * 
 * @author pxw
 * 
 */
public class CommandInvoker implements Runnable {
	private static final int MAX_COMMAND_LEN = 200;

	private BlockingQueue<Command> queue = new ArrayBlockingQueue<Command>(
			MAX_COMMAND_LEN);
	private ExecutorService threadPool = Executors.newCachedThreadPool();

	public CommandInvoker() {
		threadPool.execute(this);
	}

	/**
	 * �������
	 */
	public synchronized void sendCommand(Command command) {
		try {
			boolean flag = hasNext();
			queue.put(command);
			if (!flag) {// just add right now
				notify();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * �ȴ�/��ȡ/ִ������
	 */
	@Override
	public void run() {
		while (true) {
			try {
				execute();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * ��������Ƿ�Ϊ��
	 */
	private synchronized boolean hasNext() {
		return queue.size() > 0;
	}
	
	/**
	 * ִ������
	 */
	private synchronized void execute() throws Exception {
		if (!hasNext()) {
			System.out.println("wait...");
			wait();
		} else {
			Command command = queue.take();
			command.execute();
		}
	}

}
