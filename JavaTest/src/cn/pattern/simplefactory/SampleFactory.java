package cn.pattern.simplefactory;

public class SampleFactory {
	/*
	 * ��ݲ�����������ͬ�Ķ���
	 */
	public static Animal createAnimal(String animalName) throws Exception {
		if (animalName.equals("Tiger")) {
			return new Tiger();
		} 
		if (animalName.equals("Dolphin")) {
			return new Dolphin();
		}
		if (animalName.equals("Parrot")) {
			return new Parrot();
		}	
		else  {
			throw new Exception("No such animal, please check your vars");
		}
			
	}
}
