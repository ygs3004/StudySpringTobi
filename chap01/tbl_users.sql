create table users(
    id varchar2(10) primary key,
    name varchar2(20) not null,
    password varchar2(10) not null,
    lv number(3) not null,
    login number(10) not null,
    recommend number(10) not null
);
alter table users add email VARCHAR2(45) default 'abc@naver.com';
commit;
select * from users;
/*
alter table users add level NUMBER(3) NOT NULL ;
alter table users add login NUMBER(10) NOT NULL ;
alter table users add recommend NUMBER(10) NOT NULL ;
*/

drop table users;