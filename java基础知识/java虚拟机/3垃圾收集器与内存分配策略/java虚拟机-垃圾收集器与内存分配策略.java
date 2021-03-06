3 垃圾收集器与内存分配策略
3.1 概述
	垃圾收集GC
	Gc要完成的三件事情：
		哪些内存需要回收？
		什么时候回收？
		如何回收？
	程序技术器、虚拟机栈、本地方法栈三个区域随线程而生，随线程而灭。所以这几个区域内不需要过多考虑回收的问题，因为方法结束或线程结束时，内存自然就跟着回收。java堆和方法区就不一样，重点考虑这两个的内存情况。
3.2 对象已死？
	垃圾收集器在对堆进行回收前，第一件事情就是确定哪些对象已经死去（即不可能再被任何途径使用的对象）。
3.2.1 引用计数算法
	给对象中添加一个引用计数器，每当有一个地方引用它时，计数器就加1；当引用失效时，计数器值就减1；任何时刻计数器都为0的对象就是不可能再被使用。
	java语言中没有选用引用计数算法来管理内存，其中最主要的原因是它很难解决对象之间的互相循环引用的问题。
例子：
    class ReferenceCountingGc{
        public Object instance = null;
        private static final int _1MB = 1024*1024;
        private byte[] bigSize = new byte[2*_1MB];
    }
    @Test
    public void sortNumTest(){
        ReferenceCountingGc objA = new ReferenceCountingGc();
        ReferenceCountingGc objB = new ReferenceCountingGc();
        objA.instance = objB;
        objB.instance = objA;
        objA = null;
        objB = null;
    }
3.2.2 根搜索算法
	Java和C#都是使用根搜索算法，判定对象是否存活。
	通过一系列的命为“GC Roots”的对象作为起始点，从这些节点开始向下搜索，搜索锁走过的路劲称为引用链，当一个对象到GC Roots没有任何引用链相连时，则证明此对象是不可用是。用图论的话就是从GC Roots到这个对象不可达。
	在java中，可作为GC Roots的对象包括下面几种：
	虚拟机栈（栈帧中的本地变量表）中引用的对象
	方法区中的静态属性引用的对象
	方法区中的常量引用的对象
	本地方法栈中JNI（即一般说的Native方法）的引用的对象
3.2.3 再谈引用
	jdk1.2之后，java对引用的概念进行了扩充，分为强引用、软引用、弱引用、虚引用。这四种引用强度依次逐渐减弱。
	强引用：普遍存在，Object obj = new Objec()；这类的引用就是强引用，只要强引用还存在，垃圾收集器用于不会回收掉被引用的对象。
	软引用：一些还有用，但非必须的对象。对于软引用关联着的对象，在系统将要发生内存溢出异常之前，将会把这些对象列回收范围之中进行第二次回收。用SoftReference类实现。
	弱引用：被弱引用关联的对象只能生存到下一次垃圾收集发生之前。用WeakReference类来实现
	虚引用：一个对象是否有虚引用的存在完全不会对其他时间构成影响，也无法通过虚引用来取得一个对象实例。为一个对象设置虚引用关联的唯一目的就是希望能在这个对象被收集器回收时收到一个系统通知。通过PhantomReference类来实现。
3.2.4 生存还是死亡
	在根搜索算法不可达的对象，并非是必须回收的，至少还要经历两次标记过程，才能真正回收。
	过程：
		如果对象进行跟搜索算法没有与GC Roots相连接的引用链，那就会被第一次标记，并进行第一次筛选。筛选条件就是是否有finalize()方法，如果没有finalize()房啊或已经被执行过，虚拟机将这两种情况都视为没有必要执行。
		如果这个对像被判定有必要执行finalize()方法，那么这个对象会被放置在一个F—Queue的序列中，并由一个虚拟机建立的低优先级的Finalizer线程去执行。
		GC将对F-Queue中的对象进行第二次小规模的标记，如果对象在finalize()方法中，重新与引用链上的任何一个对象建立了关联，就不会被GC回收。
例子：
public class FinalizeTest {
    public static FinalizeTest SAVE_HOOK = null;
    public void isAlive(){
        System.out.println("我还活着");
    }

    @Override
    protected void finalize() throws Throwable {
        super.finalize();
        System.out.println("finalize方法被执行");
        //给SAVE_HOOK赋予了新的引用，所以不用背gc回收了
        FinalizeTest.SAVE_HOOK = this;
    }

