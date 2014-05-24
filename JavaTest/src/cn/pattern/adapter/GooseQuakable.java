package cn.pattern.adapter;

public class GooseQuakable implements Quakable {
	
	private Gooseable goose;
	
	public GooseQuakable(Gooseable goose) {
		this.goose=goose;
	}

	@Override
	public void fly() {
		goose.fly();
	}

	@Override
	public void quak() {
		goose.goose();
	}
	
}
