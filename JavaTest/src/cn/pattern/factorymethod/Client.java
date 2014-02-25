package cn.pattern.factorymethod;

public class Client {
	/*
	 * ��������ģʽ�����󹤳��ฺ�𴴽�����Ľӿڣ�
	 * �������Ĵ���������ʵ�ֳ��󹤳��ľ���������ɡ�
	 */
		public static void main(String[] args) {
		Factory factory = new TigerFactory();
		Animal animal = factory.createAnimal();
		animal.eat();
		
		factory = new DolphinFactory();
		animal = factory.createAnimal();
		animal.eat();
		
		factory = new ParrotFactory();
		animal = factory.createAnimal();
		animal.eat();
	}

}
