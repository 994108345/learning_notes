1 Spring 概述
	Spring 框架由7大模块组成
	核心容器：提供了Speing框架的核心功能。BeanFactory是Spring核心容器的主要组件。它通过控制反转将应用程序的配置和依赖性规范与实际的应用程序代码分开。
	Spring Context ： 通过配置文件，向 Spring 框架提供上下文信息。它构建在BeanFactory之上，另外增加了国际化，资源访问等功能。
	Spring Aop：Spring提供了面向方面编程的功能，因为Spring的核心的基于控制反转的，所以可以很容易的使Spring的依赖注入为AOP提供支持。
	Spring Dao：提供了一个简单而有效的JDBC的使用，使用它的DAO就足以应付开发人员的日常应用。
	Spring ORM：Spring除了有自己的JDBC应用之外，还提供了对一些ORM框架的支持，例如JDO。HIBERNATE和Mybatis等。
	Spring web：提供了简单的处理多部分请求以及将请求参数绑定到域对象的任务。
	Spring MVC:Spring提供了MVC3模式的实现，使用起来非常方便，但他不强迫开发人员使用。

Spring的特点：
 1：设计良好的分层结构，促使开发人员可以很简单地进行扩充。
 2：以IoC为核心，促使开发人员面向接口编程。
 3：良好的架构设计，是的应用程序尽可能地依赖应用程序的环境，从而使应用脱离了环境的影响
 4：Spring能替代EJB，如果开发人员使用EJB，则使用Sring后还可以继续使用EJB。
 5：Spring MVC可以很好的实现mvc2，并提供了很简单的对国际化与资源访问的支持:。
 6：Spring可以与其他框架良好的结合。

3 Spring基础概念
	3.1 反向控制/依赖注入
	反向控制（IoC：Inversion of Control）
	IoC的工作原理就是依赖注入：（DI：Dependency Injection）
	3.1.1 反向控制（IoC）
	实现必须依赖抽象，而不是抽象依赖实现。这是反向控制的一种表现方式。
	例子：即最简单的面向接口编程，通过多态。
	3.1.2 依赖注入（DI）
	为了更能说明IoC模式的特点，取了一个新名字，叫做依赖注入。

	3.2 依赖注入的三种实现方式
	依赖注入的意义：让组件依赖于抽象，当组件要与其他实际对象发生依赖关系时，通过抽象来注入依赖的实际对象。
	依赖注入的三种实现方式：1：接口注入 2：Set注入 3：构造注入
	3.2.1 接口注入
	接口注入指的就是在接口中定义要注入的信息，并通过接口完成注入。
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
	测试类：
	public class TestBusiness
	{
		private Business business = new Business();
		public void getData(){
			business.createDI(new OracleDataBase()):
			business.getData();
		}
	}
	通过接口，根据java多态的特性，给目标类确定类型。
	3.2.2 Set注入
	Set注入就是在接收注入的类中定义一个Set方法，并在参数中定义需要注入的元素。
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
	测试类：
	public class TestBusiness
	{
		private Business business = new Business();
		public void getData(){
			business.setDataBase(new OracleDataBase()):
			business.getData();
		}
	}
	3.2.3 构造注入
	构造注入指的就是在接收注入的类中定义一个构造方法，并在参数中定义需要注入的元素。
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
	测试类：
	public class TestBusiness
	{
		private Business business = new Business();
		public void getData(){
			business.setDataBase(new OracleDataBase()):
			business.getData();
		}
	}
	3.3 配置的xml
	在Spring中，利用Set注入和构造注入的时，在Xml配置文档中使用的语法是不一样的。
	
	<bean id="HelloWorld" class="com.gc.action.HelloWorld">
		//通过构造函数进行注入
		<constructor-arg index="0">
			<value>HelloWorld</value>
		</constructor-arg>
		//通过Set注入
		<property name="HelloWorld">
			<value>HelloWorld</value>
		</property>
	<bean>
	3.4 使用哪种注入方式
	各有各的好
	要在对象建立时就准备好所有的资源，就使用构造注入，在对象建立后才需要注入，就使用Set注入。
	3.5 小结
	Spring的核心是个IoC容器。

