package cn.pattern.strategy;

/**
 * Test
 * 
 * @author Administrator
 */
public class ZhaoYun {
    public static void main(String[] args) {
        Context context = new Context();
        context.setStrategy(new BackDoor());
        context.operate();

        context.setStrategy(new GivenGreenLight());
        context.operate();

        context.setStrategy(new BlockEneny());
        context.operate();
    }
}
