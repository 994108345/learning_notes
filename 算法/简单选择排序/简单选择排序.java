简单选择排序
定义:通过关键字的比较。从n-i+1个记录中选出关键字最小的记录，并和第i个记录交互之。
理解：每次找到最小值，才进行值交换。
@Test
public void easyChoseSort(){
	int[] a = {3,5,1,2,9,8,6,7,10,4};
	int middle = 0;//值交换的时候的中间值
	for (int i = 0; i < a.length; i++) {
		int minIndex = i;//最小值的数组下标
		int left = a[i];
		for (int j = i+1; j < a.length; j++) {
			int right = a[j];
			if(left>right){
				left = right;
				minIndex = j;
			}
		}
		if(i != minIndex){
			middle = a[minIndex];
			a[minIndex] = a[i];
			a[i] = middle;
		}
	}2018/7/19
	for (int i : a) {
		System.out.print(i+",");
	}
}