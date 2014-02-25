package cn.pattern.bridge;

public class test {

	public static void main(String[] args) {
		HorseFactory.set(HorseFactory.WHITE);
		Horse big = new BigHorse();
		big.show();
		HorseFactory.set(HorseFactory.BLACK);
		big.show();
		
		Horse small = new SmallHorse();
		small.show();
		HorseFactory.set(HorseFactory.WHITE);
		small.show();
	}

}
