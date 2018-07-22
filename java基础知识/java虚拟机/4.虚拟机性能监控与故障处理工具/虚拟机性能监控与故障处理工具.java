4 虚拟机性能监控与故障处理工具
4.1 概述
4.2 JDK的命令行工具
	JDK的命令行工具就是JDK目录中的“java.exe”和“javac.exe”这两个命令工具。
4.2.1 jps：虚拟机进程状况工具
	功能和Unix的ps命令类似，可列出正在运行的虚拟机进程，并显示虚拟机执行主类(Main Class，main()函数所在的类)的名称，以及这些经常的本地虚拟机的唯一ID。对于本地虚拟机进程来说，LVMID与操作系统的进程ID是一致的。使用windows的任务管理器或Unix的ps命令也可以查询到虚拟机进程的LVMID，但如果同时启动了多个虚拟进程，无法根据进程名称定位时，那就只能依赖jps命令显示主类的功能才能区分了。
	Jps命令格式：jps [option] [hostid]
	jps可以通过RMI协议查询开启了RMI服务的远程虚拟机进程状态，hostid为RMI注册表中注册的主机名。
4.2.2 jstat：虚拟机统计信息见识工具
	jstat是用于监视虚拟机各种运行状态信息的命令行工具。它可以显示本地或远程中的类加载、内存、垃圾收集、jit编译等运行数据，它是运行期定位虚拟机性能问题的首选工具。
	命令格式：jstat [ option vmid [ interval[s|ms][count] ] ]
	如果是本地虚拟机进程，VMID与LVMID是一致的，如果是远程虚拟机进程，那VMID的格式应当是：
	[protocol:][//]lvmid[@hostname[:port]/servername]。
	参数interval和count代表查询间隔和次数，如果省略这两个参数，说明只查询一次。
	例如：假设需要每250毫秒查询一次进程2764垃圾收集的状况，一共查询20次，那命令应当是：jstat -gc 2764 250 20
	选项option代表用户希望查询的虚拟机信息，主要分为三类：类装载、垃圾收集和运行编译状况。
4.2.3 jinfo:java配置信息工具
	jinfo的作用的实时的查看和调整虚拟机的各项参数。
	命令格式：jinfo [option] pid
	例子：
	jinfo -flag CMSInitiatingOccupancyFraction 26720//查看26720java进程的CMSInitiatingOccupancyFraction参数
	jinfo -sysprops 26720//把虚拟机进程的System.getProperties()内容打印出来
4.2.4 jmap:java内存影像工具
	jmap命令用于生成堆转储快照(一般称为heapdump或dump文件)。
	还可以查询finalize执行队列，Java堆和永久代的详细信息，如空间使用率、当前使用的是那种收集器等。
	命令格式：jmap [option] vmid
4.2.5 jhat虚拟机堆转储快照分析工具
	来分析jmap生成的堆转储快照。
	jhat eclipse.bin
	一般不用这个：原因有两个,1：一般不会在不熟应用程序的服务器上直接分析dump文件 。2:分析工作很耗时。
4.2.6 jstack:java堆栈跟踪工具
	jstack命令用于生成虚拟机当前时刻的线程快照(一般称为threaddump或javacore文件)。线程快照就是当前虚拟机内每一条线程正在执行的方法堆栈的集合，生成线程快照的主要目的是定位线程出现长时间停顿的原因。
	命令格式：jstack [option] vmid
4.3 JDK可视化工具
	JDK中除了提供大量的命令行工具外，还有两个功能强大的可视化工具：JConsole和VisualVm。
4.3.1 JConsole:Java监视与管理控制台
	JConsole是一款基于JMX的可视化监视和管理的工具。它管理部分的功能是针对JMX MBean进行管理。
	1.启动JConsole
		运行JDK/bin目录下的“jconsole.exe”启动JConsole后，会自动搜索本级的所有虚拟机进程。
	2.内存监控
		用于监视受收集器福安里的虚拟机内存(Java堆和永久代)的变化趋势。
	3.线程监控
		遇到线程停顿的时候可以使用这个页签进行监控分析。
4.3.2 VisualVM:多合一鼓掌处理工具
	VisualVm是目前为止，JDK发布的功能最强大的运行监视和鼓掌处理程序。可以加入各种插件。
	运行jdk/bin文件下的jvisualvm.exe。
	1.VisualVm兼容范围与插件安装
	2.生成和浏览堆转储快照
	3.分析程序性能
	4.BTrace动态日志跟踪