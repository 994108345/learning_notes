8 ������ֽ���ִ������
��������Ǵӱ��ػ�����ת��Ϊ�ֽ��롣
8.1 ����
	ִ��������java���������ĵ���ɲ���֮һ��
	�������һ�������������ĸ��
	�������ִ��������ֱ�ӽ����ڴ�������Ӳ����ָ��Ͳ���ϵͳ�����ϣ��������ִ�������������Լ�ʵ�֡�
	��ͬ�����ʵ�����棬ִ��������ִ��java�����ʱ������н���ִ�кͱ���ִ������ѡ��
	ִ�����涼��һ�µģ���������ֽ����ļ�������������ֽ�������ĵ�Ч���̣��������ִ�н����
8.2 ����ʱջ֡�ṹ
	ջ֡������֧����������з������úͷ���ִ�е����ݽṹ���������������ʱ�������е������ջ��ջԪ�ء�
	ջ֡�洢�˷����ľֲ�������������ջ����̬���Ӻͷ������ص�ַ����Ϣ��
	һ�������ĵ��ÿ�ʼ�������Ͷ�Ӧһ��ջ֡�������ջ��ջ����ջ�Ĺ��̡�
	һ��ջ֡�ķ����ڴ治���ܵ����������ڱ������ݵ�Ӱ�졣����ȡ���ھ���������ʵ�֡�
8.2.1 �ֲ�������
	���壺�ֲ���������һ�����ֵ�洢�ռ䣬���ڴ�ŷ��������ͷ����ڲ�����ľֲ�������
	��Java��������ʱ�򣬾��ڷ�����Code���Ե�max_locals��������ȷ���˸÷�������Ҫ��������ֲ������������
	�ֲ�������������Ա����ۣ�������Slot��Ϊ��С��λ��û����ȷ�涨���С��
	һ��Slot�����Դ��һ��32λ���ڵ��������͡�java��ռ��32λ���ڵ�����������boolean��byte��char��short��int��float��reference��returnAddress�������͡�����regenence�Ƕ�������ã����Ը��ݴ˲鵽�����ٶ��ڴ������кͷ�����������������returnAddress��Ϊ�ֽ���ָ��jsr��jsr_w��ret�����,ָ����һ���ֽ���ָ��ĵ�ַ��
	����54λ���������ͣ��ͷ�������������Slot�ռ䡣java��ȷ�涨��64λ���������;�long��double���֣�������������Slot��ʾ64����Ϊ���߳�˽�е����ݣ���������������Slot����ԭ�Ӳ��������������̰߳�ȫ���⡣
	�ֲ���������Slot�Ŀ����õġ������ӣ�
	ע�⣺ͨ��cmdȥ��ָ���������ļ���ע�����е�ʱ��Ҫ���ϲ��� java -verbose:gc ���ļ����������Ż��ӡgc���������
public static void main(String[] args) {
       byte[] b = new byte[644*1024*1024];
       System.gc();
}
 ��������
 [GC 660432K->660008K(846336K), 0.0024178 secs]
 [Full GC 660008K->659927K(846336K), 0.0261979 secs]
�����ӿ�֪��gc��û�л��վֲ�������ԭ��ֻ�з��������ˣ��Ż���վֲ���������gc�ڷ����У����ڷ�������ǰִ�У����Բ�����ո÷����ľֲ�������
���Ǽ����Ľ���
public static void main(String[] args) {
     {
         byte[] b = new byte[644 * 1024 * 1024];
     }
     System.gc();
}
��������
[GC 660432K->660056K(846336K), 0.0021121 secs]
[Full GC 660056K->659927K(846336K), 0.0304845 secs]
����������֪
�����ӿ�֪�����ǽ�������ʹ������С��������ʱ����ʱ�Ѿ�û�жԸ����Ե��κ����ã�Ӧ�ÿ��Ա�gc���գ����ǿ���������֪������û�б����ա�ԭ������Ȼ���������򣬵���b��ռ�õ�Slot��û�б��������������ã�������ΪGC Rootsһ���ֵľֲ���������Ȼ���ֶ����Ĺ��������Բ��ᱻ���ա�

