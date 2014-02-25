package cn.pattern.factory;

public class Car implements Product {

	@Override
	public void perform() {
		System.out.println("this is a car");
	}

}
