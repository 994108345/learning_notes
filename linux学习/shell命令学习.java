1:shell变量
	1:输出hello world
		echo "hello world"

	2:定义变量
		name="wenzailong"

	3: 查看变量
		echo ${name}

	4.设置只读变量
		readonly name

	5.删除变量
		unset name
		注意:不能删除只读变量

	6:单引号：.单引号里的任何字符都会原样输出，单引号字符串中的变量是无效的；
			.单引号字串中不能出现单引号（对单引号使用转义符后也不行）。

	7:双引号：.双引号里可以有变量
			.双引号里可以出现转义字符

	1.1 字符串
		8:拼接字符串：
			join="a"${age}"a"
			直接拼就可以了

		9:获取字符串长度
			echo ${#age}
			
		10.提取字符串
			echo ${age:1:2}

		11:查找字符串
			echo `expr index "${string}" ab`
			注意：以上的查询字符a或b的位置！

	1.2 数组
		1.2.1 定义数组
			arr1=(1 2 3 4)
			也可以一个一个设置
			arr1[0]=1
			arr1[1]=a
			arr1[2]=s
			数组的下标范围没有限制

		1.2.2 读取数组
			${arr1[2]}

			读取所有的值
			echo ${arr1[@]}

		1.2.3 获取数组的长度
			
	1.3 注释
			加#
2.传递参数
	2.1 为脚本设置可执行权限，并执行脚本
		$ chmod +x test.sh 
		$ ./test.sh 1 2 3
		脚本内容为：
		echo "Shell 传递参数实例！";
		echo "执行的文件名：$0";
		echo "第一个参数为：$1";
		echo "第二个参数为：$2";
		echo "第三个参数为：$3";
		$0是运行脚本的文件名，$1 $2 $3就是你要传入的参数！
	2.2 其他参数的
		$#	传递到脚本的参数个数
		$*	以一个单字符串显示所有向脚本传递的参数。如"$*"用「"」括起来的情况、以"$1 $2 … $n"的形式输出所有参数。
		$$	脚本运行的当前进程ID号
		$!	后台运行的最后一个进程的ID号
		$@	与$*相同，但是使用时加引号，并在引号中返回每个参数。如"$@"用「"」括起来的情况、以"$1" "$2" … "$n" 的形式输出所有参数。
		$-	显示Shell使用的当前选项，与set命令功能相同。
		$?	显示最后命令的退出状态。0表示没有错误，其他任何值表明有错误。

3 运算符
	3.1 + 
		num=`expr 2 + 2`
		注意：表达式和运算符之间要有空格
			乘号(*)前边必须加反斜杠(\)才能实现乘法运算；
			其他类似

	3.2 关系运算符
		注意：关系运算符只支持数字，不支持字符串，除非字符串的值是数字。
		-eq	检测两个数是否相等，相等返回 true。	[ $a -eq $b ] 返回 false。
		-ne	检测两个数是否不相等，不相等返回 true。	[ $a -ne $b ] 返回 true。
		-gt	检测左边的数是否大于右边的，如果是，则返回 true。	[ $a -gt $b ] 返回 false。
		-lt	检测左边的数是否小于右边的，如果是，则返回 true。	[ $a -lt $b ] 返回 true。
		-ge	检测左边的数是否大于等于右边的，如果是，则返回 true。	[ $a -ge $b ] 返回 false。
		-le	检测左边的数是否小于等于右边的，如果是，则返回 true。	[ $a -le $b ] 返回 true。
		
	3.3 布尔运算符
		!	非运算，表达式为 true 则返回 false，否则返回 true。	[ ! false ] 返回 true。
		-o	或运算，有一个表达式为 true 则返回 true。	[ $a -lt 20 -o $b -gt 100 ] 返回 true。
		-a	与运算，两个表达式都为 true 才返回 true。	[ $a -lt 20 -a $b -gt 100 ] 返回 false。
	3.3 逻辑运算符
		&&	逻辑的 AND	[[ $a -lt 100 && $b -gt 100 ]] 返回 false
		||	逻辑的 OR	[[ $a -lt 100 || $b -gt 100 ]] 返回 true
	3.4 字符串运算符
		=	检测两个字符串是否相等，相等返回 true。	[ $a = $b ] 返回 false。
		!=	检测两个字符串是否相等，不相等返回 true。	[ $a != $b ] 返回 true。
		-z	检测字符串长度是否为0，为0返回 true。	[ -z $a ] 返回 false。
		-n	检测字符串长度是否为0，不为0返回 true。	[ -n $a ] 返回 true。
		str	检测字符串是否为空，不为空返回 true。	[ $a ] 返回 true。
	3.5 文件测试运算符
		-b file	检测文件是否是块设备文件，如果是，则返回 true。	[ -b $file ] 返回 false。
		-c file	检测文件是否是字符设备文件，如果是，则返回 true。	[ -c $file ] 返回 false。
		-d file	检测文件是否是目录，如果是，则返回 true。	[ -d $file ] 返回 false。
		-f file	检测文件是否是普通文件（既不是目录，也不是设备文件），如果是，则返回 true。	[ -f $file ] 返回 true。
		-g file	检测文件是否设置了 SGID 位，如果是，则返回 true。	[ -g $file ] 返回 false。
		-k file	检测文件是否设置了粘着位(Sticky Bit)，如果是，则返回 true。	[ -k $file ] 返回 false。
		-p file	检测文件是否是有名管道，如果是，则返回 true。	[ -p $file ] 返回 false。
		-u file	检测文件是否设置了 SUID 位，如果是，则返回 true。	[ -u $file ] 返回 false。
		-r file	检测文件是否可读，如果是，则返回 true。	[ -r $file ] 返回 true。
		-w file	检测文件是否可写，如果是，则返回 true。	[ -w $file ] 返回 true。
		-x file	检测文件是否可执行，如果是，则返回 true。	[ -x $file ] 返回 true。
		-s file	检测文件是否为空（文件大小是否大于0），不为空返回 true。	[ -s $file ] 返回 true。
		-e file	检测文件（包括目录）是否存在，如果是，则返回 true。	[ -e $file ] 返回 true。
		
4 printf命令
	4.1 输出指令 
	printf "woqunidayede" 

	4.2替代符
		printf "%-10s %-8s %-4.2f\n" 郭靖 男 66.1234 
		%s %c %d %f都是格式替代符
		%-10s 指一个宽度为10个字符（-表示左对齐，没有则表示右对齐），任何字符都会被显示在10个字符宽的字符内，如果不足则自动以空格填充，超过也会将内容全部显示出来。
		%-4.2f 指格式化为小数，其中.2指保留2位小数。
	4.3 转义序列
		\a	警告字符，通常为ASCII的BEL字符
		\b	后退
		\c	抑制（不显示）输出结果中任何结尾的换行字符（只在%b格式指示符控制下的参数字符串中有效），而且，任何留在参数里的字符、任何接下来的参数以及任何留在格式字符串中的字符，都被忽略
		\f	换页（formfeed）
		\n	换行
		\r	回车（Carriage return）
		\t	水平制表符
		\v	垂直制表符
		\\	一个字面上的反斜杠字符
		\ddd	表示1到3位数八进制值的字符。仅在格式字符串中有效
		\0ddd	表示1到3位的八进制值字符

5.test命令
	test 命令用于检查某个条件是否成立，它可以进行数值、字符和文件三个方面的测试。
	例如：
	if test $[num1] -eq $[num2]
	then
		echo '两个数相等！'
	else
		echo '两个数不相等！'
	fi
6.流程控制

	6.1 if else
		如果else分支没有语句执行，就不要写这个else。

	6.2 循环条件
		for var in item1 item2 ... itemN
		do
			command1
			command2
			...
			commandN
		done

	6.3 while语句
		while condition
		do
			command
		done

		6.3.1 无限循环
			while :
			do
				command
			done
			或者：
			while true
			do
				command
			done
			或者：
			for (( ; ; ))

	6.4 until循环
		until condition
		do
			command
		done

		until 循环执行一系列命令直至条件为 true 时停止。

	6.5 case 
		case 值 in
		模式1)
			command1
			command2
			...
			commandN
			;;
		模式2）
			command1
			command2
			...
			commandN
			;;
		esac
		case的语法和C family语言差别很大，它需要一个esac（就是case反过来）作为结束标记，每个case分支用右圆括号，用两个分号表示break。
	6.6 跳出循环
		break
		continue
7 函数
	funWithReturn(){
    echo "这个函数会对输入的两个数字进行相加运算..."
    echo "输入第一个数字: "
    read aNum
    echo "输入第二个数字: "
    read anotherNum
    echo "两个数字分别为 $aNum 和 $anotherNum !"
    return $(($aNum+$anotherNum))
}

8.输入/输出重定向
	command > file	将输出重定向到 file。
	command < file	将输入重定向到 file。
	command >> file	将输出以追加的方式重定向到 file。
	n > file	将文件描述符为 n 的文件重定向到 file。
	n >> file	将文件描述符为 n 的文件以追加的方式重定向到 file。
	n >& m	将输出文件 m 和 n 合并。
	n <& m	将输入文件 m 和 n 合并。
	<< tag	将开始标记 tag 和结束标记 tag 之间的内容作为输入。
			 
	需要注意的是文件描述符 0 通常是标准输入（STDIN），1 是标准输出（STDOUT），2 是标准错误输出（STDERR）。
    
	8.1 /dev/null 文件
		/dev/null 是一个特殊的文件，写入到它的内容都会被丢弃；如果尝试从该文件读取内容，那么什么也读不到。但是 /dev/null 文件非常有用，将命令的输出重定向到它，会起到"禁止输出"的效果。
                                                                                                                               
