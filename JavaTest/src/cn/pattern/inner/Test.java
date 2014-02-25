package cn.pattern.inner;

public class Test {
    public static void main(String[] args) {
        StrategyManager manager = new StrategyManager();
        // add startety
        for (int i = 0; i < 10; i++) {
            final int x = i * i;
            manager.add(new Strategy() {
                @Override
                protected String setName() {
                    return "hello,this is " + x;
                }
            });
        }

        Taker taker = manager.getTaker();
        // take strategy to execute
        for (int i = 0; i < 10; i++) {
            taker.getStrategy().show();
        }
    }
}