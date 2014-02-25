package cn.code.thread;

public class ThreadMultiTest {
	public static void main(String[] args) {
		Runner runner = new Runner();
		new Thread(runner).start();
		new Thread(runner).start();
		new Thread(runner).start();
		new Thread(runner).start();
	}
}

class Runner implements Runnable {
	Object sync = new Object();
	int i = 100;
	
	public void run() {
		while (true) {
			synchronized (sync) {
				if(i >=0) {
					System.out.println("thread-"+Thread.currentThread().getId()+", no:"+i);
					i--;
				} else {
					break;
				}
			}
			try {Thread.sleep(1*10);} catch (InterruptedException e) {e.printStackTrace();}
		}
	}

}
