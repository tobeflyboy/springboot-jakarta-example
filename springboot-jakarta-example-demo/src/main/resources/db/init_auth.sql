CREATE TABLE sys_log  (
    id varchar(32) NOT NULL ,
    username varchar(100) NULL DEFAULT NULL ,
    ip_address varchar(100) NOT NULL ,
    ip_source varchar(100) NOT NULL ,
    message varchar(100) NULL DEFAULT NULL ,
    browser_name varchar(100) NULL DEFAULT NULL ,
    system_name varchar(100) NOT NULL ,
    create_date timestamp NOT NULL ,
    PRIMARY KEY (id)
);

CREATE TABLE sys_menu  (
     id varchar(32) NOT NULL ,
     parent_id varchar(32) NULL DEFAULT NULL ,
     menu_name varchar(100) NOT NULL ,
     menu_code varchar(50) NOT NULL ,
     menu_href varchar(100) NULL DEFAULT NULL ,
     menu_icon varchar(100) NULL DEFAULT NULL ,
     menu_level varchar(100) NOT NULL ,
     menu_weight interger NOT NULL ,
     is_show char(1) NOT NULL ,
     create_date timestamp NOT NULL ,
     create_by varchar(20) NOT NULL ,
     PRIMARY KEY (id)
);

INSERT INTO sys_menu VALUES ('3bc6a24d1c5d4196b4d7bc3a732d2208', '3e7d54f2bd82464484defcb4709b3142', '登录日志', 'syslog', 'sys_log/list', NULL, '2', '4', '1', '2020-01-13 11:25:29', 'admin');
INSERT INTO sys_menu VALUES ('3c2363839f584216b643e6bd3c05695d', '3e7d54f2bd82464484defcb4709b3142', '用户管理', 'user', 'user/list', '', '2', '1', '1', '2019-12-24 15:04:59', 'admin');
INSERT INTO sys_menu VALUES ('3e7d54f2bd82464484defcb4709b3142', NULL, '系统管理', 'system', NULL, 'layui-icon-home', '1', '0', '1', '2019-12-24 15:02:32', 'admin');
INSERT INTO sys_menu VALUES ('5c2f6c5c80084a99a9d7166ba328bfdd', 'e3c575455f1a4af683b26b3f56a27815', '数据源监控', 'druid', 'druid', NULL, '2', '1', '1', '2019-12-29 20:17:10', 'admin');
INSERT INTO sys_menu VALUES ('7c3195059e954531909f6b31c91826b9', '3e7d54f2bd82464484defcb4709b3142', '项目介绍', 'systemIntroduce', 'system/introduce', NULL, '2', '0', '1', '2020-01-19 16:31:48', 'admin');
INSERT INTO sys_menu VALUES ('893c49dd5fdb44d79bb2897db9472517', '8f1eb86b09354635b3857222d54991d3', 'v-charts图表', 'vCharts', 'devUtils/vCharts', NULL, '2', '1', '1', '2020-01-16 16:16:48', 'admin');
INSERT INTO sys_menu VALUES ('8db930130a1e4b2b9fd479d1f9a4ed45', '3e7d54f2bd82464484defcb4709b3142', '菜单管理', 'menu', 'menu/list', NULL, '2', '2', '1', '2019-12-24 15:07:12', 'admin');
INSERT INTO sys_menu VALUES ('8f1eb86b09354635b3857222d54991d3', NULL, '研发工具', 'devUtils', '', 'layui-icon-util', '1', '3', '1', '2020-01-15 16:33:27', 'admin');
INSERT INTO sys_menu VALUES ('ba90c64234a44202818e10868ab9da91', '8f1eb86b09354635b3857222d54991d3', '菜单图标', 'menuIcon', 'devUtils/menuIcon', NULL, '2', '0', '1', '2020-01-15 16:34:17', 'admin');
INSERT INTO sys_menu VALUES ('be0a8e5ec52c4f0baa2a3edf8194f7f2', 'e3c575455f1a4af683b26b3f56a27815', '服务器监控', 'server', 'system/serverInfo', NULL, '2', '0', '1', '2019-12-27 17:08:56', 'admin');
INSERT INTO sys_menu VALUES ('e3c575455f1a4af683b26b3f56a27815', NULL, '系统监控', 'monitor', NULL, 'layui-icon-engine', '1', '1', '1', '2019-12-24 15:37:04', 'admin');
INSERT INTO sys_menu VALUES ('ed8df2ffe77645cdb7a1b2b10f5d89e4', '3e7d54f2bd82464484defcb4709b3142', '角色管理', 'role', 'role/list', NULL, '2', '1', '1', '2019-12-24 15:08:17', 'admin');
INSERT INTO sys_menu VALUES ('6dd18b967ee14d03b82a094b86473345', NULL, '中台管理', 'management', NULL, 'layui-icon-service', '1', '1', '1', '2019-12-24 15:08:17', 'admin');
INSERT INTO sys_menu VALUES ('17cc729340ac47088e35c5fdb8156bb1', '6dd18b967ee14d03b82a094b86473345', '新闻列表', 'news', 'news/news', NULL, '1', '1', '1', '2019-12-24 15:08:17', 'admin');

CREATE TABLE sys_menu_role  (
    menu_id varchar(32) NOT NULL ,
    role_id varchar(32) NOT NULL
);

INSERT INTO sys_menu_role VALUES ('e3c575455f1a4af683b26b3f56a27815', '811d784a392ad816');
INSERT INTO sys_menu_role VALUES ('3e7d54f2bd82464484defcb4709b3142', '811d784a392ad816');
INSERT INTO sys_menu_role VALUES ('3c2363839f584216b643e6bd3c05695d', '811d784a392ad816');
INSERT INTO sys_menu_role VALUES ('ed8df2ffe77645cdb7a1b2b10f5d89e4', '811d784a392ad816');
INSERT INTO sys_menu_role VALUES ('8db930130a1e4b2b9fd479d1f9a4ed45', '811d784a392ad816');
INSERT INTO sys_menu_role VALUES ('3bc6a24d1c5d4196b4d7bc3a732d2208', '811d784a392ad816');
INSERT INTO sys_menu_role VALUES ('be0a8e5ec52c4f0baa2a3edf8194f7f2', '811d784a392ad816');
INSERT INTO sys_menu_role VALUES ('3e7d54f2bd82464484defcb4709b3142', '38ab52848a074a3b8845b09eadb3fd71');
INSERT INTO sys_menu_role VALUES ('3c2363839f584216b643e6bd3c05695d', '38ab52848a074a3b8845b09eadb3fd71');
INSERT INTO sys_menu_role VALUES ('ed8df2ffe77645cdb7a1b2b10f5d89e4', '38ab52848a074a3b8845b09eadb3fd71');
INSERT INTO sys_menu_role VALUES ('8db930130a1e4b2b9fd479d1f9a4ed45', '38ab52848a074a3b8845b09eadb3fd71');
INSERT INTO sys_menu_role VALUES ('3bc6a24d1c5d4196b4d7bc3a732d2208', '38ab52848a074a3b8845b09eadb3fd71');
INSERT INTO sys_menu_role VALUES ('e3c575455f1a4af683b26b3f56a27815', '38ab52848a074a3b8845b09eadb3fd71');
INSERT INTO sys_menu_role VALUES ('be0a8e5ec52c4f0baa2a3edf8194f7f2', '38ab52848a074a3b8845b09eadb3fd71');
INSERT INTO sys_menu_role VALUES ('5c2f6c5c80084a99a9d7166ba328bfdd', '38ab52848a074a3b8845b09eadb3fd71');
INSERT INTO sys_menu_role VALUES ('3e7d54f2bd82464484defcb4709b3142', 'b8174920f33f4b17ad5f415c700aacd2');
INSERT INTO sys_menu_role VALUES ('3c2363839f584216b643e6bd3c05695d', 'b8174920f33f4b17ad5f415c700aacd2');
INSERT INTO sys_menu_role VALUES ('7c3195059e954531909f6b31c91826b9', 'b8174920f33f4b17ad5f415c700aacd2');
INSERT INTO sys_menu_role VALUES ('ed8df2ffe77645cdb7a1b2b10f5d89e4', 'b8174920f33f4b17ad5f415c700aacd2');
INSERT INTO sys_menu_role VALUES ('8db930130a1e4b2b9fd479d1f9a4ed45', 'b8174920f33f4b17ad5f415c700aacd2');
INSERT INTO sys_menu_role VALUES ('3bc6a24d1c5d4196b4d7bc3a732d2208', 'b8174920f33f4b17ad5f415c700aacd2');
INSERT INTO sys_menu_role VALUES ('e3c575455f1a4af683b26b3f56a27815', 'b8174920f33f4b17ad5f415c700aacd2');
INSERT INTO sys_menu_role VALUES ('be0a8e5ec52c4f0baa2a3edf8194f7f2', 'b8174920f33f4b17ad5f415c700aacd2');
INSERT INTO sys_menu_role VALUES ('5c2f6c5c80084a99a9d7166ba328bfdd', 'b8174920f33f4b17ad5f415c700aacd2');
INSERT INTO sys_menu_role VALUES ('8f1eb86b09354635b3857222d54991d3', 'b8174920f33f4b17ad5f415c700aacd2');
INSERT INTO sys_menu_role VALUES ('ba90c64234a44202818e10868ab9da91', 'b8174920f33f4b17ad5f415c700aacd2');
INSERT INTO sys_menu_role VALUES ('893c49dd5fdb44d79bb2897db9472517', 'b8174920f33f4b17ad5f415c700aacd2');
INSERT INTO sys_menu_role VALUES ('6dd18b967ee14d03b82a094b86473345', '811d784a392ad816');
INSERT INTO sys_menu_role VALUES ('17cc729340ac47088e35c5fdb8156bb1', '811d784a392ad816');
INSERT INTO sys_menu_role VALUES ('6dd18b967ee14d03b82a094b86473345', '38ab52848a074a3b8845b09eadb3fd71');
INSERT INTO sys_menu_role VALUES ('17cc729340ac47088e35c5fdb8156bb1', '38ab52848a074a3b8845b09eadb3fd71');
INSERT INTO sys_menu_role VALUES ('6dd18b967ee14d03b82a094b86473345', 'b8174920f33f4b17ad5f415c700aacd2');
INSERT INTO sys_menu_role VALUES ('17cc729340ac47088e35c5fdb8156bb1', 'b8174920f33f4b17ad5f415c700aacd2');

