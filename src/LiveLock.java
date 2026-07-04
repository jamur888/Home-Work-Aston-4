import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class LiveLock {

    private static final Lock lock1 = new ReentrantLock();
    private static final Lock lock2 = new ReentrantLock();

    public static void main(String[] args) {
        Thread thread1 = new Thread(() -> {
            while (true) {
                if (!lock1.tryLock()) {
                    continue;
                }
                System.out.println("Поток 1: Захватил lock1, пытается взять lock2...");

                if (!lock2.tryLock()) {
                    System.out.println("Поток 1: lock2 занят. Освобождаю lock1 для уступки.");
                    lock1.unlock(); // Уступаем ресурс
                    try {
                        Thread.sleep(10);
                    } catch (InterruptedException e) {
                    }
                    continue;
                }

                System.out.println("Поток 1: Выполнил работу!");
                lock2.unlock();
                lock1.unlock();
                break;
            }
        });

        Thread thread2 = new Thread(() -> {
            while (true) {

                if (!lock2.tryLock()) {
                    continue;
                }
                System.out.println("Поток 2: Захватил lock2, пытается взять lock1...");

                if (!lock1.tryLock()) {
                    System.out.println("Поток 2: lock1 занят. Освобождаю lock2 для уступки.");
                    lock2.unlock(); // Уступаем ресурс
                    try {
                        Thread.sleep(10);
                    } catch (InterruptedException e) {
                    }
                    continue;
                }

                System.out.println("Поток 2: Выполнил работу!");
                lock1.unlock();
                lock2.unlock();
                break;
            }
        });

        thread1.start();
        thread2.start();
    }
}