public static void main(String[] args) {
	{
		byte[] b = new byte[644 * 1024 * 1024];
	}
	int a = 0;
	System.gc();
}
��������
[GC 660432K->660008K(846336K), 0.0024151 secs]
Full GC 660008K->471K(846336K), 0.0212659 secs]
�������֪��gc�����˸����ԡ�ԭ���ǣ��ڴ����뿪��b���Ե�������ʱ����a����ȥռ��bԭ����ռ�õ�Slot�����Դ�ʱb��û�д�����GC Roots�Ĺ�������gcʱ���Ϳ��Ա����ա�
ͨ������Ҳ��ͨ������nullֵȥ�ﵽ����������GC Roots֮��Ĺ��������ǲ����ţ����ǽ�������ʹ�������������ƣ�

����������θ���ʼֵ�Ĺ��̣���һ��׼���׶ε�ϵͳ��ʼ��ֵ���ڶ��ǳ�ʼ���׶θ������Ա�����ֵ�����Ǿֲ�����û�и���ʼֵ�ǲ����õģ�

8.2.2 ������ջ
	���壺����һ�������ȳ���ջ���ڱ����ʱ�򣬽�������д��Code���Ե�max_stacks������֮�С�
	������ջ���ڷ��������ڼ����д�����ȡ���ݡ����磺�����������ͨ��������ջִ�еģ�
	����ջ֡�Ĳ��־ֲ��������ص���һ�����������ڷ�������ʱ���Ϳ��Թ���ͬһ�������ݣ�������ж���Ĳ������ƴ��ݡ�
8.2.3 ��̬����
	ÿ��ջ֡������һ��ָ������ʱ��������ջ֡�������������ã��������������Ϊ��֧�ַ������ù����еĶ�̬���ӡ�
8.2.4 �������ص�ַ
	����ִ�к������ַ�ʽ�˳����������
		1 ִ�����������κ�һ���������ص��ֽ���ָ��з���ֵ�ʹ��ŷ���ֵ���ݸ��ϲ�ķ��������ߡ����ֳ�Ϊ������ɳ��ڡ�
		2 ��ִ�����������쳣�������쳣û���ڷ������еõ�����������ϲ�����߷����κη���ֵ�����Ϊ�쳣��ɳ��ڡ�
	�������ַ��ط�ʽ����Ҫ���ص����������õ�λ�á�
		��������ʱ�������ߵ�PC��������ֵ�Ϳ�����Ϊ���ص�ַ��ջ֡�п��ܻᱣ�����������ֵ��
		�쳣����ʱ�����ص�ַ��ͨ���쳣����������ȷ���ģ�ջ֡һ�㲻�ᱣ���ⲿ����Ϣ��
	�����˳��Ĺ���ʵ�ʵ�����ѵ�ǰջ֡��ջ��
8.2.5 ������Ϣ
	������淶��������һЩ�淶��û����������Ϣ��ջ֮֡�С�ʵ�ʿ����У�һ���Ѷ�̬���ӣ��������ص�ַ�븽����Ϣȫ����Ϊһ�࣬��Ϊջ֡��Ϣ��
8.3 ��������
	�������ò�����ͬ�ڷ���ִ�У���������Ψһ���������ȷ��������һ�����������漰�����ڲ����������й��̡�Class�ļ�����洢�Ķ��Ƿ������ã�������ֱ�����ã�ֻ���ڼ����ڼ����ȷ��Ŀ�귽����ֱ�����á�
