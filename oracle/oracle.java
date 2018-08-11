序列:
create sequence SEQ_mbwms_stk_Adjust_id
minvalue 1
maxvalue 99999999999999999999
start with 1
increment by 1
cache 20;

nvl函数:
select l.loc_prop,nvl(l.description,'11') from SF_WAREHOUSE_LOC l;

merge操作（存在则更新，不存在则插入）
merge into erp_org o1
using (select 'A00016W0241'as code from dual
      union 
      select 'A00016W0242' as code from dual
) o2
on (o1.code = o2.code)
when matched then
  update set o1.name = o2.code
when not matched then
   insert (id,code) values (sys_guid(),o2.code)