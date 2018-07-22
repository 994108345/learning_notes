Obejct类
public boolean equals(Object obj) {return (this == obj);}
	Object的eauls方法默认还是判断两个对象的存储地址

public String toString() {return getClass().getName() + "@" + Integer.toHexString(hashCode());}
	Object的toStrng方法默认返回，类的全限定类型和哈希码

public final void wait() throws InterruptedException {
        wait(0);
    }

public final native void wait(long timeout) throws InterruptedException;
	
protected void finalize() throws Throwable { }
	该方法是被垃圾收集器调用，当对象没有其他引用时，就会被垃圾回收器回收，回收前，会调用该方法。一般在该方法中，我们会去释放一些资源。例如文件流，会话对象等。












































