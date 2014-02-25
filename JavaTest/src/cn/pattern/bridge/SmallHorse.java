package cn.pattern.bridge;

public class SmallHorse extends Horse {

	public SmallHorse() {
		setWear();
	}
	
	@Override
	public void show() {
		getWear().wear();
		System.out.println("small...");
	}

}
