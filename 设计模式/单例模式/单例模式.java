����ģʽ���壺ȷ��һ����ֻ��һ��ʵ�������ṩһ��ȫ�ַ��ʵ㡣
��Ҫ���⣺1.�ö��߳̿�����ɴ������ҡ�2.����õ��������������Ϳ��ܴ����������ģʽ��
���������1.��volatile�ؼ��ֺ�synchronized˫�ؼ�������2.ָ����ͬһ������������ء�
һ�㵥��ģʽ������д������һ�ֵĶ�������ģʽ������һ�ֽ�����������ģʽ
��������ģʽ������Ҫ��ʱ���ٴ���
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
�ж����Σ�Ҳ��˫�����������һ���ж�����Ϊ�����uniqueInstance������nullʱ����ȫû�б�Ҫ������룬���������������Խ�ʡ��Դ��

��������ģʽ�����Ȳ����ˣ����ھ�Ҫ����
	public class SingleDemo {
    public volatile static SingleDemo uniqueInstace = new  SingleDemo();

    private SingleDemo() {
    }

    public static SingleDemo getInstance(){
        return uniqueInstace;
    }
}
ֱ���þ�̬��ʼ���ķ�ʽ�ڼ������ʱ����Լ�ʵ������
