-- 创建数据库，如果不存在则创建
CREATE SCHEMA IF NOT EXISTS home_service COLLATE utf8mb4_general_ci;

-- 使用数据库
USE home_service;

-- 创建表，如果不存在则创建

CREATE TABLE IF NOT EXISTS hs_user
(
    user_id        varchar(60)         not null comment 'id'
        primary key,
    user_name      varchar(50)         not null,
    user_passwd    varchar(60)         not null,
    user_sex       tinyint  default 0  not null comment '1 male 2 female',
    user_email     varchar(50)         null comment 'email',
    user_cellphone varchar(15)         null,
    user_openid    varchar(128)        null,
    user_role_id   smallint default -1 not null comment '对应的权限 id'
)
    comment 'user table';



create table IF NOT EXISTS home_service.hs_permission
(
    pm_id         int auto_increment
        primary key,
    pm_role_id    smallint    not null,
    pm_permission varchar(50) not null
)
    comment '权限表';

INSERT INTO home_service.hs_roles (roles_id, roles_name, roles_enable) VALUES (0, '超级管理员', 1);
INSERT INTO home_service.hs_roles (roles_id, roles_name, roles_enable) VALUES (1, '管理员', 1);
INSERT INTO home_service.hs_roles (roles_id, roles_name, roles_enable) VALUES (-1, '来宾', 1);
INSERT INTO home_service.hs_roles (roles_id, roles_name, roles_enable) VALUES (2, '用户', 1);
INSERT INTO home_service.hs_roles (roles_id, roles_name, roles_enable) VALUES (3, '未授权用户', 1);

create table IF NOT EXISTS home_service.hs_roles
(
    roles_id     smallint          not null comment '权限 id',
    roles_name   varchar(10)       not null comment '权限名',
    roles_enable tinyint default 1 not null comment '0关闭 1开启'
)
    comment '权限表';

