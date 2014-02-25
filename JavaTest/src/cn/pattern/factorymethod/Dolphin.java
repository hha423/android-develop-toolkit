package cn.pattern.factorymethod;

public class Dolphin implements Animal {

	public void swin() {
		System.out.println("Dolphin will swin.");
	}
	
	@Override
	public void eat() {
		System.out.println("Dolphin will eat.");
	}

}