8.3.1 ����
	���壺����Ŀ��������д�á����������б���ʱ�ͱ���ȷ�����������෽���ĵ��ó�Ϊ������
	��java�У����ϡ���������֪�������ڲ��ɱ䡱���Ҫ��ķ�����Ҫ�о�̬������˽�з��������ࡣ��������ʺ��ڼ��ؽ׶ν��н�����
	java��������ṩ���������������ֽ���ָ��ֱ��ǣ�
		invokestatic:���þ�̬����
		invokespecial������ʵ��������<init>������˽�з����͸��෽����
		invokevirtual���������е��鷽����
		invokeeinterface�����ýӿڷ�������������ʱ��ȷ��һ��ʵ�ִ˽ӿڵĶ���
	ֻҪ�ܱ�invokestatic��invokespecialָ����õķ������������ڽ����׶�ȷ��Ψһ�ĵ��ð汾����������������о�̬������˽�з�����ʵ���������͸��෽������Ϊ�鷽������֮�෴�����������鷽��������final��ʹ��invokevirtual��ִ�еģ������������޷������ǡ�������Ҳ�Ƿ��鷽����
	��������һ���ĸ���̬���̣�
8.3.2 ����
	���ɵ��ÿ��ܾ�̬Ҳ���ܶ�̬���á�
	1.��̬����
��һ��������˵����
public class StaticDispatch {
    static abstract class Human{}
    static class Man extends Human{}
    static class Women extends Human{}
    public void sayHello(Human guy){
        System.out.println("hello guy!");
    }
    public void sayHello(Man guy){
        System.out.println("hello gentlemen!");
    }
    public void sayHello(Women guy){
        System.out.println("hello lady!");
    }

    public static void main(String[] args) {
        Human man = new Man();
        Human women = new Women();
        StaticDispatch sr = new StaticDispatch();
        sr.sayHello(man);
        sr.sayHello(women);
    }
}
��������
hello guy!
hello guy!
		����������һ�����صĹ��̡�
		Human man = new Man();
		�����е�Human�����ǳ�Ϊ��̬���ͻ���������ͣ������Man���ǳ�Ϊ������ʵ�����͡�
		��̬���ͺ�ʵ�������ڳ����ж����Է���һЩ�仯��
		����
			��̬���͵ı仯��������ʹ��ʱ��������������ľ�̬��������ı䣬���Ҿ�̬�������ڱ����ڿ�֪��
			ʵ�����ͱ仯�Ľ���������ڲſ�ȷ�����������ڱ�������ʱ�򲢲�֪��һ�������ʵ��������ʲô��
		���������ʱ��ͨ�������ľ�̬���Ͷ�����ʵ��������Ϊ�ж����ݡ������ڱ���׶Σ�javac�������͸��ݲ����ľ�̬���;���ʹ���ĸ����ذ汾��
	����������̬��������λ����ִ�а汾�ķ��ɶ���������Ϊ��̬���ɡ���̬���ɵ������Ӧ�þ��Ƿ������ء�
���ǿ�һ�����ص����ӣ�
public class Overload {
    public static void sayHello(Object arg){
        System.out.println("hello Object");
    }
    public static void sayHello(int arg){
        System.out.println("hello int");
    }
    public static void sayHello(long arg){
        System.out.println("hello long");
    }
    public static void sayHello(Character arg){
        System.out.println("hello Character");
    }
    public static void sayHello(char arg){
        System.out.println("hello char");
    }
    public static void sayHello(char... arg){
        System.out.println("hello char...");
    }
    public static void sayHello(Serializable arg){
        System.out.println("hello Serializable");
    }

    public static void main(String[] args) {
        sayHello('a');
    }
}
	�������ע�͵�sayHello(char arg)���������hello int
	���Ƿ���һ���Զ�����ת����'a'���˿��Դ���һ���ַ����⣬�����Դ�������65���ַ���a����Unicode��ֵΪʮ��������65��
	������Ǽ���ע�͵�sayHello(int arg)�������hello long
	�Դ����ƣ�����char-int-long-float-double��˳�����ƥ�䣬���ǲ���ƥ�䵽byte��short���ͣ���Ϊchar��byte��short��ת���ǲ���ȫ�ġ�

	2 ��̬����
		��̬�������д�кܽ��ܵĹ�����
��һ��������������
public class DynamicDispatch {
    static abstract class Human{
        protected abstract void sayHello();
    }
    static  class Man extends Human{
        @Override
        protected void sayHello() {
            System.out.println("man say hello");
        }
    }
    static class Women extends Human{