4 Spring的核心容器
	Spring的核心容器实现了IoC，其目的是提供一种无侵入式的框架。BeanFactory和ApplicationContext是了解Spring核心的关键。
	4.1 什么是Bean
	Bean是描述Java的软件组件模型。通过Bean可以无限扩充java程序的功能，bean可以实现代码的重复利用。
	4.2 Bean的基础知识
	Spring中，有两个最基本重要的包，即org.springframework.beans和org.springframework.context在这两个包中，为了实现一种无侵入式的框架，代码大量引用了Java中的反射机制，通过动态调用的方式避免硬编码，为Spring的反向控制提供了基础。
	在这里那个包里，最重要的类是BeanFactory和ApplicationContext。
	4.2.1 Bean的标识（id和name）
	<bean id="HelloWorld" class="com.gc.action.HelloWorld">
		<property name="msg">
			<value>HelloWorld</value>
		</property>
	</bean>
	id：是Bean的唯一标识，class指出bean的来源,
	也可以用name属性指定id：<bean name="HelloWorld" class="com.gc.action.HelloWorld">
	设置时，两者至少指定一个。
	两者的区别是：
	id:允许指定一个Bean的id，并且它在CML DTD中作为一个真正的XML元素的ID属性被标记，所以XML解析器能够在其他元素指向它的时候做一些额外的校验。因为XML规范严格限定了在XML ID中合法的字符，如果在开发中有必要使用非法字符，即不符合ID规定的标准，则可以通过name属性指定一个或多个id，当指定多个id，用逗号和分号分隔！
	4.2.2 Bean的类（class）
	class属性指定了Bean的来源！注意写全路径名！

	4.2.3 Singleton的使用
	在Spring中，Bran可以被定义为两种部署模式中的一种：singleton或non-singleton。Spring默认为singleton模式。
	singleton：只有一个共享实例存在，所有对这个bena的请求都会返回唯一的实例。
	non-singleton：这个Bean每次请求都会创建一个新的Bean实例，也就是new。
	<bean id="HelloWorld" class="com.gc.action.HelloWorld" signleton="true">
		<property name="msg">
			<value>HelloWorld</value>
		</property>
	</bean>
	 signleton="true"就是singleton，signleton="false"就是non-singleton，默认是signleton。
	 4.2.4 Bean的属性
	 Bean的属性值有两种注入方式：基于set和构造函数的依赖注入。
	 一个类里可以有多个bean，bena可以配置在一起，也可以分开配置！
	 4.2.5 对于null值的处理
	<bean id="HelloWorld" class="com.gc.action.HelloWorld">
		<property name="msg">
			<value>null</value>
		</property>
	</bean>
	或者
	<bean id="HelloWorld" class="com.gc.action.HelloWorld">
		<property name="msg">
			<null/>
		</property>
	</bean>
	设置值为null
	4.2.6 使用依赖depends-on
		Bean的depends-on属性可以用来初始化使用这个Bean之前，强制执行一个或多个Bean的初始化。
	<bean id="HelloWorld" class="com.gc.action.HelloWorld" depends-on="date">
		<property name="msg">
			<value>HelloWorld</value>
		</property>
	</bean>
	即在初始化HelloWorld Bean时，要先初始化date Bean。
	4.3 Bean的生命周期
	Bean的生命周期包括Bean的定义、Bean的初始化、Bean的使用和Bean的销毁四个阶段。
	4.3.1 Bean的定义
	在Spring中，通常是通过配置文档的方式来定义Bean。在一个大应用中，会有很多Bean，这样文档就会很大，这使可以把相关的Bean放在一个配置文档中，出现多个配置文档！
	4.3.2 Bean的初始化
	Spring中，Bean的初始化有两种方式
	1.在配置文档中通过制定init-method属性来完成。
	2.实现org.springframework.beans.factory.InitializingBean接口
	第一种：首先在HelloWorld.java中增加一个方法init()；用来完成初始化工作。然后修改配置文档config.xml.指定Bean中要初始化的方法。
	public class HelloWorld
	{
		private  String msg = null;
		public void init(){
			msg = "Hello World";
		}
		public String getMsg(){...}
		public void setMsg(){...}
	}
	修改配置文档，指定Bean要初始化的方法为init()，并且去掉通过setter注入值的方式
	<bean id="HelloWorld" class="com.gc.action.HelloWorld" init-method="init">
	</bean>
	第二种：让Hellowoeld.java实现InitializingBean接口，增加一个方法afterPropertiesSet()用来完成初始化工作，然后修改配置文档config.xml
	public class HelloWorld implements InitializingBean
	{
		private String msg = null;
		public void afterPropertiesSet(){
			msg = "Hello World";
		}
		public String getMsg(){...}
		public void setMsg(){...}
	}
	修改配置文件信息如下：
	<bean id="HelloWorld" class="com.gc.action.HelloWorld">
	</bean>
	因为第一种没有把代码耦合与Spring，所以推荐使用第一种方式。
	4.3.3 Bean的使用
	3种使用Bean的方式
	第一：使用BeanWrapper
	HelloWorld helloWorld = new HelloWorld();
	BeanWrapper bw = new BeanWrapperImpl(helloWorld);
	bw.setPropertyValue("msg","HelloWorld");
	System.out.println(bw.getPropertyValue("msg"));
	第二：使用BeanFactory
	InputStream is = new FileInputStream("Config.xml");
	XmlBeanFactory factory = new XmlBeanFactory(is);
	HelloWorld helloWorld = (HelloWorld)factory.getBean("HelloWorld");
	System.out.println(helloWorld.getMsg());
	第三种：使用ApplicationContext
	ApplicationContext axtx = new FileSystemXmlApplicataionContext("config.xml");
	HelloWorld helloWorld = (HelloWorld)actx.getBean("HelloWorld");
	System.out.println(helloWorld.getMsg());
	4.3.4 Bean的销毁
	销毁有两种方式
	第一：在配置文件通过指定destory-method属性来完成
	public class HelloWorld
	{
		private String msg = null;
		public void cleanUp(){
			this.msg = "";
		}
		public String getMsg(){...}
		public void  setMsg(){...}
	}
	配置xml
	<bean id="HelloWorld" class="com.gc.action.HelloWorld" destory-method="cleanUp">
	</bean>
	第二：实现org.springframework.beans..factory.DisposableBean接口。
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
	配置xml
	<bean id="HelloWorld" class="com.gc.action.HelloWorld">
	</bean>
	第一种没有把代码耦合Spring，所以推荐使用第一种方式。
	4.4 用red的属性指定依赖的三种方式
		Spring中，用ref发属性指定依赖有三种模式：local，Bean。parent
	4.4.1 用local属性指定
	如果Bean与被参考引用的Bean在同一个xml文件中，且被参考引用的Bean的用id来命名的，那么就可以使用ref的local属性。这样会让xml解析器更早的再xml文件解析式，验证bean的id，local属性的值必须与被参考引用的bean 的id属性一致，如果在同一个xml中没有匹配的元素，xml解析器就会产生一个错误。
	用xml来配置文档的明显缺点就是开发人员不能及时的发现xml中的错误。所以为了能够尽早地发现xml中错误配置信息，使用local是最好的。
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
	4.4.2 用Bean属性指定
	使用ref的Bean属性来指定是最常见的形式，因为她允许Bean可以在不同的xml中。
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
	4.4.3 用parent属性来指定
	 用praent属性指定被参考引用的Bean时，允许引用当前BeanFactory或ApplicationContext的父BeanFactory或ApplicationContext中的Bean。parent属性的值可以与被参考引用的Bean的id属性相同，也可以与name相同。
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
	4.4.4 用local属性与Bean属性指定依赖的比较
		三种模式中，local属性和bean属性最常用，现在对这些属性进行比较
		相同之处：两者都可以用Bean的id进行参考引用，都可以对统一Xml中的bean进行参考引用。
		不同之处：用Bean属性指定依赖可以用Bean的name来进行参考引用，还可以对不同xml中的Bean进行参考引用。
	4.5 Bean自动装配的五种模式
	使用Bean的autowire属性来指定Bean定义的自动装配，共有五种模式，即byName、byType、constructor、autodetext和no
	4.5.1 使用byName模式
		通过Bean的属性名字，进行自动装配。在Spring的配置文档XML中，查找一个与将装配的属性同样名字的Bean。
		例如：
		public class HelloWorld
		{
			private String msg = null;
			public String  getMsg(){...}
			public void  setMsg(){...}
		}
		xml：
		<bean id="HelloWorld" class="com.gc.action.HelloWorld" autowire="byName">
			<property name="msg">
				<value>HelloWorld</value>
			</property>
		</bean>
		因为xml配置的name=msg，且autowire是byName，所以会去目标类HelloWorld类中去寻找相同名字为msg的属性。
	4.5.2 使用byType模式
		和byName属性类似，这个是通过类型匹配的。
		public class HelloWorld
		{
			private String msg = null;
			private Date date = null;
			public String getMsg(String msg){...}
			public void  setMsg(){...}
			public void  setDate(Date date){...}
			public Date  getDate(){...}
		}
		xml：
		<bean id="HelloWorld" class="com.gc.action.HelloWorld" autowire="byType">
			<property name="msg">
				<value>HelloWorld</value>
			</property>
		</bean>
		<bean id="date" class="java.util.Date"/>
		如果没有匹配的ean。则什么都不会发生，属性不会被设置。但是这不是开发人员想看到的，所有要设计dependency-check="objects"属性来指定这种情况下应该抛出错误。

	4.5.3 使用constructor模式
		格局构造函数的参数自动装配
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
		xml：
		<bean id="HelloWorld" class="com.gc.action.HelloWorld" autowire="constructor">
			<property name="msg">
				<value>HelloWorld</value>
			</property>
		</bean>
		<bean id = "date" class="java.util.Date"/>
	4.5.4 使用autodetect模式
		autodetect模式指的就是通过对Bean检查类的内部来选择constructor或byType，如果先找到constructor就用constructor。没有找到constructor，而找到byType就用byBytype，即先找到啥就用啥
	4.5.5 no模式
		不适用自动装配。很多企业级的应用环境不鼓励使用自动装配模式，因为对于bean之间的参考依赖关系不清晰。

	4.6 Bean依赖检查的四种模式
		bena的dependency-check属性来指定Bean定义的依赖检查共有四种模式：simple，object。all。nonoe
		4.6.2 使用simple模式
		对基本类型、字符串和集合进行依赖检查
		<bean id="date" class="com.***." dependency-check="simple">  
		//只检查简单类型属性以及集合类型属性  
		4.6.3 使用object模式
		对依赖的对象进行依赖检查
		<bean id="date" class="com.***." dependency-check="object">  
		//检查除简单类型属性以及集合类型属性外的引用类型属性	
		4.6.4 使用all模式
		对全部属性进行依赖检查
		<bean id="date" class="com.***." dependency-check="all">  
		//检查所有类型属性  
		4.6.5 使用none模式
		不进行依赖检查
		<bean id="date" class="com.***." dependency-check="none">  
		//如果不进行设置设就是Spring中dependency-check的默认值，不进行任何检查。

	4.7 集合的注入方式
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
		与上部分类似，只是把类中属性的类型改成Set，<list>改成<set>而已
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
	4.8 管理Bean
		三种管理方法：使用beanWrapper管理bean、使用BeanFactory管理、使用ApplicationContext管理
	4.8.1 BeanWrapper管理Bean
		在org.springframework.beans包中，有两个重要的类，BeanWrapper接口及它实现BeanWrapperImpl。通过这个类可以获得Bean的属性，并对bean进行读写
		代码如下：主要是测试的时候
		public class HelloWorld {
			private Set msg = null;

			public Set getMsg() {
				return msg;
			}

			public void setMsg(Set msg) {
				this.msg = msg;
			}
		}
		测试类：
		public class TestHelloWorld {
			public static void main(String[] args) throws ClassNotFoundException, IllegalAccessException, InstantiationException {
				Object obj = Class.forName("spring.HelloWorld").newInstance();
				BeanWrapper bw = new BeanWrapperImpl(obj);
				bw.setPropertyValue("msg","hello");
				System.out.println(bw.getPropertyValue("msg"));
			}
		}
	4.8.2 使用BeanFactory管理bean
		BeanFactory实际上是实例化、配置管理众多Bean的容器。
		BeanFactory可以用接口org.springframework,beans.factory.BeanFactory表示。这个接口有多个实现，最常用的简答的就是org.springframewoek.beans.factory.xml.XmlBeanFactory
		代码如下：
		HelloWorld和上面一样
		测试类：
		public class TestHelloWorld {
			public static void main(String[] args) throws ClassNotFoundException, IllegalAccessException, InstantiationException {
				ClassPathResource res = new ClassPathResource("config.xml");
				XmlBeanFactory factory = new XmlBeanFactory(res);
				HelloWorld helloWorld = (HelloWorld)factory.getBean("HelloWorld");
				System.out.println(helloWorld.getMsg());
			}
		}
	4.8.3 ApplicationContext管理Bean
		ApplicationContext建立在BeanFactory之上，并增加了其他功能。例如国际化支持，获取支援、事件传递等。BeanFactory提供了配置框架和基本功能，而ApplicationContext为它增加了更强的功能。即ApplicationContext是BeanFactory的超集，任何BeanFactory功能同样适用于ApplicationContext。
		测试类：
		public class TestHelloWorld {
			public static void main(String[] args) throws ClassNotFoundException, IllegalAccessException, InstantiationException {
				ApplicationContext axtx = new FileSystemXmlApplicationContext("config.xml");
				HelloWorld helloWorld = (HelloWorld)axtx.getBean("HelloWorld");
				System.out.println(helloWorld.getMsg());
			}
		}
	4.9 Application更强的功能
	4.9.1 国际化支持
		一般对于信息的处理一般有两种方法：
		.将信息存放在数据库，用时候从数据库里取。
		.将信息存放到java类，用的时候从java类里取。
		这两种方式对于实现国际化来说，都是比较困难的。ApplicationContext继承了org.springframework.context.MessageSource接口，使用getMessage（）的各个方法来获得信息资源，从而实现国画信息的目的。
		getMessag有三个方法
			String getMessage(String code, Object[] args, String defaultMessage, Locale locale);
			如果找不到指定的信息，则会使用默认信息
			String getMessage(String code, Object[] args, Locale locale) throws NoSuchMessageException;
			没有默认值可以指定，找不到信息，就会抛出一个NoSuchMessageException
			String getMessage(MessageSourceResolvable resolvable, Locale locale) throws NoSuchMessageException;
			通过MessageSourceResolvable来传入需要获取信息的代号。
		当ApplicationContext被加载的时候，它会自动查找XML中定义的messageSource，Spring约定这个Bean必须被定义为messageSource。开发人员可以通过org.springframework.context.support.ResourceBundleMessageSource来取得国际化信息。
		<bean id="messageSource" class="org.springframework.context.support.ResourceBundleMessageSource">
			<property name="basename">
				<value>messages</value>
			</property>
		</bean>
		上面的配置意味着信息存放在messages.properties。
		文档里写：HelloWorld=问候语:{0} 问候时间：{1}
		{0}和{1}用来表示从外部传入的参数。

	4.9.2 资源访问
		第一种：虚拟路径获取
		ApplicationContext axtx = new FileSystemXmlApplicationContext("config.xml");
		Resource resource = actx.getResource("classpath:messages.properties");
		"classpath:"是Spring约定的URL虚拟路径

		第二种：实际路劲来获取
		ApplicationContext axtx = new FileSystemXmlApplicationContext("config.xml");
		Resource resource = actx.getResource("file:d/eclipse/workspace/myApp/WEB-INF/src/messages.properties");
		第三种：相对路劲来存取
		ApplicationContext axtx = new FileSystemXmlApplicationContext("config.xml");
		Resource resource = actx.getResource("WEB-INF/src/messages.properties");

	4.9.3 事件传递
		ApplicationContext中的事件处理是通过ApplicationEvent类和ApplicationListener接口来提供的。通过ApplicationContext的publishEvent()方法通知ApplicationListener
		设计类继承ApplicationEvent的类LogEvent,LogEvent就是通过ApplicationContext被发布出去的。
		然后定义一个实现ApplicationListener接口的类LogEventListener.则ApplicationContext会在发布LogEvent事件时通知LogEventListener。
		接着实现一个ApplicationContextAeare接口的类Log，通过publishEvent()方法，带入LogEvent作为参数，来通知AListener.
