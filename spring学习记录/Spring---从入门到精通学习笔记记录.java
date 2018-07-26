1 Spring ����
	Spring �����7��ģ�����
	�����������ṩ��Speing��ܵĺ��Ĺ��ܡ�BeanFactory��Spring������������Ҫ�������ͨ�����Ʒ�ת��Ӧ�ó�������ú������Թ淶��ʵ�ʵ�Ӧ�ó������ֿ���
	Spring Context �� ͨ�������ļ����� Spring ����ṩ��������Ϣ����������BeanFactory֮�ϣ����������˹��ʻ�����Դ���ʵȹ��ܡ�
	Spring Aop��Spring�ṩ���������̵Ĺ��ܣ���ΪSpring�ĺ��ĵĻ��ڿ��Ʒ�ת�ģ����Կ��Ժ����׵�ʹSpring������ע��ΪAOP�ṩ֧�֡�
	Spring Dao���ṩ��һ���򵥶���Ч��JDBC��ʹ�ã�ʹ������DAO������Ӧ��������Ա���ճ�Ӧ�á�
	Spring ORM��Spring�������Լ���JDBCӦ��֮�⣬���ṩ�˶�һЩORM��ܵ�֧�֣�����JDO��HIBERNATE��Mybatis�ȡ�
	Spring web���ṩ�˼򵥵Ĵ���ಿ�������Լ�����������󶨵�����������
	Spring MVC:Spring�ṩ��MVC3ģʽ��ʵ�֣�ʹ�������ǳ����㣬������ǿ�ȿ�����Աʹ�á�

Spring���ص㣺
 1��������õķֲ�ṹ����ʹ������Ա���Ժܼ򵥵ؽ������䡣
 2����IoCΪ���ģ���ʹ������Ա����ӿڱ�̡�
 3�����õļܹ���ƣ��ǵ�Ӧ�ó��򾡿��ܵ�����Ӧ�ó���Ļ������Ӷ�ʹӦ�������˻�����Ӱ��
 4��Spring�����EJB�����������Աʹ��EJB����ʹ��Sring�󻹿��Լ���ʹ��EJB��
 5��Spring MVC���Ժܺõ�ʵ��mvc2�����ṩ�˺ܼ򵥵ĶԹ��ʻ�����Դ���ʵ�֧��:��
 6��Spring����������������õĽ�ϡ�

