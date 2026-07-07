package DeadLock;

public class DeadLock {
    public static void main(String[] args) {
        Object lock1 = new Object();
        Object lock2 = new Object();

        Thread thread1 = new Thread(new DeadlockTask("Поток 1", lock1, lock2));
        Thread thread2 = new Thread(new DeadlockTask("Поток 2", lock2, lock1));

        thread1.start();
        thread2.start();

        try {
            thread1.join();
            thread2.join();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        System.out.println("Главный поток завершен (это сообщение не выведется из-за DeadLock).");
    }
}