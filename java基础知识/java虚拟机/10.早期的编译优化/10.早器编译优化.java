10 早期编译期优化
	从计算机出现的第一天起，对效率的追逐就是程序天生的坚定信仰！
10.1 概述
	java语言的编译期是一段不确定的操作过程，一般分为三种
	 1 前端编译器，即将*.java文件转变成*.class文件的过程。
	 2 虚拟机的后端运行编译器，把字节码转变成机器码的过程。
	 3 静态提前比那一起，直接把*.java文件编译成本地机器代码的过程。
	 我们主要讨论第一种前端编译器

10.2 前端编译器javaC
	java自带的javac就是最常见的前端编译器。该编译器大致可以分为三个过程
		1 解析与填充符号表
		2 插入式注解处理器的注解处理过程
		3 语义分析与字节码生成过程
	10.2.2 解析与符号填充表
		解析步骤分为词法分析和语法分析
			1 词法分析
				定义：词法分析是将源代码字符流转变成标记集合。
				一般编译过程中，关键字、变量名、字面量和运算符都可以当做标记
				例如：int a = b + 2；这句代码就包含了6个标记
			2 语法分析
				定义：语法分析是根据Token序列来构造抽象语法树的过程。
				抽象语法树是一种用来描述代码语法结构的树形表示方式。每一个结点都代表着一个语法结构：例如包、类型、修饰符等等 
		填充符号表
			定义：符号表符号表是由一组符号地址和符号信息构成的表格。
			符号表中锁记录的信息在编译的不同阶段都要用到。
	10.2.3 注解处理器
		定义：注解处理器可以看做的一个编译器的插件，可以读取、修改、添加抽象语法树中的任意元素。
		如果注解处理器在注解期间对语法树进行了修改，那么编译器将回到解析及填充符号表的过程重新处理，知道所有的插入式注解处理器都没有再对语法树进行修改为止。，每一次循环称为一个Round
	10.2.4 语义分析与字节码生成
		1 语义分析
			定义：语义分析是对语法分析后生成的抽象语法树进行上下文有关性质的审查。
			即验证语法正确性：例
			boolean a = false int b = 3; char v =2; int d = a + c;
			这样在编译的时候就会知道int d = a + c；是错的
			语义分析又分成标注检查和数据及控制流分析两个步骤
				1.1 标注检查
					定义：标注检查步骤检查的内容包括变量使用前是否已经被声音、数据类型是不是匹配等待。
					其中有一个重要的动作叫常量折叠：
					例：int a = 1 + 2； 会变成int a = 3；					
				1.2 数据及控制流分析
					定义：数据及控制流分析是对程序上下文逻辑更进一步的验证。它可以检查例如局部变量在使用前是否赋值、方法是否都有返回值等等
		2 解语法糖
			定义：语法糖，也叫糖衣语法，指在计算机计中添加的某种语法，这种语法对语言的功能并没有影响，但是更方便程序员的使用，提高效率。
			java中最常用的语法糖就泛型。
			虚拟机运行时不支持这些语法，会在编译阶段还原回简单的基础语法结构，这个过程就叫做截语法糖
		3 字节码生成
			定义：字节码生成阶段就是把起那么各个步骤所生成的信息转化成字节码写到磁盘中，编译器还进行了少量的代码添加和转换工作。
			例如：把字符串的加操作替换为StringBuffer或StringBuilder。
10.3 语法糖
	语法糖虽然不会提高实质性功能改进，带上他们可以提高效率。提高语法严谨性，减少编码出错的机会
	10.3.1 泛型
		泛型的实现是类型擦除。即由泛型的代码在编译后，泛型会被擦除掉
		例：
			Map<String,String> map = new HashMap<String,String>();
			在编译后会变成
			Map map = new HashMap();
		你会发现，泛型被擦除不见了，程序又变回了原生类型。
		再看下面代码
public class Demo2 {
    public static String method(List<String> list){
        System.out.println("invoke method(List<String> list)");
        return "";
    }
    public static int method(List<int> list){
        System.out.println("invoke method(List<int> list)");
    }
}
		重载的方法看似参数不一样，其实编译的时候泛型擦除掉时，发现方法参数是一样的，所以该代码无法通过编译。

	10.3.2 自动装箱拆箱
    @Test
    public void demo1(){
        Integer a = 1;
        Integer b = 2;
        Integer c = 3;
        Integer d = 3;
        Integer e = 321;
        Integer f = 321;
        Long g = 3L;
        Long h = 10000000L;
        Long i = 10000000L;
        Long j = 3L;
        System.out.println(c == d);//true
        System.out.println(e == f);//false
        System.out.println(c == (a + b));//true
        System.out.println(c.equals(a + b));//true
        System.out.println( g ==(a + b));//true
        System.out.println(g.equals( a + b));//false
        System.out.println(h == i);//false
        System.out.println(g == j);//true
    }
	鉴于包装类型的“==”运算在没有遇到算术运算的情况下不会自动拆箱，而equals不会处理数据的转型关系。
	10.3.3 条件编译
		对应if判断条件为常量的语句，编译时就会被运行。且根据布尔值真假，把分支中不成立的代码块消掉。
public void demo2(){
	if(true){
		System.out.println("true"");
	}else{
		System.out.println("false");
	}
}
		编译后会变成
public void demo2(){
	if(true){
		System.out.println("true"");
	}
}	