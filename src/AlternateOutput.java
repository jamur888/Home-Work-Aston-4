
public class AlternateOutput {
    private static final Object lock = new Object();
    private static boolean turnOfThread1 = true;

    public static void main(String[] args) {
        Thread thread1 = new Thread(() -> {
            while (true) {
                synchronized (lock) {
                    while (!turnOfThread1) {
                        try {
                            lock.wait();
                        } catch (InterruptedException e) {
                            Thread.currentThread().interrupt();
                        }
                    }
                    System.out.print("1 ");
                    turnOfThread1 = false;
                    lock.notify();
                }
            }
        });

        Thread thread2 = new Thread(() -> {
            while (true) {
                synchronized (lock) {
                    while (turnOfThread1) {
                        try {
                            lock.wait();
                        } catch (InterruptedException e) {
                            Thread.currentThread().interrupt();
                        }
                    }
                    System.out.print("2 ");
                    turnOfThread1 = true;
                    lock.notify();
                }
            }
        });

        thread1.start();
        thread2.start();
    }
}