    public static void main(String[] args) throws InterruptedException {
        SAVE_HOOK = new FinalizeTest();
        //等于null就是告诉gc可以回收这个对象了
        SAVE_HOOK = null;
        System.gc();
        Thread.sleep(500);//给一点时间让gc执行
        if(SAVE_HOOK != null){
            SAVE_HOOK.isAlive();
        }else{
            System.out.println("我已经死了");
        }
		//和上面一样的代码
        SAVE_HOOK = null;
        System.gc();
        Thread.sleep(500);//给一点时间让gc执行
        if(SAVE_HOOK != null){
            SAVE_HOOK.isAlive();
        }else{
            System.out.println("我已经死了");
        }
    }
}
输出结果：
	finalize方法被执行
	我还活着
	我已经死了
	注意，第一次还活着，第二次却死了，这是因为任何一个对象的fnalize()方法都只会被系统自动调用一次，如果对象面临下一次回收，它的finalize()方法将不会再执行。
	运行这个方法代价好昂，不确定性大，无法保证各个对象的调用的顺序。
	finalize()能做的所有工作，使用try-finally或其他方式都可以做的更好，更及时，所以不推荐用这个方法！
3.2.5 回收方法区
	在方法区垃圾收集效率很低。
	方法区垃圾收集主要分为两个部分：废弃常量和无用的类。
	回收废弃常量比较简单，和java堆回收类似。以一个字符串“abc”为例，如果此时没有任何String对象引用常量池中的“abc”常量，这时发生内存回收的，且必要的话就会回收这个常量
	无用的类回收比较麻烦！要满足三个条件
		该类所有的实例都已经被回收，也就是java堆中不存在该类的任何实例。
		加载该类的ClassLoader已经被回收
		该类对应的java.lang.Class对象没有任何地方被引用，无法再任何地方通过反射访问该类的方法。
	虚拟机对满足上诉三个条件的无用类进行回收，仅仅是可以回收，而不是一定会回收。
	是否对类进行回收，HotSpot虚拟机提供了 -Xnoclassgc参数进行控制，还可以使用 -verbose:class 及 -XX:+TraceClassLoading、-XX：+TraceClassUnLoading查看类的加载和卸载信息。-verbose:class和-XX:+TraceClassLoading可以在Product版的虚拟机中使用，但是-XX:+TraceClassLoading参数需要fastdebug版的虚拟机支持。
3.3 垃圾收集算法
	垃圾回收算法设计大量的程序细节，此处不细说，只是介绍几种算法的思想及发展过程！
	老年代：这块内存区域里的大部分对象不会被回收（即这里面的对象不容易死亡）
	新生代：这块内存区域里的大部分对象会被回收（即这里面的对象容易死亡）
3.3.1 标记-清楚算法
	最基础的算法：标记清楚算法！
	分为标记和清除两个阶段：
		首先标记处需要回收的对象
		然后统一回收掉所有被标记的对象。
	缺点：
		效率问题：效率低，
		空间问题：会产生大量不连续的内存碎片。空间碎片太多可能会导致，当程序在以后的运行过程中需要分配较大对象时无法找到足够的连续内存而不得不提前触发另一次垃圾收集动作。
3.3.2 复制算法
	他将可用内存按容量划分为大小相等的两块，每次只使用其中的一块，当这一块的内存用完了，就将还存活着的对象赋值到另外一块上面，然后再把已经使用过的内存一次性清理掉。
	缺点：内存缩小到原来的一半。
	现代的商业虚拟机，都是采用复制算法回收新生代。IBM专门研究表明，新生代中的对象98%是朝生夕死的，所以不需要按1:1的比例分配内存空间，而是将内存分为一块较大的Eden空间和两块较小的Survivor空间，每次使用Eden和其中的一块Survivor。回收时，将Eden和Survivor中还存活的对象一次性考到另外一块Survivor空间上，最后清理掉Eden和刚才用过的Survivor空间。
3.3.3 标记整理算法
	根据老年代的特点，提出了标记整理算法。
	两步骤
		先标记可回收对象
		然后让所有存活的对象都向一端移动，然后直接清理掉段边界以外的内存。
3.3.4 分代收集算法
	根据对象的存活周期的不同划分为几块。一般分为新生代和老年代，这样就可以根据各个年代的特点采用最适当的收集算法。
3.4 垃圾收集器
	不同的虚拟机有不同的收集器。
	垃圾算法是内存回收的方法论，垃圾收集器就是内存回收的具体实现。
	这里讨论的是基于Sun HotSpot虚拟机1.6版Update22
	如果两个收集器之间存在连线，就说明他们可以搭配使用
