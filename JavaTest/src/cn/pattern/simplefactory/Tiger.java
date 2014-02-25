package cn.pattern.simplefactory;

public class Tiger implements Animal {

	//@Override
	public void eat() {
		System.out.println("Tiger will eat.");
	}

	public void run() {
		System.out.println("Tiger will run.");
	}
}
