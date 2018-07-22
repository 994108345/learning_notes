什么是线程？
java中，创建线程类就两个途径。实现Runnable接口，扩展Thread类。一般基于单继承多接口的原因，推荐使用Runnable接口！
最简单的线程例子
class ThreadDemo{
    public static void main(String[] args){
        new NewThread();
        try{
	//主线程执行的代码
            for (int i= 5; i>0; i++){
                System.out.println("Main Thtrad :" +i);
            }
        } catch (Exception e){
            System.out.println("Main thread interruped.");
        }
        System.out.println("Main thred exiting.");
    }
}
class NewThread implements Runnable{
    Thread t;
    NewThread(){
        t = new Thread(this, "Demo Thread");
        System.out.println("Child thread :" + t);
        t.start();
    }
    @Override
    public void run() {
        try{
	//子线程执行的代码
            for (int i=5 ; i>0; i++){
                System.out.println("Child Thread:"+ i);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
            System.out.println("Exiting child thred");
        }
    }
}
这个功能只是简单的实现了主线程和子线程互相交替打印字符串到控制台的功能，并且死循环的运行下去。
运行结果：
会不断互相交替打印"Child Thread i" 和 "Main Thtrad i",谁先谁后，不固定！
看了以上代码会发现一个疑惑的地方，线程的run()到底什么时间执行。
其实，线程运行start()方法后，线程被允许可以执行线程类中的run()方法。该方法不用我们调用，程序会自己执行。但是具体什么时间执行，主线程和子线程的执行的先后顺序，都是电脑cpu自动选择的结果。所以会出现交替打印的效果。

线程的结束？
一般我们都希望主线程比子线程完结束。这样可以进行释放资源等操作！这里我们要用到join()方法！join()是等待一个线程终止
看下面一个例子
class NewMoreThread implements Runnable{
    Thread t;
    String name;
    NewMoreThread(String threadName){
        name = threadName;
        t = new Thread(this, name);
        System.out.println("New thread" + t);
        t.start();
    }
    @Override
    public void run() {
        try{
            for (int i=5 ; i>0; i--){
                System.out.println(name +":"+ i);
                Thread.sleep(1000);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
            System.out.println(name +"interruped.");
        }
        System.out.println(name + "exiting.");
    }
}
class JoinDemo{
    public static void main(String[] args){
        NewMoreThread ob1 =new NewMoreThread("One");
        NewMoreThread ob2 =new NewMoreThread("Two");
        NewMoreThread ob3 =new NewMoreThread("Three");
        System.out.println("Thread One os alive" + ob1.t.isAlive());
        System.out.println("Thread Two os alive" + ob2.t.isAlive());
        System.out.println("Thread Three os alive" + ob3.t.isAlive());
        try{
            System.out.println("Waiting for threads to finish.");
            ob1.t.join();
            ob2.t.join();
            ob3.t.join();
        } catch (Exception e){
            System.out.println("Main thread interruped.");
        }
        System.out.println("Thread One is alive:" + ob1.t.isAlive());
        System.out.println("Thread Two is alive:" + ob2.t.isAlive());
        System.out.println("Thread Three is alive:" + ob3.t.isAlive());
    }
}
打印结果：
New threadThread[Two,5,main]
New threadThread[Three,5,main]
One:5
Thread One os alivetrue
Two:5
Thread Two os alivetrue
Thread Three os alivetrue
Waiting for threads to finish.
Three:5
Three:4
One:4
Two:4
Two:3
Three:3
One:3
Three:2
One:2
Two:2
One:1
Three:1
Two:1
Disconnected from the target VM, address: '127.0.0.1:62114', transport: 'socket'
Twoexiting.
Threeexiting.
Oneexiting.
Thread One is alive:false
Thread Two is alive:false
Thread Three is alive:false
你会发现，三个线程都结束的时，才会执行最后的三个打印控制台操作！

线程的运行顺序？
很多人就想到，如果是多个线程一起运行，能不能手动控制运行的先后顺序。答案是不能绝对控制，线程的运行顺序和很多因素都有关系！只能提高某些线程获取cpu资源执行代码的几率!
线程通过；setPriority(Thread.MAX_PRIORITY);给线程赋予优先级。MAX_PRIORITY默认值是5.最大值是10，最小值是1，值越大，优先级越大。
先给大家一个例子！
class Clicker implements Runnable{
    int click = 0;
    Thread t;
    private volatile boolean running = true;

