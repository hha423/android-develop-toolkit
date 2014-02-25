package cn.code.semphare;

public class Semaphore {
    int value;

    public Semaphore(int v) {
        this.value = v;
    }

    // 定义P原语操作，原语操作就是执行时不能中断，所以synchronized修饰
    public synchronized void p() {
        value = value - 1;
        if (value < 0) {
            try {
                this.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public synchronized void v() {
        value = value + 1;
        if (value <= 0) {
            this.notify();
        }
    }
}
