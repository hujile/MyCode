import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

public class CyclicBarrierDemo {
    public static void main(String[] args){
        CyclicBarrier cyclicBarrier = new CyclicBarrier(7,()->{
            System.out.println("哈哈哈哈哈哈，召唤神龙~~~~！");
        });
        for (int i = 1; i <= 7 ; i++) {
            //lambda表达式中只能引用标记了final的外层局部变量，
            //也就是说不能在lambda表达式中修改定义在作用域之外
            //的局部变量，否则会编译错误
            final int temp = i;
            new Thread(()->{
                try {
                    System.out.println(Thread.currentThread().getName()+"\t"+temp+"个龙珠");
                    cyclicBarrier.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (BrokenBarrierException e) {
                    e.printStackTrace();
                }
            },String.valueOf(i)).start();
        }
    }
}
