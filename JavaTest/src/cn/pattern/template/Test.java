package cn.pattern.template;

/**
 * Test
 */
public class Test {
	public static void main(String[] args) {
		HuMan adult = new Man();
		adult.live();
		System.out.println("------");
		adult = new WoMan();
		adult.live();
	}
}
