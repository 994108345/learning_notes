冒泡排序的原理：相邻两个数据进行两两比较，然后将偏大或偏小的数据向一方移动。

1：简单冒泡排序

@Test
    public void normalBubble(){
        int middle;
        for (int i = 0; i < a.length; i++) {
            for(int j = 0; j < a.length-i-1; j++){
                int front = a[j];
                int behind = a[j+1];
                if(front > behind){
                    middle = front;
                    a[j] = behind;
                    a[j+1] = middle;
                }
            }
        }
    }
由代码可知，排序为升序。每一次比较都会把大的值，往右排序。
第一次遍历后，因为数字会两两比较。会找出最大的值，放在了最后边。所以第二次遍历的时候就不需要再对比最后一个值，以此类推，当循环了i此的时候，最后的i个元素是不需要比较的。所以 j<a.length-i-1。

2.优化冒泡排序

@Test
    public void  improveBubble(){
        int middle;
        for (int i = 0; i < a.length; i++) {
            boolean isSwop = false;
            for(int j = 0; j < a.length-i-1; j++){
                int front = a[j];
                int behind = a[j+1];
                if(front > behind){
                    middle = front;
                    a[j] = behind;
                    a[j+1] = middle;
                    isSwop = true;
                }
 
            }
            if(!isSwop){
                break ;
            }
        }
    }
假如，这个数组在第一次排序后就是我们要的有序数组，此时应该让他们退出循环遍历。设置一个boolean值，假如有一次循环时，没有两两交换过值，说明此时数组就是有序的，此时就要退出循环。