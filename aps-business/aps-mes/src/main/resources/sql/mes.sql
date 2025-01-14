create table mes_customer
(
    customer_id bigint        not null comment '客户编号',
    name        varchar(30)   not null comment '客户名称',
    phone       varchar(13)   not null comment '联系电话',
    region      varchar(30)   not null comment '地域',
    remark      varchar(100)  null comment '备注',
    create_by   varchar(10)   null comment '创建人',
    create_time datetime      null comment '创建时间',
    update_by   varchar(10)   null comment '更新人',
    update_time datetime      null comment '更新时间',
    del_flag    int default 0 not null comment '软删除标记',
    constraint mes_customer_pk
        primary key (customer_id)
)
    comment 'MES-客户表';

create table mes_order
(
    order_id         bigint        not null comment '订单编号',
    order_ancestors  varchar(30)   not null comment '订单祖籍',
    customer_id      bigint        not null comment '客户编号',
    customer_name    varchar(30)   not null comment '客户名称',
    order_time       datetime      not null comment '下单时间',
    order_limit_time datetime      not null comment '需求时间',
    order_status     varchar(20)   not null comment '订单状态',
    remark           varchar(100)  null comment '备注',
    create_by        varchar(10)   null comment '创建人',
    create_time      datetime      null comment '创建时间',
    update_by        varchar(10)   null comment '更新人',
    update_time      datetime      null comment '更新时间',
    del_flag         int default 0 not null comment '软删除标记',
    constraint mes_order_pk
        primary key (order_id)
)
    comment 'MES-订单表';

create table mes_product
(
    product_id       bigint        not null comment '产品编号',
    product_group_id bigint        not null comment '产品族编号',
    product_name     varchar(30)   not null comment '产品名称',
    product_type     varchar(30)   not null comment '产品类型',
    product_desc     varchar(100)  null comment '产品描述',
    product_img      varchar(100)  null comment '产品图片',
    product_spec_key varchar(30)   not null comment '产品规格字典键',
    product_spec_val varchar(20)   not null comment '产品规格字典值',
    product_mode     varchar(100)  not null comment '产出工步（生产此物方式）',
    product_usage    varchar(100)  not null comment '产入工步（此物用途）',
    remark           varchar(100)  null comment '备注',
    create_by        varchar(10)   null comment '创建人',
    create_time      datetime      null comment '创建时间',
    update_by        varchar(10)   null comment '更新人',
    update_time      datetime      null comment '更新时间',
    del_flag         int default 0 not null comment '软删除标记',
    constraint mes_product_pk
        primary key (product_id)
)
    comment 'MES-产品表';
