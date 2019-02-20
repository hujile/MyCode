import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

/***
 * 多线程中，第三种获得多线程的方式
 */
public class CallableDemo {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        FutureTask<Integer> futureTask = new FutureTask<Integer>(()->{
            System.out.println("come in lambda express");
            return 521;
        });
        Thread t1 = new Thread(futureTask,"A");
        t1.start();
        System.out.println("FutureTask："+futureTask.get());
    }
}
class MyThread implements Runnable{

    @Override
    public void run() {

    }
}
class MyThread2 implements Callable<Integer>{

    @Override
    public Integer call() throws Exception {
        System.out.println("Come in");
        return 520;
    }
}