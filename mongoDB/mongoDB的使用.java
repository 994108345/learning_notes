1 MongoDb概念介绍
	database:数据库就是普通数据库中常说的数据库
	collection: 集合，就是普通数据库中的表
	document：文档，数据库叫数据记录行row
	field：域,数据库中叫字段
	index：索引
	primary key：主键
	1.1	数据库
		mongoDB默认数据库为“db”，该数据库存储在data目录中
		show dbs：显示所有的数据的列表
		db：显示当前数据库对象或集合
		user **：连接到一个指定的数据库
		数据库名的限制
			不能是空字符串（"")。
			不得含有' '（空格)、.、$、/、\和\0 (空字符)。
			应全部小写。
			最多64字节。
		有一些保留的数据库
			admin：将用户存入这个库，这个用户可以继承所有数据库的权限。
			local：数据不会被复制，用来存储本地单台服务器的任何集合。
			config：分片时，保存分片的相关信息，在内部使用
	1.2 文档
		定义：文档是一组键值对。不需要设置相同的字段，数据类型也不需要一样
		注意：1.文档中的键值对是有序的。
				2.文档中的值不仅可以是在双引号里面的字符串，还可以是其他几种数据类型（甚至可以是整个嵌入的文档)。
				3.MongoDB区分类型和大小写。
				4.MongoDB的文档不能有重复的键。
				5.文档的键是字符串。除了少数例外情况，键可以使用任意UTF-8字符。
		文档键命名规范：
			键不能含有\0 (空字符)。这个字符用来表示键的结尾。
			“.”和“$”有特别的意义，只有在特定环境下才能使用。
			以下划线"_"开头的键是保留的(不是严格要求的)。
	1.3 集合
		定义：集合就是MongoDb的文档组，类似于数据库的表。集合的数据没有固定结构。
		集合名命名规范：
			1 集合名不能是空字符串""。
			2 集合名不能含有\0字符（空字符)，这个字符表示集合名的结尾。
			3 集合名不能以"system."开头，这是为系统集合保留的前缀。
			4 用户创建的集合名字不能含有保留字符。有些驱动程序的确支持在集合名里面包含，这是因为某些系统生成的集合中包含该字符。除非你要访问这种系统创建的集合，否则千万不要在名字里出现“$”。
		capped collections
			定义：Capped collections 就是固定大小的collection。它有很高的性能以及队列过期的特性(过期按照插入的顺序)。它非常适合类似记录日志的功能。和标准的collection不同，你必须要显式的创建一个capped collection， 指定一个collection的大小，单位是字节。collection的数据存储空间值提前分配的。
			例如：db.createCollection("mycoll", {capped:true, size:100000})
			注意：
				1 在capped collection中，你能添加新的对象。
				2 能进行更新，然而，对象不会增加存储空间。如果增加，更新就会失败 。
				3 数据库不允许进行删除。使用drop()方法删除collection所有的行。
				4 注意: 删除之后，你必须显式的重新创建这个collection。
				5 在32bit机器中，capped collection最大存储为1e9( 1X109)个字节。
		1.4 元数据
			定义：













