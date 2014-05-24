package cn.pattern.adapter;

public class Test {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Quakable blueq=new BlueQuakable();
		
		show(blueq);
		
		Gooseable goose=new WhiteGooseable();
		GooseQuakable gooseQuakable = new GooseQuakable(goose);
		
		show(gooseQuakable);
	}

	private static void show(Quakable blueq) {
		blueq.quak();
		blueq.fly();	
	}

}
