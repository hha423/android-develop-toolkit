package cn.pattern.builder;

public class Director {
	
	private Factory factory;
	
	public Director(Factory factory) {
		this.factory = factory;
	}
	
	public Motorcycle assembleMotorcycle() {
		
		Carcase carcase = factory.createCarcase();
		carcase.build();
		
		Wheel wheel = factory.createWheel();
		wheel.build();
		
		Tyre tyre = factory.createTyre();
		tyre.build();
		
		Engine engine = factory.createEngine();
		engine.build();
		
		return new Motorcycles();
	}

}
