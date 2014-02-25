package cn.pattern.template;

public class WoMan  extends HuMan{

	@Override
	public void eat() {
		System.out.println("woman eat.");
	}

	@Override
	public void drink() {
		System.out.println("woman drink.");
	}

	@Override
	public void work() {
		System.out.println("woman work.");
	}

	@Override
	public void sleep() {
		System.out.println("woman sleep.");
	}

}
