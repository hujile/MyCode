import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

//3个售票员出售30张票
public class TicketSale {
    public static void main(String[] args) {
        Ticket ticket = new Ticket();
        new Thread(() ->{ for (int i = 1; i <= 30 ; i++) ticket.sale(); },"A").start();
        new Thread(() ->{ for (int i = 1; i <= 30 ; i++) ticket.sale(); },"B").start();
        new Thread(() ->{ for (int i = 1; i <= 30 ; i++) ticket.sale(); },"C").start();
    }
}
class Ticket{
    private int tickets = 30;
    //synchronized在企业级开发中一般不使用，替换使用Lock
    private Lock lock = new ReentrantLock();
    //售票方法
    public void sale(){
        try{
            lock.lock();
            if(tickets>0){//防止超卖等问题，做出判断进行限制
                System.out.println(Thread.currentThread().getName()+
                        "出售第"+(tickets--)+"张票"+
                        ",剩余票数为："+tickets);
            }

        }catch(Exception e){
            e.printStackTrace();
        }finally{
            lock.unlock();
        }
    }
}