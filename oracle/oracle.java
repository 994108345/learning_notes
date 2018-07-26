??:
create sequence SEQ_mbwms_stk_Adjust_id
minvalue 1
maxvalue 99999999999999999999
start with 1
increment by 1
cache 20;

nvl??:
select l.loc_prop,nvl(l.description,'11') from SF_WAREHOUSE_LOC l;