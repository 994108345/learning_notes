mybatis开发手册阅读学习
1：介绍
1.1为什么使用mybatis框架
	mybatis是第一个类持久化框架支持自定义sql语句、存储结构和提前隐射。消除了大部分jdbc的手动设置和检测结果。mybatis可以使用简单的xml文件或注解配置隐射原码，隐射接口和让POJO（普通的java对象）隐射到数据库记录上。
2：使用mybatis

	2.1：首先导入需要的包
	使用maven管理项目，导入下面的包，version为mybatis包的版本号。
	<dependency>
		<groupId>org.mybatis</groupId>
		<artifactId>mybatis</artifactId>
		<version>${version}</version>
	</dependency>

	2.2构建SqlSessionFactory从xml文件中。
	mybatis的应用主要都是围绕在SqlSessionFactory这个实例使用的。而SqlSessionFactory需要通过SqlSessionFactoryBuilder这个对象去获取。
	获得SqlSessionFactoryBuilder这个对象有两个途径1：通过配置文件配置 2：自己手动定义创建实例获取。建议使用第一种，目前大部分都是使用第一种。
	
	2.2.1首先介绍一下第一种：通过配置文件获取SqlSessionFactoryBuilder；
	//
	String resource = "org/mybatis/example/mybatis-config.xml";//配置文件路劲
	InputStream inputStream = Resources.getResourceAsStream(resource);
	SqlSessionFactory sqlSessionFactory =
	new SqlSessionFactoryBuilder().build(inputStream);

	//配置文件内容
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

	2.2.2第二种：手动获取SqlSessionFactoryBuilder实例
	DataSource dataSource = BlogDataSourceFactory.getBlogDataSource();
	TransactionFactory transactionFactory =
	new JdbcTransactionFactory();
	Environment environment =
	new Environment("development", transactionFactory, dataSource);
	Configuration configuration = new Configuration(environment);
	configuration.addMapper(BlogMapper.class);
	SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(configuration);

	2.3从SqlSessionFactoryBuilder获得SqlSessionFactory
	SqlSession session = sqlSessionFactory.openSession();
	try {
		Blog blog = session.selectOne(
		"org.mybatis.example.BlogMapper.selectBlog", 101);
	} finally {
		session.close();
	}

	2.4数据库映射文件写法
	<?xml version="1.0" encoding="UTF-8" ?>
	<!DOCTYPE mapper
		PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN"
		"http://mybatis.org/dtd/mybatis-3-mapper.dtd">
	<mapper namespace="org.mybatis.example.BlogMapper">
		<select id="selectBlog" resultType="Blog">
			select * from Blog where id = #{id}
		</select>
	</mapper>

	2.5.在接口中调用
	Blog blog = session.selectOne("org.mybatis.example.BlogMapper.selectBlog", 101);

	2.6 命名空间namespaced的注意事项
	1：如果resultType没有写实体类的全路径而只是写类名。刚好你的项目中有两个相同的类，此时系统会报错！！！

	2.7还可以直接在方法上通过注释配置sql自定义语句而不用通过mapper.xml文件
		@Select("SELECT * FROM blog WHERE id = #{id}")
		Blog selectBlog(int id);
	
	2.8范围和生命周期
	不正确的使用会引发严重的并发问题。
	一般我们都是使用是spring框架来帮我们管理创建sqlsession。通过spring创建的都是安全线程，所以不用担心并发问题。
	
	2.8.1 SqlSessionFactoryBuilder
	使用SqlSessionFactoryBuilder创建SqlSessionFactory，所以全程只需要创建一个SqlSessionFactoryBuilder，然后重复创建SqlSessionFactory。这样可以确保在解析完xml问及那后就可以释放资源，去做其他更难重要的事情。

	2.8.2 SqlSessionFactory
	每个线程都有一个自己的2SqlSessionFactory实例，该实例不会和别人共享。所以最好的范围是在请求和方法范围。在静态块中不要保持依赖SqlSessionFactory，甚至是类的实例字段。永远不要在任何托管范围内对SqlSession进行引用。例如HttpSession的servlet框架，如果你用任何一个web框架，请考虑在SqlSession中遵循与HTTP请求类似的范围。换句话说。在接收http请求，你可以打开一个sqlsession，然后在返回请求的时候，你可以关闭它，关闭session是非常重要的，你需要总是确认他关闭了再finally块中，这下面是确认关闭sqlsession的标准模式。
		SqlSession session = sqlSessionFactory.openSession();
		try {
		// do work
		} finally {
		session.close();
		}

	2.1.6.4 mapper instance
	映射器是你创建绑定映射语句的接口。映射器的实例是从sqlsession中取得的，像这样，从该技术上来说，映射器实例最广的范围就和他们请求的Sqlsession一样。然而，映射器实例最好的范围是方法范围，在使用他们的方法中请求他们。然后被丢弃掉。他们不需要被显示地关闭。然而，尽管在整个请求过程中保持他们不是问题。类似于sqlsession，你可能发现在这个级别上管理太多的资源会失去控制，保证他的简单，保持映射对象在这个方法范围中，下面的例子演示了这个做法。
	SqlSession session = sqlSessionFactory.openSession();
	try {
	BlogMapper mapper = session.getMapper(BlogMapper.class);
		// do work
	} finally {
		session.close();
	}

