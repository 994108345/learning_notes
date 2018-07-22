8 虚拟机字节码执行引擎
代码编译是从本地机器码转变为字节码。
8.1 概述
	执行引擎是java虚拟机最核心的组成部分之一。
	虚拟机是一个相对于物理机的概念。
	物理机的执行引擎是直接建立在处理器、硬件、指令集和操作系统层面上，虚拟机的执行引擎则是由自己实现。
	不同虚拟机实现里面，执行引擎在执行java代码的时候可能有解释执行和编译执行两种选择。
	执行引擎都是一致的：输入的是字节码文件，处理过程是字节码解析的等效过程，输出的是执行结果。
8.2 运行时栈帧结构
	栈帧是用于支持虚拟机进行方法调用和方法执行的数据结构，它是虚拟机运行时数据区中的虚拟机栈的栈元素。
	栈帧存储了方法的局部变量表、操作数栈、动态连接和方法返回地址等信息。
	一个方法的调用开始到结束就对应一个栈帧在虚拟机栈入栈到出栈的过程。
	一个栈帧的分配内存不会受到程序运行期变量数据的影响。仅仅取决于具体的虚拟机实现。
8.2.1 局部变量表
	定义：局部变量表是一组变量值存储空间，用于存放方法参数和方法内部定义的局部变量。
	在Java程序编译的时候，就在方法的Code属性的max_locals数据项中确定了该方法所需要分配的最大局部变量表的容量
	局部变量表的容量以变量槽（下面简称Slot）为最小单位，没有明确规定其大小。
	一个Slot，可以存放一个32位以内的数据类型。java中占用32位以内的数据类型有boolean、byte、char、short、int、float、reference和returnAddress八种类型。其中regenence是对象的引用，可以根据此查到对象再堆内存中所有和方法区的类型索引。returnAddress是为字节码指令jsr、jsr_w、ret服务的,指向了一条字节码指令的地址。
	对于54位的数据类型，就分配两个连续的Slot空间。java明确规定的64位的数据类型就long和double两种，用两个连续的Slot表示64，因为是线程私有的数据，所以两个连续的Slot都是原子操作，不会引起线程安全问题。
	局部变量中是Slot的可重用的。见例子：
	注意：通过cmd去敲指令运行类文件，注意运行的时候要加上参数 java -verbose:gc 类文件名，这样才会打印gc回收情况。
public static void main(String[] args) {
       byte[] b = new byte[644*1024*1024];
       System.gc();
}
 输出结果：
 [GC 660432K->660008K(846336K), 0.0024178 secs]
 [Full GC 660008K->659927K(846336K), 0.0261979 secs]
看例子可知，gc并没有回收局部变量。原因只有方法结束了，才会回收局部变量表，而gc在方法中，即在方法结束前执行，所以不会回收该方法的局部变量。
我们继续改进：
public static void main(String[] args) {
     {
         byte[] b = new byte[644 * 1024 * 1024];
     }
     System.gc();
}
输出结果：
[GC 660432K->660056K(846336K), 0.0021121 secs]
[Full GC 660056K->659927K(846336K), 0.0304845 secs]
看输出结果可知
看例子可知，我们将参数的使用域缩小，当出域时，此时已经没有对该属性的任何引用，应该可以被gc回收，但是看输出结果可知，还是没有被回收。原因是虽然出了作用域，但是b所占用的Slot还没有被其他变量所复用，所以作为GC Roots一部分的局部变量表仍然保持对它的关联，所以不会被回收。

public static void main(String[] args) {
	{
		byte[] b = new byte[644 * 1024 * 1024];
	}
	int a = 0;
	System.gc();
}
输出结果：
[GC 660432K->660008K(846336K), 0.0024151 secs]
Full GC 660008K->471K(846336K), 0.0212659 secs]
看结果可知，gc回收了该属性。原因是，在代码离开了b属性的作用域时，有a属性去占用b原来所占用的Slot，所以此时b并没有存在与GC Roots的关联，在gc时，就可以被回收。
通常我们也会通过设置null值去达到消除属性与GC Roots之间的关联，但是不优雅，还是建议优先使用作用域来控制！

