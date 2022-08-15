package start.B001_thread;

public class A009_thread_JUC {
//    在多线程并发中，诸如“++”或“--”元素不具备原子性，不是线程安全的，需要使用JDK中的JUC原子类
//
//    Atomic 原子操作包
//    JUC中的 java.util.concurrent.atomic 类的原子类主要分为以下四种：
//
//    基本原子类：
//    AtomicInteger：整型原子类。
//    AtomicLong：长整型原子类。
//    AtomicBoolean：布尔型原子类。
//    数组原子类：
//    AtomicIntegerArray：整型数组原子类。
//    AtomicLongArray：长整型数组原子类。
//    AtomicReferenceArray：引用类型数组原子类。
//    引用原子类：
//    AtomicReference：引用类型原子类。
//    AtomicMarkableReference：带有更新标记位的原子引用类型。
//    AtomicStampedReference：带有更新版本号的原子引用类型。
//    字段更新原子类：
//    AtomicIntegerFieldUpdater：原子更新整型字段的更新器。
//    AtomicLongFieldUpdater：原子更新长整型字段的更新器。
//    AtomicRefernceFieldUpdater：原子更新引用类型中的字段。

//    AtomicInteger 线程安全原理
//    基础原子类主要通过CAS自旋和volatile实现，下面以 AtomicInteger 源码为例分析一下。
//
//    public class AtomicInteger extends Number implements java.io.Serializable {
//        private static final long serialVersionUID = 6214790243416807050L;
//
//        // Unsafe类实例
//        private static final Unsafe unsafe = Unsafe.getUnsafe();
//
//        // value属性值的地址偏移量
//        private static final long valueOffset;
//
//        static {
//            try {
//                // 计算 value 属性值的地址偏移量
//                valueOffset = unsafe.objectFieldOffset
//                        (AtomicInteger.class.getDeclaredField("value"));
//            } catch (Exception ex) { throw new Error(ex); }
//        }
//
//        // 内部 value值，使用volatile保证线程可见性
//        private volatile int value;
//
//        // 初始化
//        public AtomicInteger(int initialValue) {
//            value = initialValue;
//        }
//
//        public AtomicInteger() {
//        }
//
//        // 获取当前 value 值
//        public final int get() {
//            return value;
//        }
//
//        // 设置值
//        public final void set(int newValue) {
//            value = newValue;
//        }
//
//
//
//        // 返回旧值赋予新值
//        public final int getAndSet(int newValue) {
//            return unsafe.getAndSetInt(this, valueOffset, newValue);
//        }
//
//        // 封装底层CAS操作
//        // 对比expect和value，不同返回false，相同则将新值赋予给value，并返回true
//        public final boolean compareAndSet(int expect, int update) {
//            return unsafe.compareAndSwapInt(this, valueOffset, expect, update);
//        }
//
//
//        // 安全自增
//        public final int getAndIncrement() {
//            return unsafe.getAndAddInt(this, valueOffset, 1);
//        }
//
//    ...
//        可以看到，其中的主要方法都通过 CAS 自旋实现，CAS自旋的主要操作为：如果一次CAS操作失败，获取最新的 value 值，再次进行 CAS 操作，直到成功。
//
//        其中的内部成员 value 使用了关键字 volatile 进行修饰，保证任何时刻总能拿到该变量的最新值，其目的在于保障变量值的线程可见性。

//    引用类型原子类 oneMaven

//
//    ABA问题
//    CAS操作如果使用不合理，会存在 ABA 问题。
//
//    ABA 问题介绍
//    通过一个例子来说明 ABA 问题。
//
//    一个线程A 从内存位置M中取出V1，另一个线程B也取出V1。现在假设线程B进行了一些操作之后将M位置的数据V1变成了V2，然后又有一些操作之后将V2变成了V1。之后，线程A进行CAS操作，发现M位置的数据仍然是V1，然后线程A操作成功。
//
//    尽管线程A的CAS操作陈宫，但是A操作的数据V1可能已经不是之前的V1，而是被线程B替换过的V1，这就是ABA问题。
//
//    ABA问题解决方案
//    很多乐观锁的实现版本都是使用版本号（Version）方式来解决ABA问题。乐观锁每次在执行数据的修改操作时都会带上一个版本号，版本号和数据的版本号一致就可以执行修改操作并对版本号执行加1操作，否则执行失败。因为每次操作的版本号都会随之增加，所以不会出现ABA问题。
//
//    使用 AtomicStampedReference 解决ABA问题
//    参考乐观锁的版本号，JDK提供了一个 AtomicStampedReference 类来解决ABA问题。它在CAS的基础上增加了一个Stamp，用来观察数据是否发生变化。
//
//    AtomicStampedReference 的 compareAndSet() 方法首先检查当前的对象引用值是否等于预期引用，并且当前标志是否等于预期标志，如果全部相等，就以原子方式将引用值和标志的值更新为给定的更新值。
//
//    AtomicStampReference 的构造器有两个参数，具体如下：
//
//    AtomicStampedReference(V initialRef, int initialStamp);
//    包含常用方法如下：
//
//    //获取被封装的数据
//    public V getReference();
//
//    // 获取被封装的数据的版本标志
//    public int getStamp();
//    CAS 操作定义如下：
//
//    public boolean compareAndSet {
//        V expectedReference,   // 预发引用值
//        V newReference,  // 更新后的引用值
//        int expectedStamp, // 预期标志
//        int newStamp // 更新后的标志
//    }
//    使用 AtomicMarkableReference 解决ABA 问题
//    AtomicMarkableReference 是 AtomicStampedReference 的简化版，只关心是否修改过，其标记 mark 是 boolean 类型。
//
//    AtomicMarkableReference 适用于只要知道对象是否被修改过，而不适用于对象被反复修改的场景



//    作者：简单一点点
//    链接：https://www.jianshu.com/p/5a01de771da5
//    来源：简书
//    著作权归作者所有。商业转载请联系作者获得授权，非商业转载请注明出处。
}
