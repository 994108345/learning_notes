11
RTTI：运行阶段类型识别(Runtime Type Identification)的简称
jaa使用Class对象来执行其RTTI。
每个类都有一个Class对象。为了生成这个了的对象，运行这个额程序的虚拟机，将使用被称为“类加载器”的子系统。
Class.forName(String classPath):会初始化对应类，并获取衣蛾类类型Class。通过Class.newInstance()就可以创建对应对象。
如果已经知道目标类型。直接通过getClass()方法获取目标代理类的引用。
	Class bClass = demo1.getClass();
	System.out.println(bClass.getName());//全限定的类名（包含包名）
    System.out.println(bClass.getCanonicalName());//全限定的类名
    System.out.println(bClass.getSimpleName());//不含包名的类名
	isInterface()://该类是否为接口
	geInterfaces()://获的该类实现的接口
	getSuperclass()://该类的父类
	newInstance()；//创建对象实例
2 类字面常量
	java还提供了一个方法生成对Class的引用，即使用类字面常量。例：Aclass.class这样创建不仅简单还安全，因为在编译时就会检查，就不用try-catch了。
	对于基本数据类型的包装类型来说，有点区别
	boolean。class 等价于 Boolean.TYPE；以此类推。其他的都一样
	注意：使用".class"的形式来创建Class对象的引用，不会最自动的初始化该Class对象。为了使用类而做的准备工作实际包含三个步骤
	1 加载 由类加载器执行。该步骤是查找字节码，并从这些字节码创建一个Class对象
	2 链接 在链接阶段将验证类中的字节码，为静态域分配存储空间，并如果必要，将解析这个类创建的对其他类的所有引用。
	3 初始化 如果该类具有超类，则对其初始化，执行静态初始化器和静态初始化块。
	用static final修饰的全局变量，不需要初始化类即可初始化该全局变量initialize。就已经初始化了！！！
	只是修饰static的全局变量，在引用的时候就必须先初始化改类。
	通配符Class<?> aClass = A.class;
	这用用法优于普通用法Class aClass = A.class;
	这样子引用泛型语法只是为了听编译器类型检查，在编写就会报错，不用等到运行时才知道错误。
		Class<FancyToy> ftClass = FancyToy.class;
        FancyToy fancyToy = ftClass.newInstance();
        Class<? super FancyToy> up = ftClass.getSuperclass();
        System.out.println(up.getSimpleName());
        Object obj = up.newInstance();
	就是返回obj类型！！！而不是父类！！

	Class<? super FancyToy> up = ftClass.getSuperclass();
	Class<? extends FancyToy> up = ftClass.getSuperclass();让子类的类类型和父类的类类型可以互相赋值

	cast()
		几首参数对象，并将其转型为Class引用的类型。用子类的类类型调用cast方法，传入子类的实例对象。
class Building{}
class House extends Building{}
class ClassCasts{
    public static void main(String[] args) {
        Building building = new House();
        Class<House> houseType = House.class;
        House h = houseType.cast(building);
        //等价于
        h = (House)building;
    }
}

RTTI（运行阶段类型识别）形式包括：
	1 传统类型转换 如(Shape)
	2 代表对象的类型的Class对象
	3 关键字instanceof


反射
	Class<String> sClass = String.class;
    Method[] methods = sClass.getMethods();//获得该类是所有方法
    Constructor[] constructors = sClass.getConstructors();


	反射调用：
	Class<String> sClass = String.class;
	Method method = sClass.getMethod("wait",String.class);//参数1：方法名。 参数2：参数的类型，可以多个
    method.invoke(sClass.newInstance(),"1");//参数1：调用此方法的对象  参数2：具体参数，和上面相对应！

空对象
	如何优雅的判断null
	看不懂--模拟对象与桩
接口与类型信息
	反射可以调用你的私有方法、内部类、匿名类！
	

























