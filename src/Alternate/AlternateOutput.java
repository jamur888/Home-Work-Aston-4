package Alternate;

public class AlternateOutput {
    public static void main(String[] args) {

        TurnState state = new TurnState(20);

        Thread thread1 = new Thread(new AlternateTask("1 ", true, state));
        Thread thread2 = new Thread(new AlternateTask("2 ", false, state));

        thread1.start();
        thread2.start();

        try {
            thread1.join();
            thread2.join();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        System.out.println("\nГлавный поток успешно дождался завершения работы и закрылся.");
    }
}