第六章 mybatis的解析和运行原理
	mybatis运行分为两大部分：
	1：读取配置文件缓存到Configuration对象，用以创建SqlSessionFactory
	2：sqlsession的执行过程
	涉及技术：
	.反射
	.动态代理
		动态代理分为JDK动态代理和CGLIB代理
		JDK动态代理：
			JDK的动态代理，是由JDK的java.lang.reflect.*包提供支持的，我们需要完成这么几个步骤。
				.编写服务类和接口，这个是真正的服务提供者，在JDK代理接口是必须的。
				.编写代理类，提供绑定和代理方法。
				JDK动态代理最大的缺点就是需要提供接口
				Mapper就是采用jdk动态代理
		CGLIB动态代理
		Mybatis通常在延迟加载的时候才会用到CGLIB的动态代理。

	SqlSessionFactory的构建过程
	SqlsessionFactory的主要功能是创建核心接口SqlSession。他是通过SqlsessionFactoryBuilder构建的，构建分为两步。
	第一步：通过org.apache.ibatis.xml.XMLConfigBuilder解析配置的XML文件，读取配置参数，并将读取的数据存入org.apache.ibatis.session.Configuration类中。Mybatis几乎所有的配置都是存在这里的。
	第二步：使用Configuration对象去创建SqlSessionFactory。Mybatis中的SqlSessionFactory只是一个接口，真正实现类是org.apache.ibatis.session.defaults.DefaultSqlSessionFactory.
	这里就用到了建造者模式，单例模式。因为SqlSesiionFactory的作用是创建SqlSession，可以重复使用SqlSesiionFactory，所以保证全局就一个SqlSesiionFactory是最好的，所以用单例模式。

	构建Condiguration
	在构建SqlSessionFactory的构建中，Configuration是最重要的，所有的配置都保存在这个对象中，它的作用如下：
	.读入配置文件，包括配置的xml文件和映射器xml文件
	.初始化基础配置
	.提供单例，为后续创建SessionFactory服务并提供配置参数。
	.执行一些重要的对象方法，初始化配置信息。
	Confinguration创建就是一个单例模式！

	6.2.2 映射器的内部组成
		映射器由三个部分组成
		.MappedStatement:保存着我们配置的sql，缓存、languageDriver等配置信息。
		.SqlSource:它是提供BoundSql对象的地方，它是MappedStatement的一个属性。它是一个接口，主要作用是根据参数和其他的规则组装SQL。
		.BoundSql:它是建立SQL和参数的地方，它有三个常用的属性：SQL、parameterObject、parameterMappings。
				.parameterObject它是参数本身。
				.传递简单对象，比如当我们传递int类型的时候，MyBatis会把参数变为Integer对象传递。类似long，float等也是如此。
				.如果我们传的是POJO或者Map，那么这个paramObject就是你传入的POJO或者Map不变。
				.如果我们没有用@Param注解，那么MyBatis就会自动把paramObject变为一个Map<String,Object>对象，其键值关系是按参数顺序来规划的，类似于{"1":p1,"2":p2,...,"param1":p1,"param2":p2......},所以，在编写的时候，我们可以直接使用#{param1}或者#{1}去直接引用我们的第一个参数。
				.如果用了@Param注解，那么mybatis就会把parameterObject变成一个Map<String,Object>对象。
				.parameterMappings.它是一个List，每一个元素都是ParameterMappin对象。这个对象会描述我们的参数。包括属性，名称，表达式，javaType、jdbcType...等重要信息。
				.sql属性就是我们写在映射器里的一条sql，
	6.2.3 构建SqlSessionFactory
		sqlSession = new SqlSessionFactoryBuilder().build(inputStream);
6.3 SqlSession运行过程
	SqlSession是一个接口，给出了所有增删改查的方法
	
	6.3.1 映射器的动态代理
		Mapper的映射是通过动态代理实现的。
		在MapperProxyFactory生成一个动态代理对象（占位），代理方法放在MapperProxy类里。
		MapperMethod采用命令模式运行。
		所以Mybatis使用接口便能运行sql，因为映射器的xml文件的命名空间对应的便是这个接口的全路径，那么它根据全路径和方法名便能够绑定起来。通过动态代理，让这个接口跑起来，而后采用命令模式，最后还是使用SqlSession的方法使得它能够执行查询。

		6.3.2 sqlsession下的四大对象
			6.3.2.1 Executor:执行器：
			由它来调度StateHandler,ParameterHandler,ResultHandler等来执行对应的SQL
			 描述；执行器，mybatis存在三种执行器
				.SIMPLE：简单执行器，不配置就是默认的
					SimpleExecutor：看代码：Configuration来构建StatementHandler，然后使用prepareStatement方法，对SQL编译并对参数进行初始化，我们在看它的实现过程，它调用了StateMentHandler的prepare()进行了预编译和基础设置，然后通过StatementHandler的parameterize()来设置参数并执行，resultHandler再组装查询结果返回给调用者来完成一次查询。这样我们的焦点又转移到了StatementHandler上。
						
				.REUSER：执行重用预处理语句
				.BATCH：针对批量操作专用的执行器。

			6.3.2.2 StatementHandler:数据库会话器，
			作用是使用数据库的Statement（PreparedStatement）执行操作，它是四大对象的核心。
			 描述：和Executor一样分为三种：SimpleStatementHandler，PreparedStatementHandler，CallableStatementHandler
			 专门处理数据库会话的。

			6.3.2.3 ParamterHandler：参数处理器，用于SQL对参数的处理。
				完成对预编译参数的设置

			6.2.3.4 ResultHandler:返回结果处理器，进行最后数据集（ResultSet）的封装返回处理的。
				组装结果集返回

org.apache.ibatis.builder.BaseBuilder使用的是建造者模式



session的动态代理模式：
代理类：org.apache.ibatis.session.SqlSessionManager
SqlSessionManager类里：
 私有的构造函数！创建一个session的代理对象
	private final SqlSessionFactory sqlSessionFactory;
	private final SqlSession sqlSessionProxy;
	private SqlSessionManager(SqlSessionFactory sqlSessionFactory) {
		this.sqlSessionFactory = sqlSessionFactory;
		//下面是使用jdk动态代理。
		this.sqlSessionProxy = (SqlSession) Proxy.newProxyInstance(
		SqlSessionFactory.class.getClassLoader(),
		new Class[]{SqlSession.class},
		new SqlSessionInterceptor());
	}
	调用真是对象方法是在SqlSessionManager的内部类--SqlSessionInterceptor
	private class SqlSessionInterceptor implements InvocationHandler {
		public SqlSessionInterceptor() {
			// Prevent Synthetic Access
		}
		@Override
		public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
		  final SqlSession sqlSession = SqlSessionManager.this.localSqlSession.get();
		  if (sqlSession != null) {
			try {
			  return method.invoke(sqlSession, args);
			} catch (Throwable t) {
			  throw ExceptionUtil.unwrapThrowable(t);
			}
		  } else {
			final SqlSession autoSqlSession = openSession();
			try {
			  final Object result = method.invoke(autoSqlSession, args);
			  autoSqlSession.commit();
			  return result;
			} catch (Throwable t) {
			  autoSqlSession.rollback();
			  throw ExceptionUtil.unwrapThrowable(t);
			} finally {
			  autoSqlSession.close();
			}
		  }
		}
	}
	实现类是org.apache.ibatis.session.defaults.DefaultSqlSession

 
