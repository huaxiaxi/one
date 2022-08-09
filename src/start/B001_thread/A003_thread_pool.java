package start.B001_thread;

import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

public class A003_thread_pool {

//    Java线程的创建非常昂贵，需要 JVM 和 OS 配合完成大量工作：
//
//    必须为线程堆栈分配和初始化大量的内存块，其中包含至少 1 MB 的栈内存。
//    需要进行系统调用，以便在 OS 中创建和注册本地线程。

//    Java 高并发应用频繁创建和销毁线程的操作非常低效，编程规范也不建议这么做，因此需要使用线程池，使用线程池有如下优点：
//    提高性能。线程池能独立负责线程的创建、维护和分配。在执行大量异步任务时，可以不需要自己创建线程，而是将任务交给线程池去调度。线程池能够尽可能使用空闲的线程去执行异步任务，最大限度地对已经创建的线程进行复用，使得性能提升明显。
//    线程管理。每个 Java 线程池会保持一些基本的线程统计信息，例如完成的任务数量、空闲时间等，以便对线程进行有效管理，使得能对所接收到的异步任务进行高效调度。

//    Executor
//    Executor 是 Java 异步任务目标的“执行者”接口，其目标是执行目标任务。其中提供了 execute() 接口来执行已提交的 Runnable 执行目标实例。
//    Executor作为执行者的角色，其目的是提供一种将“任务提交者”与“任务执行者”分离开来的机制。它只有一个函数式方法：
//    void execute(Runnable command)
//
//    ExecutorService
//    ExecutorService 继承于 Executor，它是 Java 异步目标任务的“执行者”服务接口，对外提供异步任务的接收服务。
//    ExecutorService 提供了“接收异步任务并转交给执行者”的方法。
//    AbstarctExecutorService
//    AbstarctExecutorService 是一个抽象类，它实现了 ExecutorService 接口，为 ExecutorService 接口中的方法提供默认实现。
//
//    ThreadPoolExecutor
//    JUC 中线程池的核心实现类，继承于 AbstarctExecutorService 抽象类。
//
//    ScheduledExecutorService
//    ScheduledExecutorService 是一个接口，它继承于 ExecutorService，它是一个可以完成“延时”和“周期性”任务的调度线程池接口。
//
//    ScheduledThreadPoolExecutor
//    ScheduledThreadPoolExecutor 继承自 ThreadPoolExecutor，它提供了 ThreadPoolExecutor 接口中“延时执行”和“周期执行”等调度方法的具体实现。
//    ScheduledThreadPoolExecutor 类似于 Timer，但是在高并发程序中，ScheduledThreadPoolExecutor 的性能要优于 Timer。
//
//    Executors
//    Executors 是一个静态工厂类，它通过静态工厂方法返回 ExecutorService 等线程池实例对象。
//    Java 通过 Executors 工厂类提供了4种快捷创建线程池的方法

