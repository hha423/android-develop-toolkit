package cn.pattern.template;

public class Man  extends HuMan{

	@Override
	public void eat() {
		System.out.println("man eat.");
	}

	@Override
	public void drink() {
		System.out.println("man drink.");
	}

	@Override
	public void work() {
		System.out.println("man work.");
	}

	@Override
	public void sleep() {
		System.out.println("man sleep.");
	}

}