5 Spring的AOP
	5.1 AOP 基本思想
		面向方面编程，Aspect Oriented Programming

	5.1.1 认识AOP
		让与业务逻辑不相关的代码和业务代码进行分离
		形容：将记录日志看做是一个横切面，所有哦对这些方法的调用都要经过这个横切面，在这个横切面进行日志操作，这样就达到了代码重用和易于维护的目的。
	5.1.2 AOP与OOP对比分析
		OOP（面向对象编程）
		AOP降低代码耦合性
	5.2 从一个输出日志的实例法分析Java的代理机制
		AOP建立在java的动态代理基础上
	5.3 AOP的三个关键概念
	5.3.1 切入点（Pointcut）
		先介绍Join Point（连接点）的概念：程序运行的某个阶段点，如某个方法调用，异常抛出。
		Pointcut是Join Point的集合.
		org.springframewoek.aop.Pointcut接口用来指定通知到特定的类和方法。
		有两个重要的方法
		ClassFilter getClassFilter();
		.用来将切入点限定在给定的目标类中
		MethodMatcher getMethodMatcher();
		.用来判断切入点是否匹配目标给定的方法。
	5.3.2 通知（Advice）
		某个连接点所采用的处理逻辑
	5.3.3 Advisor
		是Pointcut和Advice的配置器。
	5.4 Spring的3中切入点（Pointcut）实现
		三种切入点，静态切入点、动态切入点、自定义切入点
	5.4.1 静态切入点
		静态切入点只限于给定的方法和目标类，而不考虑方法的参数。
		Spring在调用静态切入点的时候，只在第一次的时候计算静态切入点的位置，然后把它缓存起来，以后就不需要再进行计算。
		使用org.springframework.aop.support.RegexpMethodPointcut，实现静态切入点。需要导入包jakata-oro-2.0.8.jar加入到ClassPath中。
		<bean id="setterAndAbsquatulatePointcut" class="org.springframework.aop.support.RegexpMethodPointcut">
			<property name="patterns">
				<list>
					<value>.*save.*</value>
				</list>
			</property>
		</bean>
		.*save.*表示所有以dave开头的方法都是切入点。
	5.4.2 动态切入点
		和静态切入点的区别：它不仅限定于给定的方法和类。动态切入点还可以指定方法的参数。以为参数的变化性，动态切入点不能缓存，每次调用都要重新计算，因此有很大的性能损耗。
		大多数数都用静态切入点，
	5.4.3 自定义切入点
		
	5.5 Spring的通知（Advice）
		五种Advice类型。Interception Around、Before、After Returnning、Throw 和 Introduction。
		调用情况：JointPoint前后，JointPoint前，JointPoint后，JointPoint抛出异常时，JointPoint调用完毕后
	5.5.1 Interception Around通知
		实现接口MethodInterceptop接口
	5.5.2 Before通知
		实现接口MethodBeforeAdvice
	5.5.3 After Returning通知
		实现AfterReturningAdvice接口
	5.5.4 Throw通知
		实现ThrowsAdvice接口
	5.5.5 Introduction通知
		实现IntroductionAdvisor,IntroductionInterceptor接口
	5.6 Spring的Advisor
		Advisor的Pointcut和advice的配置器
		org.springwork.aop.support.DefaultPointcutAdvisor是通用的Advisor类，主要通过XML的方式配置Pointcut和Advice
	5.7 用ProxyFactoryBean创建AOP代理

	5.10 Spring的自动代理
		org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator
		
		

























