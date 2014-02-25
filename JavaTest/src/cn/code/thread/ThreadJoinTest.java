package cn.code.thread;

public class ThreadJoinTest {
	static int a = 0;

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			System.out.println(getResult());
		} catch (InterruptedException e) {
			e.printStackTrace();
			System.out.println("err");
		}
	}

	/**
	 * 将一个多线程的处理变成一个顺序处理过程。
	 * @return
	 * @throws InterruptedException
	 */
	private static int getResult() throws InterruptedException {
		Thread t = new Thread(new Runnable() {
			@Override
			public void run() {
				a = 10;
				try {
					Thread.sleep(2000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		});
		t.start();
		long start=System.currentTimeMillis();
		System.out.println("start:"+start);
		t.join();// 等Thread run over
		System.out.println("join:"+(System.currentTimeMillis()-start)+"ms");
		return a;
	}
}
