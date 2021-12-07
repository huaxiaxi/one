package thread.chapter6;

import java.util.concurrent.TimeUnit;

public class ThreadGroupEnumerateThreadThreads {
    public static void main(String[] args) throws InterruptedException {
//        ThreadGroup myGroup = new ThreadGroup("myGroup");
//        Thread thread = new Thread(myGroup, () -> {
//            while (true){
//                try {
//                    TimeUnit.SECONDS.sleep(1);
//                }catch (InterruptedException e){
//
//                }
//            }
//        }, "MyThread");
//
//        thread.start();
//        TimeUnit.MILLISECONDS.sleep(2);
//        ThreadGroup mainGroup = Thread.currentThread().getThreadGroup();
//
//        Thread[] list = new Thread[mainGroup.activeCount()];
//        int recurseSize = mainGroup.enumerate(list);
//        System.out.println(recurseSize);
//
//        recurseSize = mainGroup.enumerate(list, false);
//        System.out.println(recurseSize);
        ThreadGroup myGroup = new ThreadGroup("group1");
        Thread thread = new Thread(myGroup, () -> {
            while (true){
                try {
                    TimeUnit.SECONDS.sleep(1);
                }catch (InterruptedException e){

                }
            }
        }, "thread");
        thread.setDaemon(true);
        thread.start();

        TimeUnit.MILLISECONDS.sleep(1);
        ThreadGroup mainGroup = Thread.currentThread().getThreadGroup();

        System.out.println("activeCount=" + mainGroup.activeCount());
        System.out.println("activeGroupCount=" + mainGroup.activeGroupCount());
        System.out.println("getMaxPriority=" + mainGroup.getMaxPriority());
        System.out.println("getName=" + mainGroup.getName());
        System.out.println("getParent=" + mainGroup.getParent());
        mainGroup.list();
        System.out.println("-------------------------------");
        System.out.println("parentOf=" + mainGroup.parentOf(myGroup));
        System.out.println("parentOf=" + mainGroup.parentOf(mainGroup));

    }
}