3.配置XML

	3.1 配置
	mybatis配置包含了对mybatis行为有显著影响的配置和参数！
	3.1.1 properties
	这些是可外部化的，可替换的属性，可以在典型的Java属性文件实例中配置。或通过属性元素的子元素传递。例如
		<properties resource="org/mybatis/example/config.properties">
			<property name="username" value="dev_user"/>
			<property name="password" value="F2Fa3!33TYyg"/>
		</properties>
	然后，可以在整个配置文件中使用属性来替换需要动态配置的值。例如
		<dataSource type="POOLED">
			<property name="driver" value="${driver}"/>
			<property name="url" value="${url}"/>
			<property name="username" value="${username}"/>
			<property name="password" value="${password}"/>
		</dataSource>
	在例子中在睡醒元素被设置值时，账号和密码将会被替换。driver和url属性将会被来自config。properties文件的值替换
	属性也可以通过SqlSessionFactory.Builder.build()方法注入，例如
	SqlSessionFactory factory = sqlSessionFactoryBuilder.build(reader, props);
	// ... or ...
	SqlSessionFactory factory = new SqlSessionFactoryBuilder.build(reader, environment, props);
	如果参数存在于以上这些地方，Mybatis会按照以下顺序装载他们！
	.首先读取属性元素主体被指定的属性。
	.其次从属性元素的原路径或者url状态加载属性，并重写已经指定的任何重复属性。
	.最后，作为方法参数传递的属性被读取，并覆盖从属性体和资源/url属性中加载的任何重复属性。
	因此，最高优先级属性是作为方法参数传入的属性，然后是资源/url属性，最后是属性元素主体中指定的属性。
	从mybatis3.4.2版本开始，你可以指定一个默认值用下面的占位符。
	<dataSource type="POOLED">
		<!-- ... -->
		<property name="username" value="${username:ut_user}"/> <!-- If 'username' property not present, username become 'ut_user' -->
	</dataSource>
	这个功能默认是没用的，如果你指定一个默认值用占位符表示，你需要添加添加指定属性来启用该功能。
	<properties resource="org/mybatis/example/config.properties">
		<!-- ... -->
		<property name="org.apache.ibatis.parsing.PropertyParser.enable-default-value" value="true"/> <!-- Enable this feature -->
	</properties>
	如果你已经使用":"作为属性键(e.g.  db:username ) 或者你已经使用OGNL（对象导航图语言）中的三元表达式，在你的sql定义中，你需要添加指定属性来修改单独的键和默认值的字符。
	<properties resource="org/mybatis/example/config.properties">
		<!-- ... -->
		<property name="org.apache.ibatis.parsing.PropertyParser.default-value-separator" value="?:"/> <!-- Change default value of separator -->
	</properties>

	<dataSource type="POOLED">
		<!-- ... -->
		<property name="username" value="${db:username?:ut_user}"/>
	</dataSource>

	3.1.2 设置
	这有一个分常重要的调整，是对运行时的myvatis进行修改。下面的表格描述了设置、意义、和默认值。
	cacheEnabled
	描述：全局启用或禁用在此配置下配置在任何mapper中的任何缓存。
	默认值：true。

	lazyLoadingEnabled
	描述：全局启动或禁用懒加载。当启用时，所有的关系都会被懒加载。这个值可以通过使用它的fetchType属性来替代特定的关系。
	默认值：false

	aggressiveLazyLoading
	描述：当启用时，任何方法调用都会加载对象中所有的懒加载属性。另一方面，每个属性会在需要时被加载（参见lazyLoadTriggerMeth）。
	默认值：true（trun in <= 3.4.1）

	multipleResultSetsEnabled
	描述：允许或不允许从单个语句中返回多结果集（兼容驱动要求）
	默认值：true

	useColumnLabel
	描述：使用列标签代替使用列名。不同的驱动在这方面有不同的表现。请参阅驱动文件文档，或者两个模式都测试，去确定你的驱动程序的行为。
	默认值：true。

	useGeneratedKeys
	描述：允许JDBC对生成的键的支持。需要一个兼容的驱动程序。如果设置为true，则该设置强制生成密钥，因为有些驱动程序拒绝兼容，但仍然有效。
	默认值：false

	autoMappingBehavior
	描述：指定mybatis是否自动映射列到字段和属性。
		NONE：不自动映射。
		PARTIAL：部分只自动映射结果，而不会在内部定义嵌套的结果映射。
		Full：会自动隐射任何复杂性的结果映射。
	值：NONE,PARTIAL,FULL
	默认值：PARTIAL

	autoMappingUnknownColum 
	描述：当检测一个自动映射目标的未知列（或者未知属性类型）时，指定一个行为。
		NONE:不做任何事情
		WARNING：发出一个警告（日志），日志级别的“org.apache.ibatis.session.AutoMappingUnknownColumnBehavior”必须		设置成WARN
		FAILING:失败的映射（抛出SqlSessionExcept的异常。
	值：NONE, WARNING,FAILING
	默认值：NONE

	defaultExecutorType
	描述：配置默认的执行者。
		SIMPLE：执行者不做任何指定的事情。
		REUSE：执行者重用准备的语句
		BATCH：执行者重用语句和批量更新
	默认值：SIMPLE

	defaultStatementTimeout
	描述：设置驱动会等待数据库返回的时间
	默认：不设置任何值

	defaultFetchSize
	描述：设置这个驱动的一个提示，当做控制抓取返回结果的大小。这个参数可以被查询设置重写。
	默认：不设置任何值。

	safeRowBoundsEnabled：
	描述：允许使用RowBounds（行绑定）在嵌套语句中，如果允许，设置成false；
	默认值：false

	safeResultHandlerEnabled
	描述：允许使用ResultHandler（结果处理程序）在嵌套语句中，如果允许，设置成false
	默认值：false

	mapUnderscoreToCamelCase
	描述：允许从经典数据库列名称A_COLUMN自动映射到camel case经典Java属性名称aColumn。
	默认值：false

	localCacheScope
	描述：mybatis使用本地缓存取保证循环依赖和加快嵌套查询。
		SESSION：在所有的session是缓存时，默认的所有查询执行者是session。
		STATEMENT:本地的session（会话）仅用于语句执行。没有数据将在两个不同的调用共享同一个SqlSession的。
	默认值：SESSION

	jdbcTypeForNull
	描述：当没有为参数提供特定的JDBC类型时，指定null值的JDBC类型，一些驱动要求指定列JDBC类型但是其他的使用通用值，像NULL,VARCHAR 或者 OTHER.
	值：Most common are: NULL,VARCHAR and OTHER
	默认值：OTHRE

	lazyLoadTriggerMethods
	描述：指定任何一个对象的方法触发懒加载。
	值：用逗号分隔的方法名列表。
	默认值：equals,clone,hashCode,toString

	defaultScriptingLanguage
	描述：指定用于动态SQL生成的默认语言。
	值：A type alias or fully qualified class name
	默认值：org.apache.ibatis.scripting.xmltags.XMLLanguageDriver
	
	defaultEnumTypeHandler
	描述：指定枚举默认使用的TypeHandler（自3.4.5版本）
	值：类型别名或完全限定的类名。
	默认值：org.apache.ibatis.type.EnumTypeHandler

	callSettersOnNulls
	描述：指定当一个检索值为null时，是否调用seter或map的put方法。他是很有用的，当你准备调用Map.keySet()或空值初始		化时，注意原语，如(int,boolean等)将不会被设置为null。
	默认值：false

	returnInstanceForEmptyRowMyBatis
	描述：当返回行中所有的列为NULL时，mybatis默认返回空。当这个设置是启用时，mybatis返回一个实体实例去替代。注意		对于嵌套结果也是这样的。
	值：任何字符串
	默认值：不设置

	logPrefix
	描述：执行mybatis添加到日志中名字的前缀字符串。
	值：任何字符串
	默认值：不设置

	logImpl
	描述：指定mybatis需要用的日志实现。如果这个设置不存在，日志实现会自己自动找到对应的实现。
	值：SLF4J | LOG4J | LOG4J2| JDK_LOGGING |COMMONS_LOGGING| STDOUT_LOGGING |NO_LOGGING
	默认值：不设置

	proxyFactory
	描述：指定mybatis用于创造有懒加载能力对象的代理工具。
	值：CGLIB | JAVASSIST
	默认值：JAVASSIST (MyBatis 3.3 或以上)

	vfsImpl
	描述：指定VFS的实现 
	值：由逗号分隔的自定义VFS实现的完全限定类名
	默认值：不设置

	useActualParamName
	描述：允许在方法签名中声明的实际名称引用语句参数
	默认值：true

	configurationFactory	
	描述：指定提供配置文件的一个类。这个被返回的配置文件实例会用于懒加载反序列化对象的属性。这个类必须有一个方法是带有（ static Configuration getConfiguration()）签名
	值：完全限制类名的别名类型
	默认值：不设置

	下面是完全配置元素的一个例子。
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

	3.1.3 typeAliases（类型命名）
	类型别名只是Java类型的较短名称。它只与XML配置有关，并且仅仅是为了减少完全限定类名的冗余类型。例如：
	<typeAliases>
		<typeAlias alias="Author" type="domain.blog.Author"/>
		<typeAlias alias="Blog" type="domain.blog.Blog"/>
		<typeAlias alias="Comment" type="domain.blog.Comment"/>
		<typeAlias alias="Post" type="domain.blog.Post"/>
		<typeAlias alias="Section" type="domain.blog.Section"/>
		<typeAlias alias="Tag" type="domain.blog.Tag"/>
	</typeAliases>
	拥有这个配置，Blog现在可以用在任何domain.blog.Blog可以用的地方。你也可以指定mybatis查询bean需要的包名。例如
	<typeAliases>
		<package name="domain.blog"/>
	</typeAliases>
	每一个在domain.blog发现的bean，如果没有找到注释，将使用bean的未大写的非限定类名注册为别名。domain.blog.Author将会注册为author。如果发现有@Alias注解，这个注解的值将会当成一个别名。看下面的例子
	@Alias("author")
	public class Author {
		...
	}
	这有许多对普通java类型简历了对应的别名类型。他们都是不在意大小写的，请注意由于名称重载而对原语的特殊处理。名字。
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

	3.1.4 类型处理程序
	无论何时，mybatis在给来自结果集的预处理语句或检索值设置参数时，一个TypeHandler用一个适当的方法去检索值转化成java类型。下面的表秒速了这个默认的TypeHandlers
	注意自3.4.5版本，mybatis已经支持JSR-310默认
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
	
	你可以重写处理器类型或者粗黄建你自己的去解决不支持或非标准类型的问题。这么做的话，实现这个接口 org.apache.ibatis.type.TypeHandler或者extends 这个方便的类  org.apache.ibatis.type.BaseTypeHandler可选的将她映射成JDBC类型。下面为例子
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

	用这么一个TypeHandler 会覆盖已经存在的处理字符串类型属性和varvhar参数结果的类型处理器。注意在数据库的元数据上，Mybatis不会反省验证这个类型。所以你必须指定他是VARCHAR字段在参数和结果映射上以至于和正确的类型处理器绑定。
	这是由于事实上直到这个语句被执行，mybatis都不知道数据的类型的。

	Mybatis将指定这个通过内省他的通用类型去处理TypeHandler的java类型。但是你可以通过两个方法覆盖这个行为。
	.给typeHandler的元素添加javaType属性 (例如:  javaType="String" )
	.在您的TypeHandler类中添加一个@MappedTypes注释，指定要关联它的java类型列表。。这个注解将不会被理财如果这个ejavaType属性已经被指定。

	可以通过两个方法指定关联JDBC类型
	.添加一个jdbcType属性给typeHandler元素（例如 jdbcType="VARCHAR"）。
	.给你的TypeHandler添加@MappedJdbcTypes注解，指定这个关联的JDBC类型列表。如果这个jdbcType属性已经被指定，这个注解将不会被理睬。

	当决定在ResultMap使用哪个TypeHandler时，java类型（来自结果类型）是已知的，但是JDBC type是未知的。因此mybatis使用这个组合（javaType=[TheJavaType], jdbcType=null）去选择一个TypeHandler。这意味着用一个  @MappedJdbcTypes 注解限制这个TypeHandler的范围，除非明确设置，否则用在ResultMaps也是无效的。为ResultMap制造一个TypeHandler属性，设置  includeNullJdbcType=true 在  @MappedJdbcTypes 注解里。自Mybatis3.4.0.无论如何，如果一个单个的TypeHandler被注册去解析java type，当ResuleMaps使用java Type时，他将默认被使用 (i.e. even without  includeNullJdbcType=true )。
	最后你可以使Mybatis查询你的TypeHandlers
	<!-- mybatis-config.xml -->
	<typeHandlers>
		<package name="org.mybatis.example"/>
	</typeHandlers>

	注意，当你使用自动查询功能的时候，JDBC type 只能用注解去指定。你可以创建一个能够处理更多类的通用的TypeHandler为了这个目的添加一个构造器去接收类当做参数，Mybatis会传递这个实际的类当构造TypeHandler的时候。
	//GenericTypeHandler.java
	public class GenericTypeHandler<E extends MyObject> extends BaseTypeHandler<E> {
		private Class<E> type;
		public GenericTypeHandler(Class<E> type) {
		if (type == null) throw new IllegalArgumentException("Type argument cannot be null");
			this.type = type;
		}
		...

	EnumTypeHandler 和  EnumOrdinalTypeHandler是通用的TypeHandler。我们将会在学习有关他们的细节。
	
	3.1.5 Handling Enums
	如果你想去映射Enum，你需要去使用EnumTypeHandler 或者  EnumOrdinalTypeHandler。
	例如，假如我们需要用数字存储圆的一些属性。默认的再Mybatis中就会使用枚举来实现。

	注意，这个功能上EnumTypeHandler是特殊的，与其他处理程序不同的是，它并不只处理一个特定的类，而是扩展枚举的任何类。
	然而，我们可能不想存储名字。我们的DBA可以坚持整型编码。那很简单，在你的配置文件中将EnumOrdinalTypeHandler添加到typeHandler。现在每个RoudingMode用她原始单的值映射一个整型值。.
	<!-- mybatis-config.xml -->
	<typeHandlers>
		<typeHandler handler="org.apache.ibatis.type.EnumOrdinalTypeHandler" javaType="java.math.RoundingMode"/>
	</typeHandlers>

	但是如果你用枚举在一个地方映射string，在另外一个地方映射integer，该怎么办？。
	auto-mapper将会自动使用EnumOrdinalTypeHandler。因此，如果我们想要返回使用普通的普通的EnumTypeHandler，我们不得不告诉她。通过使用那些sql语句明确设置这个处理器的类型。
	（映射文件直到下一节才能被覆盖，所以如果这是你第一次通过这个文档看这个，你可以想跳过这个。待会再看）
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

	注意这个迫使我们在我们的select语句中使用resultMap代替resultType。

	3.1.6 对象工程（objectFactory）
	每一次，mybatis为结果对象创建一个新的实例时，他用ObjectFactory实例去做的。默认的ObjectFactory只使用默认构造函数实例化目标类。如果你想去覆盖这个ObjectFactory的行为方法，你可以创建你自己的ObjectFactory。例如：

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

	这个ObjectFactory接口是非常简单的，他包含两个创建方法，第一个是处理默认构造函数，另外一个是处理参数构造函数。最后，这个setProperties（）方法可以被配置的ObjectFactory使用。在包含objectFactory元素主体内定义的属性竟会被传递给setProperties（）方法，在初始化你的ObjectFactory实例后。

	3.1.7 插件
	Mybatis允许你在映射语句执行中的某些点去拦截调用。默认的，Mybatis允许插件去拦截方法调用。
		? Executor (update, query, flushStatements, commit, rollback, getTransaction, close, isClosed)
		? ParameterHandler (getParameterObject, setParameters)
		? ResultSetHandler (handleResultSets, handleOutputParameters)
		? StatementHandler (prepare, parameterize, batch, update, query)
	这些类方法打细节可以通过每一个完整的方法签名去发现，以及每个MyBatis发行版提供的源代码。你需要理解你重写的方法的具体实现，假设你正在做的不仅仅的监听调用。如果你想阐释修改和重写给予方法的实现，你可能会破坏mybatis的核心。这些是最低级别的类和方法，所以用插件要谨慎。用插件他们提供的能力的漂亮简单的，简单的实现这个初始化接口，确定指定这个你想去拦截的签名。

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

	上面的插件会在执行初始化时拦截所有的调用“update”方法。这是一个内部对象，负责映射语句的低级执行。
	注意重写配置类
		除了通过插件修改mybatis核心实现，你也完全可以重写配置类。简单的继承他并且在里面重写任何一个方法，可以通过他调用这个 SqlSessionFactoryBuilder.build(myConfig) 方法。尽快如此，他可能在mybatis 的实现上有验证的影响。所以谨慎使用。
	3.1.8 environments（环境）
		mybatis可以被配置多个环境，这可以帮助您将SQL映射应用到多个数据库中，原因有很多。你可能对你发开发有不同的配置。测试和生产环境，或者，你可以有多个生成数据库共享同一个图表，或者你想用同同样的sql映射。这有许多使用场景。

		一个重要的事情是记住这个想法：当你可以配置多个环境的时候，你可以只选择在每个SqlSessionFactory实例中选择一个。
		所以如果你想去连接两个数据库，你需要创建两个SqlSessionFactory实例。用三个数据库，你需要三个实例，等等。他是真的很容易记。
			.一个SqlSessionFactory实例化每个数据库
			为了指定build的环境，你简单的将他作为可选参数传递到SqlSessionFactoryBuilder。下面是接受环境的两个签名。
			SqlSessionFactory factory = new SqlSessionFactoryBuilder().build(reader, environment);
			SqlSessionFactory factory = new SqlSessionFactoryBuilder().build(reader, environmen

		如果这个环境被省略了。然后这个默认环境被加载。就像例子那样：
			SqlSessionFactory factory = new SqlSessionFactoryBuilder().build(reader);
			SqlSessionFactory factory = new SqlSessionFactoryBuilder().build(reader, properties

		这个environments元素定义了如何配置环境。
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

		注意这些关键点
			.默认的环境ID(e.g. default="development").
			.定义了每个环境的环境ID。(e.g. id="development").
			.TransactionManager配置 (e.g. type="JDBC")
			.数据源配置 (e.g. type="POOLED")
		这个默认的环境和环境id是自己说明的。可以按你喜欢命名他们。只要确定这个默认匹配他们当中的一个。

		transactionManager
			这有两个TransactionManager类型(i.e. type="[JDBC|MANAGED]")，他们包括了mybatis：
			.JDBC---这个配置简单的直接利用JDBC提交和回退工具。他依赖于这个链接检索数据库去管理实务的范围。
			.MANAGED---这个配置几乎什么都不做。他从不提交或回退一个链接。相反的，他让这个容器管理事务的完整生命周期(e.g.a JEE Application Server context). 他默认是关闭链接的，然而，一些容器不期望这样，因此，如果你需要从关闭链接去停止他，设置"closeConnection"属性为false。例如：
				<transactionManager type="MANAGED">
					<property name="closeConnection" value="false"/>
				</transactionManager>
			注意如果你希望使用mybatis和spring一起，不需要配置任何TransactionManager。因为这个Spring模块会自己设置他，一个覆盖所有先前设置的配置。
			这些TransactionManager类型都不需要任何属性，无论如何，他们都是Type Aliases（类型别名），所以从另一方面来说，而不是使用他们，你可以设置你自己的完全限制类名或Type Alias，引用你自己的对TransActionFactory接口的实现。
			public interface TransactionFactory {
				void setProperties(Properties props);
				Transaction newTransaction(Connection conn);
				Transaction newTransaction(DataSource dataSource, TransactionIsolationLevel level, boolean autoCommit);
			}
			在XML中的任何属性配置都会在实例化后被传递给 setProperties() 方法。你的实现将会需要创建一个Transaction接口，这个是非常简单接口。
				public interface Transaction {
					Connection getConnection() throws SQLException;
					void commit() throws SQLException;
					void rollback() throws SQLException;
					void close() throws SQLException;
					Integer getTimeout() throws SQLException;
				}
			使用这两个接口，你可以完全的自己设置Mybatis如何处理Transaction

			dataSource
				这个dateSource元素用标准的JDBC数据源接口配置这个JDBC连接对象源 
				.大部分mybatis应用将会配置一个数据源，就想实例的那样。然而，那是不需要的。但是要注意，为了促进懒加载，这个dataSource是需要的。
			这有三个构建dataSource类型 (i.e. type="[UNPOOLED|POOLED|JNDI]"):
			UNPOOLED--该DataSource 的实现只是在每次请求时打开和关闭连接。虽然有点慢，这是一个好的选择对于简答的应用来说，不需要立即可用连接的性能。不同的数据库也是不同的再这个功能领域中，因此，对于一些人来说，池和这个配置可能不太重要。
				UNPOOLED DaaSource只有五个配置属性
				? driver – JDBC驱动类的完全限定名 (NOT of the DataSource class if your driver includes one).
				? url – 你数据库实例的JDBC的URL
				? username – The database username to log in with.
				? password - The database password to log in with.
				? defaultTransactionIsolationLevel – 连接的默认事务的隔离级别.
			随意的，你也可以将属性传递给数据库驱动。属性名为前缀，例如：
				? driver.encoding=UTF8
			这个将会传递单数encoding，带着值UTF9，通过DriverManager.getConnection(url, driverProperties)方法给你的数据库驱动。
				POOLED--这个DataSource池的实现是JDBC连接对象，是为了避免最初的连接和验证所需的时间，要求去创建一个新的连接实例。对于当前的web应用来说，去快速获取响应，这是一个流行的方法。
				除了上面的(UNPOOLED) 属性，还有许多属性可以使用配置这个POOLED datasource:
				? poolMaximumActiveConnections – This is the number of active (i.e. in use) connections that can exist at any given time. Default: 10
				? poolMaximumIdleConnections – The number of idle connections that can exist at any given time.
				? poolMaximumCheckoutTime – This is the amount of time that a Connection can be "checked out" of the pool before it will be forcefully returned. Default: 20000ms (i.e. 20 seconds)
				? poolTimeToWait – This is a low level setting that gives the pool a chance to print a log status
				and re-attempt the acquisition of a connection in the case that it’s taking unusually long (to avoid failing silently forever if the pool is misconfigured). Default: 20000ms (i.e. 20 seconds)
				? poolMaximumLocalBadConnectionTolerance – This is a low level setting about tolerance of bad connections got for any thread. If a thread got a bad connection, it may still have another chance to re-attempt to get another connection which is valid. But the retrying times should not more than the sum of  poolMaximumIdleConnections and poolMaximumLocalBadConnectionTolerance . Default: 3 (Since: 3.4.5)
				? poolPingQuery – The Ping Query is sent to the database to validate that a connection is in
				good working order and is ready to accept requests. The default is "NO PING QUERY SET", which will cause most database drivers to fail with a decent error message.
				? poolPingEnabled – This enables or disables the ping query. If enabled, you must also set the poolPingQuery property with a valid SQL statement (preferably a very fast one). Default: false.
				? poolPingConnectionsNotUsedFor – This configures how often the poolPingQuery
				will be used. This can be set to match the typical timeout for a database connection, to avoid unnecessary pings. Default: 0 (i.e. all connections are pinged every time – but only if poolPingEnabled is true of course).
			JNDI--这是DataSource的一个实现，使用与容器，比如EJB或者Application Servers。这可以集中或外部配置数据源，并在JNDI上下文中引用它。这个DataSource配置只需要两个属性
				? initial_context – This property is used for the Context lookup from the InitialContext
				(i.e. initialContext.lookup(initial_context)). This property is optional, and if omitted, then the data_source property will be looked up against the InitialContext directly.
				? data_source – This is the context path where the reference to the instance of the DataSource can be found. It will be looked up against the context returned by the initial_context lookup, or against the InitialContext directly if no initial_context is supplied.
			和其他DtaSource配置相似，他也可以直接通过属性前缀传送属性，例如
				? env.encoding=UTF8
			你可以通过实现接口要插入任何第三方数据源
				org.apache.ibatis.datasource.DataSourceFactory :
				public interface DataSourceFactory {
					void setProperties(Properties props);
					DataSource getDataSource();
				}
			org.apache.ibatis.datasource.unpooled.UnpooledDataSourceFactory 可以被用作超类去创建新的datasource
			连接物，例如这是使用C3P0的代码。
				import org.apache.ibatis.datasource.unpooled.UnpooledDataSourceFactory;
				import com.mchange.v2.c3p0.ComboPooledDataSource;
				public class C3P0DataSourceFactory extends UnpooledDataSourceFactory {
					public C3P0DataSourceFactory() {
						this.dataSource = new ComboPooledDataSource();
					}
				}
			去设置，为每一个你想mybatis调用的每一个setter方法添加一个属性。
				<dataSource type="org.myproject.C3P0DataSourceFactory">
					<property name="driver" value="org.postgresql.Driver"/>
					<property name="url" value="jdbc:postgresql:mydb"/>
					<property name="username" value="postgres"/>
					<property name="password" value="root"/>
				</dataSource>
			
		3.1.9 databaseProvlder
			Mybatis能够依赖你数据库的供应者生成不同的语句。他的多DB供应商支持是基于映射语句的databaseId属性。MyBatis将加载所有没有databaseId属性的语句，或者使用与当前数据库匹配的databaseId。以防同一个语句被找到没有databaseId，这个后一个将会被丢弃。为了能够实现多供应商支持，添加一个databaseIdProvider到mybatis-config。xml文件。例如：
				<databaseIdProvider type="DB_VENDOR" />
			这个 DB_VENDOR实现databaseIdProvider设置为databaseId，这个字符串由DatabaseMetaData#getDatabaseProductName() 方法返回。通常字符串太长和不同的版本，对于同样的产品可能返回不同的值。你可能想将他覆盖成一个更短通过添加属性。例如：
			<databaseIdProvider type="DB_VENDOR">
				<property name="SQL Server" value="sqlserver"/>
				<property name="DB2" value="db2"/>
				<property name="Oracle" value="oracle" />
			</databaseIdProvider>相应的
			当提供属性时，这个 DB_VENDOR databaseIdProvider将会查询这个属性值，并把查到的第一个值作为key，这些值都来自database product的返回name，如果没有匹配的属性，则为null。在这种情况下，如果getDatabaseProductName() 方法返回“Oracle (DataDirect)”，这个databaseId就会被设置成"oracle".
			你可以通过实现这个接口org.apache.ibatis.mapping.DatabaseIdProvider建立你自己的DatabaseIdProvider，并在mybatis-config.xml中注册他。
			public interface DatabaseIdProvider {
				void setProperties(Properties p);
				String getDatabaseId(DataSource dataSource) throws SQLException;
			}
		
		3.1.10 mappers
			现在mybatis的行为被上面的 配置元素配置，我们准备定义自己的映射sql语句。但是首先，我们需要去告诉mybatis去哪里找这些语句。在这方面java不会准备提供任何好的方法去自动查找。所以自豪的方法是去简单的告诉mybatis去哪里找这个映射文件。你可以使用类路径相对资源引用。完全限定url引用(including  file:/// URLs)，类名或者包名。
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

			这个语句简单的告诉了mybatis去哪里找这些配置映射文件，其他细节在每一个映射文件中，这就是我们下一节会具体讲的内容。

4.Mapper XML Files
	4.1 Mapper XML Files
		Mybatis真正的能力是在隐射语句上。这就是魔术发生的地方。对于所有的能力来说，映射XML文件是相对简单的。当然，如果您将它们与等效的JDBC代码进行比较，您将立即看到95%的代码节省。MyBatis是为了关注SQL而构建的，它会尽力避开您的方式。
		Mapper XML文件只有很少的一级元素。(按照它们应该被定义的顺序)
			? cache – Configuration of the cache for a given namespace.
			? cache-ref – Reference to a cache configuration from another namespace.
			? resultMap – The most complicated and powerful element that describes how to load your objects from the database result sets.
			? parameterMap – Deprecated! Old-school way to map parameters. Inline parameters are preferred and this element may be removed in the future. Not documented here.
			? sql – A reusable chunk of SQL that can be referenced by other statements.
			? insert – A mapped INSERT statement.
			? update – A mapped UPDATE statement.
			? delete – A mapped DELETE statement.
			? select – A mapped SELECT statement.
		下一节将会详细的描述每一个元素，从语句本身开始。
		
		4.1.1 select
			这个select语句将会是你用到最多的元素。把数据放在数据库中并不是很有价值，直到你把它取出来。大部分应用查询数据远比修改数据次数多，对每一个插入更新或者删除操作，都带着很多查询操作。这是mybatis创建的原则之一，也是为什么这么关注和努力都放在查询和结果映射的原因。对于简单的情景，selelct元素也是相当的简单，例如：
				<select id="selectPerson" parameterType="int" resultType="hashmap">
					SELECT * FROM PERSON WHERE ID = #{id}
				</select>

			这个语句是调用selectPerson，使用的参数类型是int（或者Integer），然后通过列名和行值之间的映射返回一个HashMap键值对。
			注意参数的符号
				#{id}
			这是告诉Mybatis创建一个PrepareStatement参数。在JDBC中，这么一个参数会被立即被“？”鉴定。就是在sql当中关联一个新的PreparedStatement。就像这样：
				// Similar JDBC code, NOT MyBatis…
				String selectPerson = "SELECT * FROM PERSON WHERE ID=?";
				PreparedStatement ps = conn.prepareStatement(selectPerson);
				ps.setInt(1,id);
			当然，这有许多代码是被JDBC单独要求的去提取这个结果和映射成一个对象的实例。这就是mybatis保存你们已经做的事情的方法。这有更多有关参数和结果映射的信息，这些细节就在后续章节。
			这一节元素有很多状态允许你去配置每一个语句行为的细节。
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
			描述：命名空间的唯一标识。用来引用语句。

			parameterType：
			描述：将会被传入语句的完全限定类型或者的参数别名。这个参数是可选的，因为MyBatis可以计算TypeHandler来使用传递给语句的实际参数。默认是不设置。

			parameterMap
			描述：这是一个弃用的方法去引用一个外部的paramperMap。用内联的参映射和paramenterType属性。

			resultType
			描述：将会被从语句返回的类型。是完全限定名或者预期类型的别名。

			returnMap
			描述：一个命名对预期的resultMap的引用。结果映射是mybatis最强大的功能。好的理解他，许多困难的映射场景都可以的被解决，用resultMap或者resuleType，不能两者一起用。

			flushCache
			描述：设置成true，就会使用本地或者二级缓存去刷新无论何时被调用的语句。默认是false。

			userCache
			描述：设置成true，会导致语句的结果被缓存在二级缓存中，默认是true。

			timeout
			描述：设置一个时间秒数，表示驱动等待数据库返回结果的时间。在抛出异常前、默认不设置！

		4.1.2
			数据的修改语句，insert。update和delete是非常相似的再他们的实现上。
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
			描述：命名空间的唯一标识。用来引用语句。

			parameterType：
			描述：将会被传入语句的完全限定类型或者的参数别名。这个参数是可选的，因为MyBatis可以计算TypeHandler来使用传递给语句的实际参数。默认是不设置。
			
			parameterMap
			描述：这是一个弃用的方法去引用一个外部的paramperMap。用内联的参映射和paramenterType属性。
			
			flushCache
			描述：设置成true，就会使用本地或者二级缓存去刷新无论何时被调用的语句。默认是false。
			
			timeout
			描述：设置一个时间秒数，表示驱动等待数据库返回结果的时间。在抛出异常前、默认不设置！

			statementType ：
			描述：任何一个STATEMENT,PREPARED或者CALLLABLE这会导致mybatis去分别使用Statement PreparedStatment或者CallableStatement。默认是PREPARED。
			
			useGeneratedKeys
			描述：（在inset和update中使用），告诉mybatis去使用jdbc getFeneratedKeys方法在内部去检索已经生成的键值通过数据库(e.g.auto increment fields in RDBMS like MySQL or SQL Server).默认是false

			
			keyProperty
			描述：（在inset和update中使用），标识一个参数，mybatis将会通过getGeneratedKys方法返回设置这个键值。或者通过selectKey这个insert语句的子元素。默认是：不设置；如果期望多个生成的列，则可以是一个逗号分隔的属性名称列表。

			databaseId:
			描述：如果有一个配置的databaseIdProvider, MyBatis将加载所有没有databaseId属性的语句，或者使用与当前数据库匹配的databaseId。如果在没有databaseId的情况下发现相同的语句，则后者将被丢弃。

			下面是一些insert，update，和delete的语句

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

			正如前面提到的，插入要稍微丰富一点。他有一些额外的属性和子元素，允许他用一些方法去处理键值生成。
			首先，如果你的数据库支持自动生成主键字段（例如mysql和sql server），然后你可以简单的设置 useGeneratedKeys="true"和设置keyProperty为目标属性，你就完成了。例如，如果上面的Author表已经用了id的自动生成列类型为这个id，这个语句会按下面修改。
				<insert id="insertAuthor" useGeneratedKeys="true"
					keyProperty="id">
					insert into Author (username,password,email,bio)
					values (#{username},#{password},#{email},#{bio})
				</insert>

			你可以传递一个Author的数组和list并检索自动生成的key。

				<insert id="insertAuthor" useGeneratedKeys="true"
				keyProperty="id">
					insert into Author (username, password, email, bio) values
					<foreach item="item" collection="list" separator=",">
						(#{item.username}, #{item.password}, #{item.email}, #{item.bio})
					</foreach>
				</insert>

			Mybatis有另外一个方法去解决数据库的key生成。不支持自动生成列类型，或者也不支持JDBC驱动支持auto-generated key。
			这是一个简单的例子，生成一个随机ID
				<insert id="insertAuthor">
					<selectKey keyProperty="id" resultType="int" order="BEFORE">
						select CAST(RANDOM()*1000000 as INTEGER) a from SYSIBM.SYSDUMMY1
					</selectKey>
					insert into Author
						(id, username, password, email,bio, favourite_section)
					values
						(#{id}, #{username}, #{password}, #{email}, #{bio}, #{favouriteSection,jdbcType=VARCHAR})
				</insert>

			在上面的例子中，这个selectKey语句将会马上执行，这个Author id属性会被设置值，然后这个插入语句也会被调用。这使您可以在数据库中自动生成密钥，而不会使Java代码复杂化。
				
				selectKey元素描述如下:
					<selectKey
						keyProperty="id"
						resultType="int"
						order="BEFORE"
						statementType="PREPARED">
				
				keyProperty
					描述：这个目标属性是需要被设置的selectKy语句的结果，如果是多个生成列可以作为一个逗号分隔的参数名list。
				keyColumn:
					描述：返回结果的列名设置匹配的属性，如果是多个生成列可以作为一个逗号分隔的参数名list。
				resultType:
					描述：结果的类型，mbatis可以总是算出来。但是加了也没什么坏处。mybatis雨荨任何简单的类型当做key，包括Strings、如果你希望多个生成列，然后你可以看见一个对象包含了所有的期望的属性或者map。
				order:
					描述：可以设置成BEFORE或者AFTER，
					BEFORE：首先查询key，设置kryProperty，然后生成insert语句。
					AFTER：它运行insert语句，然后是selectKey语句——这与Oracle这样的数据库很常见，这些数据库可能在insert语句中嵌入了序列调用。
				statementType:
					描述:和上面一样，MYBATIS支持STATEMENT.PREPARED和CALLABLE 语句，对应的是Statement PreparedStatment或者CallableStatement。
				
		4.1.3 sql
			这个元素可以用在定义重复使用的sql片段代码，这个代码包含了其他语句。他可以被静态的参数化（在加载阶段）。不同的参数在include实例中也有点不同。例如：
				<sql id="userColumns"> ${alias}.id,${alias}.username,${alias}.password </sql>
			这个sql片段可以在include中被其他语句中使用。例如
				
				<select id="selectUsers" resultType="map">
					select
						<include refid="userColumns"><property name="alias" value="t1"/></include>,
						<include refid="userColumns"><property name="alias" value="t2"/></include>
					from some_table t1
						cross join some_table t2
				</select>
			
			属性值也可以使用，包括refid属性或包含在include子句中的属性值，例如：
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
				在所有的语句部分，你已经看到了简单的设置参数例子。参数化是非常强大的属性。
					<select id="selectUsers" resultType="User">
						select id, username, password
							from users
						where id = #{id}
					</select>

				上面的例子说明了一个最简单的命名参数映射。这个parameterType是int，参数名可以随便叫，设置复杂参数这么设置
					<insert id="insertUser" parameterType="User">
						insert into users (id, username, password)
						values (#{id}, #{username}, #{password})
					</insert>

				这些属性会传递到PrepareStatement参数上。
				对于参数，还有其他功能，可以指定一特殊的类型。例如
					#{property,javaType=int,jdbcType=NUMERIC}
				
				看起来已经有点啰嗦了，但事实是你很少会设置这些。对于数值类型，也有一个数字尺度来确定有多少位小数是相关的。
					#{height,javaType=double,jdbcType=NUMERIC,numericScale=2}

				mode：



			





	







