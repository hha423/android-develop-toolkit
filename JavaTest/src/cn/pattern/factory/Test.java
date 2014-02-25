package cn.pattern.factory;

public class Test {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		AbstractFactory factory = null;
		Product product = null;
		
		factory = new PlaneFactory();
		product = factory.getProduct();
		product.perform();

		factory = new CarFactory();
		product = factory.getProduct();
		product.perform();
		
	}

}
