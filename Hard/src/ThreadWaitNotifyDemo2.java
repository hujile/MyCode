/**
 * 1.高内聚，低耦合，线程操作资源类
 * 2.判断 干活 通知
 * 3.避免虚假唤醒（所有判断使用while）
 */
public class ThreadWaitNotifyDemo2 {

    public static void main(String[] args) {
        ShareResource sr = new ShareResource();
       new Thread(()->{
           for (int i = 1; i <= 10 ; i++) {
               try {
                   sr.increment();
               } catch (InterruptedException e) {
                   e.printStackTrace();
               }
           }
       },"A").start();
       new Thread(()->{
           for (int i = 1; i <= 10 ; i++) {
               try {
                   sr.decrement();
               } catch (InterruptedException e) {
                   e.printStackTrace();
               }
           }
       },"B").start();
       new Thread(()->{
           for (int i = 1; i <= 10 ; i++) {
               try {
                   sr.increment();
               } catch (InterruptedException e) {
                   e.printStackTrace();
               }
           }
       },"C").start();
       new Thread(()->{
           for (int i = 1; i <= 10 ; i++) {
               try {
                   sr.decrement();
               } catch (InterruptedException e) {
                   e.printStackTrace();
               }
           }
       },"D").start();

    }
}
class ShareResource{
    private int number = 0;
    public synchronized void increment() throws InterruptedException {
        //判断
        while(number!=0){
            this.wait();
        }
        //干活
        number++;
        System.out.println(Thread.currentThread().getName()+"\t"+number);
        //通知
        this.notifyAll();
    }
    public synchronized void decrement() throws InterruptedException {
        while(number==0){
            this.wait();
        }
        number--;
        System.out.println(Thread.currentThread().getName()+"\t"+number);

    }
}