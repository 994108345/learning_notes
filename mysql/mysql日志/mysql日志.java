mysql日志
mysql 的日志主要分为四类：
.错误日志：记录mysql服务的启动	、运行或停止mysql服务时出现的错误。
.查询日志：记录建立的客户端连接和执行的语句。
.二进制日志：记录所有更改数据的语句，可以用于数据复制
.慢查询日志：记录所有执行时间超过long—_query_time的所有查询或不适用索引的查询。
	我的mysql的日志文件默认在:C:\Program Files (x86)\MySQL\MySQL Server 5.0\data目录下
	默认情况下，所有的日志创建于Mysql数据目录中。通过刷新日志，可以强制mysql关闭和重新打开日志文件（或者在某些情况下切换到一个新的日志）当执行一个FLUSH LOGS语句或执行 mysqladmin flush-logs 或 mysqladmn refresh时，讲刷新日志（待检测）
	启动日志功能会降低mysql数据库的性能。日志会占用磁盘空间。
	通过show VARIABLES like 'log_%'这个语句可以看到所有日志文件的状态和信息
二进制日志：
	二进制日志主要记录Mysql数据库的变化。包含了所有更新了数据或者已经潜在更新了数据的语句。语句以“事件”的形式保存。
	使用二进制日志的主要目的是最大可能地恢复数据库，。
	默认情况下，二进制日志是关闭的。通过修改my.ini匹配值文件设置信息
	在my.ini配置文件中找到[mysqld]，在这个[mysqld]组下面加上配置信息：
	log-bin[=path/[filename]]//path表名日志文件所在的目录路劲，filename指定了日志文件的名称（我们可以再系统里找到一个filename。index文件，这是所有日志文件的清单，不写路劲和文件名就是使用默认名。）
	expire_logs_days=10//定义了mysql清除过期日志的时间，即自动删除的天数。默认值为0，表示没有自动删除。
	max_binlog_size=100M//定义了单个文件的小小限制，如果写入的内容大小超出给定值，日志关闭当前文件，重新打开一个新的日志文件，默认值是1GB
	如果mysql默认是装在C盘的，该文件默认的不让我们修改的。需要以下操作：
	点击文件右键->属性->安全->编辑->组或用户名选择Users->User权限在允许那一栏全部勾上->确定就ok了
	改完配置文件要，要生效需要重启mysql服务，就是在服务中找到mysql 的服务，重启启动即可。
	这时候再运行，即可发现log-bin 的value从OFF变成ON，即开启状态。
	这时候就可以再目录data目录下看到二进制文件。
	注意：数据库文件最好不要与日志文件放在同一个次胖上，这样，当数据库文件所在的磁盘发生故障时，可以使用日志文件恢复数据。
	使用SHOW BINARY LOGS可以查看二进制日志文件的信息。
	每启动一次Mysql服务，将会产生一个新的日志文件！
	删除二进制文件
		reset master：删除所有的二进制文件
		purge {master|BINARY} LOGS TO ‘log_name’:按文件名删除
		purge {master|BINARY} LOGS BEFORE ‘date’:按日期删除
	使用二进制文件还原数据库

	


