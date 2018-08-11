List<Integer> i = new ArrayList<>();
i.add(1);i.add(2);i.add(3);i.add(4);i.add(5);i.add(6);
for (int i1 = 0; i1 < i.size(); i1++) {
	int a = i.get(i1);
	System.out.println(a);
	a = 11;
	System.out.println(i.get(i1));
}
由上可知，java的基本类型没有引用传递。
String比较特殊，因为StringBuilder和StringBuffer是值传递的，而String没有。
public void demo2(){
        StringBuilder stringBUilder1 = new StringBuilder("1");
        StringBuilder stringBUilder2 = new StringBuilder("2");
        StringBuilder stringBUilder3 = new StringBuilder("3");
        List<StringBuilder> list = new ArrayList<>();
        list.add(stringBUilder1);
        list.add(stringBUilder2);
        list.add(stringBUilder3);
        for (int i = 0; i < list.size(); i++) {
            StringBuilder s = list.get(i);
            System.out.println(s);
            s.replace(0,1,"11");
            System.out.println(list.get(i).toString());
        }
    }
原理是因为String重修修改值时，是修改了内存地址，而StringBuilder和StringBuffer只是修改了值。

public void demo1(){
	class Demo{
		String name;

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}
	}
	List<Demo> objList = new ArrayList<>();
	Demo demo1 = new Demo();
	demo1.setName("1");
	Demo demo2 = new Demo();
	demo2.setName("2");
	Demo demo3 = new Demo();
	demo3.setName("3");
	Demo demo4 = new Demo();
	demo4.setName("4");
	objList.add(demo1);objList.add(demo2);objList.add(demo3);objList.add(demo4);
	for (int i = 0; i < objList.size(); i++) {<!--保存从middle传来的主数据接口-->
    <dubbo:service interface="com.mtsbw.wms.app.api.UpdateMainDataFromMiddleService" group="${dubbo.group.wms}" provider="wmsProvider" ref="bfOrgService"/>
		Demo demo = objList.get(i);
		System.out.println(demo.getName());
		demo.setName("111");
		System.out.println(objList.get(i).getName());
	}
}
以上代码可知，其他非基础类型都是引用传递。

String a = new String("abc"); String b = new String("abc"); ，则判断 a==b 的值为false，因为分别为a和b创建了新对象。

list的引用传递
		List list = new ArrayList();
        list.add("1");
        list.add("2");
        list.add("3");
        list.add("4");
        Map map  = new HashMap<>();
        map.put("list",list);

        List firstList = (List)map.get("list");
        List updateList = firstList.subList(0,2);
        map.put("list",updateList);
        System.out.println(firstList);
		输出结果：[1, 2, 3, 4]
	由上可知，firstList没有变
我们重新给map的key=list赋值时，此时重新划分了一块内存来存list，而不是让原来的list引用等于updateList的引用。











