package cn.pattern.adapter;

public class WhiteGooseable implements Gooseable{

	@Override
	public void goose() {
		System.out.println("It's goose  goose");
	}

	@Override
	public void fly() {
		System.out.println("It's goose  fly");
	}


}