3.4.1 Serial收集器
	Serial收集器是最基本、历史最悠久的收集器，曾经（在JDK1.3.1之前）的虚拟机新生代收集的唯一选择。这个收集器是一个单线程收集器。且运行的时候会暂停其他正常工作的所有线程。
	实际上，到目前为止，它依然是虚拟机在client模式下的默认新生代收集器。
	优点：简单而高效。
3.4.2 ParNew收集器
	其实就是Serial收集器的多线程版本。目前只有它能与CMS收集器配合工作。
3.4.3 Parallel Scavenge收集器
	也是新生代收集器，使用复制算法，又是并行多线程收集器。
	特点：达到一个可控制的吞吐量（所谓吞吐量就是CPU用于运行用户代码的时间与CPU总消耗时间的比值）。
	停顿时间越短就越适合需要与用户交互的程序，良好的相应速度能提升用户的体验。而高吞吐量则可以高效率的利用CPU时间，尽快地完成程序的运算任务，主要适合在后台运算而不需要太多交互任务。
3.4.4 Serial Old收集器
	Serial Old是Serial收集器的老年代版本。
	单线程收集器，使用标记整理算法
3.4.5 Parallel Old收集器
	是Parallel Scavenge收集器的老年代版本，使用多线程和标记整理算法。
3.4.6 CMS收集器
	是一种以获取最短回收停顿时间为目标的收集器。基于标记-清除算法。
	四个步骤
		初始标记
		并发标记
		重新标记
		并发清除
		初始标记和重新标记这两个步骤，需要停止其他进程。初始标记仅仅只是标记一下GC Roots能直接关联到的对象，速度很快。
		并发标记阶段就是进行GCRoots Tracing的过程，而重新标记阶段是为了修正并发标记期间，因用户程序继续运作而导致标记产生变动的那一部分对象的标记记录。
	优点：并发收集，低停顿
	缺点：
		CMS收集器对CPU资源非常敏感
		CMS收集器无法处理浮动垃圾
		收集结束产生大量空间碎片
3.4.7  G1收集器
	基于标记整理算法的收集器
它可以非常精确的控制停顿，可以指定一段m时间内，垃圾收集上消耗的时间不超过n毫秒。
G1将整个java堆划分为多个大小固定的独立区域，并且跟踪这些区域里面的垃圾堆积程度，在后台维护一个优先列表，每次根据允许的收集时间，优先回收垃圾最多的区域。

3.4.8 垃圾收集器参数总结
	
3.5 内存分配与回收策略
	java技术体系中提倡的自动内存管理最终可以归结为自动化地解决了两个问题：给对象分配内存以及回收分配给对象的内存。
	介绍一下Minor GC和Full GC
	新生代GC:(Minor GC):指发生在新生代的垃圾收集动作，因为Java对象大多都具备朝生夕灭的特性，所以Minor GC非常频繁，一般回收速度也比较快。
	老年代GC(Major GC/Full GC):指发生在老年代的GC，出现了Major GC，经常会伴随至少一次的Minor GC(但非绝对的)，Major GC的速度一般比Minor GC慢10倍以上。
3.5.1 对象优先在Eden分配
	大多数情况下，对象再新生代Eden区中分配。当Eden区没有足够的空间进行分配时，虚拟机将发起一次Minor GC
3.5.2 大对象直接进入老年代
	大对象就是需要连续内存空间的java对象
	-XX:PretenureSizeThreashold参数，大于这个值的对象直接存老年代中。这样做是避免在Eden区及两个Survivor区之间发生大量的内存拷贝。
	PretenureSizeThreashold参数只对Serial和ParNew两款收集器有效。
3.5.3 长期存活的对象存入老年代
	为了识别哪些对象放新生代哪些放老年代，定义了一个对象年龄计数器。经历了一次Minor GC就加1，当大于等于15时，就是老年代。
3.5.4 动态对象年龄判定
	并不会死板的判断15年龄是老年代，如果在Survivor空间中相同年龄所有对象大小的总和大于Survior空间的一般，年龄大于或等于该年龄的对象就可以直接进入老年代。
3.5.5 空间分配担保
	发生Minor GC时，虚拟机会检测之前每次晋升到老年代的平均大小是否大于老年代的剩余空间大小，如果大于，则改为直接进行一次Full GC，如果小于，则查看HandlePromotionFailure设置是否允许担保失败：如果允许，就进行Minor GC；如果不允许，则也要改为一次Full GC。




















