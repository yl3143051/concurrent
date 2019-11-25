package c_005;

public class MyContainer9 {

    private boolean isSub = true;

    private int count = 0;

    public synchronized void toFalse() {
        try {
            while (!isSub) {
                this.wait();
            }
            System.out.println("sub  ---- " + count);
            isSub = false;
            this.notify();
        } catch (Exception e) {
            e.printStackTrace();
        }
        count++;
    }

    public synchronized void toTrue() {
        try {
            while (isSub) {
                this.wait();
            }
            System.out.println("main (((((((((((( " + count);
            isSub = true;
            this.notify();
        } catch (Exception e) {
            e.printStackTrace();
        }
        count++;
    }

    public static void main(String[] args) {
        final MyContainer9 ot = new MyContainer9();
        for (int j = 0; j < 100; j++) {
            new Thread(new Runnable() {
                public void run() {
//                    try {
//                        Thread.sleep(10);
//                    } catch (InterruptedException e) {
//                        e.printStackTrace();
//                    }
                    for (int i = 0; i < 5; i++) {
                        ot.toFalse();
                    }
                }
            }).start();

            new Thread(new Runnable() {
                public void run() {
//                    try {
//                        Thread.sleep(10);
//                    } catch (InterruptedException e) {
//                        e.printStackTrace();
//                    }
                    for (int i = 0; i < 5; i++) {
                        ot.toTrue();
                    }
                }
            }).start();
        }

    }
}