类变量有两次赋初始值的过程，第一是准备阶段的系统初始零值，第二是初始化阶段赋予程序员定义的值。但是局部变量没有赋初始值是不能用的！

8.2.2 操作数栈
	定义：就是一个后入先出的栈。在编译的时候，将最大深度写入Code属性的max_stacks数据项之中。
	操作数栈是在方法运行期间进行写入和提取内容。例如：算术运算就是通过操作数栈执行的！
	两个栈帧的部分局部变量表重叠在一起，这样可以在方法调用时，就可以共用同一部分数据，无须进行额外的参数复制传递。
8.2.3 动态连接
	每个栈帧都包含一个指向运行时常量池中栈帧所属方法的引用，持有这个引用是为了支持方法调用过程中的动态连接。
8.2.4 方法返回地址
	方法执行后，有两种方式退出这个方法、
		1 执行引擎遇到任何一个方法返回的字节码指令。有返回值就带着返回值传递给上层的方法调用者。这种称为正常完成出口。
		2 在执行中遇到了异常，并且异常没有在方法体中得到处理，不会给上层调用者返回任何返回值，这称为异常完成出口。
	无论哪种返回方式，都要返回到方法被调用的位置。
		正常返回时，调用者的PC计数器的值就可以作为返回地址，栈帧中可能会保存这个计数器值。
		异常返回时，返回地址是通过异常处理器表来确定的，栈帧一般不会保存这部分信息。
	方法退出的过程实际等于与把当前栈帧出栈。
8.2.5 附加信息
	虚拟机规范允许增加一些规范里没有描述的信息到栈帧之中。实际开发中，一般会把动态连接，方法返回地址与附加信息全部归为一类，称为栈帧信息。
8.3 方法调用
	方法调用并不等同于方法执行，方法调用唯一的任务就是确定调用哪一个方法，不涉及方法内部发具体运行过程。Class文件里面存储的都是符号引用，不包含直接引用，只有在加载期间才能确定目标方法的直接引用。
8.3.1 解析
	定义：调用目标程序代码写好、编译器进行编译时就必须确定下来。这类方法的调用称为解析。
	在java中，符合“编译器可知，运行期不可变”这个要求的方法主要有静态方法和私有方法两大类。因此他们适合在加载阶段进行解析。
	java虚拟机中提供了四条方法调用字节码指令，分别是：
		invokestatic:调用静态方法
		invokespecial：调用实例构造器<init>方法、私有方法和父类方法。
		invokevirtual：调用所有的虚方法。
		invokeeinterface：调用接口方法，会在运行时再确定一个实现此接口的对象。
	只要能被invokestatic和invokespecial指令调用的方法，都可以在解析阶段确定唯一的调用版本，符合这个条件的有静态方法、私有方法、实例构造器和父类方法。称为虚方法。与之相反，其他就是虚方法，其中final是使用invokevirtual来执行的，但是由于它无法被覆盖。所以它也是非虚方法。
	解析调用一定的个静态过程！
8.3.2 分派
	分派调用可能静态也可能动态调用。
	1.静态分派
用一个例子来说明：
public class StaticDispatch {
    static abstract class Human{}
    static class Man extends Human{}
    static class Women extends Human{}
    public void sayHello(Human guy){
        System.out.println("hello guy!");
    }
    public void sayHello(Man guy){
        System.out.println("hello gentlemen!");
    }
    public void sayHello(Women guy){
        System.out.println("hello lady!");
    }

    public static void main(String[] args) {
        Human man = new Man();
        Human women = new Women();
        StaticDispatch sr = new StaticDispatch();
        sr.sayHello(man);
        sr.sayHello(women);
    }
}
输出结果：
hello guy!
hello guy!
		我们来分析一下重载的过程。
		Human man = new Man();
		代码中的Human，我们称为静态类型或者外观类型，后面的Man我们称为变量的实际类型。
		静态类型和实际类型在程序中都可以发生一些变化。
		区别：
			静态类型的变化仅发生在使用时发生，变量本身的静态变量不会改变，并且静态类型是在编译期可知。
			实际类型变化的结果在运行期才可确定，编译器在编译程序的时候并不知道一个对象的实际类型是什么！
		虚拟机重载时是通过参数的静态类型而不是实际类型作为判定依据。所以在编译阶段，javac编译器就根据参数的静态类型决定使用哪个重载版本。
	所有依赖静态类型来定位方法执行版本的分派动作，都称为静态分派。静态分派的最典型应用就是方法重载。
