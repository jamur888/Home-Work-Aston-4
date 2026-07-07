package LiveLock;

import java.util.concurrent.locks.Lock;

public class LivelockTask implements Runnable {
    private final String name;
    private final Lock firstLock;
    private final Lock secondLock;

    public LivelockTask(String name, Lock firstLock, Lock secondLock) {
        this.name = name;
        this.firstLock = firstLock;
        this.secondLock = secondLock;
    }

    @Override
    public void run() {
        while (true) {
            if (firstLock.tryLock()) {
                System.out.println(name + ": Захватил первый лок. Пробую второй...");
                try {
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                    return;
                }

                if (secondLock.tryLock()) {
                    System.out.println(name + ": Успешно захватил оба лока!");
                    secondLock.unlock();
                    firstLock.unlock();
                    break;
                }
                System.out.println(name + ": Второй лок занят. Отпускаю первый...");
                firstLock.unlock();
            }
            try {
                Thread.sleep(5);
            } catch (InterruptedException e) {
                return;
            }
        }
    }
}

