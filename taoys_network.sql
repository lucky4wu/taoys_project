drop sequence taoys_seq_ping;

create sequence taoys_seq_ping
	start with 1
	increment by 1
	nocache
	nocycle
	nomaxvalue;


drop table taoys_ping;

create table taoys_ping(
	id number(9),
	uname varchar2(20) not null,
	email varchar2(40) not null,
	ipAddress varchar2(4000) not null,
	timeOut varchar2(20),
	pingTimes varchar2(9),
	createTime varchar2(20) not null,
	constraint taoys_ping_id_pk primary key(id)
);

insert into taoys_ping value(id,uname,email,ipAddress,timeOut,pingTimes,createTime) 
	values(taoys_seq_ping.nextval,'taoyisong','805394425@qq.com', 'www.baidu.com', '1000', '4', to_char(sysdate, 'YYYY-MM-DD HH24:MI:SS'));
	 
-- 20150125 update	
alter table taoys_ping add (status varchar2(20));
	
insert into taoys_ping value(id,uname,email,ipAddress,timeOut,pingTimes,createTime, status) 
	values(taoys_seq_ping.nextval,'taoyisong','805394425@qq.com', 'www.baidu.com', '1000', '4', to_char(sysdate, 'YYYY-MM-DD HH24:MI:SS'), '1');
	 
commit;

