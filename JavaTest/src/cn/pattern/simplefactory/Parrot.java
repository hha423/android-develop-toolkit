package cn.pattern.simplefactory;


public class Parrot implements Animal {

	@Override
	public void eat() {
		System.out.println("Parrot will eat");
	}
	
	public void fly() {
		System.out.println("Parrot will fly");
	}
	
}