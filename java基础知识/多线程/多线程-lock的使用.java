ʹ��ReentrantLock��
	����jdk1.5�����ӵ�ReentrantLock�ࡣ
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
	ʹ��lock()������ȡ����ʹ��unlock()�����ͷ��������Ա��������������м�Ĵ������ͬ���ġ�

	1.2 Condition
		�ؼ���sunchronized��ͨ��wait() notify() notifyAll()��������ʵ�ֵȴ�/֪ͨģ�͡���ReentrantLockҲ����ʵ��ͬ���Ĺ��ܣ�������Ҫ������Condition������Ա�synchronized��condition������Ϊ���Դ������conditionʵ����
		ʹ��notify/notidyAll�����֪ͨ�ģ�����condition����ѡ����֪ͨ��
		ע�⣺�ڵ���await����֮ǰ��������lock()�������ͬ������������Ȼ�ᱨ��
		�������£�
		�����ࣺ
		public class MyService {
			Lock lock = new ReentrantLock();
			private Condition condition = lock.newCondition();
			public void await(){
				try{
					lock.lock();
					System.out.println("awairʱ��Ϊ"+System.currentTimeMillis());
					condition.await();
				}catch (Exception e){
					e.printStackTrace();
				}finally {
					lock.unlock();
					System.out.println("�����ͷ���");
				}
			}

			public void signal(){
				try{
					lock.lock();
					System.out.println("signalʱ��Ϊ" + System.currentTimeMillis());
				}catch (Exception e){
					e.printStackTrace();
				}finally{
					lock.unlock();
				}
			}
		}
		�߳�A�ࣺ
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
		Run������:
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
		���н����
		awairʱ��Ϊ1526374729023
		signalʱ��Ϊ1526374730009

		Object���wait()�൱��Condition���await()
		Object���notify()�൱��Condition���notify()
		Object���notifyAll()�൱��Condition���notifyAll()

		1.2.1
			ʵ�ֶ��conditionʹ��
				���Ǵ������condition��������һ����дһ��await()��signal()��
				public class MyService {
					Lock lock = new ReentrantLock();
					private Condition conditionA = lock.newCondition();
					private Condition conditionB = lock.newCondition();
					public void awaitA(){
						try{
							lock.lock();
							System.out.println("��ǰ�߳�Ϊ"+Thread.currentThread().getName()+"��awaitAʱ��Ϊ"+System.currentTimeMillis());
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
							System.out.println("��ǰ�߳�Ϊ"+Thread.currentThread().getName()+"��signalAʱ��Ϊ" + System.currentTimeMillis());
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
							System.out.println("��ǰ�߳�Ϊ"+Thread.currentThread().getName()+"��signalAll_Aʱ��Ϊ" + System.currentTimeMillis());
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
							System.out.println("��ǰ�߳�Ϊ"+Thread.currentThread().getName()+"��awaitBʱ��Ϊ"+System.currentTimeMillis());
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
							System.out.println("��ǰ�߳�Ϊ"+Thread.currentThread().getName()+"��signalBʱ��Ϊ" + System.currentTimeMillis());
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
							System.out.println("��ǰ�߳�Ϊ"+Thread.currentThread().getName()+"��signalAll_Bʱ��Ϊ" + System.currentTimeMillis());
							conditionB.signalAll();
						}catch (Exception e){
							e.printStackTrace();
						}finally{
							lock.unlock();
						}
					}
				}
				Run��
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
				��������
				��ǰ�߳�ΪThread-0��awaitAʱ��Ϊ1526376205793
				��ǰ�߳�ΪThread-1��awaitAʱ��Ϊ1526376205795
				��ǰ�߳�Ϊmain��signalAʱ��Ϊ1526376206779
				��ǰ�߳�Ϊmain��signalBʱ��Ϊ1526376206779

		1.2.2 ʵ��������/������ģʽ��һ��һ�����ӡ
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
						System.out.println("����");
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
						System.out.println("����");
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
			���н��������
						����
						����
						����
						����
						����
						......
			���ʵ�ֶ�Զ��������������ģʽ������������Ҫ�ģ�ֻҪ�ഴ�������̣߳�Ȼ���myservice��condition.signal();�ĳ�condition.signalAll();�Ϳ��Է�ֹ���ּ���״̬��

		1.2.3 ��ƽ����ǹ�ƽ��
			��Lock��Ϊ��ƽ���ͷǹ�ƽ��
				��ƽ����ʾ�̻߳�ȡ����˳��İ����̵߳�˳��������ģ��������ȵõ�FIFO�Ƚ��ȳ�˳��
				�ǹ�ƽ������һ�ֻ�ȡ������ռ���ƣ������������ģ��͹�ƽ����һ���ľ��������Ĳ�һ���ȵõ����������ʽ�������ĳЩ�߳�һֱ�ò�������
			��ƽ�����ӣ�
				//Service��
					public class Service {
						private ReentrantLock lock;

						public Service(boolean isFair) {
							lock = new ReentrantLock(isFair);
						}
						public void serviceMethod(){
							try{
								lock.lock();
								System.out.println("ThreadName=" + Thread.currentThread().getName() + "�������");
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
									System.out.println("�߳�"+Thread.currentThread().getName() + "������");
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
			���к�ᷢ�ִ�ӡ��������������״̬������ǹ�ƽ�����ص�
			��final Service service = new Service(true);�ĳ�final Service service = new Service(false);���Ƿǹ�ƽ����
			���к�ᷢ�ִ�ӡ������Աȹ�ƽ���ң�˵����start()�������̲߳������Ȼ������
		
		1.2.4 ������˵
				.getHoldCount():��ѯ��ǰ�̱߳��ִ������ĸ�����Ҳ���ǵ���lock()�����Ĵ�����
					���ӣ�
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
				.getQueueLength():�������ȴ���ȡ���������̹߳�������
					���ӣ�
					Service:
					public class Service {
						public  ReentrantLock lock = new ReentrantLock();
						public void serrviceMethod1(){
							try{
								lock.lock();
								System.out.println("ThreadName=" + Thread.currentThread().getName() + "���뷽����");
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
								System.out.println("���߳�����" + service.lock.getQueueLength() + "�ڵȴ���ȡ��!");
							} catch (Exception e) {
								e.printStackTrace();
							}

						}
					}
				.getWaitQueueLength():���صȴ���������صĸ�������condition���̹߳�������
				���ӣ�
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
								System.out.println("ThreadName=" + Thread.currentThread().getName() + "���뷽����");
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
								System.out.println("��"+lock.getWaitQueueLength(condition) + "���߳����ڵȴ�condition");
								condition.signalAll();
							}catch (Exception e){
								e.printStackTrace();
							}finally {
								lock.unlock();
							}
						}
					}
				.boolean hasQueuedThread(Thread thread):��ѯָ���߳��Ƿ����ڵȴ���ȡ����
				.boolean hasQueuedThreads():��ѯ�Ƿ����߳����ڵȴ���ȡ������
				.hasWaiters(Condition condition):��ѯ�Ƿ����߳����ڵȴ���������йص�condition����

				.isFair():�ж��Ƿ�Ϊ��ƽ��
				.boolean isHeldByCurrentThread():��ѯ��ǰ�߳��Ƿ񱣳ִ�����
				.isLocked():��ѯ�������Ƿ��������̱߳���

				.void lockInterruptibly():�����Ǯ�߳�δ���жϣ����ȡ����������Ѿ����ж�������쳣��
				.boolean tryLock():���ڵ���ʱ����δ����һ���̱߳��ֵ�����£��Ż�ȡ��������
					ͨ����˵�����������ʱ��lockû�б��κ��̻߳�ȡ�����ǵ���lock.tryLock()����true�����ң���ȡ��lock����������lock.lock();�������û�б�unlock().�����߳��ٵ����������ʱ����false��
				.tryLock(long timeOut,TimeUnit unit):��������ڸ����ȴ�ʱ����û�б������¶��̱߳��֣��ҵ�ǰ�߳�δ���жϣ����ȡ��������
				
				.awaitUninterruptibly():wait()�� ���̱߳�interrrupt���ѻᱨ�쳣��������awaitUninterruptibly�������߳̽���ȴ���interrupted�Ͳ������쳣��
				.awaitUntil:�ô˷������̵߳ȴ�һ��ʱ�䣬���Ա������߳���ǰ���ѣ�
		1.2.5 ʹ��ReentranReadWriteLock���ʹ��
			    ReentrantLock������ȫ��������Ч������ͬһʱ��ֻ��һ���߳���ִ��ReentrantLock.lock()���������������Ȼ��֤���̵߳İ�ȫ�ԣ�����Ч���ر�͡����� ��JDK���ṩ��һ�ֶ�д��ReentrantReadWriteLock�࣬ʹ���ӿ�Ч�ʡ�ע�⣡Ҫ���ǲ�����ʵ�������ķ����У��ſ���ʹ�ö�д�����������������ٶȡ�
				  ��д��Ҳ����������һ���Ƕ�����������Ҳ�ƹ���������һ����д����������Ҳ����������
				  �������֮�䲻���⣬��д�����⣬дд�����⡣
				  ��û���߳̽���д�����ʱ�����ж�ȡ�����Ķ��Thread�����Ի�ȡ����
				  ����д�������Threadֻ���ڻ�ȡд������ܽ���д������������Thread����ͬʱ���ж�ȡ������ͬһʱ��ֻ����һ��Thread����д������

			1.2.5.1 ��������
				���ӣ�ʹ�ö��������ö�����߳�ͬʱ����ͬһ����lock()�Ĵ���
					service:
					public class Service {
						public ReentrantReadWriteLock lock = new ReentrantReadWriteLock();
						public void read(){
							try{
								try{
									lock.readLock().lock();
									System.out.println("��ö���" + Thread.currentThread().getName() + " " + System.currentTimeMillis());
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
					Run��
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
				���н����
					��ö���A 1526439206124
					��ö���B 1526439206125
				�����ʾ�����߳�A.sleep���ͷ���������£��߳�B��Ȼ���Խ��뱻���Ĵ��룬���ӿ��Ч��

			1.2.5.2 дд����
				ֱ�Ӱ���������ӵ�service�ĳ����������Ϳ����ˣ�
					public class Service {
						public ReentrantReadWriteLock lock = new ReentrantReadWriteLock();
						public void read(){
							try{
								try{
									lock.writeLock().lock();
									System.out.println("���д��" + Thread.currentThread().getName() + " " + System.currentTimeMillis());
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
				������Ϊ��
					���д��B 1526439801097
					���д��A 1526439804099
				�����Ե�B�߳�ִ�����A�̲߳Ž��������롣����дд���⣡
				
			1.2.5.3. ��д����
				�����߳�a,b��a�߳̽��ж�������b�߳̽���д��������Ϊ���⣬����һ���̻߳�ȴ�����һ���߳�������Ϻ��ִ���Լ����̣߳�
				�������£�
				Service��
				public class Service {
					public ReentrantReadWriteLock lock = new ReentrantReadWriteLock();

					public void read(){
						try{
							try{
								lock.readLock().lock();
								System.out.println("��ö���" + Thread.currentThread().getName() + " " + System.currentTimeMillis());
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
								System.out.println("���д��" + Thread.currentThread().getName() + " " + System.currentTimeMillis());
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
				Run��
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
				ThreadA��
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
				ThradB��
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
				���н��:
					��ö���A 1526440490027
					���д��B 1526440493027
				����Զ��׼������ཻ�����еģ�
