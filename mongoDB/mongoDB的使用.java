1 MongoDb�������
	database:���ݿ������ͨ���ݿ��г�˵�����ݿ�
	collection: ���ϣ�������ͨ���ݿ��еı�
	document���ĵ������ݿ�����ݼ�¼��row
	field����,���ݿ��н��ֶ�
	index������
	primary key������
	1.1	���ݿ�
		mongoDBĬ�����ݿ�Ϊ��db���������ݿ�洢��dataĿ¼��
		show dbs����ʾ���е����ݵ��б�
		db����ʾ��ǰ���ݿ����򼯺�
		user **�����ӵ�һ��ָ�������ݿ�
		���ݿ���������
			�����ǿ��ַ�����"")��
			���ú���' '���ո�)��.��$��/��\��\0 (���ַ�)��
			Ӧȫ��Сд��
			���64�ֽڡ�
		��һЩ���������ݿ�
			admin�����û���������⣬����û����Լ̳��������ݿ��Ȩ�ޡ�
			local�����ݲ��ᱻ���ƣ������洢���ص�̨���������κμ��ϡ�
			config����Ƭʱ�������Ƭ�������Ϣ�����ڲ�ʹ��
	1.2 �ĵ�
		���壺�ĵ���һ���ֵ�ԡ�����Ҫ������ͬ���ֶΣ���������Ҳ����Ҫһ��
		ע�⣺1.�ĵ��еļ�ֵ��������ġ�
				2.�ĵ��е�ֵ������������˫����������ַ����������������������������ͣ���������������Ƕ����ĵ�)��
				3.MongoDB�������ͺʹ�Сд��
				4.MongoDB���ĵ��������ظ��ļ���
				5.�ĵ��ļ����ַ����������������������������ʹ������UTF-8�ַ���
		�ĵ��������淶��
			�����ܺ���\0 (���ַ�)������ַ�������ʾ���Ľ�β��
			��.���͡�$�����ر�����壬ֻ�����ض������²���ʹ�á�
			���»���"_"��ͷ�ļ��Ǳ�����(�����ϸ�Ҫ���)��
	1.3 ����
		���壺���Ͼ���MongoDb���ĵ��飬���������ݿ�ı����ϵ�����û�й̶��ṹ��
		�����������淶��
			1 �����������ǿ��ַ���""��
			2 ���������ܺ���\0�ַ������ַ�)������ַ���ʾ�������Ľ�β��
			3 ������������"system."��ͷ������Ϊϵͳ���ϱ�����ǰ׺��
			4 �û������ļ������ֲ��ܺ��б����ַ�����Щ���������ȷ֧���ڼ��������������������ΪĳЩϵͳ���ɵļ����а������ַ���������Ҫ��������ϵͳ�����ļ��ϣ�����ǧ��Ҫ����������֡�$����
		capped collections
			���壺Capped collections ���ǹ̶���С��collection�����кܸߵ������Լ����й��ڵ�����(���ڰ��ղ����˳��)�����ǳ��ʺ����Ƽ�¼��־�Ĺ��ܡ��ͱ�׼��collection��ͬ�������Ҫ��ʽ�Ĵ���һ��capped collection�� ָ��һ��collection�Ĵ�С����λ���ֽڡ�collection�����ݴ洢�ռ�ֵ��ǰ����ġ�
			���磺db.createCollection("mycoll", {capped:true, size:100000})
			ע�⣺
				1 ��capped collection�У���������µĶ���
				2 �ܽ��и��£�Ȼ�������󲻻����Ӵ洢�ռ䡣������ӣ����¾ͻ�ʧ�� ��
				3 ���ݿⲻ�������ɾ����ʹ��drop()����ɾ��collection���е��С�
				4 ע��: ɾ��֮���������ʽ�����´������collection��
				5 ��32bit�����У�capped collection���洢Ϊ1e9( 1X109)���ֽڡ�
		1.4 Ԫ����
			���壺













