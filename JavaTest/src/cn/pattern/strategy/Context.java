package cn.pattern.strategy;

public class Context {
	private IStrategy mStrategy;
	
	public Context() {
		
	}
	
	public void setStrategy(IStrategy strategy) {
		mStrategy = strategy;
	}
	
	public void operate() {
		mStrategy.operate();
	}
}
