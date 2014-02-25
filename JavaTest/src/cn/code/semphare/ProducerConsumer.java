package cn.code.semphare;

public class ProducerConsumer {
    public static void main(String[] args) {
        //我认为最关键的就是根据问题，找到如下三个变量，并赋值，很多时候，我们就是不能够根据问题，很好的找到临界资源值
        int mutex = 1;//缓冲池是临界资源，所有的生成者和消费者都要使用它，且都要改变它的状态，故对于缓冲池的操作必须是互斥的
        int empty = 10;//表示空闲的缓冲区的数目，初始值为10
        int full = 0;//full表示有数据的缓冲区的数目，初始值
        
        Semaphore[] s = new Semaphore[3];
        s[0] = new Semaphore(mutex);
        s[1] = new Semaphore(empty);
        s[2] = new Semaphore(full);
        
        Producer p = new Producer(0, s);
        Consumer c = new Consumer(0, s);
        
        Thread pp = new Thread(p);
        Thread pp1 = new Thread(p);
        Thread cc = new Thread(c);

        pp.start();
        pp1.start();
        cc.start();
    }

}