ָ�
	1 ���ݿ����
		�������ݿ⣺
		use data_name;
		������ݿⲻ���ڣ��򴴽����ݿ⣬�����л���ָ�����ݿ⡣

		�鿴Ŀǰ�õ����ݿ���
		db

		��ʾ���е����ݿ�
		show dbs
		ע�⣺ֻ�в������ݵ����ݿ�Ż���ʾ

		ɾ�����ݿ⣺
		db.dropDatabase()
		�ɹ��᷵�أ�{"dropped":"runoob","ok":1}

		����û���
		db.createUser({
			... user:"root",
			... pwd:"bobo",
			... roles:[{role:"dbOwner",db:"wenzailong"}]})


	2 ���ϲ���
		�������ϣ�
		db.createCollection(name,options);
		name:������
		options����ѡ�������������ü�����Ϣ

		��ʾ���ϣ�
		show collection��
		show tables

		ɾ�����ϣ�
		db.collection_name.drop();
		�ɹ����أ�true

		�ڲ���ʱֱ�Ӵ������ϣ�
		db.lihongbo.insert({"name:"bobojiang"})



	3 �ĵ�����
		�����ĵ� insert
		db.COLLECTION_NAME.insert(JSONOBJ);
		COLLECTION_NAME:������
		JSONOBJ��Ҫ�����JSON����

		��ѯ�����е��ĵ� find
		db.COLLECTION_NAME.find();
		COLLECTION_NAME:������

		�����ĵ���update
		db.COLLECTION_NAME.update(
		   <query>,
		   <update>,
		   {
			 upsert: <boolean>,
			 multi: <boolean>,
			 writeConcern: <document>
		   }
		)	
		query : update�Ĳ�ѯ����������sql update��ѯ��where����ġ�
		update : update�Ķ����һЩ���µĲ���������$,$inc...���ȣ�Ҳ�������Ϊsql update��ѯ��set�����
		upsert : ��ѡ�������������˼�ǣ����������update�ļ�¼���Ƿ����objNew,trueΪ���룬Ĭ����false�������롣
		multi : ��ѡ��mongodb Ĭ����false,ֻ�����ҵ��ĵ�һ����¼������������Ϊtrue,�ͰѰ����������������¼ȫ�����¡�
		writeConcern :��ѡ���׳��쳣�ļ���
		���ӣ�db.col.update({'title':'MongoDB �̳�'},{$set:{'title':'MongoDB'}})

		�����ĵ���save
		db.COLLECTION_NAME.save(
		   <document>,
		   {
			 writeConcern: <document>
		   }
		)
		document : �ĵ����ݡ�
		writeConcern :��ѡ���׳��쳣�ļ���

		�鿴�ĵ���
		db.collection_name.find()
		db.collection_name.find().pretty()

		ֻ���µ�һ����¼��
		db.col.update( { "count" : { $gt : 1 } } , { $set : { "test2" : "OK"} } );

		ȫ�����£�
		db.col.update( { "count" : { $gt : 3 } } , { $set : { "test2" : "OK"} },false,true );

		ֻ��ӵ�һ����
		db.col.update( { "count" : { $gt : 4 } } , { $set : { "test5" : "OK"} },true,false );

		ȫ����Ӽӽ�ȥ:
		db.col.update( { "count" : { $gt : 5 } } , { $set : { "test5" : "OK"} },true,true );

		ȫ�����£�
		db.col.update( { "count" : { $gt : 15 } } , { $inc : { "count" : 1} },false,true );

		ֻ���µ�һ����¼��
		db.col.update( { "count" : { $gt : 10 } } , { $inc : { "count" : 1} },false,false );

		���������¼��
		db.lihongbo.insertMany([{"name":"a","age":3},{"name":"b","age":2},{"name":"d","age":3},{"name":"c","age":2}]);

	4 ɾ���ĵ� remove
		db.collection.remove(
		   <query>,
		   {
			 justOne: <boolean>,
			 writeConcern: <document>
		   }
		)
		query :����ѡ��ɾ�����ĵ���������
		justOne : ����ѡ�������Ϊ true �� 1����ֻɾ��һ���ĵ���
		writeConcern :����ѡ���׳��쳣�ļ���
		���ӣ�db.collection_name.remove({'title':'MongoDB �̳�'})
		
		ɾ����������
		db.collection_name.remove({})
	
	5 ��ѯ�ĵ�
		db.collection.find(query, projection)
		query ����ѡ��ʹ�ò�ѯ������ָ����ѯ����
		projection ����ѡ��ʹ��ͶӰ������ָ�����صļ�����ѯʱ�����ĵ������м�ֵ�� ֻ��ʡ�Ըò������ɣ�Ĭ��ʡ�ԣ���
		
		���������ĵ�
		db.col_name.find().pretty()
		pretty() �����Ը�ʽ���ķ�ʽ����ʾ�����ĵ���

		ֻ����һ���ĵ���
		db.col_name.findOne().pretty()

		����	{<key>:<value>}	db.col.find({"by":"����̳�"}).pretty()	
		С��	{<key>:{$lt:<value>}}	db.col.find({"likes":{$lt:50}}).pretty()	
		С�ڻ����	{<key>:{$lte:<value>}}	db.col.find({"likes":{$lte:50}}).pretty()	
		����	{<key>:{$gt:<value>}}	db.col.find({"likes":{$gt:50}}).pretty()	
		���ڻ����	{<key>:{$gte:<value>}}	db.col.find({"likes":{$gte:50}}).pretty()	
		������	{<key>:{$ne:<value>}}	db.col.find({"likes":{$ne:50}}).pretty()	

		and����
		db.col_name.find({key1:value1, key2:value2}).pretty()
		���ӣ�db.lihongbo.find({name:"a"},{age:3}).pretty()

		or����
		db.col_name.find({$or: [{key1: value1}, {key2:value2}]}).pretty()
		���ӣ�db.lihongbo.find({$or:[{name:"a"},{age:2}]}).pretty()

		and��or����
		db.lihongbo.find({"age":{$gte:2},$or:[{"name":"a"},{"name":"c"}]}).pretty()

		projection����ʹ�ã�ָ�������У�
		db.collection.find(query, {title: 1, by: 1}) // 1-inclusionģʽ ָ�����صļ���������������
		db.collection.find(query, {title: 0, by: 0}) // 0-exclusionģʽ ָ�������صļ�,����������

	6 ����������
		(>) ���� - $gt
		(<) С�� - $lt
		(>=) ���ڵ��� - $gte
		(<= ) С�ڵ��� - $lte
		�������ݣ�
		db.col.insertMany([{
			title: 'PHP �̳�', 
			description: 'PHP ��һ�ִ�����̬������վ���ǿ�����ķ������˽ű����ԡ�',
			by: '����̳�',
			url: 'http://www.runoob.com',
			tags: ['php'],
			likes: 200
		},{title: 'Java �̳�', 
			description: 'Java ����Sun Microsystems��˾��1995��5���Ƴ��ĸ߼�����������ԡ�',
			by: '����̳�',
			url: 'http://www.runoob.com',
			tags: ['java'],
			likes: 150
		},{title: 'MongoDB �̳�', 
			description: 'MongoDB ��һ�� Nosql ���ݿ�',
			by: '����̳�',
			url: 'http://www.runoob.com',
			tags: ['mongodb'],
			likes: 100
		}])

		���ӣ�����100��С��150
			db.col.find({"likes":{$gt:100,$lt:150}}).pretty()

	7 $type������
		typeƥ���ֶ����͡�
		����    ����
		Double	1	 
		String	2	 
		Object	3	 
		Array	4	 
		Binary data	5	 
		Undefined	6	�ѷ�����
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
		
	���ӣ�db.col.find({title:{$type:2}})//��ѯtitle�������ַ�����

	8 limit��skip����
		limit����ȡָ������������
			db.col.find().limit(1)//��ѯ��һ����¼
		skip������ָ����������
			db.col.find().skip(1)//��ѯ��һ����¼������м�¼

	9 ����
		sort({fild_name,num})������ num=1������-1�ǽ���
		���ӣ�db.col.find().sort({likes:-1})

	10 ����
		db.collection_name.createIndex(keys,options)����������
		keys:����Ҫ�������ֶ� ��ֵ����1���ǰ����򴴽�������-1���ǰ����򴴽�����
		options:���ԼӺܶ�����
			background	Boolean	���������̻������������ݿ������background��ָ���Ժ�̨��ʽ���������������� "background" ��ѡ������ "background" Ĭ��ֵΪfalse��
			unique	Boolean	�����������Ƿ�Ψһ��ָ��Ϊtrue����Ψһ������Ĭ��ֵΪfalse.
			name	string	���������ơ����δָ����MongoDB��ͨ�������������ֶ���������˳������һ���������ơ�
			dropDups	Boolean	�ڽ���Ψһ����ʱ�Ƿ�ɾ���ظ���¼,ָ�� true ����Ψһ������Ĭ��ֵΪ false.
			sparse	Boolean	���ĵ��в����ڵ��ֶ����ݲ��������������������Ҫ�ر�ע�⣬�������Ϊtrue�Ļ����������ֶ��в����ѯ����������Ӧ�ֶε��ĵ�.��Ĭ��ֵΪ false.
			expireAfterSeconds	integer	ָ��һ������Ϊ��λ����ֵ����� TTL�趨���趨���ϵ�����ʱ�䡣
			v	index version	�����İ汾�š�Ĭ�ϵ������汾ȡ����mongod��������ʱ���еİ汾��
			weights	document	����Ȩ��ֵ����ֵ�� 1 �� 99,999 ֮�䣬��ʾ��������������������ֶεĵ÷�Ȩ�ء�
			default_language	string	�����ı��������ò���������ͣ�ôʼ��ʸɺʹ����Ĺ�����б� Ĭ��ΪӢ��
			language_override	string	�����ı��������ò���ָ���˰������ĵ��е��ֶ��������Ը���Ĭ�ϵ�language��Ĭ��ֵΪ language.
	11 �ۺ�
		��Ҫ�Ǵ������ݣ�������ƽ��ֵ�����

		aggregate()
		���ӣ�db.col.aggregate([{$group:{_id : "$title", num_count : {$sum : 1}}}])
		�൱�ڣ�select by_user, count(*) from mycol group by by_user

		$sum	�����ܺ͡�	db.mycol.aggregate([{$group : {_id : "$by_user", num_tutorial : {$sum : "$likes"}}}])
		$avg	����ƽ��ֵ	db.mycol.aggregate([{$group : {_id : "$by_user", num_tutorial : {$avg : "$likes"}}}])
		$min	��ȡ�����������ĵ���Ӧֵ����Сֵ��	db.mycol.aggregate([{$group : {_id : "$by_user", num_tutorial : {$min : "$likes"}}}])
		$max	��ȡ�����������ĵ���Ӧֵ�����ֵ��	db.mycol.aggregate([{$group : {_id : "$by_user", num_tutorial : {$max : "$likes"}}}])
		$push	�ڽ���ĵ��в���ֵ��һ�������С�	db.mycol.aggregate([{$group : {_id : "$by_user", url : {$push: "$url"}}}])
		$addToSet	�ڽ���ĵ��в���ֵ��һ�������У���������������	db.mycol.aggregate([{$group : {_id : "$by_user", url : {$addToSet : "$url"}}}])
		$first	������Դ�ĵ��������ȡ��һ���ĵ����ݡ�	db.mycol.aggregate([{$group : {_id : "$by_user", first_url : {$first : "$url"}}}])
		$last	������Դ�ĵ��������ȡ���һ���ĵ�����	db.mycol.aggregate([{$group : {_id : "$by_user", last_url : {$last : "$url"}}}])

		�ܵ���
			�ܵ�һ�����ڽ���ǰ�������������Ϊ��һ������Ĳ�����
			$project���޸������ĵ��Ľṹ���������������������ӻ�ɾ����Ҳ�������ڴ����������Լ�Ƕ���ĵ���
			$match�����ڹ������ݣ�ֻ��������������ĵ���$matchʹ��MongoDB�ı�׼��ѯ������
			$limit����������MongoDB�ۺϹܵ����ص��ĵ�����
			$skip���ھۺϹܵ�������ָ���������ĵ������������µ��ĵ���
			$unwind�����ĵ��е�ĳһ�����������ֶβ�ֳɶ�����ÿ�����������е�һ��ֵ��
			$group���������е��ĵ����飬������ͳ�ƽ����
			$sort���������ĵ�����������
			$geoNear������ӽ�ĳһ����λ�õ������ĵ���
	
			��ѯ�����ֻ��title�ֶΣ��������0�����ǲ�ѯ��title��������ֶ�		
			db.col.aggregate({$project:{title:1}})

	12 ����
		mongoDB����������Ҫ�����ڵ㣬һ�������ڵ㣬������ͻ����������඼�Ǵӽڵ㣬���������ڵ��ϵ����ݡ��������䷽ʽ���ǣ�һ��һ�ӣ�һ�����
	
	13 ��Ƭ����

	14 ������ָ�
		mongodump -h dbhost -d dbname -o dbdirectory
			-h��
			MongDB���ڷ�������ַ�����磺127.0.0.1����ȻҲ����ָ���˿ںţ�127.0.0.1:27017

			-d��
			��Ҫ���ݵ����ݿ�ʵ�������磺test

			-o��
			���ݵ����ݴ��λ�ã����磺c:\data\dump����Ȼ��Ŀ¼��Ҫ��ǰ�������ڱ�����ɺ�ϵͳ�Զ���dumpĿ¼�½���һ��testĿ¼�����Ŀ¼�����Ÿ����ݿ�ʵ���ı������ݡ�

		mongorestore -h <hostname><:port> -d dbname <path>
		--host <:port>, -h <:port>��
			MongoDB���ڷ�������ַ��Ĭ��Ϊ�� localhost:27017

			--db , -d ��
			��Ҫ�ָ������ݿ�ʵ�������磺test����Ȼ�������Ҳ���Ժͱ���ʱ��Ĳ�һ��������test2

			--drop��
			�ָ���ʱ����ɾ����ǰ���ݣ�Ȼ��ָ����ݵ����ݡ�����˵���ָ��󣬱��ݺ�����޸ĵ����ݶ��ᱻɾ��������Ŷ��

			<path>��
			mongorestore ����һ�����������ñ�����������λ�ã����磺c:\data\dump\test��

			�㲻��ͬʱָ�� <path> �� --dir ѡ�--dirҲ�������ñ���Ŀ¼��

			--dir��
			ָ�����ݵ�Ŀ¼

			�㲻��ͬʱָ�� <path> �� --dir ѡ�

	15 ���
		mongostat
			��mongoDB/binĿ¼�£�����mongostat����ʾÿ���̶����mongoDB������ ״̬
		mongotop
			����һ��mongoDB��ʵ�����鿴��Щ����ʱ�����ڶ�ȡ��д�����ݡ�
			mongotop 10��û10����һ��
			mongotop --locks������ÿ�������ݿ�����ʹ����























