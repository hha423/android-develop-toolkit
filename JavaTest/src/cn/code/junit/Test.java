package cn.code.junit;

import junit.framework.TestCase;

public class Test extends TestCase {
	public void test() throws Exception {
		User user2 = new User("sum", 23);
		System.out.println("user name:"+user2.getName()+",age:"+user2.getAge());
		user2.show();
	}
}