我们看一个重载的例子：
public class Overload {
    public static void sayHello(Object arg){
        System.out.println("hello Object");
    }
    public static void sayHello(int arg){
        System.out.println("hello int");
    }
    public static void sayHello(long arg){
        System.out.println("hello long");
    }
    public static void sayHello(Character arg){
        System.out.println("hello Character");
    }
    public static void sayHello(char arg){
        System.out.println("hello char");
    }
    public static void sayHello(char... arg){
        System.out.println("hello char...");
    }
    public static void sayHello(Serializable arg){
        System.out.println("hello Serializable");
    }

    public static void main(String[] args) {
        sayHello('a');
    }
}
	如果我们注释掉sayHello(char arg)，输出会变成hello int
	这是发生一次自动类型转换，'a'除了可以代表一个字符串外，还可以代表数字65（字符‘a’的Unicode数值为十进制数字65）
	如果我们继续注释掉sayHello(int arg)输出会变成hello long
	以此类推：按照char-int-long-float-double的顺序进行匹配，但是不会匹配到byte和short类型，因为char到byte货short的转型是不安全的。

	2 动态分派
		动态分配和重写有很紧密的关联。
看一个例子来分析：
public class DynamicDispatch {
    static abstract class Human{
        protected abstract void sayHello();
    }
    static  class Man extends Human{
        @Override
        protected void sayHello() {
            System.out.println("man say hello");
        }
    }
    static class Women extends Human{

        @Override
        protected void sayHello() {
            System.out.println("women say hello");
        }
    }

    public static void main(String[] args) {
        Human man = new Man();
        Human women = new Women();
        man.sayHello();
        women.sayHello();
        man = new Women();
        man.sayHello();
    }
}
运行结果：
man say hello
women say hello
women say hello
使用javap -verbose ClassTest查看字节码
 public static void main(java.lang.String[]);
    flags: ACC_PUBLIC, ACC_STATIC
    Code:
      stack=2, locals=3, args_size=1
         0: new           #2                  // class ClassTest$Man
         3: dup
         4: invokespecial #3                  // Method ClassTest$Man."<init>":()V
         7: astore_1
         8: new           #4                  // class ClassTest$Women
        11: dup
        12: invokespecial #5                  // Method ClassTest$Women."<init>":()V
        15: astore_2
        16: aload_1
        17: invokevirtual #6                  // Method ClassTest$Human.sayHello:()V
        20: aload_2
        21: invokevirtual #6                  // Method ClassTest$Human.sayHello:()V
        24: new           #4                  // class ClassTest$Women
        27: dup
        28: invokespecial #5                  // Method ClassTest$Women."<init>":()V
        31: astore_1
        32: aload_1
        33: invokevirtual #6                  // Method ClassTest$Human.sayHello:()V
        36: return
0~15行的准备工作，简历man和women的内存空间，对应
Human man = new Man();Human women = new Women();
这两句。接下来的16~21是关键。16~20创建了两个对象的引用压到栈顶，这两个对象是要执行sayHello方法的所有者，称为接受者，17~21是方法调用指令。仔细看17和21都是调用指令invokevirtual,但是最终执行目标方法不相同，原因主要是因为incokevirtual指令的多态查找过程开始说起。
	inbokevirtual指令的运行过程
		1) 找到操作栈顶的第一个元素所指向的对象的实际类型，记做C。
		2) 如果在类型C找到与常量中的描述符和简单名称都相符的方法，则进行访问权限校验，如果通过则返回这个方法的直接引用，查找过程结束：不通过则返回java.lang.IllegalAccessError异常。
		3) 否则，按几次关系从上往下依次对父类进行第2步的搜索和验证
		4) 如果始终没有找到合适的方法，则抛出java.lang.AbstractMethodError异常。
		由于invokevirtual指令执行的第一步就是在运行期间确定接受者的实际类型，所以两次调用中的invokevirtual指令把常量池中的类方法符号引用解析到了不同的直接引用上，这个过程就是java语言中方法重写的本质。我们把这种在运行期根据实际类型确定方法执行版本的分派过程称为动态分派。
	3.单分派与多分派
		定义：根据分派基于多少种宗量，可以将分派划分为单分派和多分派。
		宗量定义：方法的接受者与方法的参数统称为方法的宗量。
