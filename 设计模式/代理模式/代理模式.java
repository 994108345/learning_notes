代理模式

1. 最简单的代理模式
	一个接口或者虚拟类，写一个方法。
	真实对象类
		-实现虚拟类
		-重写方法
	代理对象类
		-实现虚拟类
		-重写方法
		-拥有真实对象的属性
	
	//接口
	public interface Image {
	   void display();
	}

	//代理对象
	public class ProxyImage implements Image{

	   private RealImage realImage;
	   private String fileName;

	   public ProxyImage(String fileName){
		  this.fileName = fileName;
	   }

	   @Override
	   public void display() {
		   //控制
		  if(realImage == null){
			 realImage = new RealImage(fileName);
		  }
		  realImage.display();
	   }
	}

	//真实对象
	public class RealImage implements Image {

	   private String fileName;

	   public RealImage(String fileName){
		  this.fileName = fileName;
		  loadFromDisk(fileName);
	   }

	   @Override
	   public void display() {
		  System.out.println("Displaying " + fileName);
	   }

	   private void loadFromDisk(String fileName){
		  System.out.println("Loading " + fileName);
	   }
	}
	//请求步骤
	public class ProxyPatternDemo {
		
	   public static void main(String[] args) {
		  Image image = new ProxyImage("test_10mb.jpg");

		  //图像将从磁盘加载
		  image.display(); 
		  System.out.println("");
		  //图像将无法从磁盘加载
		  image.display();     
	   }
	}