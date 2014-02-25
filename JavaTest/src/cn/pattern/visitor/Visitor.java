package cn.pattern.visitor;

import java.util.Collection;

public interface Visitor {
	public void visitCollection(Collection<?> col);
	public void visitString(String string);
	public void visitInteger(Integer integer);
}
