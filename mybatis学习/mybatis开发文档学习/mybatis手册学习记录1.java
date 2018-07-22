mybatis�����ֲ��Ķ�ѧϰ
1������
1.1Ϊʲôʹ��mybatis���
	mybatis�ǵ�һ����־û����֧���Զ���sql��䡢�洢�ṹ����ǰ���䡣�����˴󲿷�jdbc���ֶ����úͼ������mybatis����ʹ�ü򵥵�xml�ļ���ע����������ԭ�룬����ӿں���POJO����ͨ��java�������䵽���ݿ��¼�ϡ�
2��ʹ��mybatis

	2.1�����ȵ�����Ҫ�İ�
	ʹ��maven������Ŀ����������İ���versionΪmybatis���İ汾�š�
	<dependency>
		<groupId>org.mybatis</groupId>
		<artifactId>mybatis</artifactId>
		<version>${version}</version>
	</dependency>

	2.2����SqlSessionFactory��xml�ļ��С�
	mybatis��Ӧ����Ҫ����Χ����SqlSessionFactory���ʵ��ʹ�õġ���SqlSessionFactory��Ҫͨ��SqlSessionFactoryBuilder�������ȥ��ȡ��
	���SqlSessionFactoryBuilder�������������;��1��ͨ�������ļ����� 2���Լ��ֶ����崴��ʵ����ȡ������ʹ�õ�һ�֣�Ŀǰ�󲿷ֶ���ʹ�õ�һ�֡�
	
	2.2.1���Ƚ���һ�µ�һ�֣�ͨ�������ļ���ȡSqlSessionFactoryBuilder��
	//
	String resource = "org/mybatis/example/mybatis-config.xml";//�����ļ�·��
	InputStream inputStream = Resources.getResourceAsStream(resource);
	SqlSessionFactory sqlSessionFactory =
	new SqlSessionFactoryBuilder().build(inputStream);

	//�����ļ�����
	<?xml version="1.0" encoding="UTF-8" ?>
	<!DOCTYPE configuration
		PUBLIC "-//mybatis.org//DTD Config 3.0//EN"
		"http://mybatis.org/dtd/mybatis-3-config.dtd">
	<configuration>
		<environments default="development">
			<environment id="development">
			<transactionManager type="JDBC"/>
				<dataSource type="POOLED">
				<property name="driver" value="${driver}"/>
				<property name="url" value="${url}"/>
				<property name="username" value="${username}"/>
				<property name="password" value="${password}"/>
				</dataSource>
			</environment>
		</environments>
		<mappers>
		<mapper resource="org/mybatis/example/BlogMapper.xml"/>
		</mappers>
	</configuration>

	2.2.2�ڶ��֣��ֶ���ȡSqlSessionFactoryBuilderʵ��
	DataSource dataSource = BlogDataSourceFactory.getBlogDataSource();
	TransactionFactory transactionFactory =
	new JdbcTransactionFactory();
	Environment environment =
	new Environment("development", transactionFactory, dataSource);
	Configuration configuration = new Configuration(environment);
	configuration.addMapper(BlogMapper.class);
	SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(configuration);

	2.3��SqlSessionFactoryBuilder���SqlSessionFactory
	SqlSession session = sqlSessionFactory.openSession();
	try {
		Blog blog = session.selectOne(
		"org.mybatis.example.BlogMapper.selectBlog", 101);
	} finally {
		session.close();
	}

	2.4���ݿ�ӳ���ļ�д��
	<?xml version="1.0" encoding="UTF-8" ?>
	<!DOCTYPE mapper
		PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN"
		"http://mybatis.org/dtd/mybatis-3-mapper.dtd">
	<mapper namespace="org.mybatis.example.BlogMapper">
		<select id="selectBlog" resultType="Blog">
			select * from Blog where id = #{id}
		</select>
	</mapper>

	2.5.�ڽӿ��е���
	Blog blog = session.selectOne("org.mybatis.example.BlogMapper.selectBlog", 101);

	2.6 �����ռ�namespaced��ע������
	1�����resultTypeû��дʵ�����ȫ·����ֻ��д�������պ������Ŀ����������ͬ���࣬��ʱϵͳ�ᱨ������

	2.7������ֱ���ڷ�����ͨ��ע������sql�Զ�����������ͨ��mapper.xml�ļ�
		@Select("SELECT * FROM blog WHERE id = #{id}")
		Blog selectBlog(int id);
	
	2.8��Χ����������
	����ȷ��ʹ�û��������صĲ������⡣
	һ�����Ƕ���ʹ����spring����������ǹ�����sqlsession��ͨ��spring�����Ķ��ǰ�ȫ�̣߳����Բ��õ��Ĳ������⡣
	
	2.8.1 SqlSessionFactoryBuilder
	ʹ��SqlSessionFactoryBuilder����SqlSessionFactory������ȫ��ֻ��Ҫ����һ��SqlSessionFactoryBuilder��Ȼ���ظ�����SqlSessionFactory����������ȷ���ڽ�����xml�ʼ��Ǻ�Ϳ����ͷ���Դ��ȥ������������Ҫ�����顣

	2.8.2 SqlSessionFactory
	ÿ���̶߳���һ���Լ���2SqlSessionFactoryʵ������ʵ������ͱ��˹���������õķ�Χ��������ͷ�����Χ���ھ�̬���в�Ҫ��������SqlSessionFactory�����������ʵ���ֶΡ���Զ��Ҫ���κ��йܷ�Χ�ڶ�SqlSession�������á�����HttpSession��servlet��ܣ���������κ�һ��web��ܣ��뿼����SqlSession����ѭ��HTTP�������Ƶķ�Χ�����仰˵���ڽ���http��������Դ�һ��sqlsession��Ȼ���ڷ��������ʱ������Թر������ر�session�Ƿǳ���Ҫ�ģ�����Ҫ����ȷ�����ر�����finally���У���������ȷ�Ϲر�sqlsession�ı�׼ģʽ��
		SqlSession session = sqlSessionFactory.openSession();
		try {
		// do work
		} finally {
		session.close();
		}

	2.1.6.4 mapper instance
	ӳ�������㴴����ӳ�����Ľӿڡ�ӳ������ʵ���Ǵ�sqlsession��ȡ�õģ����������Ӹü�������˵��ӳ����ʵ�����ķ�Χ�ͺ����������Sqlsessionһ����Ȼ����ӳ����ʵ����õķ�Χ�Ƿ�����Χ����ʹ�����ǵķ������������ǡ�Ȼ�󱻶����������ǲ���Ҫ����ʾ�عرա�Ȼ����������������������б������ǲ������⡣������sqlsession������ܷ�������������Ϲ���̫�����Դ��ʧȥ���ƣ���֤���ļ򵥣�����ӳ����������������Χ�У������������ʾ�����������
	SqlSession session = sqlSessionFactory.openSession();
	try {
	BlogMapper mapper = session.getMapper(BlogMapper.class);
		// do work
	} finally {
		session.close();
	}

