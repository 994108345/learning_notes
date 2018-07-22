单例模式定义：确保一个类只有一个实例，并提供一个全局访问点。
主要问题：1.用多线程可能造成创建混乱。2.如果用到多个类加载器，就可能创建多个单例模式。
解决方案：1.用volatile关键字和synchronized双重检查加锁。2.指定用同一个类加载器加载。
一般单例模式有两种写法。第一种的饿汉单例模式，另外一种叫做懒汉单例模式
懒汉单例模式：即等要用时候再创建
public class SingleDemo {
    public volatile static SingleDemo uniqueInstace ;

    private SingleDemo() {
    }

    public static SingleDemo getInstance(){
        if(uniqueInstace == null) {
            synchronized (SingleDemo.class) {
                if (uniqueInstace == null) {
                    uniqueInstace = new SingleDemo();
                } else {
                    return uniqueInstace;
                }
            }
        }
        return uniqueInstace;
    }
}
判断两次：也叫双重锁定，多加一次判断是因为，如果uniqueInstance不等于null时，完全没有必要进入代码，并加锁。这样可以节省资源。

饿汉单例模式：即等不及了，现在就要创建
	public class SingleDemo {
    public volatile static SingleDemo uniqueInstace = new  SingleDemo();

    private SingleDemo() {
    }

    public static SingleDemo getInstance(){
        return uniqueInstace;
    }
}
直接用静态初始化的方式在加载类的时候就自己实例化。
