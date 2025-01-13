CREATE TABLE loya_jewel_case
(
    jewel_id             INT AUTO_INCREMENT PRIMARY KEY COMMENT '首饰ID',
    jewel_name           VARCHAR(255) NOT NULL COMMENT '首饰名称',
    category             VARCHAR(50)  NOT NULL COMMENT '首饰分类 (earrings, necklace, bracelet, bangle, brooch, ring)',
    image_url            VARCHAR(255) NOT NULL COMMENT '首饰图片地址',
    reservation_status   VARCHAR(50)  NOT NULL COMMENT '首饰预定状态 (available, reserved to be sent, on trip)',
    expected_return_time DATETIME COMMENT '预计寄回时间',
    create_by            VARCHAR(50)  NOT NULL COMMENT '创建人',
    create_time          DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    update_by            VARCHAR(50) COMMENT '更新人',
    update_time          DATETIME              DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间'
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4 COMMENT ='首饰管理表';

CREATE TABLE loya_order_management
(
    order_id                    INT AUTO_INCREMENT PRIMARY KEY COMMENT '订单ID',
    jewel_code                  VARCHAR(50)  NOT NULL COMMENT '关联首饰编码',
    artist_name                 VARCHAR(100) NOT NULL COMMENT '艺人',
    purpose                     VARCHAR(255) NOT NULL COMMENT '用途',
    return_photo_time           DATETIME COMMENT '返图时间',
    exposure_method             VARCHAR(255) COMMENT '露出方式',
    delivery_date               DATE COMMENT '需求送达日期',
    expected_usage_duration     INT COMMENT '预计使用时长 (天)',
    shipment_tracking_no        VARCHAR(100) COMMENT '寄出物流单号',
    arrival_date                DATETIME COMMENT '送达日期（基础签收时间）',
    return_shipment_tracking_no VARCHAR(100) COMMENT '寄返物流单号',
    return_date                 DATETIME COMMENT '寄回时间（寄返签收时间）',
    return_photo                VARCHAR(255) COMMENT '订单返图',
    promotion_link              VARCHAR(255) COMMENT '宣发链接',
    order_status                VARCHAR(50)  NOT NULL COMMENT '订单流程 (pending shipment, shipped awaiting receipt, received awaiting return, returned)',
    create_by                   VARCHAR(50)  NOT NULL COMMENT '创建人',
    create_time                 DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    update_by                   VARCHAR(50) COMMENT '更新人',
    update_time                 DATETIME              DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间'
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4 COMMENT ='订单管理表';

CREATE TABLE loya_logistics
(
    logistics_id      INT AUTO_INCREMENT PRIMARY KEY COMMENT '物流ID',
    tracking_number   VARCHAR(50) COMMENT '物流单号',
    order_id          BIGINT       NOT NULL COMMENT '关联订单ID',
    direction         VARCHAR(50)  NOT NULL COMMENT '物流单号',
    shipper           VARCHAR(50) COMMENT '快递公司',
    jewel_ids         VARCHAR(255) NOT NULL COMMENT '关联多个首饰ID，用逗号分隔',
    sender_name       VARCHAR(100) NOT NULL COMMENT '寄件人',
    recipient_name    VARCHAR(100) NOT NULL COMMENT '收件人',
    sender_phone      VARCHAR(20)  NOT NULL COMMENT '寄件电话',
    recipient_phone   VARCHAR(20)  NOT NULL COMMENT '收件电话',
    sender_address    VARCHAR(255) NOT NULL COMMENT '寄件地址',
    recipient_address VARCHAR(255) NOT NULL COMMENT '收件地址',
    sender_time       DATETIME COMMENT '寄件时间',
    recipient_time    DATETIME COMMENT '收件时间',
    create_by         VARCHAR(50)  NOT NULL COMMENT '创建人',
    create_time       DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    update_by         VARCHAR(50) COMMENT '更新人',
    update_time       DATETIME              DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间'
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4 COMMENT ='物流管理表';

-- 插入首饰分类的字典类型
INSERT INTO sys_dict_type (dict_name, dict_type, status, create_by, create_time, remark)
VALUES ('首饰分类', 'jewel_category', '0', 'admin', NOW(), '首饰分类列表');

-- 插入首饰预定状态的字典类型
INSERT INTO sys_dict_type (dict_name, dict_type, status, create_by, create_time, remark)
VALUES ('首饰预定状态', 'jewel_reservation_status', '0', 'admin', NOW(), '首饰预定状态列表');

-- 插入订单流程的字典类型
INSERT INTO sys_dict_type (dict_name, dict_type, status, create_by, create_time, remark)
VALUES ('订单流程', 'order_status', '0', 'admin', NOW(), '订单流程列表');

INSERT INTO sys_dict_type (dict_name, dict_type, status, create_by, create_time, remark)
VALUES ('物流方向', 'logistics_direction', '0', 'admin', NOW(), '物流方向');
-- 插入首饰分类数据
INSERT INTO sys_dict_data (dict_code, dict_sort, dict_label, dict_value, dict_type, status, create_by, create_time,
                           remark)
VALUES (30, 1, '耳饰', 'earrings', 'jewel_category', '0', 'admin', NOW(), '耳饰'),
       (31, 2, '项链', 'necklace', 'jewel_category', '0', 'admin', NOW(), '项链'),
       (32, 3, '手链', 'bracelet', 'jewel_category', '0', 'admin', NOW(), '手链'),
       (33, 4, '手镯', 'bangle', 'jewel_category', '0', 'admin', NOW(), '手镯'),
       (34, 5, '胸针', 'brooch', 'jewel_category', '0', 'admin', NOW(), '胸针'),
       (35, 6, '戒指', 'ring', 'jewel_category', '0', 'admin', NOW(), '戒指');

-- 插入首饰预定状态数据
INSERT INTO sys_dict_data (dict_code, dict_sort, dict_label, dict_value, dict_type, status, create_by, create_time,
                           remark)
VALUES (36, 1, '空闲', 'available', 'jewel_reservation_status', '0', 'admin', NOW(), '空闲状态'),
       (37, 2, '已预定待寄出', 'reserved', 'jewel_reservation_status', '0', 'admin', NOW(), '已预定待寄出状态'),
       (38, 3, '已出差', 'trip', 'jewel_reservation_status', '0', 'admin', NOW(), '已出差状态');

-- 插入订单流程数据
INSERT INTO sys_dict_data (dict_code, dict_sort, dict_label, dict_value, dict_type, status, create_by, create_time,
                           remark)
VALUES (39, 1, '锁定', 'locked', 'order_status', '0', 'admin', NOW(), '锁定'),
       (40, 1, '待寄出', 'pending_shipment', 'order_status', '0', 'admin', NOW(), '待寄出流程'),
       (41, 2, '已寄出待签收', 'shipped_awaiting_receipt', 'order_status', '0', 'admin', NOW(), '已寄出待签收流程'),
       (42, 3, '已签收待寄回', 'received_awaiting_return', 'order_status', '0', 'admin', NOW(), '已签收待寄回流程'),
       (43, 4, '已寄回', 'returned', 'order_status', '0', 'admin', NOW(), '已寄回流程');

INSERT INTO sys_dict_data (dict_code, dict_sort, dict_label, dict_value, dict_type, status, create_by, create_time,
                           remark)
VALUES (44, 1, '寄出', 'trip', 'logistics_direction', '0', 'admin', NOW(), '寄出'),
       (45, 1, '寄返', 'turn', 'logistics_direction', '0', 'admin', NOW(), '寄返');


-- 菜单 SQL
insert into sys_menu (menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status,
                      perms, icon, create_by, create_time, update_by, update_time, remark)
values ('首饰管理', '1', '1', 'jewelcase', 'jewel/jewelcase/index', 1, 0, 'C', '0', '0', 'jewel:jewelcase:list', '#',
        'admin', sysdate(), '', null, '首饰管理菜单');

-- 按钮父菜单ID
SELECT @parentId := LAST_INSERT_ID();

-- 按钮 SQL
insert into sys_menu (menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status,
                      perms, icon, create_by, create_time, update_by, update_time, remark)
values ('首饰管理查询', @parentId, '1', '#', '', 1, 0, 'F', '0', '0', 'jewel:jewelcase:query', '#', 'admin', sysdate(),
        '', null, '');

insert into sys_menu (menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status,
                      perms, icon, create_by, create_time, update_by, update_time, remark)
values ('首饰管理新增', @parentId, '2', '#', '', 1, 0, 'F', '0', '0', 'jewel:jewelcase:add', '#', 'admin', sysdate(),
        '', null, '');

insert into sys_menu (menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status,
                      perms, icon, create_by, create_time, update_by, update_time, remark)
values ('首饰管理修改', @parentId, '3', '#', '', 1, 0, 'F', '0', '0', 'jewel:jewelcase:edit', '#', 'admin', sysdate(),
        '', null, '');

insert into sys_menu (menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status,
                      perms, icon, create_by, create_time, update_by, update_time, remark)
values ('首饰管理删除', @parentId, '4', '#', '', 1, 0, 'F', '0', '0', 'jewel:jewelcase:remove', '#', 'admin', sysdate(),
        '', null, '');

insert into sys_menu (menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status,
                      perms, icon, create_by, create_time, update_by, update_time, remark)
values ('首饰管理导出', @parentId, '5', '#', '', 1, 0, 'F', '0', '0', 'jewel:jewelcase:export', '#', 'admin', sysdate(),
        '', null, '');

-- 菜单 SQL
insert into sys_menu (menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status,
                      perms, icon, create_by, create_time, update_by, update_time, remark)
values ('订单管理', '1', '1', 'orderManagement', 'order/orderManagement/index', 1, 0, 'C', '0', '0',
        'order:orderManagement:list', '#', 'admin', sysdate(), '', null, '订单管理菜单');

-- 按钮父菜单ID
SELECT @parentId := LAST_INSERT_ID();

-- 按钮 SQL
insert into sys_menu (menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status,
                      perms, icon, create_by, create_time, update_by, update_time, remark)
values ('订单管理查询', @parentId, '1', '#', '', 1, 0, 'F', '0', '0', 'order:orderManagement:query', '#', 'admin',
        sysdate(), '', null, '');

insert into sys_menu (menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status,
                      perms, icon, create_by, create_time, update_by, update_time, remark)
values ('订单管理新增', @parentId, '2', '#', '', 1, 0, 'F', '0', '0', 'order:orderManagement:add', '#', 'admin',
        sysdate(), '', null, '');

insert into sys_menu (menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status,
                      perms, icon, create_by, create_time, update_by, update_time, remark)
values ('订单管理修改', @parentId, '3', '#', '', 1, 0, 'F', '0', '0', 'order:orderManagement:edit', '#', 'admin',
        sysdate(), '', null, '');

insert into sys_menu (menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status,
                      perms, icon, create_by, create_time, update_by, update_time, remark)
values ('订单管理删除', @parentId, '4', '#', '', 1, 0, 'F', '0', '0', 'order:orderManagement:remove', '#', 'admin',
        sysdate(), '', null, '');

insert into sys_menu (menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status,
                      perms, icon, create_by, create_time, update_by, update_time, remark)
values ('订单管理导出', @parentId, '5', '#', '', 1, 0, 'F', '0', '0', 'order:orderManagement:export', '#', 'admin',
        sysdate(), '', null, '');

-- 菜单 SQL
insert into sys_menu (menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status,
                      perms, icon, create_by, create_time, update_by, update_time, remark)
values ('物流管理', '1', '1', 'logistics', 'logistics/logistics/index', 1, 0, 'C', '0', '0', 'logistics:logistics:list',
        '#', 'admin', sysdate(), '', null, '物流管理菜单');

-- 按钮父菜单ID
SELECT @parentId := LAST_INSERT_ID();

-- 按钮 SQL
insert into sys_menu (menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status,
                      perms, icon, create_by, create_time, update_by, update_time, remark)
values ('物流管理查询', @parentId, '1', '#', '', 1, 0, 'F', '0', '0', 'logistics:logistics:query', '#', 'admin',
        sysdate(), '', null, '');

insert into sys_menu (menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status,
                      perms, icon, create_by, create_time, update_by, update_time, remark)
values ('物流管理新增', @parentId, '2', '#', '', 1, 0, 'F', '0', '0', 'logistics:logistics:add', '#', 'admin',
        sysdate(), '', null, '');

insert into sys_menu (menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status,
                      perms, icon, create_by, create_time, update_by, update_time, remark)
values ('物流管理修改', @parentId, '3', '#', '', 1, 0, 'F', '0', '0', 'logistics:logistics:edit', '#', 'admin',
        sysdate(), '', null, '');

insert into sys_menu (menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status,
                      perms, icon, create_by, create_time, update_by, update_time, remark)
values ('物流管理删除', @parentId, '4', '#', '', 1, 0, 'F', '0', '0', 'logistics:logistics:remove', '#', 'admin',
        sysdate(), '', null, '');

insert into sys_menu (menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status,
                      perms, icon, create_by, create_time, update_by, update_time, remark)
values ('物流管理导出', @parentId, '5', '#', '', 1, 0, 'F', '0', '0', 'logistics:logistics:export', '#', 'admin',
        sysdate(), '', null, '');

