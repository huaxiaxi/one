package start.B001_thread;

public class A002_thread_basic_operation {
    public static void main(String[] args) throws InterruptedException {
//        1.sleep : 的作用是让当前正在执行的线程休眠，让 CPU 去执行其它的任务。线程状态会进入阻塞状态。sleep 方法是静态方法
//        for (int i = 0; i < 5; i++) {
//            Thread thread = new SleepThread();
//            thread.start();
//        }
//        System.out.println(Thread.currentThread().getName() + "运行结束。");

//        2. interrupt: Java 提供了 stop 方法终止正在运行的线程，但是这个方法不被建议使用，因为中断一个线程是很危险的行为。
//        推荐的方式是使用 interrupt 方法，此方法的本质不是中断一个线程，而是将线程设置为中断状态。它的作用：
//        如果此线程处于阻塞状态，那么会立刻退出阻塞（一般是 Object.wait()、Thread.join()、Thread.sleep()三种方法之一），并抛出 InterruptException 异常。
//        如果此线程正处于运行状态，线程就不受任何影响，继续运行，仅仅是将线程的中断标记设置为 true。程序可以通过 isInterrupted() 方法查看自己是否应该中断并执行退出操作。
//        Thread thread1 = new SleepThread();
//        thread1.start();
//        Thread thread2 = new SleepThread();
//        thread2.start();
//
//        Thread.sleep(2000);
//        thread1.interrupt();
//
//        Thread.sleep(5000);
//        thread2.interrupt();
//
//        Thread.sleep(1000);
//        System.out.println("程序运行结束");
//        3.join 合并：主线程等待子线程执行完成之后在运行主线程.
//        使用 join() 方法需要注意的地方：
//        1.join() 方法需要使用被合并线程的句柄，当前线程会进入 TIME_WAITING 等待状态，让出 CPU。
//        2.如果设置了执行时间，并不能保证当前线程会在这个时间之后变为 RUNNABLE 。
//        3.如果主动方合并线程在等待时被中断，会抛出 InterruptedException 异常。
        Thread thread1 = new SleepThread();
        System.out.println("启动 Thread1.");
        thread1.start();
        thread1.join();
        Thread thread2 = new SleepThread();
        System.out.println("启动 Threads2.");
        thread2.start();
        thread2.join(1000);
        System.out.println("线程运行结束。");

//        注意没有时限和有时限的 thread.join() 方法的一个不同之处，
//        没有时限的主线程会在子线程执行期间处于 WAITING 状态，而后者是处于 TIMED_WAITING 状态，它们都不会被分配 CPU 时间片。

//        4.yield: 让步：
//        线程的 yield （让步）操作的作用是让目前正在执行的线程放弃当前的执行，让出 CPU 的执行权限，使得 CPU 去执行其它的线程，处于让步状态的线程状态在 JVM 层面仍然是 RUNNABLE，从 操作系统层面上来说会从执行状态变成就绪状态。
//        线程在 yield 时，线程放弃和重占 CPU 时间是不确定的，可能是刚刚放弃 CPU，马上又获取 CPU 执行权限，重新开始执行。
//        yield() 方法是静态方法，它可以让当前正在执行的线程暂停，但不会阻塞该线程，只是让线程转入就绪状态。
//        5.daemon: 守护
//        Java 种的线程分为两类，守护线程和用户线程，守护线程也称后台线程，专门指程序进程运行过程中，在后台提供某种通用服务的线程。比如Java种的GC线程。
//
//        Thread 类中，实例属性 daemon属性表示当前线程是否为守护线程。通过方法 setDaemon 可以设置daemon属性，通过方法 isDaemon 可以获取daemon属性。
//
//        用户线程和守护线程与 JVM 虚拟机进行终止的依赖不同。用户线程是主动关系，如果用户线程全部终止，JVM虚拟机进程也随之终止；守护进程是被动关系，如果JVM进程终止，所有守护线程也随之终止。
//
//        使用守护线程时，有以下几点要注意：
//
//        守护线程必须在启动前将其守护状态设置为true，启动之后在设置 JVM 会抛出 InterruptedException。
//        守护线程有被 JVM 终止的风险，所以在守护线程中尽量不去访问系统资源，如文件句柄、数据库连接等。
//        守护线程创建的线程也是守护线程。

//        作者：简单一点点
//        链接：https://www.jianshu.com/p/9f8f22b51e5f
//        来源：简书
//        著作权归作者所有。商业转载请联系作者获得授权，非商业转载请注明出处。






    }

    private static class SleepThread extends Thread {
        static int threadSeqNo = 1;

        public SleepThread() {
            super("sleepThread-" + threadSeqNo);
            threadSeqNo++;
        }

        public void run() {
//            for (int i = 0; i < 50; i++) {
                try {
//                    System.out.println(Thread.currentThread().getName() + "，睡眠轮次： " + i);
                    System.out.println(Thread.currentThread().getName() + "进入睡眠轮次 " );
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    System.out.println(Thread.currentThread().getName()  + " --INTERRUPT STATUS: " + Thread.currentThread().isInterrupted() + " <---中断标志。");
                    e.printStackTrace();
                    System.out.println(Thread.currentThread().getName() + " 发生异常中断。");
                    return;
                }
                System.out.println(Thread.currentThread().getName()  + " --INTERRUPT STATUS: " + Thread.currentThread().isInterrupted() + " <---中断标志。");
                System.out.println(Thread.currentThread().getName() + "运行结束。");
//            }
        }
    }
}
