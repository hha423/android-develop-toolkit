package cn.pattern.simplefactory;

public class Client {
	/*
	 * �򵥹���ģʽ���ֽо�̬����ģʽ������һ������Ĺ����������𴴽�һЩ���ʵ��
	 * ����Щ���������඼Ӧ����һ����ͬ�ĸ��࣬����Ϳ���ʵ��������������������̡�
	 */
	public static void main(String[] args) {
		try {
			Animal animal = SampleFactory.createAnimal("Tiger");
			animal.eat();
			animal = SampleFactory.createAnimal("Dolphin");
			animal.eat();
			animal = SampleFactory.createAnimal("Parrot");
			animal.eat();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
