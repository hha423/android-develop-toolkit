package cn.pattern.command;

import java.util.Random;

public class test {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Runnable runnable1 = new Runnable() {
			@Override
			public void run() {
				for (int time = 0; time < 50; time++) {
					int r = new Random().nextInt(10);
					if (r == 0) {
						CommandUtils.command("init", handler1, 1);
					} else if (r == 1) {
						CommandUtils.query(handler1, 2);
					} else {
						CommandUtils.spend(new Integer(r).toString(), handler1,
								3);
					}
					try {
						Thread.sleep((r + 1) * 500);
					} catch (Exception e) {
					}
				}
			}
		};

		Runnable runnable2 = new Runnable() {
			@Override
			public void run() {
				for (int time = 0; time < 50; time++) {
					int r = new Random().nextInt(5);
					if (r == 0) {
						CommandUtils.command("init", handler2, 1);
					} else if (r == 1) {
						CommandUtils.query(handler2, 2);
					} else {
						CommandUtils.spend(new Integer(r + 100).toString(),
								handler2, 3);
					}
					try {
						Thread.sleep((r + 1) * 500);
					} catch (Exception e) {

					}
				}
			}
		};

		Runnable runnable3 = new Runnable() {
			@Override
			public void run() {
				for (int time = 0; time < 50; time++) {
					int r = new Random().nextInt(100);
					if (r < 33) {
						CommandUtils.command("init", handler3, 1);
					} else if (33 <= r && r < 66) {
						CommandUtils.query(handler3, 2);
					} else {
						CommandUtils.spend(new Integer(r + 100).toString(),
								handler3, 3);
					}
					try {
						Thread.sleep((r + 1) * 500);
					} catch (Exception e) {

					}
				}
			}
		};

		new Thread(runnable1).start();

		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		new Thread(runnable2).start();

		try {
			Thread.sleep(10 * 1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		new Thread(runnable3).start();

	}

	static Handler handler1 = new Handler() {
		@Override
		public void handMessage(Message msg) {
			super.handMessage(msg);
			System.out.println("����:" + getOps(msg.what) + "," + msg.obj);
		}
	};

	static Handler handler2 = new Handler() {
		@Override
		public void handMessage(Message msg) {
			super.handMessage(msg);
			System.out.println("����:" + getOps(msg.what) + "," + msg.obj);
		}
	};

	static Handler handler3 = new Handler() {
		@Override
		public void handMessage(Message msg) {
			super.handMessage(msg);
			System.out.println("����:" + getOps(msg.what) + "," + msg.obj);
		}
	};

	protected static String getOps(int what) {
		if(what ==1) {
			return "����";
		} else if(what ==2) {
			return "��ѯ";
		} else {
			return "���";
		}
	}

}
