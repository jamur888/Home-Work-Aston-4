package DeadLock;

public class DeadlockTask implements Runnable {
    private final String name;
    private final Object firstLock;
    private final Object secondLock;

    public DeadlockTask(String name, Object firstLock, Object secondLock) {
        this.name = name;
        this.firstLock = firstLock;
        this.secondLock = secondLock;
    }

    @Override
    public void run() {
        synchronized (firstLock) {
            System.out.println(name + ": Удерживает первый лок...");
            try { Thread.sleep(50); } catch (InterruptedException e) { return; }
            System.out.println(name + ": Ожидает второй лок...");

            synchronized (secondLock) {
                System.out.println(name + ": Успешно захватил оба лока!");
            }
        }
    }
}