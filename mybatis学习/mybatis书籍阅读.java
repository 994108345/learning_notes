
��һ�£�MYbatis���
	1.1 ��ͳ��JSBC���
		JDBC����SUN��˾�����һϵ�й淶����ֻ�����˽ӿڹ淶������ʵ�����ɸ������ݿ⳧���Լ�ȥʵ�ֵġ�
		JDBC��һ�ֵ��͵��Ž�ģʽ��
		ʹ�ô�ͳJDBC��ʽ�ı׶ˣ�
			1.��������:
			2.���쳣�����ҹر���Դ��
	1.2 ORMģʽ
		�����ϵӳ�䣨ORM��
		ORMģ�;������ݿ�ı�ͼ򵥵�java�����ӳ���ϵģ�ͣ����POJO
	
	1.3 hibernate
		hibernate��ͨ�������ļ��������ݿ������ֱ��ӳ�䵽POJO�ϡ���ȫ��ӳ���ܡ�

		hibernate��ȱ�ݣ�
			1.ȫ��ӳ������Ĳ����㣬�������ʱ��Ҫ�������е��ֶ�
			2.�޷����ݲ�ͬ��������װ��ͬ��sql
			3.�Զ��͸�ֵ��sql��ѯ֧�ֽϲ��Ҫ�Լ�дsql�����غ���Ҫ�Լ���������װΪPOJO
			4.������Ч��֧�ִ洢����
			5.��Ȼ��HQL���������ܽϲ���ͻ�����ϵͳ������Ҫ�Ż�SQL����Hibernate��������
		
	1.4 MyBatis
		���Զ�ӳ���ܣ���Ϊ����Ҫ�ֶ�ƥ���ṩPOJO��SQL��ӳ���ϵ��
		Mybatis��ǰ����iBatis��
		Ҳ�����Զ����ӳ�䣬���ʵ�����ͷ��ض����Ӧ�ֶ�����ͬ��
		ʹ��ʱ��ֻ��Ҫһ���ӿڣ�����ʵ���ࡣ

	1.5 ʲôʱ����Mybatis

�ڶ��£�MyBatis����
	2.1 �������
		.SqlSessionFactoryBuilder������SqlSessionFactory
		.SqlSessionFactory������SqlSession���Ự��
		.SqlSession:keyi:���Է���sqlȥִ�в����ؽ����Ҳ���Ի�ȡMapper�ӿڡ�
		.SQL Mapper����java�ӿں�XML�ļ����ɣ���Ҫ������Ӧ��SQL��ӳ�������������SQLȥִ�У������ؽ����
	
	2.2.3 ӳ����
		ӳ��������Java�ӿں�XML�ļ���ͬ��ɵġ�

	2.3��������
		2.3.1 SqlSessionFactoryBuilder
			SqlSessionFactoryBuilder��������XML����Java����������Դ������SqlSessionFactory�ģ����ǹ����������á����ԣ�����Ϳ��Ի��գ�������������ֻ�����ڷ����ڲ���
		
		2.3.2 SqlSessionFactory
			�����Ǵ���SqlSession����SqlSession��һ���Ự��ÿ�η������ݿ⣬��Ҫͨ��SqlSessionFactory����SqlSession������SqlSessionFactory�����������������ڡ�
		
		2.3.3 SqlSession
			��һ���Ự���������������������ݿ⴦������Ĺ����С�
		
		2.3.4 Mapper
			Mapper��һ���ӿڣ����þͷ���sql����������Ҫ�Ľ��������������һ��sqlsession���񷽷��У��Ƿ������

		2.4ʵ��
	
������ ����
	<?xml version="1.0" encoding = "UTF-8"?>
	<configuration>  <!-- ���� -->
		<properties/>  <!-- ���� -->
		<setting/>  <!-- ���� -->
		<typeAliases/>  <!-- �������� -->
		<typeHandlers/>  <!-- ���ʹ����� -->
		<objectFactory/>  <!-- ���󹤳� -->
		<plugins/>  <!-- ��� -->
		<environments>  <!-- ���û��� -->
			<environment>  <!-- ���ñ��� -->
				<transactionManager/>  <!-- ��������� -->
				<dataSource/>  <!-- ����Դ -->
			</environment>
		</environments>
		<databaseIdProvider/>  <!-- ���ݿ⳧�̱�ʶ -->
		<mappers/>  <!-- ӳ���� -->
	</configuration>

	3.1 propertiesԪ��
		�������Ե�Ԫ�أ����������������ļ�����������ʹ������

		3.1.1 property ��Ԫ��
			�����������ݿ��Ĵ������
		3.1.3 �����������
		3.1.1 ���ȼ�
			Mybatis�������ò�����������ظ������ˣ�����صĲ����Ḳ���ȼ��صĲ���
			����˳����1��ֱ���������ļ������õ�propertiesԪ�ء�2������propertieԪ������resource���Զ�ȡ�����������ļ������õ����ԡ�3����ȡ��Ϊ�����������ݵĲ�����
			�������ȼ�(�Ӹߵ���)�ǣ�1.��ȡ��Ϊ�����������ݵ����ԣ�������ͬ������2.��ȡ�����ļ�3.ֱ������propertie
			������ʹ�ö�����ò��������׻��ң���ѡʹ��properties�ļ���
			
	3.2 ����
	3.3 mybatis �ı����ǲ����ִ�Сд��
		3.3.1 ϵͳ�������
			��ϵͳ�Ѿ�����õı�����һ����һЩ�������ͣ�����Data,������data��
		3.3.2 �Զ������
			ͨ��ʹ��ע��@Alias���ñ�����û�����õģ�Ĭ�Ͻ����ʵ�������ı���ȡΪͬ��������ĸСд��
	3.4 typeHandler���ʹ�����
		typeHandler���Ǵ����ݿ�ȡ���������jdbcTypeת����javaType��
		3.4.1 ϵͳ�����typeHandler
			ע�⣺
			.��ֵ���͵ľ��ȣ����ݿ��int��double��decimal��Щ���ͺ�java�ľ��ȳ��ȶ��ǲ�һ���ġ�
			.ʱ�侫�ȣ�ȡ���ݵ�����DareOnlyTypeHandler���ɣ��õ�����Ϊ�����SqlTimestampTypeHandler��
		3.4.2 �Զ���typeHandler
			ϵͳ�����typeHandler�Ѿ����Դ���󲿷�ҵ���ˡ�ֻ�к�������������Ҫ�����Լ����壬���磺�ֵ���ö��
			@MappedTypes���������JavaType���ͣ�
			@MappedJdbcTypes���������JdbcType����
		3.4.3 ö������ typeHandler
			3.4.3.1 EnumOrdinalTypeHandler
				��mybatisĬ�ϵ�ö�����͵Ĵ����������������ö�ٶ�����±�
				���ֻ����mybatis����һ�������ʹ�ã��ܼ򵥡�ֱ������mybatis-config.xml�ļ�
				�������ݣ�
				<typeHandlers>
					<typeHandler handler="org.apache.ibatis.type.EnumOrdinalTypeHandler"></typeHandler>
				</typeHandlers>
				��mapper.xml�ļ��У�Ҫ�������ֶ�Ҫ����typeHandler
				���磺
				��������ã�<result column="sex" typeHandler="org.apache.ibatis.type.EnumOrdinalTypeHandler" property="sex" />
				������ã�
				<insert id="insertUserByTypeHandler">
					insert into `user`(sex)
					values(#{sex,typeHandler=org.apache.ibatis.type.EnumOrdinalTypeHandler})
				</insert>
			3.4.3.2 EnumTypeHandler
				���������ö�ٵĶ�Ӧ�ַ���
			3.4.3.3 �Զ������typeHandler
				�Լ�д���Զ�����Ҫʵ��TypeHandler�ӿڣ�����ע�ͣ����봦��ֵ����
				@MappedTypes(String.class)
				public class UserRemarkTypeHandler implements TypeHandler<String>
				
		3.5 ObjectFactory
			��mybatis����һ�����ؽ��ʱ��������ObjectFactoryȥ����POJO��

		3.6 ���

		3.7 environment���û���
			3.7.1 ����
				����Դ��1.���ݿ�Դ������ 2�����ݿ����������

				transactionManager���õ������ݿ���������type���������÷�ʽ
				1��JDBC
				2��MANAGED ���׹�����JNDI����Դ�г���
				3���Զ���

				dataSource����������Դ���ӵ���Ϣ��type�����ַ�ʽ
				1��UNPOOLED�������ӳ����ݿ�
				2��POOLED�����ӳ����ݿ�
				3��JNDI��JNDI����Դ
				4���Զ�������Դ

			3.7.2 ���ݿ�����
				
			3.7.3 ����Դ
				���Ҫ������������Դ����Ҫʹ���Զ�������Դ���ͱ���ʵ��org.apache.ibatis.datasource.DataSourceFactory

		3.8 databaseIdProvider ���ݿ⳧�̱�ʶ
			
		3.9 ����ӳ�����ķ���

������ ӳ����
	4.1 ӳ��������ҪԪ��
		
	4.2
		4.2.3�Զ�ӳ��
			autoMappingBehavior��
				NONE��
					��ʱ��mybatis ���Զ��ṩӳ�书�ܡ�
				PARTIAL
					�Ի��Զ�ӳ�䣬û�ж���Ƕ�׽����ӳ��Ľ����
				FULL
					���Զ�ӳ���κθ���Ľ����
			Ĭ����PARTIAL

		4.2.4 ӳ�����
			������С��5��ʱ����@Param����õģ�����5��javaBean
		
		4.2.5 ʹ��resultMapӳ������
	
		4.3.2 ����������Զ���
			userGeneratedKeys���Ƿ�ʹ�����ݿ����ò�������
			�������ɵ�����������
				<selectKey keyProperted="id" parameterType="role" useGenneratedKeys="true" keyProperty="id">
					<selectKey keyProperty="id" resultType="int" order="BEFORE">
						select if (max(id) is null,1,max(id)+2) as newId from t_role
					</selectKey>
					inserty ......
		
		4.5.2 �洢����֧��
			������ص�ֵ��null��mybatis��֪��null��ʲô���ͣ�������Ҫ����jdbcType
		
		4.5.3�����϶����滻�ʹ���
			
	4.6 sqlԪ��

	4.7 ������
		������ȫ������Ҳ�����ھֲ�����
		ȫ�����ã��������ļ��������ã�<setting name = "aggressiveLazyLoading" value = "false"/>Ĭ����true������Ĭ���ǿ��������ص�
 		�ֲ����ڼ�����ǩ������<association>��<collection>fetchType���ԣ�fetchType���������ԣ�eager���������أ���lazy�����������أ���

	4.8 ����ceche
		4.8.1 ϵͳ���棨һ������Ͷ������棩��
			һ���������sqlsession��sqlsession����֮���Ǹ���ġ��������������sqlsessionFactory��
			Ĭ���ǿ���һ�����棬�����ڲ�����sql��ȫһ��������£���ô��ʹ��ͬһ��SqlSession�������ͬһ��Mapper�ķ���ʱ������ִֻ��һ��sql����Ϊ��һ�β��ʱ�򣬻Ὣ��ѯ����ŵ������С������ٲ�ֱ���á�����������ó�ʱ��ˢ��ʱ�䡣
			������������Ҫ�������ÿ�����
				����<cache/>���ٿ�����������
				��Ϊ ��SqiSessionFactory����ģ����Բ�ͬ��SqlSession�ǲ�����ġ�
			
		
������ ��̬SQL
	5.1 ����
		<where>
			<if>
			</if>
		</where>
		���if��������where�򲻻����
		<set>���Զ������һ������ȥ��
	
			
			

	


	

