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
INSERT INTO sys_menu (menu_id, menu_name, parent_id, order_num, path, component, query, route_name, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark) VALUES (2000, '葵花项目', 0, 2, 'kuihua', null, null, '', 1, 0, 'M', '0', '0', null, '#', 'admin', '2024-09-15 04:20:26', '', null, '');

-- 菜单 SQL
insert into sys_menu (menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
values('葵花分数记录', '2000', '1', 'scoreRecord', 'KuiHua/scoreRecord/index', 1, 0, 'C', '0', '0', 'KuiHua:scoreRecord:list', '#', 'admin', sysdate(), '', null, '葵花分数记录菜单');

-- 按钮父菜单ID
SELECT @parentId := LAST_INSERT_ID();

-- 按钮 SQL
insert into sys_menu (menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
values('葵花分数记录查询', @parentId, '1',  '#', '', 1, 0, 'F', '0', '0', 'KuiHua:scoreRecord:query',        '#', 'admin', sysdate(), '', null, '');

insert into sys_menu (menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
values('葵花分数记录新增', @parentId, '2',  '#', '', 1, 0, 'F', '0', '0', 'KuiHua:scoreRecord:add',          '#', 'admin', sysdate(), '', null, '');

insert into sys_menu (menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
values('葵花分数记录修改', @parentId, '3',  '#', '', 1, 0, 'F', '0', '0', 'KuiHua:scoreRecord:edit',         '#', 'admin', sysdate(), '', null, '');

insert into sys_menu (menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
values('葵花分数记录删除', @parentId, '4',  '#', '', 1, 0, 'F', '0', '0', 'KuiHua:scoreRecord:remove',       '#', 'admin', sysdate(), '', null, '');

insert into sys_menu (menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
values('葵花分数记录导出', @parentId, '5',  '#', '', 1, 0, 'F', '0', '0', 'KuiHua:scoreRecord:export',       '#', 'admin', sysdate(), '', null, '');