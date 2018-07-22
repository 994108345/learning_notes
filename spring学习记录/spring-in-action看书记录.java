1 spring基础

	1.1 Spring简介

		1.1.1 Spring 特点
			.轻量级：非侵入式的，即基于spring开发的系统中的对象一般不依赖Spring的类
			.反向控制（IOC：Inversion of Control）：容器在实例化对象时，主动将其依赖类注入给它。所以使用IOC的对象是被动接受依赖类，而不是主动寻找
			.面向切面（AOP：Aspect Oriented Programming）：将业务逻辑从系统服务中分离，实现内聚开发。系统只做其该做的业务逻辑，不负责其他系统问题（如日志和事务支持）
			.容器：包含且管理系统对象的声明周期和配置，通过配置设定Bean是单一实例还是每次请求产生一个实例，并设定Bean之间的关联关系。
			.框架：使用简单组件配置组合成一个复杂的系统，系统中的对象是通过XML文件配置组合起来的，且Spring提供了很多基础功能（事务管理、持久层集成等）。
		1.1.2 Spring 模块
			.核心容器：提供了基础功能，包括BeanFactory类（Spring框架的核心，采用工厂模式实现IOC）
			.应用上下文模块：扩展了BeanFactory,提供许多企业级服务
			.AOP模块：
			.JSBC和DAO模块
			.O/R映射模块
			.web模块
			.MVC模块
1.2 示例
	1.2.1 Spring 简单示例
	1.2.2 IoC示例
		减少耦合的一个常用做法就是将具体实现隐藏在接口下，是的具体实现类的替换不会影响到引用类。
	1.2.3 AOP
		1.2.3.1 AOP简介
			提升业务的分离
			系统业务（如日志和事务）的调用被分散在各个模块中，而这些业务并不是该模块的主要业务。
			系统业务覆盖了它影响到的组件。
		1.2.3.2 AOP实例

1.3 Spring 比较
1.3.1 比价Spring和EJB	

			
2.装配Bean
	Spring提供两种不同的容器
		.bean工厂：由org.springframework.beans.factory.BeanFactory接口定义，是最简单的容器，提供基础的依赖注入支持
		.应用上下文：由org.springframework.context.ApplicationContext接口定义，建立在bean工厂基础之上，提供系统架构服务。
	2.1 简介
		2.1.1 BeanFactory介绍
		2.1.2 应用上下文
			应用上下文不仅载入Bean定义信息，装配Bean，根据需要分发Bean，还提供如下功能
			.文本信息解析工具，包括对国际化的支持。
			.载入文件资源的通用方法
			.向注册为监听器的Bean发送事
			常用实现类如下：
			.ClassPathXmlApplicationContext:从类路径中XML文件载入上下文定义信息，把上下文定义文件当成类路径资源；
			.FileSystemXmlApplicationContext:从文件系统中的XML文件载入上下文定义信息（在指定的路径中寻找）；
			.XmlWebApplicationContext:从web系统中的XML文件载入上下文定义信息
		2.1.3 Bean的生命
			1.容器寻找Bean的定义信息并将其实例化。
			2.使用依赖注入，Spring按照Bean定义信息配置Bean的所有属性
			3.如果Bean实现了BeanNameAware接口，工厂调用Bean的setBeanName()方法传递Bean的ID。
			4.如果Bean实现了BeanFavtoryAeare接口，工厂调用setBeanFactory()方法传入工厂自身。
			5.如果有BeanPostProcessor和Bean关联，那么气postProcessBeforeInitiaLization（）方法将被调用。
			6.如果Bean指定了init-method方法，将被调用
			7.最后，如果有BeanPostProcessor和Bean关联，那么起postProcessAfterInitialization()方法将被调用。
			此时，Bean已经可以被应用系统使用，并将被保留在BeanFactory中直到塔不在被需要。有两种方法可将其从BeanFactory中删除掉。
			1.如果Bean实现了DisposableBean接口，destory（）方法被调用
			2.如制定了定制的销毁方法，就调用这个方法。

















