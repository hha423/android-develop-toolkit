package cn.pattern.factorymethod;

public class TigerFactory implements Factory {

	@Override
	public Animal createAnimal() {		
		return new Tiger();
	}

}
