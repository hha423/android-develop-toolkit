package cn.code.callback;

public class EventNotifier {
	private InterestingEvent ie;
	private boolean sthHappended;
	
	public EventNotifier(InterestingEvent event) {
		ie = event;
		sthHappended = true;
	}

	public void doWork() {
		if(sthHappended) {
			ie.interestingEvent();
		}
	}

}
