package cn.code.semphare;

public class Consumer implements Runnable{
    private Semaphore[] s;
    private int i;
    public Consumer(int i,Semaphore[] sp){
        this.i = i;
        this.s = sp;
    }
    
    public void run() {
        while(true){
            s[2].p(); //消费缓冲
            s[0].p(); //消费信息
            System.out.println("consumer "+i++); 
            try {
                Thread.sleep(5*1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            s[0].v(); //归还信号
            s[1].v(); //空区增加
        }
    }

}
