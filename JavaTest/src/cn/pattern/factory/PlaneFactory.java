package cn.pattern.factory;

public class PlaneFactory extends AbstractFactory {

	@Override
	public Product getProduct() {
		return new Plane();
	}

}
