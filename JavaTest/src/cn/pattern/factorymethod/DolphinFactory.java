package cn.pattern.factorymethod;

public class DolphinFactory implements Factory {

	@Override
	public Animal createAnimal() {
		return new Dolphin();
	}

}
