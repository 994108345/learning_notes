一、查找重复记录
1、查找全部重复记录
Select * From 表 Where 重复字段 In (Select 重复字段 From 表 Group By 重复字段 Having Count(*)>1)
2、过滤重复记录(只显示一条)
Select * From HZT Where ID In (Select Max(ID) From HZT Group By Title)
二、删除重复记录
1、删除全部重复记录（慎用）
Delete 表 Where 重复字段 In (Select 重复字段 From 表 Group By 重复字段 Having Count(*)>1)
2、保留一条（这个应该是大多数人所需要的 ^_^）
Delete HZT Where ID Not In (Select Max(ID) From HZT Group By Title)
注：此处保留ID最大一条记录
三、举例
1、查找表中多余的重复记录，重复记录是根据单个字段（peopleId）来判断
select * from people where peopleId in (select peopleId from people group by peopleId having count(peopleId) > 1)
2、删除表中多余的重复记录，重复记录是根据单个字段（peopleId）来判断，只留有rowid最小的记录
delete from people where peopleId in (select peopleId from people group by peopleId having count(peopleId) > 1) and rowid not in (select min(rowid) from people group by peopleId having count(peopleId )>1)
3、查找表中多余的重复记录（多个字段）
select * from vitae a where (a.peopleId,a.seq) in (select peopleId,seq from vitae group by peopleId,seq having count(*) > 1)
4、删除表中多余的重复记录（多个字段），只留有rowid最小的记录
delete from vitae a where (a.peopleId,a.seq) in (select peopleId,seq from vitae group by peopleId,seq having count(*) > 1) and rowid not in (select min(rowid) from vitae group by peopleId,seq having count(*)>1)
5、查找表中多余的重复记录（多个字段），不包含rowid最小的记录
select * from vitae a where (a.peopleId,a.seq) in (select peopleId,seq from vitae group by peopleId,seq having

四、查询出的结果去掉除服记录
select distinct u.USER_NAME,u.PASS_WORD from user u;
五、如果空，则返回固定数据
select user_name,pass_word,name,age,IFNULL(sex,1) as sex,ifnull(remark,"11")as remark from user;
mysql没有nvl函数！oracle才有。