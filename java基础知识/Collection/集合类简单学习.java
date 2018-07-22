Collection：一个独立元素的序列，这些元素都服从一条或多条规则。
	List：必须按插入的顺序保存元素
	Set：插人重复元素时，只会留一个。
	Queue：按照排队规则来确定对象的存放顺序。只允许在荣放弃的一端插入对象，另一端移除对象。
Map：一组成对的”键值对“对象，允许你使用键来查找值。

容器类都可以自己调整自己的尺寸
Arrays:public static <T> List<T> asList(T... a);将一个逗号分隔的列表或者数组转换成List集合
Collection:boolean addAll(Collection<? extends E> c);

List:
	ArrayList:都按照被插入的顺序保存元素。随机访问元素快，插入和移除元素慢。底层实现采用线性表结构，所以随机访问快，添加删除慢。
	LinkedList:都按照被插入的顺序保存元素。随机访问方面相对较慢，但移除和插入很快，提供了优化的顺序访问。底层实现采用双向链表结构，所以随机访问慢，添加删除快。
List根据equals判断是否相等是和普通类型是不一样的。
Set:
	HashSet:是最快的获取元素方式，不关心元素的顺序
		看HashSet的add方法原码：
private transient HashMap<E,Object> map;
private static final Object PRESENT = new Object();
public boolean add(E e) {
        return map.put(e, PRESENT)==null;
}
Set内部实现是通过map存值，且添加的对象当做map的key，value为创建的一个Object类型的PRESENT。

	TreeSet：关心元素的顺序，是使用TreeSet，按照结果的升序保存对象
	LinkedHashSet：按照被添加的顺序保存对象

Map:
	HashMap:提供了最快的查找技术，
	TreeMap:按照比较结果升序保存键，
	LinkedHashMap:按照插入顺序保存键，同时还保留了HashMap的查询速度。

List:
	contains()：确定某个对象是否在列表中。
	remove:移除某个对象
	indexOf():发现该对象再List中所处位置的索引编号
	这些方法都会用到equals()方法，所以要意识到List的行为根据equals()的行为而有所变化。
	subList():允许你从教大的列表中创建一个片段
	containAll();确定一个列表中的对象是否都存在于指定列表中
	replace();替换指定位置的对象
	addAll():在已有的List表位追加list
	toArray（）：将集合转换成数组
	迭代器：迭代器是一个对象，它的工作是遍历并选择序列中的对象，而客户端程序员不必指定或关系该序列的底层结构。此外，迭代器通常被称轻量级对象：创建它的代价小。因此，经常可以见到对迭代器有些奇怪的限制
		.java的迭代器只能单向移动
		.使用iterator()方法要求容器返回一个Iterator。Iterator将准备好返回序列的第一个元素
		.使用next()获得序列中的下一个元素
		.使用hasNext()检查序列中是否还有元素
		.使用remove()将迭代器新近返回的元素删除。
	ListIterator：
		是更强大的Iterator的子类型，它只能用于各种List类的访问。
		ListIterator可以双向移动。
		可以用set()方法替换它访问过的最后一个元素。
		调用listIterator()方法产生一个指向List开头的迭代器，还能用listIterator(n)创建一个指向第n个位置的迭代器。

	LinkedList：
		LinkedList添加了可以使其作为栈、队列。和双端队列的方法。
		element（）；返回表头的第一个元素，如果list为空，则抛出异常NoSuchElementExeception
		getFirst（）：返回表头的第一个元素，如果list为空，则抛出异常NoSuchElementExeception
		peel():返回表头的第一个元素，如果list为空，则返回null
		removeFirst（）：移除并返回列表的头，而在列表为空时抛出NoSuchElementExeception
		remove（）：移除并返回列表的头，而在列表为空时抛出NoSuchElementExeception
		pull（）：移除并返回列表的头，而在列表为空时返回null
		addFirst（）：将某个元素插入到列表的尾端
		add（）：将某个元素插入到列表的尾端
		addLast（）：将某个元素插入到列表的尾端
		removeLast（）：移除返回列表的最后一个元素。

		栈(Stack):指后进先出的容器，
			LinkedList具有能够实现栈的所有功能方法
			peek（）:返回栈顶元素，但是并不将其从栈顶移除。
			pop():返回栈顶元素，并移除
			push():往栈订添加元素。
Set
	不保存重复的元素。Set最常使用在再测试归属性，你可以很容易的询问某个对象是否在某个Set中。
	Set和Collection具有完全一样的接口，只是实现不同。
	HashSet数据存放的顺序是乱的，使用了散列。
	TreeSet将元素存储在红黑树数据结构中。
Map
	键值对
Queue
	先进先出的容器。一端插入，一端取出。
	LinkedList提供了方法支持队列，并且实现了Queue接口。
	所以创建的时候向上转型。
	Queue queue = new LinkedList();
	offer():将一个元素插入到队尾，或者返回false。
	peek（）：不移除的情况下，返回队头,如果队列为空返回null
	element（）：不移除的情况下，返回队头。如果队列为空抛出NoSuckElementException
	poll()：移除并返回队头：队列为空返回null
	remove():移除并返回队头：如果队列为空抛出NoSuckElementException
	自动包装机制会自动地将nextInt()方法的int转换为Integer对象，将char转换为character
		priorityQueue:
			优先队列，可以人为设置哪些数据先出队列。
			通过Comparator来修改顺序。
Collection和Iterator

ConcurrentMap接口及其实现ConcurrentHashMap()它们是用于多线程的。
CopyOnWriteArrayList和CopyOnWriteArraySet，用于多线程

Collections
	nCopy（int num;object object）；创建指定数量的集合
	fill():用一个对象替换目标集合中的所有对象
	

