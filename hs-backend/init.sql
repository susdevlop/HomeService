create table hs_user
(
    user_id        varchar(60)       not null comment 'id',
    user_name      varchar(50)       not null,
    user_passwd    varchar(60)       not null,
    user_sex       tinyint default 0 not null comment '1 male 2 female',
    user_email     varchar(50)       null comment 'email',
    user_cellphone varchar(15)       null,
    user_openid    varchar(128)      null
)
    comment 'user table ';

