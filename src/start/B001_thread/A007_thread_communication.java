package start.B001_thread;

import java.io.IOException;

public class A007_thread_communication {

//    如果需要多个线程按照指定的规则共同完成一个任务，那么这些线程之间需要互相协调，这个过程被称为线程间通信。
//
//    等待-通知方式
//    线程间通信有多种方式，等待-通知、共享内存、管道流，本部分主要介绍等待-通知方式。
//
//    wait 方法和 notify 方法原理
//    Java 语言中的“等待-通知”方式的线程间通信使用了对象的 wait()和notify() 两类方法。这两个方法和监视器紧密相关，每个 Java 对象都有这两类实例方法。
//
//    对象的 wait 方法
//    对象的 wait 方法的主要作用是让当前线程阻塞并等待被唤醒。使用wait方法一定要放在同步块中，调用方法如下：
//
//    sychronized(locko) {
//        locko.wait();
//    ...
//    }
//    Object 类中的 wait 方法有以下3个版本：
//
//    void wait();
//
//    // 限时等待
//    void wait(long timeout);
//
//    // 更加精确的限时等待
//    void wait(long timeout, int nanos);
//    wait 方法的核心原理大致如下：
//
//    当线程调用了某个同步锁对象的 wait() 方法后，JVM 会将当前线程加入到监视器的 WaitSet（等待集），等待被其他线程唤醒。
//    当前线程会释放对象监视器的 Owner 权利，让其他线程可以抢夺对象监视器。
//    让当前线程等待，其状态变成WAITING。
//    对象的 notify 方法
//    对象的 notify 方法的主要作用是唤醒在等待的线程，。使用notify方法一定要放在同步块中，调用方法如下：
//
//    sychronized(locko) {
//        locko.notify();
//    ...
//    }
//    notify 方法有两个版本：
//
//    // 唤醒监视器等待集里面的第一条等待线程，被唤醒的线程状态由WAITING变为BLOCKED。
//    void notify();
//
//    // h唤醒监视器等待集里的所有等待线程，被唤醒的线程状态由WAITING变为BLOCKED。
//    void notifyAll();
//    notify 方法的核心原理如下：
//
//    当线程调用了某个同步锁对象的 notify 方法或 notifyAll 方法后，JVM会唤醒监视器WaitSet中对应的线程。
//    等待线程被唤醒后，会从监视器的 WaitSet 移动到 EntryList，线程具备了排队抢夺监视器 Owner 权利的资格，其状态从 WAITING 变为 BLOCKED。
//    EntryList 中的线程抢夺到监视器的 Owner 权利之后，线程的状态从 BLOCKED 变成 Runnable，具备重新执行的资格。

    static Object locko = new Object();

    static class WaitTarget implements Runnable {
        public void run() {
            synchronized (locko) {
                try {
                    System.out.println("启动等待");
                    locko.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("接收到通知， 当前线程继续执行");
            }
        }
    }

    static class NotifyTarget implements Runnable {
        public void run() {
            synchronized (locko) {
                try {
                    System.out.println(System.in.read());
                } catch (IOException e) {
                    e.printStackTrace();
                }
                locko.notifyAll();
                System.out.println("发出通知了，但是线程还没有立即释放锁");
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
        Thread waitThread = new Thread(new WaitTarget(), "waitThread");
        waitThread.start();
        Thread.sleep(1000);
        Thread notifyThread = new Thread(new NotifyTarget(), "NotifyThread");
        notifyThread.start();

    }

//    可以看到 WaitThread 处于 WAITING 状态，因为它处于对象监视器的等待集中，等待被唤醒。 NotifyThread 处于 RUNNABLE 状态。
//
//    在控制台输入任意的字符即可使代码继续执行下去。
//
//    通过用例可知，WaitThread 调用 locko.wait 后会一直处于 WAITING 状态，不会再占用CPU时间片，也不会占用同步对象 locko 的监视器，一直到其他线程使用 locko.notify 方法发出通知。
//
//    sychronized 同步块内部使用wait和notify
//    在调用同步对象的 wait 和 notify 方法时，“当前线程”必须拥有该线程的同步锁，也就是必须在同步块中使用。否则JVM会抛出 java.lang.IllegalMonitorException 异常。
//
//    这两个方法的通信要点是：
//
//    调用某个同步对象的 wait 和 notify 方法前，必须要取得这个锁对象的监视锁，所以 wait 和 notify 方法必须放在 sychronized 同步块中。
//    调用 wait 方法时使用 while 进行条件判断，只有这样才能在被唤醒后继续检查 wait 的条件，并在条件没有满足的情况下继续等待。
//    下面是使用 while 的示例：
//
//            while (amount < 0) {
//        sychronized(NOT_EMPTY) {
//            NOT_EMPTY.wait();
//        }
//    }



//    作者：简单一点点
//    链接：https://www.jianshu.com/p/b62e1d8b1891
//    来源：简书
//    著作权归作者所有。商业转载请联系作者获得授权，非商业转载请注明出处。
}
