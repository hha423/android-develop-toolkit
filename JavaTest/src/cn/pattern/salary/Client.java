package cn.pattern.salary;

public class Client {

	public static void main(String[] args) {
		try {
			Salary salary = SampleFactory.createSalary("HeBeiSalary");
			salary.computeSalary();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
