package start.B001_thread;

import org.junit.Test;

public class A006_lock {
//    1. Java对象结构
//    Java 内置锁很多信息都放在对象结构中，这里先了解一下 Java 对象结构。
//    Java 对象（Object实例）结构包括对象头、对象体和对齐字节三部分。
//    三部分的作用：
//    对象头包括三个字段：
//    Mark Word（标记字），用于存储自身运行时的数据，例如GC标志位、哈希码、锁状态等。
//    Class Pointer（类对象指针），用于存放方法区Class对象的地址，虚拟机通过这个指针来确定这个对象是哪个类的实例。
//    Array Length（数组长度），可选字段，Java数组用于记录长度。
//    对象体包含对象的实例变量，用于成员属性值，包括父类的成员属性值，这部分内存按4字节对齐。
//    对齐字节也叫填充字节，保证Java对象所占内存字节数是8的倍数。
//    1.2 Mark Word
//    Java 内置锁的信息主要存放在对象头的 Mark Word 之中。
//    Java 内置锁在 JDK 1.8之后有4种状态：无锁、偏向锁、轻量级锁、重量级锁。
//    下面介绍下各个部分：图5.lock_in_java8
//        lock：锁状态标记位。
//        biased_lock：是否启用偏向锁。
//        age：分代年龄。
//        identity_hashcode：对象表示哈希码。
//        thread：线程ID。
//        epoch：偏向时间戳。
//        ptr_to_lock_record：在轻量级锁的状态下指向栈帧中锁记录的指针。
//        ptr_to_heavyweight_monitor：在重量级锁的状态下指向对象监视器的指针。
//        其中lock和biased_lock一起表示锁状态：
//
//            状态	biased_lock	lock
//            无锁	0	01
//            偏向锁	1	01
//            轻量级锁	0	00
//            重量级锁	0	10
//            GC标记	0	11
//    2. 锁状态
//    锁状态会随着竞争情况而逐渐升级，但是内置锁只可以升级，不能降级。
//
//    下面依次介绍下每个锁状态：
//
//    无锁：Java对象刚刚创建的时候没有任何线程来竞争，说明对象处于无锁状态。
//    偏向锁：偏向锁是指一段同步代码一直被同一个线程所访问，那么该线程会自动获取锁。这个过程不需要任何检查和切换，非常高效。
//    轻量级锁：当有两个线程开始竞争这个锁对象时，不再是偏向锁，锁会升级为轻量级锁。两个线程公平晶振，哪个线程先占有锁对象，锁对象的 Mark Word 就指向哪个线程的栈帧中的锁记录。
//    重量级锁：重量级锁会让其它申请的线程之间进入阻塞，性能降低。重量级锁也叫同步锁，这时Mark Word会指向一个监视器对象，监视器对象用集合的形式来登记和管理排队的线程。
//    下面在介绍下自旋原理：
//
//    当锁处于偏向锁，又被另一个线程企图抢占时，偏向锁就会升级为轻量级锁。企图抢占的线程会通过自旋的形式尝试获取锁。如果持有锁的线程能在短时间内释放锁资源，那么那些等待竞争锁的线程就不需要进行内核态和用户态之间的切换来进行阻塞挂起的状态，它们只需要等一等（自旋），等持有锁的线程释放后立即获取线程。
//
//    但是如果一直获取不到锁，也不能一直自旋消耗CPU，这就需要设置一个自旋等待的最大时间。JDK1.6 以后引入了自适应自旋锁，自旋的时间不固定，而是由前一次在同一个锁上的自旋时间以及锁的拥有者的状态来决定的。线程如果自旋成功，下次自旋的次数会更多，如果自旋失败，下次就会减少。
//
//    如果持有锁的线程执行的时间超过自旋等待的最大时间仍没有释放锁，就会导致其它争用锁的线程在虽大等待时间内还是获取不到锁，争用线程会停止自旋进入阻塞状态，该锁膨胀为重量级锁。
//

//    3.偏向锁
//    偏向锁的作用是消除无竞争情况下的同步原语，提升程序性能。
//    测试使用 Maven 项目，引入了 OpenJDK 提供的 JOL（Java Object Layout）包
//    详见oneMaven: InnerLockTest

//    3.1偏向锁的撤销
//    如果多个线程竞争偏向锁，会引发偏向锁的撤销（很可能引入安全点），然后膨胀到轻量级锁。
//
//    偏向锁的撤销流程：
//
//    在一个安全点停止拥有锁的线程。
//    遍历线程的栈帧，检查是否存在锁记录，如果存在锁记录，就需要清空锁记录，使其变成无锁状态，并修复记录指向的 Mark Word，清除其线程ID。
//    将当前锁升级成轻量级锁。
//    唤醒当前线程。
//    撤销偏向锁的条件：
//
//    多个线程竞争偏向锁。
//    调用偏向锁对象的 hashcode() 方法或者 System.identityHashCode() 方法之后，将哈希码放置到Mark Word中，内置锁变成无锁状态，偏向锁被撤销。
//    轻量级锁
//            轻量级锁介绍
//    引入轻量级锁的主要目的是在多线程竞争不激烈的情况下，通过CAS机制竞争锁减少重量级锁产生的性能损耗。重量级锁使用了操作系统底层的互斥锁（Mutex Lock），会导致线程在用户态和核心态之间频繁切换，从而带来较大的性能损耗。
//
//    轻量级锁的使用场景：如果一个对象虽然有多线程要加锁，但加锁的时间是错开的（也就是没有竞争），那么可以使用轻量级锁来优化。
//
//    轻量锁存在的目的是尽可能不动用操作系统层面的互斥锁，因为其性能比较差。线程的阻塞和唤醒需要CPU从用户态转为核心态，频繁地阻塞和唤醒对CPU来说是一件负担很重的工作。同时我们可以发现，很多对象锁的锁定状态只会持续很短的一段时间，例如整数的自加操作，在很短的时间内阻塞并唤醒线程显然不值得，为此引入了轻量级锁。轻量级锁是一种自旋锁，因为JVM本身就是一个应用，所以希望在应用层面上通过自旋解决线程同步问题。
//
//    轻量级锁的执行过程：
//
//    在抢锁线程进入临界区之前，如果内置锁没有被锁定，JVM首先将在抢锁线程的栈帧中建立一个锁记录（Lock Record），用于存储对象Mark Word的拷贝，
//
//    然后抢锁线程将使用CAS自旋操作，尝试将内置锁对象头的Mark Word的ptr_to_lock_record（锁记录指针）更新为抢锁线程栈帧中锁记录的地址，如果这个更新执行成功了，这个线程就拥有了这个对象锁。然后JVM将Mark Word中的lock标记位改为00（轻量级锁标志），即表示该对象处于轻量级锁状态。
//
//    抢锁成功之后，JVM会将Mark Word中原来的锁对象信息（如哈希码等）保存在抢锁线程锁记录的Displaced Mark Word（可以理解为放错地方的Mark Word）字段中，再将抢锁线程中锁记录的owner指针指向锁对象。
//
//    锁记录是线程私有的，每个线程都有自己的一份锁记录，在创建完锁记录后，会将内置锁对象的Mark Word复制到锁记录的Displaced Mark Word字段。这是为什么呢？因为内置锁对象的MarkWord的结构会有所变化，Mark Word将会出现一个指向锁记录的指针，而不再存着无锁状态下的锁对象哈希码等信息，所以必须将这些信息暂存起来，供后面在锁释放时使用。

//
//    轻量级锁的分类
//    轻量级锁主要有两种：普通自旋锁和自适应自旋锁。
//
//    普通自旋锁
//    普通自旋锁指当前有线程来竞争锁时，抢锁线程会在原地循环等待，而不是被阻塞，直到那个占有锁的线程释放锁之后，这个抢锁线程才可以获得锁。
//
//    默认情况下，自旋的次数为10次，用户可以通过 -XX:PreBlockSpin 选项来进行更改。
//
//    自适应自旋锁
//    自适应自旋锁等待线程空循环的自旋次数并非是固定的，而是会动态地根据实际情况来改变自旋等待的次数，自旋次数由前一次在同一个锁上的自旋时间及锁的拥有者的状态来决定。自适应自旋锁的大概原理是：
//
//    如果抢锁线程在同一个锁对象上之前成功获得过锁，jvm就会认为这次自旋很有可能再次成功，因此允许自旋等待持续相对更长的时间。
//
//    如果对于某个锁，抢锁线程很少成功获得过，那么jvm将可能减少自旋时间甚至省略自旋过程，以避免浪费处理器资源。
//
//    自适应自旋解决的是“锁竞争时间不确定”的问题。自适应自旋假定不同线程持有同一个锁对象的时间基本相当，竞争程度趋于稳定。总的思想是：根据上一次自旋的时间与结果调整下一次自旋的时间。
//
//    轻量级锁的膨胀
//    轻量级锁的本意是为了减少多线程进入操作系统底层的互斥锁的概率，并不是要替代操作系统互斥锁。所以，在争用激烈的场景下，轻量级锁会膨胀为基于操作系统内核互斥锁实现的重量级锁。
//
//    重量级锁
//            重量级锁的核心原理
//    重量级锁使用了监视器机制。JVM 中每个对象都会有一个监视器，监视器和对象一起创建、销毁。监视器保证同一时间只有一个线程可以访问被保护的临界区代码块。
//
//    本质上，监视器是一种同步工具，特点是：
//
//    同步。监视器保护的临界区代码互斥执行。
//    写作，监视器提供 Signal 机制，允许正持有许可的线程暂时放弃许可进入阻塞等待状态，等待其它线程发送 Signal 去唤醒。
//    在 Hotspot 虚拟机中，监视器是由 C++ 类 ObjectManger 实现的。
//
//    重量级锁的开销
//    重量级锁使用了 Linux 操作系统内核态系的互相锁，涉及用户态和核心态之间的切换，开销较大。

//    总结与对比
//    介绍了所有的锁，再回头看一下上一章节 synchronized 的执行过程，大致如下：
//
//    线程抢占锁时，首先检查对象中是否为偏向锁。
//    如果是偏向锁，则检查其中的线程 ID 是否为抢锁线程 ID，如果是，就表示抢锁线程处于偏向锁状态，可以快速获得锁。
//    如果线程 ID 并未指向抢锁线程，就通过 CAS 操作竞争锁。如果竞争成功，就将 Mark Word 中的线程ID设置为抢锁线程，偏向标志位设置为1，锁标志位设置为01，此时内置锁对象处于偏向锁状态。
//    如果CAS操作竞争失败，就说明发生了竞争，撤销偏向锁，升级为轻量级锁。
//    JVM 使用 CAS 将锁对象的 Mark Word 替换为抢锁线程的锁记录指针，如果成功，抢锁线程就获得锁。如果替换失败，就表示其它线程竞争锁。JVM 尝试使用 CAS 自旋替换抢锁线程的锁指针记录，如果自旋成功，那么锁对象依然处于轻量级锁状态。
//    如果JVM的CAS替换锁记录指针自旋失败，轻量级锁就膨胀为重量级锁，后面等待锁的线程也会进入阻塞状态。
//    三种锁的对比如下表：
//
//    锁	优点	缺点	适用场景
//    偏向锁	加锁和解锁不需要额外地消耗，和执行非同步方法比只存在纳秒级差距	如果线程间存在竞争，则会带来额外的锁撤销的消耗	适用于只有一个线程访问临界区的场景
//    轻量级锁	竞争的线程不会阻塞，提高了程序的响应速度	抢不到锁竞争的线程使用CAS自旋等待，会消耗CPU	锁占用时间很短，吞吐率低
//    重量级锁	线程竞争不适用自旋，不会消耗CPU	线程阻塞，响应时间缓慢	所占用时间较长，线程阻塞


//    作者：简单一点点
//    链接：https://www.jianshu.com/p/36408b110b57
//    来源：简书
//    著作权归作者所有。商业转载请联系作者获得授权，非商业转载请注明出处。


}
