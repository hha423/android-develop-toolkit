package cn.pattern.bridge;

public abstract class Horse {
	private Wear wear;

	protected void setWear() {
		this.wear =HorseFactory.getSear();
	}
	
	protected Wear getWear() {
		return this.wear;
	}

	public abstract void show();
}
