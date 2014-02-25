package cn.pattern.strategy;

public class GivenGreenLight implements IStrategy{

	@Override
	public void operate() {
		System.out.println("绿灯");
	}

}
