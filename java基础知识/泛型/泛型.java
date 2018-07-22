����
	��ҪĿ��
		����ָ������Ҫ����ʲô���͵Ķ��󣬶����ɱ���������֤���͵���ȷ�ԡ�
	
	����������
		ʹ�ü����ţ���һ����ĸ��ס
		class A<T>{}
	�������ͷ���
		public<T> void fun(T t)
	ע�⣺����������ʱ�������ڴ��������ʱ��ָ�����Ͳ�����ֵ����Ȼ��ֱ�ӽ�����Object�ˡ�
	����ʹ�÷��ͷ�����ʱ��ͨ������ָ���������ͣ���Ϊ��������Ϊ�����ҳ��������ͣ����Ϊ���Ͳ����ƶϡ�����������͵�ʱ���Զ�������ƾͻ�������У����������͵�ֵ��װ�ɶ�Ӧ�Ķ���
�����ࣺ
class TestDemo2 implements  InterfaceDemo1{

    @Override
    public void PrintStr(Object o) {
        
    }
}

interface InterfaceDemo1<T>{
    void PrintStr(T t);
}
���ͷ�����
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

	ʹ�÷��ͣ����Ƕ���һ����Թ����ʱ����Ҫ������м������Ĵ��롣
	����ֻ�Ը�ֵ������Ч������ʱ�������á�����㽫һ�����ͷ������õĽ����Ϊ���������ݸ���һ������ʱ����ʱ����������ִ�������жϣ����һ���벻ͨ����
public class New {
    public static <K,V> Map<K,V> map(){
        return new HashMap<K,V>();
    }
    static void f(Map<Map,List< ? extends List>> map){}

    public static void main(String[] args) {
        New.f(New.map());//�ᱨ��
    }
}
��ʾ������˵����
	�ڷ��ͷ����У�������ʾ��ָ�����͡�

4 �ɱ�����뷺�ͷ���
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
T... args����һ����������һ����

���Ǻ�������ΪArrayList<String>().getClass();��ArrayList<Integer>().getClass();��һ������ʵ��������ȵģ�
���ӣ�
public class ErasedTypeEquivalence {
    public static void main(String[] args) {
        Class c1 = new ArrayList<String>().getClass();
        Class c2 = new ArrayList<Integer>().getClass();
        System.out.println(c1 == c2);
    }
}
��������true;

�ڷ����ڲ����޷�����κ��йط��Ͳ������͵���Ϣ��
java������ʹ�ò�����ʵ�ֵģ�ʹ�÷���ʱ���κξ����������Ϣ���������ˡ�	

<T extends Hasf>//extend�ǵ���˼��T����ΪHasf����Hasf�滻��T���͡�
����Ƿ��͵�����һ���߽硣
�������� ��java���͵ķ����ԡ���Ϊ������jdk1.5�ų��ֵģ����Բ�����java���������õ����еİ취��
������Ҫ������·���ǴӷǷ������뵽���������ת����̣��Լ��ڲ��ƻ�������������£�������������java���ԡ�
ʹ�÷��ʹ�������ʱ��Array.newInstance()���Ƽ���ʽ;


��ʹ�����ڷ��������ڲ��Ƴ����й�ʵ�����͵���Ϣ���������Ծɿ���ȷ���ڷ���������ʹ�õ����͵��ڲ�һ���ԡ�
���ӣ�
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

�����Ĳ���
	�������ͱ�ǩ�������ת��ʹ�ö�̬��isInstance();
���ӣ�
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
��������ȷ�����ͱ�ǩ����ƥ�䷺�Ͳ���

��������ʵ��
	�ù������󴴽�һ��ʵ��
���ӣ�
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
�ᱨ����ΪIntegerû���κ�Ĭ�Ϲ���������������
����Ҫ��ʹ����ʽ�Ĺ������������������ͣ�ʹ��ֻ�ܽ���ʵ������������ࡣ
���ӣ�
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
ʹ��ģ�鷽�����ģʽʵ���������
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


































