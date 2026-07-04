public class DeadLock {

    private static final Object lock1 = new Object();
    private static final Object lock2 = new Object();

    public static void main(String[] args) {

        Thread thread1 = new Thread(() -> {
            synchronized (lock1) {
                System.out.println("Поток 1: Удерживает lock1...");
                try {
                    Thread.sleep(50);
                } catch (InterruptedException e) {
                }
                System.out.println("Поток 1: Ожидает lock2...");
                synchronized (lock2) {
                    System.out.println("Поток 1: Захватил lock1 и lock2");
                }
            }
        });

        Thread thread2 = new Thread(() -> {
            synchronized (lock2) {
                System.out.println("Поток 2: Удерживает lock2...");
                try {
                    Thread.sleep(50);
                } catch (InterruptedException e) {
                }
                System.out.println("Поток 2: Ожидает lock1...");
                synchronized (lock1) {
                    System.out.println("Поток 2: Захватил lock2 и lock1");
                }
            }
        });

        thread1.start();
        thread2.start();
    }
}


