package cn.code.executers;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Test {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Object lock = new Object();
		
		System.out.println("========"); //执行器管理Thread对象
		ExecutorService exec = Executors.newCachedThreadPool();
		for(int i =0; i< 5; i++) {
			exec.execute(new LightThread(lock));
		}
	}

}
