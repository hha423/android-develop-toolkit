package cn.pattern.factory;

public class CarFactory extends AbstractFactory {

	@Override
	public Product getProduct() {
		return new Car();
	}

}