    public Clicker(int p){
        t = new Thread(this);
        t.setPriority(p);
    }
    @Override
    public void run() {
        while (running){
            click++;
        }
    }
    public void stop(){
        running = false;
    }
    public void start(){
        t.start();
    }
}
class HiLoPri{
    public static void main (String[] args){
        Thread.currentThread().setPriority(Thread.MAX_PRIORITY);
        Clicker hi = new Clicker(Thread.NORM_PRIORITY + 2 );
        Clicker lo = new Clicker(Thread.NORM_PRIORITY - 2);
        hi.start();
        lo.start();
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
            System.out.println("Main thread interrupted.");
        }
        lo.stop();
        hi.stop();
        try {
            hi.t.join();
            lo.t.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
            System.out.println("InterrupetedException caught");
        }
        System.out.println("Low - priority thread:" +lo.click);
        System.out.println("High - priority thread:" +hi.click);
    }
}
运行结果：
High - priority thread:311376165
Low - priority thread:141875148
很明显，high的运行次数远大于low！因为执行的概率高！

当使用多线程时，同时操作一个对象时，可能出现重复操作的现象，造成输出结果混乱
线程同步
class CallMe{
    synchronized void call(String msg){
        System.out.println("[" +msg);
        try{
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
            System.out.println("Intrttupted");
        }
        System.out.println("]");
    }
}
class Caller implements Runnable{

    String msg;
    CallMe target;
    Thread t;
    public Caller(CallMe targ, String s){
        target = targ;
        msg = s;
        t = new Thread(this);
        t.start();
    }
    @Override
    public void run() {
        target.call(msg);

    }
}
class Synch {
    public static void main (String[] args){
        CallMe target = new CallMe();
        Caller ob1 = new Caller(target, "Hello");
        Caller ob2 = new Caller(target,"Synchronized");
        Caller ob3 = new Caller(target,"world");
        try{
            ob1.t.join();
            ob2.t.join();
            ob3.t.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

线程池：
	四种线程池：
	.newSingleThreadExecutor（） 
	单例线程池：池里只有一个线程。
	.newFixedThreadPool（num）
	固定线程池：给初值设置线程池线程数，数目设置后就不会改！
	.newCachedThreadPool（）
	如果线程数不够用，会自己自动创建线程。
	.NewScheduledThreadPool:
	创建一个固定大小的线程池

	exec.shutdown();//自动关闭线程
	exec.awaitTermination(1, TimeUnit.HOURS);//等待所有子线程运行完毕后

有返回结果的线程
	public class MultiThreadThread implements Callable{
		String name ;

		public MultiThreadThread(String name) {
			this.name = name;
		}
		@Override
		public Object call() throws Exception {
			System.out.println("此时的线程名字叫做"+name);
			double a = Math.random();
			return a;
		}
	}
//使用线程池接收线程返回结果！
public class A{
		public static void main(String[] args){
				try {
					ExecutorService exe = Executors.newFixedThreadPool(10);
					List<Future> returnList = new ArrayList();
					for (int i1 = 0; i1 < 10; i1++) {
						Callable callable = new MultiThreadThread(String.valueOf(i1));
						Future future = exe.submit(callable);
						returnList.add(future);
					}
					exe.shutdown();
					for (Future future : returnList) {
						System.out.println("获得的随机数是"+String.valueOf(future.get()));
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
				return null;
			}
	}




		