CREATE TABLE sys_role  (
    id varchar(32) NOT NULL ,
    authority varchar(30) NULL DEFAULT NULL ,
    name varchar(100) NULL DEFAULT NULL ,
    create_time timestamp NULL DEFAULT NULL ,
    PRIMARY KEY (id)
);

INSERT INTO sys_role VALUES ('38ab52848a074a3b8845b09eadb3fd71', 'ROLE_GENERAL', '普通用户', '2020-01-17 11:01:01');
INSERT INTO sys_role VALUES ('811d784a392ad816', 'ROLE_TEST', '测试', '2020-01-11 19:34:21');
INSERT INTO sys_role VALUES ('b8174920f33f4b17ad5f415c700aacd2', 'ROLE_ADMIN', '管理员', '2019-12-12 21:42:43');

CREATE TABLE sys_user  (
    id varchar(32) NOT NULL ,
    name varchar(30) NULL DEFAULT NULL ,
    password varchar(100) NULL DEFAULT NULL ,
    nick_name varchar(50) NULL DEFAULT NULL,
    sex char(1) NULL DEFAULT NULL ,
    mobile varchar(100) NULL DEFAULT NULL ,
    email varchar(100) NULL DEFAULT NULL ,
    birthday varchar(10) NULL DEFAULT NULL ,
    hobby varchar(255) NULL DEFAULT NULL ,
    live_address varchar(500) NULL DEFAULT NULL ,
    create_time timestamp NULL DEFAULT NULL ,
    update_time timestamp NULL DEFAULT NULL ,
    PRIMARY KEY (id)
);

INSERT INTO sys_user VALUES ('64c40c54ef21495da322901107a7ad00', 'admin', '$2a$10$OgVXB6JzNxeGBVd2iAtRP.6JbKL/1WAwu2GuV91OkXfwqVemKwcWa', 'admin', '男', '177', '111111@qq.com', '2020-01-06', '111', '22', '2019-12-12 21:41:53', '2020-01-17 10:56:02');
INSERT INTO sys_user VALUES ('86fdffc82e2dcfdf', 'test', '$2a$10$wGIuvNDCU0hZFndHBGmjcOIWviuEPQJzApyZ1h4oFUrgdL/Uo4XA6', 'test', '女', '17788886666', '111@qq.com', '2020-01-15', 'dfsf', 'ff', '2020-01-11 19:31:41', '2020-01-14 15:20:20');
INSERT INTO sys_user VALUES ('8cd77cb345e94c31a5b884ed6e3616a2', 'thyme', '$2a$10$WF0tz62CbtwiBNGb6YPu9.o0ojKHZx7iQXn.ZzAlPnAQ.IppcT3AG', 'thyme', '男', '17788886666', '111@qq.com', '2020-01-06', 'f', 'ff', '2019-12-12 21:42:15', '2020-01-14 15:20:49');
INSERT INTO sys_user VALUES ('e1941a92b1c74c8893e29b6ad95cf0ba', 'cxd', '$2a$10$CzRDbWQGQgAiDB1cg8ut.O7XBYh2EFdQg0xLOiNcffLYjUhCW7FQq', 'caixiaodong', '男', '17788886666', '111@qq.com', '2020-01-20', 'aa', 'aa', '2020-01-17 11:01:44', NULL);

CREATE TABLE sys_user_role  (
    user_id varchar(32) NOT NULL ,
    role_id varchar(32) NOT NULL
);

INSERT INTO sys_user_role VALUES ('64c40c54ef21495da322901107a7ad00', 'b8174920f33f4b17ad5f415c700aacd2');
INSERT INTO sys_user_role VALUES ('86fdffc82e2dcfdf', 'b8174920f33f4b17ad5f415c700aacd2');
INSERT INTO sys_user_role VALUES ('8cd77cb345e94c31a5b884ed6e3616a2', 'b8174920f33f4b17ad5f415c700aacd2');
INSERT INTO sys_user_role VALUES ('e1941a92b1c74c8893e29b6ad95cf0ba', '38ab52848a074a3b8845b09eadb3fd71');

select * from sys_log;
select * from sys_menu;
select * from sys_menu_role;
select * from sys_role;
select * from sys_user;
select * from sys_user_role;