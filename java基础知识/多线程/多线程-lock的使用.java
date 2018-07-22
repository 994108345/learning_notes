使用ReentrantLock类
	是在jdk1.5新增加的ReentrantLock类。
	1.1 
		public class LockTest {
			private Lock lock = new ReentrantLock();
			public void lockTest() {
				lock.lock();
				for (int i = 0; i < 10; i++) {
					System.out.println("ThreadName=" + Thread.currentThread().getName() + i);
				}
				lock.unlock();
			}
		}
	使用lock()方法获取锁，使用unlock()方法释放锁。所以被两个方法夹在中间的代码就是同步的。

	1.2 Condition
		关键字sunchronized是通过wait() notify() notifyAll()方法相结合实现等待/通知模型。而ReentrantLock也可以实现同样的功能，但是需要借助于Condition对象。相对比synchronized，condition更灵活！因为可以创建多个condition实例。
		使用notify/notidyAll是随机通知的，但是condition可以选择性通知。
		注意：在调用await方法之前，必须用lock()方法获得同步监视器，不然会报错！
		代码如下：
		服务类：
		public class MyService {
			Lock lock = new ReentrantLock();
			private Condition condition = lock.newCondition();
			public void await(){
				try{
					lock.lock();
					System.out.println("awair时间为"+System.currentTimeMillis());
					condition.await();
				}catch (Exception e){
					e.printStackTrace();
				}finally {
					lock.unlock();
					System.out.println("锁被释放了");
				}
			}

			public void signal(){
				try{
					lock.lock();
					System.out.println("signal时间为" + System.currentTimeMillis());
				}catch (Exception e){
					e.printStackTrace();
				}finally{
					lock.unlock();
				}
			}
		}
		线程A类：
		public class ThreadA implements  Runnable{
			private MyService myService;

			public ThreadA(MyService myService) {
				this.myService = myService;
			}

			@Override
			public void run() {
				myService.await();
			}
		}
		Run运行类:
		public class Run {
			public static void main(String[] args) {
				try {
					MyService myService = new MyService();
					ThreadA a = new ThreadA(myService);
					Thread threadA = new Thread(a);
					threadA.start();
					Thread.sleep(1000);
					myService.signal();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
		运行结果：
		awair时间为1526374729023
		signal时间为1526374730009

		Object类的wait()相当于Condition类的await()
		Object类的notify()相当于Condition类的notify()
		Object类的notifyAll()相当于Condition类的notifyAll()

		1.2.1
			实现多个condition使用
				就是创建多个condition，并给灭一个都写一个await()和signal()。
				public class MyService {
					Lock lock = new ReentrantLock();
					private Condition conditionA = lock.newCondition();
					private Condition conditionB = lock.newCondition();
					public void awaitA(){
						try{
							lock.lock();
							System.out.println("当前线程为"+Thread.currentThread().getName()+"。awaitA时间为"+System.currentTimeMillis());
							conditionA.await();
						}catch (Exception e){
							e.printStackTrace();
						}finally {
							lock.unlock();
						}
					}

					public void signalA(){
						try{
							lock.lock();
							System.out.println("当前线程为"+Thread.currentThread().getName()+"。signalA时间为" + System.currentTimeMillis());
							conditionA.signal();
						}catch (Exception e){
							e.printStackTrace();
						}finally{
							lock.unlock();
						}
					}

					public void signalAll_A(){
						try{
							lock.lock();
							System.out.println("当前线程为"+Thread.currentThread().getName()+"。signalAll_A时间为" + System.currentTimeMillis());
							conditionA.signalAll();
						}catch (Exception e){
							e.printStackTrace();
						}finally{
							lock.unlock();
						}
					}

					public void awaitB(){
						try{
							lock.lock();
							System.out.println("当前线程为"+Thread.currentThread().getName()+"。awaitB时间为"+System.currentTimeMillis());
							conditionB.await();
						}catch (Exception e){
							e.printStackTrace();
						}finally {
							lock.unlock();
						}
					}

					public void signalB(){
						try{
							lock.lock();
							System.out.println("当前线程为"+Thread.currentThread().getName()+"。signalB时间为" + System.currentTimeMillis());
							conditionB.signal();
						}catch (Exception e){
							e.printStackTrace();
						}finally{
							lock.unlock();
						}
					}

					public void signalAll_B(){
						try{
							lock.lock();
							System.out.println("当前线程为"+Thread.currentThread().getName()+"。signalAll_B时间为" + System.currentTimeMillis());
							conditionB.signalAll();
						}catch (Exception e){
							e.printStackTrace();
						}finally{
							lock.unlock();
						}
					}
				}
				Run类
				public class Run {
					public static void main(String[] args) {
						try {
							MyService myService = new MyService();
							ThreadA a = new ThreadA(myService,"A");
							ThreadA b = new ThreadA(myService,"B");
							Thread threadA = new Thread(a);
							Thread threadB = new Thread(b);
							threadA.start();
							threadB.start();
							Thread.sleep(1000);
							myService.signalA();
							myService.signalB();
						} catch (InterruptedException e) {
							e.printStackTrace();
						}

					}
				}
				输出结果：
				当前线程为Thread-0。awaitA时间为1526376205793
				当前线程为Thread-1。awaitA时间为1526376205795
				当前线程为main。signalA时间为1526376206779
				当前线程为main。signalB时间为1526376206779

		1.2.2 实现生产者/消费者模式：一对一交替打印
			//Myservice:
			public class MyService {
				private Lock lock = new ReentrantLock();
				private Condition condition = lock.newCondition();
				private boolean hasValue = false;
				public void set(){
					try{
						lock.lock();
						while(hasValue){
							condition.await();
						}
						hasValue = true;
						System.out.println("生产");
						condition.signal();
					}catch (Exception e){
						e.printStackTrace();
					}finally {
						lock.unlock();
					}
				}

				public void get(){
					try{
						lock.lock();
						while(!hasValue){
							condition.await();
						}
						hasValue = false;
						System.out.println("消费");
						condition.signal();
					}catch (Exception e){
						e.printStackTrace();
					}finally {
						lock.unlock();
					}
				}
			}
			//ConsumerThread:
			public class ConsumerThread implements Runnable {
				private MyService myService;

				public ConsumerThread(MyService myService) {
					this.myService = myService;
				}

				@Override
				public void run() {
					for (int i = 0; i < Integer.MAX_VALUE ; i++) {
						myService.get();
					}
				}
			}
			//ProducerThread:
			public class ProducerThread implements  Runnable {
				private MyService myService;

				public ProducerThread(MyService myService) {
					this.myService = myService;
				}

				@Override
				public void run() {
					for (int i = 0; i < Integer.MAX_VALUE; i++) {
						myService.set();
					}
				}
			}
			//Run:
			public class Run {
				public static void main(String[] args) {
					try {
						MyService myService = new MyService();
						ConsumerThread a = new ConsumerThread(myService);
						ProducerThread b = new ProducerThread(myService);
						Thread threadA = new Thread(a);
						Thread threadB = new Thread(b);
						threadA.start();
						threadB.start();
					} catch (Exception e) {
						e.printStackTrace();
					}

				}
			}
			运行结果：生产
						消费
						生产
						消费
						生产
						消费
						......
			如果实现多对多的生产者消费者模式，其他都不需要改，只要多创建几个线程，然后把myservice的condition.signal();改成condition.signalAll();就可以防止出现假死状态！

		1.2.3 公平锁与非公平锁
			锁Lock分为公平锁和非公平锁
				公平锁表示线程获取锁的顺序的按照线程的顺序来分配的，即先来先得的FIFO先进先出顺序。
				非公平锁就是一种获取锁的抢占机制，是随机获得锁的，和公平锁不一样的就是先来的不一定先得到锁，这个方式可能造成某些线程一直拿不到锁！
			公平锁例子：
				//Service：
					public class Service {
						private ReentrantLock lock;

						public Service(boolean isFair) {
							lock = new ReentrantLock(isFair);
						}
						public void serviceMethod(){
							try{
								lock.lock();
								System.out.println("ThreadName=" + Thread.currentThread().getName() + "获得锁定");
							}catch (Exception e){
								e.printStackTrace();
							}finally {
								lock.unlock();
							}
						}
					}

				//RunFair:
					public class RunFair {
						public static void main(String[] args) {
							final Service service = new Service(true);
							Runnable runnable = new Runnable() {
								@Override
								public void run() {
									System.out.println("线程"+Thread.currentThread().getName() + "运行了");
									service.serviceMethod();
								}
							};
							Thread[] threadArray= new Thread[10];
							for (int i = 0; i < 10; i++) {
								threadArray[i] = new Thread(runnable);
							}
							for (int i = 0; i < 10; i++) {
								threadArray[i].start();
							}
						}
					}
			运行后会发现打印结果基本呈有序的状态，这就是公平锁的特点
			将final Service service = new Service(true);改成final Service service = new Service(false);就是非公平锁！
			运行后会发现打印结果明显比公平锁乱！说明先start()启动的线程不代表先获得锁。
		
		1.2.4 方法解说
				.getHoldCount():查询当前线程保持此锁定的个数，也就是调用lock()方法的次数。
					例子：
					service:
					public class Service {
						private ReentrantLock lock = new ReentrantLock();
						public void serrviceMethod1(){
							try{
								lock.lock();
								System.out.println("serviceMrthod1 getHoldCount=" + lock.getHoldCount());
								serrviceMethod2();
							}catch (Exception e){
								e.printStackTrace();
							}finally {
								lock.unlock();
							}
						}
						public void serrviceMethod2(){
							try{
								lock.lock();
								System.out.println("serviceMrthod2 getHoldCount=" + lock.getHoldCount());
							}catch (Exception e){
								e.printStackTrace();
							}finally {
								lock.unlock();
							}
						}
					}
					Run:
					public class Run {
						public static void main(String[] args) {
							try {
								Service service = new Service();
								service.serrviceMethod1();
							} catch (Exception e) {
								e.printStackTrace();
							}

						}
					}
				.getQueueLength():返回正等待获取此锁定的线程估计数。
					例子：
					Service:
					public class Service {
						public  ReentrantLock lock = new ReentrantLock();
						public void serrviceMethod1(){
							try{
								lock.lock();
								System.out.println("ThreadName=" + Thread.currentThread().getName() + "进入方法！");
								Thread.sleep(Integer.MAX_VALUE);
							}catch (Exception e){
								e.printStackTrace();
							}finally {
								lock.unlock();
							}
						}
					}
					Run:
					public class Run {
						public static void main(String[] args) {
							final Service service = new Service();
							try {
							   Runnable runnable = new Runnable() {
								   @Override
								   public void run() {
									   service.serrviceMethod1();
								   }
							   };
								Thread[] threadArray = new Thread[10];
								for (int i = 0; i < 10; i++) {
									threadArray[i] = new Thread(runnable);
								}
								for (int i = 0; i < 10; i++) {
									threadArray[i].start();
								}
								Thread.sleep(2000);
								System.out.println("有线程数：" + service.lock.getQueueLength() + "在等待获取锁!");
							} catch (Exception e) {
								e.printStackTrace();
							}

						}
					}
				.getWaitQueueLength():返回等待此锁定相关的给定条件condition的线程估计数。
				例子：
				Run:
					public class Run {
						public static void main(String[] args) {
							final Service service = new Service();
							try {
							   Runnable runnable = new Runnable() {
								   @Override
								   public void run() {
									   service.waitMethod();
								   }
							   };
								Thread[] threadArray = new Thread[10];
								for (int i = 0; i < 10; i++) {
									threadArray[i] = new Thread(runnable);
								}
								for (int i = 0; i < 10; i++) {
									threadArray[i].start();
								}
								Thread.sleep(2000);
								service.notidyMethod();
							} catch (Exception e) {
								e.printStackTrace();
							}

						}
					}
				Service:
					public class Service {
						public  ReentrantLock lock = new ReentrantLock();
						public Condition condition = lock.newCondition();
						public void waitMethod(){
							try{
								lock.lock();
								System.out.println("ThreadName=" + Thread.currentThread().getName() + "进入方法！");
								condition.await();
							}catch (Exception e){
								e.printStackTrace();
							}finally {
								lock.unlock();
							}
						}
						public void notidyMethod(){
							try{
								lock.lock();
								System.out.println("有"+lock.getWaitQueueLength(condition) + "个线程正在等待condition");
								condition.signalAll();
							}catch (Exception e){
								e.printStackTrace();
							}finally {
								lock.unlock();
							}
						}
					}
				.boolean hasQueuedThread(Thread thread):查询指定线程是否正在等待获取此锁
				.boolean hasQueuedThreads():查询是否有线程正在等待获取此锁定
				.hasWaiters(Condition condition):查询是否有线程正在等待与此锁定有关的condition条件

				.isFair():判断是否为公平锁
				.boolean isHeldByCurrentThread():查询当前线程是否保持此锁定
				.isLocked():查询此锁定是否由任意线程保持

				.void lockInterruptibly():如果当钱线程未被中断，则获取锁定，如果已经被中断则出现异常。
				.boolean tryLock():仅在调用时锁定未被另一个线程保持的情况下，才获取该锁定。
					通俗来说，就是如果此时的lock没有被任何线程获取，我们调用lock.tryLock()返回true，并且，获取了lock的锁定！即lock.lock();如果锁定没有被unlock().其他线程再调用这个方法时返回false！
				.tryLock(long timeOut,TimeUnit unit):如果锁定在给定等待时间内没有被另外衣蛾线程保持，且当前线程未被中断，则获取该锁定。
				
				.awaitUninterruptibly():wait()中 的线程被interrrupt唤醒会报异常。但是用awaitUninterruptibly（）让线程进入等待再interrupted就不会抛异常！
				.awaitUntil:用此方法让线程等待一定时间，可以被其他线程提前唤醒，
		1.2.5 使用ReentranReadWriteLock类的使用
			    ReentrantLock具有完全互斥排他效果，即同一时间只有一个线程再执行ReentrantLock.lock()方法后面的任务。虽然保证了线程的安全性，但是效率特别低。所以 在JDK中提供了一种读写锁ReentrantReadWriteLock类，使它加快效率。注意！要求是不操作实例变量的方法中，才可以使用读写锁来提升反码运行速度。
				  读写锁也有两个锁：一个是读操作的锁，也称共享锁：另一个是写操作的锁，也叫排他锁。
				  多个读锁之间不互斥，读写锁互斥，写写锁互斥。
				  在没有线程进行写入操作时，进行读取操作的多个Thread都可以获取读锁
				  进行写入操作的Thread只有在获取写锁后才能进行写入操作，即多个Thread可以同时进行读取操作，同一时刻只允许一个Thread进行写操作。

			1.2.5.1 读读共享：
				例子：使用读锁可以让多个读线程同时进入同一个被lock()的代码
					service:
					public class Service {
						public ReentrantReadWriteLock lock = new ReentrantReadWriteLock();
						public void read(){
							try{
								try{
									lock.readLock().lock();
									System.out.println("获得读锁" + Thread.currentThread().getName() + " " + System.currentTimeMillis());
									Thread.sleep(10000);
								}catch (Exception e){
									e.printStackTrace();
								}finally {
									lock.readLock().unlock();
								}
							}catch (Exception e){
								e.printStackTrace();
							}
						}
					}
					Run：
					public class Run {
						public static void main(String[] args) {
							Service service = new Service();
							try {
								ThreadA a  = new ThreadA(service);
								ThreadB b = new ThreadB(service);

							  Thread threadA = new Thread(a);
							  Thread threadB = new Thread(b);
							  threadA.setName("A");
							  threadB.setName("B");
							  threadA.start();
							  threadB.start();
							} catch (Exception e) {
								e.printStackTrace();
							}

						}
					}
					ThreadA:
					public class ThreadA implements Runnable{
						private Service service;

						public ThreadA(Service service) {
							this.service = service;
						}

						@Override
						public void run() {
							service.read();
						}
					}
					ThreadB:
					public class ThreadB implements Runnable{
						private Service service;

						public ThreadB(Service service) {
							this.service = service;
						}

						@Override
						public void run() {
							service.read();
						}
					}
				运行结果：
					获得读锁A 1526439206124
					获得读锁B 1526439206125
				结果显示，在线程A.sleep不释放锁的情况下，线程B任然可以进入被锁的代码，大大加快的效率

			1.2.5.2 写写互斥
				直接把上面的例子的service改成下面这样就可以了：
					public class Service {
						public ReentrantReadWriteLock lock = new ReentrantReadWriteLock();
						public void read(){
							try{
								try{
									lock.writeLock().lock();
									System.out.println("获得写锁" + Thread.currentThread().getName() + " " + System.currentTimeMillis());
									Thread.sleep(1000*3);
								}catch (Exception e){
									e.printStackTrace();
								}finally {
								   lock.writeLock().unlock();
								}
							}catch (Exception e){
								e.printStackTrace();
							}
						}
					}
				输出结果为：
					获得写锁B 1526439801097
					获得写锁A 1526439804099
				很明显的B线程执行完后，A线程才进入这块代码。所以写写互斥！
				
			1.2.5.3. 读写互斥
				两个线程a,b，a线程进行读操作，b线程进行写操作。因为互斥，所以一个线程会等待另外一个线程运行完毕后才执行自己的线程！
				例子如下：
				Service：
				public class Service {
					public ReentrantReadWriteLock lock = new ReentrantReadWriteLock();

					public void read(){
						try{
							try{
								lock.readLock().lock();
								System.out.println("获得读锁" + Thread.currentThread().getName() + " " + System.currentTimeMillis());
								Thread.sleep(1000*3);
							}catch (Exception e){
								e.printStackTrace();
							}finally {
							   lock.readLock().unlock();
							}
						}catch (Exception e){
							e.printStackTrace();
						}
					}

					public void write(){
						try{
							try{
								lock.writeLock().lock();
								System.out.println("获得写锁" + Thread.currentThread().getName() + " " + System.currentTimeMillis());
								Thread.sleep(1000*3);
							}catch (Exception e){
								e.printStackTrace();
							}finally {
								lock.writeLock().unlock();
							}
						}catch (Exception e){
							e.printStackTrace();
						}
					}
				}
				Run：
				public class Run {
					public static void main(String[] args) {
						Service service = new Service();
						try {
							ThreadA a  = new ThreadA(service);
							ThreadB b = new ThreadB(service);

						  Thread threadA = new Thread(a);
						  Thread threadB = new Thread(b);
						  threadA.setName("A");
						  threadB.setName("B");
						  threadA.start();
						  threadB.start();
						} catch (Exception e) {
							e.printStackTrace();
						}

					}
				}
				ThreadA：
				public class ThreadA implements Runnable{
					private Service service;

					public ThreadA(Service service) {
						this.service = service;
					}

					@Override
					public void run() {
						service.read();
					}
				}
				ThradB：
				public class ThreadB implements Runnable{
					private Service service;

					public ThreadB(Service service) {
						this.service = service;
					}

					@Override
					public void run() {
						service.write();
					}
				}
				运行结果:
					获得读锁A 1526440490027
					获得写锁B 1526440493027
				结果显而易见，互相交替运行的！
