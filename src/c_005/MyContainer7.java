package c_005;

import java.util.LinkedList;
import java.util.concurrent.TimeUnit;

public class MyContainer7<T> {

    final private LinkedList<T> lists = new LinkedList<>();
    final private int MAX = 10;
    private int count = 0;


    public synchronized void put(T t) {
        while (lists.size() == MAX) {
            try {
                this.wait(); //wait释放锁 但是被notify唤醒后还会去竞争锁 才会再去执行接下来的逻辑
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        lists.add(t);
        ++count;
        this.notify();
    }

    public synchronized T get() {
        T t = null;
        while (lists.size() == 0) {
            try {
                this.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        t = lists.removeFirst();
        count--;
        this.notify();
        return t;
    }

    public static void main(String[] args) {
        MyContainer7<String> c = new MyContainer7<>();
        //启动消费者线程
        for (int i = 0; i < 100; i++) {
            new Thread(() -> {
                for (int j = 0; j < 5; j++) System.out.println(Thread.currentThread().getName() + "消费了：" + c.get());
            }, "c" + i).start();
        }

        try {
            TimeUnit.SECONDS.sleep(2);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        //启动生产者线程
        for (int i = 0; i < 20; i++) {
            new Thread(() -> {
                for (int j = 0; j < 25; j++) c.put(Thread.currentThread().getName() + " " + j);
            }, "p" + i).start();
        }
    }
}
