create table sys_permission  (
    id varchar(32) not null,
    permission_code varchar(100) not null,
    permission_name varchar(200) not null,
    parent_permission_code varchar(100),
    url varchar(500),
    icon varchar(50),
    hide integer,
    lev integer,
    sort integer,
    create_time timestamp not null,
    create_by varchar(32),
    primary key (id)
);

create table sys_role  (
    id varchar(32) not null,
    role_code varchar(100) not null,
    role_name varchar(100) not null,
    create_time timestamp not null,
    create_by varchar(32),
    primary key (id)
);

create table sys_role_permission  (
    id varchar(32) not null,
    role_id varchar(32) not null,
    permission_id varchar(32) not null,
    create_time timestamp not null,
    create_by varchar(32),
    primary key (id)
);

create table sys_user  (
    id varchar(32) not null,
    username varchar(50) not null,
    password varchar(100) not null,
    salt varchar(50) not null,
    real_name varchar(50),
    email varchar(100),
    status integer not null,
    create_time timestamp not null,
    create_by varchar(32),
    update_time timestamp,
    update_by varchar(32),
    last_login_time timestamp,
    primary key (id)
);

create table sys_user_role  (
    id varchar(32) not null,
    user_id varchar(32) not null,
    role_id varchar(32) not null,
    create_time timestamp not null,
    create_by varchar(32)
);

select * from sys_permission;
select * from sys_role;
select * from sys_role_permission;
select * from sys_user;
select * from sys_user_role;

/*
drop table sys_permission;
drop table sys_role;
drop table sys_role_permission;
drop table sys_user;
drop table sys_user_role;

create table sys_log  (
    id varchar(32) not null ,
    username varchar(100) null default null ,
    ip_address varchar(100) not null ,
    ip_source varchar(100) not null ,
    message varchar(100) null default null ,
    browser_name varchar(100) null default null ,
    system_name varchar(100) not null ,
    create_time timestamp not null ,
    primary key (id)
);
*/

