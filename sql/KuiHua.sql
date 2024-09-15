-- auto-generated definition
create table kh_score_record
(
    record_id      bigint auto_increment comment '记录ID'
        primary key,
    user_id        bigint                       not null comment '用户ID',
    status         varchar(10) default 'Normal' not null comment '状态',
    score          int         default 0        not null comment '积分',
    shared_link    varchar(1000)                not null comment '种草链接',
    shared_picture varchar(4000)                not null comment '种草图片',
    create_by      varchar(100)                 null comment '创建人',
    create_time    datetime                     null comment '创建时间',
    update_by      varchar(100)                 null comment '更新人',
    update_time    datetime                     null comment '更新时间'
)
    comment '葵花分数记录表';

