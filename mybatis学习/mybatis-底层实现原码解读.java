������ mybatis�Ľ���������ԭ��
	mybatis���з�Ϊ���󲿷֣�
	1����ȡ�����ļ����浽Configuration�������Դ���SqlSessionFactory
	2��sqlsession��ִ�й���
	�漰������
	.����
	.��̬����
		��̬�����ΪJDK��̬�����CGLIB����
		JDK��̬����
			JDK�Ķ�̬��������JDK��java.lang.reflect.*���ṩ֧�ֵģ�������Ҫ�����ô�������衣
				.��д������ͽӿڣ�����������ķ����ṩ�ߣ���JDK����ӿ��Ǳ���ġ�
				.��д�����࣬�ṩ�󶨺ʹ�������
				JDK��̬��������ȱ�������Ҫ�ṩ�ӿ�
				Mapper���ǲ���jdk��̬����
		CGLIB��̬����
		Mybatisͨ�����ӳټ��ص�ʱ��Ż��õ�CGLIB�Ķ�̬����

	SqlSessionFactory�Ĺ�������
	SqlsessionFactory����Ҫ�����Ǵ������Ľӿ�SqlSession������ͨ��SqlsessionFactoryBuilder�����ģ�������Ϊ������
	��һ����ͨ��org.apache.ibatis.xml.XMLConfigBuilder�������õ�XML�ļ�����ȡ���ò�����������ȡ�����ݴ���org.apache.ibatis.session.Configuration���С�Mybatis�������е����ö��Ǵ�������ġ�
	�ڶ�����ʹ��Configuration����ȥ����SqlSessionFactory��Mybatis�е�SqlSessionFactoryֻ��һ���ӿڣ�����ʵ������org.apache.ibatis.session.defaults.DefaultSqlSessionFactory.
	������õ��˽�����ģʽ������ģʽ����ΪSqlSesiionFactory�������Ǵ���SqlSession�������ظ�ʹ��SqlSesiionFactory�����Ա�֤ȫ�־�һ��SqlSesiionFactory����õģ������õ���ģʽ��

	����Condiguration
	�ڹ���SqlSessionFactory�Ĺ����У�Configuration������Ҫ�ģ����е����ö���������������У������������£�
	.���������ļ����������õ�xml�ļ���ӳ����xml�ļ�
	.��ʼ����������
	.�ṩ������Ϊ��������SessionFactory�����ṩ���ò�����
	.ִ��һЩ��Ҫ�Ķ��󷽷�����ʼ��������Ϣ��
	Confinguration��������һ������ģʽ��

	6.2.2 ӳ�������ڲ����
		ӳ�����������������
		.MappedStatement:�������������õ�sql�����桢languageDriver��������Ϣ��
		.SqlSource:�����ṩBoundSql����ĵط�������MappedStatement��һ�����ԡ�����һ���ӿڣ���Ҫ�����Ǹ��ݲ����������Ĺ�����װSQL��
		.BoundSql:���ǽ���SQL�Ͳ����ĵط��������������õ����ԣ�SQL��parameterObject��parameterMappings��
				.parameterObject���ǲ�������
				.���ݼ򵥶��󣬱��統���Ǵ���int���͵�ʱ��MyBatis��Ѳ�����ΪInteger���󴫵ݡ�����long��float��Ҳ����ˡ�
				.������Ǵ�����POJO����Map����ô���paramObject�����㴫���POJO����Map���䡣
				.�������û����@Paramע�⣬��ôMyBatis�ͻ��Զ���paramObject��Ϊһ��Map<String,Object>�������ֵ��ϵ�ǰ�����˳�����滮�ģ�������{"1":p1,"2":p2,...,"param1":p1,"param2":p2......},���ԣ��ڱ�д��ʱ�����ǿ���ֱ��ʹ��#{param1}����#{1}ȥֱ���������ǵĵ�һ��������
				.�������@Paramע�⣬��ômybatis�ͻ��parameterObject���һ��Map<String,Object>����
				.parameterMappings.����һ��List��ÿһ��Ԫ�ض���ParameterMappin�������������������ǵĲ������������ԣ����ƣ����ʽ��javaType��jdbcType...����Ҫ��Ϣ��
				.sql���Ծ�������д��ӳ�������һ��sql��
	6.2.3 ����SqlSessionFactory
		sqlSession = new SqlSessionFactoryBuilder().build(inputStream);
6.3 SqlSession���й���
	SqlSession��һ���ӿڣ�������������ɾ�Ĳ�ķ���
	
	6.3.1 ӳ�����Ķ�̬����
		Mapper��ӳ����ͨ����̬����ʵ�ֵġ�
		��MapperProxyFactory����һ����̬�������ռλ��������������MapperProxy���
		MapperMethod��������ģʽ���С�
		����Mybatisʹ�ýӿڱ�������sql����Ϊӳ������xml�ļ��������ռ��Ӧ�ı�������ӿڵ�ȫ·������ô������ȫ·���ͷ��������ܹ���������ͨ����̬����������ӿ��������������������ģʽ�������ʹ��SqlSession�ķ���ʹ�����ܹ�ִ�в�ѯ��

		6.3.2 sqlsession�µ��Ĵ����
			6.3.2.1 Executor:ִ������
			����������StateHandler,ParameterHandler,ResultHandler����ִ�ж�Ӧ��SQL
			 ������ִ������mybatis��������ִ����
				.SIMPLE����ִ�����������þ���Ĭ�ϵ�
					SimpleExecutor�������룺Configuration������StatementHandler��Ȼ��ʹ��prepareStatement��������SQL���벢�Բ������г�ʼ���������ڿ�����ʵ�ֹ��̣���������StateMentHandler��prepare()������Ԥ����ͻ������ã�Ȼ��ͨ��StatementHandler��parameterize()�����ò�����ִ�У�resultHandler����װ��ѯ������ظ������������һ�β�ѯ���������ǵĽ�����ת�Ƶ���StatementHandler�ϡ�
						
				.REUSER��ִ������Ԥ�������
				.BATCH�������������ר�õ�ִ������

			6.3.2.2 StatementHandler:���ݿ�Ự����
			������ʹ�����ݿ��Statement��PreparedStatement��ִ�в����������Ĵ����ĺ��ġ�
			 ��������Executorһ����Ϊ���֣�SimpleStatementHandler��PreparedStatementHandler��CallableStatementHandler
			 ר�Ŵ������ݿ�Ự�ġ�

			6.3.2.3 ParamterHandler������������������SQL�Բ����Ĵ���
				��ɶ�Ԥ�������������

			6.2.3.4 ResultHandler:���ؽ��������������������ݼ���ResultSet���ķ�װ���ش���ġ�
				��װ���������

org.apache.ibatis.builder.BaseBuilderʹ�õ��ǽ�����ģʽ



session�Ķ�̬����ģʽ��
�����ࣺorg.apache.ibatis.session.SqlSessionManager
SqlSessionManager���
 ˽�еĹ��캯��������һ��session�Ĵ������
	private final SqlSessionFactory sqlSessionFactory;
	private final SqlSession sqlSessionProxy;
	private SqlSessionManager(SqlSessionFactory sqlSessionFactory) {
		this.sqlSessionFactory = sqlSessionFactory;
		//������ʹ��jdk��̬����
		this.sqlSessionProxy = (SqlSession) Proxy.newProxyInstance(
		SqlSessionFactory.class.getClassLoader(),
		new Class[]{SqlSession.class},
		new SqlSessionInterceptor());
	}
	�������Ƕ��󷽷�����SqlSessionManager���ڲ���--SqlSessionInterceptor
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
	ʵ������org.apache.ibatis.session.defaults.DefaultSqlSession

 
