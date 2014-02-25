package cn.pattern.visitor;


public class StringElement implements Visible {
	public String value="hello world";
	
	@Override
	public void accept(Visitor vistor) {
		vistor.visitString(value);
	}

}
