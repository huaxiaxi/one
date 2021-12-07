package thread.chapter5;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.stream.IntStream;

import static java.lang.Thread.currentThread;
import static java.util.concurrent.ThreadLocalRandom.current;

public class BooleanLockTest {
    private final Lock lock = new BooleanLock();
    public void syncMethod(){
        try{
            lock.lock();
            int randomInt = current().nextInt(10);
            System.out.println(currentThread() + " get the lock.");
            TimeUnit.SECONDS.sleep(randomInt);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }finally {
            lock.unlock();
        }
    }

    public void syncMethodTimeoutable(){
        try {
            lock.lock(1000);
            System.out.println(currentThread() + " get the lock. ");
            int randomInt = current().nextInt(10);
            TimeUnit.SECONDS.sleep(randomInt);
        } catch (InterruptedException | TimeoutException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

    public static void main(String[] args) throws InterruptedException {
        BooleanLockTest blt = new BooleanLockTest();
//        IntStream.range(0, 10)
//                .mapToObj(i -> new Thread(blt::syncMethod))
//                .forEach(Thread::start);

//        new Thread(blt::syncMethod, "T1").start();
//        TimeUnit.MILLISECONDS.sleep(2);
//        Thread t2 = new Thread(blt::syncMethod,"T2");
//        t2.start();
//        TimeUnit.MILLISECONDS.sleep(10);
//        t2.interrupt();
//        System.out.println(t2.isInterrupted());
//        System.out.println(t2.getState());

        new Thread(blt::syncMethodTimeoutable, "T1").start();
        TimeUnit.MILLISECONDS.sleep(2);
        Thread t2 = new Thread(blt::syncMethodTimeoutable,"T2");
        t2.start();
        TimeUnit.MILLISECONDS.sleep(10);

    }
}