        @Override
        protected void sayHello() {
            System.out.println("women say hello");
        }
    }

    public static void main(String[] args) {
        Human man = new Man();
        Human women = new Women();
        man.sayHello();
        women.sayHello();
        man = new Women();
        man.sayHello();
    }
}
���н����
man say hello
women say hello
women say hello
ʹ��javap -verbose ClassTest�鿴�ֽ���
 public static void main(java.lang.String[]);
    flags: ACC_PUBLIC, ACC_STATIC
    Code:
      stack=2, locals=3, args_size=1
         0: new           #2                  // class ClassTest$Man
         3: dup
         4: invokespecial #3                  // Method ClassTest$Man."<init>":()V
         7: astore_1
         8: new           #4                  // class ClassTest$Women
        11: dup
        12: invokespecial #5                  // Method ClassTest$Women."<init>":()V
        15: astore_2
        16: aload_1
        17: invokevirtual #6                  // Method ClassTest$Human.sayHello:()V
        20: aload_2
        21: invokevirtual #6                  // Method ClassTest$Human.sayHello:()V
        24: new           #4                  // class ClassTest$Women
        27: dup
        28: invokespecial #5                  // Method ClassTest$Women."<init>":()V
        31: astore_1
        32: aload_1
        33: invokevirtual #6                  // Method ClassTest$Human.sayHello:()V
        36: return
0~15�е�׼������������man��women���ڴ�ռ䣬��Ӧ
Human man = new Man();Human women = new Women();
�����䡣��������16~21�ǹؼ���16~20�������������������ѹ��ջ����������������Ҫִ��sayHello�����������ߣ���Ϊ�����ߣ�17~21�Ƿ�������ָ���ϸ��17��21���ǵ���ָ��invokevirtual,��������ִ��Ŀ�귽������ͬ��ԭ����Ҫ����Ϊincokevirtualָ��Ķ�̬���ҹ��̿�ʼ˵��
	inbokevirtualָ������й���
		1) �ҵ�����ջ���ĵ�һ��Ԫ����ָ��Ķ����ʵ�����ͣ�����C��
		2) ���������C�ҵ��볣���е��������ͼ����ƶ�����ķ���������з���Ȩ��У�飬���ͨ���򷵻����������ֱ�����ã����ҹ��̽�������ͨ���򷵻�java.lang.IllegalAccessError�쳣��
		3) ���򣬰����ι�ϵ�����������ζԸ�����е�2������������֤
		4) ���ʼ��û���ҵ����ʵķ��������׳�java.lang.AbstractMethodError�쳣��
		����invokevirtualָ��ִ�еĵ�һ�������������ڼ�ȷ�������ߵ�ʵ�����ͣ��������ε����е�invokevirtualָ��ѳ������е��෽���������ý������˲�ͬ��ֱ�������ϣ�������̾���java�����з�����д�ı��ʡ����ǰ������������ڸ���ʵ������ȷ������ִ�а汾�ķ��ɹ��̳�Ϊ��̬���ɡ�
	3.������������
		���壺���ݷ��ɻ��ڶ��������������Խ����ɻ���Ϊ�����ɺͶ���ɡ�
		�������壺�����Ľ������뷽���Ĳ���ͳ��Ϊ������������
���忴ʵ����
public class Dispatch {
    static class QQ{}
    static class _360{}
    public static class Father{
        public void hardChoice(QQ arg){
            System.out.println("father chose qq");
        }
        public void hardChoice(_360 arg){
            System.out.println("father chose 360");
        }
    }
    public static class Son extends Father{
        public void hardChoice(QQ arg){
            System.out.println("son chose qq");
        }
        public void hardChoice(_360 arg){
            System.out.println("son chose 360");
        }
    }

    public static void main(String[] args) {
        Father father = new Father();
        Father son = new Son();
        father.hardChoice(new _360());
        son.hardChoice(new QQ());
    }
}
��������
father chose 360
son chose qq
���ȿ�����׶ε�ѡ����̣��ھ�̬�����У�ѡ��Ŀ�귽���ĵ����������㣺1�Ǿ�̬������Fathre����Son��2�Ƿ���������QQ����360����Ϊ�Ǹ���������������ѡ�����Ծ�̬�������ڶ�������͡�
�ٿ������н׶��������ѡ�񣬼���̬���ɹ��̡���ִ��son.hardChoice(new QQ())����ʱ��ֻ��ʵ��������Father����Son��Ӱ��÷�����ѡ��ֻ��һ��������Ϊѡ�����ݣ�����Java���ԵĶ�̬�������ڵ��������͡�

	 4 �������̬���ɵ�ʵ��
	 ���ڶ�̬�����Ƿǳ�Ƶ���Ķ��������Ҷ�̬�����ķ����汾ѡ�����Ҫ����ʱ����ķ���Ԫ�������������ʵ�Ŀ�귽������˻������ܵĿ��ǣ����ڷ������н���һ���鷽�������Ƶ����������

	8.4 ����ջ���ֽ������ִ������
		����������ִ��������ִ��java�����ʱ���н���ִ�кͱ���ִ������ѡ��
	8.4.1 ����ִ��
	8.4.2 ����ջ��ָ�����ڼĴ�����ָ�
		����ջ��ָ�������������ջ���й���
		���ڼĴ���ָ��������Ĵ������й���
		����ջ��ָ�
			�ŵ㣺����ֲ��
			ȱ�㣺ִ���ٶ�����һЩ��
		���ڼĴ�����ָ�
			ȱ�㣻�Ĵ�����Ӳ��ֱ���ṩ������ֱ��������ЩӲ����

	8.4.3 ����ջ�Ľ�����ִ�й���
����򵥵����� �������㣡
public int calc(){
        int a = 100;
        int b = 200 ;
        int c = 300;
        return (a + b) * c;
    }
�ֽ��룺
 public int calc();
    flags: ACC_PUBLIC
    Code:
      stack=2, locals=4, args_size=1
         0: bipush        100
         2: istore_1
         3: sipush        200
         6: istore_2
         7: sipush        300
        10: istore_3
        11: iload_1
        12: iload_2
        13: iadd
        14: iload_3
        15: imul
        16: ireturn
      LineNumberTable:
        line 3: 0
        line 4: 3
        line 5: 7
        line 6: 11
��������
	ִ��ƫ�Ƶ�ַΪ0��ָ�bipushָ������õĽ����ֽڵ����ͳ���ֵ��-128~127�����������ջ��
	ִ��ƫ�Ƶ�ַΪ1��ָ�istore_1����������ջ��������ֵ��ջ����ŵ���һ���ֲ�����Slot�С�3~10������ͬ�������顣
	ִ��ƫ�Ƶ�ַΪ11��ָ�iload_1��ȫ�ֱ������һ��Slot�е�����ֵ��ֵ��������ջ��
	ִ��ƫ�Ƶ�ַΪ12��ָ�iload_2��iload_1���ơ��ѵڶ���Slot������ֵ��ջ
	ִ��ƫ�Ƶ�ַΪ13��ָ�iadd��������ջ��ǰ����ջ��Ԫ�س�ջ�������ͼӷ���Ȼ��������ջ��
	ִ��ƫ�Ƶ�ַΪ14��ָ�iload_3�ѷ��ڵ������ֲ�����Slot�е�300��ջ��������ջ�С�imul�ǽ�������ջ��ǰ����ջ��Ԫ�س�ջ�������ͳ˷���Ȼ��ѽ�����´���ջ��
	ִ��ƫ�Ƶ�ַΪ16��ָ�ireturn�Ƿ������ص�ָ��֮һ���������ִ�в�������ջ��������ֵ���ظ��Ƿ����ĵ����ߡ�����Ϊֹ����������
	����Ĺ��̽�����һ������ģ�ͣ���������ջ��ִ�й�������һЩ�Ż���������ܣ�ʵ�ʵ����й��̲�һ����ȫ���ϸ���ģ�͵���������














