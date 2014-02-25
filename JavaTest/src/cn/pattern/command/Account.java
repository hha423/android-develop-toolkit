package cn.pattern.command;

public class Account {
	public int money = 1000;
	private final static Account INSTANCE = new Account();

	public synchronized static Account getInstance() {
		return INSTANCE;
	}

	public synchronized String query() {
		return "�������:"+String.valueOf(money);
	}

	public synchronized String expense(String amount) {
		int temp = money - Integer.valueOf(amount);
		if (temp > 0) {
			money = temp;
			return "��ѽ��:"+amount+",�������:"+String.valueOf(money);
		} else {
			return "����,���ֵ";
		}
	}

	public synchronized String common(String ...cmds) {
		return "��ִ������:" + cmds;
	}
}
