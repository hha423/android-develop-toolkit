package cn.code.callback;

public class CallMe implements InterestingEvent
{
	
	private EventNotifier en;
	
	public CallMe() {
		en = new EventNotifier(this); //相当于内部类
	}

	@Override
	public void interestingEvent() {
		System.out.println("sth happened.");
	}
	
	public void show() {
		en.doWork();
	}

}
