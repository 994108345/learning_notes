����ģʽ

1. ��򵥵Ĵ���ģʽ
	һ���ӿڻ��������࣬дһ��������
	��ʵ������
		-ʵ��������
		-��д����
	���������
		-ʵ��������
		-��д����
		-ӵ����ʵ���������
	
	//�ӿ�
	public interface Image {
	   void display();
	}

	//�������
	public class ProxyImage implements Image{

	   private RealImage realImage;
	   private String fileName;

	   public ProxyImage(String fileName){
		  this.fileName = fileName;
	   }

	   @Override
	   public void display() {
		   //����
		  if(realImage == null){
			 realImage = new RealImage(fileName);
		  }
		  realImage.display();
	   }
	}

	//��ʵ����
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
	//������
	public class ProxyPatternDemo {
		
	   public static void main(String[] args) {
		  Image image = new ProxyImage("test_10mb.jpg");

		  //ͼ�񽫴Ӵ��̼���
		  image.display(); 
		  System.out.println("");
		  //ͼ���޷��Ӵ��̼���
		  image.display();     
	   }
	}