package cn.code.semphare.inner;

import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Semaphore;

public class CommandInvoker {

	public static void execCommand(final ExecutorService pool, final Semaphore sp) {
		pool.execute(new Runnable() {
			@Override
			public void run() {
				while (true) {
					int time = new Random().nextInt(3) * 1000;
					System.out.println("spent time:" + time);
					try {
						Thread.sleep(time);
					} catch (InterruptedException e1) {
						e1.printStackTrace();
					}
					try {
						sp.acquire();
						System.out.println("exec common command...");
					} catch (InterruptedException e) {
						e.printStackTrace();
					} finally {
						sp.release();
					}
				}
			}
		});
	}

	public static void readRecord(final ExecutorService pool, final Semaphore sp) {
		pool.execute(new Runnable() {
			@Override
			public void run() {
				while (true) {
					System.out.println("spent time:" +3500);
					try {
						Thread.sleep(7 * 500);
					} catch (InterruptedException e1) {
						e1.printStackTrace();
					}
					try {
						sp.acquire();
						System.out.println("read and upload record...");
					} catch (InterruptedException e) {
						e.printStackTrace();
					} finally {
						sp.release();
					}
				}
			}
		});
	}

}