具体看实例：
public class Dispatch {
    static class QQ{}
    static class _360{}
    public static class Father{
        public void hardChoice(QQ arg){
            System.out.println("father chose qq");
        }
        public void hardChoice(_360 arg){
            System.out.println("father chose 360");
        }
    }
    public static class Son extends Father{
        public void hardChoice(QQ arg){
            System.out.println("son chose qq");
        }
        public void hardChoice(_360 arg){
            System.out.println("son chose 360");
        }
    }

    public static void main(String[] args) {
        Father father = new Father();
        Father son = new Son();
        father.hardChoice(new _360());
        son.hardChoice(new QQ());
    }
}
输出结果：
father chose 360
son chose qq
首先看编译阶段的选择过程，在静态分派中，选择目标方法的的依据有两点：1是静态类型是Fathre还是Son，2是方法参数是QQ还是360。因为是根据两个宗量进行选择，所以静态分派属于多分派类型。
再看看运行阶段虚拟机的选择，即动态分派过程、在执行son.hardChoice(new QQ())代码时，只有实际类型是Father还是Son会影响该方法的选择，只有一个宗量作为选择依据，所以Java语言的动态分派属于单分派类型。

	 4 虚拟机动态分派的实现
	 由于动态分派是非常频繁的动作，而且动态分批的方法版本选择过程要运行时在类的方法元数据中搜索合适的目标方法，因此基于性能的考虑，会在方法区中建立一个虚方法表替代频繁的搜索。

	8.4 基于栈的字节码解释执行引擎
		许多虚拟机的执行引擎在执行java代码的时候都有解释执行和编译执行两种选择
	8.4.1 解释执行
	8.4.2 基于栈的指令集与基于寄存器的指令集
		基于栈的指令集：依赖操作数栈进行工作
		基于寄存器指令集：依赖寄存器进行工作
		基于栈的指令集
			优点：可移植性
			缺点：执行速度稍慢一些。
		基于寄存器的指令集
			缺点；寄存器由硬件直接提供，程序直接依赖这些硬件。

	8.4.3 基于栈的解释器执行过程
看最简单的例子 四则运算！
public int calc(){
        int a = 100;
        int b = 200 ;
        int c = 300;
        return (a + b) * c;
    }
字节码：
 public int calc();
    flags: ACC_PUBLIC
    Code:
      stack=2, locals=4, args_size=1
         0: bipush        100
         2: istore_1
         3: sipush        200
         6: istore_2
         7: sipush        300
        10: istore_3
        11: iload_1
        12: iload_2
        13: iadd
        14: iload_3
        15: imul
        16: ireturn
      LineNumberTable:
        line 3: 0
        line 4: 3
        line 5: 7
        line 6: 11
分析步骤
	执行偏移地址为0的指令：bipush指令的作用的将单字节的整型常量值（-128~127）推入操作数栈顶
	执行偏移地址为1的指令：istore_1，将操作数栈顶的整型值出栈并存放到第一个局部变量Slot中。3~10都做着同样的事情。
	执行偏移地址为11的指令：iload_1将全局变量表第一个Slot中的整型值赋值到操作数栈顶
	执行偏移地址为12的指令：iload_2与iload_1类似。把第二个Slot的整型值入栈
	执行偏移地址为13的指令：iadd将操作数栈中前两个栈顶元素出栈，做整型加法，然后重新入栈。
	执行偏移地址为14的指令：iload_3把放在第三个局部变量Slot中的300入栈到操作数栈中。imul是将操作数栈中前两个栈顶元素出栈，做整型乘法，然后把结果重新传入栈。
	执行偏移地址为16的指令：ireturn是方法返回的指令之一，它将结果执行并将操作栈顶的整型值返回给辞方法的掉用者。到此为止，方法结束
	上面的过程仅仅是一个概念模型，虚拟机最终会对执行过程做出一些优化来提高性能，实际的运行过程不一定完全符合概念模型的描述。。














