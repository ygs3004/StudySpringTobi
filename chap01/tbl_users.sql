create table users(
    id varchar2(10) primary key,
    name varchar2(20) not null,
    password varchar2(10) not null
);

commit;

drop table users;