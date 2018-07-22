泛型
	主要目的
		用来指定容器要持有什么类型的对象，而且由编译器来保证类型的正确性。
	
	创建泛型类
		使用尖括号，将一个字母括住
		class A<T>{}
	创建泛型方法
		public<T> void fun(T t)
	注意：创建泛型类时，必须在创建对象的时候指定类型参数的值，不然就直接解析成Object了。
	而在使用泛型方法的时候，通常不必指明参数类型，因为编译器会为我们找出具体类型，这称为类型参数推断。传入基本类型的时候，自动打包机制就会介入其中，将基本类型的值包装成对应的对象，
泛型类：
class TestDemo2 implements  InterfaceDemo1{

    @Override
    public void PrintStr(Object o) {
        
    }
}

interface InterfaceDemo1<T>{
    void PrintStr(T t);
}
泛型方法：
public class GenericTest {
    public<T> void fun(T t){
        System.out.println(t.getClass().getName());
    }

    public static void main(String[] args) {
        GenericTest gt = new GenericTest();
        gt.fun("");
        gt.fun(1);
        gt.fun('a');
        gt.fun(12L);
    }
}

	使用泛型，人们都有一个抱怨，有时候需要向程序中加入更多的代码。
	类型只对赋值操作有效，其他时候不起作用。如果你将一个泛型方法调用的结果作为参数，传递给另一个方法时，这时编译器不会执行类型判断！而且会编译不通过。
public class New {
    public static <K,V> Map<K,V> map(){
        return new HashMap<K,V>();
    }
    static void f(Map<Map,List< ? extends List>> map){}

    public static void main(String[] args) {
        New.f(New.map());//会报错
    }
}
显示的类型说明：
	在泛型方法中，可以显示地指明类型。

4 可变参数与泛型方法
public class GenericVarargs {
    public static <T> List<T> makeList(T... args){
        List<T> result = new ArrayList<T>();
        for (T arg : args) {
            result.add(arg);
        }
        return result;
    }
    public static <T> List<T> arrayList(T[] args){
        List<T> result = new ArrayList<T>();
        for (T arg : args) {
            result.add(arg);
        }
        return result;
    }
    public static void main(String[] args){
        List<String> ls = makeList("A");
        System.out.println(ls);
        ls = makeList("A","B","C");
        System.out.println(ls);
        ls = arrayList("ABCSEFGHIJKLMNOPQRSTUVWXYZ".split(""));
        System.out.println(ls);
    }
}
T... args就像一个泛型数组一样。

我们很容易认为ArrayList<String>().getClass();和ArrayList<Integer>().getClass();不一样，其实两者是相等的！
例子：
public class ErasedTypeEquivalence {
    public static void main(String[] args) {
        Class c1 = new ArrayList<String>().getClass();
        Class c2 = new ArrayList<Integer>().getClass();
        System.out.println(c1 == c2);
    }
}
输出结果：true;

在泛型内部。无法获得任何有关泛型参数类型的信息。
java泛型是使用擦除来实现的，使用泛型时，任何具体的类型信息都被擦除了。	

<T extends Hasf>//extend是的意思将T擦除为Hasf，即Hasf替换了T类型。
这就是泛型的其中一个边界。
擦除减少 了java泛型的泛化性。因为泛型是jdk1.5才出现的，所以擦除是java解决泛型最好的折中的办法。
擦除主要的政党路由是从非泛化代码到泛化代码的转变过程，以及在不破坏现有类库的情况下，将泛型融入了java语言。
使用泛型创建数组时，Array.newInstance()是推荐方式;


即使擦除在方法或类内部移除了有关实际类型的信息，编译器仍旧可以确保在方法或类中使用的类型的内部一致性。
例子：
public class FilledListMaker<T> {
    List<T> create(T t,int n){
        List<T> result = new ArrayList<T>();
        for (int i = 0; i < n ; i++) {
            result.add(t);
        }
        return result;
    }

    public static void main(String[] args) {
        FilledListMaker<String> stringMaker = new FilledListMaker<String>();
        List<String> list = stringMaker.create("hello",4);
        System.out.println(list);
    }
}

擦除的补偿
	引入类型标签，剧可以转而使用动态的isInstance();
例子：
public class Building {
}
class House extends Building{}
class ClassTypeCapture<T>{
    Class<T> kind;

    public ClassTypeCapture(Class<T> kind) {
        this.kind = kind;
    }
    public boolean f(Object arg){
        return kind.isInstance(arg);
    }

    public static void main(String[] args) {
        ClassTypeCapture<Building> ctt1 = new ClassTypeCapture<Building>(Building.class);
        System.out.println(ctt1.f(new Building()));
        System.out.println(ctt1.f(new House()));
        ClassTypeCapture<House> ctt2 = new ClassTypeCapture<House>(House.class);
        System.out.println(ctt2.f(new Building()));
        System.out.println(ctt2.f(new House()));
    }
}
编译器将确保类型标签可以匹配泛型参数

创建类型实例
	用工厂对象创建一个实例
例子：
public class ClassAsFactory<T> {
    T x;

    public ClassAsFactory(Class<T> kind) {
        try {
            this.x = kind.newInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
class Employee{}
class InstantiateGenericType{
    public static void main(String[] args) {
        ClassAsFactory<Employee> fe = new ClassAsFactory<Employee>(Employee.class);
        System.out.println("ClassAsFactory<Employee> succeeded");
        try{
            ClassAsFactory<Integer> fi = new ClassAsFactory<Integer>(Integer.class);
        }catch (Exception e){

        }
    }
}
会报错。因为Integer没有任何默认构造器！！！！！
所以要求使用显式的工厂。并将限制其类型，使得只能接受实现这个工厂的类。
例子：
public class Foo2<T> {
    private T x;

    public <F extends FactoryI<T>>Foo2(F x) {
        this.x= (T) x.create();
    }
}
class IntegerFactory implements FactoryI<Integer>{

    @Override
    public Integer create() {
        return new Integer(0);
    }
}
class Widget{
    public static class Factory implements FactoryI<Widget>{

        @Override
        public Widget create() {
            return new Widget();
        }
    }
}

class FactoryConstranint{
    public static void main(String[] args) {
        new Foo2<Integer>(new IntegerFactory());
        new Foo2<Widget>(new Widget.Factory());
    }
}
interface FactoryI<T>{
    T create();
}
使用模块方法设计模式实现这个功能
public abstract class GenericWithCreate<T> {
    final T element;

    public GenericWithCreate() {
        this.element = create();
    }
    abstract T create();
}
class X{}
class Creator extends GenericWithCreate<X>{

    X create(){return new X();}
    void f(){
        System.out.println(element.getClass().getSimpleName());
    }
}
class CreatorGeneric{
    public static void main(String[] args) {
        Creator c = new Creator();
        c.f();
    }
}


































