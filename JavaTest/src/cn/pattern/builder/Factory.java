package cn.pattern.builder;

public interface Factory {
	Carcase createCarcase();
	Wheel createWheel();
	Tyre createTyre();
	Engine createEngine();
}
