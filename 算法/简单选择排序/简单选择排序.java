��ѡ������
����:ͨ���ؼ��ֵıȽϡ���n-i+1����¼��ѡ���ؼ�����С�ļ�¼�����͵�i����¼����֮��
��⣺ÿ���ҵ���Сֵ���Ž���ֵ������
@Test
public void easyChoseSort(){
	int[] a = {3,5,1,2,9,8,6,7,10,4};
	int middle = 0;//ֵ������ʱ����м�ֵ
	for (int i = 0; i < a.length; i++) {
		int minIndex = i;//��Сֵ�������±�
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