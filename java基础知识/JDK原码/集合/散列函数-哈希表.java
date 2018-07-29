哈希函数
定义：设计一段逻辑，根据对象匹配出一串数字，作为key存数组的下标，而对象就当做这个数组的元素。即，我们在这个数组查找对象时，不需要遍历整个数组，只需要计算出对象匹配的数字，直接取对应小标的数组元素。
优点：速度快，查找次数几乎是一个常数，用时间复杂度表示就是O(1)。
缺点：只能用数组表示，所以当数组容量达到最大时，需要切换到更大的数组，这个步骤就很耗时间。
但是，我们不能保证每一个对象都只对应一个key，即一旦两个不同的对象匹配了同样的数字，即叫做散列冲突。后面会介绍如何解决散列冲突。

映射函数：
	匹配的过程，我们叫做映射函数，即哈希函数，如何匹配就是哈希表的重点。
	除余法：即选择一个除数b,每一个对象a都对这个除数取余，index = a mod b 即 index = a % b;这是最常见的方法。
	平方散列法：相对于cpu来说，乘法相对于除法省时，所以设计一个乘法的散列函数，index = a * a >> 28
	数字选择法：如果关键字的位数比较多，超过了长整型，这时可以选择数字分布比较均匀的若干位组成新的值作为关键字或直接作为函数值。
	斐波那契散列法：平方散列的缺点是显而易见的，我能们不能选择出一个理想的乘数。答案是肯定可以的
	1，对于16位整数而言，这个乘数是40503 
	2，对于32位整数而言，这个乘数是2654435769 
	3，对于64位整数而言，这个乘数是11400714819323198485 
	理想数是根据斐波那契数列算出来的，即黄金分割法则。
	对我们常见的32位整数而言，公式： 
	index = (value * 2654435769) >> 28

冲突处理：
	线性重新散列
判断哈希算法好坏有四个标准
1.平衡性
2.单调性
3.分散性
4.负载




java基本类型的hashCode：
	java的基本类型是没有hashCode方法。只有对应的封装方法才有hashCode。
	String.hashCode()：
	源码：
		public int hashCode() {
        int h = hash;
        if (h == 0 && value.length > 0) {
            char val[] = value;

            for (int i = 0; i < value.length; i++) {
                h = 31 * h + val[i];
            }
            hash = h;
        }
        return h;
    }
	Byte.hashCode():
	源码：
	 public int hashCode() {
        return (int)value;
    }
	返回的是本身的值，并强制转化成int类型。
	
	Short.hashCode():
	源码：
	public int hashCode() {
        return (int)value;
    }
	返回的是本身的值，并强制转化成int类型。
	
	Integer.hashCode():
	源码：
	public int hashCode() {
        return value;
    }
	即：整型的hashCode就是本身的值。
	
	Long.hashCode()
	源码：
	public int hashCode() {
        return (int)(value ^ (value >>> 32));
    }
	value右移32位即除了2^32，然后次方乘。
	
	Float.hashCode():
	源码：
	public static int floatToIntBits(float value) {
        int result = floatToRawIntBits(value);
        // Check for NaN based on values of bit fields, maximum
        // exponent and nonzero significand.
        if ( ((result & FloatConsts.EXP_BIT_MASK) ==
              FloatConsts.EXP_BIT_MASK) &&
             (result & FloatConsts.SIGNIF_BIT_MASK) != 0)
            result = 0x7fc00000;
        return result;
    }
	
	Character.hashCode():
	源码：
	
	
	
	
