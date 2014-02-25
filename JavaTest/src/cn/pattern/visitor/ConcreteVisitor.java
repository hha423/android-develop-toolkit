package cn.pattern.visitor;

import java.util.Collection;
import java.util.Iterator;

public class ConcreteVisitor implements Visitor {

	@Override
	public void visitCollection(Collection<?> col) {
		Iterator<?> it = col.iterator();
		while(it.hasNext()) {
			Object o = it.next();
			if(o instanceof Visible) {
				((Visible)o).accept(this);
			}
		}
	}

	@Override
	public void visitString(String string) {
		System.out.println("visite string:"+string);
	}

	@Override
	public void visitInteger(Integer integer) {
		System.out.println("visite string:"+integer);
	}

}
