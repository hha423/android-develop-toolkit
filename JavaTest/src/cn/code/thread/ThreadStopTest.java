package cn.code.thread;

public class ThreadStopTest {

    /**
     * 线程中止，两步走
     * 
     * @param args
     */
    public static void main(String[] args) {
        TestThread t = new TestThread();
        t.start();

        try {
            Thread.sleep(2 * 1000);
            System.out.println("try to shutdown thread...");
            t.shutThread();
            System.out.println("main join...");
            t.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("end..."); //t.isAlive()=false
    }
    
    static  class TestThread extends Thread {
        private long counter = 0;
        private volatile boolean shutdownRequested = false;

        @Override
        public void run() {
            super.run();
            try {
                while (!shutdownRequested) {
                    doWork();
                }
            } catch (Exception e) {

            } finally {
                doShutdown();
            }
        }

        private void doWork() throws InterruptedException {
            counter++;
            System.out.println("cur counter value:" + counter);
            Thread.sleep(50);
        }

        public void shutThread() {
            shutdownRequested = true;
            interrupt();
        }

        private void doShutdown() {
            System.out.println("counter=" + counter + " till thread shutdown");
        }

    }


}
