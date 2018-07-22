
第一章：MYbatis简介
	1.1 传统的JSBC编程
		JDBC是由SUN公司提出的一系列规范，它只定义了接口规范，具体实现是由各个数据库厂商自己去实现的。
		JDBC是一种典型的桥接模式。
		使用传统JDBC方式的弊端：
			1.工做量大:
			2.对异常捕获并且关闭资源！
	1.2 ORM模式
		对象关系映射（ORM）
		ORM模型就是数据库的表和简单的java对象的映射关系模型，简称POJO
	
	1.3 hibernate
		hibernate是通过配置文件，把数据库的数据直接映射到POJO上。是全表映射框架。

		hibernate的缺陷：
			1.全表映射带来的不方便，比如更新时需要发送所有的字段
			2.无法根据不同的条件组装不同的sql
			3.对多表和赋值的sql查询支持较差，需要自己写sql，返回后，需要自己讲数据组装为POJO
			4.不能有效的支持存储过程
			5.虽然有HQL，但是性能较差，大型互联网系统往往需要优化SQL，而Hibernate做不到。
		
	1.4 MyBatis
		半自动映射框架，因为是需要手动匹配提供POJO，SQL和映射关系。
		Mybatis的前身是iBatis。
		也可以自动完成映射，如果实体名和返回对象对应字段名相同。
		使用时，只需要一个接口，无需实现类。

	1.5 什么时候用Mybatis

第二章：MyBatis入门
	2.1 核心组件
		.SqlSessionFactoryBuilder：创建SqlSessionFactory
		.SqlSessionFactory：创建SqlSession（会话）
		.SqlSession:keyi:可以发送sql去执行并返回结果，也可以获取Mapper接口。
		.SQL Mapper：由java接口和XML文件构成，需要给出对应的SQL和映射规则。他负责发送SQL去执行，并返回结果。
	
	2.2.3 映射器
		映射器是由Java接口和XML文件共同组成的。

	2.3生命周期
		2.3.1 SqlSessionFactoryBuilder
			SqlSessionFactoryBuilder就是利用XML或者Java】编码获得资源来构建SqlSessionFactory的，就是构造器的作用。所以，用完就可以回收，所以声明周期只存在于方法内部。
		
		2.3.2 SqlSessionFactory
			作用是创建SqlSession，而SqlSession是一个会话，每次访问数据库，都要通过SqlSessionFactory创建SqlSession，所以SqlSessionFactory存在于整个生命周期。
		
		2.3.3 SqlSession
			是一个会话。生命周期是在请求数据库处理事务的过程中。
		
		2.3.4 Mapper
			Mapper是一个接口，作用就发送sql，返回我们要的结果。所以她是在一个sqlsession事务方法中，是方法界别。

		2.4实例
	
