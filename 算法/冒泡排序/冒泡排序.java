ð�������ԭ�������������ݽ��������Ƚϣ�Ȼ��ƫ���ƫС��������һ���ƶ���

1����ð������

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
�ɴ����֪������Ϊ����ÿһ�αȽ϶���Ѵ��ֵ����������
��һ�α�������Ϊ���ֻ������Ƚϡ����ҳ�����ֵ�����������ߡ����Եڶ��α�����ʱ��Ͳ���Ҫ�ٶԱ����һ��ֵ���Դ����ƣ���ѭ����i�˵�ʱ������i��Ԫ���ǲ���Ҫ�Ƚϵġ����� j<a.length-i-1��

2.�Ż�ð������

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
���磬��������ڵ�һ��������������Ҫ���������飬��ʱӦ���������˳�ѭ������������һ��booleanֵ��������һ��ѭ��ʱ��û������������ֵ��˵����ʱ�����������ģ���ʱ��Ҫ�˳�ѭ����