根据进程号查询端口号
	1. ?根据进程pid查端口：
? ? ? ? ? ? ?lsof -i | grep pid
? ? 2. ?根据端口port查进程（某次面试还考过）：
? ? ? ? ? ? lsof ?-i:port ? ??

? ? 3. 根据进程pid查端口：
? ? ? ? ? ?netstat -nap | grep pid
? ? 4. ?根据端口port查进程
? ? ? ? ? ?netstat -nap | grep port
   
100. 文件操作
	100.1 创建文件
		vi wenzailong.sh

	100.2 打开文件
		vi wenzailong.sh

	100.3 编辑指令
		i :在当前位置插入
		a:在当前位置后追加
		o:在当前位置的后面插入一行
		I :在行头插入
		A:在行尾追加
		O:在当前位置的前面插入一行
		'ESC'键从编辑模式转换到阅读模式
		阅读模式（或叫命令模式）下：
		:w 保存文件
		:w filename 保存成filename文件
		:q 退出
		:q! 强行退出
		:w! 强行写
		:wq 保存退出
		:x 同wq
	100.4 在tomcat发布项目
		cd bin：进入bin目录
		./shunt.sh 关闭tomcat 
		如果关闭不了。可以通过 ps aux| greap tomcat 找到该tomcat线程，通过kill -9 thread_num，来杀死线程
		cd webApp：进入发布包所在文件夹
		rm rf package_name*：删除包
		rz：导入我们已经install的程序包
		cd bin：进入bin目录
		./start.sh :运行启动tomcat
	100.5 查看日志信息
		例子：grep -a "库" wms_server_error.log wms_server_error.log.2018-07-16-09
		1．命令格式：

		grep [option] pattern file

		2．命令功能：

		用于过滤/搜索的特定字符。可使用正则表达式能多种命令配合使用，使用上十分灵活。

		3．命令参数：

		-a   --text   #不要忽略二进制的数据。   

		-A<显示行数>   --after-context=<显示行数>   #除了显示符合范本样式的那一列之外，并显示该行之后的内容。   

		-b   --byte-offset   #在显示符合样式的那一行之前，标示出该行第一个字符的编号。   

		-B<显示行数>   --before-context=<显示行数>   #除了显示符合样式的那一行之外，并显示该行之前的内容。   

		-c    --count   #计算符合样式的列数。   

		-C<显示行数>    --context=<显示行数>或-<显示行数>   #除了显示符合样式的那一行之外，并显示该行之前后的内容。   

		-d <动作>      --directories=<动作>   #当指定要查找的是目录而非文件时，必须使用这项参数，否则grep指令将回报信息并停止动作。   

		-e<范本样式>  --regexp=<范本样式>   #指定字符串做为查找文件内容的样式。   

		-E      --extended-regexp   #将样式为延伸的普通表示法来使用。   

		-f<规则文件>  --file=<规则文件>   #指定规则文件，其内容含有一个或多个规则样式，让grep查找符合规则条件的文件内容，格式为每行一个规则样式。   

		-F   --fixed-regexp   #将样式视为固定字符串的列表。   

		-G   --basic-regexp   #将样式视为普通的表示法来使用。   

		-h   --no-filename   #在显示符合样式的那一行之前，不标示该行所属的文件名称。   

		-H   --with-filename   #在显示符合样式的那一行之前，表示该行所属的文件名称。   

		-i    --ignore-case   #忽略字符大小写的差别。   

		-l    --file-with-matches   #列出文件内容符合指定的样式的文件名称。   

		-L   --files-without-match   #列出文件内容不符合指定的样式的文件名称。   

		-n   --line-number   #在显示符合样式的那一行之前，标示出该行的列数编号。   

		-q   --quiet或--silent   #不显示任何信息。   

		-r   --recursive   #此参数的效果和指定“-d recurse”参数相同。   

		-s   --no-messages   #不显示错误信息。   

		-v   --revert-match   #显示不包含匹配文本的所有行。   

		-V   --version   #显示版本信息。   

		-w   --word-regexp   #只显示全字符合的列。   

		-x    --line-regexp   #只显示全列符合的列。   

		-y   #此参数的效果和指定“-i”参数相同。
		
