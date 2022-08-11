package start.B001_thread;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

public class A004_thread_local {

//    private static final ThreadLocal<Foo> LOCAL_FOO = new ThreadLocal<>();
    private static final ThreadLocal<Foo> LOCAL_FOO = ThreadLocal.withInitial(() -> new Foo());

    public static void main(String[] args) {
        ExecutorService pool = Executors.newCachedThreadPool();
        for (int i = 0; i < 3; i++) {
            pool.execute(() -> {
//                if (LOCAL_FOO.get() == null){
//                    LOCAL_FOO.set(new Foo());
//                }
                System.out.println("线程初始值：" + LOCAL_FOO.get());
                try {
                    for (int i1 = 0; i1 < 3; i1++) {
                        Foo foo = LOCAL_FOO.get();
                        foo.setBar(foo.getBar() + 1);
                        Thread.sleep(10);
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("最终线程值：" + LOCAL_FOO.get());
                LOCAL_FOO.remove();
            });
        }
//        ThreadLocal的使用场景
//        ThreadLocal是解决线程安全问题一个较好的方案，它通过为每个线程提供一个独立的值去解决并发的冲突问题。
//
//        线程隔离
//        ThreadLocal 主要用于线程隔离，防止自己的变量被其它线程修改，而且可以避免同步锁带来的性能损失。
//
//        ThreadLocal 线程隔离的使用案例包括为每个线程绑定一个用户会话信息、数据库连接、HTTP请求等。常见使用场景为数据库连接独享、Session数据管理等。
//
//        看一段 Hibernate 中的代码：
//
//        private static final ThreadLocal threadSession = new ThreadLocal();
//
//        public static Session getSession() throws InfrastructureException {
//            Session s = (Session) threadSession.get();
//            try {
//                if(s == null) {
//                    s = getSessionFactory().openSession();
//                    threadSession.set(s);
//                }
//            } catch (HibernateException ex) {
//                throw new InfrastructureException(ex);
//            }
//            return s;
//        }
//        跨函数传递数据
//        通常用于一个线程内，跨类、跨方法传递数据时，在某一个地方进行了设置，在随后同一个线程的任意地方都可以获取。
//
//        典型应用包括：
//
//        用来传递请求过程中的用户ID。
//        用来传递请求过程中的用户会话。
//        用来传递HTTP用户的请求实例的 HttpRequest。
//        下面是一段参考代码：
//
//        public class SessionHolder {
//            private static final ThreadLocal<String> sidLocal = new ThreadLocal<>("sidLocal");
//
//            private static final ThreadLocal<UserDTO> sessionUserLocal = new ThreadLocal<>("sessionUserLocal");
//
//            private static final ThreadLocal<HttpSession> sessionLocal = new ThreadLocal<>("sessionLocal");
//
//    ...
//
//            public static void setSession(HttpSession session) {
//                sessionLocal.set(session);
//            }
//
//            public static HttpSession getSession() {
//                HttpSession session = sessionLocal.get();
//                Assert.notNull(session, "session not set");
//                return session;
//            }
//        }

//        ThreadLocal 源码分析
//        本部分看下 ThreadLocal 源码。
//
//        set方法
//        set(T value)方法用于设置本地线程变量，核心源码如下：
//
//        public void set(T value) {
//            // 获取当前线程
//            Thread t = Thread.currentThread();
//            // 获取当前线程的 ThreadLocalMap
//            ThreadLocalMap map = getMap(t);
//            if (map != null)
//                map.set(this, value);
//            else
//                createMap(t, value);
//        }
//
//        ThreadLocalMap getMap(Thread t) {
//            return t.threadLocals;
//        }
//
//
//        void createMap(Thread t, T firstValue) {
//            t.threadLocals = new ThreadLocalMap(this, firstValue);
//        }
//        大致流程：
//
//        获取当前线程，并获取当前线程的 ThreadLocalMap 成员传入 map。
//        如果 map 不为空，就将 value 设置到 map 中，当前的 ThreadLocal 作为键。
//        如果 map 为空，就位该线程创建 map，然后将 value 加入其中。
//        get方法
//        get() 方法源码如下：
//
//        public T get() {
//            // 获取当前线程
//            Thread t = Thread.currentThread();
//            // 获取线程的 ThreadLocalMap
//            ThreadLocalMap map = getMap(t);
//            // 如果map不为空
//            if (map != null) {
//                ThreadLocalMap.Entry e = map.getEntry(this);
//                if (e != null) {
//                    @SuppressWarnings("unchecked")
//                    T result = (T)e.value;
//                    return result;
//                }
//            }
//            // 如果map为空
//            return setInitialValue();
//        }
//
//// 设置 ThreadLocal 关联的初始值并返回
//        private T setInitialValue() {
//            // 获取初始值
//            T value = initialValue();
//            Thread t = Thread.currentThread();
//            ThreadLocalMap map = getMap(t);
//            if (map != null)
//                map.set(this, value);
//            else
//                createMap(t, value);
//            return value;
//        }
//        大致流程如下：
//
//        获取当前线程，并获取当前线程的 ThreadLocalMap 成员，暂存于 map 变量。
//        如果获得的 map 不为空，那么以当前的 ThreadLocal 实例为键获取 map 中的记录。
//        如果记录的值不为空，就返回该值。
//        如果记录为空，就利用 initialValue() 初始化钩子函数获取初始值，被设置在 map 中。
//        remove 方法
//        remove() 方法用于移除线程本地变量中的值，源码如下：
//
//        public void remove() {
//            ThreadLocalMap m = getMap(Thread.currentThread());
//            if (m != null)
//                m.remove(this);
//        }
//        initialValue 方法
//        initialValue() 方法用于获取初始值，源码如下：
//
//        protected T initialValue() {
//            return null;
//        }
//        默认返回 null，一般使用 ThreadLocal.withInitial(...) 静态工厂方法来在定义 ThreadLocal 实例时设置初始值的回调函数。
//
//        静态工厂方法源码如下：
//
//        static final class SuppliedThreadLocal<T> extends ThreadLocal<T> {
//
//            private final Supplier<? extends T> supplier;
//
//            SuppliedThreadLocal(Supplier<? extends T> supplier) {
//                this.supplier = Objects.requireNonNull(supplier);
//            }
//
//            @Override
//            protected T initialValue() {
//                return supplier.get();
//            }
//        }
//        使用示例：
//
//        ThreadLocal<Foo> LOCAL_FOO = ThreadLocal.withInitial(() -> new Foo());
//        ThreadLocalMap 源码分析
//        ThreadLocal 的操作都是基于 ThreadLocalMap 展开的，而 ThreadLocalMap 是 ThreadLocal 的一个内部静态类，实现了一套简单的 Map 结构。
//
//        ThreadLocalMap 的成员变量如下：
//
//        static class ThreadLocalMap {
//            // 条目类型，一个静态内部类
//            static class Entry extends WeakReference<ThreadLocal<?>> {
//                /** The value associated with this ThreadLocal. */
//                Object value;
//
//                Entry(ThreadLocal<?> k, Object v) {
//                    super(k);
//                    value = v;
//                }
//            }
//
//            // 初始容量
//            private static final int INITIAL_CAPACITY = 16;
//
//            // 条目数组，作为哈希表使用
//            private Entry[] table;
//
//            // 当前条目数量
//            private int size = 0;
//
//            // 扩容因子
//            private int threshold; // Default to 0
//    ...
//
//            然后看一下它的 set 方法。
//
//            private void set(ThreadLocal<?> key, Object value) {
//
//                Entry[] tab = table;
//                int len = tab.length;
//                // 根据 key 的 HashCode 找到插槽
//                int i = key.threadLocalHashCode & (len-1);
//
//                // 从i开始向后循环搜索
//                for (Entry e = tab[i];
//                     e != null;
//                     e = tab[i = nextIndex(i, len)]) {
//                    ThreadLocal<?> k = e.get();
//
//                    // 找到插槽
//                    if (k == key) {
//                        e.value = value;
//                        return;
//                    }
//
//                    // 找到异常插槽，重新设置
//                    if (k == null) {
//                        replaceStaleEntry(key, value, i);
//                        return;
//                    }
//                }
//
//                // 没有找到插槽，增加新的 Entry
//                tab[i] = new Entry(key, value);
//                // 数量增加
//                int sz = ++size;
//                // 清理 key 为 null 的无效 Entry
//                if (!cleanSomeSlots(i, sz) && sz >= threshold)
//                    rehash();
//            }
//            另外，需要注意 Entry 使用了弱引用（WeakReference）。
//
//            static class Entry extends WeakReference<ThreadLocal<?>> {
//                /** The value associated with this ThreadLocal. */
//                Object value;
//
//                Entry(ThreadLocal<?> k, Object v) {
//                    super(k);
//                    value = v;
//                }
//            }
//            这里简单介绍下原因。首先介绍下弱引用的特点，仅有弱引用指向的对象只能生存到下一次垃圾回收之前。因此，在下次 GC 发生时，就可以使用那些没有被其他强引用指向、仅被 Entry 的 Key 所指向的 ThreadLocal 实例能被顺利回收。回收之后，Entry 的 Key 变为空，后续调用 get()、set() 或 remove() 的时候会清空这些 Entry。
//
//            ThreadLocal 使用规范
//            在如下条件下，ThreadLocal 有可能发生内存泄露：
//
//            线程长时间运行而没有被销毁，线程池中的 Thread 实例很容易满足此条件。
//            ThreadLocal 引用内设置为null，且后续在同一 Thread 实例执行期间，没有发生其它 ThreadLocal 实例的get()、set() 或 remove()操作。
//            建议使用 ThreadLocal 时遵循以下原则：
//
//            尽量使用 private static final 修饰 ThreadLocal 实例，private 和 final修饰符保证尽可能不让他人修改，static修饰符保证实例全局唯一。
//            ThreadLocal 使用完成之后务必调用 remove() 方法。


//        作者：简单一点点
//        链接：https://www.jianshu.com/p/b089360c4899
//        来源：简书
//        著作权归作者所有。商业转载请联系作者获得授权，非商业转载请注明出处。
    }
    static class Foo {
        static final AtomicInteger AMOUNT = new AtomicInteger(0);
        int index = 0;
        int bar = 10;
        public Foo() {
            index = AMOUNT.incrementAndGet();
        }

        public int getBar() {
            return bar;
        }

        public void setBar(int bar) {
            this.bar = bar;
        }

        @Override
        public String toString() {
            return index + "@Foo{bar=" + bar + "}";
        }
    }

}
