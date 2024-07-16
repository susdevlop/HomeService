-- 创建数据库，如果不存在则创建
CREATE SCHEMA IF NOT EXISTS home_service COLLATE utf8mb4_general_ci;

-- 使用数据库
USE home_service;

-- 创建表，如果不存在则创建
CREATE TABLE IF NOT EXISTS hs_user
(
    user_id        VARCHAR(60)       NOT NULL COMMENT 'id',
    user_name      VARCHAR(50)       NOT NULL,
    user_passwd    VARCHAR(60)       NOT NULL,
    user_sex       TINYINT DEFAULT 0 NOT NULL COMMENT '1 male 2 female',
    user_email     VARCHAR(50)       NULL COMMENT 'email',
    user_cellphone VARCHAR(15)       NULL,
    user_openid    VARCHAR(128)      NULL,
    PRIMARY KEY (user_id)
    )
    COMMENT 'user table';