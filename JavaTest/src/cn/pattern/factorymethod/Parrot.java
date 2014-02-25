package cn.pattern.factorymethod;

public class Parrot implements Animal {

	public void fly() {
		System.out.println("Parrot will fly");
	}
	
	@Override
	public void eat() {
		System.out.println("Parrot will eat");
	}

}
