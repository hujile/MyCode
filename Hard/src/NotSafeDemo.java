import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.CopyOnWriteArraySet;

public class NotSafeDemo {
    public static void main(String[] args) {
       // List<String> list = new ArrayList<>();
        List<String> list = new CopyOnWriteArrayList<>();
        for (int i = 0; i < 30; i++) {
            new Thread(() ->{
                list.add(UUID.randomUUID().toString().substring(0,4));
                System.out.println(list);
            },String.valueOf(i)).start();
        }
        mapNoSafe();
        setNoSafe();
            /*运行结果如下
            * [null, fc3d, 07f1]
              [null, fc3d, 07f1]
              [null, fc3d, 07f1]
              之所以出现null是因为ArrayList是线程不安全的，没有使用同步锁，
              所以读写操作不会等待，可能当某一个线程还没有被填写时，就会被
              读取，所以就出现了null值，当线程数量加大时，例如为30，就会出现
              ConcurrentModificaitonException异常，Java并发修改异常，这个
              异常可以使用Vector解决，或使用
              List<String> list = Collections.synchronized(new ArrayList())解决
              但一般企业开发中不使用上述两个方法
              这时就需要使用写时复制CopyOnWrite* */
        /**
         * CopyOnWrite容器即写时复制的容器，其实现原理：
         * 向一个容器中添加元素的时候，并不直接向Object[]elements中添加，
         * 而是通过Arrays.copyOf方法复制一个elements-->newElements，并在声明中令其length+1，
         * 然后将所要添加的元素添加到newElements,最后将elements的引用指向newElements，
         * 使用setArray(newElements)方法。
         * 所以写时复制也是一种读写分离的思想，读和写分别在不同的容器中实现
         */
        //源码
        /**
         * public boolean add(E e)
         * {
         *     final ReentrantLock lock = this.lock;
         *     lock.lock();
         *     try{
         *          Object[] elements = getArray();
         *          int len = elements.length;
         *          Object[] newElements = Arrays.copyOf(elements,len+1);
         *          setArray(newElements);
         *          return true;
         *     }finally{
         *     lock.unlock();
         *  }
         * }
         */
    }
    //Set和Map类同（HashSet底层就是HashMap）
    public static void setNoSafe(){
        Set<String> set = new CopyOnWriteArraySet<>();//使用HashSet会出现上述同样的异常ConcurrentModificationException
        for (int i = 0; i < 30; i++) {
           new Thread(()->{
               set.add(UUID.randomUUID().toString().substring(0,4));
               System.out.println(set);
           },String.valueOf(i)).start();
        }
    }
    public static void mapNoSafe(){
        //使用HashMao会出现上述同样的异常ConcurrentModificaitonException
        Map<String,String> map = new ConcurrentHashMap<>();
        for (int i = 0; i < 30; i++) {
            new Thread(() ->{
                map.put(Thread.currentThread().getName(),UUID.randomUUID().toString().substring(0,4));
                System.out.println(map);
            },String.valueOf(i)).start();
        }
    }
}


