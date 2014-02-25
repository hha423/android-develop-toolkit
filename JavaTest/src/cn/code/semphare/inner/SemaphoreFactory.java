package cn.code.semphare.inner;

import java.util.concurrent.Semaphore;

public class SemaphoreFactory {
	private static Semaphore sem;
	
	public synchronized static Semaphore createSemaphore() {
	    if(sem==null) {
	        sem =  new Semaphore(2);
	    }
		return sem;
	}
}
