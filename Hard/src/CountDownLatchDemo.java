import java.util.concurrent.CountDownLatch;

/**
 *
 */
public class CountDownLatchDemo {
    public static void main(String[] args) throws InterruptedException {
        CountDownLatch countDownLatch = new CountDownLatch(6);
        for (int i = 1; i <= 6; i++) {
            new Thread(() -> {
                System.out.println(Thread.currentThread().getName() + "\t" + "国被灭");
                countDownLatch.countDown();
            }, CountryEnum.forEach_CountryEnum(i).getRetMessage()).start();
        }
        countDownLatch.await();
        System.out.println(Thread.currentThread().getName() + "\t" + "一统华夏");
    }

    public static void closeDoor() throws InterruptedException {
        CountDownLatch countDownLatch = new CountDownLatch(6);
        for (int i = 1; i <= 6; i++) {
            new Thread(() -> {
                System.out.println(Thread.currentThread().getName() + "\t" + "同学离开");
                countDownLatch.countDown();
            }, String.valueOf(i)).start();
        }
        countDownLatch.await();
        System.out.println(Thread.currentThread().getName() + "\t" + "班长关门");
    }

    public enum CountryEnum {
        ONE(1, "齐"), TWO(2, "楚"), THREE(3, "燕"), FOUR(4, "赵"), FIVE(5, "魏"), SIX(6, "韩");

        public Integer getRetCode() {
            return retCode;
        }

        public String getRetMessage() {
            return retMessage;
        }

        CountryEnum(Integer retCode, String retMessage) {
            this.retCode = retCode;
            this.retMessage = retMessage;
        }

        private Integer retCode;

        private String retMessage;


        public static CountryEnum forEach_CountryEnum(int index) {
            CountryEnum[] myArray = CountryEnum.values();
            for (CountryEnum element : myArray) {
                if (index == element.getRetCode()) {
                    return element;
                }
            }
            return null;
        }
    }
}