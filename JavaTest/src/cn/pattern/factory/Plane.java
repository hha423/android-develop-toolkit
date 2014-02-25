package cn.pattern.factory;

public class Plane implements Product {

	@Override
	public void perform() {
		System.out.println("this is a plane");
	}

}
