import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
     * 题目：8锁
     * 1.标准访问情况下，先打印短信还是邮件 :Message(最好在两个线程之间sleep一下线程，保证顺序的先后)
     * 2.短信暂停4s，先打印短信还是邮件：Message
     *    线程访问到资源类中的方法时，在未使用完毕之前，会将整个资源类锁起来
 *         一个对象如果有多个synchronized方法，在某一个时刻，只要一个线程去调用
 *         其中的一个synchronized方法了，其它的线程都只能等待，换句话说，
 *         在某一个时刻内，只能有唯一一个线程去访问这些synchronized方法，
 *         而被上锁的是当前对象this，即整个资源类，被锁定后其他线程都不能进入到当前对象的其它synchronized方法
     * 3.新增开机方法，先打印短信还是开机：Open
 *          对象内的synchronized方法和普通方法风马牛不相及，无关
     * 4.有两部手机，先打印短信还是邮件：Email
 *          两个相同对象内的synchronized方法，由于是两个资源类，被线程访问synchronized时加锁资源类，
 *          但加锁的类并不是同一个类，所以互不影响，所有的非静态同步方法用的是同一个锁，即实例对象本身
     * 5.静态同步方法，同一部手机，先打印短信还是邮件：Message
 *     6.静态同步方法，两部手机，先打印短信还是邮件：Message
 *          static修饰的方法如果被锁，即为全局锁
 *      synchronized实现同步的基础：Java中的每一个对象都可以作为锁
 *      具体表现为以下三种形式：
 *      对于普通同步方法，锁的是当前实例对象
 *      对于静态同步方法，锁的是当前类的Class对象
 *      对于同步代码块，锁的是synchronized括号里配置的对象
 *      当一个线程试图访问同步代码块时，它首先必须得到锁，退出或抛出异常时必须释放锁
     *  也就是说如果一个实例对象的非静态同步方法获取锁后，该实例对象的其他非静态同步方法
 *      必须等待获取锁的方法释放锁后才能获取锁，所以勿需等待该实例对象已获取锁的非静态同步
 *      方法释放就可以获取他们自己的锁
     * 7.一个静态同步，一个普通同步，一部手机，先打印短信还是邮件：Email
     * 8.一个静态同步，一个普通同步，两部手机，先打印短信还是邮件：Email
 *      所有的静态同步方法用的也是同一把锁——类对象本身，
 *      这两把锁是两个不同的对象，所以静态同步方法与非静态同步方法之间
 *      是不会有静态条件的。但是一旦一个静态同步方法获取锁后，其他的静态同步方法
 *      都必须等待该方法释放锁后才能获取锁，而不管是同一个实例对象的静态同步方法之间
 *      还是不同的实例对象的静态同步方法之间，只要它们是同一个类的实例对象。
     */
    public class Lock8 {
        public static void main(String[] args) {
            Phone phone = new Phone();
            Phone phone2 = new Phone();
            new Thread(()->{
                phone.sendMessage();
            },"A").start();
            //最好在两个线程之间sleep一下线程，保证顺序的先后
            try{ Thread.sleep(200); }catch (InterruptedException e){ e.printStackTrace(); }
            new Thread(()->{
                //phone.sendEmail();
                phone2.sendEmail();
            },"B").start();

        }
}
class Phone{
        public static synchronized void sendMessage(){
                try {
                    TimeUnit.SECONDS.sleep(4);
                }catch (InterruptedException e){
                    e.printStackTrace();
                }
                System.out.println("Message");
        }
        public  synchronized void sendEmail(){
                System.out.println("Email");
        }
        public void openPhone(){
                System.out.println("openPhone");
        }
}
