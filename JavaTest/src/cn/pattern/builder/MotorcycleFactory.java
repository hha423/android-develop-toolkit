package cn.pattern.builder;

public class MotorcycleFactory implements Factory {

	@Override
	public Carcase createCarcase() {
		return new Carcases();
	}

	@Override
	public Wheel createWheel() {
		return new Wheels();
	}

	@Override
	public Tyre createTyre() {
		return new Tyres();
	}

	@Override
	public Engine createEngine() {
		return new Engines();
	}

}
