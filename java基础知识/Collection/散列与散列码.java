散列与散列码
	map是没有迭代也不能循环遍历的，要访问map中的value只能通过用key去访问。
	public class HashMapTest {
		private int num;

		public int getNum() {
			return num;
		}

		public void setNum(int num) {
			this.num = num;
		}

		public HashMapTest(int num) {
			this.num = num;
		}

		@Override
		public String toString() {
			return "HashMapTest{" +
					"num='" + num + '\'' +
					'}';
		}
	}
	public class HashTest {
		private int[] arr = {1,2,3,3};
		@Test
		public void demo1(){
			Map<HashMapTest,String> map = new HashMap<HashMapTest,String>();
			List<HashMapTest> list = new ArrayList<>();
			/*往map中插入数组*/
			for (int i : arr) {
				HashMapTest hashMapTest = new HashMapTest(i);
				map.put(hashMapTest,hashMapTest.toString());
				list.add(hashMapTest);//将key存到集合中，后面要用集合中的key去获取map中的value。
			}
			//for(int i :map)因为map的特殊结构，所以mao没有迭代也没用循环遍历
			System.out.println("map的长度:"+map.size());
			for (HashMapTest hashMapTest : list) {
				String string = map.get(hashMapTest);
				System.out.println("map的value:"+string);
			}
			HashMapTest hashMapTest = new HashMapTest(3);
			String resultStr = map.get(hashMapTest);
			System.out.println("根据key取值：" + resultStr);

		}
	}
	输出结果：
	map的长度:4
	map的key：HashMapTest{num='1'}
	map的value:HashMapTest{num='1'}
	map的key：HashMapTest{num='2'}
	map的value:HashMapTest{num='2'}
	map的key：HashMapTest{num='3'}
	map的value:HashMapTest{num='3'}
	map的key：HashMapTest{num='3'}
	map的value:HashMapTest{num='3'}
	根据key取值：null
	
	上述程序，主要是创建了一个map，在map中存了几个值，然后读取map中的所有值，并打印到控制台。
	根据输出结果，我们会有几个疑问：
	1.为什么map会存了四个值，明明第三个值和第四个值的key看起来是一样的，按道理应该会覆盖。
	2.会什么用一样的key去查询map，会查不到值。
	
	这就要看map源码了。map存值是通过key 的散列码去区分两个key是否一样，即调用key.hashCode()。我们的HashMapTest没有实现重写hashCode()，所以默认使用Object中的hashCode()，Object.hashCode()默认是使用对象的地址计算散列码。再看上诉代码，我们虽然在HashMapTest中设置的值num都是一样的，但是每一个HashMapTest都是我们新创建的，即内存地址不一样，所以map认为key是不相等的。HashMap是使用equals()判断当前的键是否与表中存在的键相同，如果在HashMap中没有重写equals()，默认是使用Object的equals()，Object的equals()是判断两个值的地址是否相同。
	为了改正上述的现象。我们需要重写HashMapTest的hashCode()和equals():
	
	正确的equals要满足五个条件
	1.自反性：对任意x,x.equlas(x)一定返回true。
	2.对称性：对任意x和y，如果y.equals(x)一定返回true。
	3.传递性：对任意x，y，z，如果有x.equals(y)返回true，y.equals(z)返回true，则x.equals(z)一定返回true。
	4.一致性：对任意x和y，如果对象中用于等价比价的信息咩有改变，那么无论调用x.equals(y)多少次。返回的结果应该保持一致。
	5.对任何不是null的x。x.equals(null)一定返回false；
	
	为了修改上诉代码的问题，我们来重写hashcode()和 equals()
	@Override
    public int hashCode() {
        return num;
    }

    @Override
    public boolean equals(Object obj) {
        if(obj instanceof HashMapTest && num==((HashMapTest) obj).num){
           return true; 
        }
        return false;
    } 
	因为此时HashMapTest就一个属性，所以hashCode直接返回num当做散列码就够了，equals方法判断HashMapTest中的每一个属性是否相等，从而判断对象是否相等，和内存地址无关。如果instanceof左边参数的null，它会返回false。所以a.euals(null)是不会报错的;
	
1.1 理解HashCode()
	java中使用了散列的数据结构：HashSet、HashMap、LinkedHashSet、LinkedHashMap
	
	
	
	
	
	