3 Spring��������
	3.1 �������/����ע��
	������ƣ�IoC��Inversion of Control��
	IoC�Ĺ���ԭ���������ע�룺��DI��Dependency Injection��
	3.1.1 ������ƣ�IoC��
	ʵ�ֱ����������󣬶����ǳ�������ʵ�֡����Ƿ�����Ƶ�һ�ֱ��ַ�ʽ��
	���ӣ�����򵥵�����ӿڱ�̣�ͨ����̬��
	3.1.2 ����ע�루DI��
	Ϊ�˸���˵��IoCģʽ���ص㣬ȡ��һ�������֣���������ע�롣

	3.2 ����ע�������ʵ�ַ�ʽ
	����ע������壺����������ڳ��󣬵����Ҫ������ʵ�ʶ�����������ϵʱ��ͨ��������ע��������ʵ�ʶ���
	����ע�������ʵ�ַ�ʽ��1���ӿ�ע�� 2��Setע�� 3������ע��
	3.2.1 �ӿ�ע��
	�ӿ�ע��ָ�ľ����ڽӿ��ж���Ҫע�����Ϣ����ͨ���ӿ����ע�롣
	public class Business implements IBusiness
	{
		private DataBase db;
		public void createDI(DataBase db){
			this.db = db;
		}
		public void getData(){
			......
			db.getData();
			......
		}
	}
	�����ࣺ
	public class TestBusiness
	{
		private Business business = new Business();
		public void getData(){
			business.createDI(new OracleDataBase()):
			business.getData();
		}
	}
	ͨ���ӿڣ�����java��̬�����ԣ���Ŀ����ȷ�����͡�
	3.2.2 Setע��
	Setע������ڽ���ע������ж���һ��Set���������ڲ����ж�����Ҫע���Ԫ�ء�
	public class Business
	{
		private DataBase db;
		public void setDataBase(DataBase db){
			this.db = db;
		}
		public void getData(){
			......
			db.getData();
			......
		}
	}
	�����ࣺ
	public class TestBusiness
	{
		private Business business = new Business();
		public void getData(){
			business.setDataBase(new OracleDataBase()):
			business.getData();
		}
	}
	3.2.3 ����ע��
	����ע��ָ�ľ����ڽ���ע������ж���һ�����췽�������ڲ����ж�����Ҫע���Ԫ�ء�
	public class Business
	{
		private DataBase db;
		public  Business(DataBase db){
			this.db = db;
		}
		public void getData(){
			......
			db.getData();
			......
		}
	}
	�����ࣺ
	public class TestBusiness
	{
		private Business business = new Business();
		public void getData(){
			business.setDataBase(new OracleDataBase()):
			business.getData();
		}
	}
	3.3 ���õ�xml
	��Spring�У�����Setע��͹���ע���ʱ����Xml�����ĵ���ʹ�õ��﷨�ǲ�һ���ġ�
	
	<bean id="HelloWorld" class="com.gc.action.HelloWorld">
		//ͨ�����캯������ע��
		<constructor-arg index="0">
			<value>HelloWorld</value>
		</constructor-arg>
		//ͨ��Setע��
		<property name="HelloWorld">
			<value>HelloWorld</value>
		</property>
	<bean>
	3.4 ʹ������ע�뷽ʽ
	���и��ĺ�
	Ҫ�ڶ�����ʱ��׼�������е���Դ����ʹ�ù���ע�룬�ڶ����������Ҫע�룬��ʹ��Setע�롣
	3.5 С��
	Spring�ĺ����Ǹ�IoC������

4 Spring�ĺ�������
	Spring�ĺ�������ʵ����IoC����Ŀ�����ṩһ��������ʽ�Ŀ�ܡ�BeanFactory��ApplicationContext���˽�Spring���ĵĹؼ���
	4.1 ʲô��Bean
	Bean������Java��������ģ�͡�ͨ��Bean������������java����Ĺ��ܣ�bean����ʵ�ִ�����ظ����á�
	4.2 Bean�Ļ���֪ʶ
	Spring�У��������������Ҫ�İ�����org.springframework.beans��org.springframework.context�����������У�Ϊ��ʵ��һ��������ʽ�Ŀ�ܣ��������������Java�еķ�����ƣ�ͨ����̬���õķ�ʽ����Ӳ���룬ΪSpring�ķ�������ṩ�˻�����
	�������Ǹ��������Ҫ������BeanFactory��ApplicationContext��
	4.2.1 Bean�ı�ʶ��id��name��
	<bean id="HelloWorld" class="com.gc.action.HelloWorld">
		<property name="msg">
			<value>HelloWorld</value>
		</property>
	</bean>
	id����Bean��Ψһ��ʶ��classָ��bean����Դ,
	Ҳ������name����ָ��id��<bean name="HelloWorld" class="com.gc.action.HelloWorld">
	����ʱ����������ָ��һ����
	���ߵ������ǣ�
	id:����ָ��һ��Bean��id����������CML DTD����Ϊһ��������XMLԪ�ص�ID���Ա���ǣ�����XML�������ܹ�������Ԫ��ָ������ʱ����һЩ�����У�顣��ΪXML�淶�ϸ��޶�����XML ID�кϷ����ַ�������ڿ������б�Ҫʹ�÷Ƿ��ַ�����������ID�涨�ı�׼�������ͨ��name����ָ��һ������id����ָ�����id���ö��źͷֺŷָ���
	4.2.2 Bean���ࣨclass��
	class����ָ����Bean����Դ��ע��дȫ·������

	4.2.3 Singleton��ʹ��
	��Spring�У�Bran���Ա�����Ϊ���ֲ���ģʽ�е�һ�֣�singleton��non-singleton��SpringĬ��Ϊsingletonģʽ��
	singleton��ֻ��һ������ʵ�����ڣ����ж����bena�����󶼻᷵��Ψһ��ʵ����
	non-singleton�����Beanÿ�����󶼻ᴴ��һ���µ�Beanʵ����Ҳ����new��
	<bean id="HelloWorld" class="com.gc.action.HelloWorld" signleton="true">
		<property name="msg">
			<value>HelloWorld</value>
		</property>
	</bean>
	 signleton="true"����singleton��signleton="false"����non-singleton��Ĭ����signleton��
	 4.2.4 Bean������
	 Bean������ֵ������ע�뷽ʽ������set�͹��캯��������ע�롣
	 һ����������ж��bean��bena����������һ��Ҳ���Էֿ����ã�
	 4.2.5 ����nullֵ�Ĵ���
	<bean id="HelloWorld" class="com.gc.action.HelloWorld">
		<property name="msg">
			<value>null</value>
		</property>
	</bean>
	����
	<bean id="HelloWorld" class="com.gc.action.HelloWorld">
		<property name="msg">
			<null/>
		</property>
	</bean>
	����ֵΪnull
	4.2.6 ʹ������depends-on
		Bean��depends-on���Կ���������ʼ��ʹ�����Bean֮ǰ��ǿ��ִ��һ������Bean�ĳ�ʼ����
	<bean id="HelloWorld" class="com.gc.action.HelloWorld" depends-on="date">
		<property name="msg">
			<value>HelloWorld</value>
		</property>
	</bean>
	���ڳ�ʼ��HelloWorld Beanʱ��Ҫ�ȳ�ʼ��date Bean��
	4.3 Bean����������
	Bean���������ڰ���Bean�Ķ��塢Bean�ĳ�ʼ����Bean��ʹ�ú�Bean�������ĸ��׶Ρ�
	4.3.1 Bean�Ķ���
	��Spring�У�ͨ����ͨ�������ĵ��ķ�ʽ������Bean����һ����Ӧ���У����кܶ�Bean�������ĵ��ͻ�ܴ���ʹ���԰���ص�Bean����һ�������ĵ��У����ֶ�������ĵ���
	4.3.2 Bean�ĳ�ʼ��
	Spring�У�Bean�ĳ�ʼ�������ַ�ʽ
	1.�������ĵ���ͨ���ƶ�init-method��������ɡ�
	2.ʵ��org.springframework.beans.factory.InitializingBean�ӿ�
	��һ�֣�������HelloWorld.java������һ������init()��������ɳ�ʼ��������Ȼ���޸������ĵ�config.xml.ָ��Bean��Ҫ��ʼ���ķ�����
	public class HelloWorld
	{
		private  String msg = null;
		public void init(){
			msg = "Hello World";
		}
		public String getMsg(){...}
		public void setMsg(){...}
	}
	�޸������ĵ���ָ��BeanҪ��ʼ���ķ���Ϊinit()������ȥ��ͨ��setterע��ֵ�ķ�ʽ
	<bean id="HelloWorld" class="com.gc.action.HelloWorld" init-method="init">
	</bean>
	�ڶ��֣���Hellowoeld.javaʵ��InitializingBean�ӿڣ�����һ������afterPropertiesSet()������ɳ�ʼ��������Ȼ���޸������ĵ�config.xml
	public class HelloWorld implements InitializingBean
	{
		private String msg = null;
		public void afterPropertiesSet(){
			msg = "Hello World";
		}
		public String getMsg(){...}
		public void setMsg(){...}
	}
	�޸������ļ���Ϣ���£�
	<bean id="HelloWorld" class="com.gc.action.HelloWorld">
	</bean>
	��Ϊ��һ��û�аѴ��������Spring�������Ƽ�ʹ�õ�һ�ַ�ʽ��
	4.3.3 Bean��ʹ��
	3��ʹ��Bean�ķ�ʽ
	��һ��ʹ��BeanWrapper
	HelloWorld helloWorld = new HelloWorld();
	BeanWrapper bw = new BeanWrapperImpl(helloWorld);
	bw.setPropertyValue("msg","HelloWorld");
	System.out.println(bw.getPropertyValue("msg"));
	�ڶ���ʹ��BeanFactory
	InputStream is = new FileInputStream("Config.xml");
	XmlBeanFactory factory = new XmlBeanFactory(is);
	HelloWorld helloWorld = (HelloWorld)factory.getBean("HelloWorld");
	System.out.println(helloWorld.getMsg());
	�����֣�ʹ��ApplicationContext
	ApplicationContext axtx = new FileSystemXmlApplicataionContext("config.xml");
	HelloWorld helloWorld = (HelloWorld)actx.getBean("HelloWorld");
	System.out.println(helloWorld.getMsg());
	4.3.4 Bean������
	���������ַ�ʽ
	��һ���������ļ�ͨ��ָ��destory-method���������
	public class HelloWorld
	{
		private String msg = null;
		public void cleanUp(){
			this.msg = "";
		}
		public String getMsg(){...}
		public void  setMsg(){...}
	}
	����xml
	<bean id="HelloWorld" class="com.gc.action.HelloWorld" destory-method="cleanUp">
	</bean>
	�ڶ���ʵ��org.springframework.beans..factory.DisposableBean�ӿڡ�
	public class HelloWorld implements DisposableBean
	{
		private String  msg = null;
		public void destroy(){
			msg = "Hello World";
		}
		public void cleanUp(){
			this.msg = "";
		}
		public String getMsg(){...}
		public void  setMsg(){...}
	}
	����xml
	<bean id="HelloWorld" class="com.gc.action.HelloWorld">
	</bean>
	��һ��û�аѴ������Spring�������Ƽ�ʹ�õ�һ�ַ�ʽ��
	4.4 ��red������ָ�����������ַ�ʽ
		Spring�У���ref������ָ������������ģʽ��local��Bean��parent
	4.4.1 ��local����ָ��
	���Bean�뱻�ο����õ�Bean��ͬһ��xml�ļ��У��ұ��ο����õ�Bean����id�������ģ���ô�Ϳ���ʹ��ref��local���ԡ���������xml�������������xml�ļ�����ʽ����֤bean��id��local���Ե�ֵ�����뱻�ο����õ�bean ��id����һ�£������ͬһ��xml��û��ƥ���Ԫ�أ�xml�������ͻ����һ������
	��xml�������ĵ�������ȱ����ǿ�����Ա���ܼ�ʱ�ķ���xml�еĴ�������Ϊ���ܹ�����ط���xml�д���������Ϣ��ʹ��local����õġ�
	<beans>
		<bean id="HelloWorld" class="com.gc.action.HelloWorld">
			<property name="msg">
				<value>HelloWorld</value>
			</property>
			<property name="data">
				<ref local="date"/>
			</property>
		</bean>
	</beans>
	4.4.2 ��Bean����ָ��
	ʹ��ref��Bean������ָ�����������ʽ����Ϊ������Bean�����ڲ�ͬ��xml�С�
	<beans>
		<bean id="HelloWorld" class="com.gc.action.HelloWorld">
			<property name="msg">
				<value>HelloWorld</value>
			</property>
			<property name="data">
				<ref bean="date"/>
			</property>
		</bean>
	</beans>
	4.4.3 ��parent������ָ��
	 ��praent����ָ�����ο����õ�Beanʱ���������õ�ǰBeanFactory��ApplicationContext�ĸ�BeanFactory��ApplicationContext�е�Bean��parent���Ե�ֵ�����뱻�ο����õ�Bean��id������ͬ��Ҳ������name��ͬ��
	<beans>
		<bean id="HelloWorld" class="com.gc.action.HelloWorld">
			<property name="msg">
				<value>HelloWorld</value>
			</property>
			<property name="data">
				<ref parent="date"/>
			</property>
		</bean>
	</beans>
	4.4.4 ��local������Bean����ָ�������ıȽ�
		����ģʽ�У�local���Ժ�bean������ã����ڶ���Щ���Խ��бȽ�
		��֮ͬ�������߶�������Bean��id���вο����ã������Զ�ͳһXml�е�bean���вο����á�
		��֮ͬ������Bean����ָ������������Bean��name�����вο����ã������ԶԲ�ͬxml�е�Bean���вο����á�
	4.5 Bean�Զ�װ�������ģʽ
	ʹ��Bean��autowire������ָ��Bean������Զ�װ�䣬��������ģʽ����byName��byType��constructor��autodetext��no
	4.5.1 ʹ��byNameģʽ
		ͨ��Bean���������֣������Զ�װ�䡣��Spring�������ĵ�XML�У�����һ���뽫װ�������ͬ�����ֵ�Bean��
		���磺
		public class HelloWorld
		{
			private String msg = null;
			public String  getMsg(){...}
			public void  setMsg(){...}
		}
		xml��
		<bean id="HelloWorld" class="com.gc.action.HelloWorld" autowire="byName">
			<property name="msg">
				<value>HelloWorld</value>
			</property>
		</bean>
		��Ϊxml���õ�name=msg����autowire��byName�����Ի�ȥĿ����HelloWorld����ȥѰ����ͬ����Ϊmsg�����ԡ�
	4.5.2 ʹ��byTypeģʽ
		��byName�������ƣ������ͨ������ƥ��ġ�
		public class HelloWorld
		{
			private String msg = null;
			private Date date = null;
			public String getMsg(String msg){...}
			public void  setMsg(){...}
			public void  setDate(Date date){...}
			public Date  getDate(){...}
		}
		xml��
		<bean id="HelloWorld" class="com.gc.action.HelloWorld" autowire="byType">
			<property name="msg">
				<value>HelloWorld</value>
			</property>
		</bean>
		<bean id="date" class="java.util.Date"/>
		���û��ƥ���ean����ʲô�����ᷢ�������Բ��ᱻ���á������ⲻ�ǿ�����Ա�뿴���ģ�����Ҫ���dependency-check="objects"������ָ�����������Ӧ���׳�����

	4.5.3 ʹ��constructorģʽ
		��ֹ��캯���Ĳ����Զ�װ��
		public class HelloWorld {
			private String msg = null;
			private Date date = null;

			public HelloWorld(Date date) {
				this.date = date;
			}

			public String getMsg() {
				return msg;
			}

			public void setMsg(String msg) {
				this.msg = msg;
			}

			public Date getDate() {
				return date;
			}

			public void setDate(Date date) {
				this.date = date;
			}
		}
		xml��
		<bean id="HelloWorld" class="com.gc.action.HelloWorld" autowire="constructor">
			<property name="msg">
				<value>HelloWorld</value>
			</property>
		</bean>
		<bean id = "date" class="java.util.Date"/>
	4.5.4 ʹ��autodetectģʽ
		autodetectģʽָ�ľ���ͨ����Bean�������ڲ���ѡ��constructor��byType��������ҵ�constructor����constructor��û���ҵ�constructor�����ҵ�byType����byBytype�������ҵ�ɶ����ɶ
	4.5.5 noģʽ
		�������Զ�װ�䡣�ܶ���ҵ����Ӧ�û���������ʹ���Զ�װ��ģʽ����Ϊ����bean֮��Ĳο�������ϵ��������

	4.6 Bean������������ģʽ
		bena��dependency-check������ָ��Bean�����������鹲������ģʽ��simple��object��all��nonoe
		4.6.2 ʹ��simpleģʽ
		�Ի������͡��ַ����ͼ��Ͻ����������
		<bean id="date" class="com.***." dependency-check="simple">  
		//ֻ�������������Լ�������������  
		4.6.3 ʹ��objectģʽ
		�������Ķ�������������
		<bean id="date" class="com.***." dependency-check="object">  
		//���������������Լ����������������������������	
		4.6.4 ʹ��allģʽ
		��ȫ�����Խ����������
		<bean id="date" class="com.***." dependency-check="all">  
		//���������������  
		4.6.5 ʹ��noneģʽ
		�������������
		<bean id="date" class="com.***." dependency-check="none">  
		//������������������Spring��dependency-check��Ĭ��ֵ���������κμ�顣

	4.7 ���ϵ�ע�뷽ʽ
	4.7.1 List
		public class HelloWorld {
			private List msg = null;

			public List getMsg() {
				return msg;
			}

			public void setMsg(List msg) {
				this.msg = msg;
			}
		}
		xml:
		<bean id="HelloWorld" class="com.gc.action.HelloWordd">
			<property name="msg">
				<list>
					<value>gf</value>
					<value>gd</value>
					<value>helloWorld</value>
				</list>
			</property>
		</bean>
	4.7.2 Set
		���ϲ������ƣ�ֻ�ǰ��������Ե����͸ĳ�Set��<list>�ĳ�<set>����
	4.7.3 Map
		<map>
			<entry key="gf">
				<value>HelloWorld</value>
			</entry>
			<entry key="gd">
				<value>H</value>
			</entry>
		</map>
	4.7.4 Properties
		<property name="msg">
			<props>
				<prop key="gf">helloworld1</prop>
				<prop key="gd">helloworld2</prop>
			</props>
		</property>
	4.8 ����Bean
		���ֹ�������ʹ��beanWrapper����bean��ʹ��BeanFactory����ʹ��ApplicationContext����
	4.8.1 BeanWrapper����Bean
		��org.springframework.beans���У���������Ҫ���࣬BeanWrapper�ӿڼ���ʵ��BeanWrapperImpl��ͨ���������Ի��Bean�����ԣ�����bean���ж�д
		�������£���Ҫ�ǲ��Ե�ʱ��
		public class HelloWorld {
			private Set msg = null;

			public Set getMsg() {
				return msg;
			}

			public void setMsg(Set msg) {
				this.msg = msg;
			}
		}
		�����ࣺ
		public class TestHelloWorld {
			public static void main(String[] args) throws ClassNotFoundException, IllegalAccessException, InstantiationException {
				Object obj = Class.forName("spring.HelloWorld").newInstance();
				BeanWrapper bw = new BeanWrapperImpl(obj);
				bw.setPropertyValue("msg","hello");
				System.out.println(bw.getPropertyValue("msg"));
			}
		}
	4.8.2 ʹ��BeanFactory����bean
		BeanFactoryʵ������ʵ���������ù����ڶ�Bean��������
		BeanFactory�����ýӿ�org.springframework,beans.factory.BeanFactory��ʾ������ӿ��ж��ʵ�֣���õļ��ľ���org.springframewoek.beans.factory.xml.XmlBeanFactory
		�������£�
		HelloWorld������һ��
		�����ࣺ
		public class TestHelloWorld {
			public static void main(String[] args) throws ClassNotFoundException, IllegalAccessException, InstantiationException {
				ClassPathResource res = new ClassPathResource("config.xml");
				XmlBeanFactory factory = new XmlBeanFactory(res);
				HelloWorld helloWorld = (HelloWorld)factory.getBean("HelloWorld");
				System.out.println(helloWorld.getMsg());
			}
		}
	4.8.3 ApplicationContext����Bean
		ApplicationContext������BeanFactory֮�ϣ����������������ܡ�������ʻ�֧�֣���ȡ֧Ԯ���¼����ݵȡ�BeanFactory�ṩ�����ÿ�ܺͻ������ܣ���ApplicationContextΪ�������˸�ǿ�Ĺ��ܡ���ApplicationContext��BeanFactory�ĳ������κ�BeanFactory����ͬ��������ApplicationContext��
		�����ࣺ
		public class TestHelloWorld {
			public static void main(String[] args) throws ClassNotFoundException, IllegalAccessException, InstantiationException {
				ApplicationContext axtx = new FileSystemXmlApplicationContext("config.xml");
				HelloWorld helloWorld = (HelloWorld)axtx.getBean("HelloWorld");
				System.out.println(helloWorld.getMsg());
			}
		}
	4.9 Application��ǿ�Ĺ���
	4.9.1 ���ʻ�֧��
		һ�������Ϣ�Ĵ���һ�������ַ�����
		.����Ϣ��������ݿ⣬��ʱ������ݿ���ȡ��
		.����Ϣ��ŵ�java�࣬�õ�ʱ���java����ȡ��
		�����ַ�ʽ����ʵ�ֹ��ʻ���˵�����ǱȽ����ѵġ�ApplicationContext�̳���org.springframework.context.MessageSource�ӿڣ�ʹ��getMessage�����ĸ��������������Ϣ��Դ���Ӷ�ʵ�ֹ�����Ϣ��Ŀ�ġ�
		getMessag����������
			String getMessage(String code, Object[] args, String defaultMessage, Locale locale);
			����Ҳ���ָ������Ϣ�����ʹ��Ĭ����Ϣ
			String getMessage(String code, Object[] args, Locale locale) throws NoSuchMessageException;
			û��Ĭ��ֵ����ָ�����Ҳ�����Ϣ���ͻ��׳�һ��NoSuchMessageException
			String getMessage(MessageSourceResolvable resolvable, Locale locale) throws NoSuchMessageException;
			ͨ��MessageSourceResolvable��������Ҫ��ȡ��Ϣ�Ĵ��š�
		��ApplicationContext�����ص�ʱ�������Զ�����XML�ж����messageSource��SpringԼ�����Bean���뱻����ΪmessageSource��������Ա����ͨ��org.springframework.context.support.ResourceBundleMessageSource��ȡ�ù��ʻ���Ϣ��
		<bean id="messageSource" class="org.springframework.context.support.ResourceBundleMessageSource">
			<property name="basename">
				<value>messages</value>
			</property>
		</bean>
		�����������ζ����Ϣ�����messages.properties��
		�ĵ���д��HelloWorld=�ʺ���:{0} �ʺ�ʱ�䣺{1}
		{0}��{1}������ʾ���ⲿ����Ĳ�����

	4.9.2 ��Դ����
		��һ�֣�����·����ȡ
		ApplicationContext axtx = new FileSystemXmlApplicationContext("config.xml");
		Resource resource = actx.getResource("classpath:messages.properties");
		"classpath:"��SpringԼ����URL����·��

		�ڶ��֣�ʵ��·������ȡ
		ApplicationContext axtx = new FileSystemXmlApplicationContext("config.xml");
		Resource resource = actx.getResource("file:d/eclipse/workspace/myApp/WEB-INF/src/messages.properties");
		�����֣����·������ȡ
		ApplicationContext axtx = new FileSystemXmlApplicationContext("config.xml");
		Resource resource = actx.getResource("WEB-INF/src/messages.properties");

	4.9.3 �¼�����
		ApplicationContext�е��¼�������ͨ��ApplicationEvent���ApplicationListener�ӿ����ṩ�ġ�ͨ��ApplicationContext��publishEvent()����֪ͨApplicationListener
		�����̳�ApplicationEvent����LogEvent,LogEvent����ͨ��ApplicationContext��������ȥ�ġ�
		Ȼ����һ��ʵ��ApplicationListener�ӿڵ���LogEventListener.��ApplicationContext���ڷ���LogEvent�¼�ʱ֪ͨLogEventListener��
		����ʵ��һ��ApplicationContextAeare�ӿڵ���Log��ͨ��publishEvent()����������LogEvent��Ϊ��������֪ͨAListener.
