/***
 * 题目：两个线程，可以操作初始值为0，实现一个线程对该变量+1，
 * 另一个线程对该变量-1,交替10轮，变量初始值为0
 *1.高内聚，低耦合，线程操作资源类
 *2.判断 干活 通知
 *3.避免虚假唤醒（此demo为错误实例，判断应使用while）
 */
public class ThreadWaitNotifyDemo {
    public static void main(String[] args) {
        ShareData shareData = new ShareData();
        new Thread(() -> {
            for (int i = 1; i <= 10 ; i++)
                try {
                    shareData.increment();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
        },"A").start();
        new Thread(() -> {
            for (int i = 1; i <= 10 ; i++)
                try {
                    shareData.decrement();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
        },"B").start();
        new Thread(() -> {
            for (int i = 1; i <= 10 ; i++)
                try {
                    shareData.increment();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
        },"C").start();
        new Thread(() -> {
            for (int i = 1; i <= 10 ; i++)
                try {
                    shareData.decrement();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
        },"D").start();
    }
}
class ShareData{
    private int number = 0;
    public synchronized void increment() throws InterruptedException {
        //判断
        if(number!=0){
            this.wait();
        }
        //干活
        number++;
        //通知
        System.out.println(Thread.currentThread().getName()+"\t"+number);

        this.notifyAll();
    }
    public synchronized void decrement() throws InterruptedException {
        if(number==0){
            this.wait();
        }
        number--;
        System.out.println(Thread.currentThread().getName()+"\t"+number);
        this.notifyAll();
    }
}