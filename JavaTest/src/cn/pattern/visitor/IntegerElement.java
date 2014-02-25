package cn.pattern.visitor;


public class IntegerElement implements Visible {
	public Integer value=5;
	
	@Override
	public void accept(Visitor vistor) {
		vistor.visitInteger(value);
	}

}