5 Spring��AOP
	5.1 AOP ����˼��
		�������̣�Aspect Oriented Programming

	5.1.1 ��ʶAOP
		����ҵ���߼�����صĴ����ҵ�������з���
		���ݣ�����¼��־������һ�������棬����Ŷ����Щ�����ĵ��ö�Ҫ������������棬����������������־�����������ʹﵽ�˴������ú�����ά����Ŀ�ġ�
	5.1.2 AOP��OOP�Աȷ���
		OOP����������̣�
		AOP���ʹ��������
	5.2 ��һ�������־��ʵ��������Java�Ĵ������
		AOP������java�Ķ�̬���������
	5.3 AOP�������ؼ�����
	5.3.1 ����㣨Pointcut��
		�Ƚ���Join Point�����ӵ㣩�ĸ���������е�ĳ���׶ε㣬��ĳ���������ã��쳣�׳���
		Pointcut��Join Point�ļ���.
		org.springframewoek.aop.Pointcut�ӿ�����ָ��֪ͨ���ض�����ͷ�����
		��������Ҫ�ķ���
		ClassFilter getClassFilter();
		.������������޶��ڸ�����Ŀ������
		MethodMatcher getMethodMatcher();
		.�����ж�������Ƿ�ƥ��Ŀ������ķ�����
	5.3.2 ֪ͨ��Advice��
		ĳ�����ӵ������õĴ����߼�
	5.3.3 Advisor
		��Pointcut��Advice����������
	5.4 Spring��3������㣨Pointcut��ʵ��
		��������㣬��̬����㡢��̬����㡢�Զ��������
	5.4.1 ��̬�����
		��̬�����ֻ���ڸ����ķ�����Ŀ���࣬�������Ƿ����Ĳ�����
		Spring�ڵ��þ�̬������ʱ��ֻ�ڵ�һ�ε�ʱ����㾲̬������λ�ã�Ȼ����������������Ժ�Ͳ���Ҫ�ٽ��м��㡣
		ʹ��org.springframework.aop.support.RegexpMethodPointcut��ʵ�־�̬����㡣��Ҫ�����jakata-oro-2.0.8.jar���뵽ClassPath�С�
		<bean id="setterAndAbsquatulatePointcut" class="org.springframework.aop.support.RegexpMethodPointcut">
			<property name="patterns">
				<list>
					<value>.*save.*</value>
				</list>
			</property>
		</bean>
		.*save.*��ʾ������dave��ͷ�ķ�����������㡣
	5.4.2 ��̬�����
		�;�̬�����������������޶��ڸ����ķ������ࡣ��̬����㻹����ָ�������Ĳ�������Ϊ�����ı仯�ԣ���̬����㲻�ܻ��棬ÿ�ε��ö�Ҫ���¼��㣬����кܴ��������ġ�
		����������þ�̬����㣬
	5.4.3 �Զ��������
		
	5.5 Spring��֪ͨ��Advice��
		����Advice���͡�Interception Around��Before��After Returnning��Throw �� Introduction��
		���������JointPointǰ��JointPointǰ��JointPoint��JointPoint�׳��쳣ʱ��JointPoint������Ϻ�
	5.5.1 Interception Around֪ͨ
		ʵ�ֽӿ�MethodInterceptop�ӿ�
	5.5.2 Before֪ͨ
		ʵ�ֽӿ�MethodBeforeAdvice
	5.5.3 After Returning֪ͨ
		ʵ��AfterReturningAdvice�ӿ�
	5.5.4 Throw֪ͨ
		ʵ��ThrowsAdvice�ӿ�
	5.5.5 Introduction֪ͨ
		ʵ��IntroductionAdvisor,IntroductionInterceptor�ӿ�
	5.6 Spring��Advisor
		Advisor��Pointcut��advice��������
		org.springwork.aop.support.DefaultPointcutAdvisor��ͨ�õ�Advisor�࣬��Ҫͨ��XML�ķ�ʽ����Pointcut��Advice
	5.7 ��ProxyFactoryBean����AOP����

	5.10 Spring���Զ�����
		org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator
		
		

























