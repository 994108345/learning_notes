List<Integer> i = new ArrayList<>();
i.add(1);i.add(2);i.add(3);i.add(4);i.add(5);i.add(6);
for (int i1 = 0; i1 < i.size(); i1++) {
	int a = i.get(i1);
	System.out.println(a);
	a = 11;
	System.out.println(i.get(i1));
}
���Ͽ�֪��java�Ļ�������û�����ô��ݡ�
String�Ƚ����⣬��ΪStringBuilder��StringBuffer��ֵ���ݵģ���Stringû�С�
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
ԭ������ΪString�����޸�ֵʱ�����޸����ڴ��ַ����StringBuilder��StringBufferֻ���޸���ֵ��

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
	for (int i = 0; i < objList.size(); i++) {
		Demo demo = objList.get(i);
		System.out.println(demo.getName());
		demo.setName("111");
		System.out.println(objList.get(i).getName());
	}
}
���ϴ����֪�������ǻ������Ͷ������ô��ݡ�












