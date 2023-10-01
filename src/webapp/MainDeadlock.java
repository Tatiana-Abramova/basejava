package webapp;

public class MainDeadlock {

    private static final Object LOCK_1 = new Object();
    private static final Object LOCK_2 = new Object();

    public static void main(String[] args) {
        lock(LOCK_1, LOCK_2).start();
        lock(LOCK_2, LOCK_1).start();
    }

    private static Thread lock(Object lock1, Object lock2) {
        return new Thread(() -> {
            synchronized (lock1) {
                System.out.println(System.currentTimeMillis() + " print1 " + Thread.currentThread().getName());
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                synchronized (lock2) {
                    System.out.println(System.currentTimeMillis() + " print2 " + Thread.currentThread().getName());
                }
            }
        });
    }
}
