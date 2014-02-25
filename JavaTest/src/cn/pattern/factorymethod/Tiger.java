package cn.pattern.factorymethod;

public class Tiger implements Animal {

	public void run() {
		System.out.println("Tiger will run.");
	}
	
	@Override
	public void eat() {
		System.out.println("Tiger will eat.");
	}

}
