package cn.pattern.bridge;

public class HorseFactory {
	static int color = 0;
	public static final int BLACK=0;
	public static final int WHITE=1;
	
	public  static void set(int c) {
		color = c;
	}
	
	public static Wear getSear() {
		if(color==BLACK) {
			return new BlackWear();
		} else {
			return new WhiteWear();
		}
	}

}
