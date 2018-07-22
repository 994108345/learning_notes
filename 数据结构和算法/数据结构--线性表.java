线性表：零个或多个数据元素的有限序列。
线性表分为顺序存储结构和链式存储结构
要点：有顺序，若存在多个元素，则第一个元素无前驱，最后一个元素无后继。其他元素有且只有一个前驱和后继。
1 顺序存储结构
定义：指的是一段地址连续的存储单元依次存储线性表的数据元素。
线性表的长度应该小于等于数组的长度
线性表的起始是1，但是数组是0，所以线性表第i个元素就要存储在数组下表为i-1的位置。
因为线性表有删除插入操作，所以分配的数组空间要大于当前线性表的长度。
设计过程：
用小学生放学排队回家为例子。排队肯定是一个人接着一个人。如果插进来一个人，插进的那个位置后的每个人都要后退一个，如果走 了一个人，走的那个人后面的人都要近一个位置。
实现代码：
public class LinearTable {
    String[] strs = new String[20];

    public LinearTable() {
        strs[0] = "1";
        strs[1] = "2";
        strs[2] = "3";
        strs[3] = "4";
        strs[4] = "5";
    }
    public void  printData(){
        for (String str : strs) {
            if(str!=null) {
                System.out.print(str + ",");
            }
        }
    }

    /**
     * 添加元素
     * @param index 要插入的位置
     * @param value 要插入的值
     */
    public void add(int index,String value){
        String nextValue = strs[index-1];
        strs[index-1] = value;
        for (int i = index; i < strs.length; i++) {
            if(strs[i] == null){
                strs[i] = nextValue;
                break;
            }else{
                String str1 = strs[i];
                strs[i] = nextValue;
                nextValue = str1;
            }
        }

    }

    /**
     * 删除元素
     * @param index 要删除的位置
     */
    public void delete(int index){
        for (int i = index; i < strs.length; i++) {
            if(strs[i] == null){
                /*strs[i-1] = null;*/
                break;
            }else{
                strs[i-1] = strs[i];
                strs[i] = null;
            }
        }
    }

    /**
     * 访问线性表的元素
     * @param index 要访问元素的位置
     * @return
     */
    public String getValur(int index){
        return strs[index-1];
    }

    @Test
    public  void addTest(){
        add(2,"10");
        this.printData();
    }
    @Test
    public void deleteTest(){
        delete(5);
        this.printData();
    }
    @Test
    public void getValueTest(){
        System.out.println("访问的值为："+getValur(2));
    }
}
优点：无须为表之间的元素之间的关系付出额外空间，可以快速读取任一位置的元素。
缺点：删除和插入的时候需要移动大量元素。当线性IAO的容量变化较大时，难以确定存储空间的容量，造成存储空间的碎片！
2 链式存储结构
	链表的第一个结点的存储位置叫做头结点，头结点的指针我们叫做头指针。
	头指针和头结点的区别：
	头指针：
	.头指针是指链表指向第一个结点的指针，若链表有头结点，则是指向头结点的指针。
	.投指针具有表示作用，所以常用头指针冠以链表代码名字
	.无论链表是否为空，头指针均不为空。头指针是链表的必要元素。
	头结点：
	.头结点是为了操作的统一和方便而设立的，放在第一元素的结点之前，其数据域一般无意义，也可以放链表长度
	.有了头结点，对咋第一元素结点前插入结点和删除第一结点，其操作与其他结点的操作就统一了。
	.头结点不一定是链表的必要元素

	2.1 单链表的读取

