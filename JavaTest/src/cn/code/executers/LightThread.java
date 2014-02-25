package cn.code.executers;

public class LightThread implements Runnable{
	protected int countDown  = 10;
	private static int taskCount = 0;
	private final int id = taskCount++;
    private Object lock;

	public LightThread(Object o) {
		this.lock = o;
	}
	
	public LightThread(Object o, int countDown) {
	    this.lock = o;
		this.countDown = countDown;
	}
	
	public String getState() {
	    synchronized (lock) {
	        return "#"+id+"("+(countDown>0?countDown:"lightoff!")+").";
        }
	}
	
	@Override
	public void run() {
		while(countDown-->0) {
			System.out.println(getState());
			Thread.yield();
		}
	}

}
