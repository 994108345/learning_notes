�����������java��������ʼ�
1 �߽�java
1.1 ����
	java�����룺һ�α�д����������
1.2 Java������ϵ
	Sun�ٷ�����java������ϵ���������¼������֣�java����������ԡ�����Ӳ��ƽ̨�ϵ��������Class�ļ���ʽ��Java API��⡢������ҵ�����Ϳ�Դ�����ĵ�������⡣
	���ǰ�java����������ԡ�Java�������Java API����������ͳ��ΪJDK��Java Development Kit����JDK������֧��Java���򿪷�����С����
	��Java API ����е�Java SE API�Ӽ���Java�������ΪJRE��Java Runtime Evironment����JRE��֧��Java�������еĻ�����
1.4.5 64λ�����
	������64λϵͳ�ϵ�javaӦ����Ҫ���ĸ�����ڴ棬ͨ����32Ϊϵͳ��������10%~30%��ԭ���ָ�����ͺ͸����������Ͷ��벹�׵�ԭ��

2 �Զ��ڴ�������
2.2 ����ʱ��������
	Java�������ִ��java�����ʱ��������������ڴ滮��Ϊ���ɸ���ͬ����������
2.2.1 ���������
	���壺�����������һ���С���ڴ�ռ䣬�������ÿ��Կ����ǵ�ǰ�߳�ִ�е�������ֽ�����к�ָʾ����
	�����̶߳��и��Եĳ�����������һ���Ӱ�죬���ǳ������ڴ�����Ϊ"�߳�˽��"�ڴ档
	�������ִ�е���Native���������ط����������������Ϊ�ա�ԭ������Ϊ��ʱ���ٿ�һ���߳�ȥִ��native���������̵߳ĳ����������null�����̵߳ĳ�������������Լ�ԭ�����Ǹ����������Ҿ��̴߳�������״̬�������߳�ִ����ϡ�
	����ڴ�����Ψһ��Java������淶��û�й涨�κ�OutOfMemoryError���ڴ�����������������Ϊ�������ʵ��̫С��
	(java��native���εķ������Ǳ��ط���������ƽ̨�ײ㽻���ķ�����������Ĵ洢ֱ�Ӵ��ڱ��ط���ջ�У�
2.2.2 Java�����ջ
	���壺�����ջ��������Java����ִ�е��ڴ�ģ�͡�
	Java�����ջҲ���߳�˽�еģ������������߳���ͬ��
	ÿ��������ִ�е�ʱ��ᴴ��һ��ջ֡�������ڴ洢�ֲ�����������ջ����̬���ӡ��������ڵ���Ϣ��
	ÿһ������������ֱ��ִ����ɵĹ��̣��Ͷ�Ӧ��һ��ջ֡�������ջ�д���ջ����ջ�Ĺ��̡�
	��������˵��ջ���������ջ�еľֲ��������֡�
	�ֲ����������˱����ڿ�֪�ĸ��ֻ����������ͣ�Boolean��byte��char��short��int��long��float��double��java�˸������������ͣ� ���������ú�returnAddress���͡�
	����64λ���ȵ�long��double�������ݻ�ռ��2���ֲ������ռ䣬�������������ֻռ��һ����
	�ֲ�������������ڴ�ռ��ڱ����ڼ���ɷ��䡣�ڷ��������ڼ䲻���޸ľֲ�������Ĵ�С��
	�����������쳣���
		�쳣1��StackOverflowError���߳������ջ��ȴ����������������
		�쳣2��OutOfMemoryError�������չʱ�޷����뵽�㹻���ڴ�ʱ���׳����쳣��
2.2.3 ���ط���ջ
	���ӵ����ú�Java�����ջ���ơ��������������ջΪ�����ִ��java�������񣬶����ط���ջ��Ϊ�����ʹ�õ���Native��������Ҳ���׳�ͬ���������쳣��StackOverflowError��OutOfMemoryError��
2.2.4 Java��
	�Դ����Ӧ����˵��java��������������ڴ�������һ�顣
	java���Ǳ������̹߳����һ�����������������ʱ������
	��������ΨһĿ�ľ��Ǵ�Ŷ���ʵ����
	Java���������ռ����������Ҫ����
	Java�ѿ��Դ��������ϲ��������߼����������ڴ�ռ䡣
2.2.5 ������
	�Ǹ����̵߳Ĺ�������
	�����ڴ洢�Ѿ�����������ص�����Ϣ����������̬������jdk 1.7֮ǰ������ʱ�����������Ĵ�������ݡ�
	��java��һ������Ҫ�������ڴ�Ϳ���ѡ��̶���С���ҿ���չ��
	����ѡ��ʵ�������ռ����������ռ���Ϊ���������Ƚ��ٳ��֡�
	�ܶ��˳Ʒ�����Ϊ�����ô�������ʵ��׼ȷ��
	���������ڴ����Ŀ����Ҫ�����Գ����صĻ��պͶ����͵�ж�ء�
	���������޷������ڴ��������ʱ���׳�OutOfMemoryError�쳣��
2.2.6 ����ʱ������
	�Ƿ�������һ���֡�Class�ļ��г�������Ϣ�����ڴ�ű��������ɵĸ����������ͷ������ã��ⲿ��������������غ�ŵ������������г������С�
	һ����˵��������Class�ļ��������ķ��������⣬����ѷ��������ֱ������Ҳ�洢������ʱ�������С�
	�߱���̬�ԣ������������ڼ佫�µĳ���������С����������õ����ľ���String��intern������
	���������޷����뵽�ڴ�ʱ���׳�OutOfMemoryError�쳣��
2.2.7 ֱ���ڴ�
	ֱ���ڴ沢�������������ʱ��������һ���֣�Ҳ����java������淶�ж�����ڴ����򣬵��Ǳ�Ƶ����ʹ�ã�Ҳ�ᵼ��OutOfMemoryError�쳣��
	����ͨ��IO�ķ�ʽ��ʹ��Native����֪����������ڴ棬Ȼ��ͨ��һ���洢��java�������DirectByteBuffer������Ϊ����ڴ�����ý��в�����
	��ռjava�Ѵ�С�����ǿ϶�ռ�����ڴ��С
	����ľ���NIO ��ֱ�ӻ���������ͨ��������ֱ�ӻ����������ݾ��Ǵ���ֱ���ڴ��С���ͨ��������ֵ�ʹ��ڶ��ڴ��У�
2.3 �������
	Object obj = new Object();
	��Object obj���ᷴӳ��Javaջ�ı��ر������У���Ϊһ��reference�������ݳ��֡�����new Object()���ⲿ�����彫�ᷴӳ��Java���С���Java���б�������ܲ鵽�˶����������ݵĵ�ַ��Ϣ����Щ������洢�ڷ������С�
	reference������java������淶��ֻ�涨��һ��ָ���������ã�����ʵ�ַ�ʽ������
		ʹ�þ����Java���лỮ�ֳ�һ���ڴ�����Ϊ����أ�reference�д洢�ľ��Ƕ���ľ����ַ������а����˶���ʵ�����ݺ��������ݸ��Եľ������͵�ַ��
		ʹ��ָ�룺java�Ѷ����оͱ��뿼����η��÷����������ݵ������Ϣ��reference��ֱ�Ӵ洢�ľ��Ƕ����ַ��
		��ȱ�㣺
			����ĺô���reference�д洢�����ȶ��ľ����ַ���ڶ����ƶ�����������ʱ�ᾭ���ƶ����󣬼�������ʱֻ��ı����е�ʵ������ָ�롣��reference������Ҫ���ı�
			ָ��ĺô����ٶȸ��죬��ʡ��һ��ָ�붨λ��ʱ�俪����
	����������������ջ�����ط���ջ�����������̶߳��������̶߳���
2.4 ʵս:OutMemoryError��OOM���쳣
2.4.1 Java�����
	���ֶ��ڴ�������쳣��ջ��Ϣ��
	java.lang.OutOfMemoryError:Java heap space...
	�����ڴ��������Ҫ�������ڴ�й¶�����ڴ����
	������ڴ�й¶���鿴й¶����GC Roots�������������ڴ�й¶���Ƕ����Ѿ������ˣ����Ա������ˣ�����û�м�ʱ�����գ�
���ӣ�
public class OutOfMemoryErrorTest {
    List resultList = new ArrayList<>();
    @Test
    public void heapException(){
        int i = 1;
        while(true){
            byte[] b = new byte[1024*100];��//100Kһ��
            resultList.add(b);
            System.out.println(String.format("��ӡ��%s",i));
            i++;
        }
    }
}
����java.lang.OutOfMemoryError: GC overhead limit exceeded��
�������İٷ�֮98��ʱ����GC�����������յ��ڴ�С�ڰٷ�֮2���ͻᱨ�����
����������һ���byte���ĳ�100Mһ�ξͳɹ���
public class OutOfMemoryErrorTest {
    List resultList = new ArrayList<>();
    @Test
    public void heapException(){
        int i = 1;
        while(true){
            byte[] b = new byte[1024*1024*100];//100Mһ��
            resultList.add(b);
            System.out.println(String.format("��ӡ��%s",i));
            i++;
        }
    }
}
����java.lang.OutOfMemoryError: Java heap space
ע�⣺java���ڴ�ŵ��ǳ�Ա�������ֲ��������������ջ��ģ�
2.4.2 �����ջ�ͱ��ط���ջ���
	���׳��������쳣��
		StackOverflowError�쳣������߳�������ȴ��������������������ȣ��׳����쳣
			�쳣��Ϣ��Exception in thread ��main�� java.lang.StackOverflowError
		OutOfMemoryError�쳣�����������չջʱ�޷������㹻���ڴ�ռ䡣
			�쳣��Ϣ��Exception in thread ��main�� java.lang.OutOfMemoryError:unablw to create new native thread
	-Xoss ���������ñ��ط���ջ��С����Ȼ���ڣ���ʵ��������Ч�ġ�ջ����ֻ��-Xss �����趨��
	-Xss ��������ջ�ڴ�����
	�����߳��£�����������ջ̫֡�󣬻������������̫С�����ڴ��޷������ʱ����������׳�StackOverflowError�쳣��
���ӣ�
	@Test
    public  void jVMStackErrorTest() throws InterruptedException {
        Thread thread = new Thread(new JVMStackError());
        thread.start();
        thread.join();
    }
    class JVMStackError implements Runnable{
        int i = 1;
        void foreachThread(){
            System.out.println(String.format("�ظ�����%s�δ˷���",i));
            i++;
            foreachThread();
        }
        @Override
        public void run() {
            foreachThread();
        }
    }
����Exception in thread "Thread-0" java.lang.StackOverflowError
	�Ƶ��ڴ棺Ʃ��32ΪWindows������2GB��������ṩ�˲���������java���ڴ�ͷ��������������ڴ�����ֵ��ʣ����ڴ�2GB����ȥXmx���������������ټ�ȥMaxPermSize����󷽷�������������������������ڴ��С�����Ժ��Ե��������������̱������ĵ��ڴ治�������ڣ�ʣ�µ��ڴ���������ջ�ͱ��ط���ջ���Ϸ֡���
	����ǽ������̵߳��µ��ڴ�������ٲ��ü����߳��������64λ�����������¡���ֻ��ͨ�����Ѻͼ���ջͬ������ȡ������̡߳�
���ӣ�
@Test
    public  void createThreadErrorTest() throws InterruptedException {
        int i = 1;
        while(true){
            Thread thread = new Thread(new CreateThreadError(i));
            thread.start();
            i++;
        }
    }
    class CreateThreadError implements Runnable{
        int i ;
        public CreateThreadError(int i) {
            this.i = i;
        }
        @Override
        public void run() {
            System.out.println(String.format("��%s���߳�",i));
        }
    }
ע�⣺��Ϊjava�̵߳�ӳ�䵽����ϵͳ���ں��߳��ϣ������ֺܴ���ջ������������������Ȥ�������ԣ�

2.4.3 ����ʱ���������
	String.intern()���Native��������������Ѿ�������һ�����ڴ�String������ַ������򷵻ط����������ַ�����String���󣬷��򣬽���String����������ַ�����ӵ��������У����ҷ���String��������á�
	-XX��PermSize��-XX:MaxPermSize���Ʒ������Ĵ�С���Ӷ����Ƴ����ص���������Ϊ�������ڷ������С�
	�쳣��Ϣ��
		Exception in thread ��main�� java.lang.OutOfMemoryError: PerGen space�����˵�������ǳ������ڴ������
���ӣ�
 @Test
    public void constantPoolError(){
        List resultList = new ArrayList<>();
        long l = 1;
        StringBuilder sbf = new StringBuilder("1");
        while(true){
            resultList.add(sbf);
            /*ѭ��һ����10M��С*/
            for (int i = 0; i < 1024*1024*100; i++) {
                sbf.append("1");
            }
            String.valueOf(sbf).intern();
            System.out.println(l++ + sbf.toString());
        }
    }
�ǵ��Ȱѳ����ش�С����Сһ�㣬��Ȼjava���ڴ���ȱ���
2.4.4 ���������
	���������Class�������Ϣ�����������������η��������أ��ֶ����������������ȡ�
	��������ͨ�����似��������̬����class�ļ���
	�ھ�����̬���ɴ���Class��Ӧ���У���Ҫ�ر�ע����Ļ���״��
2.4.5 ����ֱ���ڴ����
	����ͨ��-XX:MaxDirectMemorySizeָ���������ָ������Ĭ����java�Ե����ֵ��-Xmx��һ����








