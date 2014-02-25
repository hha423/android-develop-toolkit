package cn.code.semphare;

public class Producer implements Runnable {
    private Semaphore[] s;
    private int i;

    public Producer(int i, Semaphore[] sp) {
        this.i = i;
        this.s = sp;
    }

    public void run() {
        while (true) { // 多个p操作，次序不能颠倒，否则可能引起死锁
            s[1].p();// 空区减１
            s[0].p();// 获得信号
            System.out.println("produce " + i++);
            // 让它休息会儿，这样我们就能比较清楚的观察运行过
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            s[0].v(); // 归还信号�?
            s[2].v(); // 缓冲区加�?
        }
    }

}
