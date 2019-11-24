package c_001;

import java.util.concurrent.TimeUnit;
/**
 * 一个同步方法可以调用另外一个同步方法，一个线程已经拥有某个对象的锁，再次申请的时候仍然会得到该对象的锁.
 * 也就是说synchronized获得的锁是可重入的
 * (在一个对象内写两个方法都加上synchronized  然后一个方法内调用另一个方法 如果不发生死锁就就说明是可以重入的)
 * @author mashibing
 */
public class Test {

    void m1() {
//        synchronized ("a") {
        synchronized (this) {
            System.out.println("m1 start");
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            m2();
        }

    }

    void m2() {
//        synchronized ("a") {
        synchronized (this) {
            try {
                TimeUnit.SECONDS.sleep(2);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("m2");
        }

    }
    public static void main(String[] args) {
        Test test = new Test();
        new Thread(test::m1).start();
    }
}
