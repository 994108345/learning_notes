���Ա������������Ԫ�ص��������С�
���Ա��Ϊ˳��洢�ṹ����ʽ�洢�ṹ
Ҫ�㣺��˳�������ڶ��Ԫ�أ����һ��Ԫ����ǰ�������һ��Ԫ���޺�̡�����Ԫ������ֻ��һ��ǰ���ͺ�̡�
1 ˳��洢�ṹ
���壺ָ����һ�ε�ַ�����Ĵ洢��Ԫ���δ洢���Ա������Ԫ�ء�
���Ա�ĳ���Ӧ��С�ڵ�������ĳ���
���Ա����ʼ��1������������0���������Ա��i��Ԫ�ؾ�Ҫ�洢�������±�Ϊi-1��λ�á�
��Ϊ���Ա���ɾ��������������Է��������ռ�Ҫ���ڵ�ǰ���Ա�ĳ��ȡ�
��ƹ��̣�
��Сѧ����ѧ�Ŷӻؼ�Ϊ���ӡ��Ŷӿ϶���һ���˽���һ���ˡ���������һ���ˣ�������Ǹ�λ�ú��ÿ���˶�Ҫ����һ��������� ��һ���ˣ��ߵ��Ǹ��˺�����˶�Ҫ��һ��λ�á�
ʵ�ִ��룺
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
     * ���Ԫ��
     * @param index Ҫ�����λ��
     * @param value Ҫ�����ֵ
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
     * ɾ��Ԫ��
     * @param index Ҫɾ����λ��
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
     * �������Ա��Ԫ��
     * @param index Ҫ����Ԫ�ص�λ��
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
        System.out.println("���ʵ�ֵΪ��"+getValur(2));
    }
}
�ŵ㣺����Ϊ��֮���Ԫ��֮��Ĺ�ϵ��������ռ䣬���Կ��ٶ�ȡ��һλ�õ�Ԫ�ء�
ȱ�㣺ɾ���Ͳ����ʱ����Ҫ�ƶ�����Ԫ�ء�������IAO�������仯�ϴ�ʱ������ȷ���洢�ռ����������ɴ洢�ռ����Ƭ��
2 ��ʽ�洢�ṹ
	����ĵ�һ�����Ĵ洢λ�ý���ͷ��㣬ͷ����ָ�����ǽ���ͷָ�롣
	ͷָ���ͷ��������
	ͷָ�룺
	.ͷָ����ָ����ָ���һ������ָ�룬��������ͷ��㣬����ָ��ͷ����ָ�롣
	.Ͷָ����б�ʾ���ã����Գ���ͷָ����������������
	.���������Ƿ�Ϊ�գ�ͷָ�����Ϊ�ա�ͷָ��������ı�ҪԪ�ء�
	ͷ��㣺
	.ͷ�����Ϊ�˲�����ͳһ�ͷ���������ģ����ڵ�һԪ�صĽ��֮ǰ����������һ�������壬Ҳ���Է�������
	.����ͷ��㣬��զ��һԪ�ؽ��ǰ�������ɾ����һ��㣬��������������Ĳ�����ͳһ�ˡ�
	.ͷ��㲻һ��������ı�ҪԪ��

	2.1 ������Ķ�ȡ

