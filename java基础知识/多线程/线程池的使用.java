线程池的使用
线程池的种类：
	1.CachedThreadPool:
		根据程序的运行需要去创建线程，它会为每一个任务添加一个新的线程，这边有一个超时机制，当空闲的线程超过60s内没有用到的话，就会被回收。缺点就是没有考虑到系统的实际内存大小。
	2.SingleThreadPool:
		就像线程数为1的FixedThreadPool,单线程去跑任务，不处理并发的操作，不会被回收。
	3.FixedThreadPool:
		创建可容纳固定数量线程的池子，响应的速度快，适合长期任务。
	4.ScheduledThreadPool:
		创建一个固定大小的线程池，唯一一个有延迟执行和周期重复执行的线程池。它的核心线程池固定，非核心线程的数量没有限制，但是闲置时会立即会被回收。
		核心线程的数目的固定的，非核心线程的数目是不固定的。一旦非核心的线程处于闲置状态用，就会立即被回收。
		主要用来执行定时任何和周期执行的任务。
创建线程池：
	ExecutorService exec = Executors.newCachedThreadPool();
	ExecutorService exec = Executors.newSingleThreadExecutor();
	ExecutorService exec = Executors.newFixedThreadPool(10);
	ExecutorService exec = Executors.newScheduledThreadPool();
方法：
	shutdown():防止新任务被提交给这个Executor，即调用这个方法后，不能再用ExecutorService去生成新线程去跑任务。
线程类：
public class ThreadPoolTest implements Runnable {
    String threadName;

    public ThreadPoolTest(String threadName) {
        this.threadName = threadName;
    }

    @Override
    public void run() {
        int b = 0;
        for (int i = 0; i < 10000; i++) {
            b = b +i;
        }
        System.out.println(Thread.currentThread().getName()+".b:" + b);
    }
}
public class CacheThreadPoolTest {
    private static ExecutorService exe = Executors.newCachedThreadPool();

    public static void main(String[] args) {
        for (int i = 0; i < 10; i++) {
            exe.execute(new ThreadPoolTest("i"));
        }
        System.out.println("全部结束了吗");
        exe.shutdown();
        exe.execute(new ThreadPoolTest("11"));
        System.out.println("全部结束了");
    }
}
输出结果：
Exception in thread "main" java.util.concurrent.RejectedExecutionException: Task thread.thread.ThreadPoolTest@63bed674 rejected from java.util.concurrent.ThreadPoolExecutor@5f18cd5[Shutting down, pool size = 10, active threads = 10, queued tasks = 0, completed tasks = 0]
	at java.util.concurrent.ThreadPoolExecutor$AbortPolicy.rejectedExecution(ThreadPoolExecutor.java:2048)
	at java.util.concurrent.ThreadPoolExecutor.reject(ThreadPoolExecutor.java:821)
	at java.util.concurrent.ThreadPoolExecutor.execute(ThreadPoolExecutor.java:1372)
	at thread.thread.CacheThreadPoolTest.main(CacheThreadPoolTest.java:21)
全部结束了吗
pool-1-thread-1.b:49995000
pool-1-thread-2.b:49995000
pool-1-thread-6.b:49995000
pool-1-thread-4.b:49995000
pool-1-thread-7.b:49995000
pool-1-thread-8.b:49995000
pool-1-thread-3.b:49995000
pool-1-thread-9.b:49995000
pool-1-thread-10.b:49995000
pool-1-thread-5.b:49995000

输出结果会报错：因为shudown后不能再生成新线程去跑任务！

从线程任务中获得返回值
	实现Runnable接口。
线程类：
public class CallThreadTest implements Callable<String> {
    @Override
    public String call() throws Exception {
        return Thread.currentThread().getName();
    }
}
测试类:
private static ExecutorService exe = Executors.newCachedThreadPool();
@Test
    public void  callThreadTest() throws ExecutionException, InterruptedException {
        List<Future<String>> list = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            Future<String> future = exe.submit(new CallThreadTest());
            list.add(future);
        }
        for (Future<String> stringFuture : list) {
            String retStr = stringFuture.get();
            System.out.println("返回结果为：" + retStr);
        }
        exe.shutdown();
    }
输出结果：
返回结果为：pool-1-thread-1
返回结果为：pool-1-thread-2
返回结果为：pool-1-thread-3
返回结果为：pool-1-thread-4
返回结果为：pool-1-thread-5