一、vi编辑器有3种基本工作模式
首先需要知道vi编辑器有3种基本工作模式，分别是：命令模式、文本输入模式、和末行模式。

第一：命令行模式：该模式是进入vi编辑器后的默认模式。任何时候，不管用户处于何种模式，按下ESC键即可进入命令模式。在该模式下，用户可以输入vi命令，用户管理自己的文档。此时从键盘上输入的任何字符都被当作编辑命令来解释。若输入的字符是合法的vi命令，则vi在接受用户命令之后完成相应的动作。但需要注意的是，所输入的命令并不回显在屏幕上。若输入的字符不是vi命令，vi会响铃报警。

第二：文本输入模式：在命令模式下输入命令i、附加命令a、打开命令o、修改命令c、取代命令r或替换命令s都可以进入文本输入模式。在该模式下，用户输入的任何字符都被vi当作文件内容保护起来，并将其显示在屏幕上。在文本输入过程中，若想回到命令模式下，按ESC键即可。

第三：末行模式：末行模式也称ex转义模式。在命令模式下，用户按“：”键即可进入末行模式下，此时vi会在显示窗口的最后一行（通常也是屏幕的最后一行）显示一个“：”作为末行模式的说明符，等待用户输入命令。多数文件管理命令都是在此模式下执行的（如把编辑缓冲区的内容写到文件中等）。末行命令在执行完后，vi自动回到命令模式。如果要从命令模式转换到编辑模式，可以键入a或者i。如果需要从文本模式返回，则按ESC即可。在命令模式下输入“:”即可切换到末行模式，然后输入命令。

综上，一般我们使用命令打开文件的时候，是进入到命令模式。在命令模式下，可以切换到文本输入模式和末行模式，但是文本输入模式和末行模式之间是不可以直接相互切换了，因此文本输入模式切换到末行模式，需要先回到命令模式再切换，反之亦然。

此外，编辑文本可以在文本输入模式下，键盘输入对文编进行编辑，或者在命令模式下使用vi命令也是可以起到编辑效果的。

实例演示
首先我们使用命令 vi filename 打开一个文件，这个时候进入到的是命令模式 
接下来我们按i，然后键盘随便输入写内容。 
然后按ESC重新进入到命令模式。 
在命令模式的情况下，我们按:，进入到了末行模式。 
我们输入wq!，然后回车，强行保存退出。 
下次我们再打开对应文件（可用less filename命令打开），即可看到内容已经更改。

补充： 
（1）末行模式下：q! 【强制退出不保存】 q【退出不保存】 wq【退出并保存后面也可以加个！】 
（2）如果你不想保存直接退出有可以在命令模式下使用“ctrl+z”快捷键或者按住“shift”键，输入两个z即可退出。

更多命令
进入vi的命令 
vi filename :打开或新建文件，并将光标置于第一行首 
vi +n filename ：打开文件，并将光标置于第n行首 
vi + filename ：打开文件，并将光标置于最后一行首 
vi +/pattern filename：打开文件，并将光标置于第一个与pattern匹配的串处 
vi -r filename ：在上次正用vi编辑时发生系统崩溃，恢复filename 
vi filename….filename ：打开多个文件，依次进行编辑

屏幕翻滚类命令 
Ctrl+u：向文件首翻半屏 
Ctrl+d：向文件尾翻半屏 
Ctrl+f：向文件尾翻一屏 
Ctrl＋b；向文件首翻一屏 
nz：将第n行滚至屏幕顶部，不指定n时将当前行滚至屏幕顶部。

