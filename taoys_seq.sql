drop sequence taoys_seq_user;

create sequence taoys_seq_user
	start with 201412001
	increment by 1
	nocache
	nocycle
	nomaxvalue;


drop table taoys_user;

create table taoys_user(
	id number(9),
	uname varchar2(20) not null,
	realname varchar2(40) not null,
	passwd varchar2(20),
	phone varchar2(14),
	email varchar2(40) not null,
	constraint taoys_user_id_pk primary key(id),
	constraint taoys_user_uname_uk unique(uname),
	constraint taoys_user_email_uk unique(email)
);

insert into taoys_user value(id,uname,realname,passwd,phone,email) 
	values(taoys_seq_user.nextval,'taoyisong','陶轶松','taoyisong','13681979542','805394425@qq.com');
	 
commit;