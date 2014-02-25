package cn.pattern.visitor;

import java.util.ArrayList;
import java.util.Collection;

public class test {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		Visitor v = new ConcreteVisitor();
		Collection<Object> col= new ArrayList<Object>();
		Visible vis = new StringElement();
		col.add(vis);
		vis = new IntegerElement();
		col.add(vis);
		
		v.visitCollection(col);
	}

}