插入文本类命令 
i ：在光标前 
I ：在当前行首 
a：光标后 
A：在当前行尾 
o：在当前行之下新开一行 
O：在当前行之上新开一行 
r：替换当前字符 
R：替换当前字符及其后的字符，直至按ESC键 
s：从当前光标位置处开始，以输入的文本替代指定数目的字符 
S：删除指定数目的行，并以所输入文本代替之 
ncw或nCW：修改指定数目的字 
nCC：修改指定数目的行

删除命令 
ndw或ndW：删除光标处开始及其后的n-1个字 
do：删至行首 
d$：删至行尾 
ndd：删除当前行及其后n-1行 
x或X：删除一个字符，x删除光标后的，而X删除光标前的 
Ctrl+u：删除输入方式下所输入的文本

搜索及替换命令 
/pattern：从光标开始处向文件尾搜索pattern 
?pattern：从光标开始处向文件首搜索pattern 
n：在同一方向重复上一次搜索命令 
N：在反方向上重复上一次搜索命令 
：s/p1/p2/g：将当前行中所有p1均用p2替代 
：n1,n2s/p1/p2/g：将第n1至n2行中所有p1均用p2替代 
：g/p1/s//p2/g：将文件中所有p1均用p2替换

选项设置 
all：列出所有选项设置情况 
term：设置终端类型 
ignorance：在搜索中忽略大小写 
list：显示制表位(Ctrl+I)和行尾标志（$) 
number：显示行号 
report：显示由面向行的命令修改过的数目 
terse：显示简短的警告信息 
warn：在转到别的文件时若没保存当前文件则显示NO write信息 
nomagic：允许在搜索模式中，使用前面不带“\”的特殊字符 
nowrapscan：禁止vi在搜索到达文件两端时，又从另一端开始 
mesg：允许vi显示其他用户用write写到自己终端上的信息

末行模式命令 
：n1,n2 co n3：将n1行到n2行之间的内容拷贝到第n3行下 
：n1,n2 m n3：将n1行到n2行之间的内容移至到第n3行下 
：n1,n2 d ：将n1行到n2行之间的内容删除 
：w ：保存当前文件 
：e filename：打开文件filename进行编辑 
：x：保存当前文件并退出 
：q：退出vi 
：q!：不保存文件并退出vi 
：!command：执行shell命令command 
：n1,n2 w!command：将文件中n1行至n2行的内容作为command的输入并执行之，若不指定n1，n2，则表示将整个文件内容作为command的输入 
：r!command：将命令command的输出结果放到当前行

寄存器操作 
“?nyy：将当前行及其下n行的内容保存到寄存器？中，其中?为一个字母，n为一个数字 
“?nyw：将当前行及其下n个字保存到寄存器？中，其中?为一个字母，n为一个数字 
“?nyl：将当前行及其下n个字符保存到寄存器？中，其中?为一个字母，n为一个数字 
“?p：取出寄存器？中的内容并将其放到光标位置处。这里？可以是一个字母，也可以是一个数字 
ndd：将当前行及其下共n行文本删除，并将所删内容放到1号删除寄存器中。		
	
top：查看所有进程



解压

　　tar –xvf file.tar //解压 tar包

　　tar -xzvf file.tar.gz //解压tar.gz

　　tar -xjvf file.tar.bz2 //解压 tar.bz2

　　tar –xZvf file.tar.Z //解压tar.Z

　　unrar e file.rar //解压rar

　　unzip file.zip //解压zip

　　总结

　　1、*.tar 用 tar –xvf 解压

　　2、*.gz 用 gzip -d或者gunzip 解压

　　3、*.tar.gz和*.tgz 用 tar –xzf 解压

　　4、*.bz2 用 bzip2 -d或者用bunzip2 解压

　　5、*.tar.bz2用tar –xjf 解压

　　6、*.Z 用 uncompress 解压

　　7、*.tar.Z 用tar –xZf 解压

　　8、*.rar 用 unrar e解压

　　9、*.zip 用 unzip 解压


根据线程pid查看占用的端口号
	 netstat -nap | grep 8223
根据进程号查询项目
	 ps -ef| grep 369

	 
查询文件名：
	find -name 'dump.rdb*'
	
test的时间调整：ntpdate -u asia.pool.ntp.org

	