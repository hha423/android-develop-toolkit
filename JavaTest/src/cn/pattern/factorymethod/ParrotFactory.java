package cn.pattern.factorymethod;

public class ParrotFactory implements Factory {

	@Override
	public Animal createAnimal() {
		return new Parrot();
	}

}