    public static void main(String[] args){
//    1. newSingleThreadExecutor 创建单线程化线程池
//    newSingleThreadExecutor 可以创建只有一个线程执行的线程池，其他线程会排队等待。

//        ExecutorService pool = Executors.newSingleThreadExecutor();

//        本线程池有以下特点：
//        单线程化的线程池中的任务是按照提交的次序顺序执行的。
//        池中的唯一线程的存活时间无限的。
//        当池中的唯一线程正在繁忙时，新提交的任务实例会进入内部的阻塞队列中，并且其阻塞队列是无界的。
//        单线程化的线程池适用的场景是：任务按照提交次序，依次执行的场景。
//        上例中最后调用了 shutdowm() 方法来关闭线程池，此方法执行后会将线程池状态改为 SHUTDOWN，此时线程池将拒绝新任务，不能再往线程池中添加任务，否则会抛出 RejectedExecutationException 异常。此时，线程池不会立刻退出，而是等添加到线程池的线程任务全部处理完才会退出。
//        还有一个 shutdownNow() 方法，执行后线程池状态会立刻变为 STOP，并试图停止所有正在执行的线程，并且不再处理阻塞队列中等待的任务，会返回那些未执行的任务。

//        2.newFixedThreadPool 创建固定数量的线程池
//        该方法用于创建一个固定数量的线程池，参数用来设置线程池中线程的数量。
//        ExecutorService pool = Executors.newFixedThreadPool(2);

/*        可以看到其中是两个任务同时执行。
        固定数量线程池的特点如下：
        如果线程数没有达到指定的数量，每次提交一个任务线程池内就创建一个新线程，直到线程达到线程池固定的数量。
        线程池的大小一旦达到数量就会保持不变，只有某个线程因为执行异常而结束，线程池才会补充线程。
        在接收异步任务执行目标实例时，如果池中的所有线程均在繁忙状态，新任务会进入阻塞队列中。
        本线程池适用场景：需要任务长期执行的场景。优点是线程数能够比较稳定地保持一个数，能够避免频繁回收线程和创建线程，适用于处理 CPU 密集型的任务。
        缺点是内部使用无界队列来存放排队任务，当大量任务堆积时会使线程无限增大，耗尽服务器资源。
 */
//        3. newCachedThreadPool 创建可缓存线程池
//        该方法用于创建一个可缓存线程池，可以灵活回收其中的空线程。
//        ExecutorService pool = Executors.newCachedThreadPool();
//        可缓存线程池特点如下：
//        在接收新的异步任务 target 执行目标实例时，如果池内所有线程繁忙，此线程池就会添加新线程来处理任务。
//        此线程池不会对线程池大小进行限制，线程池大小完全依赖于操作系统能够创建的最大线程大小。
//        如果部分线程空闲，也就是存量线程的数量超过了处理任务数量，就会回收空闲（60s不执行任务）线程。
//        可缓存线程池的适用场景：需要快速处理突发性强，耗时较短的任务场景，如 Netty 的 NIO 处理场景，REST API 接口的瞬时削峰场景。可缓存线程池的线程数量不固定，只要有空闲线程就会被回收；接收到的新异步执行目标，查看是否有线程处于空闲状态，如果没有就直接创建新的线程。
//        可缓存线程池的弊端：线程池没有最大线程数量限制，如果大量的异步任务执行目标实例同时提交，可能会因为创建线程过多而导致资源耗尽。

//        for (int i = 0; i < 3; i++) {
//            pool.execute(new TargetTask());
//            pool.submit(new TargetTask());
//        }
//        try {
//            Thread.sleep(1000);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//        pool.shutdown();

//        4.newScheduledThreadPool 创建可调度线程池
//        该方法用于创建一个“可调度线程池”，即一个提供“延时”和“周期性”任务调度功能的 ScheduledExecutorService 类型的线程池，Executors 提供了多个创建可调度线程池的工厂方法。
//        // 方法一：创建一个可调度的线程池，池内仅含有一个线程
//        public static ScheduledExecutorService newSingleThreadScheduledExecutor();
//
//        // 方法二：创建一个可调度的线程池，池内含有N个线程，N的值为输入参数
//        public static ScheduledExecutorService
//        newScheduledThreadScheduledThreadPool(int corePoolSize);
//        newSingleThreadScheduledExecutor 工厂方法所创建的仅含有一个线程的可调度线程池适用于调度串行化任务，也就是一个任务一个任务地串行化调度执行。

//        ScheduledExecutorService scheduled = Executors.newScheduledThreadPool(2);
//        for (int i = 0; i < 2; i++) {
//            scheduled.scheduleAtFixedRate(new TargetTask(), 0, 500, TimeUnit.MILLISECONDS);
//        }
//        try {
//            Thread.sleep(1000);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//        scheduled.shutdown();

//        newScheduledThreadPool 工厂方法可以创建一个执行“延时”和“周期性”任务的可调度线程池。创建的线程为 ScheduledExecutorService 实例，其中有一系列方法，包括 scheduleAtFixedRate 和 scheduleWithFixedDelay。
//        scheduleAtFixedRate 的声明如下：
//        public ScheduledFuture<?> scheduleAtFixedRate(
//                Runnable command,   // 异步任务 target 的下执行目标
//        long initialDelay,  // 首次执行延时
//        long period,        // 两次开始执行最小间隔时间
//        TimeUnit unit       // 所设置时间的计时单位
//    );
//        scheduleWithFixedDelay 的声明如下：
//        public ScheduledFuture<?> scheduleWithFixedDelay(
//                Runnable command,   // 异步任务 target 的下执行目标
//        long initialDelay,  // 首次执行延时
//        long delay,         // 前一次执行结束到下一次执行开始的间隔时间
//        TimeUnit unit       // 所设置时间的计时单位
//    );
//        当被调任务的执行时间大于指定的间隔时间时， ScheduledExecutorService 并不会创建一个新的线程去并发执行这个任务，而是等待前一次调度执行完毕。
//        可调度线程池的适用场景：周期性地执行任务的场景。

//        5.线程池的标准创建方法
/*        大部分企业的开发规范都会禁止使用快捷线程池，而是使用 ThreadPoolExecutor 的构造方法来创建。ThreadPoolExecutor 常用的构造器如下所示：

        public ThreadPoolExecutor(
        int corePoolSize,    // 核心线程数，即使线程空闲，也不会回收
        int maximumPoolSize, // 线程数上限
        long keepAliveTime,  // 线程最大空闲时长
        TimeUnit unit,       // 计时单位
        BlockingQueue<Runnable> workQueue, // 任务的排队队列
        RejectedExecutionHandler handler   // 拒绝策略
            )
        接下来对这些参数具体介绍。

        核心和最大线程数量
        corePoolSize 用于设置核心线程池数量，maximumPoolSize 用于设置最大线程数量。工作线程维护规则为：

        当线程池接收到新任务，并且当前工作线程数少于 corePoolSize 时，即使其它线程处于空闲状态，也会创建一个新线程来处理该请求，直到线程数达到 corePoolSize。
        如果当前工作线程数量多于 corePoolSize，但小于 maximumPoolSize，那么仅当前排队队列已满时才会创建新线程。通过设置 corePoolSize 和 maximumPoolSize 相同，可以创建一个固定大小的线程池。
        当 maximumPoolSize 被设置为无界值（如Integer.MAX_VALUE）时，线程池可以接收任意数量的任务。
        corePoolSize 和 maximumPoolSize 可以通过 setCorePoolSize() 和 setMaximumPoolSize() 两个方法进行动态更改。
        BlockingQueue
        BlockingQueue（阻塞队列）的实例用于暂时接收到的异步任务，如果线程池的核心线程都在忙，那么所接收到的目标任务缓存在阻塞队列中。

        KeepAliveTime
        keepAliveTime 参数用于设置池内线程最大 Idle（空闲）时长，如果超过这个时间，非核心线程会被回收。

        通过 setKeepAliveTime() 方法可以动态调整线程存活时间，通过调用 allowCoreThreadTimeOut(boolean) 方法并设置参数为 true，则keepAliveTime 参数也会适用于核心线程。

        向线程池提交任务的两种方式
        可以通过 execute() 和 submit() 两个方法提交任务，它们之间：

        二者所接受的参数不一样，execute() 只能接收 Runnable 参数， submit() 可以接收 Callable 或 Runnable 参数。
        submit() 提交任务后有返回值，而 execute()没有。
        submit() 方法更方便异常处理。
        线程池的任务调度流程
        线程池的任务调度规则如下：

        工作线程数量小于核心线程数量，执行新任务时会优先创建线程，而不是获取空闲线程。
        任务数量大于核心线程数量，新任务将被加入阻塞队列中。执行任务时，也是先从阻塞队列中获取任务。
        完成一个任务或会从阻塞队列获取下一个任务，直到队列为空。
        在核心线程用完，阻塞队列已满的情况下，会创建非核心线程处理新任务。
        在如果线程池总数超过 maximumPoolSize，线程池会拒绝接收任务，为新任务执行拒绝策略。
*/
//        6.线程工厂 ThreadFactory ThreadFactory 是线程工厂，Executors是线程池工厂。
//        使用 ThreadFactory 需要实现一个 ThreadFactory 类

//        ExecutorService pool = Executors.newFixedThreadPool(2, new SimpleThreadFactory());
//        for (int i = 0; i < 3; i++) {
//            pool.submit(new TargetTask());
//        }
//        try {
//            Thread.sleep(1010);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//        System.out.println("关闭线程池");
//        pool.shutdown();

/*        7.任务阻塞队列
        Java 中的阻塞队列（BlockingQueue）与普通队列相比有一个特点，在阻塞队列为空时会阻塞当前线程的元素获取操作。常见的阻塞队列实现有：

        ArrayBlockingQueue 是一个数组实现的有界阻塞队列，元素按 FIFO 排序，创建时需要设置大小。
        LinkedBlockingQueue 是一个基于链表实现的阻塞队列，按 FIFO 排序，可以设置容量，不设置则默认为 Integer.MAX_VALUE。吞吐量高于 ArrayBlockingQueue。
        PriorityBlockingQueue 是具有优先级的无界队列。
        DelayQueue 是一个无界阻塞延迟队列底层基于 PriorityBlockingQueue 实现，队列中每个元素都有过期时间，当取出元素时，只有已经过期的元素才会出队，队列头部的元素是过期最快的元素。
        SynchronousQueue 是一个不存储元素的阻塞队列，每个插入操作必须等到另一个线程的调用移除操作，否则插入操作一直处于阻塞状态，存储量通常高于 LinkedBlockingQueue。
*/
/*
        8. 调度器的钩子方法
        ThreadPoolExecutor 线程池调度器提供了 3 个钩子方法：
        // 任务执行之前的钩子方法
                protected void beforeExecute(Thread t, Runnable r) {}
        // 任务执行之后的钩子方法
                protected void adterExecute(Runnable t, Throwable t) {}
        // 线程池终止时的钩子方法
                protected void terminated() {}
 */
        ExecutorService pool = new ThreadPoolExecutor(2, 4, 60, TimeUnit.SECONDS,
                new LinkedBlockingDeque<>(2)) {
            @Override
            protected void terminated() {
                System.out.println("调度器终止");
            }

            @Override
            protected void beforeExecute(Thread t, Runnable r) {
                System.out.println(Thread.currentThread().getName() + " 前钩开始执行");
                super.beforeExecute(t, r);
            }

            @Override
            protected void afterExecute(Runnable r, Throwable t) {
                super.afterExecute(r, t);
                System.out.println(Thread.currentThread().getName() + "后钩开始执行");
            }
        };
        for (int i = 0; i < 3; i++) {
            pool.execute(new TargetTask());
        }
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("关闭线程池");
        pool.shutdown();
/*
        9.线程池的拒绝策略
        任务在另种情况下会被线程池拒绝：

        线程池已经关闭。
        线程池工作线程已达最大数量且阻塞队列已满。
        拒绝策略包含如下实现：

        AbortPolicy，如果线程池已满，新任务会被拒绝并抛出RejectedExecutionException 异常，该策略是默认拒绝策略。
        DiscardPolicy，如果线程池已满，新任务就会被拒绝，且不会抛出任何异常。
        DiscardOldestPolicy，如果队列满了，就将最早进入队列的任务抛弃，从队列中腾出空间，在尝试加入队列。
        CallerRunsPolicy，调用者执行策略，新任务如果添加到线程池失败，那么提交任务线程会自己执行该任务。
        另外，还可以自定义策略，实现 RejectExecutionHandler 接口的 rejectedExecution 方法即可。

        例如：

        public static class CustomIgnorePolicy implements RejectedExecutionHandler {
            public void rejectedExecution(Runnable r, ThreadPoolExecutor e) {
                // 加一些自定义逻辑
                ...
            }
        }
        线程池的优雅关闭
        一般情况下，线程池启动后建议手动关闭。

        线程池的状态
        首先介绍一下线程池的状态。

        public class ThreadPoolExecutor extends AbstarctExectuorService {
            private static final int RUNNING    = -1 << COUNT_BITS;
            private static final int SHUTDOWN   =  0 << COUNT_BITS;
            private static final int STOP       =  1 << COUNT_BITS;
            private static final int TIDYING    =  2 << COUNT_BITS;
            private static final int TERMINATED =  3 << COUNT_BITS;

            ...
        }
        线程池的 5 种状态：

        RUNNING，线程池创建之后的初始状态，可以执行任务。
        SHUTDOWN，该状态下线程池不再接受新任务，但是会将工作队列中的任务执行完毕。
        STOP，该状态下线程不再接受新任务，也不会处理任务队列中的剩余任务，并且会中断所有工作线程。
        TIDYING，该状态下所有任务都已终止或者处理完成，将会执行 terminate() 钩子方法。
        TERMINATED，执行完 terminated() 钩子方法之后的状态。
        线程池的状态转换规则为：

        线程池创建之后状态为 RUNNING`。
        执行线程池的 shutdown() 实例方法，会使线程池状态从 RUNNING 转换为 SHUTDOWN。
        执行线程池的 shutdownNow() 实例方法，会使线程池的状态从 RUNNING 转换为 STOP。
        当线程处于 SHUTDOWN 状态时，执行shutdownNow() 方法会使其状态转换为 STOP。
        等待线程池的所有工作线程停止，工作队列清空之后，线程池状态会从 STOP 转换为 TIDYING。
        执行完 terminated() 钩子方法之后，线程池状态从 TIDYING 转换为 TERMINATED。

 */
/*        10.关闭线程池涉及的方法
            shutdown 方法
            shutdown 方法用于有序关闭线程池，此方法等待当前工作的剩余任务全部执行完毕后才会执行关闭，此时线程不会接收新的任务。

            其源码如下：

            public void shutdown() {
                final ReentrantLock mainLock = this.mainLock;
                mainLock.lock();
                try {
                    // 检查权限
                    checkShutdownAccess();
                    // 设置线程池状态
                    advanceRunState(SHUTDOWN);
                    // 中断空闲线程
                    interruptIdleWorkers();
                    // 钩子函数，主要用于清理一些资源
                    onShutdown();
                } finally {
                    mainLock.unlock();
                }
                tryTerminate();
            }
            shutdownNow 方法
            shutdownNow 方法会立即关闭线程池，并且会清空当前工作队列中的剩余任务，返回尚未执行的任务。

            其源码如下：

            public List<Runnable> shutdownNow() {
                List<Runnable> tasks;
                final ReentrantLock mainLock = this.mainLock;
                mainLock.lock();
                try {
                    // 检查权限
                    checkShutdownAccess();
                    // 设置线程池状态
                    advanceRunState(STOP);
                    // 中断所有线程，包括工作线程和空闲线程
                    interruptWorkers();
                    // 丢弃工作队列种的剩余任务
                    tasks = drainQueue();
                } finally {
                    mainLock.unlock();
                }
                tryTerminate();
                return tasks;
            }
            awaitTermination 方法
            如果需要等待到线程池关闭，可以调用 awaitTermination 方法，使用方法如下：

            threadPool.shutdown();
            try {
                while(!threadPool.awaitTermination(60, TimeUnit.SECONDS)) {
                    System.out.println("线程池任务还未结束");
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            线程池关闭完成后，方法会返回 true，这里不建议一直等待，可以参考如下代码（参考Dubbo）：

            if(!threadPool.isTerminated()) {
                try {
                    for(int i = 0; i < 1000; i++) {
                        if(threadPool.awaitTermination(10, TimeUnit.MILLISENCONDS)) {
                            break;
                        }
                        threadPool.shutdownNow();
                    }
                } catch (InterruptException e) {
                    System.err.println(e.getMessage());
                } carch (Throwable e) {
                    System.err.println(e.getMessage());
                }
            }
            优雅关闭线程池
            优雅关闭线程池分为以下几步：

            执行 shutdown() 方法，拒绝新任务的提交，并等待所有任务有序地执行完毕。
            执行 awaitTermination(long timeout, TimeUnit unit) 方法，指定超时时间，判断是否已经关闭所有任务，线程池关闭完成。
            如果 awaitTermination 返回 false，或者被中断，就调用 shutdownNow() 方法立即关闭线程池所有任务。
            补充执行 awaitTermination(long timeout, TimeUnit unit) 方法，判断线程池是否关闭完成，如果超时，就可以进入循环关闭，循环一定的次数（如 1000 次），不断关闭线程池，直到其关闭或者循环结束。
            可以参考如下代码：


              public static void shutdownThreadPoolGracefully(ExecutorService threadPool) {

                // 如果已经返回则关闭
                if(!(threadPool instanceof ExecutorService) || threadPool.isTerminated()) {
                    return;
                }

                try {
                    threadPool.shutdown();
                } catch (SecurityException e) {
                    return;
                } catch (NullPointerException e) {
                    return;
                }

                try {
                    // 等待 60 秒，等待线程池中的任务完成执行
                    if(!threadPool.awaitTermination(60, TimeUnit.SECONDS)) {
                        threadPool.shutdownNow();
                        if(!threadPool.awaitTermination(60, TimeUnit.SECONDS)) {
                            System.out.println("线程池任务未正常执行结束");
                        }
                    }
                } catch (InterruptedException ie) {
                    threadPool.shutdownNow();
                }

                // 如果仍然没关闭，循环关闭 1000 次
                if(!threadPool.isTerminated()) {
                    try {
                        for(int i = 0; i < 1000; i++) {
                            if(threadPool.awaitTermination(10, TimeUnit.MILLISECONDS)) {
                                break;
                            }
                            threadPool.shutdownNow();
                        }
                    } catch (InterruptedException e) {
                        System.err.println(e.getMessage());
                    } catch (Throwable e) {
                        System.err.println(e.getMessage());
                    }
                }
            }

            注册 JVM 钩子函数自动关闭线程池
            如果使用了线程池，可以在 JVM 种注册一个钩子函数，在 JVM 进程关闭之前，由钩子函数自动将线程池优雅地关闭，以确保资源正确释放。

            参考如下代码：

            static class SeqOrScheduledTargetThreadPoolLazyHolder {
                static final ScheduledThreadPoolExecutor EXECUTOR =
                        new ScheduledThreadPoolExecutor(1, new CustomThreadFactory("seq"));

                static {
                    Runtime.getRuntime().addShutdownHook(
                        new ShutdownHookThread("定时和顺序任务线程池", new Callable<Void>() {
                            @Override
                            public Void call() throws Exception {
                                shutdownThreadPoolGracefully(EXECUTOR);
                                return null;
                            }
                        })
                    );
                }
            }

 */
 /*
        11.Executors 快捷创建线程池的潜在问题
        前文说到很多企业的编程规范中，禁止使用 Executors 快捷创建线程池，这里说下原因。

        创建固定数量的线程池和单线程化线程池：在于使用的阻塞队列是无界队列，如果任务提交速度大于任务处理速度，会造成队列中大量的任务等待。如果队列很大， 会导致 JVM 出现 Out Of Memory。

        可缓存线程池和可调度线程池：线程数量不设上限，可以无限创建线程，如果任务提交过多，会造成大量的线程被启动，出现 Out Of Memory。

        12.确定线程池的线程数
        初始化线程池的时候需要设置线程数。

        对于 IO 密集型任务，执行 IO 操作时间较长，CPU利用率不高，例如 Netty，可以设置 IO 处理线程数默认为 CPU 核数的两倍。

        对于 CPU 密集型任务，可以设置线程数等于 CPU 数就行。如果设置线程数太多，会频繁切换线程。

        对于混合型任务，一般参考一个业界比较成熟的估算公式：

        最佳线程数 = ((线程等待时间 + 线程CPU时间) / 线程 CPU 时间) * CPU核数

        经过简单的换算，以上公式可以进一步转换为：

        最佳线程数 = (线程等待时间与线程CPU时间之比 + 1) * CPU核数


*/


    }

    private static class TargetTask implements Runnable {
        static AtomicInteger taskNo = new AtomicInteger(1);
        private String taskName;
        public TargetTask() {
            taskName = "Task-" + taskNo.get();
            taskNo.incrementAndGet();
        }
        @Override
        public void run() {
            System.out.println("任务： " + taskName + " doing");
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("任务：" + taskName + "运行结束");
        }
    }

    private static class SimpleThreadFactory implements ThreadFactory {
        static AtomicInteger threadNo = new AtomicInteger(1);

        @Override
        public Thread newThread(Runnable r) {
            String threadName = "SimpleThread-" + threadNo.get();
            System.out.println("创建一个线程，名称为：" + threadName);
            threadNo.incrementAndGet();
            Thread thread = new Thread(r, threadName);
            thread.setDaemon(true);
            return thread;
        }
    }


//    作者：简单一点点
//    链接：https://www.jianshu.com/p/e60db9993a39
//    来源：简书
//    著作权归作者所有。商业转载请联系作者获得授权，非商业转载请注明出处。
}
