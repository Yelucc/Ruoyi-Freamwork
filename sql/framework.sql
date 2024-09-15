-- auto-generated definition
create table sys_url_map
(
    short_url    varchar(255) not null comment '短链'
        primary key,
    long_url     blob         not null comment '原始链',
    create_time  datetime     not null comment '创建时间',
    expires_time datetime     null comment '过期时间'
)
    charset = utf8mb4;

-- 菜单 SQL
insert into sys_menu (menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
values('短链映射', '2', '1', 'urlmap', 'system/urlmap/index', 1, 0, 'C', '0', '0', 'system:urlmap:list', '#', 'admin', sysdate(), '', null, '短链映射菜单');

-- 按钮父菜单ID
SELECT @parentId := LAST_INSERT_ID();

-- 按钮 SQL
insert into sys_menu (menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
values('短链映射查询', @parentId, '1',  '#', '', 1, 0, 'F', '0', '0', 'system:urlmap:query',        '#', 'admin', sysdate(), '', null, '');

insert into sys_menu (menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
values('短链映射新增', @parentId, '2',  '#', '', 1, 0, 'F', '0', '0', 'system:urlmap:add',          '#', 'admin', sysdate(), '', null, '');

insert into sys_menu (menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
values('短链映射修改', @parentId, '3',  '#', '', 1, 0, 'F', '0', '0', 'system:urlmap:edit',         '#', 'admin', sysdate(), '', null, '');

insert into sys_menu (menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
values('短链映射删除', @parentId, '4',  '#', '', 1, 0, 'F', '0', '0', 'system:urlmap:remove',       '#', 'admin', sysdate(), '', null, '');

insert into sys_menu (menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
values('短链映射导出', @parentId, '5',  '#', '', 1, 0, 'F', '0', '0', 'system:urlmap:export',       '#', 'admin', sysdate(), '', null, '');