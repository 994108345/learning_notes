ʲô���̣߳�
java�У������߳��������;����ʵ��Runnable�ӿڣ���չThread�ࡣһ����ڵ��̳ж�ӿڵ�ԭ���Ƽ�ʹ��Runnable�ӿڣ�
��򵥵��߳�����
class ThreadDemo{
    public static void main(String[] args){
        new NewThread();
        try{
	//���߳�ִ�еĴ���
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
	//���߳�ִ�еĴ���
            for (int i=5 ; i>0; i++){
                System.out.println("Child Thread:"+ i);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
            System.out.println("Exiting child thred");
        }
    }
}
�������ֻ�Ǽ򵥵�ʵ�������̺߳����̻߳��ཻ���ӡ�ַ���������̨�Ĺ��ܣ�������ѭ����������ȥ��
���н����
�᲻�ϻ��ཻ���ӡ"Child Thread i" �� "Main Thtrad i",˭��˭�󣬲��̶���
�������ϴ���ᷢ��һ���ɻ�ĵط����̵߳�run()����ʲôʱ��ִ�С�
��ʵ���߳�����start()�������̱߳��������ִ���߳����е�run()�������÷����������ǵ��ã�������Լ�ִ�С����Ǿ���ʲôʱ��ִ�У����̺߳����̵߳�ִ�е��Ⱥ�˳�򣬶��ǵ���cpu�Զ�ѡ��Ľ�������Ի���ֽ����ӡ��Ч����

�̵߳Ľ�����
һ�����Ƕ�ϣ�����̱߳����߳���������������Խ����ͷ���Դ�Ȳ�������������Ҫ�õ�join()������join()�ǵȴ�һ���߳���ֹ
������һ������
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
��ӡ�����
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
��ᷢ�֣������̶߳�������ʱ���Ż�ִ������������ӡ����̨������

�̵߳�����˳��
�ܶ��˾��뵽������Ƕ���߳�һ�����У��ܲ����ֶ��������е��Ⱥ�˳�򡣴��ǲ��ܾ��Կ��ƣ��̵߳�����˳��ͺܶ����ض��й�ϵ��ֻ�����ĳЩ�̻߳�ȡcpu��Դִ�д���ļ���!
�߳�ͨ����setPriority(Thread.MAX_PRIORITY);���̸߳������ȼ���MAX_PRIORITYĬ��ֵ��5.���ֵ��10����Сֵ��1��ֵԽ�����ȼ�Խ��
�ȸ����һ�����ӣ�
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
���н����
High - priority thread:311376165
Low - priority thread:141875148
�����ԣ�high�����д���Զ����low����Ϊִ�еĸ��ʸߣ�

��ʹ�ö��߳�ʱ��ͬʱ����һ������ʱ�����ܳ����ظ������������������������
�߳�ͬ��
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

�̳߳أ�
	�����̳߳أ�
	.newSingleThreadExecutor���� 
	�����̳߳أ�����ֻ��һ���̡߳�
	.newFixedThreadPool��num��
	�̶��̳߳أ�����ֵ�����̳߳��߳�������Ŀ���ú�Ͳ���ģ�
	.newCachedThreadPool����
	����߳��������ã����Լ��Զ������̡߳�
	.NewScheduledThreadPool:
	����һ���̶���С���̳߳�

	exec.shutdown();//�Զ��ر��߳�
	exec.awaitTermination(1, TimeUnit.HOURS);//�ȴ��������߳�������Ϻ�

�з��ؽ�����߳�
	public class MultiThreadThread implements Callable{
		String name ;

		public MultiThreadThread(String name) {
			this.name = name;
		}
		@Override
		public Object call() throws Exception {
			System.out.println("��ʱ���߳����ֽ���"+name);
			double a = Math.random();
			return a;
		}
	}
//ʹ���̳߳ؽ����̷߳��ؽ����
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
						System.out.println("��õ��������"+String.valueOf(future.get()));
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
				return null;
			}
	}




		
