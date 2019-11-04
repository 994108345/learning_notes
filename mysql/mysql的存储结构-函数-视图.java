mysql的存储过程-存储函数-视图

1 存储过程
	
	1.1 定义：存储过程就是多个sql语句的组合，并加上一定的逻辑。
	1.2 优势： 1 第一次执行存储过程，mysql会编译该存储过程的语句，后续再执行该存储过程，无需编译，直接执行。普通的sql语句，每次运行都要先编译后执行，所以存储过程会比普通的sql执行速度较快一点。
			2 如果存在多个复杂的数据库操作，在代码中执行一个个sql，为了保证数据库数据库的一致性，只能通过事务来解决，如果全部写入存储过程，即无需事务，一次执行完毕。
			3 

	1.3 创建存储过程（无参）：
	
	delimiter $
	create PROCEDURE pro_test01()
	begin 
	select *from ws_chat_log;
	end $
	delimiter
	
	上诉语句中：delimiter $和 delimiter // 差不多，看每个人的习惯，delimiter是分割符，mysql 默认’; ’是分隔符，如果不提前声明分割符，编译器会把存储过程当成 sql 语句执行，会报错,所以要事先用delimiter关键字声明，在用完以后 还要把分割符还原
	
	在mybatis中调用存储过程
	<select id="callProduct" resultType="cn.nldxm.windspace.website.model.mysql.MysqlTest">
		call pro_test01()
	</select>
	
	1.4 创建存储过程（有参）：
		
	delimiter $
	create PROCEDURE pro_test03(in a int)
	begin 
	select *from ws_chat_log where id = a;
	end $
	delimiter
	
	mybatis中的写法
	<select id="callProductWithParam" resultType="cn.nldxm.windspace.website.model.mysql.MysqlTest">
    call pro_test03(
    #{id}
    )
	
	1.4.1 参数类型：
	IN： 输入参数：表示调用者向过程传入值（传入值可以是字面量或变量）
	OUT： 输出参数：表示过程向调用者传出值(可以返回多个值)（传出值只能是变量）
	INOUT 输入输出参数：既表示调用者向过程传入值，又表示过程向调用者传出值（值只能是变量）
	
	1.4.1.1 in的例子
	delimiter $
	create PROCEDURE pro_test03(in a int)
	begin 
	select *from ws_chat_log where id = a;
	end $
	delimiter
	
	mybatis中的写法
	<select id="callProductWithParam" resultType="cn.nldxm.windspace.website.model.mysql.MysqlTest">
    call pro_test03(
    #{id}
    )
	<select>
	
	1.4.1.2 out的例子
	delimiter $
	CREATE PROCEDURE proc_out_damo4(out a int,out b VARCHAR(255)) 
	begin
	select id,pic_name
	#绑定值
	into a,b 
	from ws_thousand_pic_src where id = 1;
	end $
	delimiter
	
	mybatis的写法
	<select id="proc_out_damo1" statementType="CALLABLE" parameterType="java.util.Map">
	  {
		call proc_out_damo4(#{id,mode=OUT,jdbcType=INTEGER},#{picName,mode=OUT,jdbcType=VARCHAR})
		}
	  </select>
	  
	java代码：
	mapper：
	注意没有返回参数！
	void proc_out_damo1(Map<String,Object> map);
	
	调用：
	Map<String,Object> map = new HashMap<>(8);
	map.put("id",null);
            map.put("picName",null);
            mapper.proc_out_damo1(map);
			
	取值：
	结果值在map的id key中	
	
	1.4.1.3 inout的例子
	delimiter $
	CREATE PROCEDURE proc_inout_damo2(inout a int) 
	begin
	select a;
	set a = 3;
	end $
	delimiter
	
	mybatis写法
	<select id="prodInoutDamo1" statementType="CALLABLE" parameterType="java.util.Map" resultType="java.lang.Integer">
    {
    call proc_inout_damo2(#{id,mode=INOUT,jdbcType=INTEGER})
    }
	</select>
	
	mapper:
	Integer prodInoutDamo1(Map<String,Object> map);
	
	调用：
	Map<String,Object> map = new HashMap<>(8);
	map.put("id",10);
    int a = mapper.prodInoutDamo1(map);
	
	取值：
	结果值再map的id key中
	
	1.4.2 变量
	
	1.4.2.1 局部变量
	
	定义局部变量：
		DECLARE l_int int unsigned default 4000000;  
		DECLARE l_numeric number(8,2) DEFAULT 9.95;  
		DECLARE l_date date DEFAULT '1999-12-31';  
		DECLARE l_datetime datetime DEFAULT '1999-12-31 23:59:59';  
		DECLARE l_varchar varchar(255) DEFAULT 'This will not be padded';
		
	1.4.2.2 用户变量
		set @a = 1;
		
	1.5 修改存储过程
		目前，MySQL还不提供对已存在的存储过程的代码修改.如果，一定要修改存储过程的diam，必须，先将存储过程删除之后，在重新编写代码，或者创建一个新的存储过程 
	1.6 删除存储过程
		#pro_name 是存储过程的名称
		DROP PROCEDURE pro_name;
		

2 函数

	mysql中存在很多已经定义好的函数。mysql也支持我们自己创建自定义函数；
	
	函数只会返回一个值，不像存储结构，可以返回一个集合
	
	2.1 创建函数
	
	create function 函数名([参数列表]) returns 数据类型
	begin
	 sql语句;
	 return 值;
	end;
	
	例1：
	CREATE FUNCTION fuc1(a int) returns int
	begin
	#sql语句
	return 100;
	end;
	
	2.2 调用函数：
	select fuc1(10);
	
	2.3 函数的查看
	show create function 函数名;
	例：show create function fuc1;
	
	
	2.4 函数的修改
	函数的修改只能修改一些如comment的选项，不能修改内部的sql语句和参数列表。
	
	2.5 函数的删除
	drop function 函数名
	例：drop function fuc1
	
	2.6 要点：
		存储过程和函数的区别
			存储过程：不允许执行return，但是通过out返回多个值；存储过程的限制相对较少；作为一个单独部分来执行；
			函数：只能通过return语句返回单个值或表对象；限制较多，不能使用零时表，只能用表变量，还有一些函数不可用；可以嵌入sql语句中使用。
		不能修改存储过程中的代码，只能删了重写一个；
		存储过程可以调用其他存储过程，但是不能删除其他存储过程；
		定义存储过程参数列表时，要把参数名与数据库中的字段区分开来。否则会出现无法预料的结果。
		传入中文参数要注意，在参数后面要加上character set gbk
		存储过程是用户定义的一系列sql语句的集合，涉及特定表或其它对象的任务，用户可以调用存储过程。而函数通常是数据库已定义的方法，它接收参数并返回某种类型的值并且不涉及特定用户表。 
		
3 视图
	
	定义：是虚拟表，MYSQL5.0后开始使用视图，从数据库一个表或多个表中导出来的表。一经定义就存储在数据库汇总，若基本表发生变化，则这种变化也会自动反映到视图上。
		
	创建视图
		CREATE [OR REPLACE] [ALGORITHM = {UNDEFINED | MERGE | TEMPTABLE}] VIEW view_name [{column_list}] AS SELECT_statement [WITH[CASCADED | LOCAL] CHECK OPTION]
		CREATE 是创建表 REPLACE 是替换表 。
		ALGORITHM 是选择算法。
			UNDEFINED ：让mysql自动选择算法；
			MERGE:将引用视图语句与视图的sql语句合并起来，最后一起执行；
			TEMPTABLE:将视图的结果存入临时表，然后用临时表来执行语句。
		CASCADED与LOCAL为可选参数。CASCADED为默认值，表示更新视图时要满足所有相关视图和表的条件：LOCAL表示更新视图的时候满足视图本身定义的条件即可。
		
		单表创建视图
		CREATE VIEW view_t as SELECT quantity,prive,quantity*prive FROM t;
		
		多表创建视图
		CREATE VIEW stu_glass (id, NAME, glass) AS SELECT student.s_id, student. NAME, stu_info.glass FROM student, stu_info WHERE student.s_id = stu_info.s_id;
		
	使用视图：
		select * from v_damo1;
		
	查看视图
		DESCRIBE 视图名；查看视图表的结构
		show table status like '视图名';
		show create view 视图名；查看视图的详细信息；
		在mysql中，information_schema数据库下的views表存储了所有视图的定义：select * from information_schema.views;
	
	修改视图
		CREATE OR REPLACE [ALGORITHM = {UNDEFINED | MERGE | TEMPTABLE}] VIEW view_name [{column_list}] AS SELECT_statement [WITH[CASCADED | LOCAL] CHECK OPTION]
		ALTER 语句修改视图
		ALTER [ALGORITHM = {UNDEFINED | MERGE | TEMPTABLE}] VIEW view_name [{column_list}] AS SELECT_statement [WITH[CASCADED | LOCAL] CHECK OPTION]
	
	更新视图
		UPDATe view_t SET quantity= 5；
		INSERT INTO view_name values(.....)
	
	删除视图
		DROP VIEW [IF EXISITS] view_name [,view_name]... [RESTRICT | CASCADE]
	
	补缺补漏：
		表和视图的区别
		1：视图是已经编译好的sql语句，基于sql语句结果集可视化的表，而表不是。
		2：视图没有实际的物理记录，而基本表有。
		3：表是内容。视图是窗口。
		4：表占用物理内存，而视图不占，视图只能用创建语句来修改。
		5：视图是查看数据的一种方法；防止用户接触数据表。
		6：表属于全局模式，是实表，视图属于全局模式属于虚表。
		7：视图的建立和删除只影响视图本身，不影响对应的基本表。
		
4 触发器	

	定义：触发器即在表的insert、update、delete的前后进行的操作。所以一个表最多只能设置六个触发器。
	
	创建触发器：
	CREATE TRIGGER trigger_name 时机 事件 ON tb1_name FOR EACH ROW trigger_stmt;
	
	事件：insert、update、delete
	时机：after、before
	for each row 是代表任何记录执行对应操作都会触发器
	trigger_stmt:触发器语句
	
	例：
	CREATE TRIGGER trigger_damo2 
	before insert ON ws_thousand_pic_src 
	FOR EACH ROW 
	insert ws_trigger(message)value("触发器插入数据");
	
	当你执行
	insert into ws_thousand_pic_src (pic_source,pic_url,pic_name,oss_url,oss_status) value(0,"测试","测试","测试",1);
	时，就会触发插入操作，在ws_trigger表中新增一条数据。
	
	查看触发器
	SHOW TRIGGERS;
	SELECT * FROM INFORMATION_SCHEMA .TRIGGERS WHERE TRIGGER_NAME = '...';
	
	删除触发器
	DROP TRIGGER trigger_name
	
	补缺补漏
	1：相同的表相同的事件只能创建一个触发器
	2：及时删除不再需要的触发器
	3：MYSQL中触发器中不能对本表进行 insert ,update ,delete 操作，以免递归循环触发。
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	