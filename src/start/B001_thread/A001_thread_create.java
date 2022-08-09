package start.B001_thread;

import java.util.concurrent.*;

public class A001_thread_create {

    static int threadNo = 1;

    static ExecutorService pool = Executors.newFixedThreadPool(3);

    public static void main(String[] args) throws InterruptedException {
//        Thread基本属性
//        Thread thread = new Thread();
//        System.out.println(thread.getName());
//        System.out.println(thread.getId());
//        System.out.println(thread.getState());
//        System.out.println(thread.getPriority());
//        System.out.println(thread.getThreadGroup());

//---------------------------------******************------------------------------

//        1.直接创建Thread
//        Thread thread = null;
//        for (int i = 0; i < 2; i++) {
//            thread = new MyThread();
//            thread.start();
//        }

//---------------------------------******************------------------------------

//        2.基于Thread类接口Runnable 使用 Runnable 接口创建现成的方式优点是可以更好地分离逻辑和数据，多个线程能够并行处理同一个资源。
//        Thread thread = null;
//        for (int i = 0; i < 2; i++) {
//            Runnable target = new RunTarget();
//            thread = new Thread(target, "MyRunnable-" + threadNo);
//            threadNo++;
//            thread.start();
//        }
//        2.1 匿名内部类实现
//        Thread thread = null;
//        for (int i = 0; i < 2; i++) {
//            thread = new Thread(new Runnable() {
//                @Override
//                public void run() {
//                    for (int j = 0; j < 3; j++) {
//                        System.out.println(Thread.currentThread().getName() + "，轮次： " + j);
//                    }
//                }
//            }, "MyRunnable-" + threadNo);
//            threadNo++;
//            thread.start();
//        }
//        2.2 Lambda实现
//        Thread thread = null;
//        for (int i = 0; i < 2; i++) {
//            thread = new Thread(() -> {
//                for (int j = 0; j < 3; j++) {
//                    System.out.println(Thread.currentThread().getName() + "，轮次： " + j);
//                }
//            }, "MyRunnable-" + threadNo);
//            threadNo++;
//            thread.start();
//        }

//        通过继承 Thread 类或者实现 Runnable 接口创建线程的缺陷是无法获取异步结果
//        如果要使用异步执行，那么就要用 Callable 接口和 FutureTask 类结合创建线程。

//---------------------------------******************------------------------------

//        3.Callable接口、 RunnableFuture<V> extends Runnable, Future<V>
//        Future接口处理异步任务提供cancel(), isCanceled(), isDone();V get(): 方法 获取异步任务执行结果，方法调用是阻塞性的。阻塞到异步任务执行完成。
//                                                               V get(long timeout, Timeunit unit)设置时限，阻塞超时抛出异常。
//        Future 只是个接口，而 FutureTask 类是它的一个默认实现，更准确的说， FutureTask 类实现了 RunnableTask 接口。
//
//        FutureTask 类中有一个 Callable 类型的成员，用来保存任务。FutureTask 的 run() 方法会执行 call() 方法。
//
//        创建步骤如下：
//        创建一个 Callable 接口的实现类，并实现其 call() 方法，在其中编写异步执行逻辑，可以有返回值。
//        使用 Callable 实现类的实例构造一个 FutureTask 实例。
//        使用 FutureTask 实例作为 Thread 构造器 target 的入参，构造 Thread 线程实例。
//        这样线程就构造好了，线程启动后，我们还可以调用 FutureTask 对象的 get() 方法阻塞性地获得并发线程的执行结果。

//        MyTask task = new MyTask();
//        FutureTask<Long> futureTask = new FutureTask<>(task);
//        Thread thread = new Thread(futureTask, "MyThread");
//        thread.start();
//        Thread.sleep(400);
//        System.out.println(Thread.currentThread().getName() + " 先歇息下。");
//        for (int i = 0; i < 100000000; i++) {
//            int j = i * 10000;
//        }
//        System.out.println(Thread.currentThread().getName() + " 获取并发任务的执行结果。");
//        try {
//            System.out.println(thread.getName() + " 线程占用时间：" + futureTask.get());
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        } catch (ExecutionException e) {
//            e.printStackTrace();
//        }
//        System.out.println(Thread.currentThread().getName() + " 运行结束。");

//        通过 FutureTask 的 get 方法获取结果，有 2 种情况：
//        异步任务执行完成，直接返回结果。
//        异步任务没有执行完成，一直阻塞到执行完成返回结果。
//        4. 通过线程池创建线程 上述线程用完即销毁，避免频繁创建和销毁，对线程复用。
//        Executors:  private static ExecutorService pool = Executors.newFixedThreadPool(3);

//---------------------------------******************------------------------------
        pool.execute(new MyThread1());
        pool.execute(() -> {
            for (int j = 0; j < 5; j++) {
                System.out.println(Thread.currentThread().getName() + " ，轮次： " + j);
            }
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        Future future = pool.submit(new MyTask());
        try {
            Long result = (Long)future.get();
            System.out.println(" 异步执行的执行结果：" + result);
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

//        可以看到线程池中的线程默认名称和普通线程也有所不同。
//
//        注意其中 ExecutorService 线程池的 execute 和 submit 方法有如下区别：
//
//        1.接收参数不一样。 submit 方法可以接收无返回值的 Runnable 类型和有返回值的 Callable 类型，execute仅接收无返回值的 Runnable 类型或者 Thread 实例。
//
//        2.submit 方法有返回值，而 execute 方法没有。

//        作者：简单一点点
//        链接：https://www.jianshu.com/p/6984f8a288c4
//        来源：简书
//        著作权归作者所有。商业转载请联系作者获得授权，非商业转载请注明出处。
    }

    private static class MyThread extends Thread {
        public MyThread() {
            super("MyThread-" + threadNo);
            threadNo++;
        }
        public void run() {
            for (int i = 0; i < 3; i++) {
                System.out.println(getName() + "，轮次： " + i);
            }
            System.out.println(getName() + "运行结束。");
        }

    }

    private static class RunTarget implements Runnable {

        @Override
        public void run() {
            for (int i = 0; i < 3; i++) {
                System.out.println(Thread.currentThread().getName() + "，轮次： " + i);
            }
        }
    }

    static class MyTask implements Callable<Long> {
        public Long call() throws Exception {
            long startTime = System.currentTimeMillis();
            System.out.println(Thread.currentThread().getName() + "线程开始运行。");
            Thread.sleep(500);

            for (int i = 0; i < 100000000; i++) {
                int j = i * 10000;
            }

            long used = System.currentTimeMillis() - startTime;
            System.out.println(Thread.currentThread().getName() + "线程结束运行。");
            return used;
        }

    }

    private static class MyThread1 implements Runnable {
        @Override
        public void run() {
            for (int i = 0; i < 5; i++) {
                System.out.println(Thread.currentThread().getName() + " ，轮次： " + i);
                try {
                    Thread.sleep(5);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}

