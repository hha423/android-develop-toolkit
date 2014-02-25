package cn.pattern.salary;

public class SampleFactory {

	public static Salary createSalary(String name) throws Exception {
		if (name.equals("HeBeiSalary") || name.equals("JiLinSalary")) {
			Class<?> c = Class.forName(name);
			Salary salary = (Salary) c.newInstance();
			return salary;
		} else {
			throw new Exception("no such company");
		}
	}
}