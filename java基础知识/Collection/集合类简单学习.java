Collection��һ������Ԫ�ص����У���ЩԪ�ض�����һ�����������
	List�����밴�����˳�򱣴�Ԫ��
	Set�������ظ�Ԫ��ʱ��ֻ����һ����
	Queue�������Ŷӹ�����ȷ������Ĵ��˳��ֻ�������ٷ�����һ�˲��������һ���Ƴ�����
Map��һ��ɶԵġ���ֵ�ԡ�����������ʹ�ü�������ֵ��

�����඼�����Լ������Լ��ĳߴ�
Arrays:public static <T> List<T> asList(T... a);��һ�����ŷָ����б��������ת����List����
Collection:boolean addAll(Collection<? extends E> c);

List:
	ArrayList:�����ձ������˳�򱣴�Ԫ�ء��������Ԫ�ؿ죬������Ƴ�Ԫ�������ײ�ʵ�ֲ������Ա�ṹ������������ʿ죬���ɾ������
	LinkedList:�����ձ������˳�򱣴�Ԫ�ء�������ʷ�����Խ��������Ƴ��Ͳ���ܿ죬�ṩ���Ż���˳����ʡ��ײ�ʵ�ֲ���˫������ṹ��������������������ɾ���졣
List����equals�ж��Ƿ�����Ǻ���ͨ�����ǲ�һ���ġ�
Set:
	HashSet:�����Ļ�ȡԪ�ط�ʽ��������Ԫ�ص�˳��
		��HashSet��add����ԭ�룺
private transient HashMap<E,Object> map;
private static final Object PRESENT = new Object();
public boolean add(E e) {
        return map.put(e, PRESENT)==null;
}
Set�ڲ�ʵ����ͨ��map��ֵ������ӵĶ�����map��key��valueΪ������һ��Object���͵�PRESENT��

	TreeSet������Ԫ�ص�˳����ʹ��TreeSet�����ս�������򱣴����
	LinkedHashSet�����ձ���ӵ�˳�򱣴����

Map:
	HashMap:�ṩ�����Ĳ��Ҽ�����
	TreeMap:���ձȽϽ�����򱣴����
	LinkedHashMap:���ղ���˳�򱣴����ͬʱ��������HashMap�Ĳ�ѯ�ٶȡ�

List:
	contains()��ȷ��ĳ�������Ƿ����б��С�
	remove:�Ƴ�ĳ������
	indexOf():���ָö�����List������λ�õ��������
	��Щ���������õ�equals()����������Ҫ��ʶ��List����Ϊ����equals()����Ϊ�������仯��
	subList():������ӽ̴���б��д���һ��Ƭ��
	containAll();ȷ��һ���б��еĶ����Ƿ񶼴�����ָ���б���
	replace();�滻ָ��λ�õĶ���
	addAll():�����е�List��λ׷��list
	toArray������������ת��������
	����������������һ���������Ĺ����Ǳ�����ѡ�������еĶ��󣬶��ͻ��˳���Ա����ָ�����ϵ�����еĵײ�ṹ�����⣬������ͨ���������������󣺴������Ĵ���С����ˣ��������Լ����Ե�������Щ��ֵ�����
		.java�ĵ�����ֻ�ܵ����ƶ�
		.ʹ��iterator()����Ҫ����������һ��Iterator��Iterator��׼���÷������еĵ�һ��Ԫ��
		.ʹ��next()��������е���һ��Ԫ��
		.ʹ��hasNext()����������Ƿ���Ԫ��
		.ʹ��remove()���������½����ص�Ԫ��ɾ����
	ListIterator��
		�Ǹ�ǿ���Iterator�������ͣ���ֻ�����ڸ���List��ķ��ʡ�
		ListIterator����˫���ƶ���
		������set()�����滻�����ʹ������һ��Ԫ�ء�
		����listIterator()��������һ��ָ��List��ͷ�ĵ�������������listIterator(n)����һ��ָ���n��λ�õĵ�������

	LinkedList��
		LinkedList����˿���ʹ����Ϊջ�����С���˫�˶��еķ�����
		element���������ر�ͷ�ĵ�һ��Ԫ�أ����listΪ�գ����׳��쳣NoSuchElementExeception
		getFirst���������ر�ͷ�ĵ�һ��Ԫ�أ����listΪ�գ����׳��쳣NoSuchElementExeception
		peel():���ر�ͷ�ĵ�һ��Ԫ�أ����listΪ�գ��򷵻�null
		removeFirst�������Ƴ��������б��ͷ�������б�Ϊ��ʱ�׳�NoSuchElementExeception
		remove�������Ƴ��������б��ͷ�������б�Ϊ��ʱ�׳�NoSuchElementExeception
		pull�������Ƴ��������б��ͷ�������б�Ϊ��ʱ����null
		addFirst��������ĳ��Ԫ�ز��뵽�б��β��
		add��������ĳ��Ԫ�ز��뵽�б��β��
		addLast��������ĳ��Ԫ�ز��뵽�б��β��
		removeLast�������Ƴ������б�����һ��Ԫ�ء�

		ջ(Stack):ָ����ȳ���������
			LinkedList�����ܹ�ʵ��ջ�����й��ܷ���
			peek����:����ջ��Ԫ�أ����ǲ��������ջ���Ƴ���
			pop():����ջ��Ԫ�أ����Ƴ�
			push():��ջ�����Ԫ�ء�
Set
	�������ظ���Ԫ�ء�Set�ʹ�����ٲ��Թ����ԣ�����Ժ����׵�ѯ��ĳ�������Ƿ���ĳ��Set�С�
	Set��Collection������ȫһ���Ľӿڣ�ֻ��ʵ�ֲ�ͬ��
	HashSet���ݴ�ŵ�˳�����ҵģ�ʹ����ɢ�С�
	TreeSet��Ԫ�ش洢�ں�������ݽṹ�С�
Map
	��ֵ��
Queue
	�Ƚ��ȳ���������һ�˲��룬һ��ȡ����
	LinkedList�ṩ�˷���֧�ֶ��У�����ʵ����Queue�ӿڡ�
	���Դ�����ʱ������ת�͡�
	Queue queue = new LinkedList();
	offer():��һ��Ԫ�ز��뵽��β�����߷���false��
	peek���������Ƴ�������£����ض�ͷ,�������Ϊ�շ���null
	element���������Ƴ�������£����ض�ͷ���������Ϊ���׳�NoSuckElementException
	poll()���Ƴ������ض�ͷ������Ϊ�շ���null
	remove():�Ƴ������ض�ͷ���������Ϊ���׳�NoSuckElementException
	�Զ���װ���ƻ��Զ��ؽ�nextInt()������intת��ΪInteger���󣬽�charת��Ϊcharacter
		priorityQueue:
			���ȶ��У�������Ϊ������Щ�����ȳ����С�
			ͨ��Comparator���޸�˳��
Collection��Iterator

ConcurrentMap�ӿڼ���ʵ��ConcurrentHashMap()���������ڶ��̵߳ġ�
CopyOnWriteArrayList��CopyOnWriteArraySet�����ڶ��߳�

Collections
	nCopy��int num;object object��������ָ�������ļ���
	fill():��һ�������滻Ŀ�꼯���е����ж���
	

