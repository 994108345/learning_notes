11
RTTI�����н׶�����ʶ��(Runtime Type Identification)�ļ��
jaaʹ��Class������ִ����RTTI��
ÿ���඼��һ��Class����Ϊ����������˵Ķ�������������������������ʹ�ñ���Ϊ���������������ϵͳ��
Class.forName(String classPath):���ʼ����Ӧ�࣬����ȡ�¶�������Class��ͨ��Class.newInstance()�Ϳ��Դ�����Ӧ����
����Ѿ�֪��Ŀ�����͡�ֱ��ͨ��getClass()������ȡĿ�����������á�
	Class bClass = demo1.getClass();
	System.out.println(bClass.getName());//ȫ�޶�������������������
    System.out.println(bClass.getCanonicalName());//ȫ�޶�������
    System.out.println(bClass.getSimpleName());//��������������
	isInterface()://�����Ƿ�Ϊ�ӿ�
	geInterfaces()://��ĸ���ʵ�ֵĽӿ�
	getSuperclass()://����ĸ���
	newInstance()��//��������ʵ��
2 �����泣��
	java���ṩ��һ���������ɶ�Class�����ã���ʹ�������泣��������Aclass.class�������������򵥻���ȫ����Ϊ�ڱ���ʱ�ͻ��飬�Ͳ���try-catch�ˡ�
	���ڻ����������͵İ�װ������˵���е�����
	boolean��class �ȼ��� Boolean.TYPE���Դ����ơ������Ķ�һ��
	ע�⣺ʹ��".class"����ʽ������Class��������ã��������Զ��ĳ�ʼ����Class����Ϊ��ʹ���������׼������ʵ�ʰ�����������
	1 ���� ���������ִ�С��ò����ǲ����ֽ��룬������Щ�ֽ��봴��һ��Class����
	2 ���� �����ӽ׶ν���֤���е��ֽ��룬Ϊ��̬�����洢�ռ䣬�������Ҫ������������ഴ���Ķ���������������á�
	3 ��ʼ�� ���������г��࣬������ʼ����ִ�о�̬��ʼ�����;�̬��ʼ���顣
	��static final���ε�ȫ�ֱ���������Ҫ��ʼ���༴�ɳ�ʼ����ȫ�ֱ���initialize�����Ѿ���ʼ���ˣ�����
	ֻ������static��ȫ�ֱ����������õ�ʱ��ͱ����ȳ�ʼ�����ࡣ
	ͨ���Class<?> aClass = A.class;
	�����÷�������ͨ�÷�Class aClass = A.class;
	���������÷����﷨ֻ��Ϊ�������������ͼ�飬�ڱ�д�ͻᱨ�����õȵ�����ʱ��֪������
		Class<FancyToy> ftClass = FancyToy.class;
        FancyToy fancyToy = ftClass.newInstance();
        Class<? super FancyToy> up = ftClass.getSuperclass();
        System.out.println(up.getSimpleName());
        Object obj = up.newInstance();
	���Ƿ���obj���ͣ����������Ǹ��࣡��

	Class<? super FancyToy> up = ftClass.getSuperclass();
	Class<? extends FancyToy> up = ftClass.getSuperclass();������������ͺ͸���������Ϳ��Ի��ำֵ

	cast()
		���ײ������󣬲�����ת��ΪClass���õ����͡�������������͵���cast���������������ʵ������
class Building{}
class House extends Building{}
class ClassCasts{
    public static void main(String[] args) {
        Building building = new House();
        Class<House> houseType = House.class;
        House h = houseType.cast(building);
        //�ȼ���
        h = (House)building;
    }
}

RTTI�����н׶�����ʶ����ʽ������
	1 ��ͳ����ת�� ��(Shape)
	2 �����������͵�Class����
	3 �ؼ���instanceof


����
	Class<String> sClass = String.class;
    Method[] methods = sClass.getMethods();//��ø��������з���
    Constructor[] constructors = sClass.getConstructors();


	������ã�
	Class<String> sClass = String.class;
	Method method = sClass.getMethod("wait",String.class);//����1���������� ����2�����������ͣ����Զ��
    method.invoke(sClass.newInstance(),"1");//����1�����ô˷����Ķ���  ����2��������������������Ӧ��

�ն���
	������ŵ��ж�null
	������--ģ�������׮
�ӿ���������Ϣ
	������Ե������˽�з������ڲ��ࡢ�����࣡
	

