指令：
	1 数据库操作
		创建数据库：
		use data_name;
		如果数据库不存在，则创建数据库，否则切换到指定数据库。

		查看目前用的数据库名
		db

		显示所有的数据库
		show dbs
		注意：只有插入数据的数据库才会显示

		删除数据库：
		db.dropDatabase()
		成功会返回：{"dropped":"runoob","ok":1}

		添加用户：
		db.createUser({
			... user:"root",
			... pwd:"bobo",
			... roles:[{role:"dbOwner",db:"wenzailong"}]})


	2 集合操作
		创建集合：
		db.createCollection(name,options);
		name:集合名
		options：可选参数，可以配置集合信息

		显示集合：
		show collection；
		show tables

		删除集合：
		db.collection_name.drop();
		成功返回：true

		在插入时直接创建集合：
		db.lihongbo.insert({"name:"bobojiang"})



	3 文档操作
		插入文档 insert
		db.COLLECTION_NAME.insert(JSONOBJ);
		COLLECTION_NAME:集合名
		JSONOBJ：要插入的JSON对象

		查询集合中的文档 find
		db.COLLECTION_NAME.find();
		COLLECTION_NAME:集合名

		更新文档：update
		db.COLLECTION_NAME.update(
		   <query>,
		   <update>,
		   {
			 upsert: <boolean>,
			 multi: <boolean>,
			 writeConcern: <document>
		   }
		)	
		query : update的查询条件，类似sql update查询内where后面的。
		update : update的对象和一些更新的操作符（如$,$inc...）等，也可以理解为sql update查询内set后面的
		upsert : 可选，这个参数的意思是，如果不存在update的记录，是否插入objNew,true为插入，默认是false，不插入。
		multi : 可选，mongodb 默认是false,只更新找到的第一条记录，如果这个参数为true,就把按条件查出来多条记录全部更新。
		writeConcern :可选，抛出异常的级别。
		例子：db.col.update({'title':'MongoDB 教程'},{$set:{'title':'MongoDB'}})

		更新文档：save
		db.COLLECTION_NAME.save(
		   <document>,
		   {
			 writeConcern: <document>
		   }
		)
		document : 文档数据。
		writeConcern :可选，抛出异常的级别。

		查看文档：
		db.collection_name.find()
		db.collection_name.find().pretty()

		只更新第一条记录：
		db.col.update( { "count" : { $gt : 1 } } , { $set : { "test2" : "OK"} } );

		全部更新：
		db.col.update( { "count" : { $gt : 3 } } , { $set : { "test2" : "OK"} },false,true );

		只添加第一条：
		db.col.update( { "count" : { $gt : 4 } } , { $set : { "test5" : "OK"} },true,false );

		全部添加加进去:
		db.col.update( { "count" : { $gt : 5 } } , { $set : { "test5" : "OK"} },true,true );

		全部更新：
		db.col.update( { "count" : { $gt : 15 } } , { $inc : { "count" : 1} },false,true );

		只更新第一条记录：
		db.col.update( { "count" : { $gt : 10 } } , { $inc : { "count" : 1} },false,false );

		插入多条记录：
		db.lihongbo.insertMany([{"name":"a","age":3},{"name":"b","age":2},{"name":"d","age":3},{"name":"c","age":2}]);

	4 删除文档 remove
		db.collection.remove(
		   <query>,
		   {
			 justOne: <boolean>,
			 writeConcern: <document>
		   }
		)
		query :（可选）删除的文档的条件。
		justOne : （可选）如果设为 true 或 1，则只删除一个文档。
		writeConcern :（可选）抛出异常的级别。
		例子：db.collection_name.remove({'title':'MongoDB 教程'})
		
		删除所有数据
		db.collection_name.remove({})
	
	5 查询文档
		db.collection.find(query, projection)
		query ：可选，使用查询操作符指定查询条件
		projection ：可选，使用投影操作符指定返回的键。查询时返回文档中所有键值， 只需省略该参数即可（默认省略）。
		
		返回所有文档
		db.col_name.find().pretty()
		pretty() 方法以格式化的方式来显示所有文档。

		只返回一个文档：
		db.col_name.findOne().pretty()

		等于	{<key>:<value>}	db.col.find({"by":"菜鸟教程"}).pretty()	
		小于	{<key>:{$lt:<value>}}	db.col.find({"likes":{$lt:50}}).pretty()	
		小于或等于	{<key>:{$lte:<value>}}	db.col.find({"likes":{$lte:50}}).pretty()	
		大于	{<key>:{$gt:<value>}}	db.col.find({"likes":{$gt:50}}).pretty()	
		大于或等于	{<key>:{$gte:<value>}}	db.col.find({"likes":{$gte:50}}).pretty()	
		不等于	{<key>:{$ne:<value>}}	db.col.find({"likes":{$ne:50}}).pretty()	

		and条件
		db.col_name.find({key1:value1, key2:value2}).pretty()
		例子：db.lihongbo.find({name:"a"},{age:3}).pretty()

		or条件
		db.col_name.find({$or: [{key1: value1}, {key2:value2}]}).pretty()
		例子：db.lihongbo.find({$or:[{name:"a"},{age:2}]}).pretty()

		and和or联用
		db.lihongbo.find({"age":{$gte:2},$or:[{"name":"a"},{"name":"c"}]}).pretty()

		projection参数使用（指定返回列）
		db.collection.find(query, {title: 1, by: 1}) // 1-inclusion模式 指定返回的键，不返回其他键
		db.collection.find(query, {title: 0, by: 0}) // 0-exclusion模式 指定不返回的键,返回其他键

	6 条件操作符
		(>) 大于 - $gt
		(<) 小于 - $lt
		(>=) 大于等于 - $gte
		(<= ) 小于等于 - $lte
		测试数据：
		db.col.insertMany([{
			title: 'PHP 教程', 
			description: 'PHP 是一种创建动态交互性站点的强有力的服务器端脚本语言。',
			by: '菜鸟教程',
			url: 'http://www.runoob.com',
			tags: ['php'],
			likes: 200
		},{title: 'Java 教程', 
			description: 'Java 是由Sun Microsystems公司于1995年5月推出的高级程序设计语言。',
			by: '菜鸟教程',
			url: 'http://www.runoob.com',
			tags: ['java'],
			likes: 150
		},{title: 'MongoDB 教程', 
			description: 'MongoDB 是一个 Nosql 数据库',
			by: '菜鸟教程',
			url: 'http://www.runoob.com',
			tags: ['mongodb'],
			likes: 100
		}])

		例子：大于100，小于150
			db.col.find({"likes":{$gt:100,$lt:150}}).pretty()

	7 $type操作符
		type匹配字段类型。
		类型    数字
		Double	1	 
		String	2	 
		Object	3	 
		Array	4	 
		Binary data	5	 
		Undefined	6	已废弃。
		Object id	7	 
		Boolean	8	 
		Date	9	 
		Null	10	 
		Regular Expression	11	 
		JavaScript	13	 
		Symbol	14	 
		JavaScript (with scope)	15	 
		32-bit integer	16	 
		Timestamp	17	 
		64-bit integer	18	 
		Min key	255	Query with -1.
		Max key	127	 
		
	例子：db.col.find({title:{$type:2}})//查询title属性是字符串的

	8 limit与skip方法
		limit：读取指定数量的数据
			db.col.find().limit(1)//查询第一条记录
		skip：跳过指定数量数据
			db.col.find().skip(1)//查询第一条记录后的所有记录

	9 排序
		sort({fild_name,num})来排序 num=1是升序，-1是降序
		例子：db.col.find().sort({likes:-1})

	10 索引
		db.collection_name.createIndex(keys,options)：创建索引
		keys:是你要创建的字段 ，值赋予1就是按升序创建索引，-1就是按降序创建索引
		options:可以加很多条件
			background	Boolean	建索引过程会阻塞其它数据库操作，background可指定以后台方式创建索引，即增加 "background" 可选参数。 "background" 默认值为false。
			unique	Boolean	建立的索引是否唯一。指定为true创建唯一索引。默认值为false.
			name	string	索引的名称。如果未指定，MongoDB的通过连接索引的字段名和排序顺序生成一个索引名称。
			dropDups	Boolean	在建立唯一索引时是否删除重复记录,指定 true 创建唯一索引。默认值为 false.
			sparse	Boolean	对文档中不存在的字段数据不启用索引；这个参数需要特别注意，如果设置为true的话，在索引字段中不会查询出不包含对应字段的文档.。默认值为 false.
			expireAfterSeconds	integer	指定一个以秒为单位的数值，完成 TTL设定，设定集合的生存时间。
			v	index version	索引的版本号。默认的索引版本取决于mongod创建索引时运行的版本。
			weights	document	索引权重值，数值在 1 到 99,999 之间，表示该索引相对于其他索引字段的得分权重。
			default_language	string	对于文本索引，该参数决定了停用词及词干和词器的规则的列表。 默认为英语
			language_override	string	对于文本索引，该参数指定了包含在文档中的字段名，语言覆盖默认的language，默认值为 language.
	11 聚合
		主要是处理数据，例计算平均值、求和

		aggregate()
		例子：db.col.aggregate([{$group:{_id : "$title", num_count : {$sum : 1}}}])
		相当于：select by_user, count(*) from mycol group by by_user

		$sum	计算总和。	db.mycol.aggregate([{$group : {_id : "$by_user", num_tutorial : {$sum : "$likes"}}}])
		$avg	计算平均值	db.mycol.aggregate([{$group : {_id : "$by_user", num_tutorial : {$avg : "$likes"}}}])
		$min	获取集合中所有文档对应值得最小值。	db.mycol.aggregate([{$group : {_id : "$by_user", num_tutorial : {$min : "$likes"}}}])
		$max	获取集合中所有文档对应值得最大值。	db.mycol.aggregate([{$group : {_id : "$by_user", num_tutorial : {$max : "$likes"}}}])
		$push	在结果文档中插入值到一个数组中。	db.mycol.aggregate([{$group : {_id : "$by_user", url : {$push: "$url"}}}])
		$addToSet	在结果文档中插入值到一个数组中，但不创建副本。	db.mycol.aggregate([{$group : {_id : "$by_user", url : {$addToSet : "$url"}}}])
		$first	根据资源文档的排序获取第一个文档数据。	db.mycol.aggregate([{$group : {_id : "$by_user", first_url : {$first : "$url"}}}])
		$last	根据资源文档的排序获取最后一个文档数据	db.mycol.aggregate([{$group : {_id : "$by_user", last_url : {$last : "$url"}}}])

		管道：
			管道一般用于将当前命令的输出结果作为下一个命令的参数。
			$project：修改输入文档的结构。可以用来重命名、增加或删除域，也可以用于创建计算结果以及嵌套文档。
			$match：用于过滤数据，只输出符合条件的文档。$match使用MongoDB的标准查询操作。
			$limit：用来限制MongoDB聚合管道返回的文档数。
			$skip：在聚合管道中跳过指定数量的文档，并返回余下的文档。
			$unwind：将文档中的某一个数组类型字段拆分成多条，每条包含数组中的一个值。
			$group：将集合中的文档分组，可用于统计结果。
			$sort：将输入文档排序后输出。
			$geoNear：输出接近某一地理位置的有序文档。
	
			查询出结果只有title字段，如果输入0，则是查询除title外的其他字段		
			db.col.aggregate({$project:{title:1}})

	12 复制
		mongoDB复制至少需要两个节点，一个的主节点，负责处理客户端请求，其余都是从节点，负责复制主节点上的数据。常见搭配方式就是：一主一从，一主多从
	
	13 分片技术

	14 备份与恢复
		mongodump -h dbhost -d dbname -o dbdirectory
			-h：
			MongDB所在服务器地址，例如：127.0.0.1，当然也可以指定端口号：127.0.0.1:27017

			-d：
			需要备份的数据库实例，例如：test

			-o：
			备份的数据存放位置，例如：c:\data\dump，当然该目录需要提前建立，在备份完成后，系统自动在dump目录下建立一个test目录，这个目录里面存放该数据库实例的备份数据。

		mongorestore -h <hostname><:port> -d dbname <path>
		--host <:port>, -h <:port>：
			MongoDB所在服务器地址，默认为： localhost:27017

			--db , -d ：
			需要恢复的数据库实例，例如：test，当然这个名称也可以和备份时候的不一样，比如test2

			--drop：
			恢复的时候，先删除当前数据，然后恢复备份的数据。就是说，恢复后，备份后添加修改的数据都会被删除，慎用哦！

			<path>：
			mongorestore 最后的一个参数，设置备份数据所在位置，例如：c:\data\dump\test。

			你不能同时指定 <path> 和 --dir 选项，--dir也可以设置备份目录。

			--dir：
			指定备份的目录

			你不能同时指定 <path> 和 --dir 选项。

	15 监控
		mongostat
			在mongoDB/bin目录下，运行mongostat，显示每个固定间隔mongoDB的运行 状态
		mongotop
			跟踪一个mongoDB的实例，查看哪些大量时间胡斐在读取和写入数据。
			mongotop 10：没10秒检测一次
			mongotop --locks：报告每个额数据库锁的使用中























