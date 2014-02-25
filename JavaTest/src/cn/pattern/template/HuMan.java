package cn.pattern.template;

public abstract class HuMan {
	
	public abstract void eat();
	public abstract void drink();
	public abstract void work();
	public abstract void sleep();
	
	final public void live() {
		this.eat();
		this.drink();
		this.work();
		this.sleep();
	}
}