3.����XML

	3.1 ����
	mybatis���ð����˶�mybatis��Ϊ������Ӱ������úͲ�����
	3.1.1 properties
	��Щ�ǿ��ⲿ���ģ����滻�����ԣ������ڵ��͵�Java�����ļ�ʵ�������á���ͨ������Ԫ�ص���Ԫ�ش��ݡ�����
		<properties resource="org/mybatis/example/config.properties">
			<property name="username" value="dev_user"/>
			<property name="password" value="F2Fa3!33TYyg"/>
		</properties>
	Ȼ�󣬿��������������ļ���ʹ���������滻��Ҫ��̬���õ�ֵ������
		<dataSource type="POOLED">
			<property name="driver" value="${driver}"/>
			<property name="url" value="${url}"/>
			<property name="username" value="${username}"/>
			<property name="password" value="${password}"/>
		</dataSource>
	����������˯��Ԫ�ر�����ֵʱ���˺ź����뽫�ᱻ�滻��driver��url���Խ��ᱻ����config��properties�ļ���ֵ�滻
	����Ҳ����ͨ��SqlSessionFactory.Builder.build()����ע�룬����
	SqlSessionFactory factory = sqlSessionFactoryBuilder.build(reader, props);
	// ... or ...
	SqlSessionFactory factory = new SqlSessionFactoryBuilder.build(reader, environment, props);
	�������������������Щ�ط���Mybatis�ᰴ������˳��װ�����ǣ�
	.���ȶ�ȡ����Ԫ�����屻ָ�������ԡ�
	.��δ�����Ԫ�ص�ԭ·������url״̬�������ԣ�����д�Ѿ�ָ�����κ��ظ����ԡ�
	.�����Ϊ�����������ݵ����Ա���ȡ�������Ǵ����������Դ/url�����м��ص��κ��ظ����ԡ�
	��ˣ�������ȼ���������Ϊ����������������ԣ�Ȼ������Դ/url���ԣ����������Ԫ��������ָ�������ԡ�
	��mybatis3.4.2�汾��ʼ�������ָ��һ��Ĭ��ֵ�������ռλ����
	<dataSource type="POOLED">
		<!-- ... -->
		<property name="username" value="${username:ut_user}"/> <!-- If 'username' property not present, username become 'ut_user' -->
	</dataSource>
	�������Ĭ����û�õģ������ָ��һ��Ĭ��ֵ��ռλ����ʾ������Ҫ������ָ�����������øù��ܡ�
	<properties resource="org/mybatis/example/config.properties">
		<!-- ... -->
		<property name="org.apache.ibatis.parsing.PropertyParser.enable-default-value" value="true"/> <!-- Enable this feature -->
	</properties>
	������Ѿ�ʹ��":"��Ϊ���Լ�(e.g.  db:username ) �������Ѿ�ʹ��OGNL�����󵼺�ͼ���ԣ��е���Ԫ���ʽ�������sql�����У�����Ҫ���ָ���������޸ĵ����ļ���Ĭ��ֵ���ַ���
	<properties resource="org/mybatis/example/config.properties">
		<!-- ... -->
		<property name="org.apache.ibatis.parsing.PropertyParser.default-value-separator" value="?:"/> <!-- Change default value of separator -->
	</properties>

	<dataSource type="POOLED">
		<!-- ... -->
		<property name="username" value="${db:username?:ut_user}"/>
	</dataSource>

	3.1.2 ����
	����һ���ֳ���Ҫ�ĵ������Ƕ�����ʱ��myvatis�����޸ġ�����ı�����������á����塢��Ĭ��ֵ��
	cacheEnabled
	������ȫ�����û�����ڴ��������������κ�mapper�е��κλ��档
	Ĭ��ֵ��true��

	lazyLoadingEnabled
	������ȫ����������������ء�������ʱ�����еĹ�ϵ���ᱻ�����ء����ֵ����ͨ��ʹ������fetchType����������ض��Ĺ�ϵ��
	Ĭ��ֵ��false

	aggressiveLazyLoading
	������������ʱ���κη������ö�����ض��������е����������ԡ���һ���棬ÿ�����Ի�����Ҫʱ�����أ��μ�lazyLoadTriggerMeth����
	Ĭ��ֵ��true��trun in <= 3.4.1��

	multipleResultSetsEnabled
	���������������ӵ�������з��ض���������������Ҫ��
	Ĭ��ֵ��true

	useColumnLabel
	������ʹ���б�ǩ����ʹ����������ͬ���������ⷽ���в�ͬ�ı��֡�����������ļ��ĵ�����������ģʽ�����ԣ�ȥȷ����������������Ϊ��
	Ĭ��ֵ��true��

	useGeneratedKeys
	����������JDBC�����ɵļ���֧�֡���Ҫһ�����ݵ����������������Ϊtrue���������ǿ��������Կ����Ϊ��Щ��������ܾ����ݣ�����Ȼ��Ч��
	Ĭ��ֵ��false

	autoMappingBehavior
	������ָ��mybatis�Ƿ��Զ�ӳ���е��ֶκ����ԡ�
		NONE�����Զ�ӳ�䡣
		PARTIAL������ֻ�Զ�ӳ���������������ڲ�����Ƕ�׵Ľ��ӳ�䡣
		Full�����Զ������κθ����ԵĽ��ӳ�䡣
	ֵ��NONE,PARTIAL,FULL
	Ĭ��ֵ��PARTIAL

	autoMappingUnknownColum 
	�����������һ���Զ�ӳ��Ŀ���δ֪�У�����δ֪�������ͣ�ʱ��ָ��һ����Ϊ��
		NONE:�����κ�����
		WARNING������һ�����棨��־������־����ġ�org.apache.ibatis.session.AutoMappingUnknownColumnBehavior������		���ó�WARN
		FAILING:ʧ�ܵ�ӳ�䣨�׳�SqlSessionExcept���쳣��
	ֵ��NONE, WARNING,FAILING
	Ĭ��ֵ��NONE

	defaultExecutorType
	����������Ĭ�ϵ�ִ���ߡ�
		SIMPLE��ִ���߲����κ�ָ�������顣
		REUSE��ִ��������׼�������
		BATCH��ִ��������������������
	Ĭ��ֵ��SIMPLE

	defaultStatementTimeout
	����������������ȴ����ݿⷵ�ص�ʱ��
	Ĭ�ϣ��������κ�ֵ

	defaultFetchSize
	�������������������һ����ʾ����������ץȡ���ؽ���Ĵ�С������������Ա���ѯ������д��
	Ĭ�ϣ��������κ�ֵ��

	safeRowBoundsEnabled��
	����������ʹ��RowBounds���а󶨣���Ƕ������У�����������ó�false��
	Ĭ��ֵ��false

	safeResultHandlerEnabled
	����������ʹ��ResultHandler��������������Ƕ������У�����������ó�false
	Ĭ��ֵ��false

	mapUnderscoreToCamelCase
	����������Ӿ������ݿ�������A_COLUMN�Զ�ӳ�䵽camel case����Java��������aColumn��
	Ĭ��ֵ��false

	localCacheScope
	������mybatisʹ�ñ��ػ���ȡ��֤ѭ�������ͼӿ�Ƕ�ײ�ѯ��
		SESSION�������е�session�ǻ���ʱ��Ĭ�ϵ����в�ѯִ������session��
		STATEMENT:���ص�session���Ự�����������ִ�С�û�����ݽ���������ͬ�ĵ��ù���ͬһ��SqlSession�ġ�
	Ĭ��ֵ��SESSION

	jdbcTypeForNull
	��������û��Ϊ�����ṩ�ض���JDBC����ʱ��ָ��nullֵ��JDBC���ͣ�һЩ����Ҫ��ָ����JDBC���͵���������ʹ��ͨ��ֵ����NULL,VARCHAR ���� OTHER.
	ֵ��Most common are: NULL,VARCHAR and OTHER
	Ĭ��ֵ��OTHRE

	lazyLoadTriggerMethods
	������ָ���κ�һ������ķ������������ء�
	ֵ���ö��ŷָ��ķ������б�
	Ĭ��ֵ��equals,clone,hashCode,toString

	defaultScriptingLanguage
	������ָ�����ڶ�̬SQL���ɵ�Ĭ�����ԡ�
	ֵ��A type alias or fully qualified class name
	Ĭ��ֵ��org.apache.ibatis.scripting.xmltags.XMLLanguageDriver
	
	defaultEnumTypeHandler
	������ָ��ö��Ĭ��ʹ�õ�TypeHandler����3.4.5�汾��
	ֵ�����ͱ�������ȫ�޶���������
	Ĭ��ֵ��org.apache.ibatis.type.EnumTypeHandler

	callSettersOnNulls
	������ָ����һ������ֵΪnullʱ���Ƿ����seter��map��put���������Ǻ����õģ�����׼������Map.keySet()���ֵ��ʼ		��ʱ��ע��ԭ���(int,boolean��)�����ᱻ����Ϊnull��
	Ĭ��ֵ��false

	returnInstanceForEmptyRowMyBatis
	�������������������е���ΪNULLʱ��mybatisĬ�Ϸ��ؿա����������������ʱ��mybatis����һ��ʵ��ʵ��ȥ�����ע��		����Ƕ�׽��Ҳ�������ġ�
	ֵ���κ��ַ���
	Ĭ��ֵ��������

	logPrefix
	������ִ��mybatis��ӵ���־�����ֵ�ǰ׺�ַ�����
	ֵ���κ��ַ���
	Ĭ��ֵ��������

	logImpl
	������ָ��mybatis��Ҫ�õ���־ʵ�֡����������ò����ڣ���־ʵ�ֻ��Լ��Զ��ҵ���Ӧ��ʵ�֡�
	ֵ��SLF4J | LOG4J | LOG4J2| JDK_LOGGING |COMMONS_LOGGING| STDOUT_LOGGING |NO_LOGGING
	Ĭ��ֵ��������

	proxyFactory
	������ָ��mybatis���ڴ�������������������Ĵ����ߡ�
	ֵ��CGLIB | JAVASSIST
	Ĭ��ֵ��JAVASSIST (MyBatis 3.3 ������)

	vfsImpl
	������ָ��VFS��ʵ�� 
	ֵ���ɶ��ŷָ����Զ���VFSʵ�ֵ���ȫ�޶�����
	Ĭ��ֵ��������

	useActualParamName
	�����������ڷ���ǩ����������ʵ����������������
	Ĭ��ֵ��true

	configurationFactory	
	������ָ���ṩ�����ļ���һ���ࡣ��������ص������ļ�ʵ�������������ط����л���������ԡ�����������һ�������Ǵ��У� static Configuration getConfiguration()��ǩ��
	ֵ����ȫ���������ı�������
	Ĭ��ֵ��������

	��������ȫ����Ԫ�ص�һ�����ӡ�
	<settings>
		<setting name="cacheEnabled" value="true"/>
		<setting name="lazyLoadingEnabled" value="true"/>
		<setting name="multipleResultSetsEnabled" value="true"/>
		<setting name="useColumnLabel" value="true"/>
		<setting name="useGeneratedKeys" value="false"/>
		<setting name="autoMappingBehavior" value="PARTIAL"/>
		<setting name="autoMappingUnknownColumnBehavior" value="WARNING"/>
		<setting name="defaultExecutorType" value="SIMPLE"/>
		<setting name="defaultStatementTimeout" value="25"/>
		<setting name="defaultFetchSize" value="100"/>
		<setting name="safeRowBoundsEnabled" value="false"/>
		<setting name="mapUnderscoreToCamelCase" value="false"/>
		<setting name="localCacheScope" value="SESSION"/>
		<setting name="jdbcTypeForNull" value="OTHER"/>
		<setting name="lazyLoadTriggerMethods"
		value="equals,clone,hashCode,toString"/>
	</settings>

	3.1.3 typeAliases������������
	���ͱ���ֻ��Java���͵Ľ϶����ơ���ֻ��XML�����йأ����ҽ�����Ϊ�˼�����ȫ�޶��������������͡����磺
	<typeAliases>
		<typeAlias alias="Author" type="domain.blog.Author"/>
		<typeAlias alias="Blog" type="domain.blog.Blog"/>
		<typeAlias alias="Comment" type="domain.blog.Comment"/>
		<typeAlias alias="Post" type="domain.blog.Post"/>
		<typeAlias alias="Section" type="domain.blog.Section"/>
		<typeAlias alias="Tag" type="domain.blog.Tag"/>
	</typeAliases>
	ӵ��������ã�Blog���ڿ��������κ�domain.blog.Blog�����õĵط�����Ҳ����ָ��mybatis��ѯbean��Ҫ�İ���������
	<typeAliases>
		<package name="domain.blog"/>
	</typeAliases>
	ÿһ����domain.blog���ֵ�bean�����û���ҵ�ע�ͣ���ʹ��bean��δ��д�ķ��޶�����ע��Ϊ������domain.blog.Author����ע��Ϊauthor�����������@Aliasע�⣬���ע���ֵ���ᵱ��һ�������������������
	@Alias("author")
	public class Author {
		...
	}
	����������ͨjava���ͼ����˶�Ӧ�ı������͡����Ƕ��ǲ������Сд�ģ���ע�������������ض���ԭ������⴦�����֡�
	alias mapped type
	_byte   byte
	_long   long
	_short   short
	_int   int
	_integer   int
	_double   double
	_float   float
	_boolean   boolean
	string   String
	byte   Byte
	long   Long
	short   Short
	int   Integer
	integer   Integer
	double   Double
	float   Float
	boolean   Boolean
	date   Date
	decimal   BigDecimal
	bigdecimal   BigDecimal
	object   Object
	map   Map
	hashmap   HashMap
	list   List
	arraylist   ArrayList
	collection   Collection
	iterator   Iterator

	3.1.4 ���ʹ������
	���ۺ�ʱ��mybatis�ڸ����Խ������Ԥ�����������ֵ���ò���ʱ��һ��TypeHandler��һ���ʵ��ķ���ȥ����ֵת����java���͡�����ı����������Ĭ�ϵ�TypeHandlers
	ע����3.4.5�汾��mybatis�Ѿ�֧��JSR-310Ĭ��
	Type Handler         Java Types                   JDBC Types
	BooleanTypeHandler java.lang.Boolean ,boolean Any compatible  BOOLEAN
	ByteTypeHandler java.lang.Byte ,  byte Any compatible  NUMERIC or BYTE
	ShortTypeHandler java.lang.Short ,  short Any compatible  NUMERIC or SHORT INTEGER
	IntegerTypeHandler java.lang.Integer ,  int Any compatible  NUMERIC or INTEGER
	LongTypeHandler java.lang.Long ,  long Any compatible  NUMERIC or LONG INTEGER
	FloatTypeHandler java.lang.Float ,  float Any compatible  NUMERIC or FLOAT
	DoubleTypeHandler java.lang.Double ,  double Any compatible  NUMERIC or DOUBLE
	BigDecimalTypeHandler java.math.BigDecimal Any compatible  NUMERIC or DECIMAL
	StringTypeHandler java.lang.String CHAR ,  VARCHAR
	ClobReaderTypeHandler java.io.Reader -
	ClobTypeHandler java.lang.String CLOB ,  LONGVARCHAR
	NStringTypeHandler java.lang.String NVARCHAR ,  NCHAR
	NClobTypeHandler java.lang.String NCLOB
	BlobInputStreamTypeHandler java.io.InputStream -
	ByteArrayTypeHandler byte[] Any compatible byte stream type
	BlobTypeHandler byte[] BLOB ,  LONGVARBINARY
	DateTypeHandler java.util.Date TIMESTAMP
	DateOnlyTypeHandler java.util.Date DATE
	TimeOnlyTypeHandler java.util.Date TIME
	SqlTimestampTypeHandler java.sql.Timestamp TIMESTAMP
	SqlDateTypeHandler java.sql.Date DATE
	SqlTimeTypeHandler java.sql.Time TIME
	ObjectTypeHandler Any OTHER , or unspecified
	EnumTypeHandler Enumeration Type VARCHAR any string compatible
	type, as the code is stored (not index).
	EnumOrdinalTypeHandler Enumeration Type Any compatible  NUMERIC or DOUBLE , as the position is stored (not the code itself).
	InstantTypeHandler java.time.Instant TIMESTAMP
	LocalDateTimeTypeHandler java.time.LocalDateTime TIMESTAMP
	LocalDateTypeHandler java.time.LocalDate DATE
	LocalTimeTypeHandler java.time.LocalTime TIME
	OffsetDateTimeTypeHandler java.time.OffsetDateTime TIMESTAMP
	OffsetTimeTypeHandler java.time.OffsetTime TIME
	ZonedDateTimeTypeHandler java.time.ZonedDateTime TIMESTAMP
	YearTypeHandler java.time.Year INTEGER
	MonthTypeHandler java.time.Month INTEGER
	YearMonthTypeHandler java.time.YearMonth VARCHAR or  LONGVARCHAR
	JapaneseDateTypeHandler java.time.chrono.Japaneseate DATE
	
	�������д���������ͻ��ߴֻƽ����Լ���ȥ�����֧�ֻ�Ǳ�׼���͵����⡣��ô���Ļ���ʵ������ӿ� org.apache.ibatis.type.TypeHandler����extends ����������  org.apache.ibatis.type.BaseTypeHandler��ѡ�Ľ���ӳ���JDBC���͡�����Ϊ����
	// ExampleTypeHandler.java
	@MappedJdbcTypes(JdbcType.VARCHAR)
	public class ExampleTypeHandler extends BaseTypeHandler<String> {
	@Override
	public void setNonNullParameter(PreparedStatement ps, int i,
		String parameter, JdbcType jdbcType) throws SQLException {
		ps.setString(i, parameter);
		}
	@Override
	public String getNullableResult(ResultSet rs, String columnName)
		throws SQLException {
			return rs.getString(columnName);
	}
	@Override
	public String getNullableResult(ResultSet rs, int columnIndex)
		throws SQLException {
			return rs.getString(columnIndex);
	}
	@Override
	public String getNullableResult(CallableStatement cs, int columnIndex)
		throws SQLException {
			return cs.getString(columnIndex);
		}
	}

	<!-- mybatis-config.xml -->
	<typeHandlers>
		<typeHandler handler="org.mybatis.example.ExampleTypeHandler"/>
	</typeHandlers>

	����ôһ��TypeHandler �Ḳ���Ѿ����ڵĴ����ַ����������Ժ�varvhar������������ʹ�������ע�������ݿ��Ԫ�����ϣ�Mybatis���ᷴʡ��֤������͡����������ָ������VARCHAR�ֶ��ڲ����ͽ��ӳ���������ں���ȷ�����ʹ������󶨡�
	����������ʵ��ֱ�������䱻ִ�У�mybatis����֪�����ݵ����͵ġ�

	Mybatis��ָ�����ͨ����ʡ����ͨ������ȥ����TypeHandler��java���͡����������ͨ�������������������Ϊ��
	.��typeHandler��Ԫ�����javaType���� (����:  javaType="String" )
	.������TypeHandler�������һ��@MappedTypesע�ͣ�ָ��Ҫ��������java�����б������ע�⽫���ᱻ���������ejavaType�����Ѿ���ָ����

	����ͨ����������ָ������JDBC����
	.���һ��jdbcType���Ը�typeHandlerԪ�أ����� jdbcType="VARCHAR"����
	.�����TypeHandler���@MappedJdbcTypesע�⣬ָ�����������JDBC�����б�������jdbcType�����Ѿ���ָ�������ע�⽫���ᱻ��ǡ�

	��������ResultMapʹ���ĸ�TypeHandlerʱ��java���ͣ����Խ�����ͣ�����֪�ģ�����JDBC type��δ֪�ġ����mybatisʹ�������ϣ�javaType=[TheJavaType], jdbcType=null��ȥѡ��һ��TypeHandler������ζ����һ��  @MappedJdbcTypes ע���������TypeHandler�ķ�Χ��������ȷ���ã���������ResultMapsҲ����Ч�ġ�ΪResultMap����һ��TypeHandler���ԣ�����  includeNullJdbcType=true ��  @MappedJdbcTypes ע�����Mybatis3.4.0.������Σ����һ��������TypeHandler��ע��ȥ����java type����ResuleMapsʹ��java Typeʱ������Ĭ�ϱ�ʹ�� (i.e. even without  includeNullJdbcType=true )��
	��������ʹMybatis��ѯ���TypeHandlers
	<!-- mybatis-config.xml -->
	<typeHandlers>
		<package name="org.mybatis.example"/>
	</typeHandlers>

	ע�⣬����ʹ���Զ���ѯ���ܵ�ʱ��JDBC type ֻ����ע��ȥָ��������Դ���һ���ܹ�����������ͨ�õ�TypeHandlerΪ�����Ŀ�����һ��������ȥ�����൱��������Mybatis�ᴫ�����ʵ�ʵ��൱����TypeHandler��ʱ��
	//GenericTypeHandler.java
	public class GenericTypeHandler<E extends MyObject> extends BaseTypeHandler<E> {
		private Class<E> type;
		public GenericTypeHandler(Class<E> type) {
		if (type == null) throw new IllegalArgumentException("Type argument cannot be null");
			this.type = type;
		}
		...

	EnumTypeHandler ��  EnumOrdinalTypeHandler��ͨ�õ�TypeHandler�����ǽ�����ѧϰ�й����ǵ�ϸ�ڡ�
	
	3.1.5 Handling Enums
	�������ȥӳ��Enum������Ҫȥʹ��EnumTypeHandler ����  EnumOrdinalTypeHandler��
	���磬����������Ҫ�����ִ洢Բ��һЩ���ԡ�Ĭ�ϵ���Mybatis�оͻ�ʹ��ö����ʵ�֡�

	ע�⣬���������EnumTypeHandler������ģ��������������ͬ���ǣ�������ֻ����һ���ض����࣬������չö�ٵ��κ��ࡣ
	Ȼ�������ǿ��ܲ���洢���֡����ǵ�DBA���Լ�����ͱ��롣�Ǻܼ򵥣�����������ļ��н�EnumOrdinalTypeHandler��ӵ�typeHandler������ÿ��RoudingMode����ԭʼ����ֵӳ��һ������ֵ��.
	<!-- mybatis-config.xml -->
	<typeHandlers>
		<typeHandler handler="org.apache.ibatis.type.EnumOrdinalTypeHandler" javaType="java.math.RoundingMode"/>
	</typeHandlers>

	�����������ö����һ���ط�ӳ��string��������һ���ط�ӳ��integer������ô�죿��
	auto-mapper�����Զ�ʹ��EnumOrdinalTypeHandler����ˣ����������Ҫ����ʹ����ͨ����ͨ��EnumTypeHandler�����ǲ��ò���������ͨ��ʹ����Щsql�����ȷ������������������͡�
	��ӳ���ļ�ֱ����һ�ڲ��ܱ����ǣ���������������һ��ͨ������ĵ�����������������������������ٿ���
	<!DOCTYPE mapper
	PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN"
	"http://mybatis.org/dtd/mybatis-3-mapper.dtd">
	<mapper namespace="org.apache.ibatis.submitted.rounding.Mapper">
		<resultMap type="org.apache.ibatis.submitted.rounding.User" id="usermap">
			<id column="id" property="id"/>
			<result column="name" property="name"/>
			<result column="funkyNumber" property="funkyNumber"/>
			<result column="roundingMode" property="roundingMode"/>
		</resultMap>
		<select id="getUser" resultMap="usermap">
			select * from users
		</select>
		<insert id="insert">
			insert into users (id, name, funkyNumber, roundingMode) values (
			#{id}, #{name}, #{funkyNumber}, #{roundingMode}
			)
		</insert>
		<resultMap type="org.apache.ibatis.submitted.rounding.User" id="usermap2">
			<id column="id" property="id"/>
			<result column="name" property="name"/>
			<result column="funkyNumber" property="funkyNumber"/>
			<result column="roundingMode" property="roundingMode"
			typeHandler="org.apache.ibatis.type.EnumTypeHandler"/>
		</resultMap>
		<select id="getUser2" resultMap="usermap2">
			select * from users2
		</select>
		<insert id="insert2">
			insert into users2 (id, name, funkyNumber, roundingMode) values (
			#{id}, #{name}, #{funkyNumber}, #{roundingMode, typeHandler=org.apache.ibatis.type.EnumTypeHandler}
			)
		</insert>
	</mapper>

	ע�������ʹ���������ǵ�select�����ʹ��resultMap����resultType��

	3.1.6 ���󹤳̣�objectFactory��
	ÿһ�Σ�mybatisΪ������󴴽�һ���µ�ʵ��ʱ������ObjectFactoryʵ��ȥ���ġ�Ĭ�ϵ�ObjectFactoryֻʹ��Ĭ�Ϲ��캯��ʵ����Ŀ���ࡣ�������ȥ�������ObjectFactory����Ϊ����������Դ������Լ���ObjectFactory�����磺

	// ExampleObjectFactory.java
	public class ExampleObjectFactory extends DefaultObjectFactory {
		public Object create(Class type) {
			return super.create(type);
		}
		public Object create(Class type, List<Class> constructorArgTypes, List<Object> constructorArgs) {
				return super.create(type, constructorArgTypes, constructorArgs);
		}
		public void setProperties(Properties properties) {
			super.setProperties(properties);
		}
		public <T> boolean isCollection(Class<T> type) {
			return Collection.class.isAssignableFrom(type);
		}}

	<!-- mybatis-config.xml -->
	<objectFactory type="org.mybatis.example.ExampleObjectFactory">
		<property name="someProperty" value="100"/>
	</objectFactory>

	���ObjectFactory�ӿ��Ƿǳ��򵥵ģ�����������������������һ���Ǵ���Ĭ�Ϲ��캯��������һ���Ǵ���������캯����������setProperties�����������Ա����õ�ObjectFactoryʹ�á��ڰ���objectFactoryԪ�������ڶ�������Ծ��ᱻ���ݸ�setProperties�����������ڳ�ʼ�����ObjectFactoryʵ����

	3.1.7 ���
	Mybatis��������ӳ�����ִ���е�ĳЩ��ȥ���ص��á�Ĭ�ϵģ�Mybatis������ȥ���ط������á�
		? Executor (update, query, flushStatements, commit, rollback, getTransaction, close, isClosed)
		? ParameterHandler (getParameterObject, setParameters)
		? ResultSetHandler (handleResultSets, handleOutputParameters)
		? StatementHandler (prepare, parameterize, batch, update, query)
	��Щ�෽����ϸ�ڿ���ͨ��ÿһ�������ķ���ǩ��ȥ���֣��Լ�ÿ��MyBatis���а��ṩ��Դ���롣����Ҫ�������д�ķ����ľ���ʵ�֣��������������Ĳ������ļ������á������������޸ĺ���д���跽����ʵ�֣�����ܻ��ƻ�mybatis�ĺ��ġ���Щ����ͼ������ͷ����������ò��Ҫ�������ò�������ṩ��������Ư���򵥵ģ��򵥵�ʵ�������ʼ���ӿڣ�ȷ��ָ���������ȥ���ص�ǩ����

	// ExamplePlugin.java
	@Intercepts({@Signature(
		type= Executor.class,
		method = "update",
		args = {MappedStatement.class,Object.class})})
		public class ExamplePlugin implements Interceptor {
		public Object intercept(Invocation invocation) throws Throwable {
		return invocation.proceed();
	}
	public Object plugin(Object target) {
		return Plugin.wrap(target, this);
	}
	public void setProperties(Properties properties) {
		}
	}

	<!-- mybatis-config.xml -->
	<plugins>
	<plugin interceptor="org.mybatis.example.ExamplePlugin">
		<property name="someProperty" value="100"/>
	</plugin>
	</plugins>

	����Ĳ������ִ�г�ʼ��ʱ�������еĵ��á�update������������һ���ڲ����󣬸���ӳ�����ĵͼ�ִ�С�
	ע����д������
		����ͨ������޸�mybatis����ʵ�֣���Ҳ��ȫ������д�����ࡣ�򵥵ļ̳���������������д�κ�һ������������ͨ����������� SqlSessionFactoryBuilder.build(myConfig) ������������ˣ���������mybatis ��ʵ��������֤��Ӱ�졣���Խ���ʹ�á�
	3.1.8 environments��������
		mybatis���Ա����ö������������԰�������SQLӳ��Ӧ�õ�������ݿ��У�ԭ���кܶࡣ����ܶ��㷢�����в�ͬ�����á����Ժ��������������ߣ�������ж���������ݿ⹲��ͬһ��ͼ������������ͬͬ����sqlӳ�䡣�������ʹ�ó�����

		һ����Ҫ�������Ǽ�ס����뷨������������ö��������ʱ�������ֻѡ����ÿ��SqlSessionFactoryʵ����ѡ��һ����
		�����������ȥ�����������ݿ⣬����Ҫ��������SqlSessionFactoryʵ�������������ݿ⣬����Ҫ����ʵ�����ȵȡ�������ĺ����׼ǡ�
			.һ��SqlSessionFactoryʵ����ÿ�����ݿ�
			Ϊ��ָ��build�Ļ�������򵥵Ľ�����Ϊ��ѡ�������ݵ�SqlSessionFactoryBuilder�������ǽ��ܻ���������ǩ����
			SqlSessionFactory factory = new SqlSessionFactoryBuilder().build(reader, environment);
			SqlSessionFactory factory = new SqlSessionFactoryBuilder().build(reader, environmen

		������������ʡ���ˡ�Ȼ�����Ĭ�ϻ��������ء���������������
			SqlSessionFactory factory = new SqlSessionFactoryBuilder().build(reader);
			SqlSessionFactory factory = new SqlSessionFactoryBuilder().build(reader, properties

		���environmentsԪ�ض�����������û�����
		<environments default="development">
			<environment id="development">
				<transactionManager type="JDBC">
				<property name="..." value="..."/>
					</transactionManager>
				<dataSource type="POOLED">
					<property name="driver" value="${driver}"/>
					<property name="url" value="${url}"/>
					<property name="username" value="${username}"/>
					<property name="password" value="${password}"/>
				</dataSource>
			</environment>
		</environments>

		ע����Щ�ؼ���
			.Ĭ�ϵĻ���ID(e.g. default="development").
			.������ÿ�������Ļ���ID��(e.g. id="development").
			.TransactionManager���� (e.g. type="JDBC")
			.����Դ���� (e.g. type="POOLED")
		���Ĭ�ϵĻ����ͻ���id���Լ�˵���ġ����԰���ϲ���������ǡ�ֻҪȷ�����Ĭ��ƥ�����ǵ��е�һ����

		transactionManager
			��������TransactionManager����(i.e. type="[JDBC|MANAGED]")�����ǰ�����mybatis��
			.JDBC---������ü򵥵�ֱ������JDBC�ύ�ͻ��˹��ߡ���������������Ӽ������ݿ�ȥ����ʵ��ķ�Χ��
			.MANAGED---������ü���ʲô�����������Ӳ��ύ�����һ�����ӡ��෴�ģ���������������������������������(e.g.a JEE Application Server context). ��Ĭ���ǹر����ӵģ�Ȼ����һЩ������������������ˣ��������Ҫ�ӹر�����ȥֹͣ��������"closeConnection"����Ϊfalse�����磺
				<transactionManager type="MANAGED">
					<property name="closeConnection" value="false"/>
				</transactionManager>
			ע�������ϣ��ʹ��mybatis��springһ�𣬲���Ҫ�����κ�TransactionManager����Ϊ���Springģ����Լ���������һ������������ǰ���õ����á�
			��ЩTransactionManager���Ͷ�����Ҫ�κ����ԣ�������Σ����Ƕ���Type Aliases�����ͱ����������Դ���һ������˵��������ʹ�����ǣ�������������Լ�����ȫ����������Type Alias���������Լ��Ķ�TransActionFactory�ӿڵ�ʵ�֡�
			public interface TransactionFactory {
				void setProperties(Properties props);
				Transaction newTransaction(Connection conn);
				Transaction newTransaction(DataSource dataSource, TransactionIsolationLevel level, boolean autoCommit);
			}
			��XML�е��κ��������ö�����ʵ�����󱻴��ݸ� setProperties() ���������ʵ�ֽ�����Ҫ����һ��Transaction�ӿڣ�����Ƿǳ��򵥽ӿڡ�
				public interface Transaction {
					Connection getConnection() throws SQLException;
					void commit() throws SQLException;
					void rollback() throws SQLException;
					void close() throws SQLException;
					Integer getTimeout() throws SQLException;
				}
			ʹ���������ӿڣ��������ȫ���Լ�����Mybatis��δ���Transaction

			dataSource
				���dateSourceԪ���ñ�׼��JDBC����Դ�ӿ��������JDBC���Ӷ���Դ 
				.�󲿷�mybatisӦ�ý�������һ������Դ������ʵ����������Ȼ�������ǲ���Ҫ�ġ�����Ҫע�⣬Ϊ�˴ٽ������أ����dataSource����Ҫ�ġ�
			������������dataSource���� (i.e. type="[UNPOOLED|POOLED|JNDI]"):
			UNPOOLED--��DataSource ��ʵ��ֻ����ÿ������ʱ�򿪺͹ر����ӡ���Ȼ�е���������һ���õ�ѡ����ڼ���Ӧ����˵������Ҫ�����������ӵ����ܡ���ͬ�����ݿ�Ҳ�ǲ�ͬ����������������У���ˣ�����һЩ����˵���غ�������ÿ��ܲ�̫��Ҫ��
				UNPOOLED DaaSourceֻ�������������
				? driver �C JDBC���������ȫ�޶��� (NOT of the DataSource class if your driver includes one).
				? url �C �����ݿ�ʵ����JDBC��URL
				? username �C The database username to log in with.
				? password - The database password to log in with.
				? defaultTransactionIsolationLevel �C ���ӵ�Ĭ������ĸ��뼶��.
			����ģ���Ҳ���Խ����Դ��ݸ����ݿ�������������Ϊǰ׺�����磺
				? driver.encoding=UTF8
			������ᴫ�ݵ���encoding������ֵUTF9��ͨ��DriverManager.getConnection(url, driverProperties)������������ݿ�������
				POOLED--���DataSource�ص�ʵ����JDBC���Ӷ�����Ϊ�˱�����������Ӻ���֤�����ʱ�䣬Ҫ��ȥ����һ���µ�����ʵ�������ڵ�ǰ��webӦ����˵��ȥ���ٻ�ȡ��Ӧ������һ�����еķ�����
				���������(UNPOOLED) ���ԣ�����������Կ���ʹ���������POOLED datasource:
				? poolMaximumActiveConnections �C This is the number of active (i.e. in use) connections that can exist at any given time. Default: 10
				? poolMaximumIdleConnections �C The number of idle connections that can exist at any given time.
				? poolMaximumCheckoutTime �C This is the amount of time that a Connection can be "checked out" of the pool before it will be forcefully returned. Default: 20000ms (i.e. 20 seconds)
				? poolTimeToWait �C This is a low level setting that gives the pool a chance to print a log status
				and re-attempt the acquisition of a connection in the case that it��s taking unusually long (to avoid failing silently forever if the pool is misconfigured). Default: 20000ms (i.e. 20 seconds)
				? poolMaximumLocalBadConnectionTolerance �C This is a low level setting about tolerance of bad connections got for any thread. If a thread got a bad connection, it may still have another chance to re-attempt to get another connection which is valid. But the retrying times should not more than the sum of  poolMaximumIdleConnections and poolMaximumLocalBadConnectionTolerance . Default: 3 (Since: 3.4.5)
				? poolPingQuery �C The Ping Query is sent to the database to validate that a connection is in
				good working order and is ready to accept requests. The default is "NO PING QUERY SET", which will cause most database drivers to fail with a decent error message.
				? poolPingEnabled �C This enables or disables the ping query. If enabled, you must also set the poolPingQuery property with a valid SQL statement (preferably a very fast one). Default: false.
				? poolPingConnectionsNotUsedFor �C This configures how often the poolPingQuery
				will be used. This can be set to match the typical timeout for a database connection, to avoid unnecessary pings. Default: 0 (i.e. all connections are pinged every time �C but only if poolPingEnabled is true of course).
			JNDI--����DataSource��һ��ʵ�֣�ʹ��������������EJB����Application Servers������Լ��л��ⲿ��������Դ������JNDI�������������������DataSource����ֻ��Ҫ��������
				? initial_context �C This property is used for the Context lookup from the InitialContext
				(i.e. initialContext.lookup(initial_context)). This property is optional, and if omitted, then the data_source property will be looked up against the InitialContext directly.
				? data_source �C This is the context path where the reference to the instance of the DataSource can be found. It will be looked up against the context returned by the initial_context lookup, or against the InitialContext directly if no initial_context is supplied.
			������DtaSource�������ƣ���Ҳ����ֱ��ͨ������ǰ׺�������ԣ�����
				? env.encoding=UTF8
			�����ͨ��ʵ�ֽӿ�Ҫ�����κε���������Դ
				org.apache.ibatis.datasource.DataSourceFactory :
				public interface DataSourceFactory {
					void setProperties(Properties props);
					DataSource getDataSource();
				}
			org.apache.ibatis.datasource.unpooled.UnpooledDataSourceFactory ���Ա���������ȥ�����µ�datasource
			�������������ʹ��C3P0�Ĵ��롣
				import org.apache.ibatis.datasource.unpooled.UnpooledDataSourceFactory;
				import com.mchange.v2.c3p0.ComboPooledDataSource;
				public class C3P0DataSourceFactory extends UnpooledDataSourceFactory {
					public C3P0DataSourceFactory() {
						this.dataSource = new ComboPooledDataSource();
					}
				}
			ȥ���ã�Ϊÿһ������mybatis���õ�ÿһ��setter�������һ�����ԡ�
				<dataSource type="org.myproject.C3P0DataSourceFactory">
					<property name="driver" value="org.postgresql.Driver"/>
					<property name="url" value="jdbc:postgresql:mydb"/>
					<property name="username" value="postgres"/>
					<property name="password" value="root"/>
				</dataSource>
			
		3.1.9 databaseProvlder
			Mybatis�ܹ����������ݿ�Ĺ�Ӧ�����ɲ�ͬ����䡣���Ķ�DB��Ӧ��֧���ǻ���ӳ������databaseId���ԡ�MyBatis����������û��databaseId���Ե���䣬����ʹ���뵱ǰ���ݿ�ƥ���databaseId���Է�ͬһ����䱻�ҵ�û��databaseId�������һ�����ᱻ������Ϊ���ܹ�ʵ�ֶ๩Ӧ��֧�֣����һ��databaseIdProvider��mybatis-config��xml�ļ������磺
				<databaseIdProvider type="DB_VENDOR" />
			��� DB_VENDORʵ��databaseIdProvider����ΪdatabaseId������ַ�����DatabaseMetaData#getDatabaseProductName() �������ء�ͨ���ַ���̫���Ͳ�ͬ�İ汾������ͬ���Ĳ�Ʒ���ܷ��ز�ͬ��ֵ��������뽫�����ǳ�һ������ͨ��������ԡ����磺
			<databaseIdProvider type="DB_VENDOR">
				<property name="SQL Server" value="sqlserver"/>
				<property name="DB2" value="db2"/>
				<property name="Oracle" value="oracle" />
			</databaseIdProvider>��Ӧ��
			���ṩ����ʱ����� DB_VENDOR databaseIdProvider�����ѯ�������ֵ�����Ѳ鵽�ĵ�һ��ֵ��Ϊkey����Щֵ������database product�ķ���name�����û��ƥ������ԣ���Ϊnull������������£����getDatabaseProductName() �������ء�Oracle (DataDirect)�������databaseId�ͻᱻ���ó�"oracle".
			�����ͨ��ʵ������ӿ�org.apache.ibatis.mapping.DatabaseIdProvider�������Լ���DatabaseIdProvider������mybatis-config.xml��ע������
			public interface DatabaseIdProvider {
				void setProperties(Properties p);
				String getDatabaseId(DataSource dataSource) throws SQLException;
			}
		
		3.1.10 mappers
			����mybatis����Ϊ������� ����Ԫ�����ã�����׼�������Լ���ӳ��sql��䡣�������ȣ�������Ҫȥ����mybatisȥ��������Щ��䡣���ⷽ��java����׼���ṩ�κκõķ���ȥ�Զ����ҡ������Ժ��ķ�����ȥ�򵥵ĸ���mybatisȥ���������ӳ���ļ��������ʹ����·�������Դ���á���ȫ�޶�url����(including  file:/// URLs)���������߰�����
			<!-- Using classpath relative resources -->
			<mappers>
				<mapper resource="org/mybatis/builder/AuthorMapper.xml"/>
				<mapper resource="org/mybatis/builder/BlogMapper.xml"/>
				<mapper resource="org/mybatis/builder/PostMapper.xml"/>
			</mappers>
			<!-- Using url fully qualified paths -->
			<mappers>
				<mapper url="file:///var/mappers/AuthorMapper.xml"/>
				<mapper url="file:///var/mappers/BlogMapper.xml"/>
				<mapper url="file:///var/mappers/PostMapper.xml"/>
			</mappers>
			<!-- Using mapper interface classes -->
			<mappers>
				<mapper class="org.mybatis.builder.AuthorMapper"/>
				<mapper class="org.mybatis.builder.BlogMapper"/>
				<mapper class="org.mybatis.builder.PostMapper"/>
			</mappers>

			<!-- Register all interfaces in a package as mappers -->
			<mappers>
				<package name="org.mybatis.builder"/>
			</mappers>

			������򵥵ĸ�����mybatisȥ��������Щ����ӳ���ļ�������ϸ����ÿһ��ӳ���ļ��У������������һ�ڻ���彲�����ݡ�

4.Mapper XML Files
	4.1 Mapper XML Files
		Mybatis����������������������ϡ������ħ�������ĵط����������е�������˵��ӳ��XML�ļ�����Լ򵥵ġ���Ȼ����������������Ч��JDBC������бȽϣ�������������95%�Ĵ����ʡ��MyBatis��Ϊ�˹�עSQL�������ģ����ᾡ���ܿ����ķ�ʽ��
		Mapper XML�ļ�ֻ�к��ٵ�һ��Ԫ�ء�(��������Ӧ�ñ������˳��)
			? cache �C Configuration of the cache for a given namespace.
			? cache-ref �C Reference to a cache configuration from another namespace.
			? resultMap �C The most complicated and powerful element that describes how to load your objects from the database result sets.
			? parameterMap �C Deprecated! Old-school way to map parameters. Inline parameters are preferred and this element may be removed in the future. Not documented here.
			? sql �C A reusable chunk of SQL that can be referenced by other statements.
			? insert �C A mapped INSERT statement.
			? update �C A mapped UPDATE statement.
			? delete �C A mapped DELETE statement.
			? select �C A mapped SELECT statement.
		��һ�ڽ�����ϸ������ÿһ��Ԫ�أ�����䱾��ʼ��
		
		4.1.1 select
			���select��佫�������õ�����Ԫ�ء������ݷ������ݿ��в����Ǻ��м�ֵ��ֱ�������ȡ�������󲿷�Ӧ�ò�ѯ����Զ���޸����ݴ����࣬��ÿһ��������»���ɾ�������������źܶ��ѯ����������mybatis������ԭ��֮һ��Ҳ��Ϊʲô��ô��ע��Ŭ�������ڲ�ѯ�ͽ��ӳ���ԭ�򡣶��ڼ򵥵��龰��selelctԪ��Ҳ���൱�ļ򵥣����磺
				<select id="selectPerson" parameterType="int" resultType="hashmap">
					SELECT * FROM PERSON WHERE ID = #{id}
				</select>

			�������ǵ���selectPerson��ʹ�õĲ���������int������Integer����Ȼ��ͨ����������ֵ֮���ӳ�䷵��һ��HashMap��ֵ�ԡ�
			ע������ķ���
				#{id}
			���Ǹ���Mybatis����һ��PrepareStatement��������JDBC�У���ôһ�������ᱻ������������������������sql���й���һ���µ�PreparedStatement������������
				// Similar JDBC code, NOT MyBatis��
				String selectPerson = "SELECT * FROM PERSON WHERE ID=?";
				PreparedStatement ps = conn.prepareStatement(selectPerson);
				ps.setInt(1,id);
			��Ȼ�������������Ǳ�JDBC����Ҫ���ȥ��ȡ��������ӳ���һ�������ʵ���������mybatis���������Ѿ���������ķ��������и����йز����ͽ��ӳ�����Ϣ����Щϸ�ھ��ں����½ڡ�
			��һ��Ԫ���кܶ�״̬������ȥ����ÿһ�������Ϊ��ϸ�ڡ�
				<select
					id="selectPerson"
					parameterType="int"
					parameterMap="deprecated"
					resultType="hashmap"
					resultMap="personResultMap"
					flushCache="false"
					useCache="true"
					timeout="10000"
					fetchSize="256"
					statementType="PREPARED"
					resultSetType="FORWARD_ONLY">
			
			id
			�����������ռ��Ψһ��ʶ������������䡣

			parameterType��
			���������ᱻ����������ȫ�޶����ͻ��ߵĲ�����������������ǿ�ѡ�ģ���ΪMyBatis���Լ���TypeHandler��ʹ�ô��ݸ�����ʵ�ʲ�����Ĭ���ǲ����á�

			parameterMap
			����������һ�����õķ���ȥ����һ���ⲿ��paramperMap���������Ĳ�ӳ���paramenterType���ԡ�

			resultType
			���������ᱻ����䷵�ص����͡�����ȫ�޶�������Ԥ�����͵ı�����

			returnMap
			������һ��������Ԥ�ڵ�resultMap�����á����ӳ����mybatis��ǿ��Ĺ��ܡ��õ��������������ѵ�ӳ�䳡�������Եı��������resultMap����resuleType����������һ���á�

			flushCache
			���������ó�true���ͻ�ʹ�ñ��ػ��߶�������ȥˢ�����ۺ�ʱ�����õ���䡣Ĭ����false��

			userCache
			���������ó�true���ᵼ�����Ľ���������ڶ��������У�Ĭ����true��

			timeout
			����������һ��ʱ����������ʾ�����ȴ����ݿⷵ�ؽ����ʱ�䡣���׳��쳣ǰ��Ĭ�ϲ����ã�

		4.1.2
			���ݵ��޸���䣬insert��update��delete�Ƿǳ����Ƶ������ǵ�ʵ���ϡ�
			<insert
				id="insertAuthor"
				parameterType="domain.blog.Author"
				flushCache="true"
				statementType="PREPARED"
				keyProperty=""
				keyColumn=""
				useGeneratedKeys=""
				timeout="20">

			<update
				id="updateAuthor"
				parameterType="domain.blog.Author"
				flushCache="true"
				statementType="PREPARED"
				timeout="20">

			<delete
				id="deleteAuthor"
				parameterType="domain.blog.Author"
				flushCache="true"
				statementType="PREPARED"
				timeout="20">

			id:
			�����������ռ��Ψһ��ʶ������������䡣

			parameterType��
			���������ᱻ����������ȫ�޶����ͻ��ߵĲ�����������������ǿ�ѡ�ģ���ΪMyBatis���Լ���TypeHandler��ʹ�ô��ݸ�����ʵ�ʲ�����Ĭ���ǲ����á�
			
			parameterMap
			����������һ�����õķ���ȥ����һ���ⲿ��paramperMap���������Ĳ�ӳ���paramenterType���ԡ�
			
			flushCache
			���������ó�true���ͻ�ʹ�ñ��ػ��߶�������ȥˢ�����ۺ�ʱ�����õ���䡣Ĭ����false��
			
			timeout
			����������һ��ʱ����������ʾ�����ȴ����ݿⷵ�ؽ����ʱ�䡣���׳��쳣ǰ��Ĭ�ϲ����ã�

			statementType ��
			�������κ�һ��STATEMENT,PREPARED����CALLLABLE��ᵼ��mybatisȥ�ֱ�ʹ��Statement PreparedStatment����CallableStatement��Ĭ����PREPARED��
			
			useGeneratedKeys
			����������inset��update��ʹ�ã�������mybatisȥʹ��jdbc getFeneratedKeys�������ڲ�ȥ�����Ѿ����ɵļ�ֵͨ�����ݿ�(e.g.auto increment fields in RDBMS like MySQL or SQL Server).Ĭ����false

			
			keyProperty
			����������inset��update��ʹ�ã�����ʶһ��������mybatis����ͨ��getGeneratedKys�����������������ֵ������ͨ��selectKey���insert������Ԫ�ء�Ĭ���ǣ������ã��������������ɵ��У��������һ�����ŷָ������������б�

			databaseId:
			�����������һ�����õ�databaseIdProvider, MyBatis����������û��databaseId���Ե���䣬����ʹ���뵱ǰ���ݿ�ƥ���databaseId�������û��databaseId������·�����ͬ����䣬����߽���������

			������һЩinsert��update����delete�����

				<insert id="insertAuthor">
					insert into Author (id,username,password,email,bio)
					values (#{id},#{username},#{password},#{email},#{bio})
				</insert>

				<update id="updateAuthor">
					update Author set
					username = #{username},
					password = #{password},
					email = #{email},
					bio = #{bio}
					where id = #{id}
				</update>

				<delete id="deleteAuthor">
					delete from Author where id = #{id}
				</delete>

			����ǰ���ᵽ�ģ�����Ҫ��΢�ḻһ�㡣����һЩ��������Ժ���Ԫ�أ���������һЩ����ȥ�����ֵ���ɡ�
			���ȣ����������ݿ�֧���Զ����������ֶΣ�����mysql��sql server����Ȼ������Լ򵥵����� useGeneratedKeys="true"������keyPropertyΪĿ�����ԣ��������ˡ����磬��������Author���Ѿ�����id���Զ�����������Ϊ���id��������ᰴ�����޸ġ�
				<insert id="insertAuthor" useGeneratedKeys="true"
					keyProperty="id">
					insert into Author (username,password,email,bio)
					values (#{username},#{password},#{email},#{bio})
				</insert>

			����Դ���һ��Author�������list�������Զ����ɵ�key��

				<insert id="insertAuthor" useGeneratedKeys="true"
				keyProperty="id">
					insert into Author (username, password, email, bio) values
					<foreach item="item" collection="list" separator=",">
						(#{item.username}, #{item.password}, #{item.email}, #{item.bio})
					</foreach>
				</insert>

			Mybatis������һ������ȥ������ݿ��key���ɡ���֧���Զ����������ͣ�����Ҳ��֧��JDBC����֧��auto-generated key��
			����һ���򵥵����ӣ�����һ�����ID
				<insert id="insertAuthor">
					<selectKey keyProperty="id" resultType="int" order="BEFORE">
						select CAST(RANDOM()*1000000 as INTEGER) a from SYSIBM.SYSDUMMY1
					</selectKey>
					insert into Author
						(id, username, password, email,bio, favourite_section)
					values
						(#{id}, #{username}, #{password}, #{email}, #{bio}, #{favouriteSection,jdbcType=VARCHAR})
				</insert>

			������������У����selectKey��佫������ִ�У����Author id���Իᱻ����ֵ��Ȼ������������Ҳ�ᱻ���á���ʹ�����������ݿ����Զ�������Կ��������ʹJava���븴�ӻ���
				
				selectKeyԪ����������:
					<selectKey
						keyProperty="id"
						resultType="int"
						order="BEFORE"
						statementType="PREPARED">
				
				keyProperty
					���������Ŀ����������Ҫ�����õ�selectKy���Ľ��������Ƕ�������п�����Ϊһ�����ŷָ��Ĳ�����list��
				keyColumn:
					���������ؽ������������ƥ������ԣ�����Ƕ�������п�����Ϊһ�����ŷָ��Ĳ�����list��
				resultType:
					��������������ͣ�mbatis������������������Ǽ���Ҳûʲô������mybatis��ݡ�κμ򵥵����͵���key������Strings�������ϣ����������У�Ȼ������Կ���һ��������������е����������Ի���map��
				order:
					�������������ó�BEFORE����AFTER��
					BEFORE�����Ȳ�ѯkey������kryProperty��Ȼ������insert��䡣
					AFTER��������insert��䣬Ȼ����selectKey��䡪������Oracle���������ݿ�ܳ�������Щ���ݿ������insert�����Ƕ�������е��á�
				statementType:
					����:������һ����MYBATIS֧��STATEMENT.PREPARED��CALLABLE ��䣬��Ӧ����Statement PreparedStatment����CallableStatement��
				
		4.1.3 sql
			���Ԫ�ؿ������ڶ����ظ�ʹ�õ�sqlƬ�δ��룬������������������䡣�����Ա���̬�Ĳ��������ڼ��ؽ׶Σ�����ͬ�Ĳ�����includeʵ����Ҳ�е㲻ͬ�����磺
				<sql id="userColumns"> ${alias}.id,${alias}.username,${alias}.password </sql>
			���sqlƬ�ο�����include�б����������ʹ�á�����
				
				<select id="selectUsers" resultType="map">
					select
						<include refid="userColumns"><property name="alias" value="t1"/></include>,
						<include refid="userColumns"><property name="alias" value="t2"/></include>
					from some_table t1
						cross join some_table t2
				</select>
			
			����ֵҲ����ʹ�ã�����refid���Ի������include�Ӿ��е�����ֵ�����磺
				<sql id="sometable">
					${prefix}Table
				</sql>
				<sql id="someinclude">
					from
						<include refid="${include_target}"/>
				</sql>
				<select id="select" resultType="map">
					select
						field1, field2, field3
					<include refid="someinclude">
						<property name="prefix" value="Some"/>
						<property name="include_target" value="sometable"/>
					</include>
				</select>

			4.1.4 Paramters
				�����е���䲿�֣����Ѿ������˼򵥵����ò������ӡ��������Ƿǳ�ǿ������ԡ�
					<select id="selectUsers" resultType="User">
						select id, username, password
							from users
						where id = #{id}
					</select>

				���������˵����һ����򵥵���������ӳ�䡣���parameterType��int���������������У����ø��Ӳ�����ô����
					<insert id="insertUser" parameterType="User">
						insert into users (id, username, password)
						values (#{id}, #{username}, #{password})
					</insert>

				��Щ���Իᴫ�ݵ�PrepareStatement�����ϡ�
				���ڲ����������������ܣ�����ָ��һ��������͡�����
					#{property,javaType=int,jdbcType=NUMERIC}
				
				�������Ѿ��еㆪ���ˣ�����ʵ������ٻ�������Щ��������ֵ���ͣ�Ҳ��һ�����ֳ߶���ȷ���ж���λС������صġ�
					#{height,javaType=double,jdbcType=NUMERIC,numericScale=2}

				mode��



			





	







