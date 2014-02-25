package cn.pattern.proxy.dyn;

public class ManWorker implements Worker {

    @Override
    public void sleep() {
        System.out.println("sleep a moment");
    }

    @Override
    public void working(int sec) {
        for (int index = 0; index < sec; index++)
            System.out.println("working...");
    }

}
