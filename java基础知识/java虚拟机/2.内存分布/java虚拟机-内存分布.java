读《深入理解java虚拟机》笔记
1 走进java
1.1 概述
	java的理想：一次编写，到处运行
1.2 Java技术体系
	Sun官方定义java技术体系包括了以下几个部分：java程序设计语言、各种硬件平台上的虚拟机、Class文件格式、Java API类库、来自商业机构和开源社区的第三方类库。
	我们把java程序设计语言、Java虚拟机、Java API类这三部分统称为JDK（Java Development Kit）。JDK是用于支持Java程序开发的最小环境
	把Java API 类库中的Java SE API子集和Java虚拟机称为JRE（Java Runtime Evironment）。JRE是支持Java程序运行的环境。
1.4.5 64位虚拟机
	运行在64位系统上的java应用需要消耗更多的内存，通常比32为系统额外增加10%~30%。原因的指针膨胀和各种数据类型对齐补白的原因。

2 自动内存管理机制
2.2 运行时数据区域
	Java虚拟机在执行java程序的时候会把它所管理的内存划分为若干个不同的数据区域。
2.2.1 程序计数器
	定义：程序计数器是一块较小的内存空间，它的作用可以看做是当前线程执行的虚拟机字节码的行号指示器。
	各个线程都有各自的程序计数器，且互不影响，我们称这类内存区域为"线程私有"内存。
	如果正在执行的是Native方法即本地方法，这个计数器则为空。原因是因为此时会再开一个线程去执行native方法，新线程的程序计数器是null，旧线程的程序计数器还是自己原来的那个计数器，且旧线程处于阻塞状态，等新线程执行完毕。
	这个内存区是唯一在Java虚拟机规范中没有规定任何OutOfMemoryError（内存溢出）情况的区域，因为存的数据实在太小。
	(java中native修饰的方法都是本地方法，即与平台底层交互的方法，其变量的存储直接存在本地方法栈中）
2.2.2 Java虚拟机栈
	定义：虚拟机栈描述的是Java方法执行的内存模型。
	Java虚拟机栈也是线程私有的，生命周期与线程相同。
	每个方法被执行的时候会创建一个栈帧，，用于存储局部变量、操作栈、动态链接、方法出口等信息。
	每一个方法被调用直至执行完成的过程，就对应着一个栈帧在虚拟机栈中从入栈到出栈的过程。
	我们这里说的栈就是虚拟机栈中的局部变量表部分。
	局部变量表存放了编译期可知的各种基本数据类型（Boolean，byte，char，short，int，long，float，double即java八个基本数据类型） 、对象引用和returnAddress类型。
	其中64位长度的long和double类型数据会占用2个局部变量空间，其余的数据类型只占用一个。
	局部变量表所需的内存空间在编译期间完成分配。在方法运行期间不会修改局部变量表的大小。
	定义了两种异常情况
		异常1：StackOverflowError：线程请求的栈深度大于虚拟机允许的深度
		异常2：OutOfMemoryError：如果扩展时无法申请到足够的内存时会抛出该异常。
2.2.3 本地方法栈
	发挥的作用和Java虚拟机栈类似。区别在于虚拟机栈为虚拟机执行java方法服务，而本地方法栈是为虚拟机使用到的Native方法服务。也会抛出同样的两个异常：StackOverflowError和OutOfMemoryError。
2.2.4 Java堆
	对大多数应用来说。java堆是虚拟机管理内存中最大的一块。
	java堆是被所有线程共享的一块区域，在虚拟机启动时创建。
	这块区域的唯一目的就是存放对象实例。
	Java堆是垃圾收集器管理的主要区域。
	Java堆可以处于物理上不连续，逻辑上连续的内存空间。
2.2.5 方法区
	是各个线程的共享区域。
	它用于存储已经被虚拟机加载的类信息、常量、静态变量（jdk 1.7之前），即时编译器编译后的代码等数据。
	和java堆一样不需要连续的内存和可以选择固定大小而且可扩展。
	可以选择不实现垃圾收集。即垃圾收集行为在这个区域比较少出现。
	很多人称方法区为“永久代”，其实不准确。
	这个区域的内存回收目标主要是正对常量池的回收和堆类型的卸载。
	当方法区无法满足内存分配需求时，抛出OutOfMemoryError异常。
2.2.6 运行时常量池
	是方法区的一部分。Class文件有常量池信息，用于存放编译期生成的各种字面量和符号引用，这部分内容是在类加载后放到方法区的运行常量池中。
	一般来说，处理保存Class文件中描述的符号引用外，还会把翻译出来的直接引用也存储在运行时常量池中。
	具备动态性，可以在运行期间将新的常量放入池中。这种特性用的最多的就是String的intern方法。
	当常量池无法申请到内存时，抛出OutOfMemoryError异常。
2.2.7 直接内存
	直接内存并不是虚拟机运行时数据区的一部分，也不是java虚拟机规范中定义的内存区域，但是被频繁的使用，也会导致OutOfMemoryError异常。
	就是通过IO的方式，使用Native函数知己分配堆外内存，然后通过一个存储在java堆里面的DirectByteBuffer对象作为这块内存的引用进行操作。
	不占java堆大小，但是肯定占本子内存大小
	最常见的就是NIO 的直接缓冲区和普通缓冲区，直接缓冲区的数据就是存在直接内存中。普通缓冲区的值就存在堆内存中，
2.3 对象访问
	Object obj = new Object();
	“Object obj”会反映到Java栈的本地变量表中，作为一个reference类型数据出现。而“new Object()”这部分语义将会反映到Java堆中。在Java堆中必须包含能查到此对象类型数据的地址信息，这些数据则存储在方法区中。
	reference类型在java虚拟机规范中只规定了一个指向对象的引用，所以实现方式有两种
		使用句柄：Java堆中会划分出一块内存来作为句柄池，reference中存储的就是对象的句柄地址，句柄中包含了对象实例数据和类型数据各自的具体类型地址。
		使用指针：java堆对象中就必须考虑如何放置访问类型数据的相关信息。reference中直接存储的就是对象地址。
		优缺点：
			句柄的好处：reference中存储的是稳定的句柄地址，在对象被移动（垃圾回收时会经常移动对象，即重排序）时只会改变句柄中的实例数据指针。而reference本身不需要被改变
			指针的好处：速度更快，节省了一次指针定位的时间开销。
	程序计数器、虚拟机栈、本地方法栈三个区域随线程而生，随线程而灭。
2.4 实战:OutMemoryError（OOM）异常
2.4.1 Java堆溢出
	出现堆内存溢出，异常堆栈信息：
	java.lang.OutOfMemoryError:Java heap space...
	遇到内存溢出，先要分析是内存泄露还是内存溢出
	如果是内存泄露，查看泄露对象到GC Roots的引用链。（内存泄露就是对象已经不用了，可以被回收了，但是没有及时被回收）
例子：
public class OutOfMemoryErrorTest {
    List resultList = new ArrayList<>();
    @Test
    public void heapException(){
        int i = 1;
        while(true){
            byte[] b = new byte[1024*100];、//100K一次
            resultList.add(b);
            System.out.println(String.format("打印了%s",i));
            i++;
        }
    }
}
报错：java.lang.OutOfMemoryError: GC overhead limit exceeded。
如果程序的百分之98的时间是GC操作，而回收的内存小于百分之2，就会报这个错。
换个容量大一点的byte，改成100M一次就成功了
public class OutOfMemoryErrorTest {
    List resultList = new ArrayList<>();
    @Test
    public void heapException(){
        int i = 1;
        while(true){
            byte[] b = new byte[1024*1024*100];//100M一次
            resultList.add(b);
            System.out.println(String.format("打印了%s",i));
            i++;
        }
    }
}
报错：java.lang.OutOfMemoryError: Java heap space
注意：java堆内存放的是成员变量，局部变量是在虚拟机栈里的！
2.4.2 虚拟机栈和本地方法栈溢出
	会抛出的两个异常：
		StackOverflowError异常：如果线程请求深度大于虚拟机所允许的最大深度，抛出该异常
			异常信息：Exception in thread “main” java.lang.StackOverflowError
		OutOfMemoryError异常：虚拟机在扩展栈时无法申请足够的内存空间。
			异常信息：Exception in thread “main” java.lang.OutOfMemoryError:unablw to create new native thread
	-Xoss 参数（设置本地方法栈大小）虽然存在，但实际上是无效的。栈容量只由-Xss 参数设定。
	-Xss 参数设置栈内存容量
	单个线程下，无论是由于栈帧太大，还是虚拟机容量太小，当内存无法分配的时候，虚拟机都抛出StackOverflowError异常。
例子：
	@Test
    public  void jVMStackErrorTest() throws InterruptedException {
        Thread thread = new Thread(new JVMStackError());
        thread.start();
        thread.join();
    }
    class JVMStackError implements Runnable{
        int i = 1;
        void foreachThread(){
            System.out.println(String.format("重复调用%s次此方法",i));
            i++;
            foreachThread();
        }
        @Override
        public void run() {
            foreachThread();
        }
    }
报错：Exception in thread "Thread-0" java.lang.StackOverflowError
	推导内存：譬如32为Windows限制是2GB，虚拟机提供了参数来控制java对内存和方法区这两部分内存的最大值。剩余的内存2GB，减去Xmx（最大堆容量），再减去MaxPermSize（最大方法区容量），程序计数器消耗内存很小，可以忽略掉。如果虚拟机进程本身消耗的内存不计算在内，剩下的内存就由虚拟机栈和本地方法栈“瓜分”。
	如果是建立多线程导致的内存溢出，再不拿减少线程数或更换64位虚拟机的情况下。就只能通过最大堆和减少栈同理来换取更多的线程。
例子：
@Test
    public  void createThreadErrorTest() throws InterruptedException {
        int i = 1;
        while(true){
            Thread thread = new Thread(new CreateThreadError(i));
            thread.start();
            i++;
        }
    }
    class CreateThreadError implements Runnable{
        int i ;
        public CreateThreadError(int i) {
            this.i = i;
        }
        @Override
        public void run() {
            System.out.println(String.format("第%s个线程",i));
        }
    }
注意：因为java线程的映射到操作系统的内核线程上，所以又很大风险会造成死机。所以又兴趣可以试试！

2.4.3 运行时常量池溢出
	String.intern()这个Native方法：如果池中已经包含了一个等于此String对象的字符串，则返回发表池中这个字符串的String对象，否则，将此String对象包含的字符串添加到常量池中，并且返回String对象的引用。
	-XX：PermSize和-XX:MaxPermSize限制方法区的大小，从而限制常量池的容量。因为常量池在方法区中。
	异常信息：
		Exception in thread “main” java.lang.OutOfMemoryError: PerGen space，这就说明报错是常量池内存溢出。
例子：
 @Test
    public void constantPoolError(){
        List resultList = new ArrayList<>();
        long l = 1;
        StringBuilder sbf = new StringBuilder("1");
        while(true){
            resultList.add(sbf);
            /*循环一次是10M大小*/
            for (int i = 0; i < 1024*1024*100; i++) {
                sbf.append("1");
            }
            String.valueOf(sbf).intern();
            System.out.println(l++ + sbf.toString());
        }
    }
记得先把常量池大小设置小一点，不然java堆内存就先爆了
2.4.4 方法区溢出
	方法区存放Class的相关信息，如类名，访问修饰符，常量池，字段描述、方法描述等。
	方法就是通过反射技术持续动态生成class文件，
	在经常动态生成大量Class的应用中，需要特别注意类的回收状况
2.4.5 本机直接内存溢出
	可以通过-XX:MaxDirectMemorySize指定，如果不指定，则默认与java对的最大值（-Xmx）一样。








