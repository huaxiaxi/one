package start.B001_thread;

public class A010_LongAdder {
//    在争用激烈的场景下，会导致大量的CAS空自旋。这会浪费大量的CPU资源，大大降低了程序的性能。
//
//    这时候可以通过使用 LongAdder 代替 AtomicInteger 来提高CAS操作的性能。
//
//    以空间换时间：LongAdder
//    Java8提供了一个类 LongAdder 通过以空间换时间的方式提高并发场景下CAS操作的性能。
//
//    LongAdder 的核心思想是热点分离，与 ConcurrentHashMap 的设计思想类似，将 value 值分离成一个数组，当多线程访问时，通过 Hash 算法将线程映射到数组的一个元素进行操作，而获取最终的的 value 结果时，则将数组的元素求和。
//
//    最终，通过 LongAdder 将内部操作对象从单一 value 值“演变”成一些列的数组元素，从而减小了内部竞争的粒度。

//    oneMaven: LongAdderTest

//    LongAdder 的原理
//    AtomicLong 使用内部变量 value 保存着实际的 long 值，所有的操作都是针对该变量进行的。也就是说在高并发环境下，value 变量其实是一个热点，重试线程越多，CAS失败概率更高，从而进入恶性CAS空自旋状态。
//
//    LongAdder 的基本实录是分散热点，将 value 值分散到一个数组中，不同线程会命中到数组的不同槽（元素）中，各个线程只对自己槽中的那个值进行CAS操作。这样热点就被分担了，冲突的概率就小很多。
//
//    使用 LongAdder，即使线程数再多也不必担心，各个线程会分配到多个元素上去更新，增加元素个数，就可以降低 value 的“热度”，恶性CAS空自旋就解决了。
//
//    如果要获得完整的 LongAdder 存储的值，只要将各个槽中的变量值累加，返回最终累加之后的值即可。
//
//    LongAdder 的实现思路与 ConcurrentHashMap 中分段锁的基本原理非常相似，本质上都是不同的线程在不同的单元上进行操作，这样减少了线程竞争，提高了并发效率。
//
//    LongAdder 的设计体现了空间换时间的思想，不过在实际高并发场景下，数组元素所消耗的空间可以忽略不计。

//    LongAdder 实例的内部结构
//      LongAdder 的内部成员包含一个 base 值和一个 cells 数组，在最初无竞争时，只操作 base 的值，当线程执行CAS失败后，才初始化 cells 数组，并为线程分配所对应的元素。

//    基类 Striped64 内部三个重要成员
//        LongAdder 继承于 Stripped64 类，base 值和 cell 数组都在 Stripped64 类中定义，它的内部三个重要的成员如下：
//
//        /**
//         * 成员一：存放Cell的哈希表，大小为2的幂
//         */
//        transient volatile Cell[] cells;
//        /**
//         * 成员二：基础值
//         * 1.在没有竞争时会更新这个值
//         * 2.在cells初始化时，cells不可用，也会尝试通过CAS操作值累加到base
//         */
//        transient volatile long base;
//        /**
//         * 自旋锁，通过CAS操作加锁，为0表示cells数组没有处于创建、扩容阶段
//         * 为1表示正在创建或者扩展cells数组，不能进行新Cell元素的设置操作
//         */
//        transient volatile int cellsBusy;
//        Stripped64 内部包含一个 base 和一个 Cell[] 类型的 cells 数组，cells 数组又叫哈希表，在没有竞争的情况下，要累加的数通过CAS累加到 base 上，如果有竞争的话，会将要累加的数累加到 cells 数组中的某个 Cell 元素里面。
//
//        Stripped64 的整体值 value 的获取函数如下：
//
//        public long longValue() {
//            return sum();
//        }
//
//        /**
//         * 将多个cells数组中的值加起来的和
//         */
//        public long sum() {
//            Cell[] as = cells;
//            Cell a;
//            long sum = base;
//            if (as != null) {
//                for (int i = 0; i < as.length; ++i) {
//                    if ((a = as[i]) != null)
//                        sum += a.value;
//                }
//            }
//            return sum;
//        }
//        Stripped64 的设计核心思路是通过内部的分散计算来避免竞争，以空间换取时间。没有竞争时 cells 数组为 null，这时只使用 base，一旦发生竞争，cells 数组就上场了。
//
//        cells 数组第一次初始化长度为2，以后每次扩容都变为原来的两倍，一直到 cells 数组的长度大于等于当前服务器的CPU核数，同一时刻能持有CPU时间片去并发操作同一内存地址的最大线程数最多也就是CPU的核数。
//
//        在存在线程争用的时候，每个线程被映射到 cells[threadLocalRandomProbe&cells.length] 位置的 Cell 元素，该线程对 value 所做的累加操作就执行在对应的 Cell 元素的值上。
//
//        LongAdder 类的 add() 操作
//        这里分析以下 LongAdder 类的 add() 方法，具体的源码如下：
//
//        /**
//         * Adds the given value.
//         *
//         * @param x the value to add
//         */
//        public void add(long x) {
//            Cell[] as; long b, v; int m; Cell a;
//            if ((as = cells) != null || !casBase(b = base, b + x)) {
//                boolean uncontended = true;
//                if (as == null || (m = as.length - 1) < 0 ||
//                        (a = as[getProbe() & m]) == null ||
//                        !(uncontended = a.cas(v = a.value, v + x)))
//                    longAccumulate(x, null, uncontended);
//            }
//        }
//
//        /**
//         * 自增1
//         */
//        public void increment() {
//            add(1L);
//        }
//
//        /**
//         * 自减1
//         */
//        public void decrement() {
//            add(-1L);
//        }
//        首先介绍一下代码的外层 if 块的两个条件语句：
//
//        cells 数组不为null，说明存在争用，在不存在争用的时候，cells 数组一定为null，一旦对 base 的CAS操作失败，才会初始化 cells 数组。
//        如果 cells 数组为null，表示之前不存在争用，并且此次 casBase 执行成功，表示基于 base 成员累加成功，add 方法直接返回；如果 casBase 方法执行失败，说明产生了第一次争用冲突，需要对 cells 数组初始化，此时即将进入内嵌 if 块。
//        casBase 方法很简单，就是通过 UNSAFE 类的CAS设置成员变量 base 的值为 base+x。casBase方法的代码如下：
//
//        final boolean casBase(long cmp, long val) {
//            return UNSAFE.compareAndSwapLong(this, BASE, cmp, val);
//        }
//        内嵌的if语句块逻辑如下：
//
//        as == null || (m = as.length - 1)<0 代表 cells 没有初始化。
//        当前线程的哈希值在 cells 数组映射位置的 Cell 对象为空，意思是还没有其他线程在同一个位置做过累加操作。
//        当前线程的哈希值在 cells 数组映射位置的 Cell 对象不为空，然后在该 Cell 对象上进行CAS操作，设置其值为 v+x，但是CAS操作失败，表示存在争用。
//        如果以上条件满足一个，就进入 longAccumulate 方法。
//
//        LongAdder 类中的 longAccumulate() 方法
//        longAccumulate() 是 Striped64 中重要的方法，实现不同的线程更新各自 Cell 中的值，其实现逻辑类似于分段锁，具体代码如下：
//
//        final void longAccumulate(long x, LongBinaryOperator fn,
//                                  boolean wasUncontended) {
//            int h;
//            if ((h = getProbe()) == 0) {
//                ThreadLocalRandom.current(); // force initialization
//                h = getProbe();
//                wasUncontended = true;
//            }
//            // 扩容意向，collide=true可以扩容，collide=false不可扩容
//            boolean collide = false;
//            // 自旋，一直到操作成功
//            for (;;) {
//                // as 表示cells引用
//                // a 表示当前线程命中的Cell
//                // n表示cells数组长度
//                // v 表示期望值
//                Cell[] as; Cell a; int n; long v;
//                // true表示下标位置的Cell为null，需要创建Cell
//                if ((as = cells) != null && (n = as.length) > 0) {
//                    if ((a = as[(n - 1) & h]) == null) {
//                        if (cellsBusy == 0) {       // cells 数组没有处于创建，扩容阶段
//                            Cell r = new Cell(x);   // Optimistically create
//                            if (cellsBusy == 0 && casCellsBusy()) {
//                                boolean created = false;
//                                try {               // Recheck under lock
//                                    Cell[] rs; int m, j;
//                                    if ((rs = cells) != null &&
//                                            (m = rs.length) > 0 &&
//                                            rs[j = (m - 1) & h] == null) {
//                                        rs[j] = r;
//                                        created = true;
//                                    }
//                                } finally {
//                                    cellsBusy = 0;
//                                }
//                                if (created)
//                                    break;
//                                continue;           // Slot is now non-empty
//                            }
//                        }
//                        collide = false;
//                    }
//                    // 当前线程竞争失败，wasUncontended为false
//                    else if (!wasUncontended)       // CAS already known to fail
//                        wasUncontended = true;      // Continue after rehash
//                        // 当前线程rehash过哈希值，CAS更新cell
//                    else if (a.cas(v = a.value, ((fn == null) ? v + x :
//                            fn.applyAsLong(v, x))))
//                        break;
//                        // 调整扩容意向，然后进入下一轮循环
//                    else if (n >= NCPU || cells != as)
//                        collide = false;            // At max size or stale
//                        // 设置扩容意向为true，但是不一定真的发生扩容
//                    else if (!collide)
//                        collide = true;
//                        // 真正扩容的逻辑
//                    else if (cellsBusy == 0 && casCellsBusy()) {
//                        try {
//                            if (cells == as) {      // Expand table unless stale
//                                Cell[] rs = new Cell[n << 1];
//                                for (int i = 0; i < n; ++i)
//                                    rs[i] = as[i];
//                                cells = rs;
//                            }
//                        } finally {
//                            cellsBusy = 0; // 释放锁
//                        }
//                        collide = false;
//                        continue;                   // Retry with expanded table
//                    }
//                    h = advanceProbe(h); //重置当前线程的Hash值
//                }
//                // cells还未初始化，并且cellsBusy加锁成功
//                else if (cellsBusy == 0 && cells == as && casCellsBusy()) {
//                    boolean init = false;
//                    try {                           // Initialize table
//                        if (cells == as) {
//                            Cell[] rs = new Cell[2];
//                            rs[h & 1] = new Cell(x);
//                            cells = rs;
//                            init = true;
//                        }
//                    } finally {
//                        cellsBusy = 0;
//                    }
//                    if (init)
//                        break;
//                }
//                // 当前线程cellsBusy加锁失败，表示其它线程这鞥在初始化cells
//                // 所以当前线程将值累加到base，注意add()方法调用此方式时fn为null
//                else if (casBase(v = base, ((fn == null) ? v + x :
//                        fn.applyAsLong(v, x))))
//                    break;                          // Fall back on using base
//            }
//        }
//        LongAddr 类的 casCellsBusy 方法
//        casCellsBusy()方法的代码很简单，就是将 cellsBusy 成员的值改为1，表示目前的cells数组在初始化或扩容中。代码如下：
//
//        final boolean casCellsBusy() {
//            return UNSAFE.compareAndSwapInt(this, CELLSBUSY, 0, 1);
//        }
//        casCellsBusy() 方法相当于锁的功能，当线程需要 cells 数组初始化或扩容时，需要调到 casCellsBusy() 方法，通过CAS方式将 cellsBusy 成员的值改为1，如果修改失败，就表示其它的线程正在进行数组初始化或扩容的操作。在 cells 数组初始化或扩容的操作执行完成之后，cellsBusy 成员的值被改为0，这时不需要进行CAS修改，直接修改即可，因为不存在争用。
//
//        当 cellsBusy 值为1时，表示cells数组正在被某个线程执行初始化或扩容操作，其它线程不能进行以下操作：
//
//        对cells数组初始化。
//        对cells数组扩容。
//        如果cells数组中某个元素为null，就为该元素创建新的Cell对象，因为数组的结构正在修改，所以其他线程下面不能创建新的Cell对象。


//
//    作者：简单一点点
//    链接：https://www.jianshu.com/p/30dfa17a8521
//    来源：简书
//    著作权归作者所有。商业转载请联系作者获得授权，非商业转载请注明出处。
}
