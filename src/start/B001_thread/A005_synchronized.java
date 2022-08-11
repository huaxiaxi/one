package start.B001_thread;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

public class A005_synchronized {

//    在 Java 中，线程同步使用最多的方法是使用 synchronized 关键字，每个 Java 对象都隐含有一把锁，称为 Java 内置锁。使用 synchronized 调用相当于获取 syncObject 的内置锁，可以对临界区代码段进行排他性保护。
//
//    synchronized 同步方法
//    当使用 synchronized 关键字修饰一个方法时，该方法被声明为同步方法。例如：
//
//    public synchronized void seldPlus() {
//        amount++;
//    }
//    这样只允许一个线程访问方法，其它线程需要执行同一个方法的化，只能等待和排队。
//
//    synchronized 同步块
//    有时候直接在方法中使用 synchronized 关键字会占用多个临界区资源，影响执行效率，这时候可以考虑使用同步块。
//
//    考虑下面的方法。
//
//    public class TwoPlus {
//        private int sum1 = 0;
//        private int sum2 = 0;
//
//        public synchronized void plus(int val1, int val2) {
//            this.sum1 += val1;
//            this.sum2 += val2;
//        }
//    }
//    其中的同步方法包含对两个临界资源的操作，需要全部占用才能进入，但他们两个其实是互相独立的，这里可以改造成同步块：
//
//    public class TwoPlus {
//        private int sum1 = 0;
//        private int sum2 = 0;
//        private Integer sum1Lock = new Integer(1); // 同步锁一
//        private Integer sum2Lock = new Integer(2); // 同步锁二
//
//        public void plus(int val1, int val2) {
//            // 同步块1
//            synchronized(this.sum1Lock) {
//                this.sum1 += val1;
//            }
//            // 同步块2
//            synchronized(this.sum2Lock) {
//                this.sum2 += val2;
//            }
//
//        }
//    }
//    实际上，同步方法就是把整个方法作为一个同步代码块，并使用 this 对象锁。
//
//    静态的同步方法
//    普通的 synchronized 实例方法，同步锁为当前对象的 this 的监视锁，如果是静态方法呢？
//
//    静态方法属于 Class 实例而不是单个 Object 实例，因此静态方法的同步锁对应的是 Class 对象的监视锁。为了区分，一般将 Object 对象的监视锁叫做对象锁，将 Class 对象的监视锁称为类锁。
//
//    由于每个类只有一个 Class 实例，因此使用类锁作为 synchronized 的同步锁时会造成同一个JVM 内的所有线程只能互斥地进入临界区段。
//
//    synchronized 使用了Java 中的内置锁，相关原理在下一章再详细介绍，这里先看下一个同步的应用，生产者——消费者问题。

//    生产者——消费者问题
//    生产者——消费者问题（Producer-Consumer Problem）也称有限缓冲问题（Boundeded-Buffer Problem），是一个多线程同步问题的经典案例。
//
//    生产者——消费者问题描述了两类访问共享缓冲区的线程在实际运行时会发生的问题，生产者线程的主要功能是生成一定量的数据放到缓冲区中，然后重复此过程。消费者线程的主要功能是从缓冲区提取（或消耗）数据。
//
//    生产者-消费者问题的关键是：
//
//    保证生产者不会在缓冲区满时加入数据，消费者也不会在缓冲区空时消耗数据。
//    保证在生产者加入过程，消费者消耗过程中，不会产生错误的数据和行为。
//    解决该问题的方案被称为“生产者——消费者”模式。
//    先看一个线程不安全版本的生产者——消费者问题的实现版本。
//    首先定义数据缓冲区类：
    public static class NotSafeDataBuffer<T> {
        public static final int MAX_AMOUNT = 10;
        private List<T> dataList = new LinkedList();

        private AtomicInteger amount = new AtomicInteger(0);

//        public void add(T element) throws Exception {
        public synchronized void add(T element) throws Exception {
            if (amount.get() > MAX_AMOUNT) {
                System.out.println("队列已经满了！");
                return;
            }
            dataList.add(element);
            System.out.println(element + "");
            amount.incrementAndGet();

            if (amount.get() != dataList.size()) {
                throw new Exception(amount + "!="  + dataList.size());
            }
        }

//        public T fetch() throws Exception {
        public synchronized T fetch() throws Exception {
            if (amount.get() <= 0) {
                System.out.println("队列已经空了！");
                return null;
            }

            T element = dataList.remove(0);
            System.out.println(element + "");
            amount.decrementAndGet();
            if (amount.get() != dataList.size()) {
                throw new Exception(amount + "!=" + dataList.size());
            }
            return element;
        }
    }
//    定义生产者
    public static class Producer implements Runnable {

        public static final int PRODUCE_GAP = 200;

        final AtomicInteger TURN = new AtomicInteger(0);
        final AtomicInteger PRODUCE_NO = new AtomicInteger(1);

        String name = null;
        Callable action = null;

        int gap = PRODUCE_GAP;

        public Producer(Callable action, int gap) {
            this.action = action;
            this.gap = gap;
            name = "生产者-" + PRODUCE_NO.incrementAndGet();
        }

        @Override
        public void run() {
            while (true) {
                try {
                    Object out = action.call();
                    if (out != null) {
                        System.out.println("第" + TURN.get() + "轮生产：" + out);
                    }
                    try {
                        Thread.sleep(gap);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    TURN.incrementAndGet();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }
    public static class Consumer implements Runnable {
        public static final int PRODUCE_GAP = 200;

        final AtomicInteger TURN = new AtomicInteger(0);
        final AtomicInteger PRODUCE_NO = new AtomicInteger(1);

        String name = null;
        Callable action = null;

        int gap = PRODUCE_GAP;

        public Consumer(Callable action, int gap) {
            this.action = action;
            this.gap = gap;
            name = "消费者-" + PRODUCE_NO.incrementAndGet();
        }

        @Override
        public void run() {
            while (true) {
                try {
                    Object out = action.call();
                    if (out != null) {
                        System.out.println("第" + TURN.get() + "轮消费：" + out);
                    }
                    try {
                        Thread.sleep(gap);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    TURN.incrementAndGet();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

//    private static NotSafeDataBuffer<String> notSafeDataBuffer = new NotSafeDataBuffer<>();
    private static NotSafeDataBuffer<String> safeDataBuffer = new NotSafeDataBuffer<>();

    static Callable<String> produceAction = () -> {
        String goods = getRandomString(6);
        try {
            safeDataBuffer.add(goods);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return goods;

    };
    static Callable<String> consumerAction = () -> {
        String goods = null;
        try {
            goods = safeDataBuffer.fetch();
        }catch (Exception e) {
            e.printStackTrace();
        }
        return goods;
    };

    public static void main(String[] args) {
        System.setErr(System.out);
        final int THREAD_TOTAL = 20;
        ExecutorService pool = Executors.newFixedThreadPool(THREAD_TOTAL);
        for (int i = 0; i < 5; i++) {
            pool.submit(new Producer(produceAction, 500));
            pool.submit(new Consumer(consumerAction, 1500));
        }
    }


    private static String getRandomString(int length) {
        String str = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        Random random = new Random();
        StringBuffer sb = new StringBuffer();

        for (int j = 0; j < length; j++) {
            int number = random.nextInt(62);
            sb.append(str.charAt(number));
        }
        return sb.toString();

    }


//    作者：简单一点点
//    链接：https://www.jianshu.com/p/3cac62f8c3dc
//    来源：简书
//    著作权归作者所有。商业转载请联系作者获得授权，非商业转载请注明出处。
}
