package cn.pattern.adapter;

public class BlueQuakable implements Quakable {

	@Override
	public void fly() {
		System.out.println("It's blue duck fly");
	}

	@Override
	public void quak() {
		System.out.println("It's blue duck quak");
	}

}
