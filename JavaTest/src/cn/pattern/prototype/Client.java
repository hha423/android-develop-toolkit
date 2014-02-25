package cn.pattern.prototype;

/**
 * 测试
 */

public class Client {

	public static void main(String[] args) {
		KeyPrototype copperKey = new CopperKey();
		copperKey.setLength(3.2f);
		copperKey.setThick(0.5f);

		KeyPrototype alumKey = (KeyPrototype) copperKey.clone();
		alumKey.setColor("yin se");
		System.out.println("copper key color:" + copperKey.getColor());
		System.out.println("alum key color:" + alumKey.getColor());

		KeyPrototype alumKeyb = new AluminiumKey();
		alumKeyb.setLength(3.1f);
		alumKeyb.setThick(0.4f);

		KeyPrototype copperKeyb = (KeyPrototype) alumKeyb.clone();
		copperKeyb.setColor("yello");
		System.out.println("copper key color:" + copperKeyb.getColor());
		System.out.println("alum key color:" + alumKeyb.getColor());

	}

}