第三章 配置
	<?xml version="1.0" encoding = "UTF-8"?>
	<configuration>  <!-- 配置 -->
		<properties/>  <!-- 属性 -->
		<setting/>  <!-- 设置 -->
		<typeAliases/>  <!-- 类型命名 -->
		<typeHandlers/>  <!-- 类型处理器 -->
		<objectFactory/>  <!-- 对象工厂 -->
		<plugins/>  <!-- 插件 -->
		<environments>  <!-- 配置环境 -->
			<environment>  <!-- 配置变量 -->
				<transactionManager/>  <!-- 事务管理器 -->
				<dataSource/>  <!-- 数据源 -->
			</environment>
		</environments>
		<databaseIdProvider/>  <!-- 数据库厂商标识 -->
		<mappers/>  <!-- 映射器 -->
	</configuration>

	3.1 properties元素
		配置属性的元素，让我们能在配置文件的上下文中使用它。

		3.1.1 property 子元素
			就是配置数据库四大参数。
		3.1.3 程序参数传递
		3.1.1 优先级
			Mybatis加载配置参数如果出现重复设置了，后加载的参数会覆盖先加载的参数
			加载顺序是1：直接在配置文件里配置的properties元素。2：根据propertie元素中是resource属性读取的其他属性文件里设置的属性。3：读取作为方法参数传递的参数。
			所以优先级(从高到低)是：1.读取作为方法参数传递的属性，并覆盖同名属性2.读取配置文件3.直接配置propertie
			不建议使用多出设置参数，容易混乱，首选使用properties文件。
			
	3.2 设置
	3.3 mybatis 的别名是不区分大小写的
		3.3.1 系统定义别名
			即系统已经定义好的别名，一般是一些数据类型，比如Data,别名的data。
		3.3.2 自定义别名
			通过使用注解@Alias设置别名，没有配置的，默认将你的实体类名的别名取为同名且首字母小写。
	3.4 typeHandler类型处理器
		typeHandler就是从数据库取出结果，把jdbcType转化成javaType。
		3.4.1 系统定义的typeHandler
			注意：
			.数值类型的精度：数据库的int、double、decimal这些类型和java的精度长度都是不一样的。
			.时间精度，取数据到日用DareOnlyTypeHandler即可，用到精度为秒的用SqlTimestampTypeHandler等
		3.4.2 自定义typeHandler
			系统定义的typeHandler已经可以处理大部分业务了。只有很少特殊的情况需要我们自己定义，例如：字典类枚举
			@MappedTypes：定义的是JavaType类型，
			@MappedJdbcTypes：定义的是JdbcType类型
		3.4.3 枚举类型 typeHandler
			3.4.3.1 EnumOrdinalTypeHandler
				是mybatis默认的枚举类型的处理区。他插入的是枚举定义的下标
				如果只是在mybatis单独一个框架中使用，很简单。直接配置mybatis-config.xml文件
				配置内容：
				<typeHandlers>
					<typeHandler handler="org.apache.ibatis.type.EnumOrdinalTypeHandler"></typeHandler>
				</typeHandlers>
				在mapper.xml文件中，要解析的字段要配置typeHandler
				例如：
				结果集配置：<result column="sex" typeHandler="org.apache.ibatis.type.EnumOrdinalTypeHandler" property="sex" />
				语句配置：
				<insert id="insertUserByTypeHandler">
					insert into `user`(sex)
					values(#{sex,typeHandler=org.apache.ibatis.type.EnumOrdinalTypeHandler})
				</insert>
			3.4.3.2 EnumTypeHandler
				他插入的是枚举的对应字符串
			3.4.3.3 自定义类的typeHandler
				自己写的自定义类要实现TypeHandler接口，加上注释，传入处理值类型
				@MappedTypes(String.class)
				public class UserRemarkTypeHandler implements TypeHandler<String>
				
		3.5 ObjectFactory
			当mybatis创建一个返回结果时，都会用ObjectFactory去构建POJO。

		3.6 插件

		3.7 environment配置环境
			3.7.1 概述
				数据源：1.数据库源的配置 2：数据库事务的配置

				transactionManager配置的是数据库事务，其中type有三种配置方式
				1：JDBC
				2：MANAGED 容易管理，在JNDI数据源中常用
				3：自定义

				dataSource：配置数据源连接的信息，type有四种方式
				1：UNPOOLED：非连接池数据库
				2：POOLED：连接池数据库
				3：JNDI：JNDI数据源
				4：自定义数据源

			3.7.2 数据库事务
				
			3.7.3 数据源
				如果要用其他的数据源，就要使用自定义数据源，就必须实现org.apache.ibatis.datasource.DataSourceFactory

		3.8 databaseIdProvider 数据库厂商标识
			
		3.9 引入映射器的方法

第四章 映射器
	4.1 映射器的主要元素
		
	4.2
		4.2.3自动映射
			autoMappingBehavior：
				NONE：
					的时候，mybatis 会自动提供映射功能。
				PARTIAL
					自会自动映射，没有定义嵌套结果集映射的结果集
				FULL
					会自动映射任何负责的结果集
			默认是PARTIAL

		4.2.4 映射参数
			当参数小于5个时，用@Param的最好的，大于5用javaBean
		
		4.2.5 使用resultMap映射结果集
	
		4.3.2 主键回填和自定义
			userGeneratedKeys：是否使用数据库内置策略生成
			特殊生成的主键可以用
				<selectKey keyProperted="id" parameterType="role" useGenneratedKeys="true" keyProperty="id">
					<selectKey keyProperty="id" resultType="int" order="BEFORE">
						select if (max(id) is null,1,max(id)+2) as newId from t_role
					</selectKey>
					inserty ......
		
		4.5.2 存储过程支持
			如果返回的值是null，mybatis不知道null是什么类型！！所以要设置jdbcType
		
		4.5.3特殊紫都城替换和处理
			
	4.6 sql元素

	4.7 懒加载
		可以在全局配置也可以在局部配置
		全局配置：在配置文件里面配置：<setting name = "aggressiveLazyLoading" value = "false"/>默认是true，就是默认是开启懒加载的
 		局部是在级联标签上配置<association>和<collection>fetchType属性，fetchType有连个属性：eager（不懒加载）和lazy（开启懒加载）。

	4.8 缓存ceche
		4.8.1 系统缓存（一级缓存和二级缓存）】
			一级缓存就是sqlsession，sqlsession各自之间是隔离的。二级缓存就是是sqlsessionFactory，
			默认是开启一级缓存，所以在参数和sql完全一样的情况下，我么能使用同一个SqlSession对象调用同一个Mapper的方法时，往往只执行一次sql。因为第一次查的时候，会将查询结果放到缓存中。所以再查直接用。这个可以设置超时和刷新时间。
			二级缓存是需要我们设置开启的
				配置<cache/>急速开启二级缓存
				因为 是SqiSessionFactory层面的，所以不同的SqlSession是不隔离的。
			
		
第五章 动态SQL
	5.1 概述
		<where>
			<if>
			</if>
		</where>
		如果if不成立，where则不会出现
		<set>会自动把最后一个逗号去掉
	
			
			

	


	

