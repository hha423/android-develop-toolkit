package cn.pattern.command;

public interface  Command {
	public final static int NONE = -1;
	public final static int QUERY = 0;
	public final static int EXPENSE = 1;
	public final static int COMMON = 2;
	
	public void execute() throws Exception;
}
