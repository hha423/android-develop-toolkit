package cn.pattern.builder;

/*
 * ������ģʽ����һ�������Ӷ���Ĺ�������ı�ʾ���룬
 * ʹ��ͬ��Ĺ�����̿��Դ�����ͬ�ı�ʾ�����ҿͻ��˲���֪������Ĺ���ϸ�ڡ�
 */

public class Client {
	
	public static void main(String[] args) {
		Director director = new Director(new MotorcycleFactory());
		director.assembleMotorcycle();
	}

}
