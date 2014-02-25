package cn.code.thread;

public class ThreadNotifyTest {

	/**
	 * notify通知wait唤醒
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		new Worker("");
	}

	private static int count = 0;

	private static class Worker {
		public Worker(Object o) {
			new Thread(new Productor(o)).start();
			new Thread(new Comsumer(o)).start();
		}
	}

	private static class Comsumer implements Runnable {
		private Object lock;

		public Comsumer(Object o) {
			this.lock = o;
		}

		@Override
		public void run() {
			while (true) {
				try {
					Thread.sleep(1 * 50); // 通知等待提前唤醒
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				synchronized (lock) { // 同步块
					if (count <= 0) {
						continue;
					}
					try {
						lock.wait(); // 处于等待，监视器对象可被其它线程获取
						System.out.println("product comsume...");
					} catch (Exception e) {
						System.out.println("err...");
					}
					count--;
					System.out.println("product comsume..., leave:" + count);
				}
			}
		}
	};

	private static class Productor implements Runnable {
		private Object lock;

		public Productor(Object o) {
			this.lock = o;
		}

		@Override
		public void run() {
			while (true) {
				try {
					Thread.sleep(1 * 20); // 通知等待提前唤醒
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				synchronized (lock) { // comsumer wait 之后 ，获得锁
					count++;
					lock.notify();
					System.out.println("product num:" + count);
				}
			}
		}
	};

}
