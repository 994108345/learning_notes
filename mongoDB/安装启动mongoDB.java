1 下载mongoDB
	官网下载很麻烦，直接找这个网站下http://dl.mongodb.org/dl/win32/x86_64。
2 启动mongoDB
启动有两种方式
	2.1 命令行启动mongoDB
	首先创建你自己想放mongoDB数据文件的文件夹，文件夹的路径例：c:\data\db
	通过cmd来到mongoDB安装目录中的bin目录下，
	运行该指令：mongod --dbpath c:\data\db//启动mongoDB服务器
会输出:
2018-06-28T14:41:15.231+0800 I CONTROL  [initandlisten] MongoDB starting : pid=49788 port=27017 dbpath=G:\code\mongoDB-db 64-bit host=DESKTOP-8ILQV2H
2018-06-28T14:41:15.231+0800 I CONTROL  [initandlisten] targetMinOS: Windows Vista/Windows Server 2008
2018-06-28T14:41:15.231+0800 I CONTROL  [initandlisten] db version v3.4.15-58-g7240c4719f
2018-06-28T14:41:15.231+0800 I CONTROL  [initandlisten] git version: 7240c4719f9b68955225d9abb2e5eb10bf8c0227
2018-06-28T14:41:15.231+0800 I CONTROL  [initandlisten] allocator: tcmalloc
2018-06-28T14:41:15.232+0800 I CONTROL  [initandlisten] modules: none
2018-06-28T14:41:15.232+0800 I CONTROL  [initandlisten] build environment:
2018-06-28T14:41:15.233+0800 I CONTROL  [initandlisten]     distarch: x86_64
2018-06-28T14:41:15.233+0800 I CONTROL  [initandlisten]     target_arch: x86_64
2018-06-28T14:41:15.235+0800 I CONTROL  [initandlisten] options: { storage: { dbPath: "G:\code\mongoDB-db" } }
2018-06-28T14:41:15.250+0800 I STORAGE  [initandlisten] wiredtiger_open config: create,cache_size=5576M,session_max=20000,eviction=(threads_min=4,threads_max=4),config_base=false,statistics=(fast),log=(enabled=true,archive=true,path=journal,compressor=snappy),file_manager=(close_idle_time=100000),checkpoint=(wait=60,log_size=2GB),statistics_log=(wait=0),verbose=(recovery_progress),
2018-06-28T14:41:16.793+0800 I CONTROL  [initandlisten]
2018-06-28T14:41:16.793+0800 I CONTROL  [initandlisten] ** WARNING: Access control is not enabled for the database.
2018-06-28T14:41:16.795+0800 I CONTROL  [initandlisten] **          Read and write access to data and configuration is unrestricted.
2018-06-28T14:41:16.796+0800 I CONTROL  [initandlisten]
2018-06-28T14:41:18.215+0800 I FTDC     [initandlisten] Initializing full-time diagnostic data capture with directory 'G:/code/mongoDB-db/diagnostic.data'
2018-06-28T14:41:18.590+0800 I INDEX    [initandlisten] build index on: admin.system.version properties: { v: 2, key: { version: 1 }, name: "incompatible_with_version_32", ns: "admin.system.version" }
2018-06-28T14:41:18.590+0800 I INDEX    [initandlisten]          building index using bulk method; build may temporarily use up to 500 megabytes of RAM
2018-06-28T14:41:18.617+0800 I INDEX    [initandlisten] build index done.  scanned 0 total records. 0 secs
2018-06-28T14:41:18.618+0800 I COMMAND  [initandlisten] setting featureCompatibilityVersion to 3.4
2018-06-28T14:41:18.626+0800 I NETWORK  [thread1] waiting for connections on port 27017
2018-06-28T14:42:11.983+0800 I NETWORK  [thread1] connection accepted from 127.0.0.1:51063 #1 (1 connection now open)
2018-06-28T14:42:11.986+0800 I NETWORK  [conn1] received client metadata from 127.0.0.1:51063 conn1: { application: { name: "MongoDB Shell" }, driver: { name: "MongoDB Internal Client", version: "3.4.15-58-g7240c4719f" }, os: { type: "Windows", name: "Microsoft Windows 8", architecture: "x86_64", version: "6.2 (build 9200)" } }
运行bin目录下的mongo.exe//运行mongoDB
会输出：
MongoDB shell version v3.4.15-58-g7240c4719f
connecting to: mongodb://127.0.0.1:27017
MongoDB server version: 3.4.15-58-g7240c4719f
Welcome to the MongoDB shell.
For interactive help, type "help".
For more comprehensive documentation, see
        http://docs.mongodb.org/
Questions? Try the support group
        http://groups.google.com/group/mongodb-user
Server has startup warnings:
2018-06-28T14:41:16.793+0800 I CONTROL  [initandlisten]
2018-06-28T14:41:16.793+0800 I CONTROL  [initandlisten] ** WARNING: Access control is not enabled for the database.
2018-06-28T14:41:16.795+0800 I CONTROL  [initandlisten] **          Read and write access to data and configuration is unrestricted.
2018-06-28T14:41:16.796+0800 I CONTROL  [initandlisten]
>
这样就启动成功了
	2.2 配置文件启动mongoDB服务
	新建连个目录，一个放日志文件，一个放数据
	例如
		c:\data\db
		c:\data\log
	再在bin下面创建一个配置文件mongod.cfg，文件内容如下：
		systemLog:
			destination: file
			path: c:\data\log\mongod.log
		storage:
			dbPath: c:\data\db
	最后安装MongoDB服务
	C:\mongodb\bin\mongod.exe --config "C:\mongodb\mongod.cfg" --install
	移除MongoDB服务：C:\mongodb\bin\mongod.exe --remove

3 简单的插入和读取
	//在runoob中插入10
	db.runoob.insert({x:10})
	//读取
	db.runoob.find()