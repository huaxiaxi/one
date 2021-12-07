package thread.chapter8;

public interface ThreadPool {
//    提交任务到任务池
    void execute(Runnable runnable);
//    关闭线程池
    void shutdown();
//    获取线程池的初始化大小
    int getInitSize();
//    获取线程池最大的线程数
    int getMaxSize();
//    获取线程池的核心线程数量
    int getCoreSize();
//    获取线程池中活跃线程数量
    int getActiveCount();
//    获取线程池中用于缓存任务队列的大小
    int getQueueSize();
//    查看线程池是否被SHUTDOWN
    boolean isShutdown();
}
