package cn.pattern.bridge;

public class BigHorse extends Horse {
	
	public BigHorse() {
		setWear();
	}

	@Override
	public void show() {
		getWear().wear();
		System.out.println("big...");
	}
	
}
