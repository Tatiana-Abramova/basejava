package webapp;

public class MainDeadlock {

    private final Object lock1 = new Object();
    private final Object lock2 = new Object();

    public static void main(String[] args) {
        MainDeadlock deadlock = new MainDeadlock();
        Thread thread1 = new Thread(deadlock::print1);
        Thread thread2 = new Thread(deadlock::print2);

        thread1.start();
        thread2.start();
    }

    private void print1() {
        synchronized (lock1) {
            System.out.println(System.currentTimeMillis() + " print1 " + Thread.currentThread().getName());
            print2();
        }
    }

    private void print2() {
        synchronized (lock2) {
            System.out.println(System.currentTimeMillis() + " print2 " + Thread.currentThread().getName());
            print1();
        }
    }
}
