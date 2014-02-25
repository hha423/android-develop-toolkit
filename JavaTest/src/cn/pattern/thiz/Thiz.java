package cn.pattern.thiz;

public class Thiz {
	
	private String title;
	private String message;
	private Thiz self = this;
	public Thiz Builder() {
		return self;
	}
	
	public Thiz setTitle(String ti) {
		title = ti;
		return self;
	}
	
	public Thiz setMessage(String msg) {
		message = msg;
		return self;
	}
	
	public Thiz show() {
		System.out.println("show message..."+title+"\n"+message);
		return self;
	}
	
	public static void main(String[] args) {
		new Thiz().Builder().setTitle("hello").setMessage("hello, world").show();
	
		Thiz z = new Thiz().Builder();
		z.setTitle("hello");
		z.setMessage("hello, world");
		z.show();
	}
}